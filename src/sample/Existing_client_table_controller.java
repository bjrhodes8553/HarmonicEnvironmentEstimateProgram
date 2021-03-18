package sample;

import com.mysql.cj.xdevapi.Client;
import com.mysql.cj.xdevapi.Table;
import java.awt.SystemTray;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class Existing_client_table_controller implements Initializable {

    @FXML
    private TableView<ClientProjectThing> tblview_client;

    @FXML
    private TableColumn col_client = new TableColumn("Client");

    @FXML
    private TableColumn col_project = new TableColumn("Project");

    @FXML
    private Button button_go_to_account;

    @FXML
    private Label label_users_name;

    private ObservableList<ClientProjectThing> list;

    Database_Accessor accessor;

/*    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle){
        col_client.setCellValueFactory(new PropertyValueFactory<ClientProjectThing, String>("Client"));
        col_project.setCellValueFactory(new PropertyValueFactory<ClientProjectThing, String>("Project"));
        buildData();
    }

    public void buildData(){
        list = FXCollections.observableArrayList();
        try{
            String query = "SELECT customer, project_name FROM harmonic_client";
            String client;
            String project;
            ResultSet rs = accessor.access_database(query);
            while (rs.next()){
                client = rs.getString("customer");
                project = rs.getString("project_name");
                ClientProjectThing cpt = new ClientProjectThing(client, project);
                list.add(cpt);
            }
            tblview_client.setItems(list);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_client.setCellValueFactory(new PropertyValueFactory<ClientProjectThing,
            String>("Client"));
        col_project.setCellValueFactory(new PropertyValueFactory<ClientProjectThing,
            String>("Project"));
        tblview_client.getColumns().addAll(col_client, col_project);
        try {
            tblview_client.setItems(populateList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList populateList() throws SQLException {
        ObservableList<ClientProjectThing> clientProjectList;
        ArrayList clientProjectArrayList = new ArrayList();
        Database_Accessor accessor = new Database_Accessor();
        ResultSet rs = accessor.access_database("SELECT customer, "
            + "project_name FROM harmonic_client");
        while (rs.next()){
            String client = rs.getString("customer");
            String project = rs.getString("project_name");
            System.out.println("KILL ME");
            System.out.println(client);
            System.out.println(project);;
            clientProjectArrayList.add(new ClientProjectThing(client, project));
            System.out.println(clientProjectArrayList);
        }
        clientProjectList =
            FXCollections.observableArrayList(clientProjectArrayList);
        System.out.println("List:");
        System.out.println(clientProjectList);
        return clientProjectList;
    }


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
