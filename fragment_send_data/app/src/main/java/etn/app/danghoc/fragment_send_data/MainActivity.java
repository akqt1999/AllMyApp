package etn.app.danghoc.fragment_send_data;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager_demo);
        mViewPager.setAdapter(new AdapterViewPager(getSupportFragmentManager()));

        mTabLayout = (TabLayout) findViewById(R.id.tablayout_demo);
        mTabLayout.setupWithViewPager(mViewPager);
        setIcon();
    }
    private void setIcon(){
        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_ac_unit_black_24dp);
        mTabLayout.getTabAt(2).setIcon(R.drawable.ic_add_alert_black_24dp);

    }
}
