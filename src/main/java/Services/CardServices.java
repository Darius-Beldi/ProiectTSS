package Services;

import Connection.MenuStatements;
import Models.AdressBook;
import Models.Card;
import Connection.CardStatements;
import Models.Transaction;
import Models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static Connection.CardStatements.*;

public class CardServices extends MenuStatements {

    private CardStatements cardStatements = new CardStatements();
    private static AuditService auditService = new AuditService();

    public void insertIntoDatabase(Card card) throws SQLException {

        cardStatements.insertCardStatement.setInt(1, card.getIdCard());
        cardStatements.insertCardStatement.setInt(2, card.getIdUser());
        cardStatements.insertCardStatement.setString(3, card.getName());
        cardStatements.insertCardStatement.setString(4, card.getIBAN());
        cardStatements.insertCardStatement.setString(5, card.getNumber());
        cardStatements.insertCardStatement.setInt(6, card.getMonth());
        cardStatements.insertCardStatement.setInt(7, card.getYear());
        cardStatements.insertCardStatement.setInt(8, card.getCVV());
        cardStatements.insertCardStatement.setInt(9, card.getBalance());
        cardStatements.insertCardStatement.setString(10, card.getCardName());
        cardStatements.insertCardStatement.executeUpdate();

        auditService.logAction("Card Inserted Successfully with id: "  + card.getIdCard());
    }

    public Boolean isIBANUsed(String IBAN) throws SQLException {
        checkIBANStatement.setString(1, IBAN);
        ResultSet rs = checkIBANStatement.executeQuery();
        if(rs.next())
            return true;
        else
            return false;
    }

    public Boolean isNumberUsed(String number) throws SQLException {
        checkCardNumbersStatement.setString(1, number);
        ResultSet rs = checkCardNumbersStatement.executeQuery();
        if(rs.next())
            return true;
        else
            return false;
    }

    public void updateCardList(User currentUser) throws SQLException {
       /*
       * public static String getCards = "SELECT " +
            "idCard, idUser, Name, CardName, IBAN, cardNumber, Month, Year, CVV, Balance" +
            " FROM cards WHERE iduser = ?";
       * */

        getCardsStatement.setInt(1, currentUser.getIdUser());
        ResultSet rsCards = getCardsStatement.executeQuery();
        List<Card> cardstemp = new ArrayList<>();
        while(rsCards.next()){

            Integer id = rsCards.getInt(1);
            Integer idUser = rsCards.getInt(2);
            String Name = rsCards.getString(3);
            String cardName = rsCards.getString(4);
            String IBAN = rsCards.getString(5);
            String Number = rsCards.getString(6);
            Integer Month = rsCards.getInt(7);
            Integer Year = rsCards.getInt(8);
            Integer CVV = rsCards.getInt(9);
            Integer Balance = rsCards.getInt(10);

            Card c = new Card(id, idUser, Name, cardName, IBAN, Number, Month, Year, CVV, Balance);

            cardstemp.add(c);
        }

        auditService.logAction("Card Updated Successfully with id: " + currentUser.getIdUser());

        currentUser.UpdateCards(cardstemp);
    }

    public void transferMoney(User currentUser, Integer cardID, AdressBook a, Integer amount) throws SQLException, ClassNotFoundException {
        System.out.println("Transfering " + amount + " to " + a.getName());

        Integer idCardOutgoing = currentUser.getCards().get(cardID-1).getIdCard();
        Integer balanceCardOutgoingAfter = currentUser.getCards().get(cardID-1).getBalance() - amount;
        updateCardBalanceStatement.setInt(1,(balanceCardOutgoingAfter));
        updateCardBalanceStatement.setInt(2, idCardOutgoing);
        updateCardBalanceStatement.execute();

        //Add the money
        //UPDATE cards SET balance = ? WHERE iban = ?

        getIdWithIbanStatement.setString(1, a.getIBAN());
        ResultSet rs = getIdWithIbanStatement.executeQuery();
        Integer idCardIncoming = 0;
        if(rs.next()){
            idCardIncoming = rs.getInt(1);
        }

        getBalancewithIbanStatement.setString(1, a.getIBAN());
        ResultSet rs2 = getBalancewithIbanStatement.executeQuery();
        Integer balance = 0;
        if(rs2.next()){
            balance = rs2.getInt(1);
        }

        updateCardBalancewithIbanStatement.setInt(1, balance + amount);
        updateCardBalancewithIbanStatement.setString(2, a.getIBAN());
        updateCardBalancewithIbanStatement.execute();

        auditService.logAction("Transfer money succesfully from card: " + idCardIncoming + " to card: " + idCardOutgoing + "amount: " +  amount);
        new Transaction(idCardOutgoing, idCardIncoming, amount);

    }

    public void deleteCards(Integer idUser) throws SQLException {
        deleteCardsStatement.setInt(1, idUser);
        deleteCardsStatement.executeUpdate();
        auditService.logAction("Cards Deleted Successfully for the userId: " + idUser);
    }
}
