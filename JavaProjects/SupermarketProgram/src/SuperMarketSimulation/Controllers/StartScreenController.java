package SuperMarketSimulation.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class StartScreenController {
    public Button startShopping;
    public Button information;

    public void Shop() {
        ShopScreenController controller1 = new ShopScreenController();
        controller1.showStage();
    }

    public void InfoScreen() throws IOException {
        Scene newScene = new Scene(FXMLLoader.load(getClass().getResource("../FXML/InformationScreen.fxml")));
        Stage popUpStage = new Stage();
        popUpStage.setScene(newScene);
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.show();
        popUpStage.setTitle("Extra Information");
    }

}
