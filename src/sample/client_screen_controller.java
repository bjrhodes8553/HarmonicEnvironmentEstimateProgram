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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import static java.awt.Color.getColor;
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
        String job_name = txtfield_job_name.getText();
        String customer = txtfield_customer.getText();
        String representative = txtarea_rep.getText();
        String project_manager = txtarea_proj_mgr.getText();
        String estimator = txtarea_estimator.getText();
        String job_notes = txtarea_job_notes.getText();
        String private_notes = txtarea_private_notes.getText();
        String conflicts = txtarea_conflicts.getText();
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
            Main.current_project.setClient_id(clientID);

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
        txtfield_job_name.clear();
        txtfield_customer.clear();
        txtarea_rep.clear();
        txtarea_proj_mgr.clear();
        txtarea_estimator.clear();
        txtarea_job_notes.clear();
        txtarea_private_notes.clear();
        txtarea_conflicts.clear();
        con.close();

        //Change Labels to green
        label_customer.setTextFill(Color.GREEN);
        label_rep.setTextFill(Color.GREEN);
        label_proj_mgr.setTextFill(Color.GREEN);
        label_estimator.setTextFill(Color.GREEN);
        label_job_name.setTextFill(Color.GREEN);
        label_job_notes.setTextFill(Color.GREEN);
        label_private_notes.setTextFill(Color.GREEN);
        label_conflicts.setTextFill(Color.GREEN);

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
        txtfield_project_due.appendText(Main.current_project.getDue_date());
    }

}
