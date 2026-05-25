import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
public class EMS  extends JFrame{
    private static final String filename = "ems.txt";
    private static final String d = "#";
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane sp;
    private JLabel lblid, lblname, lblpos,lbldept,lblgend,lbltype;
    private ButtonGroup gr;
    private JRadioButton rb1,rb2;
    private JComboBox<String> dept,type;
    private JButton btnadd, btnupd,btndel;
    private JTextField txtid,txtname,txtpos;

    EMS () {
        //lbl
        lblid = new JLabel("Employee ID:");
        lblname = new JLabel("Full Name:");
        lblpos = new JLabel("Position:");
        lbldept = new JLabel("Departent:");
        lblgend = new JLabel("Gender:");
        lbltype = new JLabel("Emplyment Type:");
        add(lblid).setBounds(20,20,100,20);
        add(lblname).setBounds(20,70,100,20);
        add(lblpos).setBounds(20,120,100,20);
        add(lbldept).setBounds(400,20,100,20);
        add(lblgend).setBounds(400,70,100,20);
        add(lbltype).setBounds(400,120,100,20);

        //txt
        txtid = new JTextField();
        txtname = new JTextField();
        txtpos = new JTextField();
        add(txtid).setBounds(120,20,200,25);
        add(txtname).setBounds(120,70,200,25);
        add(txtpos).setBounds(120,120,200,25);
        
        //rbs
        rb1 = new JRadioButton("Male");
        rb2 = new JRadioButton("Female");
        gr = new ButtonGroup();
        gr.add(rb1);
        gr.add(rb2);
        add(rb1).setBounds(510,70,85,25);
        add(rb2).setBounds(600,70,85,25);

        //cbox
        String[] dep = {"IT Department", "Finance","HR Department", "Fire Department", "Security Department"};
        dept = new JComboBox<>(dep);
        add(dept).setBounds(510,20,150,25);
        String[] typ = {"Full-time", "Part-time"};
        type = new JComboBox<>(typ);
        add(type).setBounds(510,120,150,25);
        
        //btns
        btnadd = new JButton("Add");
        btnupd = new JButton("Update");
        btndel = new JButton("Delete");
        add(btnadd).setBounds(100,170,100,30);
        add(btnupd).setBounds(270,170,100,30);
        add(btndel).setBounds(440,170,100,30);

        //table
        String[] cols = {"Employee ID", "Full Name", " Position", "Department", "Gender", "Type"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        sp = new JScrollPane(table);
        add(sp).setBounds(20,210,700,240);
        JTableHeader th = table.getTableHeader();
        th.setBackground(new Color(10,220,140));


        read();
        tableS();
        add();
        update();
        delete();





        setLayout(null);
        setVisible(true);
        setSize(750,500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Emloyee Management System");
    }
    void save() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            for (int i = 0; i < model.getRowCount(); i++) {
                bw.write(
                    model.getValueAt(i, 0) +d+
                    model.getValueAt(i, 1) +d+
                    model.getValueAt(i, 2) +d+
                    model.getValueAt(i, 3) +d+
                    model.getValueAt(i, 4) +d+
                    model.getValueAt(i, 5)
                );
                bw.newLine();
            }
            bw.close();
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    void read(){
        try {
            File file = new File(filename);
            if (!file.exists()) return;
            
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(d);

                if (data.length == 6) {
                    model.addRow(data);
                }
            }
            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    void clear() {
        txtid.setText("");
        txtname.setText("");
        txtpos.setText("");
        gr.clearSelection();
        dept.setSelectedIndex(0);
        type.setSelectedIndex(0);
    }
    void add() {
        btnadd.addActionListener(e -> {
            int conf = JOptionPane.showConfirmDialog(null, "Are you sure you want to add data?", "Confirm add", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                String id = txtid.getText();
                String name = txtname.getText();
                String pos = txtpos.getText();
                String dep = (String) dept.getSelectedItem();
                String gen = "";
                if (rb1.isSelected()) {
                    gen = rb1.getText();
                } else if (rb2.isSelected()) {
                    gen = rb2.getText();
                }
                String ty = (String) type.getSelectedItem();

                model.insertRow(0, new Object[] {
                    id, name, pos, dep,gen,ty
                });

                save();
                clear();
            }
            


        });
    }

    void update() {
        btnupd.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row from the table first");
                return;
            }
            int conf = JOptionPane.showConfirmDialog(null, "Are you sure you want to add data?", "Confirm update", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                model.setValueAt(txtid.getText(), row, 0);
                model.setValueAt(txtname.getText(), row, 1);
                model.setValueAt(txtpos.getText(), row, 2);
                model.setValueAt(dept.getSelectedItem(), row, 3);
                String gen = "";
                if (rb1.isSelected()){
                    gen = rb1.getText();
                } else if (rb2.isSelected()) {
                    gen = rb2.getText();
                }
                model.setValueAt(gen, row, 4);
                model.setValueAt(type.getSelectedItem(), row, 5);
                save();
                clear();
            }
        });
    }
    void delete() {
        btndel.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1 ) {
                JOptionPane.showMessageDialog(null, "Please select a row from the table first");
                return;
            }

            int conf = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete data?", "Confirm delete", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                model.removeRow(row);
                save();
                clear();
            }
        });
    }













    void tableS () {
        table.addMouseListener(new MouseAdapter(){
            public void mouseClicked (MouseEvent e) {
                int row = table.getSelectedRow();

                txtid.setText(model.getValueAt(row,0).toString());
                txtname.setText(model.getValueAt(row, 1).toString());
                txtpos.setText(model.getValueAt(row, 2).toString());
                dept.setSelectedItem(model.getValueAt(row, 3).toString());
                String gen = model.getValueAt(row, 4).toString();
                if (gen.equals("Male")) {
                    rb1.setSelected(true);
                } else if (gen.equals("Female")) {
                    rb2.setSelected(true);
                }

                type.setSelectedItem(model.getValueAt(row, 5).toString());
         
            }
        });
    }




    public static void main(String[] args) {
        new EMS();
    }

}
