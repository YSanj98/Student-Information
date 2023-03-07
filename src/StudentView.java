import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentView {
    private JTable table1;
    private JScrollPane table_1;
    private JPanel JpanelStuView;

    public static void run() {
        JFrame frame = new JFrame("StudentView");
        frame.setContentPane(new StudentView().JpanelStuView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;
    public void tableLoad(){
        try {
            pst = con.prepareStatement("select * from student");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));

        }catch(SQLException e) {
            e.printStackTrace();

        }
    }

    public StudentView(JPanel jpanelStuView) {
        JpanelStuView = jpanelStuView;
        tableLoad();
    }
}





