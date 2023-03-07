import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;


public class StuInfo {
    private JTextField textFieldStID;
    private JTextField textFieldName;
    private JTextField textFieldDob;
    private JTextField textFieldCity;
    private JButton ADDButton;
    private JButton refreshButton1;
    private JPanel JpanelStuInfo;
    private JButton updateButton;
    private JButton deleteButton;
    private JTable table1;
    private JScrollPane table_1;
    private JButton refreshButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("StuInfo");
        frame.setContentPane(new StuInfo().JpanelStuInfo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/students", "root", "");
            System.out.println("Database Connected Succesfully");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void tableLoad() {
        try {
            pst = con.prepareStatement("select * from student");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public StuInfo() {
        connect();
        tableLoad();

        //add button
        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int stId = Integer.parseInt(textFieldStID.getText());
                String name = textFieldName.getText();
                String dob = textFieldDob.getText();
                String city = textFieldCity.getText();
                try {
                    pst = con.prepareStatement("INSERT INTO `student` (`student_id`, `name`, `dob`, `city`) VALUES (?, ?, ?, ?);");
                    pst.setString(1, String.valueOf(stId));
                    pst.setString(2, name);
                    pst.setString(3, dob);
                    pst.setString(4, city);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Added");

                    textFieldStID.setText("");
                    textFieldName.setText("");
                    textFieldDob.setText("");
                    textFieldCity.setText("");
                    textFieldStID.requestFocus();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        //update button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int stId = Integer.parseInt(textFieldStID.getText());
                String name = textFieldName.getText();
                String dob = textFieldDob.getText();
                String city = textFieldCity.getText();

                try {
                    pst = con.prepareStatement("update students set student_id = ?,name = ?,dob = ?,city = ? where id = ?");
                    pst.setString(1, String.valueOf(stId));
                    pst.setString(2, name);
                    pst.setString(3, dob);
                    pst.setString(4, city);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Update");
                    tableLoad();
                    textFieldStID.setText("");
                    textFieldName.setText("");
                    textFieldDob.setText("");
                    textFieldCity.requestFocus();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //delete button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String stId;
                stId = textFieldStID.getText();

                try {
                    pst = con.prepareStatement("delete from students  where id = ?");

                    pst.setString(1, stId);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Delete");
                    tableLoad();
                    textFieldName.setText("");
                    textFieldDob.setText("");
                    textFieldCity.setText("");
                    textFieldStID.requestFocus();
                } catch (SQLException e1) {

                    e1.printStackTrace();
                }
            }
        });

        //view data on table
        refreshButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableLoad();
            }
        });
    }
}
