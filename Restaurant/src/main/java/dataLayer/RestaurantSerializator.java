package dataLayer;

import bussinessLayer.Restaurant;
import bussinessLayer.products.MenuItem;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantSerializator {

    public static List<MenuItem> readFromFile() {

        try (FileInputStream fileIn = new FileInputStream("products.txt");
             ObjectInputStream in = new ObjectInputStream(fileIn);) {

            List<MenuItem> menuItems = (List<MenuItem>) in.readObject();
            return menuItems;

        } catch (Exception i) {
            i.printStackTrace();
        }

        return null;
    }

    public static void writeInFile(List<MenuItem> menu) {

        try (FileOutputStream fileOut = new FileOutputStream("products.txt");
             ObjectOutputStream out = new ObjectOutputStream(fileOut);) {

            out.writeObject(menu);

        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
