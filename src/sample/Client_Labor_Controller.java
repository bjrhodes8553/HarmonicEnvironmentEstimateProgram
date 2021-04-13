package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Client_Labor_Controller {

    @FXML
    private TableView<Labor> tbllview_db_labor;

    @FXML
    private TableColumn<?, ?> col_db_name;

    @FXML
    private TableColumn<?, ?> col_db_price;

    @FXML
    private TableView<Labor> tblview_client_labor;

    @FXML
    private TableColumn<?, ?> col_client_name;

    @FXML
    private TableColumn<?, ?> col_client_price;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_add_client_labor;

    @FXML
    private Button btn_remove_client_labor;

    @FXML
    void initialize() throws SQLException {
        populate_labor_db_table();
        populate_customer_labor();

    }

    @FXML
    void add_client_labor(MouseEvent event) throws SQLException {
        int cust_id = Main.current_client.getClient_id();
        String lab_name = tbllview_db_labor.getSelectionModel().getSelectedItem().getName();
        Database_Accessor accessor = new Database_Accessor();
        accessor.update_database("INSERT INTO customer_labor(customer_id, "
                + "labor_name) VALUES ('"
                +cust_id+"', '"+lab_name+"')");
        for(int i = 0; i<tblview_client_labor.getItems().size(); i++){
            tblview_client_labor.getItems().clear();
        }
        populate_customer_labor();

    }

    @FXML
    void go_back(MouseEvent event) {
        Main.createNewScene(event, "Existing_client_table_screen.fxml");

    }

    @FXML
    void remove_client_labor(MouseEvent event) {
        Database_Accessor accessor = new Database_Accessor();
        Labor current_labor = tblview_client_labor.getSelectionModel().getSelectedItem();
        String name = current_labor.getName();
        accessor.update_database("DELETE FROM customer_labor"
                + " WHERE labor_name = '" + name +"'");
        tblview_client_labor.getItems().remove(current_labor);

    }

    void populate_customer_labor() throws SQLException {
        Labor current_labor = null;
        String lab_name = "";
        ResultSet rs2 = null;
        int clientID = Main.current_client.getClient_id();
        String projectName = "", customer = "", representative = "",
                projectManager = "", estimator = "", jobNotes = "",
                privateNotes = "", conflicts = "";

        //Set up the columns for the tableview with correct variables.
        col_client_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_client_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        ArrayList<Labor> labor_array_list = new ArrayList<>();
        Database_Accessor accessor = new Database_Accessor();

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
        tblview_client_labor.getItems().addAll(labor_array_list);
    }



    public void populate_labor_db_table() throws SQLException {
        Labor current_labor = null;
        col_db_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_db_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        ArrayList <Labor> labor_array_list = new ArrayList<>();
        Database_Accessor accessor = new Database_Accessor();
        ResultSet rs = accessor.access_database("SELECT * "
                +" FROM labor");
        while (rs.next()){
            String name = rs.getString("laborName");
            double price = rs.getDouble("price_per_hour");
            String description = rs.getString("laborDesc");
            current_labor = new Labor(name, price, description);
            Main.current_labor = current_labor;
            labor_array_list.add(current_labor);
        }
        tbllview_db_labor.getItems().addAll(labor_array_list);
    }

}
