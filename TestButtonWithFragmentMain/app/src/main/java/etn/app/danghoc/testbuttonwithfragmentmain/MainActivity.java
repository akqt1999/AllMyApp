package etn.app.danghoc.testbuttonwithfragmentmain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Lisener {
    Button mBt;
    Lisener listener;
    boolean chekOn=false; // false is hide , true is show
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBt = findViewById(R.id.button_test);
        mBt.setVisibility(View.INVISIBLE);
        addFragment();
        mBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isChekOn()){
                    listener.checkOnCick(false);
                    setChekOn(!isChekOn());
                }else {
                    listener.checkOnCick(true);
                    setChekOn(!isChekOn());
                }
            }
        });

    }


    private void addFragment() {
        FragmetTest fragmentTest = new FragmetTest();
        fragmentTest.setLisener(this);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_content, fragmentTest).commit();
    }

    @Override
    public void checkOnCick(boolean vv) {
        if (vv) {
            mBt.setVisibility(View.VISIBLE);
        } else {
            mBt.setVisibility(View.INVISIBLE);
        }
    }

    public void setListener(Lisener lisener) {
        this.listener = lisener;
    }

    public boolean isChekOn() {
        return chekOn;
    }

    public void setChekOn(boolean chekOn) {
        this.chekOn = chekOn;
    }
}
