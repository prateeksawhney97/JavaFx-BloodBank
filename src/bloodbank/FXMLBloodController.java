package bloodbank;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class FXMLBloodController implements Initializable {

    @FXML
    private TextField tSerial;
    @FXML
    private TextField tDID;
    @FXML
    private DatePicker dRdate;
    @FXML
    private DatePicker dEdate;
    @FXML
    private TextField tStatus;
    @FXML
    private Button bSave;
    @FXML
    private Button bDelete;
    @FXML
    private Button bNewEntry;
    @FXML
    private Button bUpdate;
    @FXML
    private Button bLoadData;
    @FXML
    private TableColumn<Blood, String> cSNO;
    @FXML
    private TableColumn<Blood, String> cDID;
    @FXML
    private TableColumn<Blood, String> cBloodGroup;
    @FXML
    private TableColumn<Blood, String> cRdate;
    @FXML
    private TableColumn<Blood, String> cEdate;
    @FXML
    private TableColumn<Blood, String> cStatus;
    @FXML
    private TableView<Blood> tBlood;

    ObservableList<Blood> data;
    @FXML
    private ComboBox<String> cbGroup;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbGroup.getItems().addAll("O+","O-","A+","A-","AB+","AB-","B+","B-");
        data = FXCollections.observableArrayList();
        cSNO.setCellValueFactory(new PropertyValueFactory<>("SNO"));
        cDID.setCellValueFactory(new PropertyValueFactory<>("DID"));
        cBloodGroup.setCellValueFactory(new PropertyValueFactory<>("Bgroup"));
        cRdate.setCellValueFactory(new PropertyValueFactory<>("Rdate"));
        cEdate.setCellValueFactory(new PropertyValueFactory<>("Edate"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        tDID.setText(String.valueOf(Data.did));
    }

    @FXML
    private void save(ActionEvent event) {
        if(tSerial.getText().isEmpty()){
            Data.showMessage("Serial Number can't be left blank");
            return;
        }
        if(tStatus.getText().isEmpty()){
            Data.showMessage("Status can't be left blank");
            return;
        }
        if(dRdate.getEditor().getText().isEmpty()){
        Data.showMessage("Please enter the receiving Date");
            return;
        }
        if(dEdate.getEditor().getText().isEmpty()){
            Data.showMessage("Please enter the Expiry Date");
            return;
        }

        try {
            Connection con = Data.getConnection();
            PreparedStatement ps = con.prepareStatement("Insert into blood values(?,?,?,?,?,?)");
            ps.setString(1, tSerial.getText());
            ps.setString(2, tDID.getText());
            ps.setString(3, cbGroup.getValue());
            ps.setString(4, dRdate.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE));
            ps.setString(5, dEdate.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE));
            ps.setString(6, tStatus.getText());

            int count = ps.executeUpdate();
            if (count > 0) {
                Data.sno = Integer.parseInt(tSerial.getText());
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Blood");
                a.setContentText("Saved");
                a.show();
            }
            con.close();
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error in Project Blood");
            a.setContentText(ex.toString());
            a.show();
            System.out.println(ex);
        }

    }

    @FXML
    private void delete(ActionEvent event) {
        try {
            Connection con = Data.getConnection();
            PreparedStatement ps = con.prepareStatement("delete from blood where sno=? ");
            ps.setString(1, tSerial.getText());
            int count = ps.executeUpdate();
            if (count > 0) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Blood");
                a.setContentText("Deleted");
                a.show();
            }
            con.close();
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error in Project Blood");
            a.setContentText(ex.toString());
            a.show();
            System.out.println(ex);
        }

    }

    @FXML
    private void newEntry(ActionEvent event) {
        try {
            Connection con = Data.getConnection();
            PreparedStatement ps = con.prepareStatement("select Max(sno) from blood");
            ResultSet rs = ps.executeQuery();
            int sno = 0;
            if (rs.next()) {
                sno = rs.getInt(1);
            }
            sno++;
            tSerial.setText(String.valueOf(sno));
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    @FXML
    private void update(ActionEvent event) {
        try {

            Connection con = Data.getConnection();
            PreparedStatement ps = con.prepareStatement("update blood set did=?,bgroup=?,rdate=?,edate=?,status=? where sno=? ");
            ps.setString(6, tSerial.getText());
            ps.setString(1, tDID.getText());
            ps.setString(2, cbGroup.getValue());
            ps.setString(3, dRdate.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE));
            ps.setString(4, dEdate.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE));
            ps.setString(5, tStatus.getText());
            int count = ps.executeUpdate();

            if (count > 0) {
                Alert m = new Alert(Alert.AlertType.INFORMATION);
                m.setTitle("Blood");
                m.setContentText("Updated");
                m.show();
            }
            con.close();
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error in Project Blood");
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
            PreparedStatement ps = con.prepareStatement("Select * from blood");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                data.add(new Blood(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
            }
            tBlood.setItems(data);
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    @FXML
    private void uploadData(MouseEvent event) {
        Blood s = tBlood.getSelectionModel().getSelectedItem();
        tDID.setText(s.getDID());
        tSerial.setText(s.getSNO());
        //import java.time.format.DateTimeFormatter;
        dRdate.setValue(LocalDate.parse(s.getRdate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        dEdate.setValue(LocalDate.parse(s.getEdate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        cbGroup.getSelectionModel().select(s.getBgroup());
        tStatus.setText(s.getStatus());

    }

}
