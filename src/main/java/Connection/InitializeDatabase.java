package Connection;

import java.sql.SQLException;

public class InitializeDatabase extends ConnectionString{

    private static String createUsersTable = "CREATE TABLE IF NOT EXISTS users (\n"
            + "iduser INT AUTO_INCREMENT PRIMARY KEY,\n"
            + "firstname VARCHAR(45) NOT NULL,\n"
            + "lastname VARCHAR(45) NOT NULL,\n"
            + "birthdate DATE NOT NULL,\n"
            + "email VARCHAR(45) NOT NULL,\n"
            + "password VARCHAR(45) NOT NULL\n"
            + ");";

    private static String createTransactionsTable = "CREATE TABLE IF NOT EXISTS transactions (\n"
            + "idtransaction INT AUTO_INCREMENT PRIMARY KEY,\n"
            + "idcardoutgoing INT NULL,\n"
            + "idcardincoming INT NULL,\n"
            + "amount DECIMAL(10,2) NOT NULL,\n"
            + "date DATE NOT NULL,\n"
            + "FOREIGN KEY (idcardoutgoing) REFERENCES cards(idcard) ON DELETE SET NULL,\n"
            + "FOREIGN KEY (idcardincoming) REFERENCES cards(idcard) ON DELETE SET NULL\n"
            + ");";

    private static String createCardsTable = "CREATE TABLE IF NOT EXISTS cards (\n"
            + "idcard INT AUTO_INCREMENT PRIMARY KEY,\n"
            + "iduser INT NOT NULL,\n"
            + "name VARCHAR(45) NOT NULL,\n"
            + "iban VARCHAR(45) NOT NULL,\n"
            + "cardnumber VARCHAR(45) NOT NULL,\n"
            + "month INT NOT NULL,\n"
            + "year INT NOT NULL,\n"
            + "cvv INT NOT NULL,\n"
            + "balance DECIMAL(10,2) NOT NULL,\n"
            + "cardname VARCHAR(45) NOT NULL,\n"
            + "FOREIGN KEY (idUser) REFERENCES users(idUser)\n"
            + ");";
    private static String createAdressBooksTable = "CREATE TABLE IF NOT EXISTS adressbooks (\n"
            + "idadressbook INT AUTO_INCREMENT PRIMARY KEY,\n"
            + "iduser INT NOT NULL,\n"
            + "name VARCHAR(45) NOT NULL,\n"
            + "iban VARCHAR(45) NOT NULL,\n"
            + "FOREIGN KEY (idUser) REFERENCES users(idUser)"
            + ");";

    // Pentru toti parola este 8287458823facb8ff918dbfabcd22ccb , adica Crypt("parola")
    private static String insertUser1 = "INSERT INTO users (firstname, lastname, birthdate, email, password) VALUES " +
            "('Ion', 'Popescu', '1990-05-15', 'ion.popescu@email.com', '8287458823facb8ff918dbfabcd22ccb');";

    private static String insertUser2 = "INSERT INTO users (firstname, lastname, birthdate, email, password) VALUES " +
            "('Maria', 'Ionescu', '1985-03-22', 'maria.ionescu@email.com', '8287458823facb8ff918dbfabcd22ccb');";

    private static String insertUser3 = "INSERT INTO users (firstname, lastname, birthdate, email, password) VALUES " +
            "('Andrei', 'Gheorghe', '1992-11-08', 'andrei.gheorghe@email.com', '8287458823facb8ff918dbfabcd22ccb');";

    private static String insertUser4 = "INSERT INTO users (firstname, lastname, birthdate, email, password) VALUES " +
            "('Elena', 'Dumitrescu', '1988-07-30', 'elena.dumitrescu@email.com', '8287458823facb8ff918dbfabcd22ccb');";

    // Insert statements pentru tabela cards
    private static String insertCard1 = "INSERT INTO cards (iduser, name, iban, cardnumber, month, year, cvv, balance, cardname) VALUES " +
            "(1, 'Card Principal', 'RO49AAAA1B31007593840000', '4532123456789012', 12, 2028, 123, 2500.50, 'Ion Popescu');";

    private static String insertCard2 = "INSERT INTO cards (iduser, name, iban, cardnumber, month, year, cvv, balance, cardname) VALUES " +
            "(1, 'Card Economii', 'RO49AAAA1B31007593840001', '4532123456789013', 8, 2027, 456, 5000.00, 'Ion Popescu');";

    private static String insertCard3 = "INSERT INTO cards (iduser, name, iban, cardnumber, month, year, cvv, balance, cardname) VALUES " +
            "(2, 'Card Salary', 'RO49BBBB1B31007593840002', '4532123456789014', 10, 2029, 789, 1200.75, 'Maria Ionescu');";

    private static String insertCard4 = "INSERT INTO cards (iduser, name, iban, cardnumber, month, year, cvv, balance, cardname) VALUES " +
            "(3, 'Card Business', 'RO49CCCC1B31007593840003', '4532123456789015', 6, 2026, 012, 8500.25, 'Andrei Gheorghe');";

    private static String insertCard5 = "INSERT INTO cards (iduser, name, iban, cardnumber, month, year, cvv, balance, cardname) VALUES " +
            "(4, 'Card Personal', 'RO49DDDD1B31007593840004', '4532123456789016', 4, 2028, 345, 3200.80, 'Elena Dumitrescu');";

    // Insert statements pentru tabela transactions
    private static String insertTransaction1 = "INSERT INTO transactions (idcardoutgoing, idcardincoming, amount, date) VALUES " +
            "(1, 3, 250.00, '2024-12-01');";

    private static String insertTransaction2 = "INSERT INTO transactions (idcardoutgoing, idcardincoming, amount, date) VALUES " +
            "(2, 4, 500.00, '2024-12-05');";

    private static String insertTransaction3 = "INSERT INTO transactions (idcardoutgoing, idcardincoming, amount, date) VALUES " +
            "(4, 1, 150.75, '2024-12-10');";

    private static String insertTransaction4 = "INSERT INTO transactions (idcardoutgoing, idcardincoming, amount, date) VALUES " +
            "(3, 5, 300.50, '2024-12-15');";

    private static String insertTransaction5 = "INSERT INTO transactions (idcardoutgoing, idcardincoming, amount, date) VALUES " +
            "(5, 2, 75.25, '2024-12-20');";

    private static String insertTransaction6 = "INSERT INTO transactions (idcardoutgoing, idcardincoming, amount, date) VALUES " +
            "(1, 4, 1000.00, '2024-12-25');";



    private static String dropUsersTable = "DROP TABLE IF EXISTS users;";

    private static String dropTransactionsTable = "DROP TABLE IF EXISTS transactions;";

    private static String dropCardsTable = "DROP TABLE IF EXISTS cards;";

    private static String dropAdressBooksTable = "DROP TABLE IF EXISTS adressbooks;";








    private static void createTables() throws SQLException {
        c.createStatement().executeUpdate(createUsersTable);
        c.createStatement().executeUpdate(createCardsTable);
        c.createStatement().executeUpdate(createTransactionsTable);
        c.createStatement().executeUpdate(createAdressBooksTable);
    }
    
    private static void dropTables() throws SQLException {
        c.createStatement().executeUpdate(dropAdressBooksTable);
        c.createStatement().executeUpdate(dropTransactionsTable);
        c.createStatement().executeUpdate(dropCardsTable);
        c.createStatement().executeUpdate(dropUsersTable);

    }

    private static void addData() throws SQLException {
        c.createStatement().executeUpdate(createUsersTable);
        c.createStatement().executeUpdate(createCardsTable);
        c.createStatement().executeUpdate(createTransactionsTable);
        c.createStatement().executeUpdate(createAdressBooksTable);

        c.createStatement().executeUpdate(insertUser1);
        c.createStatement().executeUpdate(insertUser2);
        c.createStatement().executeUpdate(insertUser3);
        c.createStatement().executeUpdate(insertUser4);

        c.createStatement().executeUpdate(insertCard1);
        c.createStatement().executeUpdate(insertCard2);
        c.createStatement().executeUpdate(insertCard3);
        c.createStatement().executeUpdate(insertCard4);
        c.createStatement().executeUpdate(insertCard5);

        c.createStatement().executeUpdate(insertTransaction1);
        c.createStatement().executeUpdate(insertTransaction2);
        c.createStatement().executeUpdate(insertTransaction3);
        c.createStatement().executeUpdate(insertTransaction4);
        c.createStatement().executeUpdate(insertTransaction5);
        c.createStatement().executeUpdate(insertTransaction6);

    }

    public InitializeDatabase(boolean creeazaTabele, boolean genereazaInserturi){
        try{
            if (creeazaTabele) {
                dropTables();
                createTables();
            }

            if(genereazaInserturi){
                addData();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }


    }

}
