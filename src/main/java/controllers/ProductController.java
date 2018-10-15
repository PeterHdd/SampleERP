package controllers;

import connectivity.Connections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.BusinessPartner;
import model.Product;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    private Connection connection = null;
    private ResultSet resultSet = null;
    private String productNames;
    private Integer id;

    @FXML
    private ComboBox<String> productCategory;
    @FXML
    private ComboBox<String> uom;
    @FXML
    private ListView<String> listView;
    @FXML
    private TextField name;
    @FXML
    private TextField currency;
    @FXML
    private TextField key;
    @FXML
    private TextField bpReference;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productCategory.getItems().addAll("Food","Plastics","Tobacco","Home Care","Water Treatment");
        uom.getItems().addAll("Kilogram","Liter","Gallon","Metre","Pound","Unit");
        init();
    }

    public void navigate() {
        Stage oldStage = HomeController.mainStages;
        oldStage.show();
        Stage currentStage = (Stage) uom.getScene().getWindow();
        currentStage.close();
    }

    public void backButton(ActionEvent e){
        navigate();
    }

    /**
     * add list of products
     */
    public void init(){
        String selectProducts = "SELECT name FROM product";
        connection = Connections.getConnections();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(selectProducts);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productNames = resultSet.getString("name");
                listView.getItems().add(productNames);
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

    public void saveButton(ActionEvent e){

        if (name.getText().isEmpty()) {
            alert("Product Name is required");
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
        if(bpReference.getText().isEmpty()){
            alert("Location is required");
            return;
        }
        if(uom.getValue().isEmpty()){
            alert("UOM is required");
            return;
        }
        if(productCategory.getValue().isEmpty()){
            alert("Category is required");
            return;
        }
        if(checkIfExists()){
            alert("Product Name and Search Key must be unique");
        }
        else{
            try {
                insertProductData();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
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
     * checks if product name and key are unique
     *
     */
    public Boolean checkIfExists(){
        Boolean isExists=false;
        connection=Connections.getConnections();
        PreparedStatement preparStatement;
        String checkIfExists="SELECT SearchKey, name FROM product WHERE SearchKey= ? OR name= ?";
        try {
            preparStatement=connection.prepareStatement(checkIfExists);
            preparStatement.setString(1,key.getText());
            preparStatement.setString(2,name.getText());
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

    public void insertProductData() throws SQLException {
        connection=Connections.getConnections();
        PreparedStatement preparedStatement;
        String selectId="SELECT BPId from businesspartner WHERE FiscalName= ?";
        preparedStatement=connection.prepareStatement(selectId);
        preparedStatement.setString(1,bpReference.getText());
        resultSet=preparedStatement.executeQuery();
        if(resultSet.next()){
            id=resultSet.getInt("BPId");
        }
        else{
            alert("Business Partner does not exist");
        }
        String insertBp = "INSERT INTO product"
                + "(SearchKey, name, currency, productCategory,UOM,bpsId) VALUES"
                + "(?,?,?,?,?,?)";
        try {
            preparedStatement=connection.prepareStatement(insertBp);
            Product product=new Product(key.getText(),name.getText(),currency.getText(),productCategory.getValue(),uom.getValue(),bpReference.getText());
            preparedStatement.setString(1,product.getSearchKey());
            preparedStatement.setString(2,product.getName());
            preparedStatement.setString(3,product.getCurrency());
            preparedStatement.setString(4,product.getProductCategory());
            preparedStatement.setString(5,product.getUom());
            preparedStatement.setInt(6,id);

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
}
