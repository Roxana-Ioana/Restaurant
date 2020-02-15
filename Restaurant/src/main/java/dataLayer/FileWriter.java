package dataLayer;

import bussinessLayer.Order;
import bussinessLayer.products.MenuItem;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public class FileWriter {

    public static void writeInFile(Order order, List<MenuItem> data, String fileName) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(fileName))) {
            bufferedWriter.write("Order: " + order.getOrderId() + "\n");
            bufferedWriter.write("Date: " + order.getDate() + "\n");
            bufferedWriter.write("Table nr: " + order.getTable() + "\n");

            for (MenuItem m : data) {
                bufferedWriter.write(m.toString() + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
