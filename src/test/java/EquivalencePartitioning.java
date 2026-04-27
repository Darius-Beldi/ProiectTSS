import Models.Card;
import Services.AuthenticationService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;


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
    public void testCard_Constructor_ValidName() throws SQLException, NoSuchAlgorithmException {

        String validName = "Debit Card";

        Card card = new Card(1, validName);

        assertNotNull(card, "Card object should not be null");
        assertEquals(validName, card.getCardName(), "Card name should match the input");
    }

    @Test
    public void testCard_Constructor_NullName() {

        String invalidName = null;

        assertThrows(IllegalArgumentException.class, () -> {
            new Card(1, invalidName);
        }, "Constructor should throw IllegalArgumentException for null name");
    }

}