package carsharing;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class CarSharingDB {
    private static final Logger log = Logger.getLogger(CarSharingDB.class.getName());
    private static final Path ROOT = Paths.get("src", "carsharing", "db");
    private static final String BASE_URL = "jdbc:h2:./" + ROOT + "/";
    private static final String DRIVER = "org.h2.Driver";
    private Connection connection;


    CarSharingDB(String name) {
        try {
            Class.forName(DRIVER);
            log.info("Connecting to database " + BASE_URL + name);
            connection = DriverManager.getConnection(BASE_URL + name);
            connection.setAutoCommit(true);
            createTable();
            connection.close();
        } catch (SQLException e) {
            log.warning("Could not connect to database at " + BASE_URL + name);
        } catch (ClassNotFoundException e) {
            log.warning("Could not load database driver " + DRIVER);
        }
    }

    private void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS COMPANY " +
                "(id INTEGER not NULL, " +
                "name VARCHAR_IGNORECASE(255), " +
                "PRIMARY KEY ( id ))";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(createTableSQL);
            statement.close();
        } catch (SQLException e) {
            log.warning("Could not create table " + createTableSQL);
        }
    }
}
