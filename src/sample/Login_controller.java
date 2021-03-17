package sample;

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
    void login(MouseEvent event) {
        String username = "";
        String password = "";


        if(txtfield_username.getText() != null){
            username = txtfield_username.getText();
            txtfield_username.clear();
        }else{
            System.out.println("Please enter a correct username");
        }
        if(txtfield_password.getText()!= null){
            password = txtfield_password.getText();
            txtfield_password.clear();
        }
        else{
            System.out.println("Please enter a correct password");
        }
        Main.createNewScene(event, "Client_selection_screen.fxml");

    }

}
