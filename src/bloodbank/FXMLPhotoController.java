package bloodbank;
//import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
public class FXMLPhotoController implements Initializable {

    @FXML
    private AnchorPane resultsPane;
    @FXML
    private TableView<Photos> tPhotos;
    @FXML
    private TableColumn<Photos,Integer> cSNO;
    @FXML
    private TableColumn<Photos,String> cImage;
    @FXML
    private TextField tSNO;
    @FXML
    private Button bBack;
    @FXML
    private Button bBrowse;
    @FXML
    private Button bShow;
    @FXML
    private ImageView imageView;
    @FXML
    private Button bSave;
        
    FileChooser open;
    File file;
    Image img;
    ObservableList<Photos>list;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    list=FXCollections.observableArrayList();
    cSNO.setCellValueFactory(new PropertyValueFactory<>("SNO"));
    cImage.setCellValueFactory(new PropertyValueFactory<>("Picture"));
    tSNO.setText(String.valueOf(Data.did));
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
    private void showOpenBox(ActionEvent event) {
    try{
        open=new FileChooser();
        open.getExtensionFilters().addAll(
        new ExtensionFilter("Image Files","*.png","*.jpg"),
        new ExtensionFilter("All Files","*.*")
        );
        open.setInitialDirectory(new File("C:\\Users\\Prateek Sawhney\\Documents\\NetBeansProjects\\BloodBank"));
        file=open.showOpenDialog(BloodBank.window);
        img=new Image(file.toURI().toString());
        imageView.setImage(img);
    }catch(Exception ex){
        Data.showMessage(ex.toString());
    }
    }

    @FXML
    private void showPhotos(ActionEvent event) {
    try{
        list.clear();
        Connection con=Data.getConnection();
        PreparedStatement ps=con.prepareStatement("select sno from pics");
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            list.add(new Photos(rs.getInt(1),"Image"));
        }
        tPhotos.setItems(list);
        con.close();
        }catch(Exception ex){
        Data.showMessage(ex.toString());
    }
    }

    @FXML
    private void save(ActionEvent event) {
    try{
        Connection con=Data.getConnection();
        PreparedStatement ps=con.prepareStatement("insert into pics values(?,?)");
        ps.setInt(1,Integer.parseInt(tSNO.getText()));
        
        InputStream is=new FileInputStream(file);
        ps.setBinaryStream(2,is,(int)file.length());
        
        int count=ps.executeUpdate();
        if(count>0)
        Data.showMessage("Image Saved Successfully");
        con.close();
        }catch(Exception ex){
        Data.showMessage(ex.toString());
    }
    }

    @FXML
    private void uploadData(MouseEvent event) {
    Photos p=tPhotos.getSelectionModel().getSelectedItem();
    tSNO.setText(String.valueOf(p.getSNO()));
    try{
        Connection con=Data.getConnection();
        PreparedStatement ps=con.prepareStatement("select pic from pics where sno=?");
        ps.setInt(1,p.getSNO());
        ResultSet rs=ps.executeQuery();
        if(rs.next()){
            InputStream is=rs.getBinaryStream(1);
            img=new Image(is);
            imageView.setImage(img);
        }
        con.close();
    }catch(Exception ex){
        Data.showMessage(ex.toString());
    }
    }
    
}
