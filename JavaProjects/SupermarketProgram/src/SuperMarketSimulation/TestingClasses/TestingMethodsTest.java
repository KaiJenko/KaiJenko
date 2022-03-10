package SuperMarketSimulation.TestingClasses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestingMethodsTest {
    TestingMethods tm;

    @BeforeEach
    void setUp() {
        tm = new TestingMethods();
    }

    @Test
    void formatGBP() {
        String result = tm.formatGBP(100.5);
        assertEquals("£100.50", result);
    }

    @Test
    void change() {
        double change = tm.change();
        assertEquals(50, change);
    }

    @Test
    void remaining() {
        double remaining = tm.change();
        assertEquals(50, remaining);
    }
}