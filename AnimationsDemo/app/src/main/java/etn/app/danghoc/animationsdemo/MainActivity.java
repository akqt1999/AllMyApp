package etn.app.danghoc.animationsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnTest;
    TextView txtTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTest=findViewById(R.id.btnTest);
        txtTest=findViewById(R.id.txtTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpAnimation();
            }
        });
    }

    private void setUpAnimation() {
        ValueAnimator valueAnimator=ValueAnimator.ofInt(0,300);
        valueAnimator.setDuration(5000);//= 2 sec
       valueAnimator.setInterpolator(new LinearInterpolator());// binh thhong
        //valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator()); // ban dau cham sau do nhanh dan
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress= (int) animation.getAnimatedValue();
                txtTest.setTranslationY(progress);
            }
        });
        valueAnimator.start();
    }
}