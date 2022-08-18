package spartanDB;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestConnection {

    String dbURL = "jdbc:oracle:thin:@44.202.63.224:1521:XE";
    String dbUsername = "SP";
    String dbPassword = "SP";
    String query = "SELECT name,gender,phone,spartan_id FROM SPARTANS";

    Connection connection ; // STEP 1 CREATE CONNECTION

    Statement statement ; //STEP 2 FOR SENDING QUERY I NEED STATEMENT

    ResultSet resultSet ;//STEP 3 STORE THE QUERY RESULT

    public TestConnection() throws SQLException {
    }


    @BeforeEach
    public void connectDB() throws SQLException {


        connection = DriverManager.getConnection(dbURL,dbUsername,dbPassword); // STEP 1 CREATE CONNECTION

         statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); //STEP 2 FOR SENDING QUERY I NEED STATEMENT

         resultSet = statement.executeQuery(query);//STEP 3 STORE THE QUERY RESULT



    }


    @AfterEach
    public void closeDB() throws SQLException {

        connection.close();
        statement.close();
        resultSet.close();

    }

    @Test
    public void test1() throws SQLException {

        resultSet.next(); // move to next row
        int row = resultSet.getRow();
        System.out.println("row = " + row);

        //go to last row and get rowNumber : WE NEED TO CONFIGURE STATEMENT OBJECT INSENSETIVE => IT IS ALLOWS TO US UP AND DOWN TO DB
        resultSet.last();
        System.out.println("resultSet = " + resultSet.getRow());


        //WE NEED TO ALSO COLUMN INFO FOR THIS WE HAVE RSMD

    }

    @Test
    public void test2() throws SQLException {
       // resultSet.next();//if there is next row go there and return boolean

        while (resultSet.next()){
            System.out.println("resultSet = " + resultSet.getString(1)); // there is only 1 column in this example
        }

        //if we have more than one column we need result set meta data

        ResultSetMetaData rsmd = resultSet.getMetaData();
        String columnName = rsmd.getColumnName(1);
        System.out.println("columnName = " + columnName);

        int columnCount = rsmd.getColumnCount();

        while(resultSet.next()){ //row
            for (int i = 1; i <= columnCount ; i++) { // column


            }
        }
    }

    @Test
    public void test3() throws SQLException { // this makes my query dynamic
         //databasemetadata gives general info about data
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        System.out.println("databaseMetaData.getDriverName() = " + databaseMetaData.getDriverName());
        System.out.println("databaseMetaData.getDriverName() = " + databaseMetaData.getDatabaseProductName());

        //if we have more than one column we need result set meta data
        ResultSetMetaData rsmd = resultSet.getMetaData();
        String columnName = rsmd.getColumnName(1);
        System.out.println("columnName = " + columnName);

        int columnCount = rsmd.getColumnCount();

        List<Map<String,Object>> queryList = new ArrayList<>();

        while(resultSet.next()){ // to handle each row data
            Map<String,Object> eachRow = new HashMap<>();
            for (int i = 1; i <= columnCount ; i++) { // to handle each column info. column starts 1

                eachRow.put(rsmd.getColumnName(i) , resultSet.getObject(i)); // I don't know have many column we have that is why I put "i"
            }
            queryList.add(eachRow); // I store all my Map in a List
        }

        System.out.println("queryList = " + queryList);


    }
}