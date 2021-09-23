package etn.app.danghoc.custom_listview.model;

public class Contact {
    private int mStt;
    private String mName;
    private String mNumberPhone;


    public Contact (int mStt,String mName,String mNumberPhone){
        this.mStt=mStt;
        this.mName=mName;
        this.mNumberPhone=mNumberPhone;
    }

    public void setmStt(int mStt){
        this.mStt=mStt;
    }
    public void setmName(String mName1){
        this.mName=mName1;
    }
    public void setmNumberPhone(String mNumberPhone1){
        this.mNumberPhone=mNumberPhone1;
    }

    public int getmStt(){
        return mStt;
    }
    public String getmName(){
        return mName;
    }
    public String getmNumberPhone(){
        return mNumberPhone;
    }


}

