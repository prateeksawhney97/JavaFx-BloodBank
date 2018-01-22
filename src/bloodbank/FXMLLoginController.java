package bloodbank;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class FXMLLoginController implements Initializable {

    @FXML
    private TextField tUsername;
    @FXML
    private TextField tPassword;
    @FXML
    private Label lPassword;
    @FXML
    private CheckBox cbPassword;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    lPassword.setVisible(false);
    }    

    @FXML
    private void copyPassword(KeyEvent event) {
    lPassword.setText(tPassword.getText());
    }


    @FXML
    private void checkLogin(ActionEvent event) {
    String username=tUsername.getText();
    String pwd=tPassword.getText();
    if(username.equals("PS") && pwd.equals("2")){
        try{
            FXMLLoader loader=new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
            Parent root=loader.load();
            Screen screen=Screen.getPrimary();
            double x=screen.getBounds().getWidth();
            double y=screen.getBounds().getHeight();
            Scene scene=new Scene(root,x,y-80);
            Stage stage=new Stage();
            stage.setScene(scene);
            BloodBank.window.close();
            BloodBank.window=stage;
            stage.show();
            
        }catch(Exception ex){
         System.out.println(ex);
        }
    }else{
        String msg ="Invalid Username or Password";
        //Data.showMessage("Invalid Username or Password");
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Blood Bank");
        a.setContentText(msg);
        a.show();
    }
    }

    @FXML
    private void exitForm(ActionEvent event) {
    System.exit(0);
    }

    @FXML
    private void showHidePassword(ActionEvent event) {
        if(cbPassword.isSelected()){
            lPassword.setVisible(true);
           // tPassword.setVisible(true);
        }else{
            lPassword.setVisible(false);
        
        }
        
    }
    
}
