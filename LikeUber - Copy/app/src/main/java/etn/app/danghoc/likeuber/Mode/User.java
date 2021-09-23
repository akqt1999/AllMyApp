package etn.app.danghoc.likeuber.Mode;

public class User { // bỏ rôig
    String email,name,numberPhone,password;
    public User(){}

    public User(String email, String name, String numberPhone, String password) {
        this.email = email;
        this.name = name;
        this.numberPhone = numberPhone;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
