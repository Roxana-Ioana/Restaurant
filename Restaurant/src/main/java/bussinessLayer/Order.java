package bussinessLayer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Order {

    private int orderId;
    private LocalDate date;
    private int table;

    public Order(int orderId, String date, int table) {
        this.orderId = orderId;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.date = LocalDate.parse(date, formatter);
        this.table = table;
    }

    public int getOrderId() {
        return orderId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getTable() {
        return table;
    }

    @Override
    public int hashCode() {
        return orderId;
    }

    @Override
    public boolean equals(Object obj) {

        Order order = (Order) obj;
        if (orderId == order.getOrderId()) {
            if (date.equals(order.getDate())) {
                if (table == order.getTable()) {
                    return true;
                }
            }
        }
        return false;
    }
}
