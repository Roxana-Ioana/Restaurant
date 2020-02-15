package bussinessLayer;

import bussinessLayer.products.MenuItem;

import java.util.List;

public interface RestaurantProcessing {

    //Methods for administrator
    //create new menu item
    void createMenuItem(MenuItem menuItem);

    //edit new menu item
    void editMenuItem(int index, MenuItem editedMenuItem);

    //delete new menu item
    void deleteMenuItem(int index);

    //Methods for waiter
    //create new order
    void createNewOrder(Order order, List<MenuItem> menuItems);

    //compute price
    float computePrice(Order order);

    //generate bill in .txt
    void generateBill(Order order, String fileName);
}
