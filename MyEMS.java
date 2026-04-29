//Balucan
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.io.*;

public class MyEMS extends JFrame {
    static DefaultTableModel model;
    static JTable table;

    MyEMS() {
        //title
        JLabel lbltitle = new JLabel("EMS Inc.");
        lbltitle.setFont(new Font("Times new Roman", Font.BOLD, 12));
        add(lbltitle).setBounds(30,5,100,100);

        //Employee ID
        JLabel lblEmpID = new JLabel("Employee ID");
        add(lblEmpID).setBounds(30,30,100,100);
        JTextField txtEmpID = new JTextField();
        add(txtEmpID).setBounds(30,90,180,20);

        //Age
        JLabel lblAge = new JLabel("Age");
        add(lblAge).setBounds(220,30,100,100);
        JTextField txtAge = new JTextField();
        add(txtAge).setBounds(220,90,180,20);

        //FullName
        JLabel lblFN = new JLabel("Full Name");
        add(lblFN).setBounds(30,75,100,100);
        JTextField txtFN = new JTextField();
        add(txtFN).setBounds(30,135,180,20);

        //Civil Status Dropdown
        JLabel lblCS = new JLabel("Civil Status");
        add(lblCS).setBounds(220,75,100,100);
        String[] civilStatus = {"Single", "Married","Widowed", "Separated", "Divorced"};
        JComboBox<String> cbCS = new JComboBox<>(civilStatus);
        add(cbCS).setBounds(220,135,180,20);
        
        //Date of Birth
        JLabel lblDoB = new JLabel("Date of Birth(DD/MM/YYYY)");
        add(lblDoB).setBounds(30,120,150,100);
        JTextField txtDoB = new JTextField();
        add(txtDoB).setBounds(30,180,180,20);

        //GEnder
        JLabel lblGen = new JLabel("Gender");
        add(lblGen).setBounds(415,30,100,100);
        //Radio Button
        JRadioButton rbtnM = new JRadioButton("Male");
        JRadioButton rbtnF = new JRadioButton("Female");
        ButtonGroup gr = new ButtonGroup();
        gr.add(rbtnM);
        gr.add(rbtnF);
        add(rbtnM).setBounds(415,90,65,15);
        add(rbtnF).setBounds(490,90,70,15);

        //Contact#
        JLabel lblCN = new JLabel("Contact Number");
        add(lblCN).setBounds(415,75,100,100);
        JTextField txtCN = new JTextField();
        add(txtCN).setBounds(415,135,170,20);

        //Department
        JLabel lblDept = new JLabel("Department");
        add(lblDept).setBounds(595,75,100,100);
        JTextField txtDept = new JTextField();
        add(txtDept).setBounds(595,135,170,20);


        //Nationality
        JLabel lblNat = new JLabel("Nationality");
        add(lblNat).setBounds(220,120,100,100);
        JTextField txtNat = new JTextField();
        add(txtNat).setBounds(220,180,180,20);

        //Email
        JLabel lblEmail = new JLabel("Email");
        add(lblEmail).setBounds(415,120,100,100);
        JTextField txtEmail = new JTextField();
        add(txtEmail).setBounds(415,180,170,20);

        //Job Title
        JLabel lblJT = new JLabel("Job Title / Position");
        add(lblJT).setBounds(595,120,150,100);
        JTextField txtJT = new JTextField();
        add(txtJT).setBounds(595,180,170,20);

        //btnAdd
        JButton btnAdd = new JButton("Add Employee");
        add(btnAdd).setBounds(620, 215, 125, 25);

        
            






        //Table
        model = new DefaultTableModel();
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        model.addColumn("Employee ID");
        model.addColumn("Full Name");
        model.addColumn("Age");
        model.addColumn("Birth");
        model.addColumn("Civil Status");
        model.addColumn("Nationality");
        model.addColumn("Contact ");
        model.addColumn("Email");
        model.addColumn("Department");
        model.addColumn("Job Title / Position");
        add(scroll).setBounds(30, 250, 735, 200);
        
        //headerColor
        JTableHeader th = table.getTableHeader();
        th.setBackground(new Color(135,206,249));
        th.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
        loadData();

        //Action Listener
        btnAdd.addActionListener(e ->{
            try {
                String empID = txtEmpID.getText();
                String fullName = txtFN.getText();
                String age = txtAge.getText();
                String dob = txtDoB.getText();
                String civilStatus2 = (String) cbCS.getSelectedItem();
                String nationality = txtNat.getText();
                String contact = txtCN.getText();
                String email = txtEmail.getText();
                String department = txtDept.getText();
                String jobTitle = txtJT.getText();

                model.addRow(new Object[]{empID, fullName, age, dob, civilStatus2, nationality, contact, email, department, jobTitle});

                BufferedWriter bw = new BufferedWriter(new FileWriter("employees.txt", true));

                bw.write(empID + "#" + fullName + "#" + age + "#" + dob + "#" +
                        civilStatus2 + "#" + nationality + "#" + contact + "#" +
                        email + "#" + department + "#" + jobTitle);
                bw.newLine();
                bw.close();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "System Error: " + ex.getMessage());
            }
        });


        

        //Frame
        setTitle("BALUCAN Employee Management System");
        setResizable(true);
        setLayout(null);
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    void loadData() {
        try {
            File file = new File("employees.txt");

            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("#");

                if (data.length == 10) { // safety check
                    model.addRow(data);
                }
            }

            br.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading data");
        }
}
    public static void main(String[] args) {
        new MyEMS();
    }

}
