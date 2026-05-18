import javax.swing.table.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
public class praktisRound2 extends JFrame{
    static JTextField txtName, txtID, txtSubs;
    static JLabel lblName, lblID, lblCourse,lblYr,lblSubs;
    static JButton btnAdd, btnUpd, btnDel, btnClr;
    static JComboBox<String> course;
    static JRadioButton rb1, rb2, rb3, rb4;
    static DefaultTableModel model;
    static JTable table;
    static ButtonGroup gr;

    praktisRound2() {
        //lbl
        lblName = new JLabel("Name");
        lblID = new JLabel("Student ID");
        lblCourse = new JLabel("Course");
        lblYr = new JLabel("Year Level");
        lblSubs = new JLabel("Subjects Enrolled");
        add(lblName).setBounds(20,80,100,20);
        add(lblID).setBounds(20,150,100,20);
        add(lblCourse).setBounds(20,230,100,20);
        add(lblYr).setBounds(20,280,100,20);
        add(lblSubs).setBounds(20,400,100,20);

        //txt
        txtName = new JTextField();
        txtID = new JTextField();
        txtSubs = new JTextField();
        add(txtName).setBounds(20,100,200,25);
        add(txtID).setBounds(20, 170, 200, 25);
        add(txtSubs).setBounds(20, 420,200,25 );

        //JcomboBox
        String[] courses = {"BSIT", "BSCS","BSIS", "ACT"};
        course = new JComboBox<>(courses);
        add(course).setBounds(20,250,100,20);

        rb1 = new JRadioButton("1st Year");
        rb2 = new JRadioButton("2nd Year");
        rb3 = new JRadioButton("3rd Year");
        rb4 = new JRadioButton("4th Year");

        gr = new ButtonGroup();
        gr.add(rb1);
        gr.add(rb2);
        gr.add(rb3);
        gr.add(rb4);

        add(rb1).setBounds(20,300,100,20);
        add(rb2).setBounds(20,320,100,20);
        add(rb3).setBounds(20,340,100,20);
        add(rb4).setBounds(20,360,100,20);

        //table
        model = new DefaultTableModel();
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        model.addColumn("Name");
        model.addColumn("Student ID");
        model.addColumn("Course");
        model.addColumn("Year Level");
        model.addColumn("Subjects Enrolled");
        add(sp).setBounds(10,480,465,270);

        //btn
        btnAdd = new JButton("Add");
        btnUpd = new JButton("Update");
        btnDel = new JButton("Delete");
        btnClr = new JButton("Clear");
        add(btnAdd).setBounds(350,100,100,30);
        add(btnUpd).setBounds(350,170,100,30);
        add(btnDel).setBounds(350,240,100,30);
        add(btnClr).setBounds(350,310,100,30);

        JTableHeader th =  table.getTableHeader();
        th.setBackground(new Color(115,150,200));
        th.setFont(new Font(Font.DIALOG, Font.BOLD, 12));

        read();
        addBtn();
        tableSelect();
        clearBtn();
        updBtn();
        delBtn();



        setLayout(null);
        setSize(500,816);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Student Registration System");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    void save() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("studentsReg.txt"));
            for (int i = 0; i <model.getRowCount(); i++) {
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
            JOptionPane.showMessageDialog(null, e);
        }
    }
    void clearBtn() {
        btnClr.addActionListener(e -> clear());
    }
    
    void clear() {
        txtName.setText("");
        txtID.setText("");
        txtSubs.setText("");
        course.setSelectedIndex(0);
        gr.clearSelection();

    }
    void addBtn() {
        btnAdd.addActionListener(e -> {
            String name = txtName.getText();
            String ID = txtID.getText();
            String subs = txtSubs.getText();
            String cour = (String) course.getSelectedItem();
            String year = "";
            if (rb1.isSelected()){
                year = rb1.getText();
            } else if (rb2.isSelected()) {
                year = rb2.getText();
            } else if (rb3.isSelected()) {
                year = rb3.getText();
            } else if (rb4.isSelected()) {
                year = rb4.getText();
            }
            model.insertRow(0, new Object [] {
                name,ID,cour,year, subs});
            
            save();
            clear();
        });
    }
    void updBtn() {
        btnUpd.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row first.");
                return;
            }
            model.setValueAt(txtName.getText(), row, 0);
            model.setValueAt(txtID.getText(), row, 1);
            model.setValueAt(course.getSelectedItem(), row, 2);
            String year = "";
            if (rb1.isSelected()) {
                year = rb1.getText();
            } else if (rb2.isSelected()) {
                year = rb2.getText();
            } else if (rb3.isSelected()) {
                year = rb3.getText();
            } else if (rb4.isSelected()) {
                year = rb4.getText();
            }

            model.setValueAt(year, row, 3);
            model.setValueAt(txtSubs, row, 4);

            save();
            clear();
        });
    }
    void delBtn() {
        btnDel.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(null,  "Please select a row first.");
                return;
            }
            model.removeRow(row);
            save();
            clear();
        });
    }
    void read() {
        try {
            File file = new File("studentsReg.txt");
            if (!file.exists()) return;
            model.setRowCount(0);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("#");

                if (data.length == 5) {
                    model.addRow(data);
                }
            }
            br.close();
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, e);

        }
    }
    void tableSelect() {
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();

                txtName.setText(model.getValueAt(row, 0).toString());
                txtID.setText(model.getValueAt(row, 1).toString());
                course.setSelectedItem(model.getValueAt(row, 2).toString());
                String year = model.getValueAt(row, 3).toString();
                txtSubs.setText(model.getValueAt(row, 4).toString());

                if (year.equals("1st Year")) {
                    rb1.setSelected(true);
                } else if (year.equals("2nd Year")) {
                    rb2.setSelected(true);

                } else if (year.equals("3rd Year")) {
                    rb3.setSelected(true);

                } else if (year.equals("4th Year")) {
                    rb4.setSelected(true);
                }

            }
            

        });
    }
    public static void main(String[] args) {
        new praktisRound2();
    }
}
