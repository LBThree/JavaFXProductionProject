/**
 * An interface to be implemented by Product.
 *
 * @author Logan Bahr
 */
public interface Item {

  /**
   * Gets the id for a product.
   *
   * @return the id
   * @deprecated never used as a member of this interface
   */
  int getId();

  /**
   * Gets the type of product.
   *
   * @return the type
   * @deprecated never used as a member of this interface
   */
  ItemType getType();

  /**
   * Gets the name of the product.
   *
   * @return the name
   * @deprecated never used as a member of this interface
   */
  String getName();

  /**
   * Returns the manufacturer for the product.
   *
   * @return the manufacturer
   * @deprecated never used as a member of this interface
   */
  String getManufacturer();

  /**
   * Sets the name for the product.
   *
   * @param name the name of the product
   * @deprecated never used as a member of this interface
   */
  void setName(String name);

  /**
   * Sets the manufacturer for the product.
   *
   * @param manufacturer the manufacturer of the product
   * @deprecated never used as a member of this interface
   */
  void setManufacturer(String manufacturer);

  /**
   * The type of the product.
   *
   * @param type the type of product selected
   * @deprecated never used as a member of this interface
   */
  void setType(ItemType type);
}
