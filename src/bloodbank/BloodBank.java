package bloodbank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
//import javafx.stage.Screen;
public class BloodBank extends Application {
    public static Stage window;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
        window=stage;
        /*Screen screen=Screen.getPrimary();
        double width=screen.getBounds().getWidth();
        double height=screen.getBounds().getHeight();
        */
        Scene scene = new Scene(root);
        stage.setOpacity(1.0);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        
        
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
