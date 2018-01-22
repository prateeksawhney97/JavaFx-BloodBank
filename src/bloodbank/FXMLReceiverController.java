package bloodbank;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class FXMLReceiverController implements Initializable {

    @FXML
    private TextField bName;
    @FXML
    private TextField bContactNumber;
    @FXML
    private RadioButton rMale;
    @FXML
    private ToggleGroup gender;
    @FXML
    private RadioButton rFemale;
    @FXML
    private DatePicker dDOB;
    @FXML
    private ListView<String> lvCity;
    @FXML
    private ComboBox<String> cbBloodGroup;
    @FXML
    private TextField tUnits;
    @FXML
    private TableColumn<Receiver,String> cID;
    @FXML
    private TableColumn<Receiver,String> cName;
    @FXML
    private TableColumn<Receiver,String> cContact;
    @FXML
    private TableColumn<Receiver,String> cGender;
    @FXML
    private TableColumn<Receiver,String> cDOB;
    @FXML
    private TableColumn<Receiver,String> cCity;
    @FXML
    private TableColumn<Receiver,String> cGroup;
    @FXML
    private TableColumn<Receiver,String> cUnits;
    @FXML
    private Button bSave;
    @FXML
    private Button bLoadData;
    @FXML
    private Button bNew;
    @FXML
    private Button bDelete;
    @FXML
    private TextField tID;
    @FXML
    private TableView<Receiver> tReceiver;
    ObservableList<Receiver> data;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbBloodGroup.getItems().addAll("O+","O-","A+","A-","AB+","AB-","B+","B-");
        lvCity.getItems().addAll("Panipat", "Karnal", "Sonipat", "Chandigarh", "Delhi", "Gurugram", "Ambala");
        data = FXCollections.observableArrayList();
        cID.setCellValueFactory(new PropertyValueFactory<>("RID"));
        cName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        cContact.setCellValueFactory(new PropertyValueFactory<>("Contactno"));
        cGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        cDOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        cGroup.setCellValueFactory(new PropertyValueFactory<>("Bgroup"));
        cUnits.setCellValueFactory(new PropertyValueFactory<>("Units"));
        cCity.setCellValueFactory(new PropertyValueFactory<>("City"));
    }    

    @FXML
    private void save(ActionEvent event) {
        if(tID.getText().isEmpty()){
            Data.showMessage("Receiver Id can't be left blank");
            return;
        }
        if(!Data.isValidContactNo(bContactNumber.getText())){
            Data.showMessage("Enter Valid Contact No.");
            return;
        }
        if(!rMale.isSelected() && !rFemale.isSelected()){
            Data.showMessage("Please Select either Male or Female!");
            return;
        }
        if(bName.getText().isEmpty()){
            Data.showMessage("Please Enter Name Of Receiver");
            return;
        }
        if(tUnits.getText().isEmpty()){
            Data.showMessage("Number Of Units Required cannot be left blank");
            return;
        }
        if(dDOB.getEditor().getText().isEmpty()){
            Data.showMessage("Please enter Date Of Birth");
            return;
        }
    
        try {
            Connection con = Data.getConnection();
            PreparedStatement ps = con.prepareStatement("Insert into receiver values(?,?,?,?,?,?,?,?)");
            ps.setString(1, tID.getText());
            ps.setString(2, bName.getText());
            ps.setString(3, bContactNumber.getText());
            if (rMale.isSelected()) {
                ps.setString(4, "Male");
            } else {
                ps.setString(4, "Female");
            }
            ps.setString(5, dDOB.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE));
            ps.setString(6, cbBloodGroup.getValue());
            ps.setString(7, tUnits.getText());
            ps.setString(8, lvCity.getSelectionModel().getSelectedItem());
            int count = ps.executeUpdate();
            if (count > 0) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Receivers");
                a.setContentText("Saved Successfully");
                a.show();
            }
            con.close();
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
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
            PreparedStatement ps = con.prepareStatement("Select * from receiver");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                data.add(new Receiver(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
            tReceiver.setItems(data);
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    @FXML
    private void newEntry(ActionEvent event) {
            try {
            data.clear();
            Connection con = Data.getConnection();
            PreparedStatement ps = con.prepareStatement("select Max(rid) from receiver");
            ResultSet rs = ps.executeQuery();
            int rid = 0;
            if (rs.next()) {
                rid = rs.getInt(1);
            }
            rid++;
            tID.setText(String.valueOf(rid));
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    @FXML
    private void delete(ActionEvent event) {
    try {
            Connection con = Data.getConnection();
            PreparedStatement ps = con.prepareStatement("delete from receiver where rid=? ");
            ps.setString(1, tID.getText());
            int count = ps.executeUpdate();
            if (count > 0) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Receivers");
                a.setContentText("Record has been Deleted");
                a.show();
            }
            con.close();
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setContentText(ex.toString());
            a.show();
            System.out.println(ex);
        }
    
    }

    @FXML
    private void uploadData(MouseEvent event) {
        Receiver r = tReceiver.getSelectionModel().getSelectedItem();
        tID.setText(r.getRID());
        bName.setText(r.getName());
        bContactNumber.setText(r.getContactno());
        if (r.getGender().equals("Male")) {
            rMale.setSelected(true);
        } else {
            rFemale.setSelected(true);
        }
        dDOB.setValue(LocalDate.parse(r.getDOB(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        cbBloodGroup.getSelectionModel().select(r.getBgroup());
        tUnits.setText(r.getUnits());
        lvCity.getSelectionModel().select(r.getCity());

    }
    
}
