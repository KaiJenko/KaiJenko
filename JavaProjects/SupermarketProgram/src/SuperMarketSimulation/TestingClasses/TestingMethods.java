package SuperMarketSimulation.TestingClasses;

import java.text.NumberFormat;
import java.util.Locale;

//This is a class of any methods I extracted from my controller classes to test them with Junit
public class TestingMethods {

    public double change() { //If the user gives more money than needed they are given change
        double total = 100;
        double cash = 150;
        return cash - total;
    }

    public double remaining() { //If the user doesn't give enough money then a remaining value is shown
        double total = 100;
        double cash = 50;
        return total - cash;
    }

    public String formatGBP(double p) {
        NumberFormat gb = NumberFormat.getCurrencyInstance(Locale.UK);
        return gb.format(p);
    }



}
