import Connection.ConnectionString;
import Connection.MenuStatements;
import Services.AuthenticationService;
import Services.AuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Testare structurala - Circuite Independente
 *
 * Functia testata: AuthenticationService.login(String email, String password)
 *
 * Complexitatea ciclomatica V(G) = 6
 * => 6 circuite independente in setul de baza (a..f)
 *
 * Fiecare test corespunde unui circuit din setul de baza.
 */
@Order(4)
public class IndependentCircuits {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private AuditService auditService;

    @Mock
    private PreparedStatement checkForExistingEmailStatement;

    @Mock
    private PreparedStatement getPasswordStatement;

    @Mock
    private PreparedStatement getUserStatement;

    @Mock
    private ResultSet rsEmail;

    @Mock
    private ResultSet rsPassword;

    @Mock
    private ResultSet rsUser;

    @BeforeEach
    public void setUp() throws Exception {
        Field connectionField = ConnectionString.class.getDeclaredField("c");
        connectionField.setAccessible(true);
        connectionField.set(null, mock(java.sql.Connection.class));
        MockitoAnnotations.openMocks(this);

        setStaticMock(MenuStatements.class.getDeclaredField("checkForExistingEmailStatement"), checkForExistingEmailStatement);
        setStaticMock(MenuStatements.class.getDeclaredField("getPasswordStatement"), getPasswordStatement);
        setStaticMock(MenuStatements.class.getDeclaredField("getUserStatement"), getUserStatement);

        Field auditServiceField = AuthenticationService.class.getDeclaredField("auditService");
        auditServiceField.setAccessible(true);
        auditServiceField.set(authenticationService, auditService);
    }

    private void setStaticMock(Field field, Object mock) throws Exception {
        field.setAccessible(true);
        field.set(null, mock);
    }

    /**
     * Circuit a : N1, N2, N3, N4, N5, N6, N7, N8, exit
     * Login reusit - toate conditiile true.
     */
    @Test
    public void circuit_a_loginSuccess() throws Exception {
        String email = "test@example.com";
        String password = "password";
        String hashedPassword = AuthenticationService.Crypt(password);

        when(checkForExistingEmailStatement.executeQuery()).thenReturn(rsEmail);
        when(rsEmail.next()).thenReturn(true);
        when(rsEmail.getString(1)).thenReturn(email);

        when(getPasswordStatement.executeQuery()).thenReturn(rsPassword);
        when(rsPassword.next()).thenReturn(true);
        when(rsPassword.getString(1)).thenReturn(hashedPassword);

        when(getUserStatement.executeQuery()).thenReturn(rsUser);
        when(rsUser.next()).thenReturn(true);
        when(rsUser.getInt(1)).thenReturn(1);
        when(rsUser.getString(2)).thenReturn("John");
        when(rsUser.getString(3)).thenReturn("Doe");
        when(rsUser.getDate(4)).thenReturn(new java.sql.Date(new Date().getTime()));
        when(rsUser.getString(5)).thenReturn(email);
        when(rsUser.getString(6)).thenReturn(hashedPassword);

        assertNotNull(authenticationService.login(email, password));
    }

    /**
     * Circuit b : N1, N2, N4, N9, exit
     * Email inexistent in baza de date (rs.next() == false).
     */
    @Test
    public void circuit_b_emailNotFound() throws Exception {
        when(checkForExistingEmailStatement.executeQuery()).thenReturn(rsEmail);
        when(rsEmail.next()).thenReturn(false);

        assertNull(authenticationService.login("test@example.com", "password"));
    }

    /**
     * Circuit c : N1, N2, N3, N4, N9, exit
     * Email gasit in baza de date dar diferit de inputul utilizatorului.
     */
    @Test
    public void circuit_c_emailMismatch() throws Exception {
        when(checkForExistingEmailStatement.executeQuery()).thenReturn(rsEmail);
        when(rsEmail.next()).thenReturn(true);
        when(rsEmail.getString(1)).thenReturn("other@example.com");

        assertNull(authenticationService.login("test@example.com", "password"));
    }

    /**
     * Circuit d : N1, N2, N3, N4, N5, N9, exit
     * Email corect dar nu exista inregistrare de parola
     * (passwordResult.next() == false).
     */
    @Test
    public void circuit_d_passwordResultNotAvailable() throws Exception {
        String email = "test@example.com";

        when(checkForExistingEmailStatement.executeQuery()).thenReturn(rsEmail);
        when(rsEmail.next()).thenReturn(true);
        when(rsEmail.getString(1)).thenReturn(email);

        when(getPasswordStatement.executeQuery()).thenReturn(rsPassword);
        when(rsPassword.next()).thenReturn(false);

        assertNull(authenticationService.login(email, "password"));
    }

    /**
     * Circuit e : N1, N2, N3, N4, N5, N6, N9, exit
     * Parola gresita.
     */
    @Test
    public void circuit_e_wrongPassword() throws Exception {
        String email = "test@example.com";

        when(checkForExistingEmailStatement.executeQuery()).thenReturn(rsEmail);
        when(rsEmail.next()).thenReturn(true);
        when(rsEmail.getString(1)).thenReturn(email);

        when(getPasswordStatement.executeQuery()).thenReturn(rsPassword);
        when(rsPassword.next()).thenReturn(true);
        when(rsPassword.getString(1)).thenReturn(AuthenticationService.Crypt("alta_parola"));

        assertNull(authenticationService.login(email, "password"));
    }

    /**
     * Circuit f : N1, N2, N3, N4, N5, N6, N7, N9, exit
     * Parola corecta dar utilizatorul nu mai exista in tabela Users.
     */
    @Test
    public void circuit_f_userNotFoundAfterPasswordMatch() throws Exception {
        String email = "test@example.com";
        String hashedPassword = AuthenticationService.Crypt("password");

        when(checkForExistingEmailStatement.executeQuery()).thenReturn(rsEmail);
        when(rsEmail.next()).thenReturn(true);
        when(rsEmail.getString(1)).thenReturn(email);

        when(getPasswordStatement.executeQuery()).thenReturn(rsPassword);
        when(rsPassword.next()).thenReturn(true);
        when(rsPassword.getString(1)).thenReturn(hashedPassword);

        when(getUserStatement.executeQuery()).thenReturn(rsUser);
        when(rsUser.next()).thenReturn(false);

        assertNull(authenticationService.login(email, "password"));
    }
}