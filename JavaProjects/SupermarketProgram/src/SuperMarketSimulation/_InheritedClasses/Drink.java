package SuperMarketSimulation._InheritedClasses;

import SuperMarketSimulation.Classes.ProductType;

public class Drink extends Product {

    private int Units;
    private final int Millilitres;

    //This is the constructor received from the parent class, any attributes added after the super are
    //attributes unique to this class
    public Drink(String productCode, String productDescription, double price, ProductType type, int millilitres, int units, String imageName, int stockControl) {
        super(productCode, productDescription, price, type, imageName, stockControl);
        Units = units;
        Millilitres = millilitres;
    }

    //Here the toString method from the parent is overridden, so when a drink is created this is the
    //layout which will be used, the getters from the parent class are used to retrieve inherited attributes
    @Override
    public String toString() {
        return getProductCode() + ": It contains: " + getProductDescription() + " It costs: "
                + formatGBP(getPrice()) + " Its alcohol content is: " + Units + " the volume is: " + Millilitres;
    }
}