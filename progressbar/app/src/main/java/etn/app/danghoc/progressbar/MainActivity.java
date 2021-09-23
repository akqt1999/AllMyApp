package etn.app.danghoc.progressbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
 ProgressBar progressBarDownload1 ;
 Button buttonDownload1 ,buttonDownloadSecond;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBarDownload1=(ProgressBar) findViewById(R.id.progressBarDownload);
        buttonDownloadSecond=(Button) findViewById(R.id.buttonDowloadBefore);
        buttonDownload1=(Button) findViewById(R.id.buttonDowload);

        //========= code
        buttonDownload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CountDownTimer countDownTimer=new CountDownTimer(10000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int current=progressBarDownload1.getProgress();
                        progressBarDownload1.setProgress(current+10);
                        if(current>=progressBarDownload1.getMax()){
                            progressBarDownload1.setProgress(0);
                        }
                    }

                    @Override
                    public void onFinish() {
                        Toast.makeText(MainActivity.this, "download finish", Toast.LENGTH_SHORT).show();
                    }

                };
               countDownTimer.start();
            }
        });
       //=========
        buttonDownloadSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current=progressBarDownload1.getSecondaryProgress();
                progressBarDownload1.setSecondaryProgress(current+10);
                if(progressBarDownload1.getSecondaryProgress()==100){
                    progressBarDownload1.setSecondaryProgress(0);
                }
            }
           });

    }
}
