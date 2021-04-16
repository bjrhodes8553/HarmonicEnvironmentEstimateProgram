package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Client_Materials_Controller {


    @FXML
    private TableView<Material> tblview_db_mat;

    @FXML
    private TableView<Material> tblview_client_mat;

    @FXML
    private TableColumn<?, ?> col_db_name;

    @FXML
    private TableColumn<?, ?> col_db_unit;

    @FXML
    private TableColumn<?, ?> col_db_price;

    @FXML
    private TableColumn<?, ?> col_client_name;

    @FXML
    private TableColumn<?, ?> col_client_unit;

    @FXML
    private TableColumn<?, ?> col_client_price;

    @FXML
    private TableColumn<?, ?> col_material_quantity;

    @FXML
    private Button btn_add_client_mat;

    @FXML
    private Button btn_remove_mat;

    @FXML
    private Button btn_back;

    @FXML
    private TextField txtfield_quantity;


    @FXML
    void initialize() throws SQLException {
        fill_in_customer_materials();
        fill_in_db_table();
    }

    @FXML
    void add_material_to_client(MouseEvent event) throws SQLException {
        int cust_id = Main.current_client.getClient_id();
        String mat_name = tblview_db_mat.getSelectionModel().getSelectedItem().getName();
        double mat_quantity = Double.parseDouble(txtfield_quantity.getText());
        Database_Accessor accessor = new Database_Accessor();
        accessor.update_database("INSERT INTO customer_materials(customer_id, "
                + "material_name, quantity) VALUES ('"
                +cust_id+"', '"+mat_name+"', '"+mat_quantity+"')");
        for(int i = 0; i<tblview_client_mat.getItems().size(); i++){
            tblview_client_mat.getItems().clear();
        }
        fill_in_customer_materials();

    }

    @FXML
    void go_back(MouseEvent event) {
        Main.createNewScene(event, "Existing_client_table_screen.fxml");

    }

    //Removes the material from the clients database and tableview.
    @FXML
    void remove_mat(MouseEvent event) {
        Database_Accessor accessor = new Database_Accessor();
        Material current_material = tblview_client_mat.getSelectionModel().getSelectedItem();
        String name = current_material.getName();
        accessor.update_database("DELETE FROM customer_materials"
                + " WHERE material_name = '" + name +"'");
        tblview_client_mat.getItems().remove(current_material);

    }

    public void fill_in_db_table() throws SQLException {
        /*
        This method populates the table view that
        will display the current materials in the database.
         */
        Material current_material = null;
        col_db_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_db_unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        col_db_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        ArrayList<Material> material_array_list = new ArrayList<>();
        Database_Accessor accessor = new Database_Accessor();
        ResultSet rs = accessor.access_database("SELECT * "
                +" FROM materials");
        while (rs.next()){
            String name = rs.getString("materialName");
            String unit = rs.getString("unit");
            double price = rs.getDouble("price");
            String description = rs.getString("materialDesc");
            current_material = new Material(name, unit, price, description);

            Main.current_material = current_material;
            material_array_list.add(current_material);
        }
        tblview_db_mat.getItems().addAll(material_array_list);
    }

    void fill_in_customer_materials() throws SQLException {
        /*
        This method populates the tableview that will display
        all the materials stored in the database that are
        attached to the clients profile.
         */
        Material current_material = null;
        String mat_name = "";
        double mat_quantity = 0;
        double mat_price = 0;
        String mat_unit = "";


        int clientID = Main.current_client.getClient_id();
        col_client_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_client_unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        col_client_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_material_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        ArrayList <Material> material_array_list = new ArrayList<>();
        Database_Accessor accessor = new Database_Accessor();;
        clientID = Main.current_client.getClient_id();

        // This query will retrieve the material names associated with the client.
        ResultSet rs = accessor.access_database("SELECT * "
                +" FROM customer_materials cm " +
                "INNER JOIN materials m ON cm.material_name= m.materialName " +
                "WHERE customer_id = '" +clientID+"'");
        while (rs.next()) {
            mat_name = rs.getString("material_name");
            mat_quantity = rs.getDouble("quantity");
            mat_price = rs.getDouble("price");
            mat_unit = rs.getString("unit");

            current_material = new Material(mat_name, mat_unit, mat_price, mat_quantity);
            material_array_list.add(current_material);
        }
        tblview_client_mat.getItems().addAll(material_array_list);

    }

}
