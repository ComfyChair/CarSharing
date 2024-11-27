package carsharing.dbutil;

import java.sql.*;
import java.util.List;

abstract class DbClient<T> {
    private static final String DRIVER = "org.h2.Driver";
    protected final String url;

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

    T select(String query) {
        List<T> allObjects = selectAll(query);
        if (allObjects.isEmpty()) {
            return null;
        } else if (allObjects.size() > 1) {
            throw new IllegalStateException("More than one car found for query: " + query);
        } else {
            return allObjects.getFirst();
        }
    }

    abstract List<T> selectAll(String query);
}
