package database;

import java.sql.*;

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

    public ResultSet selectStudentInfoWithCityName() {
        try {
            return statement.executeQuery("select s.id as 'student id', s.name as 'student name', c.cityName as 'city name' from student s INNER JOIN city c on c.id = s.cityId");
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

    private boolean isCityExistByName(String cityName) {
        boolean isCityExists = false;
        try {
            ResultSet resultSet = statement.executeQuery(String.format("select count(cityName) from city where cityName = '%s'", cityName));
            resultSet.next();
            int cityCount = resultSet.getInt(1);
            if (cityCount > 0) {
                isCityExists = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isCityExists;
    }

    public void addNewStudent(String studentName, String cityName) {
        try {
            PreparedStatement prepStCity;
            ResultSet resSetCity;
            connect.setAutoCommit(false);

            if (!isCityExistByName(cityName)) {
                prepStCity = connect.prepareStatement(String.format("INSERT INTO city (cityName) VALUES ('%s')", cityName));
            } else {
                prepStCity = connect.prepareStatement(String.format("Select id From city where cityName = '%s'", cityName));
            }
            resSetCity = prepStCity.executeQuery();
            resSetCity.next();
            int cityId = resSetCity.getInt(1);

            PreparedStatement prepStatStudent = connect.prepareStatement(String.format("INSERT INTO student (name, cityId) VALUES ('%s', %s)", studentName, cityId));
            prepStatStudent.execute();
        } catch (SQLException throwables) {
            try {
                connect.rollback();
                System.out.println("rollback");
            } catch (SQLException e) {
                throwables.printStackTrace();
            }
        } finally {
            try {
                connect.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void writeResultSet(ResultSet resultSet) {
        try {
            System.out.println("ID" + '\t' + "NAME" + '\t' + "CITY NAME");
            while (resultSet.next()) {
                String id = resultSet.getString("student id");
                String name = resultSet.getString("student name");
                String cityId = resultSet.getString("city name");
                System.out.println(id + '\t' + name + '\t' + cityId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
