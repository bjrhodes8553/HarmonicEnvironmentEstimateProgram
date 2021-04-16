package sample;

public class QuoteTableObjectThing {

  public QuoteTableObjectThing(String name, Double price, String unit,
      int quantity) {
    this.name = name;
    this.price = price;
    this.unit = unit;
    this.quantity = quantity;
  }

  private String name;
  private Double price;
  private String unit;
  private int quantity;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
