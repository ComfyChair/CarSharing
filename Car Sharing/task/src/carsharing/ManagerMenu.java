package carsharing;

import carsharing.dbutil.CarDAO;
import carsharing.dbutil.CompanyDAO;
import carsharing.model.Car;
import carsharing.model.Company;

import java.util.List;
import java.util.Scanner;

public class ManagerMenu {
    private static final String MAIN_MENU = """
            1. Company list
            2. Create a company
            0. Back""";
    private static final String COMPANY_MENU = "%s company:\n 1. Car list\n 2. Create a car\n0. Back\n";
    private final Scanner scanner;
    private final CompanyDAO companyDAO;
    private final CarDAO carDAO;
    
    public ManagerMenu(String dbName, Scanner scanner) {
        this.scanner = scanner;
        this.companyDAO = new CompanyDAO(dbName);
        this.carDAO = new CarDAO(dbName);
        managerMenu();
    }

    private void managerMenu() {
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
            Company company = CommonMenus.chooseCompany(scanner, companies);
            if (company != null) {
                companyMenu(company);
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
                case "1" -> {
                    List<Car> cars = carDAO.findByCompanyId(company.getId());
                    CommonMenus.carChoiceMenu(cars, String.format("%s cars:", company.getName()));
                }
                case "2" -> addNewCar(company);
                default -> System.out.println("Invalid input");
            }
        }
    }

    private void addNewCar(Company company) {
        System.out.print("Enter the car name: ");
        String name = scanner.nextLine();
        carDAO.add(new Car(name, company.getId()));
    }
}
