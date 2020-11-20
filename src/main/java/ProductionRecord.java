import java.util.Date;

public class ProductionRecord {

  int productionNumber;
  int productID;
  String serialNumber;
  Date dateProduced;


  /********************************CONSTRUCTORS*************************************************/

  // this is the constructor that will be called when the user records production from the UI
  public ProductionRecord(int productID) {
    this.productID = productID;
    productionNumber = 0;
    serialNumber = "0";
    dateProduced = new Date();
  }

  public ProductionRecord(int productionNumber, int productID, String serialNumber,
      Date dateProduced) {
    this.productionNumber = productionNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
    this.dateProduced = new Date();
  }

  public ProductionRecord(Product product, int numberOfItems) {

    dateProduced = new Date();

    String manufacturerCode = product.getManufacturer().substring(0, 3);
    String itemCode = product.getType().code;
    String prettyProductionNumber = String.format("%05d", productionNumber);

    serialNumber = manufacturerCode + itemCode + prettyProductionNumber;


  }


  /*******************************GETTERS+SETTERS************************************************/
  public int getProductionNumber() {
    return productionNumber;
  }

  public void setProductionNumber(int productionNumber) {this.productionNumber = productionNumber;}

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public Date getDateProduced() {
    return dateProduced;
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
        "Prod. Num: " + productionNumber +
            " Product ID: " + productID +
            " Serial Num: " + serialNumber +
            " Date: " + dateProduced;
  }
}