package controllers;

import connectivity.Connections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Users;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    private Connection connection=null;
    private ResultSet resultSet=null;
    private String userNames;


    @FXML
    private TextField name;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private ListView<String> listView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String selectUsers="SELECT UserName FROM users";
        connection=Connections.getConnections();
        PreparedStatement preparedStatement;
        try {
            preparedStatement=connection.prepareStatement(selectUsers);
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                userNames = resultSet.getString("UserName");
                listView.getItems().add(userNames);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    public void saveButton(ActionEvent e) throws SQLException {

        if(name.getText().isEmpty()){
            alert("name is required");
            return;
        }
        if(userName.getText().isEmpty()){
            alert("user name is required");
            return;
        }
        if(firstName.getText().isEmpty()){
            alert("first name is required");
            return;
        }
        if(lastName.getText().isEmpty()){
            alert("last name is required");
            return;
        }
        if(password.getText().isEmpty()){
            alert("password is required");
            return;
        }
        else{

            insertUserData();
            Stage oldStage=HomeController.mainStages;
            oldStage.show();
            Stage currentStage=(Stage)userName.getScene().getWindow();
            currentStage.close();

        }

    }
    public void alert(String text){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(text);
        alert.show();
    }

    public void insertUserData() {
        connection=Connections.getConnections();
        PreparedStatement preparedStatement;
        String insertUser = "INSERT INTO users"
                + "(FirstName, LastName, UserName, Password) VALUES"
                + "(?,?,?,?)";
        try {
            preparedStatement=connection.prepareStatement(insertUser);
            Users name=new Users(firstName.getText(),lastName.getText(),userName.getText(),password.getText());
            String encrypt= DigestUtils.shaHex(name.getPassword());
            preparedStatement.setString(1,name.getFirstName());
            preparedStatement.setString(2,name.getLastName());
            preparedStatement.setString(3,name.getUserName());
            preparedStatement.setString(4,encrypt);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            alert("User Name already exists");

        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void backButton(ActionEvent e){
        Stage oldStage=HomeController.mainStages;
        oldStage.show();
        Stage currentStage=(Stage)userName.getScene().getWindow();
        currentStage.close();
    }

}
