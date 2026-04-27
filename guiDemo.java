//Balucan
import java.io.*;
import javax.swing.table.*;
import java.awt.event.*;
import javax.swing.*;

public class guiDemo extends JFrame {
    static DefaultTableModel model;
    static JTable table;

    guiDemo() {
        
        JLabel lblCustT = new JLabel("Customer Type");
        add(lblCustT).setBounds(45,50,150,30);

        JTextField txtCustT = new JTextField();
        add(txtCustT).setBounds(175, 50, 240, 30);

        JLabel lblConsump = new JLabel("Consumption");
        add(lblConsump).setBounds(45,100,150,30);
        

        JTextField txtConsump = new JTextField();
        add(txtConsump).setBounds(175, 100, 240, 30);
        //txtConsump.setEnabled(false);

        JLabel lblTotlaBill = new JLabel("Total Bill");
        add(lblTotlaBill).setBounds(45, 150, 150, 30);

        JTextField txtTotalBill = new JTextField();
        add(txtTotalBill).setBounds(175, 150, 240,30);
        txtTotalBill.setEditable(false);

        JButton btnAdd = new JButton("Add");
        add(btnAdd).setBounds(45,190, 110, 30);

        JButton btnDel = new JButton("Delete");
        add(btnDel).setBounds(175,190, 110, 30);


        JButton btnUpd = new JButton("Update");
        add(btnUpd).setBounds(305,190, 110, 30);

        JButton btnClear = new JButton("Clear");
        add(btnClear).setBounds(225, 230, 70, 30);

        
        
        
        btnAdd.addActionListener(e->{
            try {
                FileWriter fw = new FileWriter("Vehicle.txt", true );

                String ct = txtCustT.getText();
                String consumption = txtConsump.getText();
                //Double TotalBill = Double.parseDouble(txtTotalBill.getText());

                Double bill;
                bill = Double.parseDouble(consumption)*50;
                txtTotalBill.setText(String.valueOf(bill));
                fw.write(ct+"#" + consumption+ "#" + bill+ "\n");
                fw.close();
                



            } catch (IOException x) {
                System.out.println("System Error!" + x);
            }
                
            
             
            

        });
        btnDel.addActionListener(e->{
            JOptionPane.showMessageDialog(null, "Bye World!");
        });

        btnUpd.addActionListener(e->{
            JOptionPane.showMessageDialog(null,"Customer Updated");
        });
        
        String[] col = {"Customer Type","Consumption", "Total Bill"};
        model = new DefaultTableModel(col,0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane).setBounds(450,50,300,300);
        read();
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    txtCustT.setText(model.getValueAt(row,0).toString());
                    txtConsump.setText(model.getValueAt(row,1).toString());
                    txtTotalBill.setText(model.getValueAt(row,2).toString());
                }
            }
        });







        setSize(800, 500);
        setLocationRelativeTo(null);
        setTitle("BALUCAN_Vehicle_Rental_System");
        setResizable(true);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void read() {
        model.setRowCount(0);
        try (BufferedReader br = new BufferedReader(new FileReader("Vehicle.txt"))) {
            String line;
            while ((line = br.readLine()) !=null) {
                String[] row = line.split("#");
                model.addRow(row);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }



    public static void main(String [] args) {
        new guiDemo();


    }

}
