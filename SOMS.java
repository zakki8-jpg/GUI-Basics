import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class SOMS extends JFrame{
    private static final String f = "soms.txt";
    private static final String d = "#";
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane sp;
    private JLabel lblid, lblcn,lblpn,lblq,lblup,lblpay,lblstat,lbltot,lblgrand;
    private JTextField txtid, txtcn,txtpn,txtq,txtup,txttot,txtgrand;
    private JComboBox<String> pm;
    private JRadioButton rb1,rb2;
    private JButton btnadd,btnupd,btndel,btnclr;
    private ButtonGroup gr;



    SOMS() {
        //lbl
        JLabel lblhead = new JLabel("SALES ORDER MANAGEMENT SYSTEM");
        lblhead.setFont(new Font("Arial", Font.BOLD, 15));
        add(lblhead).setBounds(300,5,300,20);
        lblid = new JLabel("Order ID:");
        lblcn = new JLabel("Customer Name:");
        lblpn = new JLabel("Product Name:");
        lblq = new JLabel("Quantity:");
        lblup = new JLabel("Unit Price:");
        lblpay = new JLabel("Payment Method:");
        lblstat = new JLabel("Order Status:");
        lbltot = new JLabel("Total Amount:");
        add(lblid).setBounds(20,30,100,20);
        add(lblcn).setBounds(20,65,100,20);
        add(lblpn).setBounds(20,100,100,20);
        add(lblq).setBounds(20,135,100,20);
        add(lblup).setBounds(20,170,100,20);
        add(lblpay).setBounds(350,30,100,20);
        add(lblstat).setBounds(350,100,100,20);
        add(lbltot).setBounds(540,50,100,20);

        //txt
        txtid = new JTextField();
        txtcn = new JTextField();
        txtpn = new JTextField();
        txtq = new JTextField();
        txtup = new JTextField();
        txttot = new JTextField();
        txttot.setEditable(false);
        add(txtid).setBounds(120,30,150,20);
        add(txtcn).setBounds(120,65,150,20);
        add(txtpn).setBounds(120,100,150,20);
        add(txtq).setBounds(120,135,150,20);
        add(txtup).setBounds(120,170,150,20);
        add(txttot).setBounds(640,50,150,20);

        //cbox
        String[] pay = {"Cash", "Credit Card", "GCash", "Bank Transfer"};
        pm = new JComboBox<>(pay);
        add(pm).setBounds(350,55,150,20);

        //rbs
        rb1 = new JRadioButton("Pending");
        rb2 = new JRadioButton("Completed");
        gr = new ButtonGroup();
        gr.add(rb1);
        gr.add(rb2);
        add(rb1).setBounds(350,120,100,20);
        add(rb2).setBounds(450,120,100,20);

        //table
        String[] cols = {"Order ID", "Customer Name", "Prodcut Name", "Quantity", "Unit Price", "Total Amount", "Payment Method", "Status"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        sp = new JScrollPane(table);
        JTableHeader th = table.getTableHeader();
        th.setBackground(new Color(20,250,30));
        add(sp).setBounds(10,250,810,175);

        //btns
        btnadd = new JButton("Add");
        btnupd = new JButton("Update");
        btndel = new JButton("Delete");
        btnclr = new JButton("Clear");
        add(btnadd).setBounds(50,200,100,30);
        add(btnupd).setBounds(240,200,100,30);
        add(btndel).setBounds(430,200,100,30);
        add(btnclr).setBounds(620,200,100,30);

        //grandtotal
        lblgrand = new JLabel("GRAND TOTAL:");
        lblgrand.setForeground(Color.blue);
        txtgrand = new JTextField();
        txtgrand.setEditable(false);
        add(lblgrand).setBounds(540,430,100,20);
        add(txtgrand).setBounds(640,430,150,20);

        read();
        gt();
        tableS();
        add();
        update();
        delete();
        clearbtn();




        setLayout(null);
        setSize(850,500);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Sales Order Management System");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);




    } 


    boolean valid() {
        String quant = txtq.getText().trim();
        String unitPrice = txtup.getText().trim();
        String id = txtid.getText().trim();
        String cn = txtcn.getText().trim();
        String pn = txtpn.getText().trim();
        String q = txtq.getText().trim();
        String up = txtup.getText().trim();

        if (id.isEmpty() || cn.isEmpty() || pn.isEmpty() || q.isEmpty() || up.isEmpty() || quant.isEmpty() || unitPrice.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are required");
            return false;
        }

        try {
            Integer.parseInt(quant);
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Quantity must be numeric");
            return false;
        }
        try {
            Double.parseDouble(unitPrice);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unit Price must be numeric");
            return false;
        }


        return true;
    }

    void gt() {
        double tot = 0;
        for (int i =0; i < model.getRowCount();i++) {
            tot += Double.parseDouble(model.getValueAt(i, 5).toString());
        }
        txtgrand.setText(String.format("%.2f", tot)); 
    }

    void clear() {
        txtid.setText("");
        txtcn.setText("");
        txtpn.setText("");
        txtq.setText("");
        txtup.setText("");
        txttot.setText("");
        gr.clearSelection();
        pm.setSelectedIndex(0);
    }
    void clearbtn (){
        btnclr.addActionListener(e -> clear());
    }
    void add() {
        btnadd.addActionListener(e -> {
            if (!valid()) return;
            int conf = JOptionPane.showConfirmDialog(null, "Are you sure you want to add record?", "Confirm add", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                String id = txtid.getText();
                String cn = txtcn.getText();
                String pn = txtpn.getText();
                String q = txtq.getText();
                String up = txtup.getText();
                String pay = (String) pm.getSelectedItem();
                Double tot = Integer.parseInt(q) * Double.parseDouble(up);
                String os = "";
                if (rb1.isSelected()) {
                    os = rb1.getText();
                } else if (rb2.isSelected()) {
                    os = rb2.getText();
                }

                model.insertRow(0, new Object [] {
                    id, cn,pn,q,up,String.format("%.2f", tot),pay,os
                });

                save();
                gt();
                clear();
            }
        });
    }
    void update() {
        btnupd.addActionListener(e -> {
            if (!valid()) return;

        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Please select a record from the table to update");
            return;
        }
        int conf = JOptionPane.showConfirmDialog(null, "Are you sure you want to update record?", "Confirm update",JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) {
            double tot = Integer.parseInt(txtq.getText()) * Double.parseDouble(txtup.getText());
            model.setValueAt(txtid.getText(), row, 0);
            model.setValueAt(txtcn.getText(), row, 1);
            model.setValueAt(txtpn.getText(), row, 2);
            model.setValueAt(txtq.getText(), row, 3);
            model.setValueAt(txtup.getText(), row, 4);
            model.setValueAt(tot, row, 5);
            model.setValueAt(pm.getSelectedItem(), row, 6);
            String os = "";
            if (rb1.isSelected()) {
                os = rb1.getText();
            } else if (rb2.isSelected()) {
                os = rb2.getText();
            }
            model.setValueAt(os, row, 7);


            save();
            gt();
            clear();

        }
        });
        
    }
    void delete() {
        btndel.addActionListener(e -> {
            if (!valid()) return;
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select a record from the table to delete");
                return;
            }
            int conf = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to delete record?",
                "Confirm delete",
                JOptionPane.YES_NO_OPTION
            );
            if (conf == JOptionPane.YES_OPTION) {
                model.removeRow(row);

                save();
                gt();
                clear();
            }
        });
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

    void read() {
        try {
            File file = new File(f);
            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            
            while ((line = br.readLine()) !=null) {
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
    void tableS() {
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                txtid.setText(model.getValueAt(row, 0).toString());
                txtcn.setText(model.getValueAt(row, 1).toString());
                txtpn.setText(model.getValueAt(row, 2).toString());
                txtq.setText(model.getValueAt(row, 3).toString());
                txtup.setText(model.getValueAt(row, 4).toString());
                txttot.setText(model.getValueAt(row, 5).toString());
                pm.setSelectedItem(model.getValueAt(row, 6).toString());
                String stat = model.getValueAt(row, 7).toString();
                if (stat.equals("Pending")) {
                    rb1.setSelected(true);
                } else if (stat.equals("Completed")) {
                    rb2.setSelected(true);
                }

            }
        });
    }












    public static void main(String[] args) {
        new SOMS();
    }
}
