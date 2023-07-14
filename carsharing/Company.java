package carsharing;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Company {
    private String addCompanyInTableQuery = "Insert Into Company (NAME) Values (?)";
//private String deleteCompanyFromTableQuery = "Select from Company Where Name=? ";
//private String UpdateCompanyInTableQuery = "Update Company Set Name=? from Company Where Name=? ";
private String RetrieveCompanyFromTableQuery = "Select * from Company ";
private   String createTableQuery = "CREATE TABLE IF NOT EXISTS Company(ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(50) UNIQUE NOT NULL)";

private String dbURL;

    public Company(String url) {
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

    public List<String> getCompanyList() throws SQLException {

//        ResultSet rs = null;
         List<String> companyList=new ArrayList<>();
        try {
            // Establish the JDBC connection
            Connection connection = DriverManager.getConnection(dbURL);
            //==============
            // Create a statement object
            Statement statement = connection.createStatement();
            // Execute the create table query
            ResultSet rs=statement.executeQuery(RetrieveCompanyFromTableQuery);

            // Close the statement and connection

//        System.out.println(rs);
        while (true){
            assert rs != null;
            if (!rs.next()) break;
            companyList.add(rs.getString(1)+". "+rs.getString(2));

        }

        if (companyList.isEmpty()) System.out.println("The company list is empty");
        System.out.println("==========");
            //=============
        statement.close();
        connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companyList;
    }

   public void addCompany(String companyName){
       try {
           // Establish the JDBC connection
           Connection connection = DriverManager.getConnection(dbURL);
           //==============
           // Create a statement object
           PreparedStatement statement = connection.prepareStatement(addCompanyInTableQuery);
           statement.setString(1,companyName);
           statement.executeUpdate();
           // Execute the create table query
           System.out.println("The company was created!");
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


