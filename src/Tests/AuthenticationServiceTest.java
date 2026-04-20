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


}
