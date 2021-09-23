package etn.app.danghoc.appshopping_demo;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import etn.app.danghoc.appshopping_demo.fragment.FragmentDashboard;
import etn.app.danghoc.appshopping_demo.fragment.FragmentHome;
import etn.app.danghoc.appshopping_demo.fragment.FragmentNotification;

public class ContainFragment extends AppCompatActivity implements OnCallBackk {
    BottomNavigationView bottomNavigationView;
    boolean checkOnFragmentDashboard=false;
   public static boolean checkOnFragmentDashboardInLogin=false;
    public static  Bundle  bundle=new Bundle();

    ActivityLogin activityLogin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contain_fragment);
        bottomNavigationView=findViewById(R.id.nav_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);
        bottomNavigationView.setItemIconTintList(null);
        bundle.putInt("position",-1);
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.contain_fragment,new FragmentHome()).commit();
        }

    }



    @Override
    protected void onResume() {
        super.onResume();
        if(checkOnFragmentDashboardInLogin){
            getSupportFragmentManager().beginTransaction().replace(R.id.contain_fragment,new FragmentDashboard()).commit();
        }
        activityLogin=new ActivityLogin();
        activityLogin.setListen(this);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener=
        new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragmentSelect=null;
                switch (menuItem.getItemId()){
                    case R.id.nav_fragment_home:
                        fragmentSelect=new FragmentHome();
                        checkOnFragmentDashboard=false;
                        break;
                    case R.id.nav_fragment_dashboard:
                        fragmentSelect=new FragmentDashboard();
                        checkOnFragmentDashboard=true;
                        break;
                    case R.id.nav_fragment_notification:
                        fragmentSelect=new FragmentNotification();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.contain_fragment,fragmentSelect).commit();
                return true;
            }
        };


    @Override
    public void onLogin(int a) {
        if(checkOnFragmentDashboard){
            getSupportFragmentManager().beginTransaction().add(R.id.contain_fragment,new FragmentDashboard()).commit();
        }
    }

}
