package etn.app.danghoc.testfirebasegetdatabase;

public class User {
    String nameUser,password;

    public User(String nameUser, String password) {
        this.nameUser = nameUser;
        this.password = password;
    }

    public User( ) {
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
