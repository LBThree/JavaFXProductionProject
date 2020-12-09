/**
 * Defines the characteristics of what constitutes a product.
 *
 * @author Logan Bahr
 */
public abstract class Product implements Item {

  /**
   * The unique id that for each product.
   */
  private int id;

  /**
   * The type of the product.
   */
  private ItemType type;

  /**
   * The manufacturer of the product.
   */
  private String manufacturer;

  /**
   * The name of the product.
   */
  private String name;


  /**
   * Creates a product.
   *
   * @param name         the name of the product
   * @param manufacturer the manufacturer of the product
   * @param type         the type of the product
   */
  public Product(String name, String manufacturer, ItemType type) {
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  /**
   * Gets the id of the product.
   *
   * @return the product's id
   */
  @Override
  public int getId() {
    return id;
  }

  /**
   * Sets the id for the product.
   *
   * @param id the unique identifier for the product
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the name of the product.
   *
   * @return the products name
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the product.
   *
   * @param name the name of the product
   * @deprecated method is never used
   */
  @Override
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the manufacturer for the product.
   *
   * @return the products manufacturer
   */
  @Override
  public String getManufacturer() {
    return manufacturer;
  }

  /**
   * Sets the manufacturer for the product.
   *
   * @param manufacturer the manufacturer of the product
   * @deprecated method is never used
   */
  @Override
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  /**
   * Gets the type for the product.
   *
   * @return the products type
   */
  @Override
  public ItemType getType() {
    return type;
  }

  /**
   * Sets the type for the product.
   *
   * @param type the type of product selected
   * @deprecated method is never used
   */
  @Override
  public void setType(ItemType type) {
    this.type = type;
  }

  /**
   * A formatted console output to describe a product.
   *
   * @return a single line to console
   */
  @Override
  public String toString() {
    return
        "NAME: "
            +
            name + "\n"
            + "MANUFACTURER: "
            + manufacturer
            + "\n"
            + "TYPE: "
            + type;
  }
}