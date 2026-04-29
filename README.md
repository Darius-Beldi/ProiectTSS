# ProiectTSS
_Note: The project is docuemnted in the [wiki](https://github.com/Darius-Beldi/ProiectTSS/wiki) section of the repository_
# Description
This project is written in ```Java 26``` using Maven build tool. 


| Table of Contents | 
| --- | 
| [Functional Testing - Boundary Value Analysis](https://github.com/Darius-Beldi/ProiectTSS/wiki/Boundary-Value-Analysis) | 
| [Functional Testing - Equivalence Partitioning ](https://github.com/Darius-Beldi/ProiectTSS/wiki/Equivalence-Partitioning-Tests) | 
| [Structural Testing - Instruction, Decision & Conditional Coverage](https://github.com/Darius-Beldi/ProiectTSS/wiki/Structural-Testing) | 
# Dependencies
The external libraries used, and imported through Maven are : 
- ```JUnit 5.1.0.2```, the framework used to run the tests
- ```Mockito 5.14.2```, the framework that allowed creating mocks of classes to bypass the database queries 

# Results
<img width="715" height="711" alt="image" src="https://github.com/user-attachments/assets/29cc072b-0b15-43a6-94e2-b184522b71a0" />


# Sources used 
- [Junit Documentation ](https://junit.org/)
- [Maven Documentation](https://junit.org/)
- [Mockito Documentation](https://site.mockito.org/) 




# Function 1 : User.Crypt(String input)
A method that, given a string, hashes it using the MD5 algorithm.
```java
public static String Crypt(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(input.getBytes());
        byte[] digest = md.digest();

        // Convert byte array to hexadecimal string
        StringBuilder hexString = new StringBuilder();
        for (byte b : digest) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
```

The boundaries are based on the input string's length: 
- ```Empty```
- ```1 char```
- ```a typical length```
- ```very long length``` 

All the tests passed!

# Function 2 : User.UpdateCards(List<Card> _cards)
A function that updates the user's cards list.
```java
public void UpdateCards(List<Card> _cards){
        cards = _cards;
    }
```
The boundaries are based on the input list's length: 
- ```Empty```
- ```1 item```
- ```more items```

All the tests passed!




# Function 1 : AuthenticationService.Crypt(String input)
 A function that takes a string and hashes it for security using MD5 algorithm.
```java
public static String Crypt(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(input.getBytes());
        byte[] digest = md.digest();

        // Convert byte array to hexadecimal string
        StringBuilder hexString = new StringBuilder();
        for (byte b : digest) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
```
 The equivalence classes are either ```NULL``` or a String of ```length > 0```.

This function is passing both tests

# Function 2 : Card.Card(Integer idUser, String cardName)
A constructor for the class Models.Card  
```java
public Card(Integer _idUser, String _CardName) throws SQLException, NoSuchAlgorithmException {
        generatedIdCard += 1;
        CardName = _CardName;


        Name = userService.userFirstName(_idUser);
        Name += userService.userLastName(_idUser);

        IBAN = generateIBAN();
        Number = generateNumber();
        Month = rand.nextInt(12) + 1;
        Year = 25 + rand.nextInt(7);
        CVV = generateCVV();
        Balance = 200;
        idCard = generatedIdCard;
        idUser = _idUser;
        cardService.insertIntoDatabase(this);
    }
```
 The equivalence classes are either ```NULL``` or a String of ```length > 0```.

This function doesn't have an edge case for when the card name is passed to it as ```NULL```




# Function to test : AuthenticationService.Login(String email, String password) 
A function that tests if the input email and password are a valid combination for a user 

### Function Code

<img width="1017" height="583" alt="image" src="https://github.com/user-attachments/assets/a564804d-6b8e-4c36-869c-cfb67ac434c6" />


### Control Flow Graph
<img width="300" height="1450" alt="ControlFlowDiagram" src="https://github.com/user-attachments/assets/a9183892-f97f-4953-8286-661e33b05578" />


For coverage testing we used the ```Mokito``` library to make mocks to simulate database entries. 

```java
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
```

## 1) Instruction Coverage 
```testLoginSuccess()```  tests almost all the lines (doesn't make sense to test for the else, because they all lead to ```return null```)
```java
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
```


## 2) Decision Coverage 
The tests : 
- ```testLoginEmailNotFound()```
- ```testLoginWrongPassword()```
- ```testLoginUserNotFoundAfterPasswordMatch()```
- ```testLoginPasswordResultNotAvailable()```

All those test for when the if clauses are ```false``` and go to line 30, ```return  null```


## 3) Conditional Coverage 
This function checks if the User object has any of the string fields ```null```
```java 
 public boolean isNull(){
        if (FirstName == null || LastName == null || BirthDate == null || Email == null || Password == null)
            return true;
        else
            return false;
    }
```

The conditional coverage tests are the following : 
- ```testIsNullAllFieldsPresent()```
- ```testIsNullFirstNameNull()```
- ```testIsNullLastNameNull()```
- ```testIsNullBirthDateNull()```
- ```testIsNullEmailNull()```
- ```testIsNullPasswordNull()```
