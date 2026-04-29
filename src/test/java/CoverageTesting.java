import Connection.MenuStatements;
import Models.User;
import Services.AuthenticationService;
import Services.AuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import Connection.ConnectionString;
import static org.mockito.Mockito.mock;

@Order(3)
public class CoverageTesting {

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

        // Injectarea mock-urilor pentru field-urile statice din MenuStatements folosind reflexie
        setStaticMock(MenuStatements.class.getDeclaredField("checkForExistingEmailStatement"), checkForExistingEmailStatement);
        setStaticMock(MenuStatements.class.getDeclaredField("getPasswordStatement"), getPasswordStatement);
        setStaticMock(MenuStatements.class.getDeclaredField("getUserStatement"), getUserStatement);

        // Injectarea mock-ului pentru auditService, care este un field privat
        Field auditServiceField = AuthenticationService.class.getDeclaredField("auditService");
        auditServiceField.setAccessible(true);
        auditServiceField.set(authenticationService, auditService);
    }

    private void setStaticMock(Field field, Object mock) throws Exception {
        field.setAccessible(true);
        field.set(null, mock);
    }

    @Test
    public void testLoginSuccess() throws SQLException, NoSuchAlgorithmException {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        String hashedPassword = AuthenticationService.Crypt(password);
        Date birthDate = new Date();

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
        when(rsUser.getDate(4)).thenReturn(new java.sql.Date(birthDate.getTime()));
        when(rsUser.getString(5)).thenReturn(email);
        when(rsUser.getString(6)).thenReturn(hashedPassword);

        // Act
        User user = authenticationService.login(email, password);

        // Assert
        assertNotNull(user);
        assertEquals(1, user.getIdUser());
        assertEquals("John", user.getFirstName());
        verify(auditService, times(1)).logAction("Logged in successfully for user: 1");
    }

    @Test
    public void testLoginEmailNotFound() throws SQLException, NoSuchAlgorithmException {
        // Arrange
        String email = "nonexistent@example.com";
        String password = "password";

        when(checkForExistingEmailStatement.executeQuery()).thenReturn(rsEmail);
        when(rsEmail.next()).thenReturn(false);

        // Act
        User user = authenticationService.login(email, password);

        // Assert
        assertNull(user);
        verify(auditService, never()).logAction(anyString());
    }

    @Test
    public void testLoginWrongPassword() throws SQLException, NoSuchAlgorithmException {
        // Arrange
        String email = "test@example.com";
        String password = "wrongpassword";
        String correctHashedPassword = AuthenticationService.Crypt("password");

        when(checkForExistingEmailStatement.executeQuery()).thenReturn(rsEmail);
        when(rsEmail.next()).thenReturn(true);
        when(rsEmail.getString(1)).thenReturn(email);

        when(getPasswordStatement.executeQuery()).thenReturn(rsPassword);
        when(rsPassword.next()).thenReturn(true);
        when(rsPassword.getString(1)).thenReturn(correctHashedPassword);

        // Act
        User user = authenticationService.login(email, password);

        // Assert
        assertNull(user);
        verify(auditService, never()).logAction(anyString());
    }

    @Test
    public void testLoginUserNotFoundAfterPasswordMatch() throws SQLException, NoSuchAlgorithmException {
        // Arrange
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
        when(rsUser.next()).thenReturn(false); // Detaliile utilizatorului nu sunt gasite

        // Act
        User user = authenticationService.login(email, password);

        // Assert
        assertNull(user);
        verify(auditService, never()).logAction(anyString());
    }

    @Test
    public void testLoginPasswordResultNotAvailable() throws SQLException, NoSuchAlgorithmException {
        // Arrange
        String email = "test@example.com";
        String password = "password";

        when(checkForExistingEmailStatement.executeQuery()).thenReturn(rsEmail);
        when(rsEmail.next()).thenReturn(true);
        when(rsEmail.getString(1)).thenReturn(email);

        when(getPasswordStatement.executeQuery()).thenReturn(rsPassword);
        when(rsPassword.next()).thenReturn(false); // Nu se gaseste parola pentru email

        // Act
        User user = authenticationService.login(email, password);

        // Assert
        assertNull(user);
        verify(auditService, never()).logAction(anyString());
    }
    
}
