package sample;

import com.mysql.cj.protocol.Resultset;
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
    private Button button_add_new_client;

    @FXML
    private Label label_users_name;

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
            clientProjectArrayList.add(new ClientProjectThing(client, project));
        }
        clientProjectList =
            FXCollections.observableArrayList(clientProjectArrayList);
        return clientProjectList;
    }

    @FXML
    void go_to_new_client(MouseEvent event){
      Main.createNewScene(event, "client_screen.fxml");
    }

    @FXML
    void go_to_account(MouseEvent event) throws SQLException {
        ClientProjectThing cpt = tblview_client.getSelectionModel().getSelectedItem();
        Database_Accessor accessor = new Database_Accessor();
        String query = "SELECT * FROM "
            + "harmonic_client WHERE customer = '" + cpt.getClient() + "' AND "
            + "project_name = '" + cpt.getProject() + "'";
        int clientID = 0;
        String projectName = "", customer = "", representative = "",
            projectManager = "", estimator = "", jobNotes = "",
            privateNotes = "", conflicts = "";

        System.out.println("Client: " + cpt.getClient());
        System.out.println("Project: " + cpt.getProject());
        System.out.println("Query: " + query);
        ResultSet rs = accessor.access_database(query);
        System.out.println(rs);
        while (rs.next()){
            clientID = rs.getInt("client_id");
            projectName = rs.getString("project_name");
            customer = rs.getString("customer");
            representative = rs.getString("representative");
            projectManager = rs.getString("project_manager");
            estimator = rs.getString("estimator");
            jobNotes = rs.getString("job_notes");
            privateNotes = rs.getString("private_notes");
            conflicts = rs.getString("conflicts");
        }
        Harmonic_Client current_client = new Harmonic_Client(clientID,
            projectName, customer,
                representative, projectManager, estimator, jobNotes,
                privateNotes, conflicts);
        Main.current_client = current_client;
        System.out.println("Accessing client " + clientID + "...");
        Main.createNewScene(event, "client_screen.fxml");
    }
}
