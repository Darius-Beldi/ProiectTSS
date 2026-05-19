import Models.Card;
import Services.AuthenticationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@Order(2)
public class EquivalencePartitioning {


    @Test
    public void testCrypt_ValidString() throws NoSuchAlgorithmException {
        String input = "parola123";

        String expected = "095b2626c9b6bad0eb89019ea6091bd9";
        String result = AuthenticationService.Crypt(input);
        assertEquals(expected, result, "Hash-ul generat nu corespunde clasei valide de date.");
    }

    @Test
    public void testCrypt_NullString() {
        assertThrows(NullPointerException.class, () -> {
            AuthenticationService.Crypt(null);
        }, "Ar trebui sa se arunce NullPointerException pentru input null.");

    }


    @Test
    public void testCard_Constructor_Valid() {
        Card card = new Card(1, 1, "Debit", "Debit Card",
                "RO49AAAA1B31007593840000", "1234567890123456",
                12, 25, 123, 200);
        assertNotNull(card);
        assertEquals(1,       card.getIdCard());
        assertEquals(1,       card.getUserId());
        assertEquals("Debit", card.getCardName());
        assertEquals("Debit Card", card.getCardName());
        assertEquals("RO49AAAA1B31007593840000", card.getIBAN());
        assertEquals("1234567890123456", card.getNumber());
        assertEquals(12,    card.getMonth());
        assertEquals(25,    card.getYear());
        assertEquals(123,   card.getCVV());
        assertEquals(200, card.getBalance(), 0.001);
    }
 
 
    @Test
    public void testCard_Constructor_InvalidId() {
        assertThrows(IllegalArgumentException.class, () ->
                new Card(0, 1, "Debit", "Debit Card",
                        "RO49AAAA1B31007593840000", "1234567890123456",
                        12, 25, 123, 200),
                "id <= 0 trebuie sa arunce IllegalArgumentException");
    }
 
 
    @Test
    public void testCard_Constructor_InvalidUserId() {
        assertThrows(IllegalArgumentException.class, () ->
                new Card(1, -1, "Debit", "Debit Card",
                        "RO49AAAA1B31007593840000", "1234567890123456",
                        12, 25, 123, 200),
                "userId <= 0 trebuie sa arunce IllegalArgumentException");
    }
 
 
    @Test
    public void testCard_Constructor_NullType() {
        assertThrows(IllegalArgumentException.class, () ->
                new Card(1, 1, null, "Debit Card",
                        "RO49AAAA1B31007593840000", "1234567890123456",
                        12, 25, 123, 200),
                "type null trebuie sa arunce IllegalArgumentException");
    }
 
    @Test
    public void testCard_Constructor_EmptyType() {
        assertThrows(IllegalArgumentException.class, () ->
                new Card(1, 1, "", "Debit Card",
                        "RO49AAAA1B31007593840000", "1234567890123456",
                        12, 25, 123, 200),
                "type gol trebuie sa arunce IllegalArgumentException");
    }
 
 
    @Test
    public void testCard_Constructor_NullCardName() {
        assertThrows(IllegalArgumentException.class, () ->
                new Card(1, 1, "Debit", null,
                        "RO49AAAA1B31007593840000", "1234567890123456",
                        12, 25, 123, 200),
                "cardName null trebuie sa arunce IllegalArgumentException");
    }
 
    @Test
    public void testCard_Constructor_EmptyCardName() {
        assertThrows(IllegalArgumentException.class, () ->
                new Card(1, 1, "Debit", "",
                        "RO49AAAA1B31007593840000", "1234567890123456",
                        12, 25, 123, 200),
                "cardName gol trebuie sa arunce IllegalArgumentException");
    }
 
 
    @Test
    public void testCard_Constructor_NullIban() {
        assertThrows(IllegalArgumentException.class, () ->
                new Card(1, 1, "Debit", "Debit Card",
                        null, "1234567890123456",
                        12, 25, 123, 200),
                "iban null trebuie sa arunce IllegalArgumentException");
    }
 
    @Test
    public void testCard_Constructor_EmptyIban() {
        assertThrows(IllegalArgumentException.class, () ->
                new Card(1, 1, "Debit", "Debit Card",
                        "", "1234567890123456",
                        12, 25, 123, 200),
                "iban gol trebuie sa arunce IllegalArgumentException");
    }
 
 
    @Test
    public void testCard_Constructor_NullNumber() {
        assertThrows(IllegalArgumentException.class, () ->
                new Card(1, 1, "Debit", "Debit Card",
                        "RO49AAAA1B31007593840000", null,
                        12, 25, 123, 200),
                "number null trebuie sa arunce IllegalArgumentException");
    }
 
    @Test
    public void testCard_Constructor_InvalidNumberLength() {
        assertThrows(IllegalArgumentException.class, () ->
                new Card(1, 1, "Debit", "Debit Card",
                        "RO49AAAA1B31007593840000", "1234567890",
                        12, 25, 123, 200),
                "number cu lungime != 16 trebuie sa arunce IllegalArgumentException");
    }
 
 
    @Test
    public void testCard_Constructor_MonthTooLow() {
        assertThrows(IllegalArgumentException.class, () ->
                new Card(1, 1, "Debit", "Debit Card",
                        "RO49AAAA1B31007593840000", "1234567890123456",
                        0, 25, 123, 200),
                "month < 1 trebuie sa arunce IllegalArgumentException");
    }
 
    @Test
    public void testCard_Constructor_MonthTooHigh() {
        assertThrows(IllegalArgumentException.class, () ->
                new Card(1, 1, "Debit", "Debit Card",
                        "RO49AAAA1B31007593840000", "1234567890123456",
                        13, 25, 123, 200),
                "month > 12 trebuie sa arunce IllegalArgumentException");
    }
 
 
    @Test
    public void testCard_Constructor_NegativeYear() {
        assertThrows(IllegalArgumentException.class, () ->
                new Card(1, 1, "Debit", "Debit Card",
                        "RO49AAAA1B31007593840000", "1234567890123456",
                        12, -1, 123, 200),
                "year negativ trebuie sa arunce IllegalArgumentException");
    }
 
 
    @Test
    public void testCard_Constructor_CvvTooLow() {
        assertThrows(IllegalArgumentException.class, () ->
                new Card(1, 1, "Debit", "Debit Card",
                        "RO49AAAA1B31007593840000", "1234567890123456",
                        12, 25, 99, 200),
                "cvv < 100 trebuie sa arunce IllegalArgumentException");
    }
 
    @Test
    public void testCard_Constructor_CvvTooHigh() {
        assertThrows(IllegalArgumentException.class, () ->
                new Card(1, 1, "Debit", "Debit Card",
                        "RO49AAAA1B31007593840000", "1234567890123456",
                        12, 25, 1000, 200),
                "cvv > 999 trebuie sa arunce IllegalArgumentException");
    }
 
 
    @Test
    public void testCard_Constructor_NegativeLimit() {
        assertThrows(IllegalArgumentException.class, () ->
                new Card(1, 1, "Debit", "Debit Card",
                        "RO49AAAA1B31007593840000", "1234567890123456",
                        12, 25, 123, -1),
                "limit negativa trebuie sa arunce IllegalArgumentException");
    }


}