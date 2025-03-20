
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;


public class DataBaseMoney {

    private Connection con;

    public DataBaseMoney() {
//        try {
//            Class.forName("org.postgresql.Driver");
//           
//            this.con = DriverManager.getConnection(url, username, password);
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//            System.out.println("Error opening DB");
//        }
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

    public long getBalanceBy(String user, String currency) {
        if (!currency.equals("USD") && !currency.equals("EUR") && !currency.equals("RON")) {
            System.out.println("Unsupported currency: " + currency);
            return -1;
        }

        String query = "SELECT balance FROM account WHERE user_id = ? AND currency = ?::iso";
        int user_id = get_user_id(user);
        if (user_id == -1) {
            System.out.println("User not found");
            return -1;
        }

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, user_id);
            ps.setString(2, currency);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("balance");
                } else {
                    System.out.println("No account found for user with currency: " + currency);
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
 /// CROP TRAILING 00..
        return builder.toString().substring(0,i+1);

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

    public String getUsernameByFullName(String fullName) {
        String query = "SELECT DISTINCT users.username FROM users " +
                "INNER JOIN account ON users.id = account.user_id " +
                "INNER JOIN person ON person.id = account.person_id " +
                "WHERE person.full_name = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, fullName);

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

        return null;
    }

    public boolean insertPastTransfers(TransferClass transferClass) {

        int from_id= get_user_id(transferClass.getFromAccountUsername()); /// already verified in transfer method
        int to_id = get_user_id(transferClass.getToAccountUsername());

        String query = "INSERT INTO past_transfers (user_id_start, user_id_end, amount, currency_from , currency_with , currency_to) VALUES (?,?,?,?::iso,?::iso,?::iso)";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, from_id);
            ps.setInt(2, to_id);
            ps.setLong(3, transferClass.getAmount());
            ps.setString(4, transferClass.getCurrencyFrom());
            ps.setString(5, transferClass.getCurrencyWith());
            ps.setString(6, transferClass.getCurrencyTo());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error inserting into account ");
            return false;
        }

        return true;
    }

    public long getSavingsBalanceBy(String user, String currency) {
        if (!currency.equals("USD") && !currency.equals("EUR") && !currency.equals("RON")) {
            System.out.println("Unsupported currency: " + currency);
            return -1;
        }

        int user_id = get_user_id(user);
        if (user_id == -1) {
            System.out.println("User not found");
            return -1;
        }

        String query = "SELECT savings_balance FROM saving_account WHERE user_id = ? AND currency = ?::iso";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, user_id);
            ps.setString(2, currency);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("savings_balance");
                } else {
                    System.out.println("No savings account found for user with currency: " + currency);
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

    public void update_savings_balance_after_interest(int id, long new_balance, String currency) {


        String query = "UPDATE saving_account SET savings_balance = ?,last_added_interest_rate=now() WHERE user_id = ? AND currency = ?::iso";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setLong(1, new_balance);
            ps.setInt(2, id);
            ps.setString(3, currency);

           ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean savings_after_interest(){

        String query = "SELECT blocked,savings_balance, currency, interest_rate,last_added_interest_rate,user_id FROM saving_account";

        try (Statement ps = con.createStatement()) {

            try (ResultSet rs = ps.executeQuery(query)) {
                while(rs.next()){
                    boolean blocked=rs.getBoolean("blocked");
                    if(!blocked){
                        long savings_balance=rs.getLong("savings_balance");
                        double interest_rate=rs.getDouble("interest_rate");

                        Timestamp timestamp=rs.getTimestamp("last_added_interest_rate");
                        int id=rs.getInt("user_id");
                        String currency=rs.getString("currency");

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
                        String updateDay = dateFormat.format(timestamp); /// day with last update

                        LocalDate currentDate = LocalDate.now();
                        String currentDay=String.format("%02d", currentDate.getDayOfMonth()); /// get day gives 1 2 3 , we want 01,02...

                        if (!currentDay.equals(updateDay)) { /// it checks if already has been updated today , if not update

                            long interest_rate_scaled = (long) (interest_rate * 1000000); /// convert 0.02( 2% ) to 20000 interest rate , long format

                            long daily_interest_rate_scaled = interest_rate_scaled / 365; /// daily rate

                            long interest_earned_day = (daily_interest_rate_scaled * savings_balance) / 1000000; /// adjust scaling , this is amount earned from that previous day

                            savings_balance += interest_earned_day; /// update currency adding new part

                            update_savings_balance_after_interest(id, savings_balance, currency);
                        }
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing the query or preparing the statement.");
            return false;
        }

        return true;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////// FRIENDS


    public boolean insertFriends(int user1, int user2) {

        String query = "INSERT INTO friends (id_friend1, id_friend2) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, user1);
            ps.setInt(2, user2);

            ps.executeUpdate();
            System.out.println("Person inserted successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error inserting the person");
            return false;
        }
        return true;
    }

    public ArrayList<String> getFriendUsernames(int userId) {
        ArrayList<String> usernames = new ArrayList<>();
        String query = "SELECT DISTINCT b.username " +
                "FROM users a " +
                "INNER JOIN friends ON a.id = friends.id_friend1 " +
                "INNER JOIN users b ON friends.id_friend2 = b.id " +
                "WHERE friends.id_friend1 = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    usernames.add(rs.getString("username"));
                }
            }

            System.out.println("Usernames retrieved successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving usernames");
            return null;
        }
        return usernames;
    }

    public ArrayList<String> getAllFriends(int userId) {
        ArrayList<String> friendsInfo = new ArrayList<>();
        String query = "SELECT DISTINCT " +
                "b.username, pers.full_name " +
                "FROM users a " +
                "INNER JOIN friends ON a.id = friends.id_friend1 " +
                "INNER JOIN users b ON friends.id_friend2 = b.id " +
                "INNER JOIN account acc ON b.id = acc.user_id " +
                "INNER JOIN person pers ON pers.id = acc.person_id " +
                "WHERE friends.id_friend1 = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String username = rs.getString("username");
                    String fullName = rs.getString("full_name");
                    friendsInfo.add(username + " (" + fullName + ")");
                }
            }

            System.out.println("Friends retrieved successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving friends information");
            return null;
        }
        return friendsInfo;
    }

    public int get_customer_user_id(String user) {

        String query = "SELECT * FROM users WHERE username = ? and type='CUSTOMER'";

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

}
