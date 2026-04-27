import Models.Card;
import Models.User;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BoundaryValueAnalysis {

    // --- BVA for User.Crypt(String input) ---
    // Boundaries: Empty string, 1 char, typical length, very long length

    @Test
    public void testCrypt_EmptyString() throws NoSuchAlgorithmException {
        String input = "";
        String result = User.Crypt(input);
        assertNotNull(result);
        assertEquals(32, result.length(), "MD5 hash should be 32 characters long");
        // Hash for empty string is known: d41d8cd98f00b204e9800998ecf8427e
        assertEquals("d41d8cd98f00b204e9800998ecf8427e", result);
    }

    @Test
    public void testCrypt_SingleChar() throws NoSuchAlgorithmException {
        String input = "a";
        String result = User.Crypt(input);
        assertNotNull(result);
        assertEquals(32, result.length(), "MD5 hash should be 32 characters long");
    }

    @Test
    public void testCrypt_TypicalString() throws NoSuchAlgorithmException {
        String input = "Password123!";
        String result = User.Crypt(input);
        assertNotNull(result);
        assertEquals(32, result.length(), "MD5 hash should be 32 characters long");
    }

    @Test
    public void testCrypt_LongString() throws NoSuchAlgorithmException {
        StringBuilder inputBuilder = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            inputBuilder.append("a");
        }
        String input = inputBuilder.toString();
        String result = User.Crypt(input);
        assertNotNull(result);
        assertEquals(32, result.length(), "MD5 hash should be 32 characters long");
    }


    // --- BVA for User.UpdateCards(List<Card> _cards) ---
    // Boundaries: Empty list, List with 1 item, List with multiple items

    private User createDummyUser() throws SQLException, NoSuchAlgorithmException {
        return new User(1, "Test", "User", new Date(), "test@test.com", "pass", true, new ArrayList<>());
    }

    private Card createDummyCard(int id) {
        return new Card(id, 1, "Name", "CardName" + id, "IBAN", "Number", 12, 25, 123, 100);
    }

    @Test
    public void testUpdateCards_EmptyList() throws SQLException, NoSuchAlgorithmException {
        User user = createDummyUser();
        List<Card> emptyList = new ArrayList<>();
        
        user.UpdateCards(emptyList);
        
        assertNotNull(user.getCards());
        assertEquals(0, user.getCards().size());
    }

    @Test
    public void testUpdateCards_SingleItemList() throws SQLException, NoSuchAlgorithmException {
        User user = createDummyUser();
        List<Card> singleItemList = new ArrayList<>();
        singleItemList.add(createDummyCard(1));
        
        user.UpdateCards(singleItemList);
        
        assertNotNull(user.getCards());
        assertEquals(1, user.getCards().size());
        assertEquals("CardName1", user.getCards().get(0).getCardName());
    }

    @Test
    public void testUpdateCards_MultipleItemsList() throws SQLException, NoSuchAlgorithmException {
        User user = createDummyUser();
        List<Card> multipleItemsList = new ArrayList<>();
        multipleItemsList.add(createDummyCard(1));
        multipleItemsList.add(createDummyCard(2));
        multipleItemsList.add(createDummyCard(3));
        
        user.UpdateCards(multipleItemsList);
        
        assertNotNull(user.getCards());
        assertEquals(3, user.getCards().size());
        assertEquals("CardName3", user.getCards().get(2).getCardName());
    }
}
