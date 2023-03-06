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
    private JButton viewButton;
    private JPanel JpanelStuInfo;

    public static void main(String[] args) {
        JFrame frame = new JFrame("StuInfo");
        frame.setContentPane(new StuInfo().JpanelStuInfo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public void connect(){
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

    public StuInfo() {
        connect();

        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                


            }
        });
    }
}
