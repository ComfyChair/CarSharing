package carsharing.dbutil;

import carsharing.model.Company;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CompanyDAO implements DataAccessObject<Company> {
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS COMPANY (" +
            "id INT GENERATED ALWAYS AS IDENTITY, " +
            "name VARCHAR_IGNORECASE(255) UNIQUE not NULL, " +
            "PRIMARY KEY ( id ))";
    private static final String SELECT_ALL = "SELECT * FROM COMPANY";
    private static final String SELECT = "SELECT * FROM COMPANY WHERE id = %d";
    private static final String INSERT_DATA = "INSERT INTO COMPANY VALUES (DEFAULT, '%s')";
    private static final String UPDATE_DATA = "UPDATE COMPANY SET name " +
            "= '%s' WHERE id = %d";
    private static final String DELETE_DATA = "DELETE FROM COMPANY WHERE id = %d";
    private static final Path ROOT = Paths.get("src", "carsharing", "db");
    private static final String BASE_URL = "jdbc:h2:./" + ROOT + "/";
    private final DbClient dbClient;

    public CompanyDAO(String name) {
        String url = BASE_URL + name;
        dbClient = new DbClient(url);
        dbClient.run(CREATE_TABLE);
    }


    @Override
    public List<Company> findAll() {
        return dbClient.selectCompanies(SELECT_ALL);
    }

    @Override
    public Company findById(int id) {
        Company company = dbClient.selectCompany(String.format(SELECT, id));
        if (company != null) {
            System.out.println("Found company with id " + id);
            return company;
        } else {
            System.out.println("Couldn't find company with id " + id);
            return null;
        }
    }

    @Override
    public void add(Company company) {
        dbClient.run(String.format(INSERT_DATA, company.getName()));
        System.out.println("The company was created!");
    }

    @Override
    public void update(Company company) {
        dbClient.run(String.format(UPDATE_DATA, company.getName(), company.getId()));
    }

    @Override
    public void deleteById(int id) {
        dbClient.run(String.format(DELETE_DATA, id));
    }
}
