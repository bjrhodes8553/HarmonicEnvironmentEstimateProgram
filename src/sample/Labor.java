package sample;

public class Labor {
    String name;
    double price;
    String description;
    double quantity;

    public Labor(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
    public Labor(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Labor(String name, double price, double quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Labor{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
