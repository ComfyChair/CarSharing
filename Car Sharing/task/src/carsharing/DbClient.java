package carsharing;

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
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Company select(String query) {
        List<Company> companies = selectForList(query);
        if (companies.isEmpty()) {
            return null;
        } else if (companies.size() > 1) {
            throw new IllegalStateException("More than one company found for query: " + query);
        } else {
            return companies.getFirst();
        }
    }

    List<Company> selectForList(String query) {
        List<Company> companies = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(url);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {
            con.setAutoCommit(true);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Company company = new Company(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }
}
