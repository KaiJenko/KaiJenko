package SuperMarketSimulation.Classes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SuperMarketMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/StartScreen.fxml"));
        Scene scene = new Scene(root, 820, 600);
        scene.getStylesheets().add("SuperMarketSimulation/FXML/_Theme1.css");//retrieves the css file I created
        primaryStage.setTitle("Welcome");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
