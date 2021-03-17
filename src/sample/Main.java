package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static Harmonic_Client current_client;
    public static Project current_project;
    public static Quote current_quote;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login_screen.fxml"));
        primaryStage.setTitle("Harmonic Environment Estimator");
        primaryStage.setScene(new Scene(root, 710, 620));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void createNewScene(Event event, String newFileFXML){
     Parent newRoot = null;
        try {
            newRoot = FXMLLoader.load(Main.class.getResource(newFileFXML));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene newScene = new Scene(newRoot);
       // newScene.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(newScene);
        window.show();
}


}
