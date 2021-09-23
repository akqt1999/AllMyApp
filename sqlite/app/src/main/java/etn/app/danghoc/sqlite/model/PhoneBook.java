package etn.app.danghoc.sqlite.model;

public class PhoneBook {
    private int mID;
    private String mName;
    private String mNumberPhone;

    public String getmName() {
        return mName;
    }

    public String getmNumberPhone() {
        return mNumberPhone;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmNumberPhone(String mNumberPhone) {
        this.mNumberPhone = mNumberPhone;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public PhoneBook(String mName, String mNumberPhone) {
        this.mName = mName;
        this.mNumberPhone = mNumberPhone;
    }

    public PhoneBook() {
    }

    public PhoneBook(int mID, String mName, String mNumberPhone) {
        this.mID = mID;
        this.mName = mName;
        this.mNumberPhone = mNumberPhone;
    }
}

