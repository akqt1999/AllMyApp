package etn.app.danghoc.androidrestaurant.Retrofit;


import etn.app.danghoc.androidrestaurant.Model.RestaurantModel;
import etn.app.danghoc.androidrestaurant.Model.UpdateUserModel;
import etn.app.danghoc.androidrestaurant.Model.UserModel;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IMyRestraurantAPI {

    @GET("user")
    Observable<UserModel> getUser(@Query("key")String apiKey,
                                  @Query("fbid")String fbid); //cai fbid chu la cai dien thoai

    @GET("restaurant")
    Observable<RestaurantModel>getRestaurant(@Query("key")String apiKey);

    @POST("user")
    @FormUrlEncoded
    Observable<UpdateUserModel>updateUserInfo(@Field("key") String apiKey,
                                              @Field("userPhone")String userPhone,
                                              @Field("userAddress")String userAddress,
                                              @Field("userName")String userName,
                                              @Field("fbid")String fbid);
}
