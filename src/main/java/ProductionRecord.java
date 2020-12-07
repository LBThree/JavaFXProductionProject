import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class ProductionRecord {


  private int productionNumber;
  private int productID;
  private String serialNumber;
  private Date dateProduced;


  /********************************CONSTRUCTORS*************************************************/

  public ProductionRecord(int productID) {

    this.productionNumber = 0;
    this.productID = productID;
    this.serialNumber = "0";
    this.dateProduced = new Date();
  }


  public ProductionRecord(int productionNumber, int productID, String serialNumber,
      Date dateProduced) {
    this.productionNumber = productionNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
    this.dateProduced = new Date(dateProduced.getTime());
  }


  public ProductionRecord(Product product, int numberOfItems) {

    this.productionNumber = 0;
    this.productID = product.getId();
    this.serialNumber = generateSerialNumber(product.getManufacturer(), product.getType(),
        numberOfItems);
    this.dateProduced = new Date();

  }


  /*******************************GETTERS+SETTERS************************************************/
  public int getProductionNumber() {
    return productionNumber;
  }

  public void setProductionNumber(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public Date getDateProduced() {
    return new Timestamp(dateProduced.getTime());
  }

  public void setDateProduced(Date dateProduced) {
    this.dateProduced = dateProduced;
  }

  public int getProductID() {
    return productID;
  }

  public void setProductID(int productID) {
    this.productID = productID;
  }

  /********************************METHODS************************************************/


  @Override
  public String toString() {
    return
        "Prod#: " + this.productionNumber +
            " Product ID: " + this.productID +
            " Serial#: " + this.serialNumber +
            " Date: " + this.dateProduced;
  }


  public static String generateSerialNumber(String manufacturer, ItemType type,
      int productionCount) {

    return manufacturer.substring(0, 3).toUpperCase() +
        type.code + String.format("%05d", productionCount);
  }

}