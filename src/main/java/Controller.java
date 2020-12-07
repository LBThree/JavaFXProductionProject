/**************************************************************************************************
 *  Author:   Logan Bahr
 *  Created:  9/19/20
 *  Purpose:  This is where the back-end of the inventory management system interacts with the
 *            front-end GUI.
 **************************************************************************************************/

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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.h2.jdbc.JdbcSQLSyntaxErrorException;

public class Controller {

  ObservableList<Widget> productLine = FXCollections.observableArrayList();

  private static Connection conn;
  private static Statement stmt;
  final static String JDBC_DRIVER = "org.h2.Driver";
  final static String DB_URL = "jdbc:h2:./res/InventoryDatabase";

  final static String USER = "";
  final static String PASS = "";


  /************************PRODUCE FXML**************************/
  @FXML
  private ComboBox<String> produce_quantity_comboBox;
  @FXML
  private Button produce_recordProduction_button;
  @FXML
  private ListView<Widget> produce_ChooseProduct;
  /*********************PRODUCTION LOG FXML**********************/
  @FXML
  private TextArea productionLog_TextArea;
  /************************PRODUCT LINE FXML*********************/
  @FXML
  public TextField productLine_ProductName_TextField;
  @FXML
  public TextField ProductLine_Manufacturer_TextField;
  @FXML
  public ChoiceBox<String> ProductLine_ItemType_ChoiceBox;
  @FXML
  private Button productLine_addProduct_button;
  @FXML
  public TableView<Widget> productLine_TableView;
  @FXML
  public TableColumn<?, ?> name_Column;
  @FXML
  public TableColumn<?, ?> manufacturer_Column;
  @FXML
  public TableColumn<?, ?> type_Column;


  // ADDING PRODUCTS
  @FXML
  void addProductButton(ActionEvent event) {

    try {
      insertProductToDB();
      loadProductList();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  // RECORDING PRODUCTION OF PRODUCTS
  @FXML
  void recordProductionButton(ActionEvent event) {

    ArrayList<ProductionRecord> productionRun = new ArrayList<>();

    try {

      int howMany = Integer.parseInt(
          produce_quantity_comboBox.
              getValue());

      String manufacturer = produce_ChooseProduct.
          getSelectionModel().
          getSelectedItem().
          getManufacturer();

      int id = produce_ChooseProduct.
          getSelectionModel().
          getSelectedItem().
          getId();

      String name = produce_ChooseProduct.
          getSelectionModel().
          getSelectedItem().
          getName();

      String type = produce_ChooseProduct.
          getSelectionModel().
          getSelectedItem().
          getType().
          toString();

      getMaxSerialNum(id);
      System.out.println(getMaxSerialNum(id));

/*
      for (int i = 1; i <= howMany; i++) {

        productionRun.add(new ProductionRecord(new Widget(name, manufacturer, type, id), i));

      }

 */



      addToProductionDB(productionRun);

      loadProductionLog();


    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /*****************************************************************/
  @FXML
  void initialize() throws SQLException {
    populateComboBox();
    populateChoiceBox();
    setupProductLineTable();
    addToListView();
    loadProductList();
    loadProductionLog();
  }


  public String getMaxSerialNum(int IDNum) {

    openConnection();

    try (PreparedStatement stmt = conn.prepareStatement(
        "SELECT MAX(SERIAL_NUM) AS SERIAL_NUM FROM PRODUCTIONRECORD WHERE PRODUCT_ID = ?")) {

      stmt.setInt(1, IDNum);

      ResultSet rs = stmt.executeQuery();

      //return rs.getString("SERIAL_NUM");
      return null;

    } catch (SQLException e) {
      e.printStackTrace();
    }
    closeConnection();
    return "ERROR";
  }

  /***********************************DONE**************************************/

  public void addToProductionDB(ArrayList<ProductionRecord> productionRunTest)
      throws SQLException, IllegalArgumentException {

    openConnection();

    stmt = conn.createStatement();

    try {

      for (ProductionRecord record : productionRunTest) {

        String insertSQL =
            "INSERT INTO PRODUCTIONRECORD (PRODUCTION_NUM, PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED) VALUES(default,"
                + "'" + record.getProductID() + "','"
                + record.getSerialNumber() + "'," + "'" + record.getDateProduced() + "')";

        stmt.executeUpdate(insertSQL);

      }
      closeConnection();

    } catch (Exception e) {
      e.printStackTrace();
      e.getCause();
    }
  }

  public void loadProductionLog() {

    openConnection();

    ArrayList<ProductionRecord> productionLog = new ArrayList<>();

    try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PRODUCTIONRECORD")) {

      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {

        int productionNum = rs.getInt("PRODUCTION_NUM");
        int productID = rs.getInt("PRODUCT_ID");
        String serialNum = rs.getString("SERIAL_NUM");
        Timestamp date = rs.getTimestamp("DATE_PRODUCED");

        productionLog.add(new ProductionRecord(productionNum, productID, serialNum, date));
      }

      showProduction(productionLog);

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  public void showProduction(ArrayList<ProductionRecord> productionRecords) {

    try {

      productionLog_TextArea.clear();

      for (ProductionRecord productionRecord : productionRecords) {

        productionLog_TextArea.appendText(productionRecord + "\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void insertProductToDB() {

    try {
      openConnection();

      stmt = conn.createStatement();

      String name = productLine_ProductName_TextField.getText();
      String manufacturer = ProductLine_Manufacturer_TextField.getText();
      String type = ProductLine_ItemType_ChoiceBox.getValue();

      String insertSQL =
          "INSERT INTO PRODUCT (TYPE, MANUFACTURER, NAME) VALUES ('" + type + "'," + "'"
              + manufacturer + "'," + "'" + name + "')";

      stmt.executeUpdate(insertSQL);

      closeConnection();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void loadProductList() throws SQLException {

    openConnection();
    stmt = conn.createStatement();

    try {
      String getSQL = "SELECT * FROM PRODUCT";

      // ResultSet lets us perform queries on a table
      ResultSet rs = stmt.executeQuery(getSQL);

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

  public void setupProductLineTable() {

    // setup to connect FXML to ObservableList
    name_Column.setCellValueFactory(new PropertyValueFactory<>("Name"));
    manufacturer_Column.setCellValueFactory(new PropertyValueFactory<>("Manufacturer"));
    type_Column.setCellValueFactory(new PropertyValueFactory<>("Type"));
    productLine_TableView.setItems(productLine);
  }

  public void addToListView() {
    produce_ChooseProduct.setItems(productLine);
    produce_ChooseProduct.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
  }

  public static void openConnection() {

    try {
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public static void closeConnection() {
    try {
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /*
   * Method for the combo box in the 'Produce' tab to populate the dropdown list with
   * numbers 1-10. There is an additional method there let the user input their own number
   * if it is greater than 10
   */
  public void populateComboBox() {
    for (int i = 1; i <= 10; i++) {

      produce_quantity_comboBox.getSelectionModel().selectFirst();
      produce_quantity_comboBox.setValue("Select / enter a value");
      produce_quantity_comboBox.getItems().add(Integer.toString(i));
      // this allows the user to enter a number of their choice
      produce_quantity_comboBox.setEditable(true);
    }
  }

  public void populateChoiceBox() {

    for (ItemType c : ItemType.values()) {
      ProductLine_ItemType_ChoiceBox.getItems().add(c.toString());

    }
  }
}
