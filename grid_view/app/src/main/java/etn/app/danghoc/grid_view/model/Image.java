package etn.app.danghoc.grid_view.model;

public class Image {
    private int image;
    private String infoimage;

    public Image(int image,String infoimage){
        this.image=image;
        this.infoimage=infoimage;
    }

    public void setImage(int image) {
        this.image = image;
    }
    public void setInfoimage(String infoimage){
        this.infoimage=infoimage;
    }
    public int getImage(){return image;}
    public String getInfoimage(){return infoimage;}

}
