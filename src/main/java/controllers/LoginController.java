package controllers;


import connectivity.Connections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;


import java.io.IOException;
import java.sql.*;


public class LoginController {

    public Connection connect=null;
    public static String userNames;
    public static Stage mainStage;
    public ResultSet resultSet;

    @FXML
    private TextField userName;
    @FXML
    private PasswordField passwords;


    @FXML
    public void register(ActionEvent event) throws IOException {
        if(userName.getText().isEmpty()){
            alert("user name is required");
            return;
        }
        if(passwords.getText().isEmpty()){
            alert("password is required");
            return;
        }
        else{
            connect=Connections.getConnections();
            PreparedStatement preparedStatement;
            try {
                preparedStatement = connect.prepareStatement("SELECT * FROM users WHERE UserName = ? AND Password= ?");
                preparedStatement.setString(1,userName.getText()); //set parameter
                String encrypt= DigestUtils.shaHex(passwords.getText());
                preparedStatement.setString(2,encrypt);
                resultSet=preparedStatement.executeQuery();
                /*
                moves cursor forward one row from its current position, so the if clause means that if the row exists
                 */
                if (resultSet.next()) {
                    userNames=resultSet.getString("UserName");
                    changeScene();


                }
                else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid UserName and Password. Please try again");
                    alert.showAndWait();

                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connect.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        }

    }

    public void alert(String text){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(text);
        alert.show();
    }

    public void changeScene() throws IOException {
        mainStage= (Stage) userName.getScene().getWindow();
        mainStage.close();
        Stage homeStage=new Stage();
        homeStage.setTitle("Home Page");
        Parent root= FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
        Scene scene=new Scene(root,1350,690);
        homeStage.setScene(scene);
        homeStage.setResizable(false);
        homeStage.show();
    }


}

