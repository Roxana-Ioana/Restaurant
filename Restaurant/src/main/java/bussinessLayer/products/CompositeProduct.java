package bussinessLayer.products;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompositeProduct extends MenuItem {

    private List<MenuItem> componentProducts;

    public CompositeProduct() {
    }

    public CompositeProduct(String name, String weight, List<MenuItem> componentProducts) {
        super(name, weight);
        this.componentProducts = new ArrayList<>();
        this.componentProducts = componentProducts;
        this.price = 0.00;
    }

    public CompositeProduct(String name, String weight) {
        super(name, weight);
        this.componentProducts = new ArrayList<>();
    }

    @Override
    public double computePrice() {
        double price = 0;
        for (MenuItem product : componentProducts) {
            price += product.getPrice();
        }
        return price;
    }

    public void addComponent(MenuItem menuItem) {
        componentProducts.add(menuItem);
    }

    public List<MenuItem> getComponentProducts() {
        return componentProducts;
    }

    public void removeMenuItem(int index) {
        componentProducts.remove(index);
    }

    public void setComponentProducts(List<MenuItem> componentProducts) {
        this.componentProducts = componentProducts;
    }
}
