import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DataBaseOp {

    private Connection con;

    public DataBaseOp() { /// constructor , deal with connectivity
//        try {
//            Class.forName("org.postgresql.Driver");
//
//            this.con = DriverManager.getConnection(url, username, password);
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//            System.out.println("Error opening DB");
//        }
    }

    /// WHAT IF USER/ADMIN HAVE SAME CREDENTIALS? => there won't be the same username twice in the whole username table ,
    ///  irl this situation doesn`t happen anyway, admin have different application

    public String check_login_user_password(String texted_username, String texted_password) {

        String result = "User not found or incorrect password.";
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) { /// use prepared when inserting with '?'

            ps.setString(1, texted_username); /// fill in the data
            ps.setString(2, texted_password);

            try (ResultSet rs = ps.executeQuery()) { /// executeQuery only for select only
                if (rs.next()) { /// if there exists a row
                    String userType = rs.getString("type");
                    return userType;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error executing the query");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error preparing the statement");
        }

        return result; /// return error message to be displayed
    }

    public boolean account_is_blocked(String user) {
        int id = get_user_id(user);

        if(id==-1){
            System.out.println("User not found");
            return false;
        }

        String query = "SELECT blocked FROM account WHERE user_id = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    boolean isBlocked = rs.getBoolean("blocked");
                    if (isBlocked) {
                        return true;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error executing the query");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error preparing the statement");
        }
        return false;
    }

    public ArrayList<String> listUsers() {
        ArrayList<String> result = new ArrayList<>();

        try (Statement st = con.createStatement()) { /// using Statement as it doesnt involve '?'

            try (ResultSet sr = st.executeQuery("SELECT * FROM users")) {
                while (sr.next()) {
                    result.add(sr.getString("username"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error executing query");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error creating statement");
        }

        return result; /// returns an array of strings containing all , null if invalid
    }

    public String getFullNameById(int personId) {
        String fullName = null;
        String query = "SELECT full_name FROM person WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, personId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    fullName = rs.getString("full_name");
                } else {
                    System.out.println("Person not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing the query or preparing the statement.");
        }

        return fullName;
    }

    ///////////////////////////////////////// GET/CHECK

    public int get_country_id(String country) {

        String query = "SELECT * FROM country WHERE name = ? ";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, country);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int rez = Integer.parseInt(rs.getString("id"));
                    return rez;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error executing the query");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error preparing the statement");
        }

        return -1;
    }

    public int get_user_id(String user) {

        String query = "SELECT * FROM users WHERE username = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, user);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int rez = Integer.parseInt(rs.getString("id"));
                    return rez;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error executing the query");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error preparing the statement");
        }

        return -1;

    }

    public int get_person_id(String full_name) {

        String query = "SELECT * FROM person WHERE full_name = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, full_name);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int rez = Integer.parseInt(rs.getString("id"));
                    return rez;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error executing the query");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error preparing the statement");
        }

        return -1;

    }

    public String check_account_id(String account_id) {

        String query = "SELECT * FROM account WHERE account_id = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, account_id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("account_id");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error executing the query");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error preparing the statement");
        }

        return null;

    } /// when generating a new account_id or IBAN, must check if it appears already in DB

    public boolean search_account_by_IBAN(String IBAN) {
        String query = "SELECT * FROM account WHERE iban = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, IBAN);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing the query or preparing the statement");
        }

        return false;
    }

    public boolean check_username(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error executing the query");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error preparing the statement");
        }
        return false;
    }

    public void remove_person_by_full_name(String full_name) {
        int id_person = get_person_id(full_name);

        if (id_person == -1) {
            System.out.println("Person not found");
            return;
        }

        String query1 = "DELETE FROM person WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(query1)) {
            ps.setInt(1, id_person);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Person deleted successfully");
            } else {
                System.out.println("No person found ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing the query");
        }
    }

    public void remove_user_by_username(String username) {

        int id_user =get_user_id(username);

        if (id_user == -1) {
            System.out.println("User not found");
            return;
        }

        String query2 = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(query2)) {
            ps.setInt(1, id_user);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User account deleted successfully");
            } else {
                System.out.println("No user account found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing the query");
        }
    }

    /**
     * @param data actual value
     * @param type represents what kind of data we search
     * @return true if data is found in the DB
     */
    public  boolean search_person_by(String data, String type) {

        List<String> validTypes = Arrays.asList("full_name", "address", "email", "phone", "fiscal_residence_id", "gen");

        if (!validTypes.contains(type)) {
            JOptionPane.showMessageDialog(null, "Invalid type selected.");
            return false;
        }

        String query = "SELECT * FROM person WHERE " + type + " = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, data);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing the query or preparing the statement");
        }
        return false;
    }

    ///////////////////////////////////// INSERT USER/PERSON

    public boolean insertUser(String username, String password, String type) {
        String query = "INSERT INTO users (username, password, type) VALUES (?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, type);

            ps.executeUpdate();
            System.out.println("User inserted successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error inserting the user");
            return false;
        }
        return true;
    }

    public boolean insertPerson(String full_name, String address, String email, String phone, String country, String gen) {
        int country_id = get_country_id(country);

        if (country_id == -1) {
            System.out.println("Error founding country from table");
            return false;
        }

        String query = "INSERT INTO person (full_name, address, email, phone, fiscal_residence_id, gen) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, full_name);
            ps.setString(2, address);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setInt(5, country_id);
            ps.setString(6, gen);

            ps.executeUpdate();
            System.out.println("Person inserted successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error inserting the person");
            return false;
        }
        return true;
    }

    ////////////////////////////////////// CURRENT ACCOUNT

    public  String generateAccountNumber(StringBuilder numberBuilder, Random random) {
        numberBuilder.setLength(0); /// clear the builder
        for (int i = 0; i < 10; i++) {
            int digit = random.nextInt(10);
            numberBuilder.append(digit);
        }
        return "ACCT" + numberBuilder;
    }

    private String generateUniqueAccountNumber(StringBuilder numberBuilder, Random random, int maxRetries, String currency, String... existingNumbers) {
        String accountNumber;
        int retryCount = 0;

        do {
            if (retryCount++ > maxRetries) {
                System.out.println("Failed to generate unique account number for " + currency);
                return null;
            }
            accountNumber = generateAccountNumber(numberBuilder, random);
        } while (check_account_id(accountNumber)!=null || Arrays.asList(existingNumbers).contains(accountNumber));

        return accountNumber;
    }

    public  String generateIBAN(StringBuilder numberBuilder, Random random) {
        numberBuilder.setLength(0);
        StringBuilder abcd = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            char ch = (char) (random.nextInt(26) + 'A');
            abcd.append(ch);
        }

        for (int i = 0; i < 16; i++) {
            int digit = random.nextInt(10);
            numberBuilder.append(digit);
        }
        return "RO" + abcd + numberBuilder;
    }

    private String generateUniqueIBAN(StringBuilder numberBuilder, Random random, int maxRetries, String... existingIBANs) {
        String iban;
        int retryCount = 0;

        do {
            if (retryCount++ > maxRetries) {
                System.out.println("Failed to generate unique IBAN");
                return null;
            }
            iban = generateIBAN(numberBuilder, random);
        } while (search_account_by_IBAN(iban) || Arrays.asList(existingIBANs).contains(iban));

        return iban;
    }

    public boolean insertAccount(String username, String full_name) {
        int user_id = get_user_id(username);
        int person_id = get_person_id(full_name);

        if (user_id == -1 || person_id == -1) {
            System.out.println("Not found person/user in table");
            return false;
        }

        StringBuilder numberBuilder = new StringBuilder();
        Random random = new Random();
        String accountNumberRON, accountNumberUSD, accountNumberEUR;
        String ibanRON, ibanUSD, ibanEUR;
        int maxRetries = 100;

        accountNumberRON = generateUniqueAccountNumber(numberBuilder, random, maxRetries, "RON");
        accountNumberUSD = generateUniqueAccountNumber(numberBuilder, random, maxRetries, "USD", accountNumberRON);
        accountNumberEUR = generateUniqueAccountNumber(numberBuilder, random, maxRetries, "EUR", accountNumberRON, accountNumberUSD);

        ibanRON = generateUniqueIBAN(numberBuilder, random, maxRetries);
        ibanUSD = generateUniqueIBAN(numberBuilder, random, maxRetries, ibanRON);
        ibanEUR = generateUniqueIBAN(numberBuilder, random, maxRetries, ibanRON, ibanUSD);

        if (accountNumberRON == null || accountNumberUSD == null || accountNumberEUR == null ||
                ibanRON == null || ibanUSD == null || ibanEUR == null) {
            return false;
        }

        String query = "INSERT INTO account (account_id, currency, iban, user_id, person_id) VALUES " +
                "(?, ?::iso, ?, ?, ?)," +
                "(?, ?::iso, ?, ?, ?)," +
                "(?, ?::iso, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            // Insert for RON
            ps.setString(1, accountNumberRON);
            ps.setString(2, "RON");
            ps.setString(3, ibanRON);
            ps.setInt(4, user_id);
            ps.setInt(5, person_id);

            // Insert for USD
            ps.setString(6, accountNumberUSD);
            ps.setString(7, "USD");
            ps.setString(8, ibanUSD);
            ps.setInt(9, user_id);
            ps.setInt(10, person_id);

            // Insert for EUR
            ps.setString(11, accountNumberEUR);
            ps.setString(12, "EUR");
            ps.setString(13, ibanEUR);
            ps.setInt(14, user_id);
            ps.setInt(15, person_id);

            ps.executeUpdate();
            System.out.println("Account inserted successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error inserting into account table");
            return false;
        }

        return true;
    }

    ///////////////////////////////////// SAVING ACCOUNT

    public ArrayList<String> get_currency(String full_name) {

        int id_person = get_person_id(full_name);
        if (id_person == -1) {
            System.out.println("Person not found");
            return null;
        }

        ArrayList<String> list = new ArrayList<>();

        String query = "select saving_account.currency from saving_account where saving_account.person_id = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id_person);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("currency"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error executing the query1");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error preparing the statement for query1");
            return null;
        }
        return list;
    }

    public boolean check_existing_SavingAccount(String user,String currency){

        int user_id = get_user_id(user);

        if(user_id==-1){
            System.out.println("User not found");
            return false;
        }

        String query = "SELECT * FROM saving_account WHERE user_id = ? and currency = ?::iso";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, user_id);
            ps.setString(2, currency);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing the query or preparing the statement");
        }

        return false;
    }

    public boolean insertSavingAccount(String username, String full_name,long money,String currency,double interest_rate,String paymennt_frequency) {

        if(account_is_blocked(username)) {
            JOptionPane.showMessageDialog(null,"Account is blocked");
            return false;
        }

        if(!currency.equals("USD") && !currency.equals("EUR") && !currency.equals("RON")){
            System.out.println("Currency not supported");
            return false;
        }

        int user_id = get_user_id(username);
        int person_id = get_person_id(full_name);

        if (user_id == -1 || person_id == -1) {
            System.out.println("Not found person/user in table");
            return false;
        }

        if(check_existing_SavingAccount(username,currency)){
            JOptionPane.showMessageDialog(null,"Account with this currency already exists");
            return false;
        }

        StringBuilder numberBuilder = new StringBuilder();
        Random random = new Random();
        String accountNumber;
        String iban;
        int maxRetries = 100;

        accountNumber = generateUniqueAccountNumber(numberBuilder, random, maxRetries, currency);
        iban = generateUniqueIBAN(numberBuilder, random, maxRetries);

        if (accountNumber == null || iban == null) {
            return false;
        }

        String query = "INSERT INTO saving_account (account_id, currency,savings_balance, iban, interest_rate , payment_frequency , user_id, person_id) VALUES (?, ?::iso, ?,?,?,?,?,?)";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, accountNumber);
            ps.setString(2, currency);
            ps.setLong(3, money);
            ps.setString(4, iban);
            ps.setDouble(5, interest_rate);
            ps.setString(6, paymennt_frequency);
            ps.setInt(7, user_id);
            ps.setInt(8, person_id);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Saving account inserted successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error inserting into account table");
            return false;
        }

        return true;
    }
}
