package etn.app.danghoc.bottom_navigation_view_with_fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView mBottomNavDemo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomNavDemo=(BottomNavigationView)findViewById(R.id.bottom_navigation);
        mBottomNavDemo.setOnNavigationItemSelectedListener(mBottomNavListener);
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,new Fragment_home()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mBottomNavListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectFragment=null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_fragment_home:
                       selectFragment=new Fragment_home();
                       break;
                        case R.id.nav_fragment_map:
                            selectFragment=new FragmentMap();
                            break;
                        case R.id.nav_framgment_moto:
                            selectFragment=new FragmentMoto();
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contain,selectFragment).commit();
                    return true;
                }
            };
}

/*
link youtube : https://youtu.be/tPV8xA7m-iw?t=876
 */