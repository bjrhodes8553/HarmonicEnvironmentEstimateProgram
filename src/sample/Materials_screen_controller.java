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
            String name = rs.getString("materialName");
            String unit = rs.getString("unit");
            double price = rs.getDouble("price");
            String description = rs.getString("materialDesc");
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
                +" FROM materials WHERE materialName = '" + name +"'");
        while (rs.next()){
            String m_name = rs.getString("materialName");
            String unit = rs.getString("unit");
            double price = rs.getDouble("price");
            description = rs.getString("materialDesc");
            current_material = new Material(m_name, unit, price, description);
            Main.current_material = current_material;
        }

        txtarea_description.appendText(Main.current_material.getDescription());
    }


    @FXML
    void edit_material(MouseEvent event) throws SQLException {
        Database_Accessor accessor = new Database_Accessor();
        String edit_unit = txtfield_edit_unit.getText();
        double edit_price = Double.parseDouble(txtfield_edit_price.getText());
        String edit_name = tblview_materials.getSelectionModel().getSelectedItem().getName();
        accessor.update_database(
                "UPDATE materials "
                +"SET unit = '" + edit_unit + "', "
                +"price = '" + edit_price + "'"
               + " WHERE materialName = '" + edit_name+ "'");
        txtfield_edit_unit.clear();
        txtfield_edit_price.clear();
        for(int i = 0; i<tblview_materials.getItems().size(); i++){
            tblview_materials.getItems().clear();
        }
        fill_in_table();

    }

    @FXML
    void edit_material_description(MouseEvent event) throws SQLException {
        Database_Accessor accessor = new Database_Accessor();
        String description = txtarea_description.getText();
        String edit_name = tblview_materials.getSelectionModel().getSelectedItem().getName();
        accessor.update_database("UPDATE materials SET materialDesc = '" +
                description + "' WHERE materialName = '" + edit_name +"'");
        txtarea_description.clear();


    }

    @FXML
    void remove_material(MouseEvent event) {
        Database_Accessor accessor = new Database_Accessor();
        Material current_material = tblview_materials.getSelectionModel().getSelectedItem();
        String name = current_material.getName();
        accessor.update_database("DELETE FROM materials"
                + " WHERE materialName = '" + name +"'");
        tblview_materials.getItems().remove(current_material);


    }

    @FXML
    void go_back(MouseEvent event){
        Main.createNewScene(event, "Existing_client_table_screen.fxml");

    }

    @FXML
    void view_desc(MouseEvent event) {
        txtarea_description.clear();
        Material current_material =  tblview_materials.getSelectionModel().getSelectedItem();
        String material_name = current_material.getName();
        try {
            populate_description(material_name);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
