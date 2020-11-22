import java.util.Date;

public class ProductionRecord {

  // counts the items made regardless of type
  private static int productionNumber;

  // counts each item by its type
  private int serialNumberType;

  private int productID;
  private String serialNumber;
  private Date dateProduced;

  private static int count_Audio = 1;
  private static int count_Visual = 1;
  private static int count_AudioMobile = 1;
  private static int count_VisualMobile = 1;

  /********************************CONSTRUCTORS*************************************************/

  // this is the constructor that will be called when the user records production from the UI
  public ProductionRecord(int productID) {
    this.productID = productID;
    productionNumber = 0;
    serialNumber = "0";
    dateProduced = new Date();
  }

  // this is the constructor used when printing to the log
  public ProductionRecord(Product product, int numberOfItems) {

    if (product.getType().code.equals("AU")) {
      serialNumberType = count_Audio++;
      productionNumber++;
    }
    if (product.getType().code.equals("VI")) {
      serialNumberType = count_Visual++;
      productionNumber++;
    }
    if (product.getType().code.equals("AM")) {
      serialNumberType = count_AudioMobile++;
      productionNumber++;
    }
    if (product.getType().code.equals("VM")) {
      serialNumberType = count_VisualMobile++;
      productionNumber++;
    }

    dateProduced = new Date();
    String manufacturerCode = product.getManufacturer().substring(0, 3);
    String itemCode = product.getType().code;
    String prettyProductionNumber = String.format("%05d", serialNumberType);

    serialNumber = manufacturerCode + itemCode + prettyProductionNumber;
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
        "Prod#: " + productionNumber +
            " Product ID: " + productID +
            " Serial#: " + serialNumber +
            " Date: " + dateProduced;
  }
}