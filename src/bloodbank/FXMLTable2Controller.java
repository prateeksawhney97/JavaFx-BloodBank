package bloodbank;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
public class FXMLTable2Controller implements Initializable {

    @FXML
    private TableView<Table> tTable2;
    @FXML
    private Button bBack;
    @FXML
    private Button bLoadData;
    @FXML
    private AnchorPane resultsPane;
    @FXML
    private TableColumn<Table,String> cAmount;
    @FXML
    private TableColumn<Table,String> cGroup;
    ObservableList<Table>data;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
             data=FXCollections.observableArrayList();
        cAmount.setCellValueFactory(new PropertyValueFactory<>("SNO"));        
        cGroup.setCellValueFactory(new PropertyValueFactory<>("Bgroup"));

    }    

    @FXML
    private void back(ActionEvent event) {
        try{
        AnchorPane root = FXMLLoader.load(getClass().getResource("FXMLResult.fxml"));
        resultsPane.getChildren().setAll(root);
      }catch(Exception ex){
        System.out.println(ex);
    }

    }

    @FXML
    private void loadData(ActionEvent event) {
           try{
    data.clear();
    Connection con=Data.getConnection();
    PreparedStatement ps=con.prepareStatement("select count(sno),bgroup from blood where edate>curdate() and status='available' group by bgroup ");
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            data.add(new Table(rs.getString(1),rs.getString(2)));
         }
        tTable2.setItems(data);
        }catch(Exception ex){
            System.out.println(ex);
        }
    
    }
    
}
