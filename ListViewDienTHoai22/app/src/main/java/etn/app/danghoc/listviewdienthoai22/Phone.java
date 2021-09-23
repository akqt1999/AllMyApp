package etn.app.danghoc.listviewdienthoai22;

public class Phone {
    int image;
    String namePhone;
    String gia;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getNamePhone() {
        return namePhone;
    }

    public void setNamePhone(String namePhone) {
        this.namePhone = namePhone;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public Phone(int image, String namePhone,String gia) {
        this.image = image;
        this.namePhone = namePhone;
        this.gia=gia;
    }
    public Phone(){
    }
}
