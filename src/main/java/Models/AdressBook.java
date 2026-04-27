package Models;

import Connection.AdressBookStatements;
import Services.AdressBooksService;
import Services.AuditService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdressBook extends AdressBookStatements {

    private static Integer generatedIdAdressBook;
    private Integer idAdressBook;
    private Integer idUser;
    private String name;
    private String IBAN;
    private AdressBooksService adressBookService = new AdressBooksService();

    static {
        try {
            PreparedStatement selectStatement = c.prepareStatement("SELECT idAdressBook FROM AdressBooks ORDER BY idAdressBook DESC LIMIT 1");
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next()) {
                generatedIdAdressBook = rs.getInt(1);
            } else {
                generatedIdAdressBook = 0; // No rows in the table
            }

            rs.close();
            selectStatement.close();
        } catch (SQLException e) {
            generatedIdAdressBook = 0;
            e.printStackTrace(); // Consider logging the exception
        }
    }

    {
        AuditService auditService = new AuditService();
        auditService.logAction("Loaded AdressBook class");
    }

    public AdressBook(User _user, String _name, String _IBAN) throws SQLException {
        generatedIdAdressBook++;
        idAdressBook = generatedIdAdressBook;

        idUser = _user.getIdUser();
        name = _name;
        IBAN = _IBAN;

        adressBookService.insertIntoDatabase(this);
    }

    public AdressBook(Integer id, Integer idUser, String name, String iban) {
        this.idAdressBook = id;
        this.idUser = idUser;
        this.name = name;
        this.IBAN = iban;
    }


    public String getName() {
        return name;
    }
    public String getIBAN() {
        return IBAN;
    }
    public  Integer getIdAdressBook() {
        return idAdressBook;
    }
    public Integer getIdUser() {
        return idUser;
    }
}
