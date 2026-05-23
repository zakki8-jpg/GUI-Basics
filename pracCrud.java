import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class pracCrud extends JFrame {
    private static final String filename = "stud.txt";
    private static final String d = "#";
    private JTextField txtid, txtname;
    private JLabel lblid,lblname,lblgender,lblyear;
    private JRadioButton rb1,rb2;
    private JComboBox<String> year;
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane sp;
    private ButtonGroup gr;
    private JButton btnadd, btnupd, btndel;

    pracCrud() {
        //lbls
        lblid = new JLabel("Student ID:");
        lblname = new JLabel("Full Name:");
        lblgender = new JLabel("Gender:");
        lblyear = new JLabel("Year Level:");
        add(lblid).setBounds(20,20,80,20);
        add(lblname).setBounds(20,60,100,20);
        add(lblgender).setBounds(310,20,100,20);
        add(lblyear).setBounds(425,60,85,20);

        //txt
        txtid = new JTextField();
        txtname = new JTextField();
        add(txtid).setBounds(85,20,200,30);
        add(txtname).setBounds(85,60,200,30);

        //btns
        btnadd = new JButton("Add");
        btnupd = new JButton("Update");
        btndel = new JButton("Delete");
        add(btnadd).setBounds(50,125,100,40);
        add(btnupd).setBounds(250,125,100,40);
        add(btndel).setBounds(450,125,100,40);

        //rbs
        rb1 = new JRadioButton("Male");
        rb2 = new JRadioButton("Female");
        gr = new ButtonGroup();
        gr.add(rb1);
        gr.add(rb2);
        add(rb1).setBounds(310,45,80,20);
        add(rb2).setBounds(310,70,80,20);

        //cbox
        String[] yrlvl = {"1st Year", "2nd Year","3rd Year","4th Year"};
        year = new JComboBox<String>(yrlvl);
        add(year).setBounds(500,60,100,20);

        //tables
        String[] cols = {"Student ID", "Name", "Gender", "Year Level"};
        model = new DefaultTableModel(cols,0);
        table = new JTable(model);
        sp = new JScrollPane(table);
        add(sp).setBounds(10,200, 615,200);
        JTableHeader th = table.getTableHeader();
        th.setBackground(new Color(20,220,220));


        read();
        tableS();
        add();
        update();
        delete();

        setLayout(null);
        setTitle("Student Information System");
        setSize(650,450);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    void update() {
        btnupd.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row ==-1) {
                JOptionPane.showMessageDialog(null, "Please select a row to update");
                return;
            }
            int conf = JOptionPane.showConfirmDialog(null, "Please Confirm update.","Confirm update", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                model.setValueAt(txtid.getText(), row, 0);
                model.setValueAt(txtname.getText(), row, 1);
                String gend = "";
                if (rb1.isSelected()) {
                    gend = rb1.getText();
                } else if (rb2.isSelected()) {
                    gend = rb2.getText();
                }
                model.setValueAt(gend, row, 2);
                model.setValueAt(year.getSelectedItem(), row, 3);
                save();
                clear();
            }
            
        });
    
    }
    void delete() {
        btndel.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row to delete");
                return;
            }
            int conf = JOptionPane.showConfirmDialog(null, "Please Confirm Delete", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                model.removeRow(row);
                save();
                clear();
            }
        
        });
        
    }
    void add() {
        btnadd.addActionListener(e -> {
            int conf = JOptionPane.showConfirmDialog(null, "Are you sure you want to add data?", "Confirm Add", JOptionPane.YES_NO_OPTION);
            if(conf == JOptionPane.YES_OPTION) {
                String id = txtid.getText();
                String name = txtname.getText();
                String gend = "";
                if (rb1.isSelected()) {
                    gend = rb1.getText();
                } else if (rb2.isSelected()) {
                    gend = rb2.getText();
                }
                String lvl = (String) year.getSelectedItem();

                model.insertRow(0, new Object[] {
                    id, name,gend,lvl
                });
                save();
                clear();
            }
            
        });
    }
    void clear() {
        txtid.setText("");
        txtname.setText("");
        gr.clearSelection();
        year.setSelectedIndex(0);
    }
    void save(){
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
    void read(){
        try {
            File file = new File(filename);
            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(d);

                if (data.length == 4) {
                    model.addRow(data);
                }
            }
            br.close();
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    void tableS () {
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent e) {
                int row = table.getSelectedRow();

                txtid.setText(model.getValueAt(row, 0).toString());
                txtname.setText(model.getValueAt(row, 1).toString());
                String gend = model.getValueAt(row, 2).toString();
                if (gend.equals("Male")) {
                    rb1.setSelected(true);
                } else if (gend.equals("Female")) {
                    rb2.setSelected(true);
                }
                year.setSelectedItem(model.getValueAt(row, 3).toString());
            }
        });
    }






    public static void main (String[] args) {
        new pracCrud();
    }
}
