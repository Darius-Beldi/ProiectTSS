package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionString<Connection> {

    protected static String url = "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7827411";
    protected static String user = "sql7827411";
    protected static String password = "BI9N3uqXNu";
    protected static java.sql.Connection c;

    static {
        try {
            c = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
