package carsharing;

import carsharing.dbutil.CarDAO;
import carsharing.dbutil.CompanyDAO;
import carsharing.dbutil.CustomerDAO;
import carsharing.model.Customer;

import java.util.List;
import java.util.Scanner;

public class CarSharingApp {
    private static final String LOGIN_MENU = """
            
            1. Log in as a manager
            2. Log in as a customer
            3. Create a customer
            0. Exit""";

    private final Scanner scanner;
    private final String dbName;
    private final CustomerDAO customerDAO;

    public CarSharingApp(String dbName) {
        this.scanner = new Scanner(System.in);
        this.dbName = dbName;
        // create Company and Car table if they don't exist
        new CompanyDAO(dbName);
        new CarDAO(dbName);
        this.customerDAO = new CustomerDAO(dbName);
        loginMenu();
    }

    private void loginMenu() {
        String input = "";
        while (!input.equals("0")) {
            System.out.println(LOGIN_MENU);
            input = scanner.nextLine();
            switch (input) {
                case "1" -> new ManagerMenu(dbName, scanner);
                case "2" -> chooseCustomer();
                case "3" -> createCustomer();
            }
        }
    }

    private void chooseCustomer() {
        List<Customer> customers = customerDAO.findAll();
        if (customers.isEmpty()) {
            System.out.println("\nThe customer list is empty!\n");
        } else {
            int choice = -1;
            while (choice < 0) {
                customerChoiceMenu(customers);
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                    if (choice > 0 && choice <= customers.size()) {
                        new CustomerMenu(customers.get(choice - 1), dbName, scanner);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\nInvalid input!");
                }
            }
        }
    }

    private void customerChoiceMenu(List<Customer> customers) {
        System.out.println("\nCustomer list:");
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            System.out.printf("%d. %s\n", i + 1, customer.getName());
        }
        System.out.println("0. Back");
    }

    private void createCustomer() {
        System.out.println("\nEnter the customer name:");
        String name = scanner.nextLine();
        customerDAO.add(new Customer(name));
    }
}
