package sample;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;


public class Quote_Controller implements Initializable {
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle){
    try {
      calculateQuote();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  public void calculateQuote() throws SQLException {
    int clientID = Main.current_client.getClient_id();
    Database_Accessor accessor = new Database_Accessor();
    ResultSet rs;
    String query;
    double quote = 0;
    Set<String> namesSet = new HashSet<String>();

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
        System.out.println(rs.getString("materialName") + " = $" + rs.getString("price") + " (x1)");
        quote += rs.getDouble("price");
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
        System.out.println(rs.getString("laborName") + " = $" + rs.getString(
            "price_per_hour") + " (x1)");
        quote += rs.getDouble("price_per_hour");
      }
    }

    // This is the final quote
    System.out.println("\nQUOTE: $" + quote);
  }

  @FXML
  void go_back(MouseEvent event){
    Main.createNewScene(event, "Existing_client_table_screen.fxml");
  }
}
