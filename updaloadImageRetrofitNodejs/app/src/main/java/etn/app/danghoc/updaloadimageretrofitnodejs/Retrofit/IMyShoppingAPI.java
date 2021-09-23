package etn.app.danghoc.updaloadimageretrofitnodejs.Retrofit;

import etn.app.danghoc.updaloadimageretrofitnodejs.model.DanhMucModel;
import etn.app.danghoc.updaloadimageretrofitnodejs.model.UpdateModel;
import etn.app.danghoc.updaloadimageretrofitnodejs.model.UploadSanPhamModel;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface IMyShoppingAPI {
    @Multipart
    @POST("/uploadfile")
    Call<ResponseBody> postImage(@Part MultipartBody.Part image, @Part("myFile") RequestBody name);

    @Multipart
    @POST("/uploadfile")
    Call<UpdateModel> postImage2(@Part MultipartBody.Part image, @Part("myFile") RequestBody name);

    @POST("/uploadfile")
    @FormUrlEncoded
    Observable<UpdateModel> postImageCom(@Part MultipartBody.Part image, @Part("myFile") RequestBody name);

    @POST("sanpham")
    @FormUrlEncoded
    Observable<UploadSanPhamModel> uploadSanPham(
            @Field("key") String key,
            @Field("IdUser") String IdUser,
            @Field("TenSP") String TenSP,
            @Field("GiaSP") float GiaSP,
            @Field("Mota") String Mota,
            @Field("IdDanhMuc") int IdDanhMuc,
            @Field("hinh") String hinh);


    @GET("danhmuc")
    Observable<DanhMucModel> getDanhMuc(@Query("key") String apiKey);
}
