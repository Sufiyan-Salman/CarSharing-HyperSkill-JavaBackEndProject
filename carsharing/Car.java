package carsharing;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Car {
    private String addCarInTableQuery = "Insert Into Car (NAME, Company_ID ) Values (?, ?)";
//private String deleteCarFromTableQuery = "Select from Car Where Name=? ";
//private String UpdateCarInTableQuery = "Update Car Set Name=? from Car Where Name=? ";
private String RetrieveCarFromTableQuery = "Select * from Car Where Company_ID=? ";
private   String createTableQuery = "CREATE TABLE IF NOT EXISTS " +
        "Car(ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(50) UNIQUE NOT NULL, Company_ID INT NOT NULL,\n " +
        "FOREIGN KEY (Company_ID)\n" +
        "    REFERENCES Company(id))";

private String dbURL;

    public Car(String url) {
        this.dbURL = url;
    }


    public void createTable(){
        try {
            // Establish the JDBC connection
            Connection connection = DriverManager.getConnection(dbURL);
            //==============
            // Create a statement object
            Statement statement = connection.createStatement();
            // Execute the create table query
            statement.executeUpdate(createTableQuery);
            // Close the statement and connection
            statement.close();
            //=============
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<String> getCarList(int companyId) throws SQLException {


         List<String> carList=new ArrayList<>();
        try {
            // Establish the JDBC connection
            Connection connection = DriverManager.getConnection(dbURL);
            //==============
            PreparedStatement statement = connection.prepareStatement(RetrieveCarFromTableQuery);
            statement.setInt(1,companyId);
            ResultSet rs=statement.executeQuery();


            System.out.println(rs);
            while (rs.next() ){
                carList.add(rs.getString(2));
//                carList.add(rs.getString(1)+". "+rs.getString(2));

            }

            if (carList.isEmpty()) System.out.println("The car list is empty!");
            System.out.println("==========");

                //=============
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carList;
    }

   public void addCar(String carName, int companyID){
       try {
           // Establish the JDBC connection
           Connection connection = DriverManager.getConnection(dbURL);
           //==============
           // Create a statement object
           PreparedStatement statement = connection.prepareStatement(addCarInTableQuery);
           statement.setString(1,carName);
           statement.setInt(2,companyID);

           statement.executeUpdate();
           // Execute the create table query
           System.out.println("The car was created!");
           System.out.println("==========");

           // Close the statement and connection
           statement.close();
           //=============
           connection.close();
       } catch (SQLException e) {
           e.printStackTrace();
       }

   }

}


