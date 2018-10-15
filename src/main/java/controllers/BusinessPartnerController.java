package controllers;

import connectivity.Connections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.BusinessPartner;
import model.Users;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class BusinessPartnerController implements Initializable {

    private Connection connection = null;
    private ResultSet resultSet = null;
    private String fiscalNames;

    @FXML
    private TextField fiscalName;
    @FXML
    private TextField companyId;
    @FXML
    private TextField taxId;
    @FXML
    private TextField location;
    @FXML
    private TextField currency;
    @FXML
    private TextField key;
    @FXML
    private ListView<String> listView;
    @FXML
    private CheckBox customerCheck;


    /**
     *
     * used for initialization, retrieves list of business partners
     *
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String selectBp = "SELECT FiscalName FROM businesspartner";
        connection = Connections.getConnections();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(selectBp);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                fiscalNames = resultSet.getString("FiscalName");
                listView.getItems().add(fiscalNames);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void backButton(ActionEvent e) {
        navigate();
    }

    public void navigate() {
        Stage oldStage = HomeController.mainStages;
        oldStage.show();
        Stage currentStage = (Stage) fiscalName.getScene().getWindow();
        currentStage.close();
     }




    public void saveButton(ActionEvent event) {
        if (fiscalName.getText().isEmpty()) {
            alert("Fiscal Name is required");
            return;
        }
        if (key.getText().isEmpty()) {
            alert("Search Key is required");
            return;
        }
        if (currency.getText().isEmpty()) {
            alert("Price List is required");
            return;
        }
        if(location.getText().isEmpty()){
            alert("Location is required");
            return;
        }
        if(checkIfExists()){
            alert("Fiscal Name and Search Key must be unique");
        }
        else{
            insertBpData();
            navigate();
        }
    }

    public void alert(String text){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(text);
        alert.show();
    }

    /**
     * inserts BP data inside the business partner table
     */
    public void insertBpData(){
        connection=Connections.getConnections();
        PreparedStatement preparedStatement;
        String insertBp = "INSERT INTO businesspartner"
                + "(SearchKey, FiscalName, TaxId, CompanyId,Customer,Price,Location) VALUES"
                + "(?,?,?,?,?,?,?)";
        try {
            preparedStatement=connection.prepareStatement(insertBp);
            BusinessPartner bpPartner=new BusinessPartner(key.getText(),fiscalName.getText(),taxId.getText(),companyId.getText(),customerCheck.isSelected(),currency.getText(),location.getText());
            preparedStatement.setString(1,bpPartner.getSearchKey());
            preparedStatement.setString(2,bpPartner.getFiscalName());
            preparedStatement.setString(3,bpPartner.getTaxId());
            preparedStatement.setString(4,bpPartner.getCompanyId());
            preparedStatement.setInt(5,(bpPartner.getCustomer())?1:0);
            preparedStatement.setString(6,bpPartner.getCurrency());
            preparedStatement.setString(7,bpPartner.getLocation());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
           e.getMessage();

        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * checks if name and key are unique
     *
     */
    public Boolean checkIfExists(){
        Boolean isExists=false;
        connection=Connections.getConnections();
        PreparedStatement preparStatement;
        String checkIfExists="SELECT SearchKey, FiscalName FROM businesspartner WHERE SearchKey= ? OR FiscalName= ?";
        try {
            preparStatement=connection.prepareStatement(checkIfExists);
            preparStatement.setString(1,key.getText());
            preparStatement.setString(2,fiscalName.getText());
            resultSet=preparStatement.executeQuery();
            if(resultSet.next()){
                isExists=true;
            }else{
                isExists=false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return isExists;

        }

    }
}
