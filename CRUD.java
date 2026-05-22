import javax.swing.*;
import java.io.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class CRUD extends JFrame{
    private static final String FILE_NAME = "enrollment.txt";
    private static final String delim = "#";
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane sp;

    private JButton btnadd, btnupd, btndel, btnclr;
    private JComboBox<String> cors;
    private  JTextField txtname, txtemail, txtadd, txtcont;
    private JLabel lbltitle,lblname, lblemail, lbladd, lblcont, lblgend;
    private JRadioButton rb1, rb2;
    private ButtonGroup gr;

    CRUD () {
        //Labels
        lbltitle = new JLabel("ENROLLMENT SYSTEM");
        lbltitle.setForeground(Color.red);
        lbltitle.setBounds(20,20,150,50);
        add(lbltitle);

        lblname = new JLabel("Full Name");
        lblemail = new JLabel("Email");
        lbladd = new JLabel("Address");
        lblcont = new JLabel("Contact Number");
        lblgend = new JLabel("Gender");
        add(lblname).setBounds(20,100,100,20);
        add(lblemail).setBounds(20,180,100,20);
        add(lbladd).setBounds(20,260,100,20);
        add(lblcont).setBounds(20,340,100,20);
        add(lblgend).setBounds(20,420,100,20);
        //COmboBOx
        JLabel lblcourse = new JLabel("Course");
        add(lblcourse).setBounds(20,490,100,20);
        String[] courses = {"BSIT", "BSCS", "BSIS", "BSEMC"};
        cors = new JComboBox<String>(courses);
        add(cors).setBounds(20,510,200,20);


        //JButtons
        btnadd = new JButton("Register");
        btnupd = new JButton("Update");
        btndel = new JButton("Delete");
        btnclr = new JButton("Clear");
        add(btnadd).setBounds(655,550,100,40);
        add(btnupd).setBounds(545,550,100,40);
        add(btndel).setBounds(435,550,100,40);
        add(btnclr).setBounds(325,550,100,40);
        



        //RadioButton
        rb1 = new JRadioButton("Male");
        rb2 = new JRadioButton("Female");
        gr = new ButtonGroup();
        gr.add(rb1);
        gr.add(rb2);
        add(rb1).setBounds(20,440,70,20);
        add(rb2).setBounds(90,440,100,20);

        

        //TextFields
        txtname = new JTextField();
        txtemail = new JTextField();
        txtadd = new JTextField();
        txtcont = new JTextField();
        add(txtname).setBounds(20,120,250,25);
        add(txtemail).setBounds(20,200,250,25);
        add(txtadd).setBounds(20,280,250,25);
        add(txtcont).setBounds(20,360,250,25);

        //Table
        String [] columns = {"Name", "Email", "Address","Contact","Gender","Course"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        sp = new JScrollPane(table);
        add(sp).setBounds(285,100,470,440);

        JTableHeader th = table.getTableHeader();
        th.setBackground(new Color(0,255,255));



        read();
        add();
        clearB();
        update();
        delete();
        tableSelect();


        setLayout(null);
        setVisible(true);
        setSize(800,650);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Enrollment System");

    }

    void save() {
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME));
            for (int i = 0; i < model.getRowCount(); i++) {
                bw.write(
                    model.getValueAt(i, 0) +delim+
                    model.getValueAt(i, 1) +delim+
                    model.getValueAt(i, 2) +delim+
                    model.getValueAt(i, 3) +delim+
                    model.getValueAt(i, 4) +delim+
                    model.getValueAt(i, 5) 
                );

                bw.newLine();
            }
            bw.close();
        } catch(IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving file");
        }
        
    }
    void read() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            while ((line = br.readLine()) !=null) {
                String [] data = line.split(delim);
                if (data.length == 6){
                    model.addRow(data);
                }
            }
            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Eror saving File");
        }
    }
    void clearB() {
        btnclr.addActionListener(e -> clear());
    }
    void clear() {
        txtname.setText("");
        txtemail.setText("");
        txtadd.setText("");
        txtcont.setText("");
        cors.setSelectedIndex(0);
        gr.clearSelection();
    }
    void add() {
        btnadd.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to enroll?",
                "Confirm Register",
                JOptionPane.YES_NO_OPTION
            );

            if(confirm == JOptionPane.YES_OPTION) {
                String name = txtname.getText();
                String email = txtemail.getText();
                String address = txtadd.getText();
                String contact = txtcont.getText();
                String gender = "";
                if (rb1.isSelected()) {
                    gender = rb1.getText();
                } else if (rb2.isSelected()) {
                    gender = rb2.getText();
                }
                String course = (String) cors.getSelectedItem();

                model.insertRow(0, new Object [] {
                    name, email, address, contact, gender, course
                });
            }
            

            save();
            clear();
        });
    }

    void update() {
        btnupd.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row first.");
                return;
            }

            int conf = JOptionPane.showConfirmDialog(null, "Are you sure you want to update row?", "Confirm Update",JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                model.setValueAt(txtname.getText(),row,0);
                model.setValueAt(txtemail.getText(),row,1);
                model.setValueAt(txtadd.getText(),row,2);
                model.setValueAt(txtcont.getText(),row,3);
                String gend = "";
                if (rb1.isSelected()) {
                gend = rb1.getText();
                } else if (rb2.isSelected()) {
                gend = rb2.getText();
                }
                model.setValueAt(gend, row, 4);
                model.setValueAt(cors.getSelectedItem(), row, 5);
            }

            save();
            clear();
        });
        
    }
    void delete() {
        btndel.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null,"Please select a row first.");
                return;   
            }
            int conf = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete data?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION){
                model.removeRow(row);

                save();
                clear();
            }
        });
    }


    void tableSelect() {
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();

                txtname.setText(model.getValueAt(row, 0).toString());
                txtemail.setText(model.getValueAt(row, 1).toString());
                txtadd.setText(model.getValueAt(row, 2).toString());
                txtcont.setText(model.getValueAt(row, 3).toString());
                String gend = model.getValueAt(row, 4).toString();
                if (gend.equals("Male")) {
                    rb1.setSelected(true);

                } else if (gend.equals("Female")) {
                    rb2.setSelected(true);
                }
                cors.setSelectedItem(model.getValueAt(row, 5).toString());
            }
        });
    }

    public static void main (String [] args) {
        new CRUD();
    }
}
