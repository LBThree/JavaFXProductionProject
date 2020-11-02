import java.util.Date;

public class ProductionRecord {

  int productionNumber;
  int productID;
  String serialNumber;
  Date dateProduced;


  /********************************CONSTRUCTORS*************************************************/

  // may need to make a default constructor to create a new Date() every time a new constructor is used
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
    this.dateProduced = dateProduced;
  }

  public ProductionRecord(Product product, int numberOfItems){

    String manufacturerCode = product.getManufacturer().toUpperCase().substring(0,3);
    String itemCode = product.getType().code;
    String prettyProductionNumber = String.format("%05d",productionNumber);

    serialNumber = manufacturerCode + itemCode + prettyProductionNumber;
  }


  /*******************************GETTERS+SETTERS************************************************/

  public int getProductionNum() {
    return productionNumber;
  }

  public void setProductionNum(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  public int getProductID() {
    return productID;
  }

  public void setProductID(int productID) {
    this.productID = productID;
  }

  public String getSerialNum() {
    return serialNumber;
  }

  public void setSerialNum(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public Date getProdDate() {
    return dateProduced;
  }

  public void setProdDate(Date dateProduced) {
    this.dateProduced = dateProduced;
  }


  @Override
  public String toString() {
    return
        "Prod. Num: " + productionNumber +
            " Product ID: " + productID +
            " Serial Num: " + serialNumber +
            " Date: " + dateProduced;
  }
}