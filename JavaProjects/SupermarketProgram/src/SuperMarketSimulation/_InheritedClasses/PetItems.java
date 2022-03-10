package SuperMarketSimulation._InheritedClasses;

import SuperMarketSimulation.Classes.ProductType;

public class PetItems extends Product {

    private String petType;

    public PetItems(String productCode, String productDescription, double price, ProductType type, String petType, String imageName, int stockControl) {
        super(productCode, productDescription, price, type, imageName, stockControl);
        this.petType = petType;
    }

    @Override
    public String toString(){
        return getProductCode() + " It contains: " + getProductDescription() + " It cost's: " + getPrice()
                + " The animal it's for: " + petType;
    }
}
