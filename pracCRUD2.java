import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.io.*;
import java.awt.*;
public class pracCRUD2 extends JFrame{
    private static final String filename = "challenge.txt";
    private static final String d = "#";
    private JLabel lblname, lblage, lblgend, lblstat;
    private JTextField txtname, txtage;
    private DefaultTableModel model;
    private JTable table;
    private JComboBox<String> status;
    private JRadioButton rb1,rb2;
    private ButtonGroup gr;
    private JScrollPane sp;
    private JButton btn1, btn2, btn3;

    pracCRUD2 () {
        //lbl
        lblname = new JLabel("Name");
        lblage = new JLabel("Age");
        lblgend = new JLabel("Gender");
        lblstat = new JLabel("Status");
        add(lblname).setBounds(20,20,100,20);
        add(lblage).setBounds(20, 100, 100, 20);
        add(lblgend).setBounds(20, 180, 100, 20);
        add(lblstat).setBounds(20, 260,100,20);

        //txt
        txtname = new JTextField();
        txtage = new JTextField();
        add(txtname).setBounds(20,40,230,30);
        add(txtage).setBounds(20,120,230,30);

        //rbs
        rb1 = new JRadioButton("Male");
        rb2 = new JRadioButton("Female");
        gr = new ButtonGroup();
        gr.add(rb1);
        gr.add(rb2);
        add(rb1).setBounds(20,200,65,25);
        add(rb2).setBounds(85,200,70,25);

        //cbox
        String[] stat = {"Single", "Married","Divorced", "Widowed"};
        status = new JComboBox<>(stat);
        add(status).setBounds(20,280,200,30);

        //btns
        btn1 = new JButton("Add");
        btn2 = new JButton("Update");
        btn3 = new JButton("Delete");
        add(btn1).setBounds(300,20,100,40);
        add(btn2).setBounds(300,100,100,40);
        add(btn3).setBounds(300,180,100,40);
                

        //table
        String[] cols = {"Name", "Age", "Gender", "Status"}; 
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        sp = new JScrollPane(table);
        add(sp).setBounds(20,320,450,230);
        JTableHeader th = table.getTableHeader();
        th.setBackground(new Color(20,200,200));




        read();
        tableS();
        add();
        update();
        delete();





        setLayout(null);
        setVisible(true);
        setSize(500,600);
        setTitle("Challenge");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    } 
    void clear() {
        txtname.setText("");
        txtage.setText("");
        gr.clearSelection();
        status.setSelectedIndex(0);
    }
    void add() {
        btn1.addActionListener(e -> {
            String name = txtname.getText();
            String age = txtage.getText();
            String gend = "";
            if (rb1.isSelected()) {
                gend = rb1.getText();
            } else if (rb2.isSelected()) {
                gend = rb2.getText();
            }
            String stat = (String) status.getSelectedItem();

            model.insertRow(0, new Object [] {
                name, age, gend, stat
            });
            save();
            clear();
        });
    }
    void update() {
        btn2.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row from the table.");
                return;
            }
            int conf = JOptionPane.showConfirmDialog(null, "Are you sure you want to update the data from the table?", "Confirm Update", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                model.setValueAt(txtname.getText(), row, 0);
                model.setValueAt(txtage.getText(), row, 1);
                String gend = "";
                if (rb1.isSelected()) {
                    gend = rb1.getText();
                } else if (rb2.isSelected()) {
                    gend = rb2.getText();
                }
                model.setValueAt(gend, row, 2);
                model.setValueAt(status.getSelectedItem(), row, 3);
            }
            save();
            clear();
        });
    }
    void delete() {
        btn3.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row from the table first");
                return;
            }
            int conf = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete data?" ,"Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                model.removeRow(row);
                save();
                clear();
            }
        });
    }
    void save() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            for (int i = 0; i < model.getRowCount(); i++) {
                bw.write(
                    model.getValueAt(i, 0) +d+
                    model.getValueAt(i, 1) +d+
                    model.getValueAt(i, 2) +d+
                    model.getValueAt(i, 3) 
                );
                bw.newLine();

            }
            bw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }  

    void read() {
        try {
            File file = new File(filename);
            if (!file.exists()) return;
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null ) {
                String[] data = line.split(d);

                if (data.length == 4) {
                    model.addRow(data);
                }
            }
            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    void tableS() {
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row =table.getSelectedRow();

                txtname.setText(model.getValueAt(row, 0).toString());
                txtage.setText(model.getValueAt(row, 1).toString());
                String gend = model.getValueAt(row, 2).toString();
                if (gend.equals("Male")) {
                    rb1.setSelected(true);
                } else if (gend.equals("Female")) {
                    rb2.setSelected(true);
                }
                status.setSelectedItem(model.getValueAt(row, 3).toString());
            }
        });
    }

    public static void main (String[] args) {
        new pracCRUD2();
    }

}
