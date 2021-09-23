package etn.app.danghoc.androidrestaurant.Adapter;

import java.util.List;

import etn.app.danghoc.androidrestaurant.Model.Restaurant;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class RestaurantSliderAdapter extends SliderAdapter {

    List<Restaurant>restaurantList;

    public RestaurantSliderAdapter(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
        imageSlideViewHolder.bindImageSlide(restaurantList.get(position).getImage());
    }
}
