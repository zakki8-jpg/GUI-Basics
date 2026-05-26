import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
public class HMS extends JFrame{
    private static final String f = "hms.txt";
    private static final String d = "#";
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane sp;
    private JRadioButton rb1,rb2;
    private JButton btnadd,btnupd,btndel,btnclr;
    private ButtonGroup gr;
    private JComboBox<String> rt,rs;
    private JLabel lblid, lblname , lbldays,lblrate,lblroom,lblstat,lblpay ,lbltotal;
    private JTextField txtid,txtname,txtdays,txtrate,txttotal;


    HMS() {
        //lbl
        lblid = new JLabel("Reservation ID:");
        lblname = new JLabel("Guest Name:");
        lbldays = new JLabel("Number of days:");
        lblrate = new JLabel("Rate per day:");
        lblroom = new JLabel("Room Type:");
        lblstat = new JLabel("Reservation Status:");
        lblpay = new JLabel("Payment Method:");
        add(lblid).setBounds(30,30,100,20);
        add(lblname).setBounds(30, 70,100,20);
        add(lbldays).setBounds(30, 110,100,20);
        add(lblrate).setBounds(30,150,100,20);
        add(lblroom).setBounds(350,30,100,20);
        add(lblstat).setBounds(350,100,120,20);
        add(lblpay).setBounds(580,30,100,20);


        //txt
        txtid = new JTextField();
        txtname = new JTextField();
        txtdays = new JTextField();
        txtrate = new JTextField();
        add(txtid).setBounds(130,30,170,25);
        add(txtname).setBounds(130,70,170,25);
        add(txtdays).setBounds(130,110,170,25);
        add(txtrate).setBounds(130,150,170,25);

        //cbox
        String[] roomType = {"Standard", "Deluxe", "Suite"};
        String[] reserve = {"Reserved", "Checked-In", "Checked-out"};
        rt = new JComboBox<>(roomType);
        rs = new JComboBox<>(reserve);
        add(rt).setBounds(350,60,150,20);
        add(rs).setBounds(350,130,150,20);

        //rbs
        rb1 = new JRadioButton("Cash");
        rb2 = new JRadioButton("Card");
        gr = new ButtonGroup();
        gr.add(rb1);
        gr.add(rb2);
        add(rb1).setBounds(580,60,60,20);
        add(rb2).setBounds(580,100,60,20);

        //btns
        btnadd = new JButton("Add");
        btnupd = new JButton("Update");
        btndel = new JButton("Delete");
        btnclr = new JButton("Clear");
        add(btnadd).setBounds(100,190,100,30);
        add(btnupd).setBounds(280,190,100,30);
        add(btndel).setBounds(460,190,100,30);
        add(btnclr).setBounds(640,190,100,30);
        
        //table
        String[] cols = {"Reservation ID", "Guest Name","Room Type", "Payment", "Days", "Rate Per Day", "Total Bill", "Status"};
        model = new DefaultTableModel(cols,0);
        table = new JTable(model);
        sp = new JScrollPane(table);
        JTableHeader th = table.getTableHeader();
        th.setForeground(Color.WHITE);
        th.setBackground(new Color(200,100,50));
        add(sp).setBounds(10,240,765,275);

        lbltotal = new JLabel("Total Revenue:");
        lbltotal.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
        txttotal = new JTextField();
        txttotal.setEditable(false);
        add(lbltotal).setBounds(480,530,130,20);
        add(txttotal).setBounds(620,530,155,20);


        read();
        Total();
        add();
        tableS();
        update();
        delete();
        clearbtn();

        setLayout(null);
        setSize(800,600);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Hotel Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    void Total() {
        double tot = 0;
        for (int i =0; i < model.getRowCount();i++) {
            tot += Double.parseDouble(model.getValueAt(i, 6).toString());
        }
        txttotal.setText(String.format("%.2f", tot)); 
    }
    void add() {
        btnadd.addActionListener(e -> {
            if (!valid()) return;

            int conf = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to add record?",
                "Confirm Add",
                JOptionPane.YES_NO_OPTION
            );
            if (conf == JOptionPane.YES_OPTION) {
                String id = txtid.getText();
                String name = txtname.getText();
                String days = txtdays.getText();
                String rate = txtrate.getText();
                double total = Integer.parseInt(days) * Double.parseDouble(rate);
                String room = (String) rt.getSelectedItem();
                String res = (String) rs.getSelectedItem();
                String pay = "";

                if (rb1.isSelected()) {
                    pay = rb1.getText();
                } else if (rb2.isSelected()) {
                    pay = rb2.getText();
                }

                model.insertRow(0, new Object[] {
                    id, name, room, pay, days, rate, String.format("%.2f", total), res
                });


                save();
                Total();
                clear();
            }
        });
        
    }

    void read() {
        try {
            File file = new File(f);
            if (!file.exists()) return;
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(d);

                if (data.length == 8) {
                    model.addRow(data);
                }
            }
            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    void clear() {
        txtid.setText("");
        txtname.setText("");
        txtdays.setText("");
        txtrate.setText("");
        gr.clearSelection();
        rt.setSelectedIndex(0);
        rs.setSelectedIndex(0);

    }
    void update() {
        btnupd.addActionListener(e -> {
            if (!valid()) return;

            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select record to update");
                return;
            }
            int conf = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to update record?",
                "Confirm update"
                , JOptionPane.YES_NO_OPTION
            );
            if (conf == JOptionPane.YES_OPTION) {
                double tot = Integer.parseInt(txtdays.getText()) * Double.parseDouble(txtrate.getText());
                model.setValueAt(txtid.getText(), row, 0);
                model.setValueAt(txtname.getText(), row, 1);
                model.setValueAt(rt.getSelectedItem(), row, 2);
                String pay = "";
                if (rb1.isSelected()) {
                    pay = rb1.getText();
                } else if (rb2.isSelected()) {
                    pay = rb2.getText();
                }
                model.setValueAt(pay, row, 3);
                model.setValueAt(txtdays.getText(), row, 4);
                model.setValueAt(txtrate.getText(), row, 5);
                model.setValueAt(tot, row, 6);
                model.setValueAt(rs.getSelectedItem(), row, 7);

                save();
                Total();
                clear();
            }
        });

    }
    void delete () {
        btndel.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row from the table");
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
                Total();
                clear();
            }
        });
    }
    void clearbtn() {
        btnclr.addActionListener(e -> clear());
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
                    model.getValueAt(i, 6) +d+
                    model.getValueAt(i, 7) 

                    
                );
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    boolean valid() {
        String id = txtid.getText().trim();
        String name = txtname.getText().trim();
        String days = txtdays.getText().trim();
        String rate = txtrate.getText().trim();

        if (id.isEmpty() || name.isEmpty()||days.isEmpty()||rate.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are required");
            return false;
        }
        try {
            Integer.parseInt(days);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Number of days must be numeric");
            return false;
        }
        try {
            Double.parseDouble(rate);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Rate per day must be numeric");
            return false;
        }

        return true;
    }
    void tableS() {
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent e) {
                int row = table.getSelectedRow();
                txtid.setText(model.getValueAt(row, 0).toString());
                txtname.setText(model.getValueAt(row, 1).toString());
                rt.setSelectedItem(model.getValueAt(row, 2).toString());
                rs.setSelectedItem(model.getValueAt(row, 7).toString());
                txtdays.setText(model.getValueAt(row, 4).toString());
                txtrate.setText(model.getValueAt(row, 5).toString());
                String stat = model.getValueAt(row, 3).toString();
                if (stat.equals("Cash")) {
                    rb1.setSelected(true);
                } else if (stat.equals("Card")) {
                    rb2.setSelected(true);
                }

            }
        });
    }

    public static void main (String[] args) {
        new HMS();
    }
}
