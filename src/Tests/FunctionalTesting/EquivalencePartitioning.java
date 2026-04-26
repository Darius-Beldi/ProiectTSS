package Tests.FunctionalTesting;

import Models.Card;
import Models.User;
import Services.AuthenticationService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

    //TODO
    // de implementat calumea
    @Test
    public void testCard_EquivalencePartitioning() {
        Card card = new Card(1, 101, "Ion Popescu", "Card Salariu",
                "RO123456789012345678901", "12345678901234", 12, 26, 123, 1000);

        assertNotNull(card, "Card-ul creat nu trebuie să fie null.");
        assertEquals("Ion Popescu", card.getName(), "Numele titularului trebuie sa corespunda.");
        assertEquals("Card Salariu", card.getCardName(), "Numele cardului trebuie sa corespunda.");
    }

    @Test
    public void testUser_EquivalencePartitioning() throws SQLException, NoSuchAlgorithmException {
        Date birthDate = new Date(95, 5, 10); // 10 Iunie 1995
        List<Card> cards = new ArrayList<>();
        User user = new User(1, "Ion", "Popescu", birthDate, "ion@test.com", "pass", true, cards);

        assertNotNull(user, "User creat trebuie sa fie nenul.");
        assertEquals("Ion", user.getFirstName());
        assertEquals("Popescu", user.getLastName());
    }
}
