import org.junit.jupiter.api.Test;

import java.sql.*;


public class TestJDBCConnection {


    private static final String USER_NAME = "root";
    private static final String PASSWORD = "admin";
    private static final String URL = "jdbc:mysql://localhost:3307/TESTDB";

    private final String QUERY = "SELECT * FROM COURSES WHERE name = ?";
    private final String INSERT = "INSERT INTO COURSES (name, cost, data) VALUES ('RUST', 100.00, '10.03.2024');";



    @Test
    public void testConnection() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(QUERY);
            statement.setString(1, "JAVA");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("Id:" + resultSet.getInt("id"));
                System.out.println("Name:" + resultSet.getString("name"));
                System.out.println("Cost:" + resultSet.getFloat("cost"));
                System.out.println("Data:" + resultSet.getString("data"));
            }
        }
    }
}
