package bloodbank;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
public class FXMLDocumentController implements Initializable {

    @FXML
    private Button bDonor;
    @FXML
    private TabPane tabMain;
    Tab tb1,tb2,tb3,tb4,tb5,tb6;
    @FXML
    private Button tBlood;
    @FXML
    private Button tReport;
    @FXML
    private Button bClose;
    @FXML
    private Button bResult;
    @FXML
    private Tab aboutPane;
    @FXML
    private ImageView imageView;
    @FXML
    private Button bReceive;
    @FXML
    private Button bPhoto;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    File file=new File("images/customLogo.jpg");
    Image img=new Image(file.toURI().toString());
    imageView.setImage(img);
    
    }    

    @FXML
    private void showDonor(ActionEvent event) {
         try{
            Node node=(AnchorPane)FXMLLoader.load(getClass().getResource("FXMLDonor.fxml"));
            tb1=new Tab("Donor Data",node);
            tabMain.getTabs().add(tb1);
            tabMain.getSelectionModel().select(tb1);
        }catch(Exception ex){
            System.out.println(ex);
        }

    }

    @FXML
    private void showBlood(ActionEvent event) {
     try{
            Node node=(AnchorPane)FXMLLoader.load(getClass().getResource("FXMLBlood.fxml"));
            tb2=new Tab("Blood Data",node);
            tabMain.getTabs().add(tb2);
            tabMain.getSelectionModel().select(tb2);
            
        }catch(Exception ex){
            System.out.println(ex);
        }
    
    }

    @FXML
    private void showReport(ActionEvent event) {
    try{
        Node node=(AnchorPane)FXMLLoader.load(getClass().getResource("FXMLReport.fxml"));
        tb3=new Tab("Report Data",node);
        tabMain.getTabs().add(tb3);
        tabMain.getSelectionModel().select(tb3);
    }catch(Exception ex){
        System.out.println(ex);
    }
    }

    @FXML
    private void closeCurrentTab(ActionEvent event) {
    try{
        tabMain.getTabs().remove(tabMain.getSelectionModel().getSelectedIndex());
            }catch(Exception ex){
        System.out.println(ex);
    }
    }

    @FXML
    private void showResults(ActionEvent event) {
    try{
        Node node=(AnchorPane)FXMLLoader.load(getClass().getResource("FXMLResult.fxml"));
        tb4=new Tab("Result Data",node);
        tabMain.getTabs().add(tb4);
        tabMain.getSelectionModel().select(tb4);
    }catch(Exception ex){
        System.out.println(ex);
    }
    }

    /*private void showPics(ActionEvent event) {
    try{
        Node node=(AnchorPane)FXMLLoader.load(getClass().getResource("FXMLPhoto.fxml"));
        tb5=new Tab("Photo Data",node);
        tabMain.getTabs().add(tb5);
        tabMain.getSelectionModel().select(tb5);
    }catch(Exception ex){
        System.out.println(ex);
    }
    }*/

    @FXML
    private void showReceiverDetails(ActionEvent event) {
    try{
        Node node=(AnchorPane)FXMLLoader.load(getClass().getResource("FXMLReceiver.fxml"));
        tb5=new Tab("Receiver Data",node);
        tabMain.getTabs().add(tb5);
        tabMain.getSelectionModel().select(tb5);
    }catch(Exception ex){
        System.out.println(ex);
    }
    }

    @FXML
    private void showPhotos(ActionEvent event) {
    try{
        Node node=(AnchorPane)FXMLLoader.load(getClass().getResource("FXMLPhoto.fxml"));
        tb6=new Tab("Photo Data",node);
        tabMain.getTabs().add(tb6);
        tabMain.getSelectionModel().select(tb6);
    }catch(Exception ex){
        System.out.println(ex);
    }
    }

    
}
