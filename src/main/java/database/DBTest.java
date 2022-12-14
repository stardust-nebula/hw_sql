package database;

import org.testng.annotations.Test;

import java.sql.ResultSet;

public class DBTest {
    private DBConnection dbConnection = new DBConnection();

    @Test
    private void dataBaseTest() {
        dbConnection.connect();
        ResultSet resultSet = dbConnection.selectStudentInfoWithCityName();
        dbConnection.writeResultSet(resultSet);
        dbConnection.close();
    }

    @Test(testName = "Add new student record")
    private void addStudentTest() {
        String studentName = "Pavel";
        String cityName = "Omsk";
        dbConnection.connect();
        dbConnection.addNewStudent(studentName, cityName);
        ResultSet resultSet = dbConnection.selectStudentInfoWithCityName();
        dbConnection.writeResultSet(resultSet);
        dbConnection.close();
    }
}
