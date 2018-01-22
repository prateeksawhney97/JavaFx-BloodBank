package bloodbank;

import java.sql.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javafx.scene.control.Alert;

public class Data {

    static int did = 0;
    static int sno = 0;
    

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/bloodbank", "root", "computer");
    }

    public static void showMessage(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Donors");
        a.setContentText(msg);
        a.show();
    }

    public static boolean isValidContactNo(String contactNo) {
        Pattern p = Pattern.compile("(0|[+]91)?[7-9][0-9]{9}");
        Matcher m = p.matcher(contactNo);
        if (m.find() && m.group().equals(contactNo)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidEmailId(String emailId) {
        Pattern p = Pattern.compile("[A-Za-z0-9][A-Za-z0-9_.]*@[A-Za-z0-9]+([.][A-Za-z]+)+");
        Matcher m = p.matcher(emailId);
        if (m.find() && m.group().equals(emailId)) {
            return true;
        } else {
            return false;
        }
    }

    public static int getDonarID(int sno) {
        try {
            Connection con = Data.getConnection();
            PreparedStatement ps = con.prepareStatement("select did from blood where sno=? ");
            ps.setInt(1, sno);
            ResultSet rs = ps.executeQuery();
            int did = 0;
            if (rs.next()) {
                did = rs.getInt(1);
            }
            con.close();
            return did;
        } catch (Exception ex) {
        }
        return 0;
    }

    public static String getBloodDonationDate(int sno) {
        try {
            Connection con = Data.getConnection();
            PreparedStatement ps = con.prepareStatement("select rdate from blood where sno=? ");
            ps.setInt(1, sno);
            String rdate = "";
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rdate = rs.getString(1);
            }
            con.close();
            return rdate;
        } catch (Exception ex) {
        }
        return "";
    }

    public static String getDonarName(int sno) {
        try {
            Connection con = Data.getConnection();
            PreparedStatement ps = con.prepareStatement("select name from donor where did=(select did from blood where sno=?)");
            ps.setInt(1, sno);
            ResultSet rs = ps.executeQuery();
            String name = "";
            if (rs.next()) {
                name = rs.getString(1);
            }
            con.close();
            return name;
        } catch (Exception ex) {
        }
        return "";
    }
}