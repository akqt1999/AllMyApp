package etn.app.danghoc.hinhnen_random;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Random;
// thêm cái Switch mỗi lần bật cái mới thì thay đổi một hinh nền

public class MainActivity extends AppCompatActivity {
  LinearLayout layoutt;
  Switch swHn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutt=(LinearLayout) findViewById(R.id.layoutHn);
        swHn=(Switch) findViewById(R.id.switchHn);

        final ArrayList<Integer> imgHinhNen=new ArrayList<>();
        imgHinhNen.add(R.drawable.hinhnendt);
        imgHinhNen.add(R.drawable.hinhnen1);
        imgHinhNen.add(R.drawable.hinhnen2);
        imgHinhNen.add(R.drawable.hinhnen3);

        // tạo random
        final Random random = new Random();
        final int vitri=random.nextInt(imgHinhNen.size());
        layoutt.setBackgroundResource(imgHinhNen.get(vitri));
     swHn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
         if(isChecked){
             int vitriSwitch=random.nextInt(imgHinhNen.size());
             layoutt.setBackgroundResource(imgHinhNen.get(vitriSwitch   ));
         }
         }
     });
    }
}
