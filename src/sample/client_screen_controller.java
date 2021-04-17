package sample;

import java.applet.Applet;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.sql.*;
import static java.lang.Double.parseDouble;

public class client_screen_controller implements Initializable {

    Connection con;
    PreparedStatement pst;
    String testID;
    Harmonic_Client current_client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        choicebox_quote_type.setItems(FXCollections.observableArrayList(
            "Type A", "Type B", "Type C"
        ));
        choicebox_quote_status.setItems(FXCollections.observableArrayList(
            "On Time", "Delayed", "Ahead of Time", "Pending"
        ));

        // If current_client's name is of length 0 that means they chose "New
        // Client"
        // If they chose "New Client" then don't populate the textfields
        if (Main.current_client.getCustomer().length() > 0) {
            current_client = Main.current_client;
            txtfield_job_name.setText(current_client.getJob_name());
            txtfield_customer.setText(current_client.getCustomer());
            txtarea_rep.setText(current_client.getRepresentative());
            txtarea_proj_mgr.setText(current_client.getProject_manager());
            txtarea_estimator.setText(current_client.getEstimator());
            txtarea_job_notes.setText(current_client.getJob_notes());
            txtarea_private_notes.setText(current_client.getPrivate_notes());
            txtarea_conflicts.setText(current_client.getConflicts());
            // disable add new client button, since they're updating not adding
            button_add_client.setDisable(true);
        }
        else {
            // disable update client button, since they're adding not updating
            button_update_client.setDisable(true);
        }
    }

    @FXML
    private ChoiceBox choicebox_quote_type;

    @FXML
    private ChoiceBox choicebox_quote_status;

    @FXML
    private TextArea txtarea_status_reason;

    @FXML
    private Label label_job_name;

    @FXML
    private TextField txtfield_job_name;

    @FXML
    private Label label_customer;

    @FXML
    private TextArea txtfield_customer;

    @FXML
    private TextArea txtarea_rep;

    @FXML
    private Label label_rep;

    @FXML
    private Label label_proj_mgr;

    @FXML
    private TextArea txtarea_proj_mgr;

    @FXML
    private Label label_estimator;

    @FXML
    private Label label_job_notes;

    @FXML
    private Label label_conflicts;

    @FXML
    private TextArea txtarea_estimator;

    @FXML
    private TextArea txtarea_job_notes;

    @FXML
    private TextArea txtarea_conflicts;

    @FXML
    private Label label_private_notes;

    @FXML
    private TextArea txtarea_private_notes;

    @FXML
    private Button button_update_client;

    @FXML Button button_add_client;

    @FXML Button btn_go_back;

    @FXML
    private Label label_original_quote_date;

    @FXML
    private DatePicker datepicker_revision_date;

    @FXML
    private TextField txtfield_revision_number;

    @FXML
    private Button button_revision;

    @FXML
    private TextArea txtfield_project_due;

    @FXML
    private Button button_update_due_date;

    @FXML
    public Text client_info_text;

    @FXML
    public Text job_name_error_text;

    @FXML
    public Text customer_error_text;

    @FXML
    public Text rep_error_text;

    @FXML
    public Text proj_mgr_error_text;

    @FXML
    public Text estimator_error_text;

    @FXML
    public Text job_notes_error_text;

    @FXML
    public Text pvt_notes_error_text;

    @FXML
    public Text conflicts_error_text;

    @FXML
    public Text dueDate_update_text;

    @FXML
    public Text quote_text;

    @FXML
    void add_revision(MouseEvent event) {
        label_original_quote_date.setText("03/15/2019");
    }


    @FXML
    void add_client(MouseEvent event) throws ClassNotFoundException, SQLException {
        String job_name = "";
        String customer = "";
        String representative = "";
        String project_manager = "";
        String estimator = "";
        String job_notes = "";
        String private_notes = "";
        String conflicts = "";
        Boolean[] booleanArray = new Boolean[8];
        Boolean isAFieldEmpty = true;


        if(txtfield_job_name.getText().equals("")){
            job_name_error_text.setText("Please fill in Job Name");
            booleanArray[0] = true;
        }
        else {
            job_name = txtfield_job_name.getText();
            label_job_name.setTextFill(Color.GREEN);
            booleanArray[0] = false;
            job_name_error_text.setText("");
        }
        if(txtfield_customer.getText().equals("")){
            customer_error_text.setText("Please fill in Customer");
            booleanArray[1] = true;
        }
        else{
            customer = txtfield_customer.getText();
            label_customer.setTextFill(Color.GREEN);
            booleanArray[1] = false;
            customer_error_text.setText("");
        }
        if(txtarea_rep.getText().equals("")){
            rep_error_text.setText("Please fill in Representative");
            booleanArray[2] = true;
        }
        else{
            representative = txtarea_rep.getText();
            label_rep.setTextFill(Color.GREEN);
            booleanArray[2] = false;
            rep_error_text.setText("");
        }
        if(txtarea_proj_mgr.getText().equals("")){
            proj_mgr_error_text.setText("Please fill in Project Manager");
            booleanArray[3] = true;
        }
        else{
            project_manager = txtarea_proj_mgr.getText();
            label_proj_mgr.setTextFill(Color.GREEN);
            booleanArray[3] = false;
            proj_mgr_error_text.setText("");
        }
        if(txtarea_estimator.getText().equals("")){
            estimator_error_text.setText("Please fill in Estimator");
            booleanArray[4] = true;
        }
        else{
            estimator = txtarea_estimator.getText();
            label_estimator.setTextFill(Color.GREEN);
            booleanArray[4] = false;
            estimator_error_text.setText("");
        }
        if(txtarea_job_notes.getText().equals("")){
            job_notes_error_text.setText("Please fill in Job Notes");
            booleanArray[5] = true;
        }
        else{
            job_notes = txtarea_job_notes.getText();
            label_job_notes.setTextFill(Color.GREEN);
            booleanArray[5] = false;
            job_notes_error_text.setText("");
        }
        if(txtarea_private_notes.getText().equals("")){
            pvt_notes_error_text.setText("Please fill in Private Notes");
            booleanArray[6] = true;
        }
        else{
            private_notes = txtarea_private_notes.getText();
            label_private_notes.setTextFill(Color.GREEN);
            booleanArray[6] = false;
            pvt_notes_error_text.setText("");
        }
        if(txtarea_conflicts.getText().equals("")){
            conflicts_error_text.setText("Please fill in Conflicts");
            booleanArray[7] = true;
        }
        else{
            conflicts = txtarea_conflicts.getText();
            label_conflicts.setTextFill(Color.GREEN);
            booleanArray[7] = false;
            conflicts_error_text.setText("");
        }

        // Check if any textfields are empty, if one is empty then boolean=true
        for (int i = 0; i < booleanArray.length; i++){
            if (booleanArray[i]){
                isAFieldEmpty = true;
                break;
            }
            else {
                isAFieldEmpty = false;
            }
        }

        if (!isAFieldEmpty) {
            int client_id = 0;
            Main.current_client = new Harmonic_Client(
                client_id,
                job_name,
                customer,
                representative,
                project_manager,
                estimator,
                job_notes,
                private_notes,
                conflicts);

            try {

                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/harmonic_environment", "root", "");
                pst = con.prepareStatement("INSERT harmonic_client set " +
                    "project_name = ?," +
                    "customer = ?," +
                    "representative = ?," +
                    "project_manager = ?," +
                    "estimator= ?," +
                    "job_notes = ?," +
                    "private_notes = ?," +
                    "conflicts = ?");
                Statement s = con.createStatement();
                ResultSet rs = s
                    .executeQuery("select MAX(client_id) from harmonic_client");
                rs.next();
                rs.getString("MAX(client_id)");
                if (rs.getString("MAX(client_id)") == null) {
                    Main.current_client.setClient_id(0);
                } else {
                    Main.current_client
                        .setClient_id(rs.getInt("MAX(client_id)") + 1);
                }
                int clientID = Main.current_client.getClient_id();

                pst.setString(1, job_name);
                pst.setString(2, customer);
                pst.setString(3, representative);
                pst.setString(4, project_manager);
                pst.setString(5, estimator);
                pst.setString(6, job_notes);
                pst.setString(7, private_notes);
                pst.setString(8, conflicts);

                pst.executeUpdate();

            } catch (ClassNotFoundException | SQLException ex) {
                System.out.println(ex);
            }
            //Clear text fields and text areas after update button pressed

            con.close();

            //Repopulate text areas with information and an updated message

            txtfield_job_name.clear();
            txtfield_customer.clear();
            txtarea_rep.clear();
            txtarea_proj_mgr.clear();
            txtarea_estimator.clear();
            txtarea_job_notes.clear();
            txtarea_private_notes.clear();
            txtarea_conflicts.clear();
            job_name_error_text.setText("");
            customer_error_text.setText("");
            rep_error_text.setText("");
            proj_mgr_error_text.setText("");
            estimator_error_text.setText("");
            job_notes_error_text.setText("");
            pvt_notes_error_text.setText("");
            conflicts_error_text.setText("");
            client_info_text.setText("Added!");
        }
        else {
            System.out.println("A textfield is empty, new client not added");
        }
    }

    @FXML
    void update_client(MouseEvent event) throws ClassNotFoundException,
        SQLException {
        // Commented out because user already knows what client they're on
        // Also because they don't need to know the ID of their client since
        // that's exclusively used for database indexing reasons
        //curr_client_text.setText("Current client: " + Main.current_client
        // .getCustomer());
        //.setText("Client ID: " + Main.current_client.getClient_id());
        String job_name = "";
        String customer = "";
        String representative = "";
        String project_manager = "";
        String estimator = "";
        String job_notes = "";
        String private_notes = "";
        String conflicts = "";
        Boolean[] booleanArray = new Boolean[8];
        Boolean isAFieldEmpty = true;

        // Get info from textfields
        if (txtfield_job_name.getText().equals("")) {
            job_name_error_text.setText("Please fill in Job Name");
            booleanArray[0] = true;
        } else {
            job_name = txtfield_job_name.getText();
            label_job_name.setTextFill(Color.GREEN);
            booleanArray[0] = false;
            job_name_error_text.setText("");
        }
        if (txtfield_customer.getText().equals("")) {
            customer_error_text.setText("Please fill in Customer");
            booleanArray[1] = true;
        } else {
            customer = txtfield_customer.getText();
            label_customer.setTextFill(Color.GREEN);
            booleanArray[1] = false;
            customer_error_text.setText("");
        }
        if (txtarea_rep.getText().equals("")) {
            rep_error_text.setText("Please fill in Representative");
            booleanArray[2] = true;
        } else {
            representative = txtarea_rep.getText();
            label_rep.setTextFill(Color.GREEN);
            booleanArray[2] = false;
            rep_error_text.setText("");
        }
        if (txtarea_proj_mgr.getText().equals("")) {
            proj_mgr_error_text.setText("Please fill in Project Manager");
            booleanArray[3] = true;
        } else {
            project_manager = txtarea_proj_mgr.getText();
            label_proj_mgr.setTextFill(Color.GREEN);
            booleanArray[3] = false;
            proj_mgr_error_text.setText("");
        }
        if (txtarea_estimator.getText().equals("")) {
            estimator_error_text.setText("Please fill in Estimator");
            booleanArray[4] = true;
        } else {
            estimator = txtarea_estimator.getText();
            label_estimator.setTextFill(Color.GREEN);
            booleanArray[4] = false;
            estimator_error_text.setText("");
        }
        if (txtarea_job_notes.getText().equals("")) {
            job_notes_error_text.setText("Please fill in Job Notes");
            booleanArray[5] = true;
        } else {
            job_notes = txtarea_job_notes.getText();
            label_job_notes.setTextFill(Color.GREEN);
            booleanArray[5] = false;
            job_notes_error_text.setText("");
        }
        if (txtarea_private_notes.getText().equals("")) {
            pvt_notes_error_text.setText("Please fill in Private Notes");
            booleanArray[6] = true;
        } else {
            private_notes = txtarea_private_notes.getText();
            label_private_notes.setTextFill(Color.GREEN);
            booleanArray[6] = false;
            pvt_notes_error_text.setText("");
        }
        if (txtarea_conflicts.getText().equals("")) {
            conflicts_error_text.setText("Please fill in Conflicts");
            booleanArray[7] = true;
        } else {
            conflicts = txtarea_conflicts.getText();
            label_conflicts.setTextFill(Color.GREEN);
            booleanArray[7] = false;
            conflicts_error_text.setText("");
        }

        // Check if any textfields are empty, if one is empty then boolean=true
        for (int i = 0; i < booleanArray.length; i++) {
            if (booleanArray[i]) {
                isAFieldEmpty = true;
                break;
            } else {
                isAFieldEmpty = false;
            }
        }

        if (!isAFieldEmpty) {
            // Update
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager
                    .getConnection(
                        "jdbc:mysql://localhost/harmonic_environment",
                        "root", "");
                pst = con.prepareStatement("UPDATE harmonic_client set " +
                    "project_name = ?," +
                    "customer = ?," +
                    "representative = ?," +
                    "project_manager = ?," +
                    "estimator= ?," +
                    "job_notes = ?," +
                    "private_notes = ?," +
                    "conflicts = ? " +
                    "WHERE client_id = " + Main.current_client.getClient_id());

                Statement s = con.createStatement();

                pst.setString(1, job_name);
                pst.setString(2, customer);
                pst.setString(3, representative);
                pst.setString(4, project_manager);
                pst.setString(5, estimator);
                pst.setString(6, job_notes);
                pst.setString(7, private_notes);
                pst.setString(8, conflicts);
                pst.execute();
            } catch (ClassNotFoundException | SQLException ex) {
                System.out.println(ex);
            }
            //Clear text fields and text areas after update button pressed

            con.close();

            //Repopulate text areas with information and an updated message

            job_name_error_text.setText("");
            customer_error_text.setText("");
            rep_error_text.setText("");
            proj_mgr_error_text.setText("");
            estimator_error_text.setText("");
            job_notes_error_text.setText("");
            pvt_notes_error_text.setText("");
            conflicts_error_text.setText("");
            client_info_text.setText("Updated!");
        }
        else {
            System.out.println("A textfield is empty, new client not added");
        }


    }

    @FXML
    void go_back(MouseEvent event){
        Main.createNewScene(event, "Existing_client_table_screen.fxml");
    }

    void getProject(){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/harmonic_environment", "root", "");

            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM project" +
                    " WHERE client_id ="+Main.current_client.getClient_id());
            while(rs.next()){
                int proj_id = rs.getInt(0);
                int client_id = rs.getInt(1);
                double room_sqft = parseDouble(rs.getString(2));
                double room_height = parseDouble(rs.getString(3));
                double room_volume = parseDouble(rs.getString(4));
                String due_date = rs.getString(5);
                String revision_date = rs.getString(6);
                int revision_num = rs.getInt(7);

                Project current_project = new Project(
                        proj_id,
                        client_id,
                        room_sqft,
                        room_height,
                        room_volume,
                        due_date,
                        revision_date,
                        revision_num);
                Main.current_project = current_project;
            }


        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }
    }




    @FXML
    void update_due_date(MouseEvent event) {

        if(txtfield_project_due.getText().equals("")){
            dueDate_update_text.setText("Please Enter Due Date");
        }else {
            String new_due_date = txtfield_project_due.getText();
            dueDate_update_text.setText("Date Updated");

            try {

                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost/harmonic_environment", "root", "");
                pst = con.prepareStatement("UPDATE project set " +
                        "due_date = ? WHERE client_id =?");
                pst.setString(1, new_due_date);
                pst.setInt(2, 1);
                pst.executeUpdate();

            } catch (ClassNotFoundException | SQLException ex) {
                System.out.println(ex);
            }
        }
    }

    @FXML
    void update_shipment(MouseEvent event) {


    }
    void initialize(){

        try {

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/harmonic_environment", "root", "");

            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM harmonic_client" +
                    " WHERE client_id ="+Main.current_client.getClient_id());
            while(rs.next()) {
                String project_name = rs.getString(1);
                String customer = rs.getString(2);
                String representative = rs.getString(3);
                String project_manager = rs.getString(4);
                String estimator = rs.getString(5);
                String job_notes = rs.getString(6);
                String private_notes = rs.getString(7);
                String conflicts = rs.getString(8);


                Harmonic_Client current_client = new Harmonic_Client(
                        Main.current_client.getClient_id(),
                        project_name,
                        customer,
                        representative,
                        project_manager,
                        estimator,
                        job_notes,
                        private_notes,
                        conflicts);

                txtfield_job_name.setPromptText(project_name);
                txtfield_customer.appendText(customer);
                txtarea_rep.appendText(representative);
                txtarea_proj_mgr.appendText(project_manager);
                txtarea_estimator.appendText(estimator);
                txtarea_job_notes.appendText(job_notes);
                txtarea_private_notes.appendText(private_notes);
                txtarea_conflicts.appendText(conflicts);

            }

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }

        txtfield_project_due.appendText(Main.current_project.getDue_date()


        );
    }

    public void update_quote(MouseEvent event) {
        String quoteType =
            choicebox_quote_type.getSelectionModel().getSelectedItem().toString();
        String quoteStatus =
            choicebox_quote_status.getSelectionModel().getSelectedItem().toString();
        String statusReason = txtarea_status_reason.getText();

        quote_text.setText("Quote Status Updated!");

        // TO-DO Get the update button to work!
        // Issue: project_id doesn't have default value. Quote table also
        // need a project_id column to be associated with a specific project
/*        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/harmonic_environment", "root", "");
            pst = con.prepareStatement("INSERT quote set " +
                "project_id = ?," +
                "type = ?," +
                "status = ?," +
                "status_reason = ?");
*//*          Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select MAX(client_id) from harmonic_client");
            rs.next();
            rs.getString("MAX(client_id)");
            if (rs.getString("MAX(client_id)") == null) {
                Main.current_client.setClient_id(0);
            } else {
                Main.current_client.setClient_id(rs.getInt("MAX(client_id)")+1);
            }
            int clientID = Main.current_client.getClient_id();*//*

            pst.setString(1, GETPROJECT ID SOMEHOW);
            pst.setString(2, quoteType);
            pst.setString(3, quoteStatus);
            pst.setString(4, statusReason);

            pst.executeUpdate();

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }*/
    }
}
