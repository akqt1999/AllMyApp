package etn.app.danghoc.appshopping_demo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification2 {

@SerializedName("idNotification")
@Expose
private String idNotification;
@SerializedName("idNguoiBan")
@Expose
private String idNguoiBan;
@SerializedName("idNguoiMu")
@Expose
private String idNguoiMu;
@SerializedName("idDonHang")
@Expose
private String idDonHang;
@SerializedName("idLoaiContent")
@Expose
private String idLoaiContent;
@SerializedName("tenSp")
@Expose
private String tenSp;

public String getIdNotification() {
return idNotification;
}

public void setIdNotification(String idNotification) {
this.idNotification = idNotification;
}

public String getIdNguoiBan() {
return idNguoiBan;
}

public void setIdNguoiBan(String idNguoiBan) {
this.idNguoiBan = idNguoiBan;
}

public String getIdNguoiMu() {
return idNguoiMu;
}

public void setIdNguoiMu(String idNguoiMu) {
this.idNguoiMu = idNguoiMu;
}

public String getIdDonHang() {
return idDonHang;
}

public void setIdDonHang(String idDonHang) {
this.idDonHang = idDonHang;
}

public String getIdLoaiContent() {
return idLoaiContent;
}

public void setIdLoaiContent(String idLoaiContent) {
this.idLoaiContent = idLoaiContent;
}

public String getTenSp() {
return tenSp;
}

public void setTenSp(String tenSp) {
this.tenSp = tenSp;
}

}