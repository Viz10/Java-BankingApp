public class UserPersonalData {

    /// this class comes in handy when dealing with the register customer and update customer form page , it updates on the run fields that can be used in other personal
    /// info text fields from registration ,  especially when inserting into DB

    private String FirstName;
    private String LastName;
    private String FullName;
    private String UserName;
    private String Password;
    private String Email;
    private String Adress;
    private String PhoneNumber;
    private String Country;
    private String Gender;
    private String City;
    private String Street;
    private String StreetNo;

    public String getFirstName() { return FirstName; }
    public void setFirstName(String firstName) { FirstName = firstName; }

    public String getLastName() { return LastName; }
    public void setLastName(String lastName) { LastName = lastName; }

    public String getFullName() { return FullName; }
    public void setFullName(String fullName) { FullName = fullName; }

    public String getUserName() { return UserName; }
    public void setUserName(String userName) { UserName = userName; }

    public String getPassword() { return Password; }
    public void setPassword(String password) { Password = password; }

    public String getEmail() { return Email; }
    public void setEmail(String email) { Email = email; }

    public String getAddress() { return Adress; }
    public void setAddress(String address) { Adress = address; }

    public String getPhoneNumber() { return PhoneNumber; }
    public void setPhoneNumber(String phoneNumber) { PhoneNumber = phoneNumber; }

    public String getCountry() { return Country; }
    public void setCountry(String country) { Country = country; }

    public String getGender() { return Gender; }
    public void setGender(String gender) { Gender = gender; }

    public String getCity() { return City; }
    public void setCity(String city) { City = city; }

    public String getStreet() { return Street; }
    public void setStreet(String street) { Street = street; }

    public String getStreetNo() { return StreetNo; }
    public void setStreetNo(String streetNo) { StreetNo = streetNo; }
}
