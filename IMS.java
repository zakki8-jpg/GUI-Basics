import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class IMS extends JFrame {
    private static final String f = "ims.txt";
    private static final String d = "#";
    private JButton btnadd, btnupd, btndel;
    private JLabel lblid, lblname, lblcat, lblq, lblunit, lblsupp,lblgt;
    private JTextField txtid,txtname,txtq,txtunit,txtsupp,txtgt;
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane sp;
    private JComboBox<String> cat;
    

    IMS() {

        lblid = new JLabel("Product ID:");
        lblname = new JLabel("Product Name:");
        lblcat = new JLabel("Category:");
        lblq = new JLabel("Quantity:");
        lblunit = new JLabel("Unit Price:");
        lblsupp = new JLabel("Supplier:");
        add(lblid).setBounds(30,50,100,20);
        add(lblname).setBounds(30,110,100,20);
        add(lblcat).setBounds(310,30,100,20);
        add(lblq).setBounds(310,70,100,20);
        add(lblunit).setBounds(310, 110,100,20);
        add(lblsupp).setBounds(580,50,60,20);

        txtid = new JTextField();
        txtname = new JTextField();
        txtq = new JTextField();
        txtunit = new JTextField();
        txtsupp = new JTextField();
        add(txtid).setBounds(130,50,150,25);
        add(txtname).setBounds(130,110,150,25);
        add(txtq).setBounds(410,70,150,25);
        add(txtunit).setBounds(410,110,150,25);
        add(txtsupp).setBounds(640,50,130,25);

        //cbox
        String[] category = {"Electronics", "Furniture", "Drugs"};
        cat = new JComboBox<>(category);
        add(cat).setBounds(410,30,140,20);

        //btns
        btnadd = new JButton("Add");
        btnupd = new JButton("Update");
        btndel = new JButton("Delete");
        add(btnadd).setBounds(160,160,100,40);
        add(btnupd).setBounds(350, 160,100,40);
        add(btndel).setBounds(540, 160,100,40);

        String[] cols = {"Product ID", "Product Name", "Category", "Quantity","Unit Price","Total","Supplier"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        sp = new JScrollPane(table);
        add(sp).setBounds(20,210,745,200);
        JTableHeader th = table.getTableHeader();
        th.setBackground(new Color(250,180,0));
        th.setFont(new Font("Segoe UI", Font.BOLD, 13));

        lblgt = new JLabel("Grand Total:");
        lblgt.setFont(new Font("Segoe UI", Font.BOLD, 15));
        txtgt = new JTextField();
        txtgt.setEditable(false);
        add(lblgt).setBounds(380,420,100,25);
        add(txtgt).setBounds(480,420,150,25);



        read();
        grandTotal();
        tableS();
        add();
        update();
        delete();
        //grandTotal();



        setLayout(null);
        setVisible(true);
        setTitle("Inventory Management System");
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(800,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    boolean valid() {
        String id = txtid.getText().trim();
        String name = txtname.getText().trim();
        String q = txtq.getText().trim();
        String price = txtunit.getText().trim();
        String supp = txtsupp.getText().trim();
        if (id.isEmpty() || name.isEmpty() || q.isEmpty() || price.isEmpty() || supp.isEmpty()){
            JOptionPane.showMessageDialog(null, "All fields are required");
            return false;

        }
        try {
            Integer.parseInt(q);
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Quanitity must be numeric");
            return false;
        }
        try {
            Double.parseDouble(price);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unit Price must be numeric");
            return false;
        }
        /*try {
            Integer.parseInt(id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Product ID must be numric");
            return false;
        }
        */
        return true;
    }
    void grandTotal () {
        double grand = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            grand += Double.parseDouble(
                model.getValueAt(i,5).toString()
            );

        }
        txtgt.setText(String.format("%.2f",grand));
    }
    void save() {
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            for (int i = 0; i<model.getRowCount(); i++) {
                bw.write(
                    model.getValueAt(i, 0) +d+
                    model.getValueAt(i, 1) +d+
                    model.getValueAt(i, 2) +d+
                    model.getValueAt(i, 3) +d+
                    model.getValueAt(i, 4) +d+
                    model.getValueAt(i,5) +d+
                    model.getValueAt(i, 6)
                );
                bw.newLine();
            } 
            bw.close();
        }catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    void read() {
        try{
            File file = new File(f);
            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                String [] data = line.split(d);

                if (data.length == 7) {
                    model.addRow(data);
                }

            }
            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    void clear() {
        txtid.setText("");
        txtname.setText("");
        txtq.setText("");
        txtunit.setText("");
        txtsupp.setText("");
        cat.setSelectedIndex(0);
    }

    void add() {
        btnadd.addActionListener(e -> {
            if (!valid()) {
                return;
            }
            int conf = JOptionPane.showConfirmDialog(null, "Please confirm add", "Confirm Add", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                String id = txtid.getText();
                String name = txtname.getText();
                String categ = (String) cat.getSelectedItem();
                String quant = txtq.getText();
                String price = txtunit.getText();
                String supp = txtsupp.getText();
                double tot = Integer.parseInt(quant) * Double.parseDouble(price);
                //String total = String.format("%.2f", tot);

                model.insertRow(0, new Object [] {
                    id,name,categ,quant,price,String.format("%.2f", tot),supp
                });
            }
            
            
            save();
            grandTotal();
            clear();
        });
    }

    void update() {
        btnupd.addActionListener(e -> {
            
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select record to update");
                return;
            }
            int conf = JOptionPane.showConfirmDialog(null, "Are you sure you want to update record?","Confirm Update", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                double tot = Integer.parseInt(txtq.getText())* Double.parseDouble(txtunit.getText());
                model.setValueAt(txtid.getText(), row, 0);
                model.setValueAt(txtname.getText(), row, 1);
                model.setValueAt(cat.getSelectedItem(), row, 2);
                model.setValueAt(txtq.getText(), row, 3);
                model.setValueAt(txtunit.getText(),row,4);
                model.setValueAt(tot, row, 5);
                model.setValueAt(txtsupp.getText(), row, 6);

                save();
                grandTotal();
                clear();
            }
        });
    }
    void delete() {
        btndel.addActionListener(e -> {
            if (!valid()) return;

            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select a record to delete");
                return;
            }
            int conf = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete record?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                model.removeRow(row);
                save();
                grandTotal();
                clear();
            }
        });
    }









    void tableS() {
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent e) {
                int row = table.getSelectedRow();
                txtid.setText(model.getValueAt(row, 0).toString());
                txtname.setText(model.getValueAt(row, 1).toString());
                cat.setSelectedItem(model.getValueAt(row, 2).toString());
                txtq.setText(model.getValueAt(row, 3).toString());
                txtunit.setText(model.getValueAt(row, 4).toString());
                txtsupp.setText(model.getValueAt(row, 6).toString());
            }
        });
    }
    public static void main (String[] args) {
        new IMS();
    }
    
}
