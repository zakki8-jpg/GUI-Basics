import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
public class EPS extends JFrame{
    private static final String f = "eps.txt";
    private static final String d = "#";
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane sp;
    private JRadioButton rb1, rb2;
    private JButton btnadd, btnupd,btndel, btnclr;
    private JComboBox<String> dept;
    private JLabel lblid, lblname, lblstat, lbldept,lblhr,lblrat,lbltp;
    private JTextField txtid,txtname,txthr,txtrat,txttp;
    private ButtonGroup gr;



    EPS () {
        //lbl
        lblid = new JLabel("Employee ID:");
        lblname = new JLabel("Employee Name:");
        lblstat = new JLabel("Employment Status:");
        lbldept = new JLabel("Department:");
        lblhr = new JLabel("Hours Worked:");
        lblrat = new JLabel("Rate Per Hour:");
        
        add(lblid).setBounds(20,20,100,20);
        add(lblname).setBounds(20,60,100,20);
        add(lblstat).setBounds(380,20,120,20);
        add(lbldept).setBounds(550,50,100,20);
        add(lblhr).setBounds(20,100,100,20);
        add(lblrat).setBounds(20,140,100,20);


        //txt
        txtid = new JTextField();
        txtname = new JTextField();
        txthr = new JTextField();
        txtrat = new JTextField(".00");
        add(txtid).setBounds(120,20,200,20);
        add(txtname).setBounds(120,60,200,20);
        add(txthr).setBounds(120,100,200,20);
        add(txtrat).setBounds(120,140,200,20);

        //rbs
        rb1 = new JRadioButton("Full-time");
        rb2 = new JRadioButton("Part-time");
        gr = new ButtonGroup();
        gr.add(rb1);
        gr.add(rb2);
        add(rb1).setBounds(380,60,100,30);
        add(rb2).setBounds(380,100,100,30);

        //cbox
        String [] department = {"IT", "HR","Finance","Marketing"};
        dept = new JComboBox<>(department);
        add(dept).setBounds(550,70,120,20);

        //btns
        btnadd = new JButton("Add");
        btnupd = new JButton("Update");
        btndel = new JButton("Delete");
        btnclr = new JButton("Clear");
        btnupd.setForeground(new Color(200,0,0));
        btndel.setForeground(new Color(0,0,240));
        btnadd.setBackground(new Color(80,250,0));
        btnupd.setBackground(new Color(80,50,250));
        btndel.setBackground(new Color(250,20,10));
        add(btnadd).setBounds(700,60,100,60);
        add(btnupd).setBounds(430,140,100,30);
        add(btndel).setBounds(570,140,100,30);
        add(btnclr).setBounds(710,140,100,30);

        //table
        String[] cols = {"ID", "Name", "Status", "Department", "Hours", "Rate", "Salary"};
        model = new DefaultTableModel(cols,0);
        table = new JTable(model);
        sp = new JScrollPane(table);
        JTableHeader th = table.getTableHeader();
        th.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
        th.setBackground(new Color(150,250,0));
        add(sp).setBounds(20,180,790,240);

        lbltp = new JLabel("Total Payroll:");
        txttp = new JTextField();
        txttp.setEditable(false);
        lbltp.setFont(new Font("Arial",Font.BOLD,13));
        add(lbltp).setBounds(600,430,100,30);
        add(txttp).setBounds(700,430,110,25);



        

        read();
        total();
        tableS();
        add();
        update();
        delete();
        clearbtn();





        setLayout(null);
        setVisible(true);
        setTitle("Employee Payroll System");
        setSize(850,500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }
    boolean valid() {
        String id = txtid.getText().trim();
        String name = txtname.getText().trim();
        String hr = txthr.getText().trim();
        String rat = txtrat.getText().trim();

        if (id.isEmpty() || name.isEmpty() || hr.isEmpty() || rat.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are required");
            return false;
        }
        try {
            Integer.parseInt(hr);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hour must me numeric");
            return false;
        }
        try {
            Double.parseDouble(rat);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Rate must be numeric");
            return false;
        }


        return true;
    }
    void clearbtn() {
        btnclr.addActionListener(e -> clear());
    }
    void clear() {
        txtid.setText("");
        txtname.setText("");
        txthr.setText("");
        txtrat.setText("");
        gr.clearSelection();
        dept.setSelectedIndex(0);
    }
    void total() {
        double tot = 0;
        for (int i = 0; i <model.getRowCount(); i++) {
            tot += Double.parseDouble(model.getValueAt(i, 6).toString());
        }
        txttp.setText(String.format("%.2f", tot));
    }
    void add(){
        btnadd.addActionListener(e -> {
            if (!valid()) {
                return;
            }
            int conf = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to add record?",
                "Confirm add",
                JOptionPane.YES_NO_OPTION
            );
            if (conf == JOptionPane.YES_OPTION) {
                String id = txtid.getText();
                String name = txtname.getText();
                String hr = txthr.getText();
                String rat = txtrat.getText();
                String stat = "";
                if (rb1.isSelected()) {
                    stat = rb1.getText();
                } else if (rb2.isSelected()) {
                    stat = rb2.getText();
                }
                String department = (String) dept.getSelectedItem();
                Double sal = Integer.parseInt(hr) * Double.parseDouble(rat);
                model.insertRow(0, new Object [] {
                    id,name, stat,department,hr,rat,String.format("%.2f", sal)
                });

                save();
                total();
                clear();
            }
            


        });
    }

    void update() {
        btnupd.addActionListener(e -> {
            if (!valid()) return;
            int row = table.getSelectedRow();
            if (row == -1 ) {
                JOptionPane.showMessageDialog(null, "Please select a row from the table");
                return;
            }
            int conf = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to update record?", 
                "Confirm Update", 
                JOptionPane.YES_NO_OPTION
            );
            if (conf == JOptionPane.YES_OPTION) {
                double tot = Integer.parseInt(txthr.getText()) * Double.parseDouble(txtrat.getText());
                model.setValueAt(txtid.getText(), row, 0);
                model.setValueAt(txtname.getText(), row, 1);
                model.setValueAt(txthr.getText(), row, 4);
                model.setValueAt(txtrat.getText(), row, 5);
                String rbs = "";
                if (rb1.isSelected()) {
                    rbs = rb1.getText();
                } else if (rb2.isSelected()) {
                    rbs = rb2.getText();
                }
                model.setValueAt(rbs, row, 2);
                model.setValueAt(dept.getSelectedItem(), row, 3);
                model.setValueAt(tot, row, 6);

                save();
                total();
                clear();

            }
        });
    }
    
    void delete() {
        btndel.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select a record to delete");
                return;
            }
            int conf = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to delete record?",
                "Confirm Delete"
                , JOptionPane.YES_OPTION
            );

            if (conf == JOptionPane.YES_OPTION) {
                model.removeRow(row);
                
                save();
                total();
                clear();
            }
        });
    }



    void save() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            for (int i = 0; i<model.getRowCount(); i++) {
                bw.write(
                    model.getValueAt(i, 0) +d+
                    model.getValueAt(i, 1) +d+
                    model.getValueAt(i, 2) +d+
                    model.getValueAt(i, 3) +d+
                    model.getValueAt(i, 4) +d+
                    model.getValueAt(i, 5) +d+
                    model.getValueAt(i, 6)
                );
                bw.newLine();
            }
            bw.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    void read() {
        try {
            File file = new File(f);
            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine())!=null) {
                String[] data = line.split(d);

                if (data.length == 7) {
                    model.addRow(data);
                }
            }
            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    void tableS() {
        table.addMouseListener(new MouseAdapter(){
            public void mouseClicked (MouseEvent e) {
                int row = table.getSelectedRow();
                txtid.setText(model.getValueAt(row, 0).toString());
                txtname.setText(model.getValueAt(row, 1).toString());
                String rbs = model.getValueAt(row, 2).toString();
                if (rbs.equals("Full-time")) {
                    rb1.setSelected(true);
                } else if (rbs.equals("Part-time")) {
                    rb2.setSelected(true);
                }
                dept.setSelectedItem(model.getValueAt(row, 3).toString());
                txthr.setText(model.getValueAt(row, 4).toString());
                txtrat.setText(model.getValueAt(row, 5).toString());
            }
        });
    }




















    public static void main (String [] args) {
        new EPS();
    }

}
