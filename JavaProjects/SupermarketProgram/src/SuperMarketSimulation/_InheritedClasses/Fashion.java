package SuperMarketSimulation._InheritedClasses;

import SuperMarketSimulation.Classes.ProductType;

public class Fashion extends Product{

    private String designer;
    private String ageRange;

    public Fashion(String productCode, String productDescription, double price, ProductType type, String designer, String ageRange, String imageName, int stockControl) {
        super(productCode, productDescription, price, type, imageName, stockControl);
        this.designer = designer;
        this.ageRange = ageRange;
    }

    @Override
    public String toString() {
        return getProductCode() + ": It contains: " + getProductDescription() + " It costs: "
                + formatGBP(getPrice()) + " The Designer is: " + designer + " It is made for the ages: " + ageRange;
    }
}
