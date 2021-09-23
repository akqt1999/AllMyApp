package etn.app.danghoc.syntask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
Button btnStart;
MyAsynTaks asynTaks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doStart();
            }
        });
    }

    private void doStart() {
        asynTaks =new MyAsynTaks(this);
        asynTaks.execute();
    }

    private void init() {
        btnStart=findViewById(R.id.btnStart);
    }
}