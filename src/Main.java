import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import Connection.InitializeDatabase;
import Models.Menu;


import java.lang.String;

public class Main{

    public static void main(String[] args) throws FileNotFoundException, NoSuchAlgorithmException, SQLException {

        Menu m = new Menu();
        m.menu();
    }
}