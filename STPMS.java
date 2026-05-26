import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
public class STPMS  extends JFrame{
    private static final String f = "stpms.txt";
    private static final String d = "#";
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane sp;
    private JButton btnadd,btnupd,btndel,btnclr;
    private JRadioButton rb1,rb2;
    private ButtonGroup gr;
    private JLabel lbltitle,lblid, lblname, lblcourse, lblunits, lblrate,lblpay, lbltuition, lbltotal;
    private JTextField txtid, txtname,txtunits,txtrate,txttuition,txttotal;
    private JComboBox<String> course;

    STPMS() {
        //lbl
        lbltitle = new JLabel("STUDENT PAYMENT MANAGEMENT SYSTEM");
        lbltitle.setForeground(Color.red);
        lbltitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lbltitle).setBounds(210,20,400,20);
        lblid = new JLabel("Student ID:");
        lblname = new JLabel("Student Name:");
        lblcourse = new JLabel("Course:");
        lblunits = new JLabel("Units Enrolled:");
        lblrate = new JLabel("Rate Per Unit:");
        lblpay = new JLabel("Payment Method:");
        lbltuition = new JLabel("Tuition Fee:");
        add(lblid).setBounds(30,60,100,20);
        add(lblname).setBounds(30,100,100,20);
        add(lblcourse).setBounds(30,140,100,20);
        add(lblunits).setBounds(30,180,100,20);
        add(lblrate).setBounds(30,220,100,20);
        add(lblpay).setBounds(380,60,100,20);
        add(lbltuition).setBounds(380,150,100,20);

        //txt
        txtid = new JTextField();
        txtname = new JTextField(); 
        txtunits = new JTextField();
        txtrate = new JTextField();
        txttuition = new JTextField();
        txttuition.setEditable(false);
        add(txtid).setBounds(130,60,200,20);
        add(txtname).setBounds(130,100,200,20);
        add(txtunits).setBounds(130,180,200,20);
        add(txtrate).setBounds(130,220,200,20);
        add(txttuition).setBounds(390,180,200,20);

        //cbox
        String [] cour = {"BSIT","BSCS", "BSA", "BSBA"};
        course = new JComboBox<>(cour);
        add(course).setBounds(130,140,200,20);

        //rbs
        rb1 = new JRadioButton("Cash");
        rb2 = new JRadioButton("Installment");
        gr = new ButtonGroup();
        gr.add(rb1);
        gr.add(rb2);
        add(rb1).setBounds(380,80,100,20);
        add(rb2).setBounds(380,110,100,20);

        //btns
        btnadd = new JButton("Add");
        btnupd = new JButton("Update");
        btndel = new JButton("Delete");
        btnclr = new JButton("Clear");
        add(btnadd).setBounds(350,230,100,30);
        add(btnupd).setBounds(460,230,100,30);
        add(btndel).setBounds(570,230,100,30);
        add(btnclr).setBounds(680,230,100,30);

        //table
        String[] cols = {"Sudent ID", "Student Name", "Course", "Payment Method", "Units", "Rate Per Unit", "Tuition-Fee"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        sp = new JScrollPane(table);
        JTableHeader th = table.getTableHeader();
        th.setBackground(Color.CYAN);
        th.setForeground(Color.RED);
        add(sp).setBounds(10,280,765,240);

        lbltotal = new JLabel("TOTAL TUITION COLLECTION:");
        txttotal = new JTextField();
        txttotal.setEditable(false);
        add(lbltotal).setBounds(450,530,200,20);
        add(txttotal).setBounds(620,530,155,20);




        read();
        TOTAL();
        tableS();
        add();
        update();
        delete();
        clearbtn();





        setLayout(null);
        setSize(800,600);
        setVisible(true);
        setLocationRelativeTo(null);
        setTitle("Student Tuition Payment System");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    boolean valid() {
        String id = txtid.getText().trim();
        String name = txtname.getText();
        String units = txtunits.getText();
        String rate = txtrate.getText();

        if (id.isEmpty()||name.isEmpty()||units.isEmpty()||rate.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All Fields are Required");
            return false;
        }
        try {
            Integer.parseInt(units);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Units Enrolled must be numeric");
            return false;
        }
        try {
            Double.parseDouble(rate);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Rate per unit must be numeric");
            return false;
        }


        return true;
    }
    void TOTAL() {
        double tot = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            tot += Double.parseDouble(model.getValueAt(i, 6).toString());
        }
        txttotal.setText(String.format("%.2f", tot));
    }
    void clear() {
        txtid.setText("");
        txtname.setText("");
        txtunits.setText("");
        txtrate.setText("");
        course.setSelectedIndex(0);
        gr.clearSelection();
        txttuition.setText("");
    }
    void clearbtn() {
        btnclr.addActionListener(e -> clear());
    }
    void delete() {
        btndel.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select row from the table");
                return;
            }
            int conf = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to delete record?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
            );
            if (conf == JOptionPane.YES_OPTION) {
                model.removeRow(row);

                save();
                TOTAL();
                clear();
            }
        });
    }
    void update() {
        btnupd.addActionListener(e -> {
            if(!valid()) return;
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select row from the table");
                return;
            }
            int conf = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to update record?",
                "Confirm Update",
                JOptionPane.YES_NO_OPTION
            );
            if (conf == JOptionPane.YES_OPTION) {
                double tot = Integer.parseInt(txtunits.getText()) * Double.parseDouble(txtrate.getText());
                String pay = "";
                if (rb1.isSelected()) {
                    pay = rb1.getText();
                }else if (rb2.isSelected()) {
                    pay = rb2.getText();
                }
                model.setValueAt(txtid.getText(), row, 0);
                model.setValueAt(txtname.getText(), row, 1);
                model.setValueAt(course.getSelectedItem(), row, 2);
                model.setValueAt(pay, row, 3);
                model.setValueAt(txtunits.getText(), row, 4);
                model.setValueAt(txtrate.getText(), row, 5);
                model.setValueAt(String.format("%.2f", tot), row, 6);

                save();
                TOTAL();
                clear();
            }
        });
    }
    void add() {
        btnadd.addActionListener(e -> {
            if(!valid()) return;
            int conf = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to add record?",
                "Confrim Add"
                , JOptionPane.YES_NO_OPTION
            );
            if (conf == JOptionPane.YES_OPTION) {
                String id = txtid.getText();
                String name = txtname.getText();
                String cour = (String) course.getSelectedItem();
                String units = txtunits.getText();
                String rate = txtrate.getText();
                String pm = "";
                if (rb1.isSelected()) {
                    pm = rb1.getText();
                } else if (rb2.isSelected()) {
                    pm = rb2.getText();
                }

                double tot = Integer.parseInt(units) * Double.parseDouble(rate);

                model.insertRow(0, new Object[] {
                    id,name,cour,pm,units,rate,String.format("%.2f", tot)
                });

                save();
                TOTAL();
                clear();

            }   
            
        });
        
    }

    void read() {
        try {
            File file = new File(f);
            if (!file.exists()) return;

            BufferedReader br =new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) !=null) {
                String [] data = line.split(d);

                if (data.length == 7) {
                    model.addRow(data);
                }
            }
            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    void save() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            for (int i = 0; i < model.getRowCount(); i++) {
                bw.write(
                    model.getValueAt(i, 0) +d+
                    model.getValueAt(i, 1) +d+
                    model.getValueAt(i, 2) +d+
                    model.getValueAt(i, 3) +d+
                    model.getValueAt(i, 4) +d+
                    model.getValueAt(i, 5) +d+
                    model.getValueAt(i, 6)
                );
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }



    void tableS() {
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                txtid.setText(model.getValueAt(row, 0).toString());
                txtname.setText(model.getValueAt(row, 1).toString());
                course.setSelectedItem(model.getValueAt(row, 2).toString());
                String payment = model.getValueAt(row, 3).toString();
                if (payment.equals("Cash")) {
                    rb1.setSelected(true);
                } else if (payment.equals("Installment")) {
                    rb2.setSelected(true);
                }
                txtunits.setText(model.getValueAt(row, 4).toString());
                txtrate.setText(model.getValueAt(row, 5).toString());
                txttuition.setText(model.getValueAt(row, 6).toString());
            }
        });
    }


    public static void main (String[] args) {
        new STPMS();
    }
}
