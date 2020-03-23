package bussinessLayer;

import bussinessLayer.products.MenuItem;

import java.util.List;

public interface RestaurantProcessing {

    //Methods for administrator
    void createMenuItem(MenuItem menuItem);

    void editMenuItem(int index, MenuItem editedMenuItem);

    void deleteMenuItem(int index);

    //Methods for waiter
    void createNewOrder(Order order, List<MenuItem> menuItems);

    float computePrice(Order order);

    void generateBill(Order order, String fileName);
}
