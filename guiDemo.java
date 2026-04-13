import javax.swing.*;

public class guiDemo extends JFrame {

    guiDemo() {

        JLabel lblCustT = new JLabel("Customer Type");
        add(lblCustT).setBounds(45,50,150,30);

        JTextField txtCustT = new JTextField();
        add(txtCustT).setBounds(175, 50, 240, 30);

        JLabel lblConsump = new JLabel("Consumption");
        add(lblConsump).setBounds(45,100,150,30);

        JTextField txtConsump = new JTextField();
        add(txtConsump).setBounds(175, 100, 240, 30);

        JLabel lblTotlaBill = new JLabel("Total Bill");
        add(lblTotlaBill).setBounds(45, 150, 150, 30);

        JTextField txtTotalBill = new JTextField();
        add(txtTotalBill).setBounds(175, 150, 240,30);

        JButton btnAdd = new JButton("Add");
        add(btnAdd).setBounds(45,200, 110, 30);

        JButton btnDel = new JButton("Delete");
        add(btnDel).setBounds(175,200, 110, 30);

        JButton btnUpd = new JButton("Update");
        add(btnUpd).setBounds(305,200, 110, 30);



        setSize(500, 300);
        setLocationRelativeTo(null);
        setTitle("BALUCAN");
        setResizable(true);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }



    public static void main(String [] args) {
        new guiDemo();


    }

}
