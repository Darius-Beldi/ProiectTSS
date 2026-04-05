package Services;

import Connection.MenuStatements;
import Models.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class AuthenticationService extends MenuStatements {


    public static String Crypt(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(input.getBytes());
        byte[] digest = md.digest();

        // Convert byte array to hexadecimal string
        StringBuilder hexString = new StringBuilder();
        for (byte b : digest) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
    private static AuditService  auditService = new AuditService();

    public User login(String email, String password) throws SQLException, NoSuchAlgorithmException {
        checkForExistingEmailStatement.setString(1, email);
        ResultSet rs = checkForExistingEmailStatement.executeQuery();
        String foundEmail = "";
        if(rs.next()){
            foundEmail = rs.getString(1);
        }

        if(foundEmail.equals(email)){
            getPasswordStatement.setString(1, email);
            ResultSet passwordResult = getPasswordStatement.executeQuery();
            if(passwordResult.next()){
                if(passwordResult.getString(1).equals(Crypt(password))){
                    getUserStatement.setString(1, foundEmail);
                    ResultSet rsUser = getUserStatement.executeQuery();
                    if(rsUser.next()){
                        int id = rsUser.getInt(1);
                        String firstName = rsUser.getString(2);
                        String lastName = rsUser.getString(3);
                        Date birthDate = rsUser.getDate(4);
                        String userEmail = rsUser.getString(5);
                        String userPassword = rsUser.getString(6);

                        auditService.logAction("Logged in successfully for user: " +  id);
                        return new User(id, firstName, lastName, birthDate, userEmail, userPassword, true, new ArrayList<>());
                    }




                }

            }
        }
        return null;
    }

    public Boolean isEmailAvailable(String email) throws SQLException {
        checkForExistingEmailStatement.setString(1, email);
        ResultSet rs = checkForExistingEmailStatement.executeQuery();
        return rs.next();
    }

    public User register(String firstName, String lastName, Date birthDate, String email, String password)
            throws SQLException, NoSuchAlgorithmException {


        return new User(0, firstName, lastName, birthDate, email, password, false, new ArrayList<>());
    }
}
