package Connection;

import java.sql.PreparedStatement;

public class AdressBookStatements extends ConnectionString{

    private static String insertAdressBook = "insert into adressbooks (idadressbook, iduser, name, iban) values (?, ?, ?, ?)";
    public static PreparedStatement insertAdressBookStatement;
    private static String deleteAddressBooks = "DELETE FROM adressbooks WHERE iduser = ?";
    public static PreparedStatement deleteAdressBooksStatement;

    static{
        try{
            insertAdressBookStatement = c.prepareStatement(insertAdressBook);
            deleteAdressBooksStatement = c.prepareStatement(deleteAddressBooks);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
