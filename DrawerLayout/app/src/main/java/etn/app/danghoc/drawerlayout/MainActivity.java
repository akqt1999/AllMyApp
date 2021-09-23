package etn.app.danghoc.drawerlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {
//private DrawerLayout dlMain;
//private Button btnOpen,btnClose,btnEvent;
   private DrawerLayout dlDemo;
    private ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       init();
    }
    private void init(){
        dlDemo=(DrawerLayout)findViewById(R.id.drawerlayout_demo);
        toggle=new ActionBarDrawerToggle(this,dlDemo,R.string.open,R.string.close);
        dlDemo.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if(toggle.onOptionsItemSelected(item)){
                return true;
            }
        return super.onOptionsItemSelected(item);
    }
    //    private void init() {
//        dlMain=(DrawerLayout)findViewById(R.id.drawerlayout_demo);
//        btnClose=(Button)findViewById(R.id.button_close);
//        btnOpen=(Button)findViewById(R.id.button_open);
//        btnEvent=(Button)findViewById(R.id.button_event1);
//        btnEvent.setOnClickListener(this);
//        btnOpen.setOnClickListener(this);
//        btnClose.setOnClickListener(this);
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.button_close :
//                dlMain.closeDrawer(Gravity.RIGHT);
//                break;
//            case R.id.button_open :
//                dlMain.openDrawer(Gravity.RIGHT);
//                break;
//            case R.id.button_event1 :
//                Toast.makeText(this, "event 1", Toast.LENGTH_SHORT).show();
//                break;
//
//        }
//    }
}
