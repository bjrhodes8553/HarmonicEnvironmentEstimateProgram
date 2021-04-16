package sample;

public class Material {
    String name;
    String unit;
    double price;
    String description;
    double quantity;

    public Material(String name, String unit, double price, String description) {
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.description = description;
    }

    public Material(String name, String unit, double price) {
        this.name = name;
        this.unit = unit;
        this.price = price;

    }

    public Material(String name, String unit, double price, double quantity) {
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.quantity = quantity;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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
        return "Material{" +
                "name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
