package bussinessLayer.products;

import java.io.Serializable;

public abstract class MenuItem implements Serializable {

    protected String name;
    protected String weight;
    protected double price;

    private static final long serialVersionUID = 7847873692237390620L;

    public MenuItem() {
    }

    public MenuItem(String name, String weight) {
        this.name = name;
        this.weight = weight;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getWeight() {
        return weight;
    }

    public abstract double computePrice();

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return name + " " + " " + weight + " " + computePrice();
    }
}
