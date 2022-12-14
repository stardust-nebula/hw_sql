package database;

import java.sql.*;

import static database.DBInfo.*;

public class DBConnection {
    private Connection connect = null;
    private Statement statement = null;
    private static final String DB_URL = "jdbc:mysql://localhost/AQA17?useSSL=true&serverTimezone=GMT";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "@q6DK5OdbZ2%bvJ";

    public void connect() {
        try {
            connect = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            statement = connect.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ResultSet selectFrom(String tableName) {
        try {
            return statement.executeQuery(String.format("select * from %s", tableName));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public ResultSet executeQuery(String query) {
        try {
            return statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static void writeResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String name = resultSet.getString("name");
            String cityId = resultSet.getString("cityId");
            System.out.println("ID: " + id);
            System.out.println("NAME: " + name);
            System.out.println("CITY ID: " + cityId);
        }
    }

    public void close() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (Exception ignored) {
        }
    }
}
