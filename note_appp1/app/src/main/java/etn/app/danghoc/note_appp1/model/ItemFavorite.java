package etn.app.danghoc.note_appp1.model;

public class ItemFavorite {
    private int id;
    private String mTitle;
    private boolean mCheck=false;

    public ItemFavorite(String mTitle,int id) {
        this.mTitle = mTitle;
        this.id =id; }

    public String getTitle() {
        return mTitle;
    }
    public boolean isCheck(){return  mCheck;}
    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
   public void setCheck(boolean check){this.mCheck=check;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
/*
má mình ngu vl
 */