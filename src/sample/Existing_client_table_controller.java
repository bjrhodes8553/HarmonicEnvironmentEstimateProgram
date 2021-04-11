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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class Existing_client_table_controller {

    @FXML
    private TableView<ClientProjectThing> tblview_client;

    @FXML
    private TableColumn col_client;

    @FXML
    private TableColumn col_project;

    @FXML
    private Button button_go_to_account;

    @FXML
    private Button button_add_new_client;

    @FXML
    private Label label_users_name;

    @FXML
    private TableView<?> tblview_material_to_client;

    @FXML
    private TableColumn<?, ?> col_client_material_name;

    @FXML
    private TableColumn<?, ?> col_client_material_unit;

    @FXML
    private TableColumn<?, ?> col_client_material_price;

    @FXML
    private Button btn_add_mat_to_client;
    @FXML
    private TableView<?> tblview_labor_to_client;

    @FXML
    private TableColumn<?, ?> col_client_labor_name;

    @FXML
    private TableColumn<?, ?> col_client_labor_price;

    @FXML
    private Button btn_add_lab_client;

    @FXML
    private TextField txtfield_mat_name;

    @FXML
    private TextField txtfield_mat_unit;

    @FXML
    private TextField txtfield_mat_price;

    @FXML
    private Button btn_add_mat_database;

    @FXML
    private TextField txtfield_lab_name_db;
    @FXML
    private TextField txtfield_lab_price_db;

    @FXML
    private Button btn_add_lab_database;

    @FXML
    private Button btn_logout;

    @FXML
    private Button btn_view_mat;
    @FXML
    private Button btn_view_lab;







    public void initialize() {
        col_client.setCellValueFactory(new PropertyValueFactory<ClientProjectThing,
            String>("client"));
        col_project.setCellValueFactory(new PropertyValueFactory<ClientProjectThing,
            String>("project"));
        try {
            tblview_client.getItems().addAll(populateList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList populateList() throws SQLException {
        ArrayList clientProjectArrayList = new ArrayList();
        Database_Accessor accessor = new Database_Accessor();
        ResultSet rs = accessor.access_database("SELECT customer, "
            + "project_name FROM harmonic_client");
        while (rs.next()){
            String client = rs.getString("customer");
            String project = rs.getString("project_name");
            clientProjectArrayList.add(new ClientProjectThing(client, project));
        }
        return clientProjectArrayList;
    }

    @FXML
    void go_to_new_client(MouseEvent event){
        int clientID = 0;
        String projectName = "", customer = "", representative = "",
            projectManager = "", estimator = "", jobNotes = "",
            privateNotes = "", conflicts = "";
        Harmonic_Client current_client = new Harmonic_Client(clientID,
            projectName, customer,
            representative, projectManager, estimator, jobNotes,
            privateNotes, conflicts);
        Main.current_client = current_client;
        System.out.println("Going to new client...");
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


    @FXML
    void add_labor_database(MouseEvent event) {
        String name = txtfield_lab_name_db.getText();
        double price = Double.parseDouble(txtfield_lab_price_db.getText());
        Database_Accessor accessor = new Database_Accessor();
        accessor.update_database("INSERT INTO labor(laborName, "
            + "price_per_hour) VALUES ('"
                +name+"', '"+price+"')");
        txtfield_lab_name_db.clear();
        txtfield_lab_price_db.clear();
    }


    @FXML
    void add_material_database(MouseEvent event) {
        String name = txtfield_mat_name.getText();
        String unit = txtfield_mat_unit.getText();
        double price = Double.parseDouble(txtfield_mat_price.getText());
        Database_Accessor accessor = new Database_Accessor();
        accessor.update_database("INSERT INTO materials(materialName, unit, "
            + "price) VALUES ('"
                +name+"', '"+unit+"','"+price+"')");
        txtfield_mat_name.clear();
        txtfield_mat_unit.clear();
        txtfield_mat_price.clear();
    }

    @FXML
    void add_material_to_client(MouseEvent event) {
    }

    @FXML
    void logout(MouseEvent event) {
        Main.createNewScene(event, "Login_screen.fxml");
    }

    @FXML
    void go_to_materials(MouseEvent event) {
        Main.createNewScene(event, "Materials_screen.fxml");
    }

    @FXML
    void go_to_labor(MouseEvent event) {
        Main.createNewScene(event, "Labor_screen.fxml");
    }
}
