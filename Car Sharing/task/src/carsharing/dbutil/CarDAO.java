package carsharing.dbutil;

import carsharing.model.Car;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CarDAO implements DataAccessObject<Car> {
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS CAR (" +
            "id INT GENERATED ALWAYS AS IDENTITY, " +
            "name VARCHAR_IGNORECASE(255) UNIQUE not NULL, " +
            "company_id INT NOT NULL, " +
            "PRIMARY KEY (id)," +
            "FOREIGN KEY (company_id) REFERENCES COMPANY (id))";
    private static final String SELECT_ALL = "SELECT * FROM CAR";
    private static final String SELECT = "SELECT * FROM CAR WHERE id = %d";
    private static final String INSERT_DATA = "INSERT INTO CAR VALUES (DEFAULT, '%s', '%d')";
    private static final String UPDATE_DATA = "UPDATE CAR SET name = '%s', company_id = '%d'" +
            " WHERE id = %d";
    private static final String DELETE_DATA = "DELETE FROM CAR WHERE id = %d";
    private static final Path ROOT = Paths.get("src", "carsharing", "db");
    private static final String BASE_URL = "jdbc:h2:./" + ROOT + "/";
    private final DbClient<Car> dbClient;

    public CarDAO(String name) {
        String url = BASE_URL + name;
        dbClient = new CarDbClient(url);
        dbClient.run(CREATE_TABLE);
    }

    @Override
    public List<Car> findAll() {
        return dbClient.selectAll(SELECT_ALL);
    }

    @Override
    public Car findById(int id) {
        Car car = dbClient.select(String.format(SELECT, id));
        if (car != null) {
            System.out.println("Found car with id " + id);
            return car;
        } else {
            System.out.println("Couldn't find car with id " + id);
            return null;
        }
    }

    @Override
    public void add(Car car) {
        dbClient.run(String.format(INSERT_DATA, car.getName(), car.getCompanyId()));
        System.out.println("The car was added!");
    }

    @Override
    public void update(Car car) {
        dbClient.run(String.format(UPDATE_DATA, car.getName(), car.getCompanyId(), car.getId()));
    }

    @Override
    public void deleteById(int id) {
        dbClient.run(String.format(DELETE_DATA, id));
    }
}
