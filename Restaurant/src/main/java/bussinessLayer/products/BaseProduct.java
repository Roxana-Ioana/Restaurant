package bussinessLayer.products;

public class BaseProduct extends MenuItem {

    public BaseProduct(String name, String weight, double price) {
        super(name, weight);
        this.price = price;
    }

    @Override
    public double computePrice() {
        return price;
    }
}
