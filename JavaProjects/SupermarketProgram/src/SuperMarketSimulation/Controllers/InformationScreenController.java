package SuperMarketSimulation.Controllers;

import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InformationScreenController {

    public Button close;

// This is a simple method to close the InformationScreen fxml file when it is pressed
    public void Exit() {
        ((Stage)close.getScene().getWindow()).close();
    }
}
