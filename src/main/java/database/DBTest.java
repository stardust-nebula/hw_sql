package database;

import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBTest {
    private DBConnection dbConnection = new DBConnection();

    @Test
    private void dataBaseTest() {
        dbConnection.connect();
        try {
            ResultSet resultSet = dbConnection.selectFrom("student");
            DBConnection.writeResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        dbConnection.close();
    }
}
