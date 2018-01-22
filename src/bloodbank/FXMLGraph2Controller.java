package bloodbank;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
public class FXMLGraph2Controller implements Initializable {

    @FXML
    private Button bBack;
    @FXML
    private Button bLoad;
    @FXML
    private BarChart<String,Integer> barChart;
    @FXML
    private LineChart<String,Integer> lineChart;
    @FXML
    private AnchorPane resultsPane;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private CategoryAxis xAxis;
            XYChart.Series series= new XYChart.Series<>();
            //series.setName("abcd");
            XYChart.Series series1= new XYChart.Series<>();
            //series1.setName("vbnm");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        series = new XYChart.Series<>();
            series1 = new XYChart.Series<>();
            String a[]={"A+","A-","AB+","AB-","B+","B-","O+","O-"};
            for(String s:a){
                series.getData().add(new XYChart.Data<>(s,0));
                series1.getData().add(new XYChart.Data<>(s,0));
            }
            barChart.getData().add(series);
            lineChart.getData().add(series1);
    
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
            //data.clear();
            barChart.getData().clear();
            lineChart.getData().clear();
            Connection con=Data.getConnection();
            PreparedStatement ps=con.prepareStatement("Select count(sno),bgroup from blood where edate>=curdate() and status='available' group by bgroup");
            ResultSet rs=ps.executeQuery();
            series=new XYChart.Series<>();
            series1=new XYChart.Series<>();
            
            while(rs.next()){
                final String b=rs.getString(2);
                System.out.println(b);
                series.getData().add(new XYChart.Data<>(rs.getString(2),rs.getInt(1)));
                series1.getData().add(new XYChart.Data<>(rs.getString(2),rs.getInt(1)));
            }
            barChart.getData().addAll(series);
            lineChart.getData().add(series1);
            con.close();
    }catch(Exception ex){
            System.out.println(ex);
            }
      
    }
    
}
