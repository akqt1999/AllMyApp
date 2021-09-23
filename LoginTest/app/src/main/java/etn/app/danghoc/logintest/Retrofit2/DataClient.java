package etn.app.danghoc.logintest.Retrofit2;

import java.util.List;

import etn.app.danghoc.logintest.User;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface DataClient {
    @Multipart // insert file
    @POST("uploadimage.php")
    Call<String> UploadImage(@Part MultipartBody.Part image);//part dua file tren folder

    @FormUrlEncoded // insert string su dung cho post
    @POST("insert.php")// truyen gia tri len sever field
    Call<String> InsertData(@Field("taikhoan")String taiKhoan // string la tra ve gia tri string
                                    ,@Field("matkhau")String matKhau///field su dung cho viet post
                                    ,@Field("image")String image);
    @FormUrlEncoded // login su dung cho post
    @POST("login.php")
    Call<List<User>>Login(@Field("taikhoan")String taiKhoan//field su dung cho viet post
                                 ,@Field("matkhau")String matKhau);

    @GET("delete.php") // su dung cho get ,  noi them duong link
    Call<String>Delete(@Query("name")String name // query noi them duong link
                                , @Query("image")String image);

    @GET("getuser.php")
    Call<List<User>>GetUer();


}





