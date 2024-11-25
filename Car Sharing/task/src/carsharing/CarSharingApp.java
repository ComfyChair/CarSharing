package carsharing;

import java.util.List;
import java.util.Scanner;

public class CarSharingApp {
    private static final String LOGIN_MENU =    "1. Log in as a manager\n" +
            "0. Exit";
    private static final String MAIN_MENU = """
            
            1. Company list
            2. Create a company
            0. Back""";
    private final DbCompanyDAO dbCompanyDAO;
    private final Scanner scanner;

    public CarSharingApp(DbCompanyDAO dbCompanyDAO) {
        this.dbCompanyDAO = dbCompanyDAO;
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
                case "1" -> listCompanies();
                case "2" -> newCompany();
            }
        }
    }

    private void newCompany() {
        System.out.print("Enter the company name: ");
        String name = scanner.nextLine();
        dbCompanyDAO.add(new Company(name));
    }

    private void listCompanies() {
        List<Company> companies = dbCompanyDAO.findAll();
        if (companies.isEmpty()) {
            System.out.println("\nThe company list is empty!");
        } else {
            System.out.println("\nCompany list:");
            for (Company company : companies) {
                System.out.printf("%d. %s%n", company.getId(), company.getName());
            }
        }
    }
}
