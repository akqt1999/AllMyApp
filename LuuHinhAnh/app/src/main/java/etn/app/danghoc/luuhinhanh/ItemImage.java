package etn.app.danghoc.luuhinhanh;

public class ItemImage {
   private int id;
   private String title;
   private byte[] image;

    public ItemImage(int id, String title, byte[] image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }
    public ItemImage( String title, byte[] image) {

        this.title = title;
        this.image = image;
    }
    public ItemImage() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
