package Connection;

import java.sql.PreparedStatement;

public class UserStatements extends ConnectionString{
    protected static String insertUser = "insert into users " +
            "(iduser, firstname, lastname, birthdate, email, password)" +
            " values (?, ?, ?, ?, ?, ?)";
    public static PreparedStatement insertUserStatement;
    protected static PreparedStatement selectUserStatement;
    public static String deleteUserSQL = "DELETE FROM users WHERE iduser = ?";
    public static PreparedStatement deleteUserStatement;

    {
        try {
            insertUserStatement = c.prepareStatement(insertUser);
            deleteUserStatement = c.prepareStatement(deleteUserSQL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
