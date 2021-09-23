package etn.app.danghoc.gamedua;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
SeekBar seekBarOne1,seekBartrue1,seekBarThree1;
Button buttonStart1,buttonPlayAgain;
CheckBox checkBoxOne1,checkBoxTrue1,checkBoxThree1;
TextView textViewScore1,textViewBestscore1,textViewBestScorePlayagainDisplay1,textViewScorePlayAgain1;
    int score=10,bestScore=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();

        buttonPlayAgain.setVisibility(View.INVISIBLE);
        textViewScorePlayAgain1.setVisibility(View.INVISIBLE);
        textViewBestScorePlayagainDisplay1.setVisibility(View.INVISIBLE);
        // runnn


        final CountDownTimer countDownTimer=new CountDownTimer(30000,100) {
            @Override
            public void onTick(long millisUntilFinished) {
                Random random=new Random();
                int ramdomOne= random.nextInt(5);
                int randomtrue= random.nextInt(5);
                int randomThree=random.nextInt(5);


                seekBarOne1.setProgress(seekBarOne1.getProgress()+ramdomOne);
                seekBartrue1.setProgress(seekBartrue1.getProgress()+randomtrue);
                seekBarThree1.setProgress( seekBarThree1.getProgress()+randomThree);

                if(seekBarThree1.getProgress()>=seekBarThree1.getMax()){
                    if(checkBoxThree1.isChecked()){
                        Toast.makeText(MainActivity.this, "you Win", Toast.LENGTH_SHORT).show();
                        score=score+5;

                    }
                    else {
                        score=score-5;

                        Toast.makeText(MainActivity.this, "you Lost", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(MainActivity.this, "Android Win", Toast.LENGTH_SHORT).show();

                    //==== ====== ========  add Score\
                    addScore();
                }


                if(seekBartrue1.getProgress()>=seekBartrue1.getMax()){

                    if(checkBoxTrue1.isChecked()){
                        score=score+5;
                        addScore();
                        Toast.makeText(MainActivity.this, "you Win", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        score=score-5;
                        Toast.makeText(MainActivity.this, "you Lost", Toast.LENGTH_SHORT).show();
                    }
                    addScore();
                    Toast.makeText(MainActivity.this, "Alian Win", Toast.LENGTH_SHORT).show();
                }


                if(seekBarOne1.getProgress()>=seekBarOne1.getMax()){
                    if(checkBoxOne1.isChecked()){
                        score=score+5;
                        Toast.makeText(MainActivity.this, "you Win", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        score=score-5;
                        Toast.makeText(MainActivity.this, "you Lost", Toast.LENGTH_SHORT).show();
                    }
                    addScore();
                    Toast.makeText(MainActivity.this, "Robot WIn", Toast.LENGTH_SHORT).show();
                }
                if(seekBarThree1.getProgress()>=seekBarThree1.getMax()||seekBartrue1.getProgress()>=seekBartrue1.getMax()||seekBarOne1.getProgress()>=seekBarOne1.getMax()){
                    this.cancel();
                    checkBoxOne1.setVisibility(View.VISIBLE);
                    checkBoxTrue1.setVisibility(View.VISIBLE);
                    checkBoxThree1.setVisibility(View.VISIBLE);
                    if(score==0){
                        textViewBestScorePlayagainDisplay1.setText(textViewBestscore1.getText());
                        buttonPlayAgain.setVisibility(View.VISIBLE);
                        textViewScorePlayAgain1.setVisibility(View.VISIBLE);
                        textViewBestScorePlayagainDisplay1.setVisibility(View.VISIBLE);
                        buttonPlayAgain.setVisibility(View.VISIBLE);


                        buttonPlayAgain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttonPlayAgain.setVisibility(View.INVISIBLE);
                                textViewScorePlayAgain1.setVisibility(View.INVISIBLE);
                                textViewBestScorePlayagainDisplay1.setVisibility(View.INVISIBLE);
                                   score=10;
                                   textViewScore1.setText(score+"");
                                buttonStart1.setVisibility(View.VISIBLE);
                            }
                        });
                    }else{
                        buttonStart1.setVisibility(View.VISIBLE);
                    }
                    // nếu nhấn vào play again sẽ ẩn 3 cái này hiện button start


                  //
                }
            }

            @Override
            public void onFinish() {

            }
        };

        //== set play again



 //======= Start == start
        buttonStart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBoxOne1.isChecked() || checkBoxTrue1.isChecked() || checkBoxThree1.isChecked()) {
                    seekBarOne1.setProgress(0);
                    seekBartrue1.setProgress(0);
                    seekBarThree1.setProgress(0);

                    buttonStart1.setVisibility(View.INVISIBLE);
                    countDownTimer.start();
                    checkBoxOne1.setVisibility(View.INVISIBLE);
                    checkBoxTrue1.setVisibility(View.INVISIBLE);
                    checkBoxThree1.setVisibility(View.INVISIBLE);
                }
                else {
                    Toast.makeText(MainActivity.this, "you have not choose", Toast.LENGTH_SHORT).show();
                }
                
            }
        });
        
        //=== checkbox Checkbox ==========
        checkBoxOne1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                checkBoxTrue1.setChecked(false);
                checkBoxThree1.setChecked(false);
            }

        });

        checkBoxTrue1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                checkBoxOne1.setChecked(false);
                checkBoxThree1.setChecked(false);
            }

        });
        checkBoxThree1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               
                checkBoxOne1.setChecked(false);
                checkBoxTrue1.setChecked(false);
            }

    });

}
    private void anhXa(){
        seekBarOne1 =(SeekBar) findViewById(R.id.seekBarOne);
        seekBartrue1=(SeekBar) findViewById(R.id.seekBarTrue);
        seekBarThree1=(SeekBar) findViewById(R.id.seekBarThree);

        buttonStart1=(Button) findViewById(R.id.buttonStart);
        buttonPlayAgain=(Button) findViewById(R.id.buttonPlayAgain) ;

        checkBoxOne1=(CheckBox) findViewById(R.id.checkBoxOne);
        checkBoxTrue1=(CheckBox) findViewById(R.id.checkBoxTrue);
        checkBoxThree1=(CheckBox) findViewById(R.id.checkBoxThree);

        textViewScore1=(TextView) findViewById(R.id.textViewScoreDisplay);
        textViewBestscore1=(TextView) findViewById(R.id.textViewBestScoreDisplay);
        textViewBestScorePlayagainDisplay1=(TextView) findViewById(R.id.textViewBestScorePlayAgainDisplay);
        textViewScorePlayAgain1=(TextView) findViewById(R.id.textViewBestScorePlayAgain);

    }

    // cong diem
    private void addScore(){
        //==== ====== ========  add Score\
        if(score>bestScore){
            bestScore=score;
        }
        textViewScore1.setText(score+"");
        textViewBestscore1.setText(bestScore+"");
    }

}
