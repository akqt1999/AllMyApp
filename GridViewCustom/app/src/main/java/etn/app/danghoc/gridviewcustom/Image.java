package etn.app.danghoc.gridviewcustom;

public class Image {
    int image;
    String title;
    public Image(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Image(int image, String title) {
        this.image = image;
        this.title = title;
    }
}
