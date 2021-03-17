package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.sql.*;
import static java.lang.Double.parseDouble;

public class client_screen_controller {
    Connection con;
    PreparedStatement pst;
    String testID;

    @FXML
    private ChoiceBox<?> choicebox_quote_type;

    @FXML
    private ChoiceBox<?> choicebox_quote_status;

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
    private Label label_estimated_weight;

    @FXML
    private TextField txtfield_additional_weight;

    @FXML
    private TextField txtfield_shipping_zip;

    @FXML
    private ChoiceBox<?> choicebox_freight_company;

    @FXML
    private TextArea txtarea_freight_cost;

    @FXML
    private TextArea txtarea_freight_sell;

    @FXML
    private TextField txtfield_num_of_shipments;

    @FXML
    private CheckBox checkbox_full_truck;

    @FXML
    private CheckBox checkbox_dedicated;

    @FXML
    private Button button_add_weight;

    @FXML
    private Button button_update_shipment;

    @FXML
    private TextField txtfield_sqft;

    @FXML
    private TextField txtfield_height;

    @FXML
    private Button button_gen_volume;

    @FXML
    private Label label_cubic_feet;

    @FXML
    void add_additional_weight(MouseEvent event) {

    }

    @FXML
    void add_revision(MouseEvent event) {
        label_original_quote_date.setText("03/15/2019");


    }

    @FXML
    void generate_volume(MouseEvent event) {
        label_cubic_feet.setText("1000");

    }

    @FXML
    void update_client(MouseEvent event) throws ClassNotFoundException, SQLException {
        String job_name = "";
        String customer = "";
        String representative = "";
        String project_manager = "";
        String estimator = "";
        String job_notes = "";
        String private_notes = "";
        String conflicts = "";


        if(txtfield_job_name.getText()!=null){
            job_name = txtfield_job_name.getText();
            txtfield_job_name.clear();
            label_job_name.setTextFill(Color.GREEN);
        }
        else {
            System.out.println("Job Name was not updated");
        }
        if(txtfield_customer.getText() != null){
            customer = txtfield_customer.getText();
            txtfield_customer.clear();
            label_customer.setTextFill(Color.GREEN);
        }
        else{
            System.out.println("Customer was not updated");
        }
        if(txtarea_rep.getText() != null){
            representative = txtarea_rep.getText();
            txtarea_rep.clear();
            label_rep.setTextFill(Color.GREEN);
        }
        else{
            System.out.println("Representative was not updated");
        }
        if(txtarea_proj_mgr.getText() != null){
            project_manager = txtarea_proj_mgr.getText();
            txtarea_proj_mgr.clear();
            label_proj_mgr.setTextFill(Color.GREEN);
        }
        else{
            System.out.println("Project manager was not updated");
        }
        if(txtarea_estimator.getText()!=null){
            estimator = txtarea_estimator.getText();
            txtarea_estimator.clear();
            label_estimator.setTextFill(Color.GREEN);
        }
        else{
            System.out.println("Estimator was not updated");
        }
        if(txtarea_job_notes.getText() != null){
            job_notes = txtarea_job_notes.getText();
            txtarea_job_notes.clear();
            label_job_notes.setTextFill(Color.GREEN);
        }
        else{
            System.out.println("Job notes were not updated");
        }
        if(txtarea_private_notes.getText() != null){
            private_notes = txtarea_private_notes.getText();
            txtarea_private_notes.clear();
            label_private_notes.setTextFill(Color.GREEN);
        }
        else{
            System.out.println("Private notes were not updated");
        }
        if(txtarea_conflicts.getText() != null){
            conflicts = txtarea_conflicts.getText();
            txtarea_conflicts.clear();
            label_conflicts.setTextFill(Color.GREEN);

        }
        else{
            System.out.println("Conflicts were not updated");
        }




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
            con = DriverManager.getConnection("jdbc:mysql://localhost/harmonic_environment", "root", "");
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
            ResultSet rs = s.executeQuery("select MAX(client_id) from harmonic_client");
            rs.next();
            rs.getString("MAX(client_id)");
            if (rs.getString("MAX(client_id)") == null) {
                Main.current_client.setClient_id(0);
            } else {
                Main.current_client.setClient_id(rs.getInt("MAX(client_id)")+1);
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
        txtfield_job_name.setPromptText(job_name);
        txtfield_customer.appendText(customer);
        txtarea_rep.appendText(representative);
        txtarea_proj_mgr.appendText(project_manager);
        txtarea_estimator.appendText(estimator);
        txtarea_job_notes.appendText(job_notes);
        txtarea_private_notes.appendText(private_notes);
        txtarea_conflicts.appendText(conflicts);


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

        String new_due_date = txtfield_project_due.getText();

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

}
