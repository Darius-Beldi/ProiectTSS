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

/**
 * Clasă de teste pentru evaluarea funcționalităților User.
 *
 * Utilizăm constructorul cu `alreadyInDatabase=true` pentru a evita 
 * apelurile reale către baza de date din teste.
 */
public class UserTest {

    // ---------------------------------------------------------------------------------------------
    // 1. Partiționare în clase de echivalență (Equivalence Class Partitioning)
    // ---------------------------------------------------------------------------------------------
    @Test
    public void testUser_EquivalencePartitioning() throws SQLException, NoSuchAlgorithmException {
        // Clasă de echivalență: Date normale (vârstă validă, string-uri ok).
        Date birthDate = new Date(95, 5, 10); // 10 Iunie 1995
        List<Card> cards = new ArrayList<>();
        User user = new User(1, "Ion", "Popescu", birthDate, "ion@test.com", "pass", true, cards);

        assertNotNull(user, "User creat trebuie să fie nenul.");
        assertEquals("Ion", user.getFirstName());
        assertEquals("Popescu", user.getLastName());
    }

    // ---------------------------------------------------------------------------------------------
    // 2. Analiza valorilor de frontieră (Boundary Value Analysis)
    // ---------------------------------------------------------------------------------------------
    @Test
    public void testUser_BoundaryValue_CardsList() throws SQLException, NoSuchAlgorithmException {
        // Valori de frontieră pe lista de carduri: 0 carduri (limită inferioară).
        User userNoCards = new User(1, "Ana", "M", new Date(), "a@b.ro", "p", true, new ArrayList<>());
        assertEquals(0, userNoCards.getCards().size(), "Limita inferioară (0 carduri) trebuie să fie gestionată corect.");

        // Adăugăm un card (limită: 1 element).
        Card card = new Card(1, 1, "A", "C", "I", "N", 1, 26, 12, 100);
        userNoCards.AddCard(card);
        assertEquals(1, userNoCards.getCards().size(), "Limita (1 card) trebuie să fie gestionată corect.");
    }

    // ---------------------------------------------------------------------------------------------
    // 3. Acoperire la nivel de instrucțiune, decizie și condiție (Coverage)
    // ---------------------------------------------------------------------------------------------
    @Test
    public void testUser_UpdateCards_Coverage() throws SQLException, NoSuchAlgorithmException {
        // Atingem logica UpdateCards: instrucțiuni directe și condiția decizională.
        User user = new User(1, "A", "B", new Date(), "email", "pass", true, new ArrayList<>());
        
        List<Card> newCards = new ArrayList<>();
        newCards.add(new Card(1, 1, "N", "CN", "I", "No", 2, 25, 123, 100));
        
        // Coverage pentru Statement și apel.
        user.UpdateCards(newCards);
        
        assertEquals(1, user.getCards().size(), "Lista trebuie să fi fost actualizată.");
        assertEquals("CN", user.getCards().get(0).getCardName());
    }

    // ---------------------------------------------------------------------------------------------
    // 4. Circuite independente (Independent Paths)
    // ---------------------------------------------------------------------------------------------
    @Test
    public void testUser_ConstructorPaths() throws SQLException, NoSuchAlgorithmException {
        // În constructorul User avem un `if (alreadyInDatabase) {...} else {...}`
        // Drumul 1: alreadyInDatabase = true (evităm DB)
        User userDbTrue = new User(1, "F", "L", new Date(), "e", "p", true, new ArrayList<>());
        assertEquals(1, userDbTrue.getIdUser(), "ID-ul trebuie forțat de argument.");
    }

    // ---------------------------------------------------------------------------------------------
    // 5. Analiză mutanți neechivalenți (Kill Mutants)
    // ---------------------------------------------------------------------------------------------
    // Mutant 1 (Metoda AddCard): Mutantul elimină apelul `cards.add(c)`.
    @Test
    public void testUser_KillMutant_AddCard() throws SQLException, NoSuchAlgorithmException {
        User user = new User(1, "N", "P", new Date(), "e", "p", true, new ArrayList<>());
        Card c = new Card(1, 1, "1", "1", "1", "1", 1, 1, 1, 1);
        
        user.AddCard(c);
        
        // Dacă mutantul elimină adăugarea, size va fi 0.
        assertEquals(1, user.getCards().size(), 
            "Mutantul care elimină 'cards.add()' a supraviețuit.");
    }

    // Mutant 2 (Metoda setPassword): Mutantul nu actualizează `Password` cu valoarea criptată, 
    // ci o lasă neschimbată sau setează null.
    @Test
    public void testUser_KillMutant_SetPassword() throws SQLException, NoSuchAlgorithmException {
        User user = new User(1, "N", "P", new Date(), "e", "old_pass", true, new ArrayList<>());
        
        user.setPassword("new_crypted_pass");
        
        // Dacă mutantul supraviețuiește, parola va fi încă "old_pass" sau "null".
        assertEquals("new_crypted_pass", user.getPassword(), 
            "Mutantul care a ignorat atribuirea parolei în setPassword a supraviețuit.");
    }
}