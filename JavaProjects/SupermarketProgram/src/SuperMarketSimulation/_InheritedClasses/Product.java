package SuperMarketSimulation._InheritedClasses;

import SuperMarketSimulation.Classes.ProductType;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

/*
This is my inheritance parent class which all of my ProductTypes inherit their attributes from,
the reason this isn't a abstract class is when calling the classes in my shop controller i would have
to call all of the individual classes but with it staying public i can call this main class and have access
to the inherited classes as well
 */

public class Product implements Serializable {

    private final String ProductCode;
    private String ProductDescription;
    private final Double price;
    private final ProductType type;
    private final String imageName;
    private int stockControl;

    //This is the super constructor which my other classes inherit as they are used in all of them
    public Product(String productCode, String productDescription, double price, ProductType type, String imageName, int stockControl) {
        ProductCode = productCode;
        ProductDescription = productDescription;
        this.price = price;
        this.type = type;
        this.imageName = imageName;
        this.stockControl = stockControl;
    }

    public String formatGBP(double p) {
        NumberFormat gb = NumberFormat.getCurrencyInstance(Locale.UK);
        return gb.format(p);
    }

    public String toString() {
        return ProductCode + ": It Contains " + ProductDescription +
                ", It Costs: " + formatGBP(price);
    }

    //By creating all of these getters my inheritance classes have access to the parents values,
    //allowing my toString methods to use the values and override the parents toString
    public String getProductCode() {
        return ProductCode;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public double getPrice() {
        return price;
    }

    public String getImageName() {
        return imageName;
    }

    public ProductType getType() {
        return type;
    }

    //This getter removes 1 from the overall value so when in the shop controller a item is chosen,
    //the stock control value removes one by calling this getter
    public int getStockControl() {
        return stockControl -= 1;
    }
}