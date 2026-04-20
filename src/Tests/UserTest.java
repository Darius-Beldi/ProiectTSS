package Tests;

import Models.Card;
import Models.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class UserTest {

    // partitionare in clase de echivalenta
    @Test
    public void testUser_EquivalencePartitioning() throws SQLException, NoSuchAlgorithmException {
        Date birthDate = new Date(95, 5, 10); // 10 Iunie 1995
        List<Card> cards = new ArrayList<>();
        User user = new User(1, "Ion", "Popescu", birthDate, "ion@test.com", "pass", true, cards);

        assertNotNull(user, "User creat trebuie să fie nenul.");
        assertEquals("Ion", user.getFirstName());
        assertEquals("Popescu", user.getLastName());
    }

    //analiza valorilor de frontiera
    @Test
    public void testUser_BoundaryValue_CardsList() throws SQLException, NoSuchAlgorithmException {
        // caz 1 - niciun card
        User userNoCards = new User(1, "Ana", "M", new Date(), "a@b.ro", "p", true, new ArrayList<>());
        assertEquals(0, userNoCards.getCards().size(), "Limita inferioară (0 carduri) trebuie să fie gestionată corect.");

        // caz 2 - doar un card
        Card card = new Card(1, 1, "A", "C", "I", "N", 1, 26, 12, 100);
        userNoCards.AddCard(card);
        assertEquals(1, userNoCards.getCards().size(), "Limita (1 card) trebuie să fie gestionată corect.");
    }

    //acoperire la nivel de instrucțiune, decizie, condiție,
    @Test
    public void testUser_UpdateCards_Coverage() throws SQLException, NoSuchAlgorithmException {
        // Atingem logica UpdateCards: instructiuni directe si conditia decizionala.
        User user = new User(1, "A", "B", new Date(), "email", "pass", true, new ArrayList<>());
        
        List<Card> newCards = new ArrayList<>();
        newCards.add(new Card(1, 1, "N", "CN", "I", "No", 2, 25, 123, 100));
        
        // Coverage pentru Statement și apel.
        user.UpdateCards(newCards);
        
        assertEquals(1, user.getCards().size(), "Lista trebuie să fi fost actualizată.");
        assertEquals("CN", user.getCards().get(0).getCardName());
    }

    //circuite independete
    @Test
    public void testUser_ConstructorPaths() throws SQLException, NoSuchAlgorithmException {
        // drumul 1 - evit baza de date
        User userDbTrue = new User(1, "F", "L", new Date(), "e", "p", true, new ArrayList<>());
        assertEquals(1, userDbTrue.getIdUser(), "ID-ul trebuie forțat de argument.");
    }

}