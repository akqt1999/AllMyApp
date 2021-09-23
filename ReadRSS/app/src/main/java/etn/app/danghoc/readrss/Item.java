package etn.app.danghoc.readrss;

import android.graphics.Bitmap;

public class Item {
    private Bitmap img;
   // String url1;
    private String title,info,link,url1;
    public Item(){ }

//    public Item(Bitmap img, String title, String info, String link) {
//        this.img = img;
//        this.title = title;
//        this.info = info;
//        this.link = link;
//    }

    public Item( String url, String title, String info, String link) {
        this.url1 = url;
        this.title = title;
        this.info = info;
        this.link = link;
    }
    public String getUrl() {
        return url1;
    }

    public void setUrl(String url) {
        this.url1 = url;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
