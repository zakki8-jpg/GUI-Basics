//Balucan
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import javax.swing.table.*;

public class SCWMS extends JFrame {

    static JTextField txtFN, txtCors, txtYrL, txtWB, txtStat;
    static JLabel lblFN, lblCors, lblYrL, lblWB, lblStat;
    static JButton btnAdd, btnClr, btnUpd, btnDelete;
    static DefaultTableModel model;
    static JTable table;

    SCWMS() {

        lblFN = new JLabel("Full Name");
        add(lblFN).setBounds(40, 315, 150, 30);

        txtFN = new JTextField();
        add(txtFN).setBounds(40, 340, 200, 25);

        lblCors = new JLabel("Course/Dept");
        add(lblCors).setBounds(40, 370, 150, 30);

        txtCors = new JTextField();
        add(txtCors).setBounds(40, 395, 200, 25);

        lblYrL = new JLabel("Year Level");
        add(lblYrL).setBounds(40, 425, 150, 30);

        txtYrL = new JTextField();
        add(txtYrL).setBounds(40, 450, 200, 25);

        lblWB = new JLabel("Wallet Balance");
        add(lblWB).setBounds(40, 480, 150, 30);

        txtWB = new JTextField();
        add(txtWB).setBounds(40, 505, 200, 25);

        lblStat = new JLabel("Status (Active/Suspended)");
        add(lblStat).setBounds(40, 535, 200, 30);

        txtStat = new JTextField();
        add(txtStat).setBounds(40, 560, 200, 25);

        btnAdd = new JButton("Add");
        add(btnAdd).setBounds(350, 620, 100, 30);

        btnUpd = new JButton("Update");
        add(btnUpd).setBounds(470, 620, 100, 30);

        btnDelete = new JButton("Delete");
        add(btnDelete).setBounds(590, 620, 100, 30);

        btnClr = new JButton("Clear");
        add(btnClr).setBounds(710, 620, 100, 30);

        model = new DefaultTableModel();

        model.addColumn("Full Name");
        model.addColumn("Course/Dept");
        model.addColumn("Year Level");
        model.addColumn("Wallet Balance");
        model.addColumn("Status");

        table = new JTable(model);

        JTableHeader th = table.getTableHeader();
        th.setBackground(new Color(135, 206, 249));
        th.setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane sp = new JScrollPane(table);
        add(sp).setBounds(300, 20, 650, 580);

        read();
        addBtn();
        updBtn();
        deleteBtn();
        clearBtn();
        tableSelect();

        setLayout(null);
        setTitle("Balucan School Canteen Management");
        setSize(1000, 720);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    boolean validateInputs() {

        String fn = txtFN.getText().trim();
        String cors = txtCors.getText().trim();
        String yl = txtYrL.getText().trim();
        String wb = txtWB.getText().trim();
        String stat = txtStat.getText().trim();

        if (fn.isEmpty() || cors.isEmpty() || yl.isEmpty() || wb.isEmpty() || stat.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are required");
            return false;
        }

        try {
            Integer.parseInt(yl);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Year Level must be numeric");
            return false;
        }

        try {
            Double.parseDouble(wb);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Wallet Balance must be numeric");
            return false;
        }

        if (!(stat.equalsIgnoreCase("Active") || stat.equalsIgnoreCase("Suspended"))) {
            JOptionPane.showMessageDialog(null, "Status must be Active or Suspended");
            return false;
        }

        return true;
    }

    void addBtn() {

        btnAdd.addActionListener(e -> {

            if (!validateInputs()) {
                return;
            }

            String fn = txtFN.getText();
            String cors = txtCors.getText();
            String yl = txtYrL.getText();
            String wb = txtWB.getText();
            String stat = txtStat.getText();

            model.addRow(new Object[] { fn, cors, yl, wb, stat });

            saveFile();

            clearFields();
        });
    }

    void updBtn() {

        btnUpd.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Select a row first");
                return;
            }

            if (!validateInputs()) {
                return;
            }

            model.setValueAt(txtFN.getText(), row, 0);
            model.setValueAt(txtCors.getText(), row, 1);
            model.setValueAt(txtYrL.getText(), row, 2);
            model.setValueAt(txtWB.getText(), row, 3);
            model.setValueAt(txtStat.getText(), row, 4);

            saveFile();

            clearFields();

            JOptionPane.showMessageDialog(null, "Record Updated");
        });
    }

    void deleteBtn() {

        btnDelete.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Select a row first");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Delete selected record?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {

                model.removeRow(row);

                saveFile();

                clearFields();

                JOptionPane.showMessageDialog(null, "Record Deleted");
            }
        });
    }

    void clearBtn() {

        btnClr.addActionListener(e -> clearFields());
    }

    void clearFields() {

        txtFN.setText("");
        txtCors.setText("");
        txtYrL.setText("");
        txtWB.setText("");
        txtStat.setText("");
    }

    void tableSelect() {

        table.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {

                int row = table.getSelectedRow();

                txtFN.setText(model.getValueAt(row, 0).toString());
                txtCors.setText(model.getValueAt(row, 1).toString());
                txtYrL.setText(model.getValueAt(row, 2).toString());
                txtWB.setText(model.getValueAt(row, 3).toString());
                txtStat.setText(model.getValueAt(row, 4).toString());
            }
        });
    }

    void saveFile() {

        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter("School_Canteen.txt"));

            for (int i = 0; i < model.getRowCount(); i++) {

                bw.write(
                        model.getValueAt(i, 0) + "#" +
                                model.getValueAt(i, 1) + "#" +
                                model.getValueAt(i, 2) + "#" +
                                model.getValueAt(i, 3) + "#" +
                                model.getValueAt(i, 4));

                bw.newLine();
            }

            bw.close();

        } catch (IOException e) {

            JOptionPane.showMessageDialog(null, "Error Saving File");
        }
    }

    void read() {

        try {

            File file = new File("School_Canteen.txt");

            if (!file.exists()) {
                return;
            }

            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split("#");

                if (data.length == 5) {
                    model.addRow(data);
                }
            }

            br.close();

        } catch (IOException e) {

            JOptionPane.showMessageDialog(null, "Error Loading File");
        }
    }

    public static void main(String[] args) {

        new SCWMS();
    }
}