package lk.ijse.market.model;

public class OrderDetails {

    private String itemCode;
    private int orderQTY;
    private double discont;
    private double total;

    public OrderDetails(String itemCode, int orderQTY, double discont, double total) {
        this.itemCode = itemCode;
        this.orderQTY = orderQTY;
        this.discont = discont;
        this.total = total;
    }

    public OrderDetails() {
    }

    @Override
    public String toString() {
        return "OrderDetails{" +

                ", itemCode='" + itemCode + '\'' +
                ", orderQTY='" + orderQTY + '\'' +
                ", discont=" + discont +
                ", total=" + total +
                '}';
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getOrderQTY() {
        return orderQTY;
    }

    public void setOrderQTY(int orderQTY) {
        this.orderQTY = orderQTY;
    }

    public double getDiscont() {
        return discont;
    }

    public void setDiscont(double discont) {
        this.discont = discont;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
