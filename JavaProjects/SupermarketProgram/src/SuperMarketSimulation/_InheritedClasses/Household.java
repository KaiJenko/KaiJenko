package SuperMarketSimulation._InheritedClasses;

import SuperMarketSimulation.Classes.ProductType;

public class Household extends Product{

    private String householdType;
    private String brand;

    public Household(String productCode, String productDescription, double price, ProductType type, String householdType, String brand, String imageName, int stockControl) {
        super(productCode, productDescription, price, type, imageName, stockControl);
        this.householdType = householdType;
        this.brand = brand;
    }

    @Override
    public String toString(){
        return getProductCode() + ": It Contains " + getProductDescription() +
                ", It Costs: " + formatGBP(getPrice()) + " It is for use in the: " + householdType + " Brand is: " + brand;
    }
}
