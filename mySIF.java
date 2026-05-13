import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.io.*;

public class mySIF extends JFrame {

    static DefaultTableModel model;
    static JTable table;
    JScrollPane scroll;

    JTextField nametxt, coursetxt, sectiontxt;
    JLabel namelbl, courselbl, sectionlbl;

    JButton btnadd, btnUpd, btnDel, btnClr;

    mySIF() {

        setTitle("Balucan Student Information Form");
        setSize(460, 420);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // LABELS
        namelbl = new JLabel("Name");
        courselbl = new JLabel("Course");
        sectionlbl = new JLabel("Section");

        add(namelbl).setBounds(20, 270, 50, 20);
        add(courselbl).setBounds(155, 270, 50, 20);
        add(sectionlbl).setBounds(290, 270, 50, 20);

        // TEXTFIELDS
        nametxt = new JTextField();
        coursetxt = new JTextField();
        sectiontxt = new JTextField();

        add(nametxt).setBounds(20, 290, 125, 20);
        add(coursetxt).setBounds(155, 290, 125, 20);
        add(sectiontxt).setBounds(290, 290, 125, 20);

        // BUTTONS
        btnadd = new JButton("Add");
        btnUpd = new JButton("Update");
        btnDel = new JButton("Delete");
        btnClr = new JButton("Clear");

        add(btnadd).setBounds(20, 320, 70, 25);
        add(btnUpd).setBounds(120, 320, 80, 25);
        add(btnDel).setBounds(225, 320, 80, 25);
        add(btnClr).setBounds(340, 320, 70, 25);

        // TABLE MODEL
        model = new DefaultTableModel(new String[]{"Name", "Course", "Section"}, 0);
        table = new JTable(model);

        scroll = new JScrollPane(table);
        add(scroll).setBounds(20, 15, 410, 240);

        // =========================
        // 🔥 MOUSE LISTENER HERE
        // =========================
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                int row = table.getSelectedRow();

                if (row != -1) {
                    nametxt.setText(model.getValueAt(row, 0).toString());
                    coursetxt.setText(model.getValueAt(row, 1).toString());
                    sectiontxt.setText(model.getValueAt(row, 2).toString());
                }
            }
        });

        // BUTTON ACTIONS
        btnadd.addActionListener(e -> addbtn());
        btnUpd.addActionListener(e -> update());
        btnDel.addActionListener(e -> delete());
        btnClr.addActionListener(e -> clearFields());

        read();

        setVisible(true);
    }

    // ADD
    void addbtn() {
        try {
            model.addRow(new Object[]{
                nametxt.getText(),
                coursetxt.getText(),
                sectiontxt.getText()
            });

            saveFile();
            clearFields();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error adding data");
        }
    }

    // UPDATE
    void update() {
        try {
            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Select a row first");
                return;
            }

            model.setValueAt(nametxt.getText(), row, 0);
            model.setValueAt(coursetxt.getText(), row, 1);
            model.setValueAt(sectiontxt.getText(), row, 2);

            saveFile();
            clearFields();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error updating data");
        }
    }

    // DELETE
    void delete() {
        try {
            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Select a row to delete");
                return;
            }

            model.removeRow(row);
            saveFile();
            clearFields();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error deleting data");
        }
    }

    // CLEAR
    void clearFields() {
        nametxt.setText("");
        coursetxt.setText("");
        sectiontxt.setText("");
        table.clearSelection();
    }

    // SAVE FILE
    void saveFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("students.txt"));

            for (int i = 0; i < model.getRowCount(); i++) {
                bw.write(model.getValueAt(i, 0) + "#" +
                         model.getValueAt(i, 1) + "#" +
                         model.getValueAt(i, 2));
                bw.newLine();
            }

            bw.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error saving file");
        }
    }

    // READ FILE
    void read() {
        try {
            File file = new File("students.txt");
            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("#");

                if (data.length == 3) {
                    model.addRow(data);
                }
            }

            br.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading data");
        }
    }

    public static void main(String[] args) {
        new mySIF();
    }
}