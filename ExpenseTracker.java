//Balucan, John Isaac
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
public class ExpenseTracker { 
    static DefaultTableModel model;
    static  JTable table;

    JFrame frame;
    JLabel lblT, lblRN, lblSN, lblTC, lblTx, lblFA;
    JTextField txtRB, txtSB, txtCB, txtTB, txtFB;
    JButton btnSB, btnCB, btnDel;


    public ExpenseTracker() {
        frame = new JFrame("BALUCAN_Expense_Tracker");
        frame.setSize(900, 500);
        frame.setLayout(null); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(240, 240, 240));

        //title
        lblT = new JLabel("Expense Tracker");
        lblT.setFont(new Font("Times new Roman", Font.BOLD, 25));
        lblT.setBounds(165, 20, 400, 30);
        frame.add(lblT);

        //reciept
        lblRN = new JLabel("Receipt Number:");
        lblRN.setBounds(70, 80, 150, 25);
        txtRB = new JTextField();
        txtRB.setBounds(230, 80, 200, 25);

        //store
        lblSN = new JLabel("Store Name:");
        lblSN.setBounds(70, 120, 150, 25);
        txtSB = new JTextField();
        txtSB.setBounds(230, 120, 200, 25);

        //cost
        lblTC = new JLabel("Total Cost:");
        lblTC.setBounds(70, 160, 150, 25);
        txtCB = new JTextField();
        txtCB.setBounds(230, 160, 200, 25);

        //tax
        lblTx = new JLabel("Tax (12%):");
        lblTx.setBounds(70, 230, 150, 25);
        txtTB = new JTextField();
        txtTB.setBounds(230, 230, 200, 25);
        txtTB.setEditable(false);
        txtTB.setBackground(new Color(235, 245, 255)); 

        //final amount
        lblFA = new JLabel("Final Amount:");
        lblFA.setBounds(70, 270, 150, 25);
        txtFB = new JTextField();
        txtFB.setBounds(230, 270, 200, 25);
        txtFB.setEditable(false);
        txtFB.setBackground(new Color(235, 245, 255));

        //record
        btnSB = new JButton("Record");
        btnSB.setBounds(130, 330, 100, 35);

        //clear
        btnCB = new JButton("Clear");
        btnCB.setBounds(270, 330, 100, 35);
        //del
        btnDel = new JButton("Delete");
        btnDel.setBounds(195, 380, 100, 35);

        frame.add(lblRN); frame.add(txtRB);
        frame.add(lblSN); frame.add(txtSB);
        frame.add(lblTC); frame.add(txtCB);
        frame.add(lblTx); frame.add(txtTB);
        frame.add(lblFA); frame.add(txtFB);
        frame.add(btnSB); frame.add(btnCB);
        frame.add(btnDel);

       


        //record actionListener
        btnSB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String receipt = txtRB.getText();
                    String store = txtSB.getText();
                    double cost = Double.parseDouble(txtCB.getText());
                    double tax = cost * 0.12;
                    double total = cost + tax;
                    txtTB.setText(String.format("%.2f", tax));

                    txtFB.setText(String.format("%.2f", total));
                    //fileWriting
                    FileWriter myFile = new FileWriter("data.txt", true);
                    myFile.write(receipt + "#" + store + "#" + cost + "#" + tax + "#" + total + "\n");
                    myFile.close();
                    JOptionPane.showMessageDialog(frame, "Record Saved!");
                } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid cost.");
                }
            }
        });
        btnCB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtRB.setText("");
                txtSB.setText("");
                txtCB.setText("");
                txtTB.setText("");
                txtFB.setText("");
            }
        });

        btnDel.addActionListener(e->{
            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(frame ,"Please select a row to delete.");
                return;
            }

            model.removeRow(row);

            try (FileWriter fw  = new FileWriter("data.txt")) {
                for (int i = 0; i < model.getRowCount(); i++) {
                    String rec = model.getValueAt(i, 0).toString();
                    String sto = model.getValueAt(i, 1).toString();
                    String cos = model.getValueAt(i, 2).toString();
                    String tax = model.getValueAt(i, 3).toString();
                    String tot = model.getValueAt(i, 4).toString();

                    fw.write(rec + "#" + sto + "#" + cos + "#" + tax + "#" + tot + "\n");

                }
                JOptionPane.showMessageDialog(frame,"Record Deleted!" );

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error deleting record" +ex);
            }
        });
        frame.setVisible(true);
        
        String [] col = {"Receipt Number", "Store Name", "Total Cost"," Tax(12%)", "Final Amount"};
        model = new DefaultTableModel(col, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        frame.add(scroll).setBounds(500,50,300,300);
        read();

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e ) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    txtRB.setText(model.getValueAt(row, 0).toString());
                    txtSB.setText(model.getValueAt(row, 1).toString());
                    txtCB.setText(model.getValueAt(row, 2).toString());
                    txtTB.setText(model.getValueAt(row, 3).toString());
                    txtFB.setText(model.getValueAt(row, 4).toString());
                }
            }
        });
        


    }


    public static void read() {
        model.setRowCount(0);
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            while ((line = br.readLine())!= null) {
                String [] row = line.split("#");
                model.addRow(row);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public static void main(String[] args) {
        new ExpenseTracker();
    }
}