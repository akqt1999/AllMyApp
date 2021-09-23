package etn.app.danghoc.gettinhhuyen.Retrofit;

import etn.app.danghoc.gettinhhuyen.model.DistrictModel;
import etn.app.danghoc.gettinhhuyen.model.TinhModel;

import etn.app.danghoc.gettinhhuyen.model.WardModel;
import io.reactivex.Observable;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IMyShoppingAPI {


    @GET("shiip/public-api/master-data/province")
    Observable<TinhModel> getProvince(
            @Header("token")String token,
            @Header("Content-Type")String content
    );
    @GET("shiip/public-api/master-data/district")
    Observable<DistrictModel> getDistrict(
            @Header("token")String token,
            @Header("Content-Type")String content,
            @Query("province_id") int province_id
    );

    @GET("shiip/public-api/master-data/ward")
    Observable<WardModel> getWord(
            @Header("token")String token,
            @Header("Content-Type")String content,
            @Query("district_id") int province_id
    );

}
