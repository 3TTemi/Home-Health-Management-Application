// package HomeHealthManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class JavaDatabase
{

  // atributes name, connection, data
  private String dbName;
  private Connection dbConn;
  private ArrayList<ArrayList<String>> data;

  // consturctor without knowing name
  public JavaDatabase()
  {
    dbName = "";
    dbConn = null;
    data = null;
  }

  // constructor with knowing nmae 
  public JavaDatabase(String dbName)
  {
    setDbName(dbName);
    setDbConn();
    data = null;
  }

  // get and set variables for database name connection
  public String getDbName()
  {
    return dbName;
  }

  public void setDbName(String dbName)
  {
    this.dbName = dbName;
  }

  public Connection getDbConn()
  {
    return dbConn;
  }

  public void setDbConn()
  {
    //    jdbc:mysql://localhost:3306/

    // Path to database
    String connectionURL = "jdbc:derby:" + this.dbName;
    // to avoid compiler warning
    this.dbConn = null;
    try
    {
      // acess the java databse driver
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      // use the driver and URL to get connection 
      this.dbConn = DriverManager.getConnection(connectionURL);
    }
    catch (ClassNotFoundException ex)
    {
      // print something wrong with driver
      System.out.println("Driver not found, check Library");
    }
    catch (SQLException se)
    {
      // print something wrong with sql
      se.printStackTrace();

      System.out.println("SQL Connection error!");
    }
  }

  // close the connection to the datbase
  public void closeDbConn()
  {
    try
    {
      this.dbConn.close();
    }
    catch (Exception err)
    {
      // print databse error

      System.out.println("DB closing error");
    }
  }

  public ArrayList<ArrayList<String>> getData(String tableName, String[] tableHeaders)
  {
    int columnCount = tableHeaders.length; // number of columns
    Statement s = null; // blank statemnet for SQL
    ResultSet rs = null; // pointer to data 
    String dbQuery = "SELECT * FROM " + tableName; // to read data
    this.data = new ArrayList<>(); // construct new data 
    // read the data
    try
    {
      // send the query and recieve data 
      // rs points to beginning of databse
      s = this.dbConn.createStatement();
      rs = s.executeQuery(dbQuery);

      // rs points to each row as you go by next
      // read the data using rs and store in ArrayList data
      while (rs.next())
      {
        // row object to hold one row data
        ArrayList<String> row = new ArrayList<>();
        //go through the row and read each cell
        for (int i = 0; i < columnCount; i++)
        {
          // 
          // read cell i
          // example: String cell = rs.getString("Name");
          // reads the cell in columb Name
          // tableHeader ={"Name", "Age", "Color]
          // tableheader i discovers name, age, color, rs gets the whcih row its on
          // incremeneting with rs as it goes by next
          String cell = rs.getString(tableHeaders[i]);
          // add the cell to the row
          // example row.add("Vinny")
          row.add(cell);
        }
        // add the row to the data
        // example: data.add "vinny", 15 "Pink"
        this.data.add(row);
      }
    }
    catch (SQLException e)
    {
      // print somethign wrong with sql
      e.printStackTrace();
      System.out.println("SQL Error: Not able to get data");
    }
    return data;
  }

  public void setData(ArrayList<ArrayList<String>> data)
  {
    this.data = data;
  }

  public void createDB(String newDbName)
  {
    setDbName(newDbName); // set dbName to new DbName 
    // create a db if not existing
    String connectionURL = "jdbc:derby:" + this.dbName + ";create=true"; // creates new database
    this.dbConn = null;
    try
    {
      // set up the datbase 
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      this.dbConn = DriverManager.getConnection(connectionURL);
      System.out.println("New Database " + this.dbName + " created!");
    }
    catch (ClassNotFoundException ex)
    {
      // print somethign wrong with datbase 
      System.out.println("Driver not found, check library");
    }
    catch (SQLException se)
    {
      // pritn something wrong with sql
      System.out.println("SQL Connection error, Db was not created!");
    }
  }

  public void createTable(String newTable, String dbName)
  {
    System.out.println(newTable); // for debugging prints SQL statement
    setDbName(dbName); // set dbName to dbName above 
    setDbConn(); // set database connection
    Statement s; // blank statement for SQL
    try
    {
      s = this.dbConn.createStatement(); // create blank stateement s
      s.execute(newTable); // place the SQL in s and execute
      System.out.print("New table created!");
      this.dbConn.close();
    }
    catch (Exception e)
    {
      // print something wrong with table 
      System.err.println("Error creating table " + newTable);
      e.printStackTrace();
    }
  }

  public Object[][] to2ndArray(ArrayList<ArrayList<String>> data)
  {
    // a 2d arrau tp rcv values from data
    if (data.size() == 0) // if empty data (from databse)
    {
      Object[][] dataList = new Object[0][0];
      return dataList;
    }
    else // if we have data
    {
      // get a row and use it to get the number of columns
      int columnCount = data.get(0).size();       // number of columns

      Object[][] dataList = new Object[data.size()][columnCount];
      // construct dataList based on size of data
      // loop to read each cell of each row into array
      for (int r = 0; r < data.size(); r++)
      {
        // get the row
        ArrayList<String> row = data.get(r);
        // loop to read each memeber of row
        for (int c = 0; c < columnCount; c++)
        {
          // read the cell
          dataList[r][c] = row.get(c);
        }
      }
      return dataList;
    }
  }

  public static void main(String[] args)
  {

    String dbName = "HomeHealthCare";
    String tableName = "Patients";
    String[] columnNames
      =
      {
        "PatientID", "Name", "Gender", "Weight", "Height", "Age", "Disability",
        "Calories", "NextAppointment", "Home", "Treatment"
      };
    String dbQuery = "INSERT INTO Patients VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    JavaDatabase dbObj = new JavaDatabase(dbName);
    Connection myDbConn = dbObj.getDbConn();

  }

}
