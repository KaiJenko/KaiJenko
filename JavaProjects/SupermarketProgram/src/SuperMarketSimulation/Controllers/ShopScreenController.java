package SuperMarketSimulation.Controllers;

import SuperMarketSimulation._InheritedClasses.Product;
import SuperMarketSimulation.Classes.ProductItems;
import SuperMarketSimulation.Classes.ProductType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/*
This is my main class which contains all of the code used in my GUI for my supermarket where the user
will choose products to purchase, most of the user interaction is coded in this class, a listview
is used as the users basket and Vbox's as the products the customer can choose to purchase
 */

public class ShopScreenController {

    public ListView<Product> shopList;
    public HBox Hbox_EnumHolder;
    public FlowPane FP_FoodItems;
    public TextField TF_ProductCode;
    public TextField TF_Description;

    public Button BTN_MinShop;
    public Button BTN_MaxShop;
    public Button BTN_CardOnly;
    public Button BTN_CashOnly;
    public Label Label_total;

    private final ProductItems Pi = new ProductItems();
    private ObservableList<Product> order;
    private final Stage thisStage;
    private final int i = 10;
    private ObservableList<Product> listOfProducts, filteredProducts;

    //This is used with the start screen controller, when A specific button is pressed then this controller method is called to open this class and GUI
    public ShopScreenController() {
        thisStage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SuperMarketSimulation/FXML/ShopScreen.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setTitle("Shop Screen - Screen 1");
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

        listOfProducts = Pi.getListOfProducts(); //gets the observable list from ProductItems class and enters them in a new observable list
        order = FXCollections.observableArrayList();
        shopList.setItems(order); //observable list order is set to be put into the shop list listview

        drawProducts(listOfProducts); //Calls the method drawProducts, and carries out the method for anything stored in listofproducts

        //This is a loop for finding the values within the enumerated class, and then creating a checkbox
        for (ProductType s : ProductType.values()) {
            CheckBox c = new CheckBox(s.name());
            c.setId(s.name());
            Hbox_EnumHolder.setSpacing(20);
            Hbox_EnumHolder.getChildren().add(c);
            c.setOnAction(this::getCheckboxType); //when the checkbox is clicked, run the method
        }
    }

    //This method is for when the checkbox is checked
    private void getCheckboxType(ActionEvent actionEvent){
        ArrayList<String> selectedType = new ArrayList<>(); //creates a empty arraylist
        for (Node checkbox : Hbox_EnumHolder.getChildren()) { //gets the checkboxes inside the HBox
            CheckBox cb = (CheckBox) checkbox; //casts the checkbox from a node back to a checkbox
            if(cb.isSelected()) { //retrieves the values which are assigned to the productType of the checkbox
                selectedType.add(cb.getId()); //Adds the checkbox value ID to the arraylist
            }
        }
        filteredProducts = Pi.getFilteredProducts(selectedType);
        FP_FoodItems.getChildren().clear(); //Removes all products from the flowpane in the GUI
        if (filteredProducts.size() == 0){
            drawProducts(listOfProducts); //Recreates the products VBox's
        }
        else {
            drawProducts(filteredProducts); //only creates specific products VBox's, dependant on the checkbox
        }
    }

    //This is the method which creates the graphics for the different products from the Product class
    public void drawProducts(ObservableList<Product> products){
        for (Product p : products) { //loops thorough and gets all of the products from the product class
            VBox v = new VBox();
            Button b = new Button();
            Label l = new Label();

            v.setMaxWidth(80);
            v.setMaxHeight(80);
            v.setSpacing(2);
            v.setAlignment(Pos.CENTER);
            v.getStyleClass().add("vbox");

            ImageView i = new ImageView(new Image("SuperMarketSimulation/Images/" + p.getImageName())); //Generates a image for each of the products via the path stated
            i.setFitHeight(70);
            i.setFitWidth(70);

            b.setText(p.getProductCode());
            b.setWrapText(true);
            b.setOnMouseClicked(event -> StockControl(p)); //Uses a lamda expression for when a button is clicked, a method is carried out
            b.setMaxHeight(100);
            b.setMaxWidth(100);
            b.setFont(Font.font("MV Boli", 9));

            l.setText(formatGBP(p.getPrice()));
            l.setFont(Font.font("MV Boli", 11));
            l.setMaxWidth(80);
            l.setMaxHeight(100);
            l.setWrapText(true);
            l.setAlignment(Pos.CENTER);

            v.getChildren().addAll(i, b, l); //adds all of the different objects to a VBox
            FP_FoodItems.getChildren().add(v); //adds the VBox to the flowpane in the GUI
            FP_FoodItems.setVgap(10);
            FP_FoodItems.setHgap(20);
            FP_FoodItems.setPadding(new Insets(10, 0, 10, 0));
        }
    }

    //This is a method for stock control of the products, it is used in the VBoxConstructor method
    private void StockControl(Product p) {
        int stock = p.getStockControl();
        if (stock >= 0) {
            order.add(p);
            System.out.println(p);
            lbl_Total();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No more of these Items left, choose another product", ButtonType.OK);
            alert.showAndWait();
        }
    }

    //A common method used throughout the program to format any doubles into a currency type such as GBP
    public String formatGBP(double p) {
        NumberFormat gb = NumberFormat.getCurrencyInstance(Locale.UK);
        return gb.format(p);
    }

    //Adds all of the product prices to a double variable
    public double calculateTotal() {
        double total = 0;
        for (Product product : order) {
            total += product.getPrice();
        }
        System.out.println(total);
        return total;
    }

    private void lbl_Total() {
        Label_total.setText(formatGBP(calculateTotal()));
    }

    //ensures the customer has chosen some products and added to the basket
    @FXML
    public void payForShop(ActionEvent actionEvent) {
        Alert alert;
        if (order.isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR, "Please add a item to the basket", ButtonType.OK);
            alert.showAndWait();
        }
        else {
            BTN_MaxShop.setDisable(false);
            BTN_MinShop.setDisable(false);
            BTN_CardOnly.setDisable(false);
            BTN_CashOnly.setDisable(false);
        }
    }

    @FXML
    public void cardOnly() {
        Alert alert;
        alert = new Alert(Alert.AlertType.INFORMATION, "Proceed to Card only Checkout?\n Cash payment will not be available", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait(); //uses buttonType to give the user option to choose yes or no
        if (result.get() == ButtonType.YES) {
            openCheckoutCardOnly(); //if the user picks yes in the GUI the corresponding checkout is opened
        }

        else if (result.get() == ButtonType.NO){
            BTN_MinShop.setDisable(true);
            BTN_MaxShop.setDisable(true);
            BTN_CashOnly.setDisable(true);
            BTN_CardOnly.setDisable(true);
        }
    }

    @FXML
    public void cashOnly(ActionEvent actionEvent) {
        Alert alert;
        alert = new Alert(Alert.AlertType.INFORMATION, "Proceed to Cash Only Checkout?\n Card Payment will not be available", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            openCheckoutCashOnly();
        }

        else if (result.get() == ButtonType.NO){
            BTN_MinShop.setDisable(true);
            BTN_MaxShop.setDisable(true);
            BTN_CashOnly.setDisable(true);
            BTN_CardOnly.setDisable(true);
        }
    }

    @FXML
    public void minShop(ActionEvent actionEvent) {
        if (order.size() <= i) {
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION, "Do you want to continue to Min Checkout? \nYou will not have access to loyalty points", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                openCheckoutScreenMin();
            }
            else if (result.get() == ButtonType.NO){
                BTN_MinShop.setDisable(true);
                BTN_MaxShop.setDisable(true);
                BTN_CashOnly.setDisable(true);
                BTN_CardOnly.setDisable(true);
            }
        }
        else {
            Alert alert;
            alert = new Alert(Alert.AlertType.WARNING, "Too Many Items In Basket, would you like to \nopen more than 10 items checkout?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                openCheckoutScreenMax();
                System.out.println("Too many Items in Basket");
            }
            else if (result.get() == ButtonType.NO) {
                BTN_MinShop.setDisable(true);
                BTN_MaxShop.setDisable(true);
                BTN_CashOnly.setDisable(true);
                BTN_CardOnly.setDisable(true);
            }
        }
    }

    //Checkout for if the basket has more than 10 items, if not the 10 items or less checkout is opened
    @FXML
    public void maxShop(ActionEvent actionEvent) {
        if (order.size() > i) {
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION, "Do you want to continue to Max Checkout?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                openCheckoutScreenMax();
            }
            else if (result.get() == ButtonType.NO){
                BTN_MinShop.setDisable(true);
                BTN_MaxShop.setDisable(true);
                BTN_CashOnly.setDisable(true);
                BTN_CardOnly.setDisable(true);
            }
        }
        else {
            Alert alert;
            alert = new Alert(Alert.AlertType.WARNING, "You don't have enough items in your basket, would you \nlike to open 10 items or less checkout?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                openCheckoutScreenMin();
            }
            else if (result.get() == ButtonType.NO) {
                BTN_MinShop.setDisable(true);
                BTN_MaxShop.setDisable(true);
                BTN_CashOnly.setDisable(true);
                BTN_CardOnly.setDisable(true);
            }
        }
    }

    @FXML
    public void Reset(ActionEvent actionEvent) {
        if (order.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please Shop First", ButtonType.OK);
            alert.showAndWait();
            System.out.println("Please add products to Basket");
        } else {
            BTN_CardOnly.setDisable(true);
            BTN_CashOnly.setDisable(true);
            BTN_MinShop.setDisable(true);
            BTN_MaxShop.setDisable(true);
        }
    }

    //Next two methods are using with the SearchBasket method to find a product in the customers basket
    public Product findProductCode(String id) {
        Product find;
        int indexLocation = -1;

        for (int i = 0; i < order.size(); i++) {
            find = order.get(i);

            if (find.getProductCode().equals(id)) { //If the value received from the getter in Product is equal to a product in the listview
                indexLocation = i;
                System.out.println(find.getProductCode());
                break;
            }
        }
        if (indexLocation == -1) {
            return null;
        } else {
            return order.get(indexLocation); //select the location of the product found
        }
    }

    //Same basic concept as method above instead of product code, searches for the Description
    public Product findProductDescription(String id) {
        Product find;
        int indexLocation = -1;

        for (int i = 0; i < order.size(); i++) {
            find = order.get(i);

            if (find.getProductDescription().equals(id)) {
                indexLocation = i;
                System.out.println(find.getProductDescription());
                break;
            }
        }
        if (indexLocation == -1) {
            return null;
        } else {
            return order.get(indexLocation);
        }
    }

    //Method for taking the users input and putting it into the two methods above to find a match
    @FXML
    public void SearchBasket(ActionEvent actionEvent) {
        Product searchedFor;
        String findCode = TF_ProductCode.getText();
        String findDescription = TF_Description.getText();

        if (findCode.equals("") && findDescription.equals("")) { //ensures no empty textfield
            Alert alert = new Alert(Alert.AlertType.WARNING, "Enter a Product code Or Description", ButtonType.OK);
            alert.showAndWait();
        }
        else {
            if (!findCode.equals("")) { //Uses the Product code and description methods
                searchedFor = findProductCode(findCode);
            }
            else {
                searchedFor = findProductDescription(findDescription);
            }
            if (searchedFor == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Product Not Found", ButtonType.OK);
                alert.showAndWait();
            }
            else { //If a match is found with the users input then select and highlight the corresponding product
                shopList.getSelectionModel().select(searchedFor);
                shopList.scrollTo(searchedFor);
                setAllTextFields(searchedFor);
            }
        }
    }

    //Removes a product the user chooses with their mouse click
    @FXML
    public void removeProduct(ActionEvent actionEvent) {
        int selectionNumber = shopList.getSelectionModel().getSelectedIndex();

        if (selectionNumber == -1) { //Makes sure the user has selected a product
            Alert alert = new Alert(Alert.AlertType.ERROR, "Select a item to remove", ButtonType.OK);
            alert.showAndWait();
        }
        else {
            order.remove(selectionNumber);
            TF_Description.setText("");
            TF_ProductCode.setText("");
            lbl_Total(); //Refreshes the total of the users shop by removing the products price
        }
        if (order.isEmpty()) {
            BTN_MinShop.setDisable(true);
            BTN_MaxShop.setDisable(true);
        }

    }

    private void setAllTextFields(Product e) {
        TF_ProductCode.setText(e.getProductCode());
        TF_Description.setText(String.valueOf(formatGBP(e.getPrice())));
    }

    //In the GUI the user has the option to save their basket if they need to close the application for any reason
    @FXML
    private  void saveCustomerOrder(){
        try {
            FileOutputStream orderDataSaved = new FileOutputStream("CustomerOrder.dat"); //Creates an empty file
            ObjectOutputStream orderInput = new ObjectOutputStream(orderDataSaved); //Saves the basket data to the empty file
            orderInput.writeObject(new ArrayList<>(order));
            orderInput.flush(); //Ensures the data is written
            orderInput.close(); //Before the byte stream is closed
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Data has been saved successfully", ButtonType.OK);
            alert.showAndWait();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Data has not been saved successfully", ButtonType.OK);
            alert.showAndWait();
            System.err.println("Error : " + e);
        }
    }

    //Any data saved above can be loaded using this method
    @FXML
    private void loadCustomerOrder(){
        try {
            FileInputStream orderDataRetrieved = new FileInputStream("CustomerOrder.dat"); //Finds the file with the associated URL
            ObjectInputStream orderOutput = new ObjectInputStream(orderDataRetrieved); //Assigns the data in the file above to a new input stream
            List<Product> list = (List<Product>) orderOutput.readObject(); //Input stream is put into a list
            order.setAll(list); //Loaded data is injected into the GUI listview
            shopList.setItems(order);
            orderOutput.close();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Data has been Loaded successfully", ButtonType.OK);
            alert.showAndWait();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Data has not been loaded Successfully", ButtonType.OK);
            alert.showAndWait();
            e.printStackTrace();
            System.out.println("Error : " + e);
        }
    }

    @FXML
    private void close(){
        thisStage.close();
    }

    //Taskbar accross the top of the GUI allows the user to save and load data, but also load information GUI
    @FXML
    private void HelpScreen() throws IOException{
        Scene newScene = new Scene(FXMLLoader.load(getClass().getResource("../FXML/InformationScreen.fxml")));
        Stage popUpStage = new Stage();
        popUpStage.setScene(newScene);
        popUpStage.initModality(Modality.APPLICATION_MODAL); //This screen has to be closed before interacting with other GUIs
        popUpStage.show();
        popUpStage.setTitle("Extra Information");
    }

    //The next four methods are used to open different checkout types, all are dependant on user input
    //Opens the 10 items or less screen and disables use of loyalty points
    private void openCheckoutScreenMin() {
        System.out.println("Transfer to Min Checkout Screen");
        CheckoutController checkout = new CheckoutController(this);
        Stage checkoutPopUp = new Stage();
        checkoutPopUp.initModality(Modality.NONE);
        checkout.showStage();
        checkout.loyaltyType.setDisable(true);
        checkout.BTN_LoadPoints.setDisable(true);
        checkout.BTN_AddPoints.setDisable(true);
        checkout.BTN_PayCash.setDisable(false);
        checkout.BTN_PayCard.setDisable(false);
        checkout.lbl_CheckoutType.setText("10 Items or Less");
    }

    //Opens the more than 10 items checkout, access to everything
    private void openCheckoutScreenMax() {
        System.out.println("Transfer to Max Checkout Screen");
        CheckoutController checkout = new CheckoutController(this);
        Stage checkoutPopUp = new Stage();
        checkoutPopUp.initModality(Modality.NONE);
        checkout.showStage();
        checkout.BTN_PayCash.setDisable(false);
        checkout.BTN_PayCard.setDisable(false);
        checkout.lbl_CheckoutType.setText("More Than 10 Items");
    }

    //Opens card only checkout, disables use of cash payment
    private void openCheckoutCardOnly() {
        System.out.println("Transfer to Max Checkout Screen");
        CheckoutController checkout = new CheckoutController(this);
        Stage checkoutPopUp = new Stage();
        checkoutPopUp.initModality(Modality.NONE);
        checkout.showStage();
        checkout.BTN_PayCard.setDisable(false);
        checkout.lbl_CheckoutType.setText("Card Only");
    }

    //Opens cash only checkout, disables card payment
    public void openCheckoutCashOnly() {
        System.out.println("Transfer to Max Checkout Screen");
        CheckoutController checkout = new CheckoutController(this);
        Stage checkoutPopUp = new Stage();
        checkoutPopUp.initModality(Modality.NONE);
        checkout.showStage();
        checkout.BTN_PayCash.setDisable(false);
        checkout.lbl_CheckoutType.setText("Cash Only");
    }

    public void closeScreen(){
        thisStage.close();
    }

    //These last methods are used to return data and use it in the checkout controller and GUI
    public double getEnteredLabel() {
        return calculateTotal();
    }

    public ObservableList<Product> getList(){
        return shopList.getItems();
    }
}

