package etn.app.danghoc.sevicenhac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    ImageButton imgBtnPlay,imgBtnStop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        imgBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(getBaseContext(), MediaPlayerService.class));
            }
        });
        imgBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(getBaseContext(),MediaPlayerService.class));
                finish();
            }
        });
    }

    private void init() {
        imgBtnPlay=findViewById(R.id.btn_play);
        imgBtnStop=findViewById(R.id.btn_stop);
    }

}