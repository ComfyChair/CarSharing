package carsharing;


public class Main {

    public static void main(String[] args) {
        String dbName = (args.length > 1 && args[0].equals("-databaseFileName")) ? args[1] : "carsharing";
        new CarSharingApp(dbName);
    }
}