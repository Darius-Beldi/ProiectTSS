package Models;

import Connection.TransactionStatements;
import Services.AuditService;
import Services.TransactionService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Transaction extends TransactionStatements {
    private static Integer generatedIdTransaction;
    private Integer idTransaction;
    private Integer idCardOutgoing;
    private Integer idCardIncoming;
    private Date date;
    private Integer amount;
    private TransactionService transactionService = new TransactionService();

    static {
        try {
            PreparedStatement selectStatement = c.prepareStatement("SELECT idTransaction FROM Transactions ORDER BY idTransaction DESC LIMIT 1");
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next()) {
                generatedIdTransaction = rs.getInt(1);
            } else {
                generatedIdTransaction = 0; // No rows in the table
            }

            rs.close();
            selectStatement.close();
        } catch (SQLException e) {
            generatedIdTransaction = 0;
            e.printStackTrace(); // Consider logging the exception
        }
    }
    {
        AuditService auditService = new AuditService();
        auditService.logAction("Loaded Transaction class");
    }
    public Transaction(int _idCardOutgoing, int _idCardIncoming, Integer _amount) throws SQLException, ClassNotFoundException {

        generatedIdTransaction++;
        idTransaction = generatedIdTransaction;
        idCardOutgoing = _idCardOutgoing;
        idCardIncoming = _idCardIncoming;
        amount = _amount;
        date = new Date();

        transactionService.insertIntoDatabase(this);

    }

    public Transaction(int _idTransaction, int _idCardOutgoing, int _idCardIncoming, Integer _amount, Date _date) {
        idTransaction = _idTransaction;
        idCardOutgoing = _idCardOutgoing;
        idCardIncoming = _idCardIncoming;
        amount = _amount;
        date = _date;

    }

    public static Integer getGeneratedIdTransaction() {
        return generatedIdTransaction;
    }

    public Integer getIdTransaction() {
        return idTransaction;
    }

    public Integer getIdCardOutgoing() {
        return idCardOutgoing;
    }

    public Integer getIdCardIncoming() {
        return idCardIncoming;
    }

    public Date getDate() {
        return date;
    }

    public Integer getAmount() {
        return amount;
    }



}
