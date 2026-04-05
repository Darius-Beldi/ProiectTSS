package Services;

import Connection.MenuStatements;
import Models.Transaction;
import Models.User;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import Connection.UserStatements;

import static Connection.CardStatements.getUserFirstNameStatement;
import static Connection.CardStatements.getUserLastNameStatement;

public class UserService extends MenuStatements {

    private AuthenticationService authService;
    private static UserStatements userStatements;
    private static AuditService auditService;

    public UserService() {
        this.authService = new AuthenticationService();
        this.userStatements = new UserStatements();
        this.auditService = new AuditService();
    }

    public static void delete(User currentUser) throws SQLException {

        CardServices cardServices = new CardServices();

        AdressBooksService.deleteAdressBooks(currentUser.getIdUser());

        cardServices.deleteCards(currentUser.getIdUser());

        userStatements.deleteUserStatement.setInt(1, currentUser.getIdUser());
        userStatements.deleteUserStatement.executeUpdate();

        auditService.logAction("User  Deleted Successfully with id: "  + currentUser.getIdUser());

    }

    public User getUserDetails(int userId) throws SQLException, NoSuchAlgorithmException {
        getDetailsStatement.setInt(1, userId);
        ResultSet rs = getDetailsStatement.executeQuery();
        if(rs.next()) {
            String firstName = rs.getString(1);
            String lastName = rs.getString(2);
            java.util.Date birthDate = rs.getDate(3);
            String email = rs.getString(4);

            getPasswordbyIDStatement.setInt(1, userId);
            ResultSet pwdResult = getPasswordbyIDStatement.executeQuery();
            String password = "";
            if(pwdResult.next()) {
                password = pwdResult.getString(1);
            }

            return new User(userId, firstName, lastName, birthDate, email, password, true, null);
        }
        return null;
    }

    public boolean changePassword(User user, String oldPassword, String newPassword)
            throws SQLException, NoSuchAlgorithmException {
        if(user.getPassword().equals(AuthenticationService.Crypt(oldPassword))) {
            String hashedNewPassword = AuthenticationService.Crypt(newPassword);
            updatePasswordStatement.setString(1, hashedNewPassword);
            updatePasswordStatement.setInt(2, user.getIdUser());
            updatePasswordStatement.execute();
            user.setPassword(hashedNewPassword);

            auditService.logAction("Changed the password for  user " + user.getIdUser());

            return true;
        }
        return false;
    }

    public void addToDatabase(User user) throws SQLException, NoSuchAlgorithmException {
        userStatements.insertUserStatement.setInt(1, user.getIdUser());
        userStatements.insertUserStatement.setString(2, user.getFirstName());
        userStatements.insertUserStatement.setString(3, user.getLastName());

        java.sql.Date sqlDate = new java.sql.Date(user.getBirthDate().getTime());
        userStatements.insertUserStatement.setDate(4, sqlDate);

        userStatements.insertUserStatement.setString(5, user.getEmail());
        userStatements.insertUserStatement.setString(6, user.getPassword());
        userStatements.insertUserStatement.execute();

        auditService.logAction("Added user " + user.getIdUser());
    }

    public String userFirstName(int userId) throws SQLException, NoSuchAlgorithmException {
        getUserFirstNameStatement.setInt(1, userId);
        ResultSet rs = getUserFirstNameStatement.executeQuery();
        rs.next();
        return rs.getString(1);
    }

    public String userLastName(int userId) throws SQLException, NoSuchAlgorithmException {
        getUserLastNameStatement.setInt(1, userId);
        ResultSet rs2 = getUserLastNameStatement.executeQuery();
        rs2.next();
        return rs2.getString(1);
    }
}