package Tests;

import Services.AuthenticationService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;

public class AuthenticationServiceTest {

    //partiționare în clase de echivalență

    // clasa 1, orice string
    @Test
    public void testCrypt_ValidString_EquivalencePartitioning() throws NoSuchAlgorithmException {
        String input = "parola123";

        String expected = "095b2626c9b6bad0eb89019ea6091bd9";
        String result = AuthenticationService.Crypt(input);
        assertEquals(expected, result, "Hash-ul generat nu corespunde clasei valide de date.");
    }

    // clasa 2 null
    @Test
    public void testCrypt_NullString_EquivalencePartitioning() {
        assertThrows(NullPointerException.class, () -> {
            AuthenticationService.Crypt(null);
        }, "Ar trebui sa se arunce NullPointerException pentru input null.");
    }

    //analiza valorilor de frontieră

    // caz 1 lungime 0
    @Test
    public void testCrypt_EmptyString_BoundaryValue() throws NoSuchAlgorithmException {
        String input = "";
        String expected = "d41d8cd98f00b204e9800998ecf8427e";
        String result = AuthenticationService.Crypt(input);
        assertEquals(expected, result, "Hash-ul pentru limita inferioara (sir vid) este incorect.");
    }

    // caz 2 lungime 1
    @Test
    public void testCrypt_SingleChar_BoundaryValue() throws NoSuchAlgorithmException {
        String input = "a";
        String expected = "0cc175b9c0f1b6a831c399e269772661"; // MD5 standard pentru "a"
        String result = AuthenticationService.Crypt(input);
        assertEquals(expected, result, "Hash-ul pentru lungimea de frontiera 1 este incorect.");
    }

    // acoperire la nivel de instrucțiune, decizie, condiție,
    @Test
    public void testCrypt_CoverageStrategies() throws NoSuchAlgorithmException {
        // MD5("test") = 098f6bcd4621d373cade4e832627b4f6
        // Primul byte e 09 -> intra in if (hex.length() == 1) => Statement Coverage pentru ramura True.
        // Al doilea byte e 8f -> NU intra in if => Statement/Decision Coverage pentru ramura False.
        // Condiția este simpla (o singura expresie logica), deci Condition Coverage este de asemenea 100%.
        String input = "test";
        String expected = "098f6bcd4621d373cade4e832627b4f6";
        String result = AuthenticationService.Crypt(input);
        assertEquals(expected, result, "Strategiile de acoperire nu returneaza hash-ul corect.");
    }

    // circuite independente
    @Test
    public void testCrypt_IndependentPaths() throws NoSuchAlgorithmException {
        // Drumul 1: Nu se intra în if (se evita hexString.append('0'))
        // Drumul 2: Se intra în if (se executa hexString.append('0'))
        String input = "test";
        String result = AuthenticationService.Crypt(input);
        assertNotNull(result);
        assertEquals(32, result.length(), "Toate caile trebuie sa compuna in final un string de lungime 32.");
    }

    // ---------------------------------------------------------------------------------------------
    // 5. Teste suplimentare pentru a OMORÎ 2 mutanți neechivalenți rămași în viață
    // ---------------------------------------------------------------------------------------------
    
    // Mutantul 1: Operatorul bitwise `0xff & b` este alterat (ex: schimbat doar în `b`).
    // Dacă mutantul supraviețuiește, un byte negativ (ex. -1) va fi transformat într-un string hex 
    // de lungime mai mare, ex: "ffffffff", rezultând într-un șir final de lungime > 32.
    @Test
    public void testCrypt_KillMutant_BitwiseAnd() throws NoSuchAlgorithmException {
        // MD5 pt "mutant_test_123" va genera byți care, fără operația AND, devin negativi
        String result = AuthenticationService.Crypt("mutant_test_123");
        
        // Dacă mutantul elimină `0xff & b`, lungimea rezultatului va exploda și nu va fi 32.
        assertEquals(32, result.length(), 
                "Mutantul care a eliminat '0xff & b' a supravietuit! Lungimea nu mai este 32.");
    }

    // Mutantul 2: Condiția de decizie `if (hex.length() == 1)` este negată sau alterată la `== 0` etc.
    // Acest mutant elimină padding-ul necesar cu zero pentru valorile hex sub 16 (0-9, a-f).
    @Test
    public void testCrypt_KillMutant_ConditionPadding() throws NoSuchAlgorithmException {
        // "test" are hash-ul "098f6bcd..." care începe cu '0'.
        String result = AuthenticationService.Crypt("test");
        
        // Dacă condiția if a fost alterată de mutant, lipsește zero-ul din față.
        // Stringul va avea lungimea de 31 în loc de 32 (dacă un singur zero lipsește), 
        // și nu va începe cu "09".
        assertEquals(32, result.length(), 
                "Mutantul care altereaza decizia de padding (length == 1) a supravietuit! Lipseste un caracter.");
        assertTrue(result.startsWith("09"), 
                "Mutantul care previne adaugarea caracterului '0' a supravietuit!");
    }
}
