package sample;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database_Accessor {
    Connection con;
    PreparedStatement pst;
    String testID;

    public void access_database(){
      /*  try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/harmonic_environment","root","");
            pst = con.prepareStatement("update harmonic_client set project_name = ?,customer = ?,representative = ?,project_manager = ?,estimator= ?,job_notes = ?,private_notes = ?,conflicts = ? where client_id = ?");


            pst.setString(1, firstname);
            pst.setString(2, lastname);
            pst.setString(3, nic);
            pst.setString(4, passport);
            pst.setString(5, address);
            pst.setString(6, date);
            pst.setString(7, Gender);
            pst.setString(8, contact);
            pst.setBytes(9, userimage);
            pst.setString(10, id);
            pst.executeUpdate();


        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }*/
    }
}
