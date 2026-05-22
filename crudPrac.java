import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
public class crudPrac extends JFrame{
    static JTextField txtN, txtID, txtC;
    static JLabel lblN, lblID, lblC, lblY, lblG;
    static JRadioButton rb1, rb2;
    static JComboBox<String> cbYear;
    static JButton btnadd, btnupd, btndel, btnclr;
    static ButtonGroup gr;
    static DefaultTableModel model;
    static JTable table;
    crudPrac() {

        lblN = new JLabel("Name");
        lblID = new JLabel("Student ID");
        lblC = new JLabel("Course");
        lblY = new JLabel("Year Level");
        lblG = new JLabel("Gender");
        add(lblN).setBounds(20,20,100,20);
        add(lblID).setBounds(20,70,100,20);
        add(lblC).setBounds(20,120,100,20);
        add(lblY).setBounds(20,170,100,20);
        add(lblG).setBounds(20,250,100,20);


        txtN = new JTextField();
        txtID = new JTextField();
        txtC = new JTextField();
        add(txtN).setBounds(20,40,150,20);
        add(txtID).setBounds(20,90,150,20);
        add(txtC).setBounds(20,140,150,20);

        btnadd = new JButton("Add");
        btnupd = new JButton("Update");
        btndel = new JButton("Delete");
        btnclr = new JButton("Clear");
        add(btnadd).setBounds(20,400,120,25);
        add(btnupd).setBounds(20,440,120,25);
        add(btndel).setBounds(20,480,120,25);
        add(btnclr).setBounds(20,520,120,25);


        
        String[] Year = {"1st Year", "2nd Year", "3rd Year", "4th Year"};
        cbYear = new JComboBox<String>(Year);
        add(cbYear).setBounds(20,190,100,25);
        
        gr = new ButtonGroup();
        rb1 = new JRadioButton("Male");
        rb2 = new JRadioButton("Female");
        gr.add(rb1);
        gr.add(rb2);
        add(rb1).setBounds(20,270,70,20);
        add(rb2).setBounds(90,270,70,20);


        model = new DefaultTableModel();
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        add(sp).setBounds(180,10,380,580); 
        model.addColumn("Name");
        model.addColumn("Student ID");
        model.addColumn("Course");
        model.addColumn("Gender");
        model.addColumn("Year Level");

        JTableHeader th = table.getTableHeader();
        th.setBackground(new Color(0,255,255));
        th.setFont(new Font(Font.DIALOG, Font.BOLD, 12));

        read();
        Add();
        update();
        delete();
        tableSelect();
        clearBtn();

        
        setLayout(null);
        setVisible(true);
        setSize(600,650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Student Registration System");
        setLocationRelativeTo(null);
        setResizable(false);
    }
    //SAVE
    void save() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("Srs.txt"));
            for (int i = 0; i < model.getRowCount(); i++) {
                bw.write(
                    model.getValueAt(i,0) + "#" +
                    model.getValueAt(i, 1) + "#" +
                    model.getValueAt(i, 2) + "#" +
                    model.getValueAt(i, 3) + "#" +
                    model.getValueAt(i,4)
                );
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    //CLEAR
    void clear() {
        txtN.setText("");
        txtID.setText("");
        txtC.setText("");
        cbYear.setSelectedIndex(0);
        gr.clearSelection();
    }
    void clearBtn() {
        btnclr.addActionListener(e -> clear());
    }



    //ADD
    void Add() {
        btnadd.addActionListener(e -> {
            String name = txtN.getText();
            String ID = txtID.getText();
            String course = txtC.getText();
            String year = (String) cbYear.getSelectedItem();
            String gender = "";

            if (rb1.isSelected()) {
                gender = rb1.getText();
            } else if (rb2.isSelected()) {
                gender = rb2.getText();
            }

            model.insertRow(0, new Object [] {
                name, ID, course, gender, year
            });

            save();
            clear();
        });
    }



    //UPDATE
    void update() {
        btnupd.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null ,"Please select a row first.");
                return;
            }

            model.setValueAt(txtN.getText(), row, 0);
            model.setValueAt(txtID.getText(), row, 1);
            model.setValueAt(txtC.getText(), row, 2);
            model.setValueAt(cbYear.getSelectedItem(), row, 3);
            String gender = "";
            if (rb1.isSelected()) {
                gender = rb1.getText();
            } else if (rb2.isSelected()) {
                gender = rb2.getText();
            }
            model.setValueAt(gender, row,4);

            save();
            clear();
            
        });

        
        
    }


    void delete() {
        btndel.addActionListener( e -> {
            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row first");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to delete this record?", "Confirm Delete",JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                model.removeRow(row);
                save();
                clear();
            }
        });
    }



    //READ
    void read() {
        try {
            File file = new File("Srs.txt");
            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader("Srs.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("#");
                if (data.length == 5) {
                    model.addRow(data);
                }
               
            }
            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }


        
    }
    void tableSelect() {
            table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int row = table.getSelectedRow();

                    txtN.setText(model.getValueAt(row, 0).toString());
                    txtID.setText(model.getValueAt(row, 1).toString());
                    txtC.setText(model.getValueAt(row,2).toString());
                    String gend = model.getValueAt(row, 3).toString();
                
                    if (gend.equals("Male")) {
                        rb1.setSelected(true);
                    } else if (gend.equals("Female")) {
                        rb2.setSelected(true);
                    }
                    cbYear.setSelectedItem(model.getValueAt(row,4).toString());
                }
                
            });
        }
    public static void main(String [] args) {
        new crudPrac();
    }

}
