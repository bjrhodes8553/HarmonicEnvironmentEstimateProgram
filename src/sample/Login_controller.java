package sample;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Login_controller {

    @FXML
    private TextField txtfield_username;

    @FXML
    private PasswordField txtfield_password;

    @FXML
    private Button btn_login;

    @FXML
    void login(MouseEvent event) throws SQLException {
        String username = "";
        String password = "";
        Database_Accessor accessor = new Database_Accessor();
        ResultSet rs;

        if(txtfield_username.getText() == null || txtfield_password.getText() == null){
            System.out.println("Please enter a correct username and password");
        }else{
            username = txtfield_username.getText();
            password = txtfield_password.getText();
            txtfield_username.clear();
            txtfield_password.clear();
            rs = accessor.access_database("SELECT username, password FROM "
                + "login");
            while (rs.next()){
                String user = rs.getString("username");
                String pass = rs.getString("password");

                if (username.equalsIgnoreCase(user) && password.equals(pass)){
                    System.out.println("Logging In...");
                    Main.createNewScene(event, "Existing_client_table_screen.fxml");
                }
                else {
                    System.out.println("Incorrect username or password.");
                }
            }
        }
    }
}
