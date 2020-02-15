package main;

import bussinessLayer.Restaurant;
import bussinessLayer.products.BaseProduct;
import bussinessLayer.products.CompositeProduct;
import bussinessLayer.products.MenuItem;
import dataLayer.RestaurantSerializator;
import presentationLayer.AdministratorGUI;
import presentationLayer.ChefGUI;
import presentationLayer.Controller;
import presentationLayer.WaiterGUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Main {

    public static void main(String[] args) {

        Restaurant restaurant = new Restaurant();
        AdministratorGUI administratorGUI = new AdministratorGUI(restaurant);
        WaiterGUI waiterGUI = new WaiterGUI(restaurant);
        ChefGUI chefGUI = new ChefGUI(restaurant);

        restaurant.addObserver(chefGUI);

        Controller controller = new Controller(restaurant, administratorGUI, waiterGUI, chefGUI);

        administratorGUI.setVisible(true);
        waiterGUI.setVisible(true);
        chefGUI.setVisible(true);
    }
}
