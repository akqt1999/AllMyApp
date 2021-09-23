package etn.app.danghoc.testswitcher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import io.ghyeok.stickyswitch.widget.StickySwitch;

public class MainActivity extends AppCompatActivity {
    StickySwitch stickySwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stickySwitch=findViewById(R.id.stickyswitch);
        stickySwitch.setDirection(StickySwitch.Direction.LEFT);
        stickySwitch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(StickySwitch.Direction direction, String s) {
                if(s.equalsIgnoreCase("locationon")){
                    Toast.makeText(MainActivity.this, "ON", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}