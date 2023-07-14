package carsharing;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Customer {
    static int ret=0;

    private String addCustomerInTableQuery = "Insert Into Customer (NAME ) Values (?)";
    private String dropTableQuery = "Drop table Customer";
//private String deleteCustomerFromTableQuery = "Select from Customer Where Name=? ";
private String UpdateRentStatusCustomerInTableQuery = "Update Customer Set Rented_Car_ID=?, car_Name=?, car_company_name=?   Where ID=? ";
private String removeRentStatusCustomerInTableQuery = "Update Customer Set Rented_Car_ID=null, car_Name=null, car_company_name=null   Where ID=? ";
private String RetrieveAllCustomerFromTableQuery = "Select * from Customer ";
private String RetrieveCustomerFromTableQuery = "Select * from Customer WHERE ID=? ";
private   String createTableQuery = "CREATE TABLE IF NOT EXISTS "+
        "Customer(ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(50) UNIQUE NOT NULL, Rented_Car_ID INT ," +
        "car_Name VARCHAR(50), car_company_name VARCHAR(50), \n" +
        "FOREIGN KEY (Rented_Car_ID)\n" +
        "    REFERENCES Car(id))";
//private   String createTableQuery = "CREATE TABLE IF NOT EXISTS "+
//        "Customer(ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(50) UNIQUE NOT NULL, Rented_Car_ID INT ," +
//        "carName VARCHAR(50), car_company_name VARCHAR(50) \n" +
//        "FOREIGN KEY (Rented_Car_ID)\n" +
//        "    REFERENCES Car(id))";
//private   String createTableQuery = "CREATE TABLE IF NOT EXISTS Customer(ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(50) UNIQUE NOT NULL, Company_ID INT NOT NULL Foreign Key Refrences Company(id))";

private String dbURL;

    public Customer(String url) {
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
//            System.out.println("Table created successfully.");
            // Close the statement and connection
            statement.close();
            //=============
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<String> getCustomerList(/*int companyId*/) throws SQLException {

//        System.out.println("this car id came in: "+companyId);
//        ResultSet rs = null;
         List<String> customerList=new ArrayList<>();
        try {
            // Establish the JDBC connection
            Connection connection = DriverManager.getConnection(dbURL);
            //==============
            PreparedStatement statement = connection.prepareStatement(RetrieveAllCustomerFromTableQuery);
//            statement.setInt(1,companyId);
            ResultSet rs=statement.executeQuery();


            System.out.println(rs);
            while (rs.next() ){

                customerList.add(rs.getString(1)+". "+rs.getString(2));

            }

            if (customerList.isEmpty()) System.out.println("The customer list is empty!");
                //=============
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

   public void addCustomer(String customerName){
       try {
           // Establish the JDBC connection
           Connection connection = DriverManager.getConnection(dbURL);
           //==============
           // Create a statement object
           PreparedStatement statement = connection.prepareStatement(addCustomerInTableQuery);
           statement.setString(1,customerName);
//           statement.setInt(2,carID);

           statement.executeUpdate();
           // Execute the create table query
           System.out.println("The customer was created!");
           // Close the statement and connection
           statement.close();
           //=============
           connection.close();
       } catch (SQLException e) {
           e.printStackTrace();
       }

   }
   public String getRentedCarDetails(int customerID){
        String rentedCarDetails="";
       System.out.println("yeh customer id aae h: "+customerID);
       try {
           // Establish the JDBC connection
           Connection connection = DriverManager.getConnection(dbURL);
           //==============
           // Create a statement object
           PreparedStatement statement = connection.prepareStatement(RetrieveCustomerFromTableQuery);
           statement.setInt(1,customerID);
           ResultSet rs=statement.executeQuery();
//           PreparedStatement statement = connection.prepareStatement(RetrieveAllCustomerFromTableQuery);
//             "Update Set Rented_Car_ID=?, car_Name=?, car_company_name=?  from Customer Where ID=? ";



           while (rs.next())
           {

//               System.out.println(rs.toString());
               String carID=rs.getString("Rented_Car_Id");
               if (carID==null ||carID.isEmpty()) {
               System.out.println("You didn't rent a car!");
               break;
//
               }else{

               String carName=rs.getString(4);
               String carCompanyName=rs.getString(5);
               System.out.println("Your rented car:\n" +carName+
                       "\n" +
                       "Company:\n" + carCompanyName.substring(3));
               }

           }

           System.out.println("==========");

           //=============
           // Close the statement and connection
           statement.close();
           connection.close();
       } catch (SQLException e) {
           e.printStackTrace();
       }


        return rentedCarDetails;


    }
    public void addRentedCarDetails(int customerID , String carName, String carCompanyName, int carID){
        String rentedCarDetails="";

            //for checking k kaheen pehle se to rented nai h
        //lekin it is better to use it in main menu, so we are not using it here
//        if (allReadyRented(customerID) ){
//            return;
//        }
        try {
            // Establish the JDBC connection
            Connection connection = DriverManager.getConnection(dbURL);
            //==============
            // Create a statement object
            PreparedStatement statement = connection.prepareStatement(UpdateRentStatusCustomerInTableQuery);

//            System.out.println("Add details me aaya");


            System.out.println("Rented car ki detail add krrha hai");
            statement.setInt(1,carID);
            statement.setString(2,carName);
            statement.setString(3,carCompanyName);
            statement.setInt(4,customerID);

            statement.executeUpdate();

            System.out.println("You rented "+"'"+carName+"'");
            System.out.println("==========");

            //=============
            // Close the statement and connection
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }




    }
    public void removeRentedCarDetails(int customerID ){
//    public void removeRentedCarDetails(int customerID , String carName, String carCompanyName, int carID){
        String rentedCarDetails="";
//        int ret=0;
//        if (ret>0){
//            System.out.println("You've returned a rented car\n");
//
//            ret=0;
//        }
//        else {

        try {
            // Establish the JDBC connection
            Connection connection = DriverManager.getConnection(dbURL);
            //==============
            // Create a statement object
            PreparedStatement statement = connection.prepareStatement(removeRentStatusCustomerInTableQuery);
            statement.setInt(1,customerID);
            PreparedStatement statement2 = connection.prepareStatement(RetrieveCustomerFromTableQuery);
            statement2.setInt(1,customerID);
//             "Update Set Rented_Car_ID=null, car_Name=null, car_company_name=null  from Customer Where ID=? ";

            ResultSet rs=statement2.executeQuery();
            System.out.println(" remove rented details me ye aaya: \n");
            while (rs.next()) {

//               System.out.println(rs.toString());
                String carID = rs.getString("Rented_Car_Id");
                if (carID == null || carID.isEmpty()) {
                    System.out.println("You didn't rent a car!");
//                    return ;
//
                }
                else {

                    statement.executeUpdate();
                    System.out.println("You've returned a rented car!\n");
                    System.out.println("==========");
                }
            }


            // Close the statement and connection
            statement2.close();
            statement.close();
            //=============
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public boolean allReadyRented(int customerID)
    {
        String rentedCarDetails="";

        try {
            // Establish the JDBC connection
            Connection connection = DriverManager.getConnection(dbURL);
            //==============
            // Create a statement object
            //for checking k kaheen pehle se to rented nai h
            PreparedStatement statement = connection.prepareStatement(RetrieveCustomerFromTableQuery);
            statement.setInt(1, customerID);
            ResultSet rs = statement.executeQuery();
            System.out.println("check details me aaya");
            while (rs.next()) {
                String carIDD = rs.getString("Rented_Car_Id");
                if (carIDD != null) {
                    System.out.println("You've already rented a car!");
                    return true;
//
                }

            }

            statement.close();
                    //=============
            connection.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }



       return false;
    }



public void dropTable()
    {
        String rentedCarDetails="";

        try {
            // Establish the JDBC connection
            Connection connection = DriverManager.getConnection(dbURL);
            //==============
            // Create a statement object
            //for checking k kaheen pehle se to rented nai h
            Statement statement=connection.createStatement();
            statement.executeUpdate(dropTableQuery);


            statement.close();
            System.out.println("Table drpeed");
                    //=============
            connection.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }




    }

}


