import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;


public class praktisTa extends JFrame{
    static JTextField txtName, txtNumber, txtLoads, txtDate;
    static JLabel lblTitle, lblName, lblNumber,lblLoads, lblDate;
    static JButton btnadd, btnclear, btnupd, btndel;
    static JComboBox<String> cbStat;
    static DefaultTableModel model;
    static JTable table;
    static JRadioButton rbtnM, rbtnF;
    static ButtonGroup gr;
   
    public static void main(String [] args) {
        new praktisTa();
    }


    praktisTa() {
        //txt and lbl
        txtName = new JTextField();
        add(txtName).setBounds(20,300,200,25);
        txtNumber = new JTextField();
        add(txtNumber).setBounds(280,300,200,25);
        txtLoads = new JTextField();
        add(txtLoads).setBounds(20,390,200,25);
        txtDate = new JTextField();
        add(txtDate).setBounds(20,480,200,25);
        //JTextField actStat = new JTextField();
        //add(actStat).setBounds(280,390,200,25);
        String[] stat = {"N/A","Active", "Inactive"};
        cbStat = new JComboBox<>(stat);
        add(cbStat).setBounds(280,390,200,25);
        

        lblTitle = new JLabel("LaBubbles Inc.");
        lblTitle.setFont(new Font("Times new Roman", Font.BOLD, 15));
        add(lblTitle).setBounds(40,55,200,25);
        lblName = new JLabel("Name");
        add(lblName).setBounds(20,280,50,25);
        lblNumber = new JLabel("Cellphone Number");
        add(lblNumber).setBounds(280,280,250,25);
        lblLoads = new JLabel("Number of Loads");
        add(lblLoads).setBounds(20,370,200,25);
        JLabel lblactStat = new JLabel("Activity Status");
        add(lblactStat).setBounds(280,370,200,25);
        JLabel gender = new JLabel("Gender");
        add(gender).setBounds(530,265,50,50);
        lblDate = new JLabel("Date (DD/MM/YYYY)");
        add(lblDate).setBounds(20,460,200,25);
        

        //Btns
        btnadd = new JButton("Add");
        add(btnadd).setBounds(30, 620,85,40);

        btnclear = new JButton("Clear");
        add(btnclear).setBounds(140,620,85,40);

        btnupd = new JButton("Update");
        add(btnupd).setBounds(250,620, 85,40);

        btndel = new JButton("Delete");
        add(btndel).setBounds(360,620,85,40);

        rbtnM = new JRadioButton("Male");
        rbtnF = new JRadioButton("Female");

        gr = new ButtonGroup();
        gr.add(rbtnM);
        gr.add(rbtnF);

        add(rbtnM).setBounds(530,300, 70,15);
        add(rbtnF).setBounds(600,300,70,15);
        



        //table
        model = new DefaultTableModel();
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);

        model.addColumn("Name");
        model.addColumn("Cellphone Number");
        model.addColumn("Number of loads");
        model.addColumn("Activity Status");
        model.addColumn("Gender");
        model.addColumn("Date");


        add(sp).setBounds(20,80,700,200);
        JTableHeader th = table.getTableHeader();
        th.setBackground(new Color(135,200,210));
        th.setFont(new Font(Font.DIALOG, Font.BOLD,12));

        read();
        addBtn();
        clrBtn();
        updateBtn();
        delete();
        tableSelect();
        



        //frme
        setLayout(null);
        setTitle("LaBubbles Laundry Management System");
        setResizable(false);
        setSize(750,800);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        

    }
    
    void clrBtn() {
        btnclear.addActionListener(e -> ClearMethod());
    }

    void ClearMethod() {
        txtName.setText("");
        txtNumber.setText("");
        txtLoads.setText("");
        cbStat.setSelectedIndex(0);
        gr.clearSelection();
        txtDate.setText("");

        
    }

    void addBtn() {
        btnadd.addActionListener(e -> {
            String name = txtName.getText();
            String cpN = txtNumber.getText();
            String loads = txtLoads.getText();
            String stat = (String) cbStat.getSelectedItem();
            String date = txtDate.getText();
            String gender = "";
            if (rbtnM.isSelected()) {
                gender = rbtnM.getText();
            } else if (rbtnF.isSelected()) {
                gender = rbtnF.getText();
            }

            model.insertRow(0, new Object[]{
            name,cpN,loads,stat,gender,date});

            saveFile();
            ClearMethod();
            

            
        });

    }
    void updateBtn() {
        btnupd.addActionListener(e -> {
        
            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row first");
                return;
            }
            model.setValueAt(txtName.getText(), row, 0);
            model.setValueAt(txtNumber.getText(),row, 1);
            model.setValueAt(txtLoads.getText(), row, 2);
            model.setValueAt(cbStat.getSelectedItem(), row, 3);
            String gender = "";

            if (rbtnM.isSelected()) {
                gender = rbtnM.getText();
            } else if (rbtnF.isSelected()) {
                gender = rbtnF.getText();
            }
            model.setValueAt(gender, row, 4);
            model.setValueAt(txtDate.getText(), row, 5);

            saveFile();
            ClearMethod();

        
        });
        
    }

    void delete() {
        btndel.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row == -1) {
            JOptionPane.showMessageDialog(null, "Please select a row first");
            return;
            }

            model.removeRow(row);
            saveFile();
            ClearMethod();
        });
        
        
    }

    void saveFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("LaBubbles.txt"));

            for (int i = 0; i < model.getRowCount(); i++) {
                bw.write(
                    model.getValueAt(i, 0) + "#" +
                    model.getValueAt(i, 1)+ "#" +
                    model.getValueAt(i, 2)+ "#" +
                    model.getValueAt(i, 3)+ "#" +
                    model.getValueAt(i, 4) +"#" +
                    model.getValueAt(i, 5));

                bw.newLine();
            }
            bw.close();
        }catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving file.");
        }
    }

    void read() {
        try {
            File file = new File("LaBubbles.txt");
            if (!file.exists()) return;

            model.setRowCount(0);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("#");

                if (data.length == 6){
                    model.addRow(data);
                }
                

            }

            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading data.");
        }
    }

    void tableSelect() {
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();

                txtName.setText(model.getValueAt(row, 0).toString());
                txtNumber.setText(model.getValueAt(row, 1).toString());
                txtLoads.setText(model.getValueAt(row, 2).toString());
                cbStat.setSelectedItem(model.getValueAt(row, 3).toString());
                String gender = model.getValueAt(row, 4).toString();
                txtDate.setText(model.getValueAt(row, 5).toString());

                if (gender.equals("Male")){
                    rbtnM.setSelected(true);

                } else if (gender.equals("Female")) {
                    rbtnF.setSelected(true);
                }
            }
        });
    }
}
