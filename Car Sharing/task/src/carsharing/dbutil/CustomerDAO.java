package carsharing.dbutil;

import carsharing.model.Customer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CustomerDAO implements DataAccessObject<Customer> {
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
            "id INT GENERATED ALWAYS AS IDENTITY, " +
            "name VARCHAR_IGNORECASE(255) UNIQUE not NULL, " +
            "rented_car_id INTEGER DEFAULT NULL, " +
            "PRIMARY KEY (id), " +
            "FOREIGN KEY (rented_car_id) REFERENCES CAR (id))";
    private static final String SELECT_ALL = "SELECT * FROM CUSTOMER";
    private static final String SELECT = "SELECT * FROM CUSTOMER WHERE id = %d";
    private static final String INSERT_DATA = "INSERT INTO CUSTOMER VALUES (DEFAULT, '%s', %d)";
    private static final String UPDATE_DATA = "UPDATE CUSTOMER SET name = '%s', rented_car_id = %d " +
            " WHERE id = %d";
    private static final String DELETE_DATA = "DELETE FROM CUSTOMER WHERE id = %d";
    private static final Path ROOT = Paths.get("src", "carsharing", "db");
    private static final String BASE_URL = "jdbc:h2:./" + ROOT + "/";
    private final DbClient<Customer> dbClient;

    public CustomerDAO(String name) {
        String url = BASE_URL + name;
        dbClient = new CustomerDbClient(url);
        dbClient.run(CREATE_TABLE);
    }

    @Override
    public List<Customer> findAll() {
        return dbClient.selectAll(SELECT_ALL);
    }

    @Override
    public Customer findById(int id) {
        Customer customer = dbClient.select(String.format(SELECT, id));
        if (customer != null) {
            return customer;
        } else {
            System.out.println("Couldn't find customer with id " + id);
            return null;
        }
    }

    @Override
    public void add(Customer customer) {
        dbClient.run(String.format(INSERT_DATA, customer.getName(), customer.getRentedCarId()));
        System.out.println("The customer was added!");
    }

    @Override
    public void update(Customer customer) {
        dbClient.run(String.format(UPDATE_DATA, customer.getName(), customer.getRentedCarId(), customer.getId()));
    }

    @Override
    public void deleteById(int id) {
        dbClient.run(String.format(DELETE_DATA, id));
    }
}
