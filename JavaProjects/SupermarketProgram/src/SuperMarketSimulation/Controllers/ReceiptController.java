package SuperMarketSimulation.Controllers;

import SuperMarketSimulation._InheritedClasses.Product;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.IOException;

/*
This class is the final screen the customer will see, it displays a receipt of items the customer has
purchased and if the customer received any change, they can then decide to do another shop or quit the
program
 */
public class ReceiptController {

    public Button BTN_NewShop;
    @FXML
    public Label lbl_TotalFromCheckout;
    @FXML
    public ListView<Product> ReceiptList;
    @FXML
    public Label lbl_ChangeReceived;
    @FXML
    public Label lbl_PointsUsed;

    private final Stage thisStage;
    private final CheckoutController checkout;

    public ReceiptController(CheckoutController checkout) {

        this.checkout = checkout;
        thisStage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/Receipt.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setTitle("Receipt Screen - Screen 3");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showStage() {
        thisStage.show();
    }

    @FXML
    private void initialize() {
        lbl_TotalFromCheckout.setText(checkout.getTotal());
        System.out.println(lbl_TotalFromCheckout);

        lbl_ChangeReceived.setText(checkout.getChange());
        System.out.println(lbl_ChangeReceived);

        lbl_PointsUsed.setText(checkout.getPoints());
        System.out.println(lbl_PointsUsed);

        ReceiptList.setItems(checkout.getShopping());
        System.out.println(ReceiptList);
    }

    @FXML
    public void MainMenu(ActionEvent actionEvent) {
        ShopScreenController controller1 = new ShopScreenController();
        controller1.showStage();
        ((Stage)BTN_NewShop.getScene().getWindow()).close();
    }

    //Platform.exit shuts the entire program down not just the screen, as other screens are left open due to allowing multiple checkouts
    @FXML
    public void ExitProgram(ActionEvent actionEvent) {
        Platform.exit();
    }

    @FXML
    private void OrderScreen(ActionEvent actionEvent){
        TotalCustomerPaymentsController customerTotal = new TotalCustomerPaymentsController(this);
        thisStage.close();
        customerTotal.showStage();
    }

    @FXML
    public String getTotal() {
        return lbl_TotalFromCheckout.getText();
    }
}
