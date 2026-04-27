package Services;

import Connection.MenuStatements;
import Models.Card;
import Models.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static Connection.TransactionStatements.insertTransactionStatement;

public class TransactionService extends MenuStatements {

    private static AuditService auditService = new AuditService();


    public void insertIntoDatabase(Transaction transaction) throws SQLException, ClassNotFoundException {
        insertTransactionStatement.setInt(1, transaction.getIdTransaction());
        insertTransactionStatement.setInt(2, transaction.getIdCardOutgoing());
        insertTransactionStatement.setInt(3, transaction.getIdCardIncoming());
        insertTransactionStatement.setDouble(4, transaction.getAmount());
        insertTransactionStatement.setDate(5, new java.sql.Date(transaction.getDate().getTime()));
        insertTransactionStatement.execute();

        auditService.logAction("Transaction inserted with id: " +  transaction.getIdTransaction());
    }


    public void printTransactionsIn(Card c) throws SQLException {
        getTransactionsInStatement.setInt(1, c.getIdCard());
        ResultSet rs = getTransactionsInStatement.executeQuery();
        while(rs.next()){
            Integer idTransaction = rs.getInt(1);
            Integer idCardOutgoing = rs.getInt(2);
            Integer idCardIncoming = rs.getInt(3);
            Integer amount = rs.getInt(4);
            Date date = rs.getDate(5);
            System.out.println("Transaction ID: " + idTransaction);
            System.out.println("From card : " + idCardIncoming);
            System.out.println("Amount: +" + amount);
            System.out.println("Date: " + date);
            System.out.println("\n");
        }
    }

    public void  printTransactionsOut(Card c) throws SQLException {
        getTransactionsOutStatement.setInt(1, c.getIdCard());
        ResultSet rs2 = getTransactionsOutStatement.executeQuery();
        while(rs2.next()){
            Integer idTransaction = rs2.getInt(1);
            Integer idCardOutgoing = rs2.getInt(2);
            Integer idCardIncoming = rs2.getInt(3);
            Integer amount = rs2.getInt(4);
            Date date = rs2.getDate(5);
            System.out.println("Transaction ID: " + idTransaction);
            System.out.println("From card : " + idCardIncoming);
            System.out.println("Amount: -" + amount);
            System.out.println("Date: " + date);
            System.out.println("\n");
        }
    }


}
