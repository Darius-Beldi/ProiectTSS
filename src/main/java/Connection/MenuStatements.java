package Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MenuStatements extends ConnectionString {

    public static String checkForExistingEmail = "SELECT email FROM users WHERE email = ?";
    public static String getDetails = "SELECT firstname, lastname, birthdate, email FROM users WHERE iduser = ?";
    public static String getPassword = "SELECT password FROM users WHERE email = ?";
    public static String getId = "SELECT iduser FROM users WHERE email = ?";
    public static String getUser = "SELECT * FROM users WHERE email = ?";
    public static String getPasswordbyID = "SELECT password FROM users WHERE iduser = ?";
    public static String updatePassword = "UPDATE users SET password = ? WHERE iduser = ?";

    public static String getCards = "SELECT " +
            "idCard, idUser, Name, CardName, IBAN, cardNumber, Month, Year, CVV, Balance" +
            " FROM cards WHERE iduser = ?";
    public static String getAdressBooks = "SELECT * FROM adressbooks WHERE iduser = ?";
    public static String updateCardBalance = "UPDATE cards SET balance = ? WHERE idcard = ?";
    public static String updateCardBalancewithIban = "UPDATE cards SET balance = ? WHERE iban = ?";
    public static String getBalancewithIban = "SELECT balance FROM cards WHERE iban = ?";
    public static String getIdWithIban = "SELECT idcard FROM cards WHERE iban = ?";


    public static String getTransactionsOut = "SELECT * FROM transactions WHERE idcardoutgoing = ?";
    public static String getTransactionsIn = "SELECT * FROM transactions WHERE idcardincoming = ?";

    public static PreparedStatement checkForExistingEmailStatement;
    public static PreparedStatement getPasswordStatement;
    public static PreparedStatement getIdStatement;
    public static PreparedStatement getUserStatement;

    public static PreparedStatement getDetailsStatement;
    public static PreparedStatement getPasswordbyIDStatement;
    public static PreparedStatement updatePasswordStatement;
    public static PreparedStatement getCardsStatement;
    public static PreparedStatement getAdressBooksStatement;
    public static PreparedStatement updateCardBalanceStatement;
    public static PreparedStatement updateCardBalancewithIbanStatement;
    public static PreparedStatement getBalancewithIbanStatement;
    public static PreparedStatement getIdWithIbanStatement;

    public static PreparedStatement getTransactionsOutStatement;
    public static PreparedStatement getTransactionsInStatement;


    static {
        try{
            checkForExistingEmailStatement = c.prepareStatement(checkForExistingEmail);
            getPasswordStatement = c.prepareStatement(getPassword);
            getIdStatement = c.prepareStatement(getId);
            getUserStatement = c.prepareStatement(getUser);
            getDetailsStatement = c.prepareStatement(getDetails);
            getPasswordbyIDStatement = c.prepareStatement(getPasswordbyID);
            updatePasswordStatement = c.prepareStatement(updatePassword);
            getCardsStatement = c.prepareStatement(getCards);
            getAdressBooksStatement = c.prepareStatement(getAdressBooks);
            updateCardBalanceStatement = c.prepareStatement(updateCardBalance);
            updateCardBalancewithIbanStatement = c.prepareStatement(updateCardBalancewithIban);
            getBalancewithIbanStatement = c.prepareStatement(getBalancewithIban);
            getIdWithIbanStatement = c.prepareStatement(getIdWithIban);

            getTransactionsOutStatement = c.prepareStatement(getTransactionsOut);
            getTransactionsInStatement = c.prepareStatement(getTransactionsIn);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
