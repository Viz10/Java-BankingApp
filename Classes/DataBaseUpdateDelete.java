import javax.swing.*;
import java.sql.*;
import java.util.*;

public class DataBaseUpdateDelete {

    private Connection con;

    public DataBaseUpdateDelete() {
//        try {
//            Class.forName("org.postgresql.Driver");
//
//            this.con = DriverManager.getConnection(url, username, password);
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//            System.out.println("Error opening DB");
//        }
    }

    public String getFullNameByUsername(String username) {
        String query = "SELECT DISTINCT person.full_name FROM person " +
                "INNER JOIN account ON person.id = account.person_id " +
                "INNER JOIN users ON users.id = account.user_id " +
                "WHERE users.username = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("full_name");
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


    public String getUsernameById(int userId) {
        String query = "SELECT username FROM users WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("username");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error executing the query");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error preparing the statement");
        }

        return null; /// not found
    }

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

    public String get_country_name(int countryId) {
        String countryName = null;
        String query = "SELECT name FROM country WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, countryId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    countryName = rs.getString("name");
                } else {
                    System.out.println("Country not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing the query or preparing the statement.");
        }

        return countryName;
    }

    public boolean check_admin_password(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ? AND type='ADMIN'";

        try (PreparedStatement ps = con.prepareStatement(query)) { /// use prepared when inserting with '?'

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) { /// executeQuery only for select only
                if (rs.next()) { /// if there exists a row
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

    public String getPersonAddress(int person_id) {

        if(person_id == -1){
            System.out.println("Person id is null");
            return null;
        }

        String query = "SELECT address FROM person WHERE id = ?";
        String address = null;

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, person_id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    address = rs.getString("address");
                } else {
                    System.out.println("Address not found for the given person ID.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error executing the query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error preparing the statement.");
        }

        return address;
    }

    /**
     *return current currencies in which user has saving accounts , if none => no available accounts
     */
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

    public boolean remove_savings_account(String full_name, String currency) {

        if (!currency.equals("USD") && !currency.equals("EUR") && !currency.equals("RON")) {
            System.out.println("Currency not supported");
            return false;
        }

        int id = get_person_id(full_name);
        if (id == -1) {
            JOptionPane.showMessageDialog(null, "Person not found");
            return false;
        }

        String query = "DELETE FROM saving_account WHERE person_id = ? and currency = ?::iso";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id);
            ps.setString(2, currency);

            int rowsAffected = ps.executeUpdate();  /// executeUpdate for delete/update operations
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Account deleted successfully");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No savings account found for the given person");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing the query");
        }
        return false;
    }

    public boolean remove_user_account(String full_name) {

        int id_person = get_person_id(full_name);
        int id_user = 0;

        if (id_person == -1) {
            System.out.println("Person not found");
            return false;
        }

        String query1 = "SELECT DISTINCT users.id FROM users " +
                "INNER JOIN account ON users.id = account.user_id " +
                "INNER JOIN person ON person.id = account.person_id " +
                "WHERE person.id = ?";

        try (PreparedStatement ps = con.prepareStatement(query1)) {
            ps.setInt(1, id_person);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    id_user = rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error executing the query1");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error preparing the statement for query1");
            return false;
        }

        String query2 = "DELETE FROM person WHERE id = ?"; // Foreign key constraints will delete related accounts
        String query3 = "DELETE FROM users WHERE id = ?";  // Ensure also user will be deleted

        // Deleting from person table
        try (PreparedStatement ps = con.prepareStatement(query2)) {
            ps.setInt(1, id_person);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Person deleted successfully");
            } else {
                System.out.println("No person found with the given id");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing the query2");
            return false;
        }

        // Deleting from users table
        try (PreparedStatement ps = con.prepareStatement(query3)) {
            ps.setInt(1, id_user);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User account deleted successfully");
                JOptionPane.showMessageDialog(null, "Account deleted successfully");
            } else {
                System.out.println("No user account found with the given id");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing the query3");
            return false;
        }
        return true;
    }

    public void block_account(String full_name, boolean block) {
        int id_person = get_person_id(full_name);

        if (id_person == -1) {
            JOptionPane.showMessageDialog(null, "Person not found.");
            return;
        }

        boolean isBlocked = false;
        String query = "SELECT blocked FROM account WHERE person_id = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id_person);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    isBlocked = rs.getBoolean("blocked");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error executing the query.");
                return;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error preparing the statement.");
            return;
        }

        if (isBlocked && block) {
            JOptionPane.showMessageDialog(null, "Account is already blocked.");
            return;
        } else if (!isBlocked && !block) {
            JOptionPane.showMessageDialog(null, "Account is not blocked.");
            return;
        }

        query = "UPDATE account SET blocked = ? WHERE person_id = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setBoolean(1, block);
            ps.setInt(2, id_person);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                JOptionPane.showMessageDialog(null, "Error updating account status.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error executing the update query.");
            return;
        }

        if(!get_currency(full_name).isEmpty()){ /// if it has saving accounts
            query = "UPDATE saving_account SET blocked = ? WHERE person_id = ?";

            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setBoolean(1, block);
                ps.setInt(2, id_person);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 0) {
                    JOptionPane.showMessageDialog(null, "Error updating saving account status.");
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error executing the update query.");
                return;
            }
        }

        JOptionPane.showMessageDialog(null, "Account status updated successfully.");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////// Balances/Freq./Int Rate

    public void update_savings_balance(String full_name, long new_balance, String currency) {
        int id_person = get_person_id(full_name);

        if (id_person == -1) {
            System.out.println("Person not found.");
            return;
        }

        if (!currency.equals("USD") && !currency.equals("EUR") && !currency.equals("RON")) {
            System.out.println("Currency not supported.");
            return;
        }


        String query = "UPDATE saving_account SET savings_balance = ? WHERE person_id = ? AND currency = ?::iso";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setLong(1, new_balance);
            ps.setInt(2, id_person);
            ps.setString(3, currency);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully updated savings balance");
            } else {
                System.out.println("Failed to update savings balance");

            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing the update query.");
        }
    }

    public void update_current_balance(String full_name, long new_balance, String currency) {

        int id_person = get_person_id(full_name);

        if (id_person == -1) {
            System.out.println("Person not found.");
            return;
        }

        if (!currency.equals("USD") && !currency.equals("EUR") && !currency.equals("RON")) {
            System.out.println("Currency not supported.");
            return;
        }

        String query = "UPDATE account SET balance = ? WHERE person_id = ? AND currency = ?::iso";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setLong(1, new_balance);
            ps.setInt(2, id_person);
            ps.setString(3, currency);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("successfully updated");
            } else {
                System.out.println("update failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing the update query.");
        }
    }

    public void update_payment_frequency(String full_name, String new_frequency, String currency) {
        int id_person = get_person_id(full_name);

        if (id_person == -1) {
            System.out.println("Person not found.");
            return;
        }

        if (!currency.equals("USD") && !currency.equals("EUR") && !currency.equals("RON")) {
            System.out.println("Currency not supported.");
            return;
        }

        String query = "UPDATE saving_account SET payment_frequency = ? WHERE person_id = ? AND currency = ?::iso";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, new_frequency);
            ps.setInt(2, id_person);
            ps.setString(3, currency);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null,"Succes");
            } else {
                JOptionPane.showMessageDialog(null,"Error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing the update query.");
        }
    }

    public void update_interest_rate(String full_name, String new_interest_rate, String currency) {
        int id_person = get_person_id(full_name);

        if (id_person == -1) {
            System.out.println("Person not found.");
            return;
        }

        if (!currency.equals("USD") && !currency.equals("EUR") && !currency.equals("RON")) {
            System.out.println("Currency not supported.");
            return;
        }

        String query = "UPDATE saving_account SET interest_rate = ? WHERE person_id = ? AND currency = ?::iso";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setDouble(1, Double.parseDouble(new_interest_rate));
            ps.setInt(2, id_person);
            ps.setString(3, currency);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null,"Succes");
            } else {
                JOptionPane.showMessageDialog(null,"Error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing the update query.");
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////// USER/PERSON DATA

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

    public boolean update_person_by(String full_name, String data, String type,StringBuilder builder) {
        int person_id = get_person_id(full_name);

        if (person_id == -1) {
            System.out.println("Person not found.");
            return false;
        }

        List<String> validTypes = Arrays.asList("first_name", "last_name", "address", "email", "phone", "fiscal_residence_id", "gen", "city", "street", "number");

        if (!validTypes.contains(type)) {
            JOptionPane.showMessageDialog(null, "Invalid type selected.");
            return false;
        }

        if (type.equals("first_name") || type.equals("last_name")) {
            String[] aux = full_name.split(" ");
            if (aux.length < 2) {
                JOptionPane.showMessageDialog(null, "Full name must contain at least a first name and a last name.");
                return false;
            }

            String firstName = aux[1];
            String lastName = aux[0];
            String updatedFullName;

            if (type.equals("first_name")) {
                data=RegisterComponents.capitalizeSegments(data);
                updatedFullName = lastName + " " + data;
            } else {
                data = data.toLowerCase();
                data = Character.toUpperCase(data.charAt(0)) + data.substring(1);
                updatedFullName = data + " " + firstName;
            }

            if(search_person_by(updatedFullName, "full_name") && updatedFullName.equals(full_name)) { /// same name
                builder.append("Result of texted name modification resembles the same full_name").append("\n");
                return false;
            }
            else if (search_person_by(updatedFullName, "full_name") && !updatedFullName.equals(full_name)) {
                builder.append("Person Name Already Exists").append("\n");
                return false;
            }

            data = updatedFullName;
            type = "full_name";
        } else if (type.equals("city") || type.equals("street") || type.equals("number")) {
            String currentAddress = getPersonAddress(person_id);
            if (currentAddress == null) {
                System.out.println("Address not found.");
                return false;
            }

            String city = "";
            String street = "";
            String number = "";
            String[] addressParts = currentAddress.split(",");

            for (String part : addressParts) { /// get the exact data from DB representation of the address
                if (part.trim().startsWith("city:")) {
                    city = part.substring(6).trim();
                } else if (part.trim().startsWith("street:")) {
                    street = part.substring(8).trim();
                } else if (part.trim().startsWith("number:")) {
                    number = part.substring(8).trim();
                }
            } /// now we have already located data

            if (type.equals("city")) { /// update the coresp. ones using the updated/unmodified data
                city = RegisterComponents.capitalizeSegments(data);
            } else if (type.equals("street")) {
                street = RegisterComponents.capitalizeSegments(data);
            } else if (type.equals("number")) {
                number = data;
            }

            data = "city: " + city + " ,street: " + street + " ,number: " + number;
            type = "address";
        }

        String query = "UPDATE person SET " + type + " = ? WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            if (type.equals("fiscal_residence_id")) {
                int fiscalResidenceId = get_country_id(data);
                ps.setInt(1, fiscalResidenceId); /// validation if existing is done elsewhere
            }
            else{
                ps.setString(1, data);
            }
            ps.setInt(2, person_id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(type + " updated successfully.");
                return true;
            } else {
                System.out.println("Failed to update.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing the query or preparing the statement.");
            return false;
        }
    }

    public boolean update_user_by_full_name(String full_name, String data, String type) {

        int id_person = get_person_id(full_name);
        int id_user= get_user_by_personId(id_person);

        if(id_user==-1){
            System.out.println("Person not found.");
            return false;
        }

        if (!type.equals("password") && !type.equals("username")) {
            JOptionPane.showMessageDialog(null, "Invalid user type.");
            return false;
        }

        String query2 = "UPDATE users SET " + type + " = ? WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(query2)) {
            ps.setString(1, data);
            ps.setInt(2, id_user);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User information updated successfully.");
                return true;
            } else {
                System.out.println("Failed to update.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing the query2 or preparing the statement.");
            return false;
        }
    }

    public int get_user_by_personId(int person_id) {
        if (person_id == -1) {
            System.out.println("Person not found.");
            return -1;
        }

        int user_id = -1;
        String query = "SELECT DISTINCT users.id FROM users " +
                "INNER JOIN account ON users.id = account.user_id " +
                "INNER JOIN person ON person.id = account.person_id " +
                "WHERE person.id = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, person_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user_id = rs.getInt(1);
                } else {
                    System.out.println("User not found.");
                    return -1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing the query or preparing the statement.");
            return -1;
        }

        return user_id;
    }

    public int get_person_by_userId(int user_id) {
        if (user_id == -1) {
            System.out.println("User not found.");
            return -1;
        }

        int person_id = -1;
        String query = "SELECT DISTINCT person.id FROM person " +
                "INNER JOIN account ON person.id = account.person_id " +
                "INNER JOIN users ON users.id = account.user_id " +
                "WHERE users.id = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, user_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    person_id = rs.getInt(1);
                } else {
                    System.out.println("Person not found.");
                    return -1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing the query or preparing the statement.");
            return -1;
        }

        return person_id;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String balanceFormatted(long balance) {
        if (balance < 0) {
            System.out.println("Balance is negative");
            return null;
        }

        if (balance == 0) {
            return "0.000000";
        }

        if (balance < 1000000) { /// less than 1
            return "0." + String.format("%06d", balance);
        }

        StringBuilder builder = new StringBuilder();
        char[] a = String.valueOf(balance).toCharArray();
        int len = a.length, cnt = 1;

        for (int i = len - 1; i >= 0; i--) {
            builder.insert(0, a[i]);
            if (cnt == 6) { /// fractional part
                builder.insert(0, ".");
            }
            if (cnt>6 && (cnt - 6) % 3 == 0  && cnt != len) { /// from 1000 to 1000 must insert a ',' between them
                builder.insert(0, ",");
            }
            cnt++;
        }
        String rez=builder.toString();
        a=rez.toCharArray();

        int i=rez.length()-1;
        for(;i>=0;--i){
            if(a[i]!='0' || a[i-2]=='.'){
                break;
            }
        }

        return builder.toString().substring(0,i+1);

    }

    public static String cropMilliseconds(String time) {
        if (time == null || time.isEmpty()) {
            return time;
        }

        int dotIndex = time.indexOf('.');
        if (dotIndex != -1) { /// if exists
            return time.substring(0, dotIndex);
        }

        return time; /// if not , return regular time
    }

    public Map<String, String> getUserData(String full_name, String type) {

        if (!type.equals("CUSTOMER") && !type.equals("ADMIN")) {
            System.out.println("Invalid user type.");
            return null;
        }

        int personId = get_person_id(full_name);

        if (personId == -1) {
            System.out.println("Person not found.");
            return null;
        }

        int user_id = get_user_by_personId(personId);

        String queryForPassword = "SELECT password,username FROM users WHERE id = ?";
        String storedPassword = "";
        String storedusername = "";

        if (personId == -1) {
            System.out.println("User not found.");
            return null;
        }

        try (PreparedStatement ps = con.prepareStatement(queryForPassword)) {
            ps.setInt(1, user_id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    storedPassword = rs.getString("password");
                    storedusername = rs.getString("username");
                } else {
                    System.out.println("Not found.");
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing the query for password.");
            return null;
        }

        Map<String, String> userData = new HashMap<>();

        String query = "SELECT full_name, address, email, phone, fiscal_residence_id, gen FROM person WHERE id= ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, personId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String maskedPassword;
                    if (type.equals("CUSTOMER")) {
                        maskedPassword = storedPassword.charAt(0) + "*".repeat(storedPassword.length() - 1); /// user will have the password in hidden format , while admin will see it full
                    } else {
                        maskedPassword = storedPassword;
                    }
                    userData.put("username", storedusername); /// fetch data to key in map
                    userData.put("password", maskedPassword);
                    userData.put("full name", rs.getString("full_name"));
                    userData.put("email", rs.getString("email"));
                    userData.put("address", rs.getString("address"));
                    userData.put("phone number", rs.getString("phone"));
                    userData.put("country", get_country_name(rs.getInt("fiscal_residence_id")));
                    userData.put("gender", rs.getString("gen"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing the main query.");
        }

        return userData;
    }

    public Map<String, String> getAccountData(String full_name,String currency) {

        if (!currency.equals("USD") && !currency.equals("EUR") && !currency.equals("RON")) {
            System.out.println("Currency not supported.");
            return null;
        }

        int rez=get_person_id(full_name);

        if(rez==-1){
            System.out.println("Person not found.");
            return null;
        }

        Map<String, String> accountData = new HashMap<>();
        String query = "SELECT account_id, blocked, balance,currency, iban, created_at, updated_at FROM account WHERE person_id = ? and currency = ?::iso";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, rez);
            ps.setString(2, currency);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    accountData.put("account id", rs.getString("account_id"));
                    accountData.put("blocked", Boolean.toString(rs.getBoolean("blocked")));
                    accountData.put("balance", balanceFormatted(rs.getLong("balance")));
                    accountData.put("currency", rs.getString("currency"));
                    accountData.put("iban", rs.getString("iban"));
                    accountData.put("created at",cropMilliseconds(rs.getTimestamp("created_at").toString()));
                    accountData.put("updated at",cropMilliseconds(rs.getTimestamp("updated_at").toString()));
                } else {
                    System.out.println("Account not found.");
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing the query or preparing the statement.");
            return null;
        }

        return accountData;
    }

    public Map<String, String> getSavingsAccountData(String full_name,String currency) {

        if (!currency.equals("USD") && !currency.equals("EUR") && !currency.equals("RON")) {
            System.out.println("Currency not supported.");
            return null;
        }

        int rez=get_person_id(full_name);

        if(rez==-1){
            System.out.println("Person not found.");
            return null;
        }

        Map<String, String> accountData = new HashMap<>();
        String query = "SELECT account_id, blocked, savings_balance,currency, iban,interest_rate,payment_frequency, created_at, updated_at FROM saving_account WHERE person_id = ? and currency = ?::iso";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, rez);
            ps.setString(2, currency);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    accountData.put("account id", rs.getString("account_id"));
                    accountData.put("blocked", Boolean.toString(rs.getBoolean("blocked")));
                    accountData.put("balance",balanceFormatted(rs.getLong("savings_balance")));
                    accountData.put("currency", rs.getString("currency"));
                    accountData.put("iban", rs.getString("iban"));
                    accountData.put("annual interest rate", String.valueOf(rs.getDouble("interest_rate")));
                    accountData.put("payment frequency", rs.getString("payment_frequency"));
                    accountData.put("created at",cropMilliseconds(rs.getTimestamp("created_at").toString()));
                    accountData.put("updated at",cropMilliseconds(rs.getTimestamp("updated_at").toString()));
                } else {
                    System.out.println("Account not found.");
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing the query or preparing the statement.");
            return null;
        }

        return accountData;
    }

    public ArrayList<TransferClass> getPastTransfersSent(String username, String currency) {

        if (!currency.equals("USD") && !currency.equals("EUR") && !currency.equals("RON")) {
            System.out.println("Currency not supported.");
            return null;
        }

        int rez = get_user_id(username);

        if (rez == -1) {
            System.out.println("Sender user not found.Error");
            return null;
        }

        ArrayList<TransferClass> list = new ArrayList<>();

        String query = "SELECT user_id_start, user_id_end, amount, currency_from, currency_with, currency_to, transfer_timestamp " +
                "FROM past_transfers WHERE (user_id_start = ? or user_id_end = ?) AND (currency_from = ?::iso or currency_to = ?::iso) order by transfer_timestamp desc";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, rez);
            ps.setInt(2, rez);
            ps.setString(3, currency);
            ps.setString(4, currency);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    /// user id end (if to whom it has been sent) or user id start (if current person has been given money) doesn`t exist anymore : display deleted user

                    String userfrom = getUsernameById(rs.getInt("user_id_start"));
                    String userto = getUsernameById(rs.getInt("user_id_end"));

                    long amount = rs.getLong("amount");
                    String currencyfrom = rs.getString("currency_from");
                    String currencyto = rs.getString("currency_to");
                    String currencywith = rs.getString("currency_with");
                    String time = cropMilliseconds(rs.getTimestamp("transfer_timestamp").toString());

                    TransferClass transferClass = new TransferClass(amount, userfrom, userto, currencyfrom, currencywith, currencyto);
                    transferClass.setTime(time);
                    transferClass.setFromAccountName(getFullNameByUsername(userfrom));
                    transferClass.setToAccountName(getFullNameByUsername(userto));
                    
                    list.add(transferClass);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing the query or preparing the statement.");
            return null;
        }

        return list;
    }


}
