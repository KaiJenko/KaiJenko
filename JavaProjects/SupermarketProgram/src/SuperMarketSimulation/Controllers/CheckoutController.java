package SuperMarketSimulation.Controllers;

import SuperMarketSimulation.Classes.CustomerLoyaltyCard;
import SuperMarketSimulation.Classes.LoyaltyCard;
import SuperMarketSimulation._InheritedClasses.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;

/*
This class is used to allow the user to pay for the shopping they received from the Shop controller
they can either pay by card or cash depending on checkout type
 */
public class CheckoutController {

    private final Stage thisStage;
    private final ShopScreenController shopScreen;
    private CustomerLoyaltyCard cu = new CustomerLoyaltyCard();
    private ObservableList<LoyaltyCard> listOfCustomers;

    //These 3 attributes are passed and used in other checkouts
    @FXML
    public Label lbl_totalFromShopController;
    @FXML
    public Label lbl_CheckoutType;
    @FXML
    public ListView<Product> shopList;

    public Button BTN_PayCash;
    public Button BTN_PayCard;
    public Button BTN_ConfirmCash;
    public Button BTN_ConfirmCard;
    public Button BTN_LoadPoints;
    public Button BTN_AddPoints;

    public Label lbl_Change;
    public Label lbl_LoyaltyTotal;
    public Label lbl_LoyaltyPoints;

    public TextField TF_CashPayment;
    public TextField TF_CardNumber;
    public TextField TF_CardSortNum;

    public ComboBox<String> CardType;
    public ComboBox<LoyaltyCard> loyaltyType;

    //I am using constructor injected data instead of controllers which means I can pass data and values
    //between classes and GUIs by setting which controller they are being passed to, such as values from
    //shop controller are being passed to this class
    public CheckoutController(ShopScreenController shopScreen) {

        this.shopScreen = shopScreen;
        thisStage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/Checkout.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setTitle("Checkout Screen - Screen 2");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String formatGBP(double p) {
        NumberFormat gb = NumberFormat.getCurrencyInstance(Locale.UK);
        return gb.format(p);
    }

    public void showStage() {
        thisStage.show();
    }

    //This is my intialize method which has everything which needs to be run on start up of this class,
    //such as any labels text or any methods which data is being used instantly, not dependant on actions
    @FXML
    private void initialize() {
        lbl_totalFromShopController.setText("" + shopScreen.formatGBP(shopScreen.getEnteredLabel()));
        lbl_LoyaltyTotal.setText("" + shopScreen.getEnteredLabel());
        System.out.println("total for shopping: " + lbl_totalFromShopController);

        shopList.setItems(shopScreen.getList());//The listviews items are created here, retrieving them from the shop controller
        validateCard();//These are calls to methods which are being used first
        CashNumOnly();
        loyaltyCard();

    }

    //These two methods make sure only one payment method can be used not both
    @FXML
    public void cardPayment(ActionEvent actionEvent) {
        CardType.setDisable(false);
        BTN_ConfirmCard.setDisable(false);
        BTN_PayCash.setDisable(true);
    }

    @FXML
    public void cashPayment(ActionEvent actionEvent) {
        TF_CashPayment.setDisable(false);
        BTN_ConfirmCash.setDisable(false);
        BTN_PayCard.setDisable(true);
    }

    //This method makes sure the correct amount of cash is paid
    @FXML
    public void validateCash() {

        String cash = TF_CashPayment.getText();
        System.out.println(cash);
        Alert alert;

        if (cash.equals("")) { //If the textfield is empty
            alert = new Alert(Alert.AlertType.WARNING, "Please put how much cash you are paying with", ButtonType.OK);
            alert.showAndWait();
        }
        else {
            double customerPayment = Double.parseDouble(TF_CashPayment.getText());//converts String to a Double
            double customerLoyalty = Double.parseDouble(lbl_LoyaltyTotal.getText());
            if (customerPayment == customerLoyalty) {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "That is the correct amount of cash", ButtonType.OK);
                lbl_Change.setText("£0.00");
                alert.showAndWait();
                openReceiptScreen();

            } else if (customerPayment > customerLoyalty) { //If more money is given than needed then change is received
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Your Change is: " + shopScreen.formatGBP(change()), ButtonType.OK);
                lbl_Change.setText(shopScreen.formatGBP(change()));
                alert.showAndWait();
                openReceiptScreen();

            } else if (customerPayment < customerLoyalty) { //Makes sure either the correct amount or more is given
                alert = new Alert(Alert.AlertType.WARNING, "You Still owe : " + shopScreen.formatGBP(remaining()), ButtonType.OK);
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.ERROR, "ERROR", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    //These four methods all return doubles and are used to tell the user different values they receive
    public double change() { //If the user gives more money than needed they are given change
        double total = Double.parseDouble(lbl_LoyaltyTotal.getText());
        double cash = Double.parseDouble(TF_CashPayment.getText());
        return cash - total;
    }

    public double remaining() { //If the user doesn't give enough money then a remaining value is shown
        double total = Double.parseDouble(lbl_LoyaltyTotal.getText());
        double cash = Double.parseDouble(TF_CashPayment.getText());
        return total - cash;
    }

    public double removeLoyaltyCardTotal() { //If the user uses their loyalty points a discount is applied to the total
        double total = shopScreen.calculateTotal();
        double loyalPoints = cu.convertPoints(loyaltyType.getValue());
        return total - loyalPoints;
    }

    public double addLoyaltyCardTotal() { //If the user doesnt use the points but receives points instead they are added to their total points
        double oldLoyaltyPoints = cu.getPoints(loyaltyType.getValue());
        double newLoyaltyPoints = cu.addPoints(loyaltyType.getValue());
        return newLoyaltyPoints - oldLoyaltyPoints;
    }

    //Within this method I use regular expressions, here i used [\\d*] which consists of any values
    //in the alphabet, [0-9] equals all digits
    public void CashNumOnly() {
        TF_CashPayment.textProperty().addListener((observable, text, numberOnly) -> {
            if (!numberOnly.matches(("\\d*"))) { //! means any character which is a letter isn't allowed
                TF_CashPayment.setText(numberOnly.replaceAll("[^\\d.]", "")); //any non digits are replaced with a blankspace
            } //[^//d.], ^ is find anything which doesn't come after ^, // is multiple rather than / which is single, d refers to only digits and . allows . to be used
        });
    }

    //Method used to make sure only certain values are accepted, by using regex
    public void validateCard() {
        for (String s : Arrays.asList("Debit Card", "Credit Card", "Master Card")) { //Populates the combobox with these strings
            CardType.getItems().add(s);
            CardType.setOnAction((actionEvent) -> {
                int indexValue = CardType.getSelectionModel().getSelectedIndex();
                Object selectedCard = CardType.getSelectionModel().getSelectedItem();

                System.out.println("Index selection is: " + indexValue + ", Card Type selected is: " + selectedCard);
                System.out.println(CardType.getValue());
                TF_CardNumber.setPromptText(CardType.getValue() + " Number");
                TF_CardSortNum.setPromptText(CardType.getValue() + " Sort Code");

                TF_CardNumber.setDisable(false);
                TF_CardSortNum.setDisable(false);

                TF_CardSortNum.textProperty().addListener((observable, text, numberOnly) -> {
                    if (!numberOnly.matches("\\d*")) { //Any non digits found are removed from the textfield
                        TF_CardSortNum.setText(numberOnly.replaceAll("[^\\d]", ""));
                    }
                });

                TF_CardNumber.textProperty().addListener((observable, text, numberOnly) -> {
                    if (!numberOnly.matches("\\d*")) {
                        TF_CardNumber.setText(numberOnly.replaceAll("[^\\d]", ""));
                    }
                });
            });
        }
    }

    //Because cards have only 16 digit numbers, and 3 digit sort codes, any input which doesn't match this is incorrect
    @FXML
    public void cardType(ActionEvent actionEvent) {
        System.out.println(TF_CardNumber.getLength());
        Alert alert;
        //use of | is a bitwise operator, so if one of the following statements are false then the alert is thrown, best used for int and double types
        if (TF_CardNumber.getLength() > 16 | TF_CardNumber.getLength() < 16) {
            alert = new Alert(Alert.AlertType.WARNING, "Card Number Digits are Incorrect", ButtonType.OK);
            alert.showAndWait();
        } else if (TF_CardSortNum.getLength() > 3 | TF_CardSortNum.getLength() < 3) {
            alert = new Alert(Alert.AlertType.WARNING, "Sort Code Digits are Incorrect", ButtonType.OK);
            alert.showAndWait();
        }
        if (TF_CardNumber.getLength() == 16) {
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Card Number Validated", ButtonType.OK);
            alert.showAndWait();
        }
        if (TF_CardSortNum.getLength() == 3) {
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Sort Code Validated", ButtonType.OK);
            alert.showAndWait();
        }
        //&& is a logical operator so if both statements are true then the if statment is carried out
        if (TF_CardNumber.getLength() == 16 && TF_CardSortNum.getLength() == 3) {
            lbl_Change.setText("£0.00");
            openReceiptScreen();
        }
    }

    //This populates the listview in the GUI with values the customer chose in the shop screen GUI
    public void loyaltyCard() {
        listOfCustomers = cu.getListOfCustomers();
        loyaltyType.getItems().addAll(listOfCustomers);
    }

    //When this button is pressed if nothing from the combobox is selected a warning is thrown
    @FXML
    public void loadLoyaltyPoints(ActionEvent actionEvent) {
        if (loyaltyType.getSelectionModel().getSelectedIndex() == -1){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a Loyalty Card", ButtonType.OK);
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Loyalty points converted to Money is: " + shopScreen.formatGBP(cu.convertPoints(loyaltyType.getValue())), ButtonType.OK);
            alert.showAndWait();
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Your loyalty points removed from total: " + shopScreen.formatGBP(removeLoyaltyCardTotal()), ButtonType.OK);
            alert2.showAndWait();
            lbl_totalFromShopController.setText("" + shopScreen.formatGBP(removeLoyaltyCardTotal()));
            lbl_LoyaltyTotal.setText("" + removeLoyaltyCardTotal());
            lbl_LoyaltyPoints.setText("Discount applied to total: " + shopScreen.formatGBP(cu.convertPoints(loyaltyType.getValue())));
            BTN_AddPoints.setDisable(true);
            BTN_LoadPoints.setDisable(true);
            loyaltyType.setDisable(true);
        }
    }

    @FXML
    public void addLoyaltyPoints(ActionEvent actionEvent){
        if (loyaltyType.getSelectionModel().getSelectedIndex() == -1){ //Makes sure a value/index is chosen in the combobox
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a Loyalty Card", ButtonType.OK);
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Loyalty points Added to card is: " + addLoyaltyCardTotal(), ButtonType.OK);
            alert.showAndWait();
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Your loyalty points Added to your card's equals: " + cu.addPoints(loyaltyType.getValue()), ButtonType.OK);
            alert2.showAndWait();
            lbl_LoyaltyPoints.setText("Total Loyalty points on card: " + cu.addPoints(loyaltyType.getValue()));
            BTN_AddPoints.setDisable(true);
            BTN_LoadPoints.setDisable(true);
            loyaltyType.setDisable(true);
        }
    }

    public void openReceiptScreen() {
        System.out.println("Give Receipt To Customer");
        ReceiptController receipt = new ReceiptController(this); //changes the controller from checkout to receipt
        receipt.showStage();
        thisStage.close();
        shopScreen.closeScreen();
    }

    //These last methods allow the receipt controller to retrieve the values and use them in the receipt GUI
    public String getTotal() {
        return lbl_totalFromShopController.getText();
    }

    public ObservableList<Product> getShopping() {
        return shopList.getItems();
    }

    public String getChange() {
        return lbl_Change.getText();
    }

    public String getPoints(){ return lbl_LoyaltyPoints.getText();}
}