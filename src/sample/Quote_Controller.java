package sample;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javax.swing.table.TableColumn;


public class Quote_Controller implements Initializable {
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle){
    try {
      calculateQuote();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  @FXML
  Label finalQuoteLabel;
  public void calculateQuote() throws SQLException {
    int clientID = Main.current_client.getClient_id();
    Database_Accessor accessor = new Database_Accessor();
    ResultSet rs;
    String query;
    double quote = 0;
    Set<String> namesSet = new HashSet<String>();
    // Used to pass object to populate_quote_table
    ArrayList<QuoteTableObjectThing> qtot = new ArrayList<>();

    // Select names of material within that client's project
    query =
        "SELECT material_name FROM customer_materials WHERE customer_id = '" + clientID + "'";
    rs = accessor.access_database(query);
    // Create set (unique, no repeating names) of the names of the materials
    while (rs.next()){
      namesSet.add(rs.getString("material_name"));
    }
    // Put these unique names into an array
    Object[] materialNames = namesSet.toArray();
    System.out.println("MATERIALS:");
    for (int i = 0; i < materialNames.length; i++){
      // Use these unique names to find the price of that material
      query =
          "SELECT * FROM materials WHERE materialName = '" + materialNames[i].toString() + "'";
      rs = accessor.access_database(query);
      // Add the prices to the quote, this will soon be multiplied by
      // quantity (or by hour for labor) once that functionality gets added
      while (rs.next()){
        Double price = rs.getDouble("price");
        String unit = rs.getString("unit");
        // REPLACE THIS WITH THE ACTUAL QUANTITY
        int quantity = 1;
        System.out.println(rs.getString("materialName") + " = $" + rs.getString("price") + " (x1)");
        quote += rs.getDouble("price");
        // Create obj and add to arraylist so it can be used in
        // populate_quote_table
        qtot.add(new QuoteTableObjectThing(materialNames[i].toString(), price, unit,
            quantity));
      }
    }

    // Same this as above, but for labor
    query =
        "SELECT labor_name FROM customer_labor WHERE customer_id = '" + clientID + "'";
    rs = accessor.access_database(query);
    while (rs.next()){
      namesSet.add(rs.getString("labor_name"));
    }
    Object[] laborNames = namesSet.toArray();
    System.out.println("\nLABOR:");
    for (int i = 0; i < laborNames.length; i++){
      query =
          "SELECT * FROM labor WHERE laborName = '" + laborNames[i].toString() + "'";
      rs = accessor.access_database(query);
      while (rs.next()){
        Double price = rs.getDouble("price_per_hour");
        String unit = "per hour";
        // REPLACE THIS WITH THE ACTUAL QUANTITY
        int quantity = 1;
        System.out.println(rs.getString("laborName") + " = $" + price + " (x1)");
        quote += price;
        qtot.add(new QuoteTableObjectThing(laborNames[i].toString(), price, unit,
            quantity));
      }
    }

    // This is the final quote
    System.out.println("\nQUOTE: $" + quote);
    populate_quote_table(qtot);
    // Set label at bottom to final quote
    finalQuoteLabel.setText("$" + String.valueOf(quote));
  }

  @FXML
  TableView<QuoteTableObjectThing> tblview_final_quote;
  @FXML
  javafx.scene.control.TableColumn<?, ?> colName;
  @FXML
  javafx.scene.control.TableColumn<?, ?> colPrice;
  @FXML
  javafx.scene.control.TableColumn<?, ?> colUnit;
  @FXML
  javafx.scene.control.TableColumn<?, ?> colQuantity;
  void populate_quote_table(ArrayList<QuoteTableObjectThing> arrayList){
    colName.setCellValueFactory(new PropertyValueFactory<>("name"));
    colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
    colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    tblview_final_quote.getItems().addAll(arrayList);
  }

  @FXML
  void go_back(MouseEvent event){
    Main.createNewScene(event, "Existing_client_table_screen.fxml");
  }
}
