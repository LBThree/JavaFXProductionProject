
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * This is the class the operates the GUI and Production Tracker.
 */
public class Controller {

  /**
   * This holds all of the products that have been created and are available to produce.
   */
  final ObservableList<Widget> productLine = FXCollections.observableArrayList();

  /**
   * Provides a connection to the database.
   */
  private static Connection conn;
  /**
   * Provides a means for executing SQL statements on the database.
   */
  private static Statement stmt;
  /**
   * The name of the driver for the database.
   */
  static final String JDBC_DRIVER = "org.h2.Driver";
  /**
   * The URL for the database.
   */
  static final String DB_URL = "jdbc:h2:./res/InventoryDatabase";

  /**
   * The username for the database.
   */
  static final String USER = "";
  /**
   * The password for the database.
   */
  static final String PASS = "";


  /**
   * Located on the 'Produce' tab of the GUI. Used to produce a given quantity of products.
   */
  @FXML
  private ComboBox<String> quantityComboBox;

  /**
   * Located on the 'Produce' tab of the GUI. Displays the list of products available to produce.
   */
  @FXML
  private ListView<Widget> existingProductListView;

  /**
   * Located on the 'Production Log' tab of the GUI. Displays the products that have been produced.
   */
  @FXML
  private TextArea productionLog;

  /**
   * Located on the 'Product Line' tab of the GUI. Allows the user to input the product's name.
   */
  @FXML
  private TextField productName;

  /**
   * Located on the 'Product Line' tab of the GUI. Allows the user to input the product's
   * manufacturer.
   */
  @FXML
  private TextField productManufacturer;

  /**
   * Located on the 'Product Line' tab of the GUI. Allows the user to input the product's ItemType.
   */
  @FXML
  private ChoiceBox<String> productType;

  /**
   * Located on the 'Product Line' tab of the GUI. Displays the products that have been added to the
   * database.
   */
  @FXML
  private TableView<Widget> existingProductTableView;

  /**
   * Located on the 'Product Line' tab of the GUI. Represents the 'Name' column for the TableView.
   */
  @FXML
  private TableColumn<?, ?> nameColumn;

  /**
   * Located on the 'Product Line' tab of the GUI. Represents the 'Manufacturer' column for the
   * TableView.
   */
  @FXML
  private TableColumn<?, ?> manufacturerColumn;

  /**
   * Located on the 'Product Line' tab of the GUI. Represents the 'Type' column for the TableView.
   */
  @FXML
  private TableColumn<?, ?> typeColumn;

  /**
   * This button is located on the 'Product Line' tab of the GUI. It allows the user to add a
   * product to the database after the necessary information has been submitted.
   *
   * @param event when the user clicks the button
   * @throws NullPointerException if there is nothing to add
   */
  @FXML
  void addProductButton(ActionEvent event) throws NullPointerException {

    try {

      addToProductDb();
      loadProductList();
      resetProductFields();
      event.consume();

    } catch (Exception e) {
      //e.printStackTrace();
      System.out.println("Please enter the correct information!");
    }
  }

  /**
   * This button is located on the 'Produce' tab of the GUI. It allows the user to select an
   * existing product and add it to the Production Log.
   *
   * @param event when the user clicks the button
   */
  @FXML
  void recordProductionButton(ActionEvent event) {

    ArrayList<ProductionRecord> productionRun = new ArrayList<>();

    try {

      int howMany = Integer.parseInt(
          quantityComboBox
              .getValue());

      String manufacturer = existingProductListView
          .getSelectionModel()
          .getSelectedItem()
          .getManufacturer();

      int id = existingProductListView
          .getSelectionModel()
          .getSelectedItem()
          .getId();

      String name = existingProductListView
          .getSelectionModel()
          .getSelectedItem()
          .getName();

      String type = existingProductListView
          .getSelectionModel()
          .getSelectedItem()
          .getType()
          .toString();

      int maxSerialNum = getMaxSerialNum(id);

      for (int i = maxSerialNum + 1; i <= maxSerialNum + howMany; i++) {

        productionRun.add(new ProductionRecord(new Widget(name, manufacturer, type, id), i));

      }

      addToProductionDb(productionRun);

      loadProductionLog();

      event.consume();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * This is the "main method" for the GUI and the methods called within are executed immediately
   * upon startup.
   *
   * @throws SQLException for invalid or irregular SQL statements
   */
  @FXML
  void initialize() throws SQLException {
    populateComboBox();
    populateChoiceBox();
    setupProductLineTable();
    addToListView();
    loadProductList();
    loadProductionLog();
  }

  /**
   * Gets the name of the product based on the product id given.
   *
   * @param idNum the id number for the product
   * @return the product's name
   */
  public String getProductName(int idNum) {

    openConnection();

    try (PreparedStatement stmt = conn.prepareStatement("SELECT NAME FROM PRODUCT WHERE ID = ?")) {

      stmt.setInt(1, idNum);

      ResultSet rs = stmt.executeQuery();

      rs.next();

      rs.getString("NAME");

      if (!rs.wasNull()) {
        return rs.getString("NAME");
      }
      return "Nothing there.";

    } catch (SQLException e) {
      e.printStackTrace();
    }

    closeConnection();
    return null;
  }

  /**
   * Gets the latest serial number based on the product id given.
   *
   * @param idNum the id number for the product
   * @return the most recent iteration of production for the given item
   */
  public int getMaxSerialNum(int idNum) {

    openConnection();

    try (PreparedStatement stmt = conn.prepareStatement(
        "SELECT MAX(SERIAL_NUM) AS SERIAL_NUM FROM PRODUCTIONRECORD WHERE PRODUCT_ID = ?")) {

      stmt.setInt(1, idNum);

      ResultSet rs = stmt.executeQuery();

      rs.next();

      rs.getString("SERIAL_NUM");

      if (!rs.wasNull()) {

        return Integer.parseInt(rs.getString("SERIAL_NUM").substring(5));

      } else {

        return 0;
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    closeConnection();

    return 0;
  }

  /**
   * Inserts a produced product into the database.
   *
   * @param productionRun holds 'n' quantity ProductionRecords for how many products are being
   *                      produced
   * @throws SQLException             for invalid or irregular product inputs
   * @throws IllegalArgumentException for invalid or irregular product inputs
   */
  public void addToProductionDb(ArrayList<ProductionRecord> productionRun)
      throws SQLException, IllegalArgumentException {

    openConnection();

    stmt = conn.createStatement();

    try {

      for (ProductionRecord record : productionRun) {

        String insertSql =
            "INSERT INTO PRODUCTIONRECORD (PRODUCTION_NUM, PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED) VALUES("
                + "default,"
                + "'"
                + record.getProductId()
                + "','"
                + record.getSerialNumber()
                + "',"
                + "'"
                + record.getDateProduced()
                + "')";

        stmt.executeUpdate(insertSql);

      }
      closeConnection();

    } catch (Exception e) {
      e.printStackTrace();
      e.getCause();
    }
  }

  /**
   * Scans the production database and creates a ProductionRecord for each product produced, then
   * displays that to the 'Production Log' tab in the GUI.
   */
  public void loadProductionLog() {

    openConnection();

    ArrayList<ProductionRecord> productionLog = new ArrayList<>();

    try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PRODUCTIONRECORD")) {

      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {

        int productionNum = rs.getInt("PRODUCTION_NUM");
        int productId = rs.getInt("PRODUCT_ID");
        String serialNum = rs.getString("SERIAL_NUM");
        Timestamp date = rs.getTimestamp("DATE_PRODUCED");

        String name = getProductName(productId);

        productionLog.add(new ProductionRecord(productionNum, name, serialNum, date));
      }

      showProduction(productionLog);

    } catch (SQLException e) {
      e.printStackTrace();
    }
    closeConnection();
  }

  /**
   * Displays the ProductionRecord for each product on the 'Production Log' tab. Used by
   * loadProductionLog().
   *
   * @param productionRecords hold the ProductionRecord for each item that has been produced in the
   *                          database
   */
  public void showProduction(ArrayList<ProductionRecord> productionRecords) {

    try {

      productionLog.clear();

      for (ProductionRecord productionRecord : productionRecords) {

        productionLog.appendText(productionRecord + "\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Takes the user input from the 'Product Line' tab of the GUI and inserts it into the database.
   *
   * @throws NullPointerException for irregular or invalid user input
   */
  public void addToProductDb() throws NullPointerException {

    openConnection();

    try (PreparedStatement stmt = conn
        .prepareStatement("INSERT INTO PRODUCT (TYPE, MANUFACTURER, NAME) VALUES (?,?,?)")) {

      String type = productType.getValue();
      String manufacturer = productManufacturer.getText();
      String name = productName.getText();

      // not sure how to get these to work

      if (name.isBlank()) {
        throw new NullPointerException("No name");
      }

      if (type.isBlank()) {
        throw new NullPointerException("Type is blank");
      }

      if (manufacturer.isBlank()) {
        throw new NullPointerException("Manufacturer is blank");
      }

      stmt.setString(1, type);
      stmt.setString(2, manufacturer);
      stmt.setString(3, name);

      stmt.execute();

      closeConnection();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Scans the database for existing products and populates the global ObservableList
   * 'productLine'.
   *
   * @throws SQLException for irregular or invalid user input
   */
  public void loadProductList() throws SQLException {

    openConnection();
    stmt = conn.createStatement();

    try {
      String getSql = "SELECT * FROM PRODUCT";

      ResultSet rs = stmt.executeQuery(getSql);

      productLine.clear();

      while (rs.next()) {
        productLine
            .add(new Widget(rs.getString(3), rs.getString(2), rs.getString(1), rs.getInt(4)));
      }
      closeConnection();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Associates the global ObservableList 'productLine' which holds existing products with the
   * TableView located on the 'Product Line' tab of the GUI.
   */
  public void setupProductLineTable() {

    // setup to connect FXML to ObservableList
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
    manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("Manufacturer"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
    existingProductTableView.setItems(productLine);
  }

  /**
   * Associates the global ObservableList 'productLine' which holds existing products with the
   * ListView located on the 'Produce' tab of the GUI.
   */
  public void addToListView() {
    existingProductListView.setItems(productLine);
    existingProductListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
  }

  /**
   * Opens the connection to the database.
   */
  public static void openConnection() {

    try {
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * Closes the connection to the database.
   */
  public static void closeConnection() {
    try {
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Used to reset the fields in the 'Product Line' tab after the user clicks the 'Add Product'
   * button on the 'Product Line' tab of the GUI.
   */
  public void resetProductFields() {
    productName.clear();
    productManufacturer.clear();
  }

  /**
   * Generates integers 1-10 in the 'Choose Quantity' box located on the 'Produce' tab of the GUI.
   */
  public void populateComboBox() {
    for (int i = 1; i <= 10; i++) {

      quantityComboBox.getSelectionModel().selectFirst();
      quantityComboBox.setValue("1");
      quantityComboBox.getItems().add(Integer.toString(i));
      quantityComboBox.setEditable(true);
    }
  }

  /**
   * Generates the different ItemTypes available to create on 'Product Line' tab.
   */
  public void populateChoiceBox() {

    for (ItemType types : ItemType.values()) {
      productType.getItems().add(types.toString());
    }
  }
}
