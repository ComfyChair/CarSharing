package carsharing;

import carsharing.model.Car;
import carsharing.model.Company;

import java.util.List;
import java.util.Scanner;

class CommonMenus {

    static Company chooseCompany(Scanner scanner, List<Company> companies) {
        System.out.println("\nChoose a company:");
        for (int i = 0; i < companies.size(); i++) {
            Company company = companies.get(i);
            System.out.printf("%d. %s%n", i + 1, company.getName());
        }
        System.out.println("0. Back");
        int choice = -1;
        while (choice < 0) {
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
                if (choice < 0 || choice > companies.size()) {
                    return companies.get(choice - 1);
                } else {
                    choice = -1;
                    System.out.println("Invalid choice");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input!");
            }
        }
        // Back option chosen
        return null;
    }

    static void carChoiceMenu(List<Car> cars, String headline) {
        if (cars.isEmpty()) {
            System.out.println("The car list is empty!");
        } else {
            System.out.println(headline);
            for (int i = 0; i < cars.size(); i++) {
                Car car = cars.get(i);
                System.out.printf("%d. %s%n", i + 1, car.getName());
            }
        }
    }    
}
