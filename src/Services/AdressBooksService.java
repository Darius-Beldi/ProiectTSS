package Services;

import Connection.MenuStatements;
import Models.AdressBook;
import Models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static Connection.AdressBookStatements.deleteAdressBooksStatement;
import static Connection.AdressBookStatements.insertAdressBookStatement;

public class AdressBooksService extends MenuStatements {

    static AuditService auditService = new AuditService();


    public void insertIntoDatabase(AdressBook a) throws SQLException {
        try {
            insertAdressBookStatement.setInt(1, a.getIdAdressBook());
            insertAdressBookStatement.setInt(2, a.getIdUser());
            insertAdressBookStatement.setString(3, a.getName());
            insertAdressBookStatement.setString(4, a.getIBAN());
            insertAdressBookStatement.executeUpdate();
            auditService.logAction("Inserted AdressBook with id : " + a.getIdAdressBook());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Set<AdressBook> readAdressBooks(User currentUser) throws SQLException {
        getAdressBooksStatement.setInt(1, currentUser.getIdUser());
        ResultSet rsAdressBooks = getAdressBooksStatement.executeQuery();
        Set <AdressBook> adressBookstemp = new HashSet<>();
        while(rsAdressBooks.next()){

            Integer id = rsAdressBooks.getInt(1);
            Integer idUser = rsAdressBooks.getInt(2);
            String Name = rsAdressBooks.getString(3);
            String IBAN = rsAdressBooks.getString(4);

            AdressBook a = new AdressBook(id, idUser, Name, IBAN);

            adressBookstemp.add(a);
        }

        auditService.logAction("Read all AdressBooks from the user: " +  currentUser.getIdUser());
        return adressBookstemp;
    }

    public static void deleteAdressBooks(Integer idUser) throws SQLException {
        deleteAdressBooksStatement.setInt(1, idUser);
        deleteAdressBooksStatement.executeUpdate();
        auditService.logAction("Deleted all AdressBooks from the user: " + idUser);
    }
}
