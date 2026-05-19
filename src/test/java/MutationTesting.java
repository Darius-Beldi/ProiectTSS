import Models.UserValidator;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Mutation testing pentru UserValidator.isNull(...)
 *
 * Codul sub test:
 *   if (firstName == null || lastName == null || birthDate == null
 *        || email == null || password == null)
 *       return true;
 *   else
 *       return false;
 */
public class MutationTesting {

    // === TESTE INIȚIALE — înainte de analiza mutanților ===

    @Test
    public void testAllFieldsPresent_returnsFalse() {
        assertFalse(UserValidator.isNull("John", "Doe", new Date(), "a@b.com", "pass"));
    }

    @Test
    public void testFirstNameNull_returnsTrue() {
        assertTrue(UserValidator.isNull(null, "Doe", new Date(), "a@b.com", "pass"));
    }

    @Test
    public void testPasswordNull_returnsTrue() {
        assertTrue(UserValidator.isNull("John", "Doe", new Date(), "a@b.com", null));
    }

    // === TESTE SUPLIMENTARE — pentru a omorî mutanții supraviețuitori ===

    @Test
    public void testOnlyLastNameNull_killsLastNameMutant() {
        assertTrue(UserValidator.isNull("John", null, new Date(), "a@b.com", "pass"));
    }

    @Test
    public void testOnlyEmailNull_killsEmailMutant() {
        assertTrue(UserValidator.isNull("John", "Doe", new Date(), null, "pass"));
    }
}