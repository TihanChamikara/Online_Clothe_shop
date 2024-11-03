package finalProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginModel {
    private final String url = "jdbc:mysql://localhost:3306/fashiondb?user=root&password=&useUnicode=true&characterEncoding=UTF-8";
    private final String username = "root";
    private final String password = "";

    public boolean authenticate(String email, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(url, username, this.password);
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM access WHERE email = ? AND password = ?")) {
                
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();

                boolean isAuthenticated = resultSet.next();
                resultSet.close();
                return isAuthenticated;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailPattern);
    }
}
