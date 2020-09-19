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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javax.swing.plaf.nimbus.State;

public class Controller {

  @FXML
  private ComboBox<String> produce_quantity_comboBox;
  @FXML
  private Button produce_recordProduction_button;
  @FXML
  private Button productLine_addProduct_button;
  @FXML
  private TextField productLine_ProductName_TextField;
  @FXML
  private TextField ProductLine_Manufacturer_TextField;
  @FXML
  private TextField ProductLine_ItemType_TextField;

  /*
   * This is what will happen when the user presses the 'Add Product' button on the 'Product Line'
   * tab and inputs the required information. I'm still trying to figure out how to make
   * 'connectToDatabase()' a standalone method so that I can just call it each time I need to access
   * the database. For now I just have the whole process taking place on the button click
   */
  @FXML
  void addProductToDataBase(ActionEvent event) {
    connectToDatabase();
  }


  /*
   * I realized that you don't really need anything so far in this initialize method so I think this
   * will be a place where all of the basic GUI functions can go like 'populateComboBox'
   */
  @FXML
  void initialize() {
    populateComboBox();
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
      String type = ProductLine_ItemType_TextField.getText();

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

      // can think of this another console command, this sting will be used as a parameter
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
}