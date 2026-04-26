package Tests.FunctionalTesting;

import Models.Card;
import Models.User;
import Services.AuthenticationService;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoundaryValueAnalysis {


    // frontierele sunt 1 si 12 pentru luna
    @Test
    public void testCard_ExpirationDate() {
        Card cardLimitMin = new Card(1, 101, "Ion", "Min", "RO", "123", 1, 25, 0, 0);
        Card cardLimitMax = new Card(2, 102, "Ana", "Max", "RO", "123", 12, 99, 999, 9999);

        assertEquals(1, cardLimitMin.getMonth(), "Luna minima de frontiera e 1.");
        assertEquals(12, cardLimitMax.getMonth(), "Luna maxima de frontiera e 12.");
    }

    //analiza valorilor de frontieră

    // caz 1 lungime 0
    @Test
    public void testCrypt_EmptyString() throws NoSuchAlgorithmException {
        String input = "";
        String expected = "d41d8cd98f00b204e9800998ecf8427e";
        String result = AuthenticationService.Crypt(input);
        assertEquals(expected, result, "Hash-ul pentru limita inferioara (sir vid) este incorect.");
    }

    // caz 2 lungime 1
    @Test
    public void testCrypt_SingleChar() throws NoSuchAlgorithmException {
        String input = "a";
        String expected = "0cc175b9c0f1b6a831c399e269772661"; // MD5 standard pentru "a"
        String result = AuthenticationService.Crypt(input);
        assertEquals(expected, result, "Hash-ul pentru lungimea de frontiera 1 este incorect.");
    }

    //analiza valorilor de frontiera
    @Test
    public void testUser_CardsList() throws SQLException, NoSuchAlgorithmException {
        // caz 1 - niciun card
        User userNoCards = new User(1, "Ana", "M", new Date(), "a@b.ro", "p", true, new ArrayList<>());
        assertEquals(0, userNoCards.getCards().size(), "Limita inferioară (0 carduri) trebuie să fie gestionată corect.");

        // caz 2 - doar un card
        Card card = new Card(1, 1, "A", "C", "I", "N", 1, 26, 12, 100);
        userNoCards.AddCard(card);
        assertEquals(1, userNoCards.getCards().size(), "Limita (1 card) trebuie să fie gestionată corect.");
    }

}
