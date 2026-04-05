package Connection;


import java.sql.PreparedStatement;

public class CardStatements extends ConnectionString{

    public static String insertCard = "insert into cards " +
            "(idcard, iduser, name, iban, cardnumber, month, year, cvv, balance, cardname)" +
            " values (?, ?, ?, ?, ?, ?, ?,?, ?, ?)";

    public static String selectCard = "select * from cards where iduser = ?";

    public static String checkIBANs = "SELECT 1 FROM cards WHERE IBAN = ?";
    public static String checkCardNumbers = "SELECT 1 FROM cards WHERE cardnumber = ?";
    public static String getUserFirstName = "SELECT Firstname FROM users WHERE iduser = ?";
    public static String getUserLastName = "SELECT lastname FROM users WHERE iduser = ?";
    public static String deleteCardsSQL = "DELETE FROM cards WHERE iduser = ?";

    public static PreparedStatement insertCardStatement;
    public static PreparedStatement selectCardStatement;
    public static PreparedStatement checkIBANStatement;
    public static PreparedStatement checkCardNumbersStatement;
    public static PreparedStatement getUserFirstNameStatement;
    public static PreparedStatement getUserLastNameStatement;
    public static PreparedStatement deleteCardsStatement;


    static {
        try {
            insertCardStatement = c.prepareStatement(insertCard);
            selectCardStatement = c.prepareStatement(selectCard);
            checkIBANStatement = c.prepareStatement(checkIBANs);
            checkCardNumbersStatement = c.prepareStatement(checkCardNumbers);
            getUserFirstNameStatement = c.prepareStatement(getUserFirstName);
            getUserLastNameStatement = c.prepareStatement(getUserLastName);
            deleteCardsStatement = c.prepareStatement(deleteCardsSQL);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
