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

public class Labor_screen_controller {

    @FXML
    private Button btn_back;

    @FXML
    private TableView<Labor> tblview_labor;

    @FXML
    private TableColumn col_lab_name;

    @FXML
    private TableColumn col_lab_price;

    @FXML
    private Button btn_edit_desciption;

    @FXML
    private TextArea txtarea_description;

    @FXML
    private Button btn_update_labor;

    @FXML
    private TextField txtfield_edit_price;

    @FXML
    private Button btn_remove_labor;

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
        Labor current_labor = null;
        col_lab_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_lab_price.setCellValueFactory(new PropertyValueFactory<>("price"));
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
        tblview_labor.getItems().addAll(labor_array_list);
    }

    public void populate_description(String name) throws SQLException {
        txtarea_description.clear();
        String description = "";
        Labor current_labor = null;
        Database_Accessor accessor = new Database_Accessor();
        ResultSet rs = accessor.access_database("SELECT * FROM labor WHERE laborName = '"+ name + "'");
        while (rs.next()){
            String l_name = rs.getString("laborName");
            double price = rs.getDouble("price_per_hour");
            description = rs.getString("laborDesc");
            current_labor = new Labor(l_name, price, description);
        }
        Main.current_labor = current_labor;
        txtarea_description.appendText(description);
    }



    @FXML
    void edit_description(MouseEvent event) throws SQLException {
        Database_Accessor accessor = new Database_Accessor();
        String description = txtarea_description.getText();
        String name = tblview_labor.getSelectionModel().getSelectedItem().getName();
        accessor.update_database("UPDATE labor SET laborDesc = '" +
            description + "' WHERE laborName = '" + name +"'");
        txtarea_description.clear();

        // Old method wasn't working, but I was too scared to delete it, so
        // it's commented out
        /*        txtarea_description.clear();
        Database_Accessor accessor = new Database_Accessor();
        String description = txtarea_description.getText();
        accessor.update_database("UPDATE labor SET laborDesc = '" + description + "' WHERE laborName = '" + Main.current_labor.getName() + "'");
        populate_description(Main.current_labor.getName());*/
    }

    @FXML
    void go_back(MouseEvent event) {
        Main.createNewScene(event, "Existing_client_table_screen.fxml");
    }

    @FXML
    void remove_labor(MouseEvent event) {
        Database_Accessor accessor = new Database_Accessor();
        Labor current_labor = tblview_labor.getSelectionModel().getSelectedItem();
        String name = current_labor.getName();
        accessor.update_database("DELETE FROM labor"
                + " WHERE laborName = '" + name +"'");
        tblview_labor.getItems().remove(current_labor);

    }

    @FXML
    void update_labor(MouseEvent event) {

    }

    @FXML
    void view_desc(MouseEvent event) {
       Labor current_labor =  tblview_labor.getSelectionModel().getSelectedItem();
       String labor_name = current_labor.getName();
        try {
            populate_description(labor_name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
