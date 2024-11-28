package carsharing;

import carsharing.dbutil.CarDAO;
import carsharing.dbutil.CompanyDAO;
import carsharing.dbutil.CustomerDAO;
import carsharing.model.Car;
import carsharing.model.Company;
import carsharing.model.Customer;

import java.util.List;
import java.util.Scanner;

public class CustomerMenu {
    private static final String CUSTOMER_MENU = """
            1. Rent a car
            2. Return a car
            3. My rented car
            0. Back""";
    private final Customer customer;
    private final Scanner scanner;
    private final CustomerDAO customerDAO;
    private final CompanyDAO companyDAO;
    private final CarDAO carDAO;

    public CustomerMenu(Customer customer, String dbName, Scanner scanner) {
        this.customer = customer;
        this.scanner = scanner;
        this.customerDAO = new CustomerDAO(dbName);
        this.companyDAO = new CompanyDAO(dbName);
        this.carDAO = new CarDAO(dbName);
        customerMenu();
    }

    private void customerMenu() {
        String input = "";
        while (!input.equals("0")) {
            System.out.println(CUSTOMER_MENU);
            input = scanner.nextLine();
            switch (input) {
                case "0" -> {}
                case "1" -> rentCar();
                case "2" -> returnCar();
                case "3" -> rentalInfo();
                default -> System.out.println("Invalid input");
            }
        }
    }

    private void rentCar() {
        List<Company> companies = companyDAO.findAll();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
        } else if (customer.getRentedCarId() != null) {
            System.out.println("You've already rented a car!");
        } else {
            Company company = CommonMenus.chooseCompany(scanner, companies);
            if (company != null) {
                List<Car> cars = carDAO.findByCompanyId(company.getId());
                CommonMenus.carChoiceMenu(cars, "Choose a car:");
                int choice = -1;
                while (choice < 0) {
                    String input = scanner.nextLine();
                    try {
                        choice = Integer.parseInt(input);
                        if (choice < 0 || choice > companies.size()) {
                            Car car = cars.get(choice - 1);
                            customer.setRentedCarId(car.getId());
                            customerDAO.update(customer);
                        } else {
                            choice = -1;
                            System.out.println("Invalid choice");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("\nInvalid input!");
                    }
                }
            }
        }
    }

    private void returnCar() {
        if (customer.getRentedCarId() == null) {
            System.out.println("You didn't rent a car!");
        } else {
            customer.setRentedCarId(null);
            customerDAO.update(customer);
            System.out.println("You've returned a rented car!");
        }
    }

    private void rentalInfo() {
        Car rental = carDAO.findById(customer.getRentedCarId());
        Company company = companyDAO.findById(rental.getCompanyId());
        System.out.printf("Your rented car:\n%s", rental.getName());
        System.out.printf("Company:\n%s", company.getName());
    }
}
