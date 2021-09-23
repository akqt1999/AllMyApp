package etn.app.danghoc.androidrestaurant.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import etn.app.danghoc.androidrestaurant.Callback.IRestaurantCallbackListener;
import etn.app.danghoc.androidrestaurant.Common.Common;
import etn.app.danghoc.androidrestaurant.Model.Restaurant;
import etn.app.danghoc.androidrestaurant.Retrofit.IMyRestraurantAPI;
import etn.app.danghoc.androidrestaurant.Retrofit.RetrofitClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends ViewModel implements IRestaurantCallbackListener {

    private MutableLiveData<String> messageError;
    private MutableLiveData<List<Restaurant>> listRestaurant;

    private IMyRestraurantAPI myRestaurantAPI;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private IRestaurantCallbackListener restaurantCallbackListener;

    public HomeViewModel() {

        myRestaurantAPI = RetrofitClient.getInstance(Common.API_RESTAURANT_ENDPOINT).create(IMyRestraurantAPI.class);

        restaurantCallbackListener=this;
    }

    public MutableLiveData<List<Restaurant>> getListRestaurant() {
        if(listRestaurant==null)
        {
            listRestaurant=new MutableLiveData<>();
            messageError=new MutableLiveData<>();
            loadRestauant();
        }
        return listRestaurant;
    }

    public MutableLiveData <String>getMessageError(){
        if(messageError==null)
            messageError=new MutableLiveData<>();
        return messageError;
    }

    private void loadRestauant() {
        compositeDisposable.
                add(myRestaurantAPI.getRestaurant(Common.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(restaurantModel -> {
                            if(restaurantModel.isSuccess())
                            {
                                restaurantCallbackListener.onRestaurantLoadSuccess(restaurantModel.getResult());
                            }

                        },throwable -> {
                            restaurantCallbackListener.onRestaurantLoadFail(throwable.getMessage());
                        })
                );

    }

    @Override
    public void onRestaurantLoadSuccess(List<Restaurant> restaurantModelList) {
        listRestaurant.setValue(restaurantModelList);
    }

    @Override
    public void onRestaurantLoadFail(String message) {
            messageError.setValue(message);
    }
}