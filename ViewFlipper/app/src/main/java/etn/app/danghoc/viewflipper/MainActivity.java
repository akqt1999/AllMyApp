package etn.app.danghoc.viewflipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {
    public static ViewFlipper viewFlipperDemo;
    private GestureDetectorCompat gestureDetectorCompat=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        ListenerSwipe listenerSwipe=new ListenerSwipe();
        listenerSwipe.setMainActivity(this);
        gestureDetectorCompat=new GestureDetectorCompat(this,listenerSwipe);
    }

    private void init() {
        viewFlipperDemo= findViewById(R.id.viewFlipper_demo);
        //viewFlipperDemo.setFlipInterval(2000);
       // viewFlipperDemo.setAutoStart(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
