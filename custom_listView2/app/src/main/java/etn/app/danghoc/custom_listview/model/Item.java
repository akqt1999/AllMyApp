package etn.app.danghoc.custom_listview.model;

public class Item {
    private String mNumber;
    private String mName;

    public Item(String mNumber, String mName) {
        this.mNumber = mNumber;
        this.mName = mName;
    }
    public void setmNumber(String number){
        this.mNumber=number;
    }
    public void setmName(String name){
        this.mName=name;
    }
    public String getmNumber(){return mNumber;}

    public String getmName() { return mName; }
}
