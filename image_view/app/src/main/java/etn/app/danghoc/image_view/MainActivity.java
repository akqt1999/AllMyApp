package etn.app.danghoc.image_view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
 ImageView imgTomato;
 RelativeLayout manhinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manhinh=(RelativeLayout) findViewById(R.id.manHinh);
        imgTomato=(ImageView) findViewById(R.id.imgApple);

        manhinh.setBackgroundResource(R.drawable.hinhnendt);


        imgTomato.setImageResource(R.drawable.tomato__);
    }
}
