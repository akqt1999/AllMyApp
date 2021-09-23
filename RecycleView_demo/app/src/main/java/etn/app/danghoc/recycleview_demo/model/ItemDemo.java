package etn.app.danghoc.recycleview_demo.model;

public class ItemDemo {

   private String mName;
    private boolean mCheck=false;
    public ItemDemo( String mName) {

        this.mName = mName;
    }

    public String getName() {
        return mName;
    }

    public boolean isCheck() {
        return mCheck;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setCheck(boolean mCheck) {
        this.mCheck = mCheck;
    }
}
