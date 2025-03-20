import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

public class RegisterComponents {

    protected JTextField FirstNameField;
    protected JTextField SurnameField;
    protected JTextField emailField;
    protected JTextField usernameField;
    protected JTextField cityField;
    protected JTextField streetField;
    protected JTextField streetnumberField;
    protected JTextField phoneField;
    protected JTextField residenceField;
    protected JTextField genre_field ;

    protected JPasswordField passwordField;
    protected JPasswordField passwordconfirmField;

    protected JLabel error_msg;
    protected JLabel userNameLabel;
    protected JLabel emailLabel;
    protected JLabel passwordLabel;
    protected JLabel passwordconfirmLabel;
    protected JLabel FirstNameLabel;
    protected JLabel SurnameLabel;
    protected JLabel cityLabel;
    protected JLabel streetLabel;
    protected JLabel streetnumberLabel;
    protected JLabel phoneLabel;
    protected JLabel residenceLabel;
    protected JLabel gender;

    protected UserPersonalData userPersonalData;
    protected DataBaseOp dataBaseOp;
    protected ErrorClass error;

    public RegisterComponents() {

        dataBaseOp = new DataBaseOp();
        error=new ErrorClass("ok");

        FirstNameField = new JTextField();
        SurnameField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();
        passwordconfirmField = new JPasswordField();
        usernameField = new JTextField();
        cityField = new JTextField();
        streetField = new JTextField();
        streetnumberField = new JTextField();
        phoneField = new JTextField();
        residenceField = new JTextField();
        genre_field = new JTextField();

        error_msg = new JLabel();
        userNameLabel = new JLabel("Username:");
        emailLabel = new JLabel("Email:");
        passwordLabel = new JLabel("Password:");
        passwordconfirmLabel = new JLabel("Confirm Password:");
        FirstNameLabel = new JLabel("First Name (Given name):");
        SurnameLabel = new JLabel("Surname (Family name):");
        cityLabel = new JLabel("City:");
        streetLabel = new JLabel("Street:");
        streetnumberLabel = new JLabel("Number:");
        phoneLabel = new JLabel("Phone:");
        residenceLabel = new JLabel("Fiscal Residence (Country):");
        gender = new JLabel("Select gender:");

        userPersonalData = new UserPersonalData();

        DocumentFilter filter = new NoSpaceDocumentFilter();
        ((AbstractDocument) FirstNameField.getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) SurnameField.getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) emailField.getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) passwordField.getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) passwordconfirmField.getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) phoneField.getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) usernameField.getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) streetnumberField.getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) genre_field.getDocument()).setDocumentFilter(filter);


        error_msg.setFont(new Font(null, Font.PLAIN, 20));
        error_msg.setForeground(Color.RED);

    }

    static public String capitalizeSegments(String input) {

        StringBuilder result = new StringBuilder();

        String[] spaceSegments = input.trim().split("\\s+");

        for (int i = 0; i < spaceSegments.length; i++) {
            if (!spaceSegments[i].isEmpty()) {
                /// split each segment by hyphens
                String[] hyphenSegments = spaceSegments[i].split("-+"); /// split the splitted by " " segment(s) into - if encountered
                for (int j = 0; j < hyphenSegments.length; j++) {
                    if (!hyphenSegments[j].isEmpty()) {
                        hyphenSegments[j] = hyphenSegments[j].toLowerCase(); /// 1st letter to uppercase
                        hyphenSegments[j] = Character.toUpperCase(hyphenSegments[j].charAt(0)) + hyphenSegments[j].substring(1);
                        result.append(hyphenSegments[j]); /// append segment
                        if (j < hyphenSegments.length - 1) { /// dont appent - if last segment
                            result.append("-");
                        }
                    }
                }
                if (i < spaceSegments.length - 1) { /// dont append space is last segment
                    result.append(" ");
                }
            }
        }
        return result.toString();
    }

    protected boolean validateUsername(String username) {
        if (username.isEmpty()) {
            error.message = "Blank Username Field";
            return false;
        }

        if (username.length() < 6) {  // Adjusted to be at least 6 characters long
            error.message = "Username must be at least 6 characters long";
            return false;
        }

        if (username.length() > 30) {
            error.message = "Username is too long";
            return false;
        }

        boolean foundLetterInUsername = false;
        boolean foundDigitInUsername = false;
        for (char ch : username.toCharArray()) {
            if (Character.isLetter(ch)) {
                foundLetterInUsername = true;
            }
            if (Character.isDigit(ch)) {
                foundDigitInUsername = true;
            }
            if (foundLetterInUsername && foundDigitInUsername) {
                break;
            }
        }

        if (!foundLetterInUsername || !foundDigitInUsername) {
            error.message = "Username must contain at least one letter and one digit";
            return false;
        }

        if (dataBaseOp.check_username(username)) { // if existing customer
            error.message = "Username already exists";
            return false;
        }

        userPersonalData.setUserName(username);

        return true;
    }

    protected boolean validatePassword(String password, String retyped_password) {
        if (password.isEmpty()) {
            error.message = "Blank Password Field";
            return false;
        }

        if (password.length() < 6) {  // Adjusted to be at least 6 characters long
            error.message = "Password must be at least 6 characters long";
            return false;
        }

        if (password.length() > 30) {
            error.message = "Password is too long";
            return false;
        }

        boolean foundLetterInPassword = false;
        boolean foundDigitInPassword = false;
        boolean foundSpecialCharInPassword = false;
        for (char ch : password.toCharArray()) {
            if (Character.isLetter(ch)) {
                foundLetterInPassword = true;
            }
            if (Character.isDigit(ch)) {
                foundDigitInPassword = true;
            }
            if (!Character.isLetterOrDigit(ch)) {
                foundSpecialCharInPassword = true;
            }
            if (foundLetterInPassword && foundDigitInPassword && foundSpecialCharInPassword) {
                break;
            }
        }

        if (!foundLetterInPassword || !foundDigitInPassword || !foundSpecialCharInPassword) {
            error.message = "Password must contain at least one letter, one digit, and one special character";
            return false;
        }

        if (retyped_password.isEmpty()) {
            error.message = "Blank Password Field";
            return false;
        }

        if (!Objects.equals(password, retyped_password)) {
            error.message = "Passwords do not match!";
            return false;
        }

        userPersonalData.setPassword(password);

        return true;
    }

    protected int validateCountry(String name) {

        if (name.isEmpty()) {
            error.message = "Blank Country Field";
            return -1;
        }

        int id = dataBaseOp.get_country_id(name);

        if (id == -1)
            error.message = "Invalid Country.Use upper case if needed";
        else {
            userPersonalData.setCountry(name);
        }

        return id;
    }

    protected boolean validateFirstName(String first_name) {
        if (first_name.isEmpty()) {
            error.message = "Blank First Name Field";
            return false;
        }

        if (first_name.length() > 30) {
            error.message = "First Name Field way too long";
            return false;
        }

        if (!first_name.matches("[a-zA-Z-]+")) {
            error.message = "Invalid First Name Format.";
            return false;
        }

        first_name = capitalizeSegments(first_name); // for cases like emil-ioan
        userPersonalData.setFirstName(first_name);

        return true;
    }

    protected boolean validateLastName(String last_name) {
        if (last_name.isEmpty()) {
            error.message = "Blank Last Name Field";
            return false;
        }

        if (last_name.length() > 30) {
            error.message = "Last Name Field way too long";
            return false;
        }

        if (!last_name.matches("[a-zA-Z]+")) {
            error.message = "Invalid Last Name Format.One word accepted only";
            return false;
        }

        last_name = last_name.toLowerCase();
        last_name = Character.toUpperCase(last_name.charAt(0)) + last_name.substring(1);
        userPersonalData.setLastName(last_name);

        return true;
    }

    protected boolean validateAndConcatNames(String first_name, String last_name) {

        if (!validateLastName(last_name)) {
            return false;
        }

        if (!validateFirstName(first_name)) {
            return false;
        }
        /// ok name
        String full_name = userPersonalData.getLastName() + " " + userPersonalData.getFirstName();

        if (dataBaseOp.search_person_by(full_name, "full_name")) {
            error.message = "Person Name Already Exists";
            return false;
        }

        userPersonalData.setFullName(full_name);

        return true;
    }

    protected boolean validatePhone(String phone) {
        if (phone.isEmpty()) {
            error.message = "Blank Phone Field";
            return false;
        }

        if (phone.length() > 10) {
            error.message = "Invalid Phone Number.";
            return false;
        }

        String phoneRegex = "^[0-9\\-\\s()]+$";
        if (!phone.matches(phoneRegex)) {
            error.message = "Invalid Phone Number Format";
            return false;
        }

        if (dataBaseOp.search_person_by(phone, "phone")) {
            error.message = "Phone Number Already Exists";
            return false;
        }

        userPersonalData.setPhoneNumber(phone);
        return true;
    }

    protected boolean validateEmail(String email) {

        if (email.isEmpty()) {
            error.message = "Blank Email Field";
            return false;
        }

        if (email.length() > 30) {
            error.message = "Email Field way too long";
            return false;
        }

        /// username part allows letters, digits, dots, underscores, percent signs, plus signs, and hyphens.
        /// the domain part starts with an '@' followed by letters, digits, dots, and hyphens.
        /// The domain extension has at least two letters.
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        if (!email.matches(emailRegex)) {
            error.message = "Invalid Email Address";
            return false;
        }

        if (dataBaseOp.search_person_by(email, "email")) {
            error.message = "Email already in use";
            return false;
        }

        userPersonalData.setEmail(email);

        return true;
    }

    protected boolean validateCity(String city) {
        if (city.isEmpty()) {
            error.message = "City Field Empty!";
            return false;
        }

        city=city.trim();

        if (city.length() > 30) {
            error.message = "City Field way too long";
            return false;
        }

        if (!city.matches("[a-zA-Z-\\s]+")) {
            error.message = "The city name should contain only letters.";
            return false;
        }


        city = capitalizeSegments(city);
        userPersonalData.setCity(city);

        return true;
    }

    protected boolean validateStreet(String street) {
        if (street.isEmpty()) {
            error.message = "Street Field Empty!";
            return false;
        }

        street=street.trim();

        if (street.length() > 30) {
            error.message = "Street Field way too long";
            return false;
        }

        if (!street.matches("[a-zA-Z-\\s]+")) {
            error.message = "The street name should contain only letters.";
            return false;
        }

        street = capitalizeSegments(street);
        userPersonalData.setStreet(street);

        return true;
    }

    protected boolean validateStreetNumber(String street_no) {
        if (street_no.isEmpty()) {
            error.message = "Street Number Field Empty!";
            return false;
        }

        if (street_no.length() > 10) {
            error.message = "Street Number Field way too long";
            return false;
        }

        if (!street_no.matches("\\d+")) {
            error.message = "The last part should be a number.";
            return false;
        }

        userPersonalData.setStreetNo(street_no);

        return true;
    }

    protected boolean validateFullAddress(String city, String street, String street_no) {
        if (!validateCity(city)) {
            return false;
        }

        if (!validateStreet(street)) {
            return false;
        }

        if (!validateStreetNumber(street_no)) {
            return false;
        }

        String full_address = "city: " + userPersonalData.getCity() + " ,street: " + userPersonalData.getStreet() + ",number: " + street_no;
        userPersonalData.setAddress(full_address);

        return true;
    }

    protected boolean validateGender(String gender) {
        if (!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female")) {
            error.message = "Gender must be 'male' or 'female'.";
            return false;
        }

        userPersonalData.setGender(gender);
        return true;
    }

}
