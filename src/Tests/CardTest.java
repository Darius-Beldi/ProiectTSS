package Tests;

import Models.Card;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clasă de teste pentru evaluarea metodelor specifice din Card.
 *
 * S-a utilizat constructorul alternativ (cel care nu generează Card nou și nu scrie în DB)
 * pentru a testa în principal metodele de afișare/detalii și comparare, asigurând
 * circuite și logici decizionale (ex. compareTo).
 */
public class CardTest {

    // ---------------------------------------------------------------------------------------------
    // 1. Partiționare în clase de echivalență (Equivalence Class Partitioning)
    // ---------------------------------------------------------------------------------------------
    // Clasa de echivalență: Obiecte Card complet inițializate.
    @Test
    public void testCard_EquivalencePartitioning() {
        Card card = new Card(1, 101, "Ion Popescu", "Card Salariu", 
                             "RO123456789012345678901", "12345678901234", 12, 26, 123, 1000);
        
        assertNotNull(card, "Card-ul creat nu trebuie să fie null.");
        assertEquals("Ion Popescu", card.getName(), "Numele titularului trebuie sa corespunda.");
        assertEquals("Card Salariu", card.getCardName(), "Numele cardului trebuie sa corespunda.");
    }

    // ---------------------------------------------------------------------------------------------
    // 2. Analiza valorilor de frontieră (Boundary Value Analysis)
    // ---------------------------------------------------------------------------------------------
    // Valorile de frontieră (pentru anul de expirare: minim 25, luna 1 sau 12).
    @Test
    public void testCard_BoundaryValue_ExpirationDate() {
        Card cardLimitMin = new Card(1, 101, "Ion", "Min", "RO", "123", 1, 25, 0, 0);
        Card cardLimitMax = new Card(2, 102, "Ana", "Max", "RO", "123", 12, 99, 999, 9999);
        
        assertEquals(1, cardLimitMin.getMonth(), "Luna minima de frontiera e 1.");
        assertEquals(12, cardLimitMax.getMonth(), "Luna maxima de frontiera e 12.");
    }

    // ---------------------------------------------------------------------------------------------
    // 3. Acoperire la nivel de instrucțiune, decizie și condiție (Coverage)
    // ---------------------------------------------------------------------------------------------
    @Test
    public void testCard_ShowDetails_Coverage() {
        Card card = new Card(1, 101, "Ion", "Card", "RO123", "456", 1, 26, 123, 100);
        String details = card.ShowDetails();
        
        // Asigurăm trecerea prin toate concatenările din metoda ShowDetails.
        assertTrue(details.contains("IBAN: RO123"));
        assertTrue(details.contains("Card Name: Card"));
        assertTrue(details.contains("Expiration Date: 1/26"));
        assertTrue(details.contains("CVV: 123"));
    }

    // ---------------------------------------------------------------------------------------------
    // 4. Circuite independente (Independent Paths)
    // ---------------------------------------------------------------------------------------------
    @Test
    public void testCard_CompareTo_IndependentPaths() {
        // Metoda compareTo are drumuri ce depind de rezultatul intern String.compareTo.
        Card card1 = new Card(1, 101, "Ion", "Alpha", "RO1", "11", 1, 26, 1, 10);
        Card card2 = new Card(2, 102, "Ion", "Beta", "RO2", "22", 1, 26, 2, 20);
        Card card3 = new Card(3, 103, "Ion", "Alpha", "RO3", "33", 1, 26, 3, 30);
        
        // Drumul unde this < o
        assertTrue(card1.compareTo(card2) < 0, "Alpha trebuie sa fie inainte de Beta.");
        
        // Drumul unde this > o
        assertTrue(card2.compareTo(card1) > 0, "Beta trebuie sa fie dupa Alpha.");
        
        // Drumul unde this == o
        assertEquals(0, card1.compareTo(card3), "Alpha trebuie sa fie egal cu Alpha.");
    }

    // ---------------------------------------------------------------------------------------------
    // 5. Analiză mutanți neechivalenți (Kill Mutants)
    // ---------------------------------------------------------------------------------------------
    // Mutant 1 (Metoda compareTo): Un mutant ar putea schimba rezultatul return-ului 
    // din metoda compareTo (ex: să returneze mereu 0 sau inversul).
    @Test
    public void testCard_KillMutant_CompareTo() {
        Card cardA = new Card(1, 1, "A", "A", "A", "A", 1, 1, 1, 1);
        Card cardB = new Card(2, 2, "B", "B", "B", "B", 2, 2, 2, 2);
        
        // A (65) - B (66) = -1. Dacă e altceva, mutantul a alterat return-ul funcției.
        int diff = cardA.compareTo(cardB);
        assertTrue(diff < 0, "Mutantul care altereaza logica compareTo a supravietuit.");
    }

    // Mutant 2 (Metoda getBalance): Un mutant ar putea altera (înlocui) getBalance 
    // cu return 0 sau return null (aici e Integer).
    @Test
    public void testCard_KillMutant_Getters() {
        Card card = new Card(1, 1, "Nume", "Nume", "I", "N", 1, 1, 1, 555);
        
        // Valoarea 555 trebuie returnată exact așa cum a fost setată
        assertEquals(555, card.getBalance(), 
                "Mutantul care a alterat returnul getBalance (ex: return 0) a supravietuit!");
    }
}