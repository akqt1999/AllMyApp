package etn.app.danghoc.pragment.model;

public class ItemListViewA {
    private String mName,mNumber;

    public void setmName(String mname){ this.mName=mname;}
    public void setmNumber(String mnumber){this.mNumber=mnumber;}
    public String getmName(){return mName;}
    public String getmNumber(){return mNumber;}

   public ItemListViewA (String mName,String mNumber){this.mName=mName;this.mNumber=mNumber;}
   public ItemListViewA(){}
}
