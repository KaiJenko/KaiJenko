package SuperMarketSimulation.TestingClasses;

import SuperMarketSimulation.Classes.ProductItems;
import SuperMarketSimulation.Classes.ProductType;
import SuperMarketSimulation._InheritedClasses.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ProductItemsTest {
    private ProductItems pi;

    @BeforeEach
    void setUp() {
        pi = new ProductItems();
        Product dicedBeef = new Product("Diced Beef", "kilo of lean diced shoulder", 9.15, ProductType.RAWMEAT, "Lean-diced-beef.jpg", 5);
        pi.addProduct(dicedBeef);
        Product filletSteak = new Product("Fillet Steak", "Single fillet steak", 7,  ProductType.RAWMEAT,"fillet-steak.jpg", 2);
        pi.addProduct(filletSteak);
        Product dicedChicken = new Product("Diced Chicken", "500 grams of diced chicken", 5.22,  ProductType.RAWMEAT,"dicedbreast.jpg", 10);
        pi.addProduct(dicedChicken);
        Product sirloin = new Product("Sirloin Steak", "2 Steaks", 10.22, ProductType.RAWMEAT, "Sirloin.jpg", 40);
        pi.addProduct(sirloin);
    }

    @Test
    void searchProducts() throws IOException {
        Product result = pi.searchProducts("dicedBeef");
        assertEquals("Diced Beef", result.getProductCode());

    }
}