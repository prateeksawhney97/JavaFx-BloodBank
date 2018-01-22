package bloodbank;
import com.github.sarxos.webcam.Webcam;
import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.imageio.ImageIO;

public class FXMLReportController implements Initializable {

    @FXML
    private TableView<Report> tReport;
    @FXML
    private TableColumn<Report, String> cSNO;
    @FXML
    private TableColumn<Report, String> cSugar;
    @FXML
    private TableColumn<Report, String> cHB;
    @FXML
    private TableColumn<Report, String> cBP;
    @FXML
    private TableColumn<Report, String> cPlatelets;
    @FXML
    private TableColumn<Report, String> cUricAcid;
    @FXML
    private TableColumn<Report, String> cWBC;
    @FXML
    private TextField tSNO;
    @FXML
    private TextField tSugar;
    @FXML
    private TextField tHB;
    @FXML
    private TextField tPlatelets;
    @FXML
    private TextField tUricAcid;
    @FXML
    private TextField tWBC;
    @FXML
    private TextField tBP;
    @FXML
    private Button bSave;
    @FXML
    private Button bDelete;
    @FXML
    private Button bNewEntry;
    @FXML
    private Button bLoadData;
    @FXML
    private Button bUpdate;

    ObservableList<Report> data;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data = FXCollections.observableArrayList();
        cSNO.setCellValueFactory(new PropertyValueFactory<>("SNO"));
        cSugar.setCellValueFactory(new PropertyValueFactory<>("Sugar"));
        cHB.setCellValueFactory(new PropertyValueFactory<>("HB"));
        cBP.setCellValueFactory(new PropertyValueFactory<>("BP"));
        cPlatelets.setCellValueFactory(new PropertyValueFactory<>("Platelets"));
        cUricAcid.setCellValueFactory(new PropertyValueFactory<>("Uricacid"));
        cWBC.setCellValueFactory(new PropertyValueFactory<>("WBC"));
        tSNO.setText(String.valueOf(Data.sno));

    }

    @FXML
    private void save(ActionEvent event) {
        try {
            Connection con = Data.getConnection();
            PreparedStatement ps = con.prepareStatement("Insert into report values(?,?,?,?,?,?,?)");
            ps.setString(1, tSNO.getText());
            ps.setString(2, tSugar.getText());
            ps.setString(3, tHB.getText());
            ps.setString(4, tBP.getText());
            ps.setString(5, tPlatelets.getText());
            ps.setString(6, tUricAcid.getText());
            ps.setString(7, tWBC.getText());

            int count = ps.executeUpdate();
            if (count > 0) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Report");
                a.setContentText("Saved");
                a.show();
            }
            con.close();
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error in Project Report");
            a.setContentText(ex.toString());
            a.show();
            System.out.println(ex);
        }

    }

    @FXML
    private void delete(ActionEvent event) {
        try {
            Connection con = Data.getConnection();
            PreparedStatement ps = con.prepareStatement("delete from report where sno=? ");
            ps.setString(1, tSNO.getText());
            int count = ps.executeUpdate();
            if (count > 0) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Peport");
                a.setContentText("Deleted");
                a.show();
            }
            con.close();
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error in Project Report");
            a.setContentText(ex.toString());
            a.show();
            System.out.println(ex);
        }

    }

    @FXML
    private void newEntry(ActionEvent event) {
        try {
            Connection con = Data.getConnection();
            PreparedStatement ps = con.prepareStatement("select Max(sno) from report");
            ResultSet rs = ps.executeQuery();
            int sno = 0;
            if (rs.next()) {
                sno = rs.getInt(1);
            }
            sno++;
            tSNO.setText(String.valueOf(sno));
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    @FXML
    private void loadData(ActionEvent event) {
        try {
            data.clear();
            Connection con = Data.getConnection();
            PreparedStatement ps = con.prepareStatement("Select * from report");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                data.add(new Report(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
            tReport.setItems(data);
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    @FXML
    private void update(ActionEvent event) {
        try {

            Connection con = Data.getConnection();
            PreparedStatement ps = con.prepareStatement("update report set sugar=?,hb=?,bp=?,platelets=?,uricacid=?,wbc=? where sno=? ");
            ps.setString(7, tSNO.getText());
            ps.setString(1, tSugar.getText());
            ps.setString(2, tHB.getText());
            ps.setString(3, tBP.getText());
            ps.setString(4, tPlatelets.getText());
            ps.setString(5, tUricAcid.getText());
            ps.setString(6, tWBC.getText());

            int count = ps.executeUpdate();

            if (count > 0) {
                Alert m = new Alert(Alert.AlertType.INFORMATION);
                m.setTitle("Report");
                m.setContentText("Updated");
                m.show();
            }
            con.close();
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error in Project Report");
            a.setContentText(ex.toString());
            a.show();
            System.out.println(ex);
        }

    }

    @FXML
    private void uploadData(MouseEvent event) {
        Report s = tReport.getSelectionModel().getSelectedItem();
        tSNO.setText(s.getSNO());
        tSugar.setText(s.getSugar());
        tHB.setText(s.getHB());
        tBP.setText(s.getBP());
        tPlatelets.setText(s.getPlatelets());
        tUricAcid.setText(s.getUricacid());
        tWBC.setText(s.getWBC());

    }

    @FXML
    private void printReport(ActionEvent event) {
        int sno=Integer.parseInt(tSNO.getText());
        try {
            File file=new File("report.htm");
            file.createNewFile();
            PrintWriter out=new PrintWriter(file);
            out.println("<html>");
            out.println("<body>");
            
            out.println("<table border='0' width='100%'>");
            out.println("<tr>");
            out.println("<td align='center'><h1>Blood Test Report</h1></td>");
            out.println("</tr>");
            out.println("<tr>");
                    out.println("<table border='0' width='100%'>");
                        out.println("<tr>");
                            out.println("<td width='300px'>S.No. : "+sno+"</td>");
                            out.println("<td rowspan='4'>"+"<img src='DonorPhoto.jpg' border=2></img>"+"</td>");
                            out.println("<td rowspan='4'>"+"<img src='logo.png' border=2></img>"+"</td>");
                        out.println("</tr>");
                        out.println("<tr>");
                            out.println("<td>Donar ID : "+Data.getDonarID(sno)+"</td>");
                        out.println("</tr>");
                        out.println("<tr>");
                            out.println("<td>Donar Name : "+Data.getDonarName(sno)+"</td>");
                        out.println("</tr>");
                        out.println("<tr>");
                            out.println("<td>Date of Blood Donation : "+Data.getBloodDonationDate(sno)+"</td>");
                        out.println("</tr>");
                    out.println("</table>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<table border='1' width='100%'>");
                Connection con=Data.getConnection();
                PreparedStatement ps=con.prepareStatement("Select * from report where sno=?");
                ps.setInt(1,sno);
                ResultSet rs=ps.executeQuery();
                String tests[]={"Sugar","HB","BP","Platelets","Uric Acid","WBC"};
                String range[]={"8-120","8-16","90-180","50000-100000","12-20","10000-20000"};
                out.println("<tr>");
                    out.println("<th>SNo</th>");
                    out.println("<th>Test</th>");
                    out.println("<th>Result</th>");
                    out.println("<th>Range</th>");
                out.println("</tr>");
                if(rs.next()){
                    for (int i = 1; i <= 6; i++) {
                        out.println("<tr>");
                        out.println("<td>"+i+"</td>");
                        out.println("<td>"+tests[i-1]+"</td>");
                        out.println("<td>"+rs.getString(i+1)+"</td>");
                        out.println("<td>"+range[i-1]+"</td>");
                        out.println("</tr>");
                    }
                }
                con.close();
                out.println("</table>");
                out.println("</tr>");
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
            out.close();
            java.awt.Desktop.getDesktop().open(file);
        } catch (Exception e) {
            Data.showMessage(e.toString());
        }
    }

    @FXML
    private void showWebcam(ActionEvent event) {
    try{    
            //int photonumber=Integer.parseInt(tDID.getText());
            Webcam wb=Webcam.getDefault();
            wb.open();
            ImageIO.write(wb.getImage(),"jpg",new File("DonorPhoto.jpg"));
            wb.close();
            Data.did = Integer.parseInt(tSNO.getText());
            Alert m = new Alert(Alert.AlertType.INFORMATION);
                m.setTitle("Donors");
                m.setContentText("Photo Taken Successfully");
                m.show();
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }

}
