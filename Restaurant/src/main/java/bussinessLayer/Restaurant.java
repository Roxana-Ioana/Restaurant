package bussinessLayer;

import bussinessLayer.products.CompositeProduct;
import bussinessLayer.products.MenuItem;
import dataLayer.FileWriter;
import dataLayer.RestaurantSerializator;

import java.io.Serializable;
import java.util.*;
import java.util.Observer;

/**
 * @invariant isWellFormed
 */
public class Restaurant extends Observable implements RestaurantProcessing{

    private HashMap<Order, List<MenuItem>> orders;
    private List<MenuItem> menuItems;

    public Restaurant() {
        menuItems = new ArrayList<>();
        orders = new HashMap<>();
        menuItems = RestaurantSerializator.readFromFile();
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }


    protected boolean isWellFormed()
    {
        return menuItems.size()!=0;
    }

    /**
     * @param menuItem
     * @pre menuItem!=null
     * @post menuItems.size()>0
     */
    @Override
    public void createMenuItem(MenuItem menuItem) {
        assert (menuItem != null);
        menuItems.add(menuItem);
        RestaurantSerializator.writeInFile(menuItems);
    }

    /**
     * @param index
     * @param editedMenuItem
     * @pre index>0 && editedMenuItem!=null
     * @post menuItems.size()==menuItems.size()@pre
     */
    @Override
    public void editMenuItem(int index, MenuItem editedMenuItem) {
        assert (editedMenuItem != null);
        assert (index > 0);
        menuItems.remove(index);
        menuItems.add(editedMenuItem);
        RestaurantSerializator.writeInFile(menuItems);
    }

    /**
     * @param index
     * @pre index>0
     * @post menuItems.size()==menuItems.size()@pre-1
     */
    @Override
    public void deleteMenuItem(int index) {
        menuItems.remove(index);
        RestaurantSerializator.writeInFile(menuItems);
    }

    /**
     * @param order
     * @param menuItems
     * @pre order!=null
     * @post orders.size()==orders.size@pre+1
     */
    @Override
    public void createNewOrder(Order order, List<MenuItem> menuItems) {
        assert (order != null);
        if (!orders.containsKey(order)) {
            orders.put(order, menuItems);
        }
    }

    /**
     * @param order
     * @param menuItem
     * @pre order!=null && menuItem!=null
     * @post menuItems.size()==menuItems.size()@pre+1
     */
    public void addMenuItemsToOrder(Order order, MenuItem menuItem) {
        assert(order!=null);
        assert(menuItem!=null);
        if (orders.containsKey(order)) {
            List<MenuItem> menuItems = orders.get(order);
            menuItems.add(menuItem);

            if(menuItem instanceof CompositeProduct)
            {
                String message = "Order nr. " + order.getOrderId() + ": " + menuItem.getName();
                setChanged();
                notifyObservers(message);
            }
        }
    }

    /**
     * @param
     * @return
     * @pre order!=null
     * @post
     */
    @Override
    public float computePrice(Order order) {
        assert (order != null);
        float price = 0;
        for (MenuItem menuItem : orders.get(order)) {
            price += menuItem.computePrice();
        }
        return price;
    }

    /**
     * @param order
     * @param fileName
     * @pre order!=null && fileName!=null
     * @post
     */
    @Override
    public void generateBill(Order order, String fileName) {
        assert (order != null);
        assert (fileName != null);
        List<MenuItem> menuItems = orders.get(order);
        FileWriter.writeInFile(order, menuItems, fileName);
    }

    public HashMap<Order, List<MenuItem>> getOrders() {
        return orders;
    }

}
