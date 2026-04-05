package Connection;

import com.sun.jdi.connect.spi.Connection;

import java.sql.PreparedStatement;

public class TransactionStatements extends ConnectionString {


    //generatedIdTransaction++;
    //        idTransaction = generatedIdTransaction;
    //        idCardOutgoing = _idCardOutgoing;
    //        idCardIncoming = _idCardIncoming;
    //        amount = _amount;
    //        date = new Date();

    protected static String insertTransaction = "insert into transactions " +
            "(idtransaction, idcardoutgoing, idcardincoming, amount, date)" +
            " values (?, ?, ?, ?, ?)";

    protected static String selectTransaction = "select * from transactions where idcard = ?";

    public static PreparedStatement insertTransactionStatement;
    protected static PreparedStatement selectTransactionStatement;

    static {
        try {
            insertTransactionStatement = c.prepareStatement(insertTransaction);
            selectTransactionStatement = c.prepareStatement(selectTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
