package bloodbank;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
public class FXMLResultController implements Initializable {
    @FXML
    private Button bTable;
    @FXML
    private Button bGraph;
    @FXML
    private AnchorPane resultsPane;
    @FXML
    private Button bTable2;
    @FXML
    private Button bGraph2;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void showTable(ActionEvent event) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("FXMLTable.fxml"));
            resultsPane.getChildren().setAll(root);
            } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void showGraph(ActionEvent event) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("FXMLGraph1.fxml"));
            resultsPane.getChildren().setAll(root);
            } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void showTable2(ActionEvent event) {
    try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("FXMLTable2.fxml"));
            resultsPane.getChildren().setAll(root);
            } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void showGraph2(ActionEvent event) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("FXMLGraph2.fxml"));
            resultsPane.getChildren().setAll(root);
            } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void showTable3(ActionEvent event) {
    try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("FXMLTable3.fxml"));
            resultsPane.getChildren().setAll(root);
            } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    @FXML
    private void showGraph3(ActionEvent event) {
    try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("FXMLGraph3.fxml"));
            resultsPane.getChildren().setAll(root);
            } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void showTable4(ActionEvent event) {
    try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("FXMLTable4.fxml"));
            resultsPane.getChildren().setAll(root);
            } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    @FXML
    private void showGraph4(ActionEvent event) {
    try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("FXMLGraph4.fxml"));
            resultsPane.getChildren().setAll(root);
            } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
}
