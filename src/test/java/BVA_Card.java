import Models.Card;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Order(2)
public class BoundaryValueAnalysisCard {

    private static final int    VALID_ID     = 1;
    private static final int    VALID_UID    = 1;
    private static final String VALID_TYPE   = "Debit";
    private static final String VALID_NAME   = "Debit Card";
    private static final String VALID_IBAN   = "RO49AAAA1B31007593840000";
    private static final String VALID_NUMBER = "1234567890123456";
    private static final int    VALID_MONTH  = 6;
    private static final int    VALID_YEAR   = 25;
    private static final int    VALID_CVV    = 500;
    private static final double VALID_LIMIT  = 200.0;

    

    

    

    @Test
    public void testCard_Month_BelowMin() {

        assertThrows(IllegalArgumentException.class, () ->
                new Card(VALID_ID, VALID_UID, VALID_TYPE, VALID_NAME,
                        VALID_IBAN, VALID_NUMBER,
                        0, VALID_YEAR, VALID_CVV, VALID_LIMIT));
    }

    @Test
    public void testCard_Month_AtMin() {

        Card card = new Card(VALID_ID, VALID_UID, VALID_TYPE, VALID_NAME,
                VALID_IBAN, VALID_NUMBER,
                1, VALID_YEAR, VALID_CVV, VALID_LIMIT);
        assertEquals(1, card.getMonth());
    }

    @Test
    public void testCard_Month_AboveMin() {

        Card card = new Card(VALID_ID, VALID_UID, VALID_TYPE, VALID_NAME,
                VALID_IBAN, VALID_NUMBER,
                2, VALID_YEAR, VALID_CVV, VALID_LIMIT);
        assertEquals(2, card.getMonth());
    }

    @Test
    public void testCard_Month_BelowMax() {

        Card card = new Card(VALID_ID, VALID_UID, VALID_TYPE, VALID_NAME,
                VALID_IBAN, VALID_NUMBER,
                11, VALID_YEAR, VALID_CVV, VALID_LIMIT);
        assertEquals(11, card.getMonth());
    }

    @Test
    public void testCard_Month_AtMax() {

        Card card = new Card(VALID_ID, VALID_UID, VALID_TYPE, VALID_NAME,
                VALID_IBAN, VALID_NUMBER,
                12, VALID_YEAR, VALID_CVV, VALID_LIMIT);
        assertEquals(12, card.getMonth());
    }

    @Test
    public void testCard_Month_AboveMax() {

        assertThrows(IllegalArgumentException.class, () ->
                new Card(VALID_ID, VALID_UID, VALID_TYPE, VALID_NAME,
                        VALID_IBAN, VALID_NUMBER,
                        13, VALID_YEAR, VALID_CVV, VALID_LIMIT));
    }

    

    

    

    @Test
    public void testCard_Cvv_BelowMin() {

        assertThrows(IllegalArgumentException.class, () ->
                new Card(VALID_ID, VALID_UID, VALID_TYPE, VALID_NAME,
                        VALID_IBAN, VALID_NUMBER,
                        VALID_MONTH, VALID_YEAR, 99, VALID_LIMIT));
    }

    @Test
    public void testCard_Cvv_AtMin() {

        Card card = new Card(VALID_ID, VALID_UID, VALID_TYPE, VALID_NAME,
                VALID_IBAN, VALID_NUMBER,
                VALID_MONTH, VALID_YEAR, 100, VALID_LIMIT);
        assertEquals(100, card.getCvv());
    }

    @Test
    public void testCard_Cvv_AboveMin() {

        Card card = new Card(VALID_ID, VALID_UID, VALID_TYPE, VALID_NAME,
                VALID_IBAN, VALID_NUMBER,
                VALID_MONTH, VALID_YEAR, 101, VALID_LIMIT);
        assertEquals(101, card.getCvv());
    }

    @Test
    public void testCard_Cvv_BelowMax() {

        Card card = new Card(VALID_ID, VALID_UID, VALID_TYPE, VALID_NAME,
                VALID_IBAN, VALID_NUMBER,
                VALID_MONTH, VALID_YEAR, 998, VALID_LIMIT);
        assertEquals(998, card.getCvv());
    }

    @Test
    public void testCard_Cvv_AtMax() {

        Card card = new Card(VALID_ID, VALID_UID, VALID_TYPE, VALID_NAME,
                VALID_IBAN, VALID_NUMBER,
                VALID_MONTH, VALID_YEAR, 999, VALID_LIMIT);
        assertEquals(999, card.getCvv());
    }

    @Test
    public void testCard_Cvv_AboveMax() {

        assertThrows(IllegalArgumentException.class, () ->
                new Card(VALID_ID, VALID_UID, VALID_TYPE, VALID_NAME,
                        VALID_IBAN, VALID_NUMBER,
                        VALID_MONTH, VALID_YEAR, 1000, VALID_LIMIT));
    }

    

    

    @Test
    public void testCard_Number_TooShort() {

        assertThrows(IllegalArgumentException.class, () ->
                new Card(VALID_ID, VALID_UID, VALID_TYPE, VALID_NAME,
                        VALID_IBAN, "123456789012345",
                        VALID_MONTH, VALID_YEAR, VALID_CVV, VALID_LIMIT));
    }

    @Test
    public void testCard_Number_ExactLength() {

        Card card = new Card(VALID_ID, VALID_UID, VALID_TYPE, VALID_NAME,
                VALID_IBAN, "1234567890123456",
                VALID_MONTH, VALID_YEAR, VALID_CVV, VALID_LIMIT);
        assertEquals("1234567890123456", card.getNumber());
    }

    @Test
    public void testCard_Number_TooLong() {

        assertThrows(IllegalArgumentException.class, () ->
                new Card(VALID_ID, VALID_UID, VALID_TYPE, VALID_NAME,
                        VALID_IBAN, "12345678901234567",
                        VALID_MONTH, VALID_YEAR, VALID_CVV, VALID_LIMIT));
    }

    

    

    @Test
    public void testCard_Id_BelowMin() {

        assertThrows(IllegalArgumentException.class, () ->
                new Card(0, VALID_UID, VALID_TYPE, VALID_NAME,
                        VALID_IBAN, VALID_NUMBER,
                        VALID_MONTH, VALID_YEAR, VALID_CVV, VALID_LIMIT));
    }

    @Test
    public void testCard_Id_AtMin() {

        Card card = new Card(1, VALID_UID, VALID_TYPE, VALID_NAME,
                VALID_IBAN, VALID_NUMBER,
                VALID_MONTH, VALID_YEAR, VALID_CVV, VALID_LIMIT);
        assertEquals(1, card.getIdCard());
    }

    @Test
    public void testCard_Id_AboveMin() {

        Card card = new Card(2, VALID_UID, VALID_TYPE, VALID_NAME,
                VALID_IBAN, VALID_NUMBER,
                VALID_MONTH, VALID_YEAR, VALID_CVV, VALID_LIMIT);
        assertEquals(2, card.getIdCard());
    }

    

    

    @Test
    public void testCard_UserId_BelowMin() {

        assertThrows(IllegalArgumentException.class, () ->
                new Card(VALID_ID, 0, VALID_TYPE, VALID_NAME,
                        VALID_IBAN, VALID_NUMBER,
                        VALID_MONTH, VALID_YEAR, VALID_CVV, VALID_LIMIT));
    }

    @Test
    public void testCard_UserId_AtMin() {

        Card card = new Card(VALID_ID, 1, VALID_TYPE, VALID_NAME,
                VALID_IBAN, VALID_NUMBER,
                VALID_MONTH, VALID_YEAR, VALID_CVV, VALID_LIMIT);
        assertEquals(1, card.getUserId());
    }

    @Test
    public void testCard_UserId_AboveMin() {

        Card card = new Card(VALID_ID, 2, VALID_TYPE, VALID_NAME,
                VALID_IBAN, VALID_NUMBER,
                VALID_MONTH, VALID_YEAR, VALID_CVV, VALID_LIMIT);
        assertEquals(2, card.getUserId());
    }

    

    

    @Test
    public void testCard_Year_BelowMin() {

        assertThrows(IllegalArgumentException.class, () ->
                new Card(VALID_ID, VALID_UID, VALID_TYPE, VALID_NAME,
                        VALID_IBAN, VALID_NUMBER,
                        VALID_MONTH, -1, VALID_CVV, VALID_LIMIT));
    }

    @Test
    public void testCard_Year_AtMin() {

        Card card = new Card(VALID_ID, VALID_UID, VALID_TYPE, VALID_NAME,
                VALID_IBAN, VALID_NUMBER,
                VALID_MONTH, 0, VALID_CVV, VALID_LIMIT);
        assertEquals(0, card.getYear());
    }

    @Test
    public void testCard_Year_AboveMin() {

        Card card = new Card(VALID_ID, VALID_UID, VALID_TYPE, VALID_NAME,
                VALID_IBAN, VALID_NUMBER,
                VALID_MONTH, 1, VALID_CVV, VALID_LIMIT);
        assertEquals(1, card.getYear());
    }

    

    

    @Test
    public void testCard_Limit_BelowMin() {

        assertThrows(IllegalArgumentException.class, () ->
                new Card(VALID_ID, VALID_UID, VALID_TYPE, VALID_NAME,
                        VALID_IBAN, VALID_NUMBER,
                        VALID_MONTH, VALID_YEAR, VALID_CVV, -0.01));
    }

    @Test
    public void testCard_Limit_AtMin() {

        Card card = new Card(VALID_ID, VALID_UID, VALID_TYPE, VALID_NAME,
                VALID_IBAN, VALID_NUMBER,
                VALID_MONTH, VALID_YEAR, VALID_CVV, 0.0);
        assertEquals(0.0, card.getLimit(), 0.001);
    }

    @Test
    public void testCard_Limit_AboveMin() {

        Card card = new Card(VALID_ID, VALID_UID, VALID_TYPE, VALID_NAME,
                VALID_IBAN, VALID_NUMBER,
                VALID_MONTH, VALID_YEAR, VALID_CVV, 0.01);
        assertEquals(0.01, card.getLimit(), 0.001);
    }
}