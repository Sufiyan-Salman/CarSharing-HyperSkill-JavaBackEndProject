package carsharing;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void customerMenu(int customerIdSelection, Company company, Scanner sc,Car car, Customer customer) throws SQLException {

        while (true)
        {
            //yahan pe ab hamare paas customer id available hogi
            System.out.println("=============");
            System.out.println(
                    "1. Rent a car\n" +
                            "2. Return a rented car\n" +
                            "3. My rented car\n" +
                            "0. Back");
            int customerMenuSelection= sc.nextInt(); //reads string.
            if(customerMenuSelection==0) break;
            else
            {
                //ek customer k paas ek rented car hogi
                if(customerMenuSelection==1){
                    //rent a car
                    if (customer.allReadyRented(customerIdSelection)) continue;
//
                    //choose a company menu starts here
                    List<String> companyList=company.getCompanyList();

                    if (companyList.isEmpty()) continue;
                    //show company list
                    showCompanyList(companyList);
                    //=========================

                    int companyId= sc.nextInt();
                    if (companyId==0 ) break; //0 means back
                    else
                    { // yane back jane ka nai kaha aur car me ghusna hai
                        System.out.println("Choose a car: ");
                        List<String>carList = car.getCarList(companyId);
                        if (carList.isEmpty())continue;
                        showCarList(carList);
//                      System.out.println("0. Back"); // either do this or write it in carlist function but ye back ka option bas isi me chahiye hmen

                        int carId= sc.nextInt();
                        if (carId==0) continue;
                        String wantedCarsCompanyName=companyList.get(companyId - 1);
                        String wantedCarName=carList.get(carId-1);
                        customer.addRentedCarDetails(customerIdSelection , wantedCarName, wantedCarsCompanyName , carId);

                        //choose the car ends here
                    }

                }
                else if(customerMenuSelection==2){
                    //return rented car
                    customer.removeRentedCarDetails( customerIdSelection);

                }
                else if(customerMenuSelection==3){
                    //my rented cars
                    customer.getRentedCarDetails(customerIdSelection);

                }


            }

        }
    }
    public static void showCarList(List<String> carList){
//        if (carList.isEmpty()) return;
        int i=1;
        for (String carr : carList) {
            System.out.println(i+". "+carr);
            i++;
        }
        System.out.println("0. Back"); // is se company menu me bila waja ye option show horha h , which should not b seen , either tolerator or customer menu me alag se ye print kradun
        //show car list ends here
    }
    public static void showCompanyList(List<String> companyList){
//        if (companyList.isEmpty()) return;
        for (String company : companyList) {
            System.out.println(company);
        }
        System.out.println("0. Back");
        //show car list ends here
    }
    public static void showCustomerList(List<String> customerList){
//        if (customerList.isEmpty()) return;
        System.out.println("Choose a customer: ");
        for (String customer : customerList) {
            System.out.println(customer);
//            System.out.println(i+". "+company);
        }
        System.out.println("0. Back");
        //show customer list ends here
    }
    public static void carMenu(int companyId, String companyName, Scanner sc,Car car) throws SQLException {
        while (true) {
            System.out.println("Compnay with this id is selected: "+companyId);
            System.out.println("");
//            System.out.println(cL.get(companyId - 1) + " company: \n");
            System.out.println(companyName + " company: \n");
            System.out.println("1. Car list\n" +
                    "2. Create a car\n" +
                    "0. Back");
            int carMenuSelectedOption = sc.nextInt();
            if (carMenuSelectedOption == 1) {
                //car lise
                List<String>carList = car.getCarList(companyId);
                if (carList.isEmpty()) continue;
                //choose a car starts here , show car list
                showCarList(carList);

                //isme back wala option show nai hona chahiye , either we tolerate it or customer menu wale me back ka option alag se print kraden
                //choose the car ends here , show car list


            } else if (carMenuSelectedOption == 2) {
                System.out.println("Enter the car name: ");
                String carName= sc.nextLine();
                carName= sc.nextLine();
                System.out.println("company with this ID is going for car list: "+companyId);
                car.addCar(carName , companyId);



            } else if (carMenuSelectedOption == 0) {
                break;

            }

        }

    }
    public static void companyMenu(Company company , Car car, Scanner sc) throws SQLException {

            while (true){
                System.out.print("1. Company list \n" +"2. Create a company \n"+"0. Back\n> ");
                int companyMenuSelectedOption= sc.nextInt();
                if(companyMenuSelectedOption==0) break;
                else if( companyMenuSelectedOption==1)
                {
                    //choose a company menu starts here
                    List<String> companyList=company.getCompanyList();
                    if (companyList.isEmpty()) continue;
                    showCompanyList(companyList);



                    int companyId= sc.nextInt();
                    if (companyId!=0 ) { // yane back jane ka nai kaha aur car me ghusna hai
                        carMenu(companyId, companyList.get(companyId - 1), sc, car);
                    }

                    //choose a company ends here

                }else if( companyMenuSelectedOption==2){
                    //create a company
                    System.out.print("Enter the company name: \n>");
                    String cName= sc.nextLine();
                    cName= sc.nextLine();
                    company.addCompany(cName);
//                        System.out.println("The company was created!");
//                       continue;
                }
//                else if( companyMenuSelectedOption==3){// for deletion
//                    System.out.print("Enter the company name: \n>");
//                    String cName= sc.nextLine();
//                    cName= sc.nextLine();
//
//                }

            }




    }

    public static void main(String[] args) throws SQLException {
        // write your code here
        //h2 db me file create honi chaiiye
//        String url = "jdbc:h2:file:../src/carsharing/db/carsharing";

        String url = "jdbc:h2:file:./src/carsharing/db/carsharing";
//        String url = "jdbc:h2:file:./src/carsharing/db/carsharingg";

        String username = "";
        String password = "";



            Company company=new Company(url);
            Car car=new Car(url);
            Customer customer=new Customer(url);
            company.createTable();
            car.createTable();
            customer.createTable();
            Scanner sc= new Scanner(System.in); //System.in is a standard input stream.
            // menu for company
//        customer.dropTable();
        while (true)
        {

            System.out.print("1. Log in as a manager\n" +
                    "2. Log in as a customer\n" +
                    "3. Create a customer\n" +
                    "0. Exit >");
            int selection= sc.nextInt(); //reads string.
            if (selection==0) break; // exit
            //company menu starts
            else if (selection==1) {
                   companyMenu(company,car,sc);
////                System.out.println("\n");
//                while (true){
//                    System.out.print("1. Company list \n" +"2. Create a company \n"+"0. Back\n> ");
//                    selection= sc.nextInt();
//                    if(selection==0) break;
//                    else if( selection==1)
//                    {
//                        List<String> cL=company.getCompanyList();
//                        if (cL.isEmpty()) continue;
//                        System.out.println("Choose a company: \n");
//                        for (String companyy: cL   ) {
//                            System.out.println(companyy);
//                        }
//                        System.out.println("0. Back");
//                        //=========================
//
//
//                        int companyId= sc.nextInt();
//                        if (companyId!=0 ) { // yane back jane ka nai kaha
//                            while (true) {
//                                System.out.println("Compnay with this id is selected: "+companyId);
//                                System.out.println("");
//                                System.out.println(cL.get(companyId - 1) + " company: \n");
//                                System.out.println("1. Car list\n" +
//                                        "2. Create a car\n" +
//                                        "0. Back");
//                                 int carOptions = sc.nextInt();
//                                if (carOptions == 1) {
//                                    List<String>carList = car.getCarList(companyId);
//                                    int i=1;
//                                    for (String carr : carList) {
//                                        System.out.println(i+". "+carr);
//                                        i++;
//                                    }
//                                    continue;
//
//                                } else if (carOptions == 2) {
//                                    System.out.println("Enter the car name: ");
//                                    String carName= sc.nextLine();
//                                    carName= sc.nextLine();
//                                    System.out.println("company with this ID is going for car list: "+companyId);
//                                    car.addCar(carName , companyId);
//
//
//
//                                } else if (carOptions == 0) {
//                                    break;
//
//                                }
//
//                            }
//                        }//   =========                     System.out.println("\n");
////                        continue;
//
//                    }else if( selection==2){
//                        System.out.print("Enter the company name: \n>");
//                        String cName= sc.nextLine();
//                        cName= sc.nextLine();
//                        company.addCompany(cName);
////                        System.out.println("The company was created!");
////                       continue;
//                    }
//                    else if( selection==3){
//                        System.out.print("Enter the company name: \n>");
//                        String cName= sc.nextLine();
//                        cName= sc.nextLine();
////                        company.deleteCompany(cName);
////                        System.out.println("The company was created!");
////                       continue;
//                    }
//
//                }
//
            }
            //company menu ends here

            else if (selection==2)
            //log in as customer
            {
                while(true){
                    List<String> customerList=customer.getCustomerList();
                    if (customerList.isEmpty()) break;
                    showCustomerList(customerList);
                    int customerIdSelection= sc.nextInt(); //reads integer
                    if (customerIdSelection==0) break;
                    else
                    { // if back option not chossen
                        //customer menu starts here
                        customerMenu(customerIdSelection,company,sc,car,customer);
                        //costumer menu ends here
                    }

                }

            }
            //create a customer
            else if (selection==3) {
                System.out.println("Enter the customer name: ");
                String customerName= sc.nextLine(); //reads string.
                customerName= sc.nextLine(); //reads string.
                customer.addCustomer(customerName);

            }
        }


    }
}
