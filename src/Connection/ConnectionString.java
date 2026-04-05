package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionString<Connection> {

    protected static String url = "jdbc:mysql://localhost:3306/bazadedate";
    protected static String user = "root";
    protected static String password = "parola";
    protected static java.sql.Connection c;

    static {
        try {
            c = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
