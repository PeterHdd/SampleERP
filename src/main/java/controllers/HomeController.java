package controllers;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    public static Stage mainStages;

    @FXML
    public Menu name;

    @FXML
    public WebView webView;

    @FXML
    public MenuItem about;

    @FXML
    public Label welcomeText;

    @FXML
    public MenuItem logout;

    @FXML
    public BorderPane borderPane;

    @FXML
    public MenuBar menuBar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setText(LoginController.userNames);
        webView.getEngine().load("https://www.google.com");

    }

    public void onAboutClick(ActionEvent e){
        String information="This ERP is a template, that is used for testing and practicing\n" +
                "\n" +
                "This product has been created by Peter Haddad and is distributed under the GNU Public License (https://www.gnu.org/licenses/gpl-3.0.en.html)\n"+
                "This instance includes the following modules:\n" +
                "\n" +
                "- Users \n" +
                "- Business Partners \n" +
                "- Products \n"
                +"\n"
                +"Thank you to all for your interest.";
        welcomeText.setFont(new Font("Regular",15));
        welcomeText.setText(information);
    }

    public void onLogout(ActionEvent event){
       Stage oldStage=LoginController.mainStage;
       oldStage.show();
       Stage currentStage=(Stage)menuBar.getScene().getWindow();
       currentStage.hide();

    }

    public void usersAddition(ActionEvent e) throws IOException {
        navigateToNextPage("Add User","/fxml/addUser.fxml");
    }

    public void businessPartnerAddition(ActionEvent e) throws IOException {
        navigateToNextPage("Add Business Partner","/fxml/addBP.fxml");

    }

    public void productAddition(ActionEvent e) throws IOException{
        navigateToNextPage("Add Product","/fxml/addProduct.fxml");
    }

    private void navigateToNextPage(String title,String resource) throws IOException {
        mainStages= (Stage) menuBar.getScene().getWindow();
        mainStages.close();
        Stage homeStage=new Stage();
        homeStage.setTitle(title);
        Parent root= FXMLLoader.load(getClass().getResource(resource));
        Scene scene=new Scene(root,700,640);
        homeStage.setScene(scene);
        homeStage.setResizable(false);
        homeStage.show();
    }








}
