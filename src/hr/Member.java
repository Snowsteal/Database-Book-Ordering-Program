package hr;

public class Member {

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }
    
    public String getState() {
        return state;
    }

    public int getZip() {
        return zip;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getUserid() {
        return userid;
    }

    public String getPassword() {
        return password;
    }

    public String getCreditcardtype() {
        return creditcardtype;
    }

    public String getCreditcardnumber() {
        return creditcardnumber;
    }
    
    String fname;
    String lname;
    String address;
    String city;
    String state;
    int zip;
    String phone;
    String email;
    String userid;
    String password;
    String creditcardtype;
    String creditcardnumber;

    public Member(String fname, String lname, String address, String city, String state, int zip, String phone, String email, String userid, String password, String creditcardtype, String creditcardnumber) {
        this.fname = fname;
        this.lname = lname;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
        this.userid = userid;
        this.password = password;
        this.creditcardtype = creditcardtype;
        this.creditcardnumber = creditcardnumber;
    }
    
    public void Print() {
        System.out.printf("Name: %s %s\n", fname, lname);
        System.out.printf("Address: %s\n", address);
        System.out.printf("City: %s, %s %d\n", city, state, zip);
        System.out.printf("Phone: %s\n", phone);
        System.out.printf("Email: %s\n", email);
        System.out.printf("UserID: %s\n", userid);
        System.out.printf("Password: %s\n", password);
        System.out.printf("Credit Card Type: %s\n", creditcardtype);
        System.out.printf("Credit Card Number: %s\n", creditcardnumber);
    }
}
