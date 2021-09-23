package etn.app.danghoc.androidrestaurant.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import etn.app.danghoc.androidrestaurant.Adapter.MyRestaurantAdapter;
import etn.app.danghoc.androidrestaurant.Adapter.RestaurantSliderAdapter;
import etn.app.danghoc.androidrestaurant.Model.Restaurant;
import etn.app.danghoc.androidrestaurant.R;
import etn.app.danghoc.androidrestaurant.Sevices.PicassoImageLoadingService;
import etn.app.danghoc.androidrestaurant.databinding.FragmentHomeBinding;
import ss.com.bannerslider.Slider;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    Unbinder unbinder;

    @BindView(R.id.banner_slider)
    Slider banner_slider;
    @BindView(R.id.recycler_restaurant)
    RecyclerView recycler_restaurant;

    

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        homeViewModel=new ViewModelProvider(this).get(HomeViewModel.class);

        //load recyclerview
        homeViewModel.getListRestaurant().observe(this,restaurants -> {
            if(restaurants.size()!=0){
                displayBanner(restaurants);
                displayRestaurant(restaurants);
            }
            else
                homeViewModel.getMessageError().observe(this,error -> {
                    Toast.makeText(getContext(), "[Load  restaurant ]"+error, Toast.LENGTH_SHORT).show();
                });

        });



        initView(root);


        return root;
    }

    private void displayRestaurant(List<Restaurant> restaurants) {
        MyRestaurantAdapter adapter=new MyRestaurantAdapter(getContext(),restaurants);
        recycler_restaurant.setAdapter(adapter);
    }

    private void displayBanner(List<Restaurant> restaurants) {
        banner_slider.setAdapter(new RestaurantSliderAdapter(restaurants));
    }

    private void initView(View root) {
      unbinder=  ButterKnife.bind(this,root);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recycler_restaurant.setLayoutManager(linearLayoutManager);
        recycler_restaurant.addItemDecoration(new DividerItemDecoration(getContext(),linearLayoutManager.getOrientation()));
       // DividerItemDecoration : dung de tao ra cac dau ____ ngan cach

        Slider.init(new PicassoImageLoadingService());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}