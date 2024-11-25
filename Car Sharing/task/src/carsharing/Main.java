package carsharing;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String name = (args.length > 1 && args[0].equals("-databaseFileName")) ? args[1] : "carsharing";
        DbCompanyDAO dbCompanyDAO = new DbCompanyDAO(name);
        new CarSharingApp(dbCompanyDAO);
    }
}