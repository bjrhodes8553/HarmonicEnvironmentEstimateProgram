package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import java.sql.*;

import java.sql.SQLException;

public class password_reset_controller {

    @FXML
    private TextField txtfield_sec_num;

    @FXML
    private PasswordField txtfield_password;

    @FXML
    private Button btn_reset;

    @FXML
    public Text reset_text;

    @FXML
    private Button reset_back;

    @FXML
    void resetPassword(MouseEvent reset) throws SQLException {
        if(txtfield_sec_num.getText().equals("123456789")){
            reset_text.setText("Password has been reset!");
        }else{
            reset_text.setText("Please enter the correct Security Number!");
        }
    }

    @FXML
    void resetBack(MouseEvent back) throws SQLException {
        Main.createNewScene(back, "Login_screen.fxml");
    }

}
