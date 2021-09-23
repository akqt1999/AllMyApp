package etn.app.danghoc.doancuoiky;

public class Item {
    private String number,title;
    private int thich;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getThich() {
        return thich;
    }

    public void setThich(int thich) {
        this.thich = thich;
    }

    public Item(String number, String title, int thich) {
        this.number = number;
        this.title = title;
        this.thich = thich;
    }
}
