package etn.app.danghoc.logintest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

@SerializedName("taikhoan")
@Expose
private String taikhoan;
@SerializedName("matkhau")
@Expose
private String matkhau;
@SerializedName("email")
@Expose
private String email;
@SerializedName("image")
@Expose
private String image;

public String getTaikhoan() {
return taikhoan;
}

public void setTaikhoan(String taikhoan) {
this.taikhoan = taikhoan;
}

public String getMatkhau() {
return matkhau;
}

public void setMatkhau(String matkhau) {
this.matkhau = matkhau;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

    public User() {
    }
}