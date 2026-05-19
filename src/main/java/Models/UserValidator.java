package Models;

import java.util.Date;

/**
 * Clasă utilitară pentru validarea unui obiect User.
 * Conține logică pură, fără dependențe de bază de date,
 * folosită pentru mutation testing.
 */
public class UserValidator {

    /**
     * Verifică dacă oricare dintre câmpurile obligatorii ale unui User este null.
     *
     * @return true dacă cel puțin un câmp este null, false altfel
     */
    public static boolean isNull(String firstName, String lastName, Date birthDate,
                                 String email, String password) {
        if (firstName == null || lastName == null || birthDate == null
                || email == null || password == null)
            return true;
        else
            return false;
    }
}