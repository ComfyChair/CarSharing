package carsharing;

import carsharing.dbutil.CarDAO;
import carsharing.model.Car;
import carsharing.model.Company;
import carsharing.dbutil.CompanyDAO;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class CarSharingApp {
    private static final String LOGIN_MENU =    "1. Log in as a manager\n0. Exit";
    private static final String MAIN_MENU = """
            
             1. Company list
             2. Create a company
            0. Back""";
    private static final String COMPANY_MENU = "%s company:\n 1. Car list\n 2. Create a car\n0. Back\n";
    private final CompanyDAO companyDAO;
    private final CarDAO carDAO;
    private final Scanner scanner;

    public CarSharingApp(String dbName) {
        this.companyDAO = new CompanyDAO(dbName);
        this.carDAO  = new CarDAO(dbName);
        this.scanner = new Scanner(System.in);
        loginMenu();
    }

    private void loginMenu() {
        String input = "";
        while (!input.equals("0")) {
            System.out.println(LOGIN_MENU);
            input = scanner.nextLine();
            if (input.equals("1")) {
                mainMenu();
            }
        }
    }

    private void mainMenu() {
        String input = "";
        while (!input.equals("0")) {
            System.out.println(MAIN_MENU);
            input = scanner.nextLine();
            switch (input) {
                case "0" -> {}
                case "1" -> chooseCompany();
                case "2" -> newCompany();
                default -> System.out.println("Invalid input");
            }
        }
    }

    private void newCompany() {
        System.out.print("Enter the company name: ");
        String name = scanner.nextLine();
        companyDAO.add(new Company(name));
    }

    private void chooseCompany() {
        List<Company> companies = companyDAO.findAll();
        if (companies.isEmpty()) {
            System.out.println("\nThe company list is empty!");
        } else {
            int choice = -1;
            while (choice < 0) {
                System.out.println("\nChoose a company:");
                for (int i = 0; i < companies.size(); i++) {
                    Company company = companies.get(i);
                    System.out.printf("%d. %s%n", i + 1, company.getName());
                }
                System.out.println("0. Back");
                String input = scanner.nextLine();
                try {
                    choice = Integer.parseInt(input);
                    if (choice > 0 && choice <= companies.size()) {
                        companyMenu(companies.get(choice - 1));
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\nInvalid input!");
                }
            }
        }
    }

    private void companyMenu(Company company) {
        String input = "";
        while (!input.equals("0")) {
            System.out.printf(COMPANY_MENU, company.getName());
            input = scanner.nextLine();
            switch (input) {
                case "0" -> {}
                case "1" -> listCars(company);
                case "2" -> newCar(company);
                default -> System.out.println("Invalid input");
            }
        }
    }

    private void newCar(Company company) {
        System.out.print("Enter the car name: ");
        String name = scanner.nextLine();
        carDAO.add(new Car(name, company.getId()));
    }

    private void listCars(Company company) {
        List<Car> cars = carDAO.findAll().stream()
                .filter(car -> car.getCompanyId() == company.getId())
                .sorted(Comparator.comparingInt(Car::getId))
                .toList();
        if (cars.isEmpty()) {
            System.out.println("The car list is empty!");
        } else {
            System.out.printf("%s cars:%n", company.getName());
            for (int i = 0; i < cars.size(); i++) {
                Car car = cars.get(i);
                System.out.printf("%d. %s%n", i + 1, car.getName());
            }
        }
    }

}
