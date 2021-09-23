package etn.app.danghoc.note_appp1.model;

public class ItemTitle {
    private int mId;
    public String mToday;
    public String mTitle;
    public String mContent;
    private int mCheckFav =0; // 0 is false ; 1 is true
    private boolean mCheck = false;

    public void setmToday(String today) {
        this.mToday = today;
    }

    public void setmTitle(String title) {
        this.mTitle = title;
    }

    public void setmContent(String content) {
        this.mContent = content;
    }

    public void setmId(int i) {
        this.mId = i;
    }

    public void setMCheck(boolean check) {
        this.mCheck = check;
    }

    public void setmCheckFav(int checkFav) {
        this.mCheckFav = checkFav;
    }

    // get
    public String getmContent() {
        return mContent;
    }

    public String getmToday() {
        return mToday;
    }

    public String getmTitle() {
        return mTitle;
    }

    public int getId() {
        return mId;
    }

    public boolean getCheck() {
        return mCheck;
    }

    public int getCheckFav() {
        return mCheckFav;
    }

    public ItemTitle(String mTitle, String mToday, String mContent,int mCheckFav) {
        this.mTitle = mTitle;
        this.mToday = mToday;
        this.mContent = mContent;
        this.mCheckFav=mCheckFav;
    }

    public ItemTitle(int mId, String mToday, String mTitle, String mContent,int mCheckFav) {
        this.mId = mId;
        this.mToday = mToday;
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mCheckFav=mCheckFav;
    }

    public ItemTitle() {
    }

    ;
}
