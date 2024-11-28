package carsharing.dbutil;

import carsharing.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDbClient extends DbClient<Customer> {

    CustomerDbClient(String url) {
        super(url);
    }

    List<Customer> selectAll(String query) {
        List<Customer> customer = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(url);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            con.setAutoCommit(true);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Integer rentedCarId = (Integer) rs.getObject("rented_car_id");
                customer.add(new Customer(id, name, rentedCarId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
}
