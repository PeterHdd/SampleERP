import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Login");
        Parent root= FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        Scene scene=new Scene(root,500,350);
        primaryStage.setScene(scene);
        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        primaryStage.show();
    }



}