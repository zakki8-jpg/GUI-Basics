import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

    

    
public class LMS extends JFrame{
    private static final String filename = "books.txt";
    private static final String d = "#";
    private JTextField txtID, txtTitle, txtAuth, txtYear;
    private JLabel lblID, lblTitle, lblAuth, lblGenre, lblAvail, lblYear;
    private JButton btnadd,btnupdate,btndelete;
    private JRadioButton rb1,rb2;
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane sp;
    private ButtonGroup gr;
    private JComboBox<String> cbg;

    LMS() {

        //lbl
        lblID = new JLabel("Book ID:");
        lblTitle = new JLabel("Title:");
        lblAuth = new JLabel("Author:");
        lblGenre = new JLabel("Genre:");
        lblAvail = new JLabel("Availability:");
        lblYear = new JLabel("Year Published:");
        add(lblID).setBounds(20,20,80,20);
        add(lblTitle).setBounds(20,60,80,20);
        add(lblAuth).setBounds(20,100,80,20);
        add(lblGenre).setBounds(400,20,80,20);
        add(lblAvail).setBounds(400, 60,80,20);
        add(lblYear).setBounds(400,100,90,20);

        //txt
        txtID = new JTextField();
        txtTitle = new JTextField();
        txtAuth = new JTextField();
        txtYear = new JTextField();
        add(txtID).setBounds(110,20,230,25);
        add(txtTitle).setBounds(110,60,230,25);
        add(txtAuth).setBounds(110,100,230,25);
        add(txtYear).setBounds(500,100,220,25);

        //cbox
        String[] genre = {"Fiction", "Adventure", "Romance", "Action", "Gore", "Horror", "Classic", "Finance"};
        cbg = new JComboBox<>(genre);
        add(cbg).setBounds(500,20,200,25);

        //rbs
        rb1 = new JRadioButton("Available");
        rb2 = new JRadioButton("Borrowed");
        gr = new ButtonGroup();
        gr.add(rb1);
        gr.add(rb2);
        add(rb1).setBounds(495,60,80,20);
        add(rb2).setBounds(580,60,90,20);

        //btns
        btnadd = new JButton("Add");
        btnupdate = new JButton("Update");
        btndelete = new JButton("Delete");
        add(btnadd).setBounds(140,150,100,30);
        add(btnupdate).setBounds(340,150,100,30);
        add(btndelete).setBounds(540,150,100,30);



        //table
        String[] cols = {"Book ID", "Title", "Author", "Genre","Year","Status"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        sp = new JScrollPane(table);
        JTableHeader th = table.getTableHeader();
        th.setBackground(new Color(0,200,220));
        add(sp).setBounds(20,200,690,240);




        read();
        tableS();
        add();
        update();
        delete();


        setLayout(null);
        setSize(750,500);
        setTitle("Library Management System");
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    void read () {
        try {
            File file = new File(filename);
            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) !=null) {
                String[] data = line.split(d);

                if (data.length == 6) {
                    model.addRow(data);
                }
                
            }
            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    void save(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            for (int i = 0; i < model.getRowCount();i++) {
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
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    void clear() {
        txtID.setText("");
        txtTitle.setText("");
        txtAuth.setText("");
        txtYear.setText("");
        cbg.setSelectedIndex(0);
        gr.clearSelection();
    }
    void add(){
        btnadd.addActionListener(e -> {
            int conf = JOptionPane.showConfirmDialog(null, "Please Confirm add", "Confirm ADD", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                String id = txtID.getText();
                String title = txtTitle.getText();
                String auth = txtAuth.getText();
                String gen = (String) cbg.getSelectedItem();
                String year = txtYear.getText();
                String av = "";
                if (rb1.isSelected()){
                    av = rb1.getText();
                } else if (rb2.isSelected()) {
                    av = rb2.getText();
                }
                model.insertRow(0,  new Object[] {
                    id,title,auth,gen,year,av
                });
                save();
                clear();
            }
        });
    }
    void update() {
        btnupdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row from the table first");
                return;
            }

            int conf = JOptionPane.showConfirmDialog(null, "Please confirm update", "Confirm UPDATE", JOptionPane.YES_NO_OPTION);
            if(conf == JOptionPane.YES_OPTION) {
                model.setValueAt(txtID.getText(), row, 0);
                model.setValueAt(txtTitle.getText(), row, 1);
                model.setValueAt(txtAuth.getText(), row, 2);
                model.setValueAt(cbg.getSelectedItem(), row, 3);
                model.setValueAt(txtYear.getText(), row, 4);
                String av = "";
                if (rb1.isSelected()) {
                    av = rb1.getText();
                } else if (rb2.isSelected()) {
                    av = rb2.getText();
                }
                model.setValueAt(av, row, 5);

                save();
                clear();
 
 
            }
        });
    }
    void delete() {
        btndelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1 ) {
                JOptionPane.showMessageDialog(null, "Please select a row from the table first");
                return;
            }

            int conf = JOptionPane.showConfirmDialog(null,"Please confrim delete", "Confirm DELETE", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                model.removeRow(row);
                save();
                clear();
            }
            
        });
    }
    void tableS() {
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent e) {
                int row = table.getSelectedRow();

                txtID.setText(model.getValueAt(row, 0).toString());
                txtTitle.setText(model.getValueAt(row, 1).toString());
                txtAuth.setText(model.getValueAt(row, 2).toString());
                cbg.setSelectedItem(model.getValueAt(row, 3).toString());
                txtYear.setText(model.getValueAt(row,  4).toString());
                String stat = model.getValueAt(row, 5).toString();
                if (stat.equals("Available")) {
                    rb1.setSelected(true);
                } else if (stat.equals("Borrowed")){
                    rb2.setSelected(true);
                }
            }
        });
    }



    public static void main(String[] args) {
        new LMS();
    }
    
}
