import java.sql.Timestamp;
import java.util.Date;

/**
 * When a product is produced it will use a production record to insert it to the database and to
 * the Production Log.
 *
 * @author Logan Bahr
 */
public class ProductionRecord {

  /**
   * The production number for each produced product. Does not repeat.
   */
  private int productionNumber;
  /**
   * The product id for each produced product. Will be repeated each time the product is produced.
   */
  private int productId;
  /**
   * The serial number for each produced product.
   */
  private String serialNumber;
  /**
   * The date when the product was produced.
   */
  private Date dateProduced;
  /**
   * The name of the product.
   */
  private String productName;

  /**
   * Creates a record for a product that has been produced.
   *
   * @param productId the unique product id for the item being produced
   * @deprecated Constructor never used.
   */
  public ProductionRecord(int productId) {

    this.productionNumber = 0;
    this.productId = productId;
    this.serialNumber = "0";
    this.dateProduced = new Date();
  }

  /**
   * Creates a record for a product that has been produced.
   *
   * @param productionNumber the serialized production number
   * @param productId        the unique product id for the item being produced
   * @param serialNumber     the generated serial number unique to each product
   * @param dateProduced     the date of production
   * @deprecated Constructor never used.
   */
  public ProductionRecord(int productionNumber, int productId, String serialNumber,
      Date dateProduced) {
    this.productionNumber = productionNumber;
    this.productId = productId;
    this.serialNumber = serialNumber;
    this.dateProduced = new Date(dateProduced.getTime());
  }

  /**
   * Creates a record for a product that has been produced.
   *
   * @param productionNumber the serialized production number
   * @param productName      the name of the product
   * @param serialNumber     the generated serial number unique to each product
   * @param dateProduced     the date of production
   */
  public ProductionRecord(int productionNumber, String productName, String serialNumber,
      Date dateProduced) {
    this.productionNumber = productionNumber;
    this.productName = productName;
    this.serialNumber = serialNumber;
    this.dateProduced = new Date(dateProduced.getTime());
  }

  /**
   * Creates a record for a product that has been produced.
   *
   * @param product       the product that is being produced
   * @param numberOfItems the quantity of a product that is produced
   */
  public ProductionRecord(Product product, int numberOfItems) {

    this.productionNumber = 0;
    this.productId = product.getId();
    this.serialNumber = generateSerialNumber(product.getManufacturer(), product.getType(),
        numberOfItems);
    this.dateProduced = new Date();
  }

  /**
   * Gets the production number.
   *
   * @return the production number
   * @deprecated method is never used
   */
  public int getProductionNumber() {
    return productionNumber;
  }

  /**
   * Sets the production number.
   *
   * @param productionNumber the production number
   * @deprecated method is never used
   */
  public void setProductionNumber(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  /**
   * Gets the serial number.
   *
   * @return the serial number
   */
  public String getSerialNumber() {
    return serialNumber;
  }

  /**
   * Sets the serial number.
   *
   * @param serialNumber the serial number
   * @deprecated method is never used
   */
  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  /**
   * Gets the date of production in the form of a Timestamp.
   *
   * @return the date produced in a Timestamp
   */
  public Date getDateProduced() {
    return new Timestamp(dateProduced.getTime());
  }

  /**
   * Sets the date the product was produced.
   *
   * @param dateProduced the date of production
   * @deprecated method is never used
   */
  public void setDateProduced(Date dateProduced) {
    this.dateProduced = dateProduced;
  }

  /**
   * Gets the id for the product.
   *
   * @return the product id
   */
  public int getProductId() {
    return productId;
  }

  /**
   * Sets the id for the product.
   *
   * @param productId the product's id
   * @deprecated method is never used
   */
  public void setProductId(int productId) {
    this.productId = productId;
  }

  /**
   * Provides a format to view the record.
   *
   * @return a line to console describing the record
   * @deprecated method is never used
   */
  @Override
  public String toString() {
    return
        "Prod#: [" + this.productionNumber
            + "]"
            + " Name: ["
            + this.productName
            + "]"
            + " Ser#: ["
            + this.serialNumber
            + "]"
            + " Date: ["
            + this.dateProduced
            + "]";
  }

  /**
   * This will generate a serial number unique to each product id.
   *
   * @param manufacturer    the manufacturer of the product
   * @param type            the Type of the product
   * @param productionCount how many products there are
   * @return a formatted String unique to each product
   */
  public static String generateSerialNumber(String manufacturer, ItemType type,
      int productionCount) {

    return manufacturer.substring(0, 3).toUpperCase()
        + type.code
        + String.format("%05d", productionCount);
  }

}