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
import java.util.function.Consumer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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


  @FXML
  void addProductToTable(ActionEvent event) {
    // when we click the button we want the data to get inserted into the table

    addToObservableList();

    productLine_ProductName_TextField.clear();
    ProductLine_Manufacturer_TextField.clear();


  }


  /*****************************************************************/
  /*
   * I realized that you don't really need anything so far in this initialize method so I think this
   * will be a place where all of the basic GUI functions can go like 'populateComboBox'
   */
  @FXML
  void initialize() {

    connectToDatabase();
    populateComboBox();
    populateChoiceBox();
    createObservableList();
    addToListView();

    // sample production log with current time and the serial number
    Widget foo = new Widget("iPhone", "Apple", ItemType.AudioMobile);
    ProductionRecord testRecord = new ProductionRecord(foo, 2);
    productionLog_TextArea.setText(testRecord.toString());

  }

  public void week_9_test() {

    AudioPlayer newAudioProduct = new AudioPlayer("DP-X1A", "Onkyo",
        "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL");

    Screen newScreen = new Screen("720x480", 40, 22);

    MoviePlayer newMovieProduct = new MoviePlayer("DBPOWER MK101", "OracleProduction", newScreen,
        MonitorType.LCD);

    ArrayList<MultimediaControl> productList = new ArrayList<>();

    productList.add(newAudioProduct);
    productList.add(newMovieProduct);

    for (MultimediaControl p : productList) {
      System.out.println(p);
      p.play();
      p.stop();
      p.next();
      p.previous();
    }
    ProductionRecord newRecord = new ProductionRecord(0);
    productionLog_TextArea.setText(newRecord.toString());

  }

  public void week_10_test() {

    // test for "AU" code in serial number string
    AudioPlayer test23 = new AudioPlayer("DP-X1A", "Onkyo",
        "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL");
    ProductionRecord record2 = new ProductionRecord(test23, 1);
    productionLog_TextArea.setText(record2.toString());

    // test for "VI" code in serial number string
    Screen newScreen2 = new Screen("4k", 240, 1);
    MoviePlayer test32 = new MoviePlayer("Macbook", "Apple", newScreen2, MonitorType.LED);
    ProductionRecord record3 = new ProductionRecord(test32, 1);
    productionLog_TextArea.setText(record3.toString());
  }

  public void issue7Test() {

    Product productProduced = new Widget("iPod", "Apple", ItemType.AUDIO);
    int numProduced = 3;  // this will come from the combobox in the UI
    int itemCount = 0;

    for (int productionRunProduct = 0; productionRunProduct < numProduced; productionRunProduct++) {
      ProductionRecord pr = new ProductionRecord(productProduced, itemCount++);
      // using the iterator as the product id for testing
      System.out.println(pr.toString());
    }
  }


  public void createObservableList() {

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


  public void addToListView() {produce_ChooseProduct.setItems(productLine);}


  /*
   * Temporary function while I determine how I want to set this class up
   */
  public void productionLogTab() {

  }

  /*
   * Temporary function while I determine how I want to set this class up
   */
  public void productLineTab() {

  }

  /*
   * Right now this is the function that is doing most of the work. I'm trying to figure out a way to
   * make this method simply open a connection to the database. That way I can make another function to
   * close a connection to the database, which will allow me to simply call the two functions whenever I want
   * to put something in or take something out.
   */
  public void connectToDatabase() {
    // can name the database here
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/InventoryDatabase";

    // Database credentials
    // not sure exactly how to use 'Connection' and 'Statement' classes yet without getting
    // null pointer exceptions. For now just leave them together how they are
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

      // can think of this another console command, this string will be used as a parameter
      // for a method when we want to show everything in the 'Product' table
      String getSQL = "SELECT * FROM PRODUCT";

      // ResultSet lets us perform queries on a table
      ResultSet rs = stmt.executeQuery(getSQL);

      // while there are results in the table, whatever is in the columns will be
      //  printed to the console

      /*
      while (rs.next()) {
        System.out.println(rs.getString(3));
        System.out.println(rs.getString(2));
        System.out.println(rs.getString(1));
      }

       */

      // STEP 4: Clean-up environment
      // still figuring out how to use these properly so that the connection is only open
      // when the database is in use
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      e.printStackTrace();

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