package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Materials_screen_controller {

    @FXML
    private TableView<Material> tblview_materials;

    @FXML
    private TableColumn col_mat_name;

    @FXML
    private TableColumn col_mat_unit;

    @FXML
    private TableColumn col_mat_price;

    @FXML
    private Button btn_remove_material;

    @FXML
    private Button btn_edit_mat;

    @FXML
    private TextField txtfield_edit_unit;

    @FXML
    private TextField txtfield_edit_price;

    @FXML
    private TextArea txtarea_description;

    @FXML
    private Button btn_edit_description;

    @FXML
    private Button btn_back;
    @FXML
    private Button btn_view_desc;


    public void initialize() {

        try {
            fill_in_table();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void fill_in_table() throws SQLException {
        Material current_material = null;
        col_mat_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_mat_unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        col_mat_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        ArrayList <Material> material_array_list = new ArrayList<>();
        Database_Accessor accessor = new Database_Accessor();
        ResultSet rs = accessor.access_database("SELECT * "
                +" FROM materials");
        while (rs.next()){
            String name = rs.getString("name");
            String unit = rs.getString("unit");
            double price = rs.getDouble("price");
            String description = rs.getString("description");
            current_material = new Material(name, unit, price, description);
            Main.current_material = current_material;
            material_array_list.add(current_material);
        }
        tblview_materials.getItems().addAll(material_array_list);
    }

    public void populate_description(String name) throws SQLException {
        String description = "";
        Material current_material = null;
        Database_Accessor accessor = new Database_Accessor();
        ResultSet rs = accessor.access_database("SELECT * "
                +" FROM material WHERE name = '"
                + name);
        while (rs.next()){
            String m_name = rs.getString("name");
            String unit = rs.getString("unit");
            double price = rs.getDouble("price");
            description = rs.getString("description");
            current_material = new Material(m_name, unit, price, description);
        }
        Main.current_material = current_material;
        txtarea_description.appendText(description);
    }


    @FXML
    void edit_material(MouseEvent event) {


    }

    @FXML
    void edit_material_description(MouseEvent event) {
        Database_Accessor accessor = new Database_Accessor();
        String description = txtarea_description.getText();
        String name = tblview_materials.getSelectionModel().getSelectedItem().getName();
        accessor.update_database("UPDATE materials "
                + " SET description = '" + description +
                 "' WHERE name = '" + name);

    }

    @FXML
    void remove_material(MouseEvent event) {

    }

    @FXML
    void go_back(MouseEvent event){
        Main.createNewScene(event, "Existing_client_table_screen.fxml");

    }

    @FXML
    void view_desc(MouseEvent event) {
        Material current_material =  tblview_materials.getSelectionModel().getSelectedItem();
        String material_name = current_material.getName();
        try {
            populate_description(material_name);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
