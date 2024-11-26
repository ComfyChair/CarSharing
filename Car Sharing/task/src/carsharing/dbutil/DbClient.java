package carsharing.dbutil;

import carsharing.model.Car;
import carsharing.model.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class DbClient {
    private static final String DRIVER = "org.h2.Driver";
    private final String url;

    DbClient(String url) {
        this.url = url;
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    void run(String sql) {
        try (Connection con =  DriverManager.getConnection(url);
             Statement stmt = con.createStatement()) {
            con.setAutoCommit(true);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Company selectCompany(String query) {
        List<Company> companies = selectCompanies(query);
        if (companies.isEmpty()) {
            return null;
        } else if (companies.size() > 1) {
            throw new IllegalStateException("More than one company found for query: " + query);
        } else {
            return companies.getFirst();
        }
    }

    List<Company> selectCompanies(String query) {
        List<Company> companies = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(url);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            con.setAutoCommit(true);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                companies.add(new Company(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

    Car selectCar(String query) {
        List<Car> cars = selectCars(query);
        if (cars.isEmpty()) {
            return null;
        } else if (cars.size() > 1) {
            throw new IllegalStateException("More than one car found for query: " + query);
        } else {
            return cars.getFirst();
        }
    }

    List<Car> selectCars(String query) {
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
