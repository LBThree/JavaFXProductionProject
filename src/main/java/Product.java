public abstract class Product implements Item {

  private int id;
  private ItemType type;
  private String manufacturer;
  private String name;

  /*************************CONSTRUCTORS********************************/
  public Product(String name, String manufacturer, ItemType type) {
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  /***********************GETTERS+SETTERS*******************************/

  @Override
  public int getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getManufacturer() {
    return manufacturer;
  }

  @Override
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  @Override
  public ItemType getType() {
    return type;
  }

  @Override
  public void setType(ItemType type) {
    this.type = type;
  }

  /*************************METHODS********************************/
  @Override
  public String toString() {
    return
        "nameasdfsd: " + name + "\n" +
            "manufacturer: " + manufacturer + "\n" +
            "type: " + type;
  }
}