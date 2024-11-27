package carsharing.dbutil;

import carsharing.model.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDbClient extends DbClient<Car> {

    CarDbClient(String url) {
        super(url);
    }

    List<Car> selectAll(String query) {
        List<Car> car = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(url);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            con.setAutoCommit(true);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int companyId = rs.getInt("company_id");
                car.add(new Car(id, name, companyId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }
}
