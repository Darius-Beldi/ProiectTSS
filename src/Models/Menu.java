package Models;

import Connection.InitializeDatabase;
import Connection.MenuStatements;
import Services.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.*;

import static Services.AuthenticationService.Crypt;

public class Menu extends MenuStatements {

    private static boolean toInitializeDataBase = true;
    private static Integer idCurrentUser;
    private static User currentUser;
    private AuthenticationService authService;
    private UserService userService;
    private CardServices cardService;
    private TransactionService transactionService;
    private AdressBooksService addressBookService;
    private InitializeDatabase  initializeDatabase;

    static {
        idCurrentUser = -1;
    }
    {
        AuditService auditService = new AuditService();
        auditService.logAction("Loaded Menu class");
    }
    public Menu() {
        this.authService = new AuthenticationService();
        this.userService = new UserService();
        this.cardService = new CardServices();
        this.transactionService = new TransactionService();
        this.addressBookService = new AdressBooksService();
    }

    private void databaseInitialization(){
        while(true) {
            System.out.println("Do you want to initialize database? (y/n)");
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            if(input.equals("y")) {
                while(true) {
                    System.out.println("Do you want to populate the database with default values?  (y/n)");
                    Scanner sc2 = new Scanner(System.in);
                    String input2 = sc2.nextLine();
                    if(input2.equals("y")) {
                        initializeDatabase = new InitializeDatabase(true, true);
                        return;
                    }
                    if(input2.equals("n")) {
                        initializeDatabase = new InitializeDatabase(true, false);
                        return;
                    }
                }
            }
            if(input.equals("n")) {
                return;
            }
        }
    }

    //Functions that use the auth service
    public void menu() throws SQLException {
        if(toInitializeDataBase)
            databaseInitialization();

        while(true){
            if (chooseLorR()) //true= login , false = register
                try {
                    System.out.println("LOGIN");
                    System.out.println("Email: ");
                    String email = new Scanner(System.in).nextLine();
                    System.out.println("Password: ");
                    String password = new Scanner(System.in).nextLine();

                    currentUser = authService.login(email, password);

                    if (currentUser != null) {
                        System.out.println("Login succesful");
                        mainPage();
                        return;
                    } else {
                        System.out.println("Login failed");
                    }
                } catch (SQLException | NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            else {
                Register();
            }
        }

    }

    ///  true = login, false = register
    public boolean chooseLorR(){
        System.out.println("Welcome to Banking App");
        System.out.println("Do you already have an account?");
        System.out.println("Y/N: ");
        boolean ok = true;
        Scanner sc = new Scanner(System.in);
        while(ok==true){
            String HasAnAccount = sc.nextLine();
            switch(HasAnAccount.toUpperCase()){

                case "Y":
                    return true;

                case "N":
                    return false;

                default:
                    System.out.println("Wrong input. Please try again");
                    System.out.println("Y/N: ");
                    break;
            }
        }
        return true;
    }

    public void Register() throws SQLException {
        System.out.println("REGISTER");
        System.out.println("First Name: ");
        String _FirstName = new Scanner(System.in).nextLine();
        System.out.println("Last Name: ");
        String _LastName = new Scanner(System.in).nextLine();



        //Birthday Handling
        int[] _Birth_datearray;
        Date _Birth_date;
        while(true){
            System.out.println("Birth Date: (yyyy-mm-dd)");
            try{
            _Birth_datearray = Arrays.stream(new Scanner(System.in).nextLine().split("-")).mapToInt(Integer::parseInt).toArray();
            if(_Birth_datearray[1] > 12 || _Birth_datearray[1] < 1 || _Birth_datearray[2] > 31 || _Birth_datearray[2] < 1){
                System.out.println("Invalid date format");
                continue;
            }
            _Birth_date = new Date(_Birth_datearray[0] - 1900, _Birth_datearray[1]-1, _Birth_datearray[2]-1);
            }catch (Exception e){
                System.out.println("Invalid date format");
                continue;
            }
            break;

        }

        //Email Handling
        String _Email = "";
        while (true){
            System.out.println("Email: ");
            _Email = new Scanner(System.in).nextLine();

            if(authService.isEmailAvailable(_Email) ){
                System.out.println("Email already in use");
            }
            else break;
        }


        System.out.println("Password: ");
        String _Password = new Scanner(System.in).nextLine();
        System.out.println("Confirm Password: ");
        String _ConfirmPassword = new Scanner(System.in).nextLine();
        if (_Password.equals(_ConfirmPassword)) {
            try {
                currentUser = authService.register(_FirstName, _LastName, _Birth_date, _Email, _Password);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }






    public void mainPage() throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        while(true){
            System.out.println("MAIN PAGE");
            System.out.println("1. View Account");
            System.out.println("2. Transfer Money");
            System.out.println("3. View Transactions");
            System.out.println("4. Create a new Card");
            System.out.println("5. Logout");
            System.out.println("6. Exit");

            Scanner sc = new Scanner(System.in);

                String option = sc.nextLine();
                switch(option){
                    case "1":
                        viewAccount();
                        break;
                    case "2":
                        transferMoney();
                        break;
                    case "3":
                        viewTransactions();
                        break;
                    case "4":
                        createCard();
                        break;
                    case "5":
                        menu();
                        return;
                    case "6":
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Wrong input. Please try again");
                        break;
                }
        }


    }

    private void createCard() throws SQLException, NoSuchAlgorithmException {

        System.out.println("CREATE CARD");
        System.out.println("Card Name: ");
        String _CardName = new Scanner(System.in).nextLine();
        Card c = new Card(currentUser.getIdUser(), _CardName);
        currentUser.AddCard(c);
        System.out.println("Card created succesfully");
        return;
    }

    private void viewTransactions() throws SQLException {

        System.out.println("TRANSACTIONS");
        cardService.updateCardList(currentUser);
        for(Card c : currentUser.getCards()){
            System.out.println("Card Name: " + c.getCardName());
            System.out.println("Number: " + c.getNumber());
            System.out.println("Balance: " + c.getBalance());
            System.out.println("\n");
            System.out.println("Transactions: ");
            
            transactionService.printTransactionsIn(c);
            transactionService.printTransactionsOut(c);
            
        }


    }

    private void transferMoney() throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        while(true){
            System.out.println("TRANSFER MONEY");
            System.out.println("1. Choose from the AdressBook");
            System.out.println("2. Add a new contact");
            System.out.println("3. Back");
            Scanner sc = new Scanner(System.in);
            String option = sc.nextLine();
            switch(option){
                case "1":
                    chooseFromAdressBook();
                    break;
                case "2":
                    addNewContact();
                    break;
                case "3":
                    mainPage();
                    return;
                default:
                    System.out.println("Wrong input. Please try again");
                    break;
            }


        }
    }

    private void addNewContact() throws SQLException {
        System.out.println("ADD NEW CONTACT");
        System.out.println("Name: ");
        String _Name = new Scanner(System.in).nextLine();
        System.out.println("IBAN: ");
        String _IBAN = new Scanner(System.in).nextLine();
        AdressBook a = new AdressBook(currentUser, _Name, _IBAN);
        System.out.println("Contact added succesfully");
    }

    private Integer selectCard(){

        while(true){
            System.out.println("SELECT CARD");

            Scanner sc = new Scanner(System.in);
            Integer count = 0;
            for(Card c : currentUser.getCards()) {
                System.out.println(++count + ". ");
                System.out.println("Card Name: " + c.getCardName());
                System.out.println("Number: " + c.getNumber());
                System.out.println("Balance: " + c.getBalance());
                System.out.println("\n");
            }
            Integer option = Integer.parseInt(sc.nextLine());

            if(option > count || option < 1){
                System.out.println("Wrong input. Please try again");
                continue;
            }
            else{
                return option;
            }

        }
    }

    private void chooseFromAdressBook() throws SQLException, ClassNotFoundException {

        cardService.updateCardList(currentUser);
        Integer cardID = selectCard();

        System.out.println("CHOOSE FROM ADRESS BOOK");

        Set <AdressBook> adressBookstemp = new HashSet<>();
        adressBookstemp = AdressBooksService.readAdressBooks(currentUser);


        while(true){
            Integer i = 1;
            for(AdressBook a : adressBookstemp){
                System.out.println(i + ". " + a.getName());
                i++;
            }
            Integer option = new Scanner(System.in).nextInt();
            if(option > i || option < 1){
                System.out.println("Wrong input. Please try again");
                continue;
            }
            else{
                AdressBook a = (AdressBook) adressBookstemp.toArray()[option-1];

                System.out.println("Amount: ");
                Integer amount = new Scanner(System.in).nextInt();
                System.out.println("Are you sure you want to transfer " + amount + " to " + a.getName() + "?");
                System.out.println("Y/N: ");
                String confirm = new Scanner(System.in).nextLine();
                if(confirm.equals("Y") || confirm.equals("y")){

                    cardService.transferMoney(currentUser, cardID, a, amount);

                    return;
                }
                else{
                    System.out.println("Transfer cancelled");
                    return;
                }
            }
        }


    }


    private void viewAccount() throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {

        while(true){


            System.out.println("VIEW ACCOUNT");
            System.out.println("1. View Details");
            System.out.println("2. Change Password");
            System.out.println("3. See Cards");
            System.out.println("4. Delete Account");
            System.out.println("5. Back");
            Scanner sc = new Scanner(System.in);
            String option = sc.nextLine();
            switch(option){
                case "1":
                    viewDetails();
                    break;
                case "2":
                    changePassword();
                    break;
                case "3":
                    seeCards();
                    break;
                case "4":
                    deleteAccount();
                case "5":
                    mainPage();
                    return;
                default:
                    System.out.println("Wrong input. Please try again");
                    break;
            }
        }
    }

    private void deleteAccount () throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        while(true){
            System.out.println("DELETE ACCOUNT");
            System.out.println("Are you sure you want to delete your account? This action is permanent and can't be reversed! (y/n)");
            Scanner sc = new Scanner(System.in);
            String option = sc.nextLine();
            switch(option){
                case "y":
                {
                    while(true){
                        System.out.println("Enter your password: ");
                        Scanner sc2 = new Scanner(System.in);
                        String password = sc2.nextLine();
                        if(User.Crypt(password).equals(currentUser.getPassword())) {
                            UserService.delete(currentUser);
                            System.out.println("deleting");
                            menu();
                        }
                        else{
                            if(password.equals("1")){
                                return;
                            }
                            System.out.println("Wrong password. Try again or press 1 to cancel.");
                        }
                    }
                }

                case "n":
                    viewAccount();
                    return;
                default:
                    System.out.println("Wrong input. Please try again");
                    break;
            }
        }
    }


    private void seeCards() throws SQLException {

        cardService.updateCardList(currentUser);

        List<Card> temp = currentUser.getCards();
        Collections.sort(temp);
        currentUser.UpdateCards(temp);

        System.out.println("CARDS");
        for(Card c : currentUser.getCards()) {
            System.out.println("Card Name: " + c.getCardName());
            System.out.println("IBAN: " + c.getIBAN());
            System.out.println("Number: " + c.getNumber());
            System.out.println("Name: " + c.getName());
            System.out.println("Expiration Date: " + c.getMonth() + "/" + c.getYear());
            System.out.println("CVV: " + c.getCVV());
            System.out.println("Balance: " + c.getBalance());
            System.out.println("\n");
        }
    }

    //--updated for services
    private void changePassword() throws SQLException, NoSuchAlgorithmException {
        System.out.println("CHANGE PASSWORD");
        System.out.println("Old Password: ");
        String oldPassword = new Scanner(System.in).nextLine();
        System.out.println("New Password: ");
        String newPassword = new Scanner(System.in).nextLine();
        System.out.println("Confirm New Password: ");
        String confirmNewPassword = new Scanner(System.in).nextLine();

        if(userService.changePassword(currentUser, oldPassword, newPassword)){
            System.out.println("Password changed successfully");
        }
        else{
            System.out.println("Password change failed");
        }

    }

    private void viewDetails() throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        System.out.println("VIEW DETAILS");


        System.out.println("First Name: " + currentUser.getFirstName());
        System.out.println("Last Name: " + currentUser.getLastName());
        System.out.println("Birth Date: " + currentUser.getBirthDate());
        System.out.println("Email: " + currentUser.getEmail());



        System.out.println("1. Back");
        Scanner sc = new Scanner(System.in);
        while(true){
            String option = sc.nextLine();
            switch(option){
                case "1":
                    viewAccount();
                    return;
                default:
                    System.out.println("Wrong input. Please try again");
                    break;
            }
        }
    }



}
