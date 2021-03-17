package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class Existing_client_table_controller {

    @FXML
    private TableView<?> tblview_client;

    @FXML
    private TableColumn<?, ?> col_client;

    @FXML
    private TableColumn<?, ?> col_project;

    @FXML
    private Button button_go_to_account;

    @FXML
    private Label label_users_name;

    @FXML
    void go_to_account(MouseEvent event) {
        Harmonic_Client current_client = new Harmonic_Client(1,
                "Wine Room",
                "Number One Hotel Inc. 111 One Street, Suite 1, One City, FL 11111",
                "Harmonic Environments Chris Fletcher Hialeah, FL",
                "EMJAC Chris Fletcher [Ext. 226]", "EMJAC Chris Fletcher [Ext. 226]",
                "These notes are viewable by the client.",
                "These notes are not viewable by the client.",
                "These are the conflicts");
        Main.current_client = current_client;
        System.out.println("Accessing client 1..");
        Main.createNewScene(event, "client_screen.fxml");


    }

}
