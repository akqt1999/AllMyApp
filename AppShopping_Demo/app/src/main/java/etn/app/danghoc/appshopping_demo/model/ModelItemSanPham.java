package etn.app.danghoc.appshopping_demo.model;

import java.io.Serializable;

public class ModelItemSanPham implements Serializable {
    private int idSanPham,idBasket;
    private String mUrlImage,mNameSanPham,mDescription;
    private double mPrice;
    private int amount;
    private double totalMoney=0;
    private String mNameStore;
    private String mNameUserSell;
    public ModelItemSanPham() {
    }

    public ModelItemSanPham( String mUrlImage, String mNameSanPham, double mPrice,String mDescription,int amount) {

        this.mUrlImage = mUrlImage;
        this.mNameSanPham = mNameSanPham;
        this.mPrice = mPrice;
        this.mDescription=mDescription;
        this.amount=amount=0;
    }



    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
    }


    // basket
    public ModelItemSanPham(int idBasket, int idSanPham, int amount, double totalMoney, String ten,String mUrlImage,double mPrice) {
        this.idSanPham = idSanPham;
        this.idBasket = idBasket;
        this.mNameSanPham = ten;
        this.totalMoney = totalMoney;
        this.amount = amount;
        this.mUrlImage=mUrlImage;
        this.mPrice=mPrice;
    }

    public ModelItemSanPham(int idSanPham, String mUrlImage, String mNameSanPham, double mPrice, String mDescription,String mNameUserSell) {
        this.idSanPham = idSanPham;
        this.mUrlImage = mUrlImage;
        this.mNameSanPham = mNameSanPham;
        this.mPrice = mPrice;
        this.mDescription=mDescription;
        this.mNameUserSell=mNameUserSell;

    }

    public ModelItemSanPham(String mNameSanPham,String mNameStore,double mPrice,int amount){
            this.mNameSanPham=mNameSanPham;
            this.mNameStore=mNameStore;
            this.mPrice=mPrice;
            this.amount=amount;
    }




    public int getIdBasket() {
        return idBasket;
    }

    public void setIdBasket(int idBasket) {
        this.idBasket = idBasket;
    }

    public int getIdSanPham() {
        return idSanPham;
    }
    public String getNameStore() { return mNameStore; }

    public void setNameStore(String mNameStore) { this.mNameStore = mNameStore; }

    public double getTotalMoney() {return totalMoney; }

    public void setTotalMoney(double totalMoney) { this.totalMoney = totalMoney; }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUrlImage() {
        return mUrlImage;
    }

    public void setUrlImage(String mUrlImage) {
        this.mUrlImage = mUrlImage;
    }

    public String getNameSanPham() {
        return mNameSanPham;
    }

    public void setNameSanPham(String mNameSanPham) {
        this.mNameSanPham = mNameSanPham;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double mPrice) {
        this.mPrice = mPrice;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getNameUserSell() {
        return mNameUserSell;
    }

    public void setNameUserSell(String mNameUserSell) {
        this.mNameUserSell = mNameUserSell;
    }
}
