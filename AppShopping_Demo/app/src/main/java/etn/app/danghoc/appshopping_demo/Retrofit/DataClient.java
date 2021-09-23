package etn.app.danghoc.appshopping_demo.Retrofit;



import java.util.List;

import etn.app.danghoc.appshopping_demo.Notification2;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public interface DataClient {
    @FormUrlEncoded
    @POST("getnotification.php")
    Call<List<Notification2>>GetNotification(@Field("idNguoiBan")String idNguoiBan);

}
