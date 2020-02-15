package presentationLayer;

import bussinessLayer.Restaurant;
import bussinessLayer.products.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ChefGUI extends JFrame implements Observer {

    private Restaurant restaurant;
    private JTextArea textArea;

    public ChefGUI(Restaurant restaurant) {
        this.restaurant = restaurant;
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(170, 176, 200));
        textArea = new JTextArea();
        textArea.setBounds(10, 10, 200, 200);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        mainPanel.add(textArea);

        this.setContentPane(mainPanel);
        this.setSize(500, 400);
        this.setTitle("Chef");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void update(Observable o, Object arg) {
        String message = (String) arg;
        textArea.append(message + "\n");
    }
}
