package sample;

//import com.mysql.cj.protocol.Resultset;
//import com.mysql.cj.xdevapi.Client;
//import com.mysql.cj.xdevapi.Table;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
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
    private TableView<Material> tblview_material_to_client;

    @FXML
    private TableColumn<?, ?> col_client_material_name;

    @FXML
    private TableColumn<?, ?> col_client_material_unit;

    @FXML
    private TableColumn<?, ?> col_client_material_price;

    @FXML
    private Button btn_view_client_materials;
    @FXML
    private TableView<Labor> tblview_labor_to_client;

    @FXML
    private TableColumn<?, ?> col_client_labor_name;

    @FXML
    private TableColumn<?, ?> col_client_labor_price;

    @FXML
    private Button btn_view_client_labor;

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

    @FXML
    private Button btn_add_edit_mat;

    @FXML
    private Button btn_add_edit_lab;








    public void initialize() {
        col_client.setCellValueFactory(new PropertyValueFactory<ClientProjectThing,
            String>("client"));
        col_project.setCellValueFactory(new PropertyValueFactory<ClientProjectThing,
            String>("project"));
        try {
            tblview_client.getItems().addAll(populateCLientList());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /*
    This method returns an ArrayList containing the current clients in the
    database. The tableview for the list of clients is then populated
    with this list.
     */
    public ArrayList populateCLientList() throws SQLException {
        ArrayList clientProjectArrayList = new ArrayList();
        Database_Accessor accessor = new Database_Accessor();
        //using the Database_Accessor object, we are able to access the db.
        ResultSet rs = accessor.access_database("SELECT customer, "
            + "project_name FROM harmonic_client");
        while (rs.next()){
            String client = rs.getString("customer");
            String project = rs.getString("project_name");
            clientProjectArrayList.add(new ClientProjectThing(client, project));
        }
        return clientProjectArrayList;
    }

    /*
    This method will take the user to a page where they can create a new
    client profile.
     */
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

    /*
    This method will take the user to whoever's account is highlighted in
    the tableview of clients. If no name is highlighted, a null pointer
    exception will happen.
     */
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


    /*
    This method is a result of pressing the Add Labor button.
    A labor object is added to the database based on the input in the fields.
     */
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



    /*
    This method populates the tableview for the materials in the customer's
    profile. The materials can then be edited by pressing the Add/Edit button,
    which will bring you to a new screen.
    This method only populates the tableview.
     */
    void populate_customer_materials() throws SQLException {
        String projectName = "", customer = "", representative = "",
                projectManager = "", estimator = "", jobNotes = "",
                privateNotes = "", conflicts = "";
        Material current_material = null;
        String mat_name = "";
        ResultSet rs2 = null;
        int clientID = 1;

        //Set the table view columns to the correct variable names.
        col_client_material_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_client_material_unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        col_client_material_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        ArrayList <Material> material_array_list = new ArrayList<>();
        Database_Accessor accessor = new Database_Accessor();
        ClientProjectThing current_client= tblview_client.getSelectionModel().getSelectedItem();
        String query = "SELECT * FROM "
                + "harmonic_client WHERE customer = '" + current_client.getClient() + "' AND "
                + "project_name = '" + current_client.getProject() + "'";
        ResultSet rs_client = accessor.access_database(query);
        while (rs_client.next()) {
           clientID = rs_client.getInt("client_id");
            projectName = rs_client.getString("project_name");
            customer = rs_client.getString("customer");
            representative = rs_client.getString("representative");
            projectManager = rs_client.getString("project_manager");
            estimator = rs_client.getString("estimator");
            jobNotes = rs_client.getString("job_notes");
            privateNotes = rs_client.getString("private_notes");
            conflicts = rs_client.getString("conflicts");
        }

        //This Harmonic_Client object will be used in other classes.
        Harmonic_Client this_client = new Harmonic_Client(clientID,
                projectName, customer,
                representative, projectManager, estimator, jobNotes,
                privateNotes, conflicts);

        //Set Main.current_client to the selected client so it can be used in other classes.
        Main.current_client = this_client;

        // This query will retrieve the material names associated with the client.
        ResultSet rs = accessor.access_database("SELECT * "
                +" FROM customer_materials WHERE customer_id = '" +clientID+"'");
        while (rs.next()) {
            mat_name = rs.getString("material_name");
        }

        // This query will retrieve the material's information that are associated with client.
        rs2 = accessor.access_database("SELECT * "
                    +" FROM materials WHERE materialName = '" + mat_name +"'");
        while (rs2.next()) {
            String m_name = rs2.getString("materialName");
            String unit = rs2.getString("unit");
            double price = rs2.getDouble("price");
            String description = rs2.getString("materialDesc");
            current_material = new Material(m_name, unit, price, description);
            material_array_list.add(current_material);
        }
        //Populates the tableview.
        tblview_material_to_client.getItems().addAll(material_array_list);

    }


    /*
    This method will populate the tableview associated with the
    labor attached to the clients profile that is selected from the
    client's tableview.
    If no client is selected a null pointer exception will occur.
     */
    void populate_customer_labor() throws SQLException {
        Labor current_labor = null;
        String lab_name = "";
        ResultSet rs2 = null;
        int clientID = 1;
        String projectName = "", customer = "", representative = "",
                projectManager = "", estimator = "", jobNotes = "",
                privateNotes = "", conflicts = "";

        //Set up the columns for the tableview with correct variables.
        col_client_labor_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_client_labor_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        ArrayList <Labor> labor_array_list = new ArrayList<>();
        Database_Accessor accessor = new Database_Accessor();

        //Getting the selected client from the client tableview.
        ClientProjectThing current_client= tblview_client.getSelectionModel().getSelectedItem();
        String query = "SELECT * FROM "
                + "harmonic_client WHERE customer = '" + current_client.getClient() + "' AND "
                + "project_name = '" + current_client.getProject() + "'";
        ResultSet rs_client = accessor.access_database(query);
        while (rs_client.next()) {
            clientID = rs_client.getInt("client_id");
            projectName = rs_client.getString("project_name");
            customer = rs_client.getString("customer");
            representative = rs_client.getString("representative");
            projectManager = rs_client.getString("project_manager");
            estimator = rs_client.getString("estimator");
            jobNotes = rs_client.getString("job_notes");
            privateNotes = rs_client.getString("private_notes");
            conflicts = rs_client.getString("conflicts");

        }
        //This Harmonic_Client object will be used in other classes.
        Harmonic_Client this_client = new Harmonic_Client(clientID,
                projectName, customer,
                representative, projectManager, estimator, jobNotes,
                privateNotes, conflicts);
        //Set Main.current_client to the selected client so it can be used in other classes.
        Main.current_client = this_client;

        ResultSet rs = accessor.access_database("SELECT * "
                +" FROM customer_labor WHERE customer_id = '" +clientID+"'");
        while (rs.next()) {
            lab_name = rs.getString("labor_name");

        }
        rs2 = accessor.access_database("SELECT * "
                +" FROM labor WHERE laborName = '" + lab_name +"'");
        while (rs2.next()) {
            String l_name = rs2.getString("laborName");
            double price = rs2.getDouble("price_per_hour");
            String description = rs2.getString("laborDesc");
            current_labor = new Labor(l_name, price, description);
            labor_array_list.add(current_labor);
        }
        tblview_labor_to_client.getItems().addAll(labor_array_list);

    }

    //This method is to the view the labor in the clients profile.
    @FXML
    void view_client_labor(MouseEvent event) throws SQLException {
        for(int i = 0; i<tblview_labor_to_client.getItems().size(); i++){
            tblview_labor_to_client.getItems().clear();
        }
        populate_customer_labor();

    }


    //This method adds a Material object to the database.
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
    void view_client_materials(MouseEvent event) throws SQLException {
        for(int i = 0; i<tblview_material_to_client.getItems().size(); i++){
            tblview_material_to_client.getItems().clear();
        }
        populate_customer_materials();
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

    @FXML
    void add_edit_client_materials(MouseEvent event) {
        Main.createNewScene(event, "Client_Materials_Screen.fxml");
    }
    @FXML
    void add_edit_client_labor(MouseEvent event) {
        Main.createNewScene(event, "Client_Labor_Screen.fxml");
    }

}
