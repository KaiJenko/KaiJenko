package SuperMarketSimulation.Classes;

import SuperMarketSimulation._InheritedClasses.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ProductItems {

    private final ObservableList<Product> listOfProducts;

    public ProductItems() {
        listOfProducts = FXCollections.observableArrayList();
        createItems();
    }

    public void addProduct(Product product){
        this.listOfProducts.add(product);
    }

    public ObservableList<Product> getListOfProducts() {
        return listOfProducts;
    }

    //This method is used in the shop controller, it is used in conjunction with the checkboxes
    //it returns a observable list which contains all of the products of a certain producttype
    public ObservableList<Product> getFilteredProducts(ArrayList<String> productTypes) {
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
        for (Product product : listOfProducts){ //products found in observable list listofproducts
            for (String producttype : productTypes){ //which match the checkbox productype clicked
                if (product.getType().toString().equals(producttype)){
                    filteredProducts.add(product); //are added to the observable list
                }
            }
        }
        return filteredProducts;
    }

    //Used for Junit testing to find a product code/description
    public Product searchProducts (String search) {
        for (Product p : listOfProducts) {
            if (p.getProductCode().contains(search) || p.getProductDescription().contains(search)) {
                return p;
            }
        }
        return searchProducts("");
    }
    //Method which populates the main body of my main GUI, mainly in a flowpane, different product types
    //have different toStrings and attributes based on inheritance class
    private void createItems() {
        Product dicedBeef = new Product("Diced Beef", "kilo of lean diced shoulder", 9.15, ProductType.RAWMEAT, "Lean-diced-beef.jpg", 5);
        addProduct(dicedBeef);
        Product filletSteak = new Product("Fillet Steak", "Single fillet steak", 7,  ProductType.RAWMEAT,"fillet-steak.jpg", 2);
        addProduct(filletSteak);
        Product dicedChicken = new Product("Diced Chicken", "500 grams of diced chicken", 5.22,  ProductType.RAWMEAT,"dicedbreast.jpg", 10);
        addProduct(dicedChicken);
        Product sirloin = new Product("Sirloin Steak", "2 Steaks", 10.22, ProductType.RAWMEAT, "Sirloin.jpg", 40);
        addProduct(sirloin);

        Product rareBeef = new Product("Rare Beef", "Pack of sliced Rare Beef", 2.35, ProductType.COOKEDMEAT, "rareBeef.jpg", 110);
        addProduct(rareBeef);
        Product salami = new Product("Salami", "Pack of sliced Salami", 1.60, ProductType.COOKEDMEAT, "salami.jpg", 10);
        addProduct(salami);

        Product apple = new Product("Gala Apples", "6 apples", 1.25, ProductType.FRUITANDVEG, "apple.jpg", 10);
        addProduct(apple);
        Product carrots = new Product("Carrots", "Bag of Carrots", 0.89, ProductType.FRUITANDVEG, "carrot.jpg", 2);
        addProduct(carrots);
        Product watermelon = new Product("Watermelon", "1 small melon", 4.99, ProductType.FRUITANDVEG, "Watermelon.jpg", 4);
        addProduct(watermelon);
        Product gooseberry = new Product("GooseBerries", "Tub of Gooseberries", 1.45, ProductType.FRUITANDVEG, "gooseberries.jpg", 5);
        addProduct(gooseberry);

        Drink kopparberg = new Drink("Kopparberg", "4 bottles", 5.55, ProductType.DRINKS, 700, 4, "Kopparberg.jpg", 5);
        addProduct(kopparberg);
        Drink lucozade = new Drink("Lucozade", "bottle of Lucozade", 0.99, ProductType.DRINKS, 500, 0, "lucozadeorange.jpg", 2);
        addProduct(lucozade);
        Drink corona = new Drink("Corona", "4 bottles of Corona", 5.25, ProductType.DRINKS, 450,5,"corona.jpg", 0);
        addProduct(corona);

        Fashion nike = new Fashion("Nike Air", "Nike Air Max 97", 1018, ProductType.FASHION, "Lil Nas X", "10+", "97.jpeg", 1);
        addProduct(nike);
        Fashion supreme = new Fashion("Jumper", "Supreme Jumper", 240, ProductType.FASHION, "Supreme", "16 - 40", "supreme.jpg", 6);
        addProduct(supreme);

        Household toiletRoll = new Household("Toilet Roll", "9 Rolls", 4.55, ProductType.HOUSEHOLD, "Bathroom","Andrex" , "toiletroll.jpg", 3);
        addProduct(toiletRoll);
        Household soap = new Household("Hand Soap", "bottle of Handsoap", 1.00, ProductType.HOUSEHOLD, "Bathroom", "Carex", "soap.jpg", 6);
        addProduct(soap);
        Household deodorant = new Household("Deodorant", "Deodorant can", 1.50, ProductType.HOUSEHOLD, "Bedroom", "Axe", "deodorant.jpg", 3);
        addProduct(deodorant);

        PetItems dogfood = new PetItems("Dog Food", "500g of Dog food", 6.70, ProductType.HOUSEHOLD, "For Dogs", "dog.jpg", 20);
        addProduct(dogfood);
        PetItems catweed = new PetItems("Cat Nip", "packet of catnip", 4.50, ProductType.HOUSEHOLD, "For Cats","catnip.jpg", 15);
        addProduct(catweed);

    }

}

