
public class Widget extends Product {

  /**
   * Creates a product.
   *
   * @param name         the name of the product
   * @param manufacturer the manufacturer of the product
   * @param type         the ItemType of the product
   * @deprecated Constructor never used.
   */
  public Widget(String name, String manufacturer, ItemType type) {
    super(name, manufacturer, type);
  }

  /**
   * Creates a product.
   *
   * @param name         the name of the product
   * @param manufacturer the manufacturer of the product
   * @param type         the ItemType of the product
   * @deprecated Constructor never used.
   */
  public Widget(String name, String manufacturer, String type) {
    super(name, manufacturer, ItemType.valueOf(type));

  }

  /**
   * Creates a product.
   *
   * @param name         the name of the product
   * @param manufacturer the manufacturer of the product
   * @param type         the ItemType of the product
   * @param id           the unique id of the product
   */
  public Widget(String name, String manufacturer, String type, int id) {
    super(name, manufacturer, ItemType.valueOf(type));
    setId(id);
  }

}





