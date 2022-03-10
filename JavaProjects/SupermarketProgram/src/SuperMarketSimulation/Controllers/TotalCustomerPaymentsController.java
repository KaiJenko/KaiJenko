package SuperMarketSimulation.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TotalCustomerPaymentsController implements Serializable{

    public ListView<String> customerList;
    public ObservableList<String> orders;

    private final Stage thisStage;
    private final ReceiptController receiptController;

    public TotalCustomerPaymentsController(ReceiptController receiptController) {

        this.receiptController = receiptController;
        thisStage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/TotalCustomerPayments.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setTitle("Order Screen - Screen 4");
        }
        catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        orders = FXCollections.observableArrayList();
        customerList.setItems(orders);
        addToList();
    }

    @FXML
    private  void saveCustomerOrder(){
        try {
            FileOutputStream out = new FileOutputStream("CustomerPayments.dat", true);
            ObjectOutputStream oOut = new ObjectOutputStream(out);
            oOut.writeObject(new ArrayList<>(Collections.singleton((orders) + "\n"))); //As the file adds each data entry as one long line, \n is used to create a new line in the txt doc
            System.out.println(orders);
            oOut.flush();
            oOut.close();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Customer Data has not been saved successfully", ButtonType.OK);
            alert.showAndWait();
            System.err.println("Error : " + e);
        }
    }

    //Any data saved above can be loaded using this method
    @FXML
    private void loadCustomerOrder(){
        try {
            FileInputStream orderDataRetrieved = new FileInputStream("CustomerPayments.dat"); //Finds the file with the associated URL
            ObjectInputStream orderOutput = new ObjectInputStream(orderDataRetrieved); //Assigns the data in the file above to a new input stream
            List<String> list = (List<String>) orderOutput.readObject(); //Input stream is put into a list
            orders.addAll(list); //Loaded data is injected into the GUI listview
            customerList.setItems(orders);
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

    public void showStage() {
        thisStage.show();
    }

    public void addToList(){
        String p = receiptController.getTotal();
        orders.add(p);
        System.out.println(p);
    }
}
