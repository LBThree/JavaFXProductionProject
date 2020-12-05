/**************************************************************************************************
 *  Author:   Logan Bahr
 *  Created:  9/19/20
 *  Purpose:  This is where the back-end of the inventory management system interacts with the
 *            front-end GUI.
 **************************************************************************************************/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
import javafx.scene.layout.Border;

public class Controller {

  ObservableList<Widget> productLine = FXCollections.observableArrayList();


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
    // when we click the button we want the data to get inserted into the table

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

    try {

      int quantity = Integer.parseInt(produce_quantity_comboBox.getValue());

      ArrayList<ProductionRecord> productionRun = new ArrayList<>();

      for (int i = 0; i < quantity; i++) {

        productionRun.add(
            new ProductionRecord(produce_ChooseProduct.getSelectionModel().getSelectedItem(),
                quantity));

      }
      //System.out.println(productionRun);

      addToProductionDB(productionRun);

      //loadProductionLog();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /*****************************************************************/
  @FXML
  void initialize() {
    populateComboBox();
    populateChoiceBox();

    setupProductLineTable();
    addToListView();
    loadProductList();
    loadProductionLog();

  }

  public void loadProductionLog() {
    /*
    The loadProductionLog method should:
      -Create ProductionRecord objects from the records in the ProductionRecord database table.
      -Populate the productionLog ArrayList
      -call showProduction
     */

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/InventoryDatabase";

    final String USER = "";
    final String PASS = "";
    Connection conn = null;
    Statement stmt = null;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      String getSQL = "SELECT * FROM PRODUCTIONRECORD";

      // ResultSet lets us perform queries on a table
      ResultSet rs = stmt.executeQuery(getSQL);

      ArrayList<ProductionRecord> productionLog = new ArrayList<>();

      while (rs.next()) {
        // Create ProductionRecord objects from the records in the ProductionRecord database table and add it to the ArrayList above
        productionLog.add(new ProductionRecord(rs.getInt("PRODUCT_ID")));
      }
      // System.out.println(productionLog.get(2).getSerialNumber());

      showProduction(productionLog);

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();

    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void showProduction(ArrayList<ProductionRecord> productionRecords) {

    try {

      for (ProductionRecord productionRecord : productionRecords) {
        productionLog_TextArea.appendText(productionRecord.toString() + "\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }


  public void connectToDatabase() {

    // can name the database here
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/InventoryDatabase";

    final String USER = "";
    final String PASS = "";
    Connection conn = null;
    Statement stmt = null;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
/*
      //STEP 3: Execute a query
      stmt = conn.createStatement();

      // each of these Strings is a local container for the text fields on the 'Product Line' tab
      String name = productLine_ProductName_TextField.getText();
      String manufacturer = ProductLine_Manufacturer_TextField.getText();
      String type = ProductLine_ItemType_ChoiceBox.getValue();

      // need this string to take whatever was put into the text fields above and convert it into a SQL
      // statement that we can execute. Can think of this as an input to the console but just
      // concatenates whatever was put into the text fields
      String insertSQL =
          "INSERT INTO PRODUCT (TYPE, MANUFACTURER, NAME) VALUES ('" + type + "'," + "'"
              + manufacturer + "'," + "'" + name + "')";
      // this is the actual method that takes the string above as a parameter and puts the information
      // into the table. I don't understand yet how I can use this method outside of this try/catch block
      // but I want to get this into the 'addProductToDataBase(ActionEvent event)'
      stmt.executeUpdate(insertSQL);

/*
      // can think of this another console command, this string will be used as a parameter
      // for a method when we want to show everything in the 'Product' table
      String getSQL = "SELECT * FROM PRODUCT";

      // ResultSet lets us perform queries on a table
      ResultSet rs = stmt.executeQuery(getSQL);

      // while there are results in the table, whatever is in the columns will be
      //  printed to the console

      while (rs.next()) {
        System.out.println(rs.getString(3));
        System.out.println(rs.getString(2));
        System.out.println(rs.getString(1));
      }

 */

      // STEP 4: Clean-up environment
      // still figuring out how to use these properly so that the connection is only open
      // when the database is in use
      //stmt.close();
      //conn.close();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }


  }

  public void loadProductList() {

    // loadProductList method should:
    // Create Product objects from the Product database table and add them to the productLine
    // ObservableList(which will automatically update the Product Line ListView).

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/InventoryDatabase";

    final String USER = "";
    final String PASS = "";
    Connection conn = null;
    Statement stmt = null;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      String getSQL = "SELECT * FROM PRODUCT";

      // ResultSet lets us perform queries on a table
      ResultSet rs = stmt.executeQuery(getSQL);

      productLine.clear();

      while (rs.next()) {
        productLine.add(new Widget(rs.getString(3), rs.getString(2), rs.getString(1)));
      }

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  public void insertProductToDB() {
    // can name the database here
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/InventoryDatabase";

    final String USER = "";
    final String PASS = "";
    Connection conn;
    Statement stmt;

    try {

      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      String name = productLine_ProductName_TextField.getText();
      String manufacturer = ProductLine_Manufacturer_TextField.getText();
      String type = ProductLine_ItemType_ChoiceBox.getValue();

      String insertSQL =
          "INSERT INTO PRODUCT (TYPE, MANUFACTURER, NAME) VALUES ('" + type + "'," + "'"
              + manufacturer + "'," + "'" + name + "')";

      stmt.executeUpdate(insertSQL);

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();


    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

  }


  public void addToProductionLog() {
    // we want to add the product that is selected from the list,
    // with the quantity that is selected from the box

    try {

      int quantity = Integer.parseInt(produce_quantity_comboBox.getValue());

      for (int i = 0; i < quantity; i++) {

        productionLog_TextArea.appendText(
            new ProductionRecord(produce_ChooseProduct.getSelectionModel().getSelectedItem(),
                quantity).toString() + "\n");

        produce_quantity_comboBox.setValue("1");
      }


    } catch (Exception e) {
      e.printStackTrace();
    }


  }

  public void addToProductionDB(ArrayList<ProductionRecord> productionRunTest)
      throws java.lang.NumberFormatException {
    // can name the database here
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/InventoryDatabase";

    final String USER = "";
    final String PASS = "";
    Connection conn;
    Statement stmt;

    try {

      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      // need to create a loop to iterate through each ProductionRecord
      // were going to insert a String however many times the product is recorded

      //System.out.println(productionRunTest.size());

      for (ProductionRecord record : productionRunTest) {

        System.out.println(record.getSerialNumber());
        System.out.println(record.getDateProduced());


        String insertSQL2 =
            "INSERT INTO PRODUCTIONRECORD (PRODUCTION_NUM, PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED) VALUES(default,null,'"
                + record.getSerialNumber() + "'," + "'" + record.getDateProduced() + "')";

        String insertSQL =
            "INSERT INTO PRODUCTIONRECORD (PRODUCTION_NUM, PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED) VALUES (' "
                + record.getProductionNumber() + "'," + "'" + record
                .getProductID() + "'," + "'" + record.getSerialNumber() + "',"
                + "'" + record.getDateProduced() + "'" + ")";

        stmt.executeUpdate(insertSQL2);

      }

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException | SQLException e) {
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


  public void setupProductLineTable() {

    // setup to connect FXML to ObservableList
    name_Column.setCellValueFactory(new PropertyValueFactory<>("Name"));
    manufacturer_Column.setCellValueFactory(new PropertyValueFactory<>("Manufacturer"));
    type_Column.setCellValueFactory(new PropertyValueFactory<>("Type"));
    productLine_TableView.setItems(productLine);
  }

  public void addToObservableList() {

    String input_Name = productLine_ProductName_TextField.getText();
    String input_manufacturer = ProductLine_Manufacturer_TextField.getText();
    String input_Type = ProductLine_ItemType_ChoiceBox.getValue();

    productLine.add(new Widget(input_Name, input_manufacturer, input_Type));

  }

  public void addToListView() {
    produce_ChooseProduct.setItems(productLine);
    produce_ChooseProduct.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
  }


}
