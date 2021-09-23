package etn.app.danghoc.androidrestaurant.Callback;

import java.util.List;

import etn.app.danghoc.androidrestaurant.Model.Restaurant;

public interface IRestaurantCallbackListener {
    void onRestaurantLoadSuccess(List<Restaurant>restaurantModelList);
    void onRestaurantLoadFail(String message);

}
