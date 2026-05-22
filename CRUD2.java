import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.table.*;
import java.util.*;

    class Student {
         String name, bday, number, gender, status;

            Student(String name, String bday,
                String number, String gender,
                String status) {

                this.name = name;
                this.bday = bday;
                this.number = number;
                this.gender = gender;
                this.status = status;
            }

    }

public class CRUD2 extends JFrame{
    private static final String filename = "what.txt";
    private static final String dm = "#";
    private JButton btn1, btn2, btn3;
    private JLabel lbl1, lbl2, lbl3, lbl4, lbl5;
    private JTextField txt1, txt2, txt3;
    private ButtonGroup gr;
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane sp;
    private JRadioButton rb1,rb2;
    private JComboBox<String> cb1;
    ArrayList<Student> list = new ArrayList<>();

    CRUD2() {
        //lbl
        lbl1 = new JLabel("Name");
        lbl2 = new JLabel("Birth Date");
        lbl3 = new JLabel("Contact Number");
        lbl4 = new JLabel("Gender");
        lbl5 = new JLabel("Status");
        add(lbl5).setBounds(250, 90,100,20);
        add(lbl4).setBounds(250,20,100,20);
        add(lbl1).setBounds(20,20,100,20);
        add(lbl2).setBounds(20, 90,100,20);
        add(lbl3).setBounds(20,160,100,20);

        //txt
        txt1 = new JTextField();
        txt2 = new JTextField();
        txt3 = new JTextField();
        add(txt1).setBounds(20,40,200,25);
        add(txt2).setBounds(20,110,200,25);
        add(txt3).setBounds(20,180,200,25);

        // radiobtn
        rb1 = new JRadioButton("Male");
        rb2 = new JRadioButton("Female");
        gr = new ButtonGroup();
        gr.add(rb1);
        gr.add(rb2);
        add(rb1).setBounds(250,40,60,20);
        add(rb2).setBounds(310,40,80,20);

        //cbox
        String[] stat = {"Single","Married","Divorced", "Widowed"};
        cb1 = new JComboBox<>(stat);
        add(cb1).setBounds(250,110,150,25);

        //btns
        btn1 = new JButton("Add");
        btn2 = new JButton("Update");
        btn3 = new JButton("Delete");
        add(btn1).setBounds(260,220,85,30);
        add(btn2).setBounds(160,220,85,30);
        add(btn3).setBounds(60,220,85,30);

        String[] col = {"Name", "Bday", "CP Number", "Gender", "Status"};
        model = new DefaultTableModel(col,0);
        table = new JTable(model);
        sp = new JScrollPane(table);
        add(sp).setBounds(10,260,460,190);

        read();
        add();
        update();
        delete();
        tableSelect();



        JTableHeader th = table.getTableHeader();
        th.setBackground(new Color(0,220,200));
        setLayout(null);
        setVisible(true);
        setSize(500,500);
        setLocationRelativeTo(null);
        setTitle("whattttt");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        

    }
    void add() {
        btn1.addActionListener(e -> {
            int conf = JOptionPane.showConfirmDialog(null, "Please confirm","Confirm Add", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                String name = txt1.getText();
                String bday = txt2.getText();
                String num = txt3.getText();
                String gend = "";
                if (rb1.isSelected()) {
                    gend = rb1.getText();
                } else if (rb2.isSelected()) {
                    gend = rb2.getText();
            }
                String status = (String) cb1.getSelectedItem().toString();

                Student s = new Student(
                    name, bday, num, gend, status
                );
                list.add(s);

                model.insertRow(0, new Object [] {
                    s.name, s.bday, s.number, s.gender, s.status
                });
            }
            

            save();
            clear();
        });
    }
    void update() {
        btn2.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row first");
                return;
            }
            int conf = JOptionPane.showConfirmDialog(null, "Are you sure you want to update data?", "Confirm update", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                
                String gend = "";
                if (rb1.isSelected()) {
                    gend = rb1.getText();
                } else if (rb2.isSelected()) {
                    gend = rb2.getText();
                }

                Student s = list.get(row);
                s.name = txt1.getText();
                s.bday = txt2.getText();
                s.number = txt3.getText();
                s.gender = gend;
                s.status = cb1.getSelectedItem().toString();

                model.setValueAt(s.name,row,0);
                model.setValueAt(s.bday,row,1);
                model.setValueAt(s.number,row,2);
                model.setValueAt(s.gender,row,3);
                model.setValueAt(s.status,row,4);
            }
            save();
            clear();
        });
    }
    void delete() {
        btn3.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please select a row first"
                );
                return;
            }
            int conf = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete?","Confirm Delete",JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                list.remove(row);
                model.removeRow(row);
                save();
                clear();
            }
        });

    }
    void clear() {
        txt1.setText("");
        txt2.setText("");
        txt3.setText("");
        gr.clearSelection();
        cb1.setSelectedIndex(0);
    }
    void tableSelect() {
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();

                txt1.setText(model.getValueAt(row, 0).toString());
                txt2.setText(model.getValueAt(row, 1).toString());
                txt3.setText(model.getValueAt(row, 2).toString());
                String gend = model.getValueAt(row, 3).toString();
                if(gend.equals("Male")) {
                    rb1.setSelected(true);
                } else if (gend.equals("Female")) {
                    rb2.setSelected(true);
                }
                cb1.setSelectedItem(model.getValueAt(row, 4).toString());
                
            }
        });
    }
    void read() {
        try {

            File file = new File(filename);

            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {

                String[] data = line.split(dm);

                if (data.length == 5) {

                    Student s = new Student(
                            data[0],
                            data[1],
                            data[2],
                            data[3],
                            data[4]
                    );

                    list.add(s);
                    model.addRow(new Object[] {
                            s.name,
                            s.bday,
                            s.number,
                            s.gender,
                            s.status
                    });
                }
            }
            br.close();
        } catch (IOException e) {

            JOptionPane.showMessageDialog(null,e);
        }
    }
    void save() {
        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            for (Student s : list) {
                bw.write(
                        s.name + dm +
                        s.bday + dm +
                        s.number + dm +
                        s.gender + dm +
                        s.status
                );
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {

            JOptionPane.showMessageDialog(null,e);
        }
    }


    
    
    
    public static void main(String[] args) {
        new CRUD2();
    }

}
