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
    private JButton AddButton;
    private JButton refreshButton1;
    private JPanel JpanelStuInfo;
    private JButton updateButton;
    private JButton deleteButton;
    private JTable table1;
    private JButton searchButton;
    private JTextField textFieldSearch;
    private JScrollPane table_1;

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
        AddButton.addActionListener(new ActionListener() {
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

        //search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    int searchId = Integer.parseInt(textFieldSearch.getText());

                    pst = con.prepareStatement("SELECT student_id,name,dob,city FROM student WHERE student_id = ?");
                    pst.setString(1, String.valueOf(searchId));
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        int sId = Integer.parseInt(rs.getString(1));
                        String sName = rs.getString( 2);
                        String sDob = rs.getString(3);
                        String sCity = rs.getString(4);

                        textFieldStID.setText(String.valueOf(sId));
                        textFieldName.setText(sName);
                        textFieldDob.setText(sDob);
                        textFieldCity.setText(sCity);

                    }
                    else
                    {
                        textFieldStID.setText("");
                        textFieldName.setText("");
                        textFieldDob.setText("");
                        textFieldCity.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Student No");

                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
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

        //update button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sId = Integer.parseInt(textFieldStID.getText());
                String sname = textFieldName.getText();
                String sdob = textFieldDob.getText();
                String scity = textFieldCity.getText();

                try {
                    pst = con.prepareStatement("UPDATE `student` SET `student_id`=?,`name`=?,`dob`=?,`city`=? WHERE `student_id`=?");
                    pst.setString(1, String.valueOf(sId));
                    pst.setString(2, sname);
                    pst.setString(3, sdob);
                    pst.setString(4, scity);
                    pst.setString(5, String.valueOf(sId));

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Update");
                    tableLoad();
                    textFieldStID.setText("");
                    textFieldName.setText("");
                    textFieldDob.setText("");
                    textFieldCity.setText("");
                    textFieldStID.requestFocus();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //delete button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int stId;
                stId = Integer.parseInt(textFieldStID.getText());

                try {
                    pst = con.prepareStatement("DELETE FROM student  WHERE student_id = ?");
                    pst.setString(1, String.valueOf(stId));

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Delete");
                    tableLoad();
                    textFieldStID.setText("");
                    textFieldName.setText("");
                    textFieldDob.setText("");
                    textFieldCity.setText("");
                    textFieldStID.requestFocus();
                } catch (SQLException e1) {

                    e1.printStackTrace();
                }
            }
        });
    }
}
