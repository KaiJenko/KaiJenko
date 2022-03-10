package SuperMarketSimulation.Classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class CustomerLoyaltyCard {

    private ObservableList<LoyaltyCard> listOfCustomers;

    public void retrieveCustomer(LoyaltyCard loyal){
        this.listOfCustomers.add(loyal);
    }

    public ObservableList<LoyaltyCard> getListOfCustomers() {
        return listOfCustomers;
    }

    public CustomerLoyaltyCard(){
        listOfCustomers = FXCollections.observableArrayList();
        createCustomers();
    }

    //This method takes the points assigned to each customers name and converts them into money,
    //which can be applied to the checkout sale, conversion rates depend on points threshold
    public double convertPoints(LoyaltyCard loyalty){
        int customerPoints = loyalty.getCustomerPoints();
        if (customerPoints > 1000){
            double cashAmount;
            cashAmount = customerPoints * 0.003;
            return cashAmount;
        }
        if (customerPoints < 1000 && customerPoints > 100){
            double cashAmount;
            cashAmount = customerPoints * 0.002;
            return cashAmount;
        }
        if (customerPoints < 100 && customerPoints > 0){
            double cashAmount;
            cashAmount = customerPoints * 0.001;
            return cashAmount;
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No points to convert", ButtonType.OK);
            alert.showAndWait();
        }
        return 0;
    }

    //This method add points based on how many the card already contains, it is called when a button
    //is pressed in the checkout GUI
    public double addPoints(LoyaltyCard loyalty){
        double customerLoyaltyPoints;
        customerLoyaltyPoints = loyalty.getCustomerPoints();
        if (customerLoyaltyPoints > 1000){
            customerLoyaltyPoints += 200;
            return customerLoyaltyPoints;
        }
        if (customerLoyaltyPoints < 1000 && customerLoyaltyPoints > 100) {
            customerLoyaltyPoints += 100;
            return customerLoyaltyPoints;
        }
        if (customerLoyaltyPoints < 100 && customerLoyaltyPoints >= 1) {
            customerLoyaltyPoints += 50;
            return customerLoyaltyPoints;
        }
        if (customerLoyaltyPoints == 0){
            customerLoyaltyPoints += 1;
            return customerLoyaltyPoints;
        }
        return 0;
    }


    public double getPoints(LoyaltyCard loyal){
        return loyal.getCustomerPoints();
    }

    //This is the data which populates the combobox on my checkout GUI
    private void createCustomers(){
        LoyaltyCard steven = new LoyaltyCard("Steven McGuire", 30, 1500, 1234);
        LoyaltyCard ilias = new LoyaltyCard("Ilias Tachmazidis", 85, 200, 6728 );
        LoyaltyCard minsi = new LoyaltyCard("Minsi Chen", 10, 0, 9999);
        LoyaltyCard natasha = new LoyaltyCard("Natasha", 19, 70, 4566);
        LoyaltyCard emily = new LoyaltyCard("Emily", 18, 450, 4868);
        LoyaltyCard connor = new LoyaltyCard("Connor", 17, 20, 6783);
        LoyaltyCard jack = new LoyaltyCard("Jack Dyson", 18, 50, 9489);
        LoyaltyCard seb = new LoyaltyCard("Sebastien Naylor", 19, 5000, 3683);
        LoyaltyCard aadam = new LoyaltyCard("Aadam", 19, 34, 1245);

        //RetrieveCustomer assigns the values of the LoyaltyCard declarations to an observable array list
        retrieveCustomer(steven);
        retrieveCustomer(ilias);
        retrieveCustomer(minsi);
        retrieveCustomer(natasha);
        retrieveCustomer(emily);
        retrieveCustomer(connor);
        retrieveCustomer(jack);
        retrieveCustomer(seb);
        retrieveCustomer(aadam);
    }


}
