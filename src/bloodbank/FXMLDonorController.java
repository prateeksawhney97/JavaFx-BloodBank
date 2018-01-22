package bloodbank;

import com.github.sarxos.webcam.Webcam;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.imageio.ImageIO;

public class FXMLDonorController implements Initializable {

    @FXML
    private TextField tDID;
    @FXML
    private TextField tName;
    @FXML
    private TextField tContact;
    @FXML
    private TextField tEmail;
    @FXML
    private RadioButton rMale;
    @FXML
    private ToggleGroup gender;
    @FXML
    private RadioButton rFemale;
    @FXML
    private DatePicker dDOB;
    @FXML
    private TextArea tAddress;
    @FXML
    private ListView<String> lvCity;
    @FXML
    private Button bSave;
    @FXML
    private Button bDelete;
    @FXML
    private Button bLoadData;
    @FXML
    private Button bNewEntry;
    @FXML
    private TableColumn<Donors, String> cDID;
    @FXML
    private TableColumn<Donors, String> cName;
    @FXML
    private TableColumn<Donors, String> cGender;
    @FXML
    private TableColumn<Donors, String> cDOB;
    @FXML
    private TableColumn<Donors, String> cContact;
    @FXML
    private TableColumn<Donors, String> cEmail;
    @FXML
    private TableColumn<Donors, String> cAddress;
    @FXML
    private TableColumn<Donors, String> cCity;

    ObservableList<Donors> data;
    @FXML
    private TableView<Donors> tDonors;
    @FXML
    private Button bUpdate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data = FXCollections.observableArrayList();
        lvCity.getItems().addAll("Panipat", "Karnal", "Sonipat", "Chandigarh", "Delhi", "Gurugram", "Ambala");
        cDID.setCellValueFactory(new PropertyValueFactory<>("DID"));
        cName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        cGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        cDOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        cContact.setCellValueFactory(new PropertyValueFactory<>("Contactno"));
        cEmail.setCellValueFactory(new PropertyValueFactory<>("Emailid"));
        cAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        cCity.setCellValueFactory(new PropertyValueFactory<>("City"));

    }

    @FXML
    private void save(ActionEvent event) {
        if (tDID.getText().isEmpty()) {
            Data.showMessage("Donor Id can't be left blank");
            return;
        }
        if (!Data.isValidContactNo(tContact.getText())) {
            Data.showMessage("Enter Valid Contact No.");
            return;
        }
        if (!Data.isValidEmailId(tEmail.getText())) {
            Data.showMessage("Enter valid email Id");
            return;
        }
        if (!rMale.isSelected() && !rFemale.isSelected()) {
            Data.showMessage("Please Select either Male or Female!");
            return;
        }
        if (tName.getText().isEmpty()) {
            Data.showMessage("Please Enter Name Of Donor");
            return;
        }
        if (tAddress.getText().isEmpty()) {
            Data.showMessage("Address cannot be left blank");
            return;
        }
        if (dDOB.getEditor().getText().isEmpty()) {
            Data.showMessage("Please enter Date Of Birth");
            return;
        }
        /*if(dDOB.getValue().format(DateTimeFormatter.ISO_DATE).isEmpty()){
            Data.showMessage("Please enter Date Of Birth");
            return;
        }*/
        try {
            Connection con = Data.getConnection();
            PreparedStatement ps = con.prepareStatement("Insert into donor values(?,?,?,?,?,?,?,?)");
            ps.setString(1, tDID.getText());
            ps.setString(2, tName.getText());
            if (rMale.isSelected()) {
                ps.setString(3, "Male");
            } else {
                ps.setString(3, "Female");
            }
            ps.setString(4, dDOB.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE));

            ps.setString(5, tContact.getText());

            ps.setString(6, tEmail.getText());
            ps.setString(7, tAddress.getText());
            ps.setString(8, lvCity.getSelectionModel().getSelectedItem());

            int count = ps.executeUpdate();
            if (count > 0) {
                Data.did = Integer.parseInt(tDID.getText());
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Donors");
                a.setContentText("Record Saved Successfully");
                a.show();
            }
            con.close();
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error in Project Donors");
            a.setContentText(ex.toString());
            a.show();
            System.out.println(ex);
        }

    }

    @FXML
    private void delete(ActionEvent event) {
        try {
            Connection con = Data.getConnection();
            PreparedStatement ps = con.prepareStatement("delete from donor where did=? ");
            ps.setString(1, tDID.getText());
            int count = ps.executeUpdate();
            if (count > 0) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Donors");
                a.setContentText("Record has been Deleted");
                a.show();
            }
            con.close();
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error in Project Donors");
            a.setContentText(ex.toString());
            a.show();
            System.out.println(ex);
        }
    }

    @FXML
    private void loadData(ActionEvent event) {
        try {
            data.clear();
            Connection con = Data.getConnection();
            PreparedStatement ps = con.prepareStatement("Select * from donor");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                data.add(new Donors(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
            tDonors.setItems(data);
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    @FXML
    private void newEntry(ActionEvent event) {
        try {
            data.clear();
            Connection con = Data.getConnection();
            PreparedStatement ps = con.prepareStatement("select Max(did) from donor");
            ResultSet rs = ps.executeQuery();
            int did = 0;
            if (rs.next()) {
                did = rs.getInt(1);
            }
            did++;
            tDID.setText(String.valueOf(did));
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    @FXML
    private void uploadData(MouseEvent event) {
        Donors s = tDonors.getSelectionModel().getSelectedItem();
        tDID.setText(s.getDID());
        Data.did = Integer.parseInt(tDID.getText());
        tName.setText(s.getName());
        //import java.time.format.DateTimeFormatter;
        if (s.getGender().equals("Male")) {
            rMale.setSelected(true);
        } else {
            rFemale.setSelected(true);
        }
        dDOB.setValue(LocalDate.parse(s.getDOB(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        tContact.setText(s.getContactno());
        tEmail.setText(s.getEmailid());

        tAddress.setText(s.getAddress());
        lvCity.getSelectionModel().select(s.getCity());

    }

    @FXML
    private void update(ActionEvent event) {
        try {
            Connection con = Data.getConnection();
            PreparedStatement ps = con.prepareStatement("update donor set name=?,gender=?,dob=?,contactno=?,emailid=?,address=?,city=? where did=? ");
            ps.setString(8, tDID.getText());
            ps.setString(1, tName.getText());
            if (rMale.isSelected()) {
                ps.setString(2, "Male");
            } else {
                ps.setString(2, "Female");
            }
            ps.setString(3, dDOB.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE));
            ps.setString(4, tContact.getText());
            ps.setString(5, tEmail.getText());
            ps.setString(6, tAddress.getText());
            ps.setString(7, lvCity.getSelectionModel().getSelectedItem());
            int count = ps.executeUpdate();

            if (count > 0) {
                Alert m = new Alert(Alert.AlertType.INFORMATION);
                m.setTitle("Donors");
                m.setContentText("Record has been Updated");
                m.show();
            }
            con.close();
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error in Project Donors");
            a.setContentText(ex.toString());
            a.show();
            System.out.println(ex);
        }

    }
}
