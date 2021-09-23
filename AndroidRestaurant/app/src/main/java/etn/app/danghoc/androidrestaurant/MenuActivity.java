package etn.app.danghoc.androidrestaurant;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nex3z.notificationbadge.NotificationBadge;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import etn.app.danghoc.androidrestaurant.Common.Common;
import etn.app.danghoc.androidrestaurant.Retrofit.IMyRestraurantAPI;
import etn.app.danghoc.androidrestaurant.Retrofit.RetrofitClient;
import io.reactivex.disposables.CompositeDisposable;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.img_restaurant)
    ImageView img_restaurant;
    @BindView(R.id.recycle_category)
    RecyclerView recycle_category;
    @BindView(R.id.badge)
    NotificationBadge badge;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton btn_cart;




    IMyRestraurantAPI myRestaurantAPI;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    AlertDialog dialog;

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        init();
        initView();

    }

    private void initView() {
        ButterKnife.bind(this);

        btn_cart.setOnClickListener(view -> {
            
        });


    }

    private void init() {
        dialog=new SpotsDialog.Builder().setContext(this).setCancelable(false).build();
        myRestaurantAPI= RetrofitClient.getInstance(Common.API_RESTAURANT_ENDPOINT).create(IMyRestraurantAPI.class);
    }
}