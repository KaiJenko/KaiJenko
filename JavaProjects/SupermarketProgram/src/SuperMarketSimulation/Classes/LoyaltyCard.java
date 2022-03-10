package SuperMarketSimulation.Classes;

public class LoyaltyCard {

    private final String customerName;
    private final int customerAge;
    private final int customerPoints;
    private final int customerPin;

    public LoyaltyCard(String customerName, int customerAge, int customerPoints, int customerPin) {
        this.customerName = customerName;
        this.customerAge = customerAge;
        this.customerPoints = customerPoints;
        this.customerPin = customerPin;
    }

    public String toString() {
        return customerName + ", " + customerAge + " years old, Points on card: " + customerPoints;
    }

    public int getCustomerPoints() {
        return customerPoints;
    }

    public int getCustomerPin() {
        return customerPin;
    }
}
