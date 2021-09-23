package etn.app.danghoc.keobuabao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button buttonConfirm1,buttonPLayAgain1;
    CheckBox checkBoxKeo1,checkBoxBua1,checkBoxBao1;
    TextView textViewKetQua1,textViewMayChon,textViewScore1,textViewBestScore1;
    ImageView imageViewkeoP1,imageViewBuaP1,imageViewBaoP1,imageViewKeoR1,imageViewBuaR1,imageViewBaoR1;
   SharedPreferences sharedPreferences;

    int  checkR=-1;
    int checkP=-1; // 1: kéo ; 2: búa ; 3: bao //
    int score =0,bestScore=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        sharedPreferences=getSharedPreferences("dataScore",MODE_PRIVATE);
        bestScore=sharedPreferences.getInt("bestscore",0);
        textViewBestScore1.setText(bestScore+"");
         textViewMayChon.setVisibility(View.INVISIBLE);
         buttonPLayAgain1.setVisibility(View.INVISIBLE);
        //kéo
        checkBoxKeo1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxBao1.setChecked(false);
                checkBoxBua1.setChecked(false);
                checkP=1;
            }
        });

        // búa
        checkBoxBua1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxBao1.setChecked(false);
                checkBoxKeo1.setChecked(false);
                checkP=2;
            }
        });

        // bao
        checkBoxBao1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxKeo1.setChecked(false);
                checkBoxBua1.setChecked(false);
                checkP=3;
            }
        });

    buttonConfirm1.setOnClickListener(new View.OnClickListener() {
        @Override// 1: kéo ; 2: búa ; 3: bao
        public void onClick(View v) {
            if (checkBoxBao1.isChecked()==false && checkBoxBua1.isChecked()==false&&checkBoxKeo1.isChecked()==false) {
                Toast.makeText(MainActivity.this, "chưa chọn", Toast.LENGTH_SHORT).show();
            } else {
                Random random = new Random();
                checkR = random.nextInt(3) + 1;
                if (checkR == 1) { // kéo
                    imageViewKeoR1.setVisibility(View.VISIBLE);
                    imageViewBaoR1.setVisibility(View.INVISIBLE);
                    imageViewBuaR1.setVisibility(View.INVISIBLE);

                    textViewMayChon.setVisibility(View.VISIBLE);
                    textViewMayChon.setText("máy chọn kéo");
                    if (checkP == 2) {
                        textViewKetQua1.setText("You Win");
                        addScore();
                    }
                    if (checkP == 3) {
                        textViewKetQua1.setText("You Lost");
                        minus();
                    }
                }

                if (checkR == 2) { // búa
                    imageViewKeoR1.setVisibility(View.INVISIBLE);
                    imageViewBaoR1.setVisibility(View.INVISIBLE);
                    imageViewBuaR1.setVisibility(View.VISIBLE);

                    textViewMayChon.setVisibility(View.VISIBLE);
                    textViewMayChon.setText("máy chọn búa");

                    if (checkP == 1) {
                        textViewKetQua1.setText("You Lost");
                        minus();
                    }
                    if (checkP == 3) {
                        textViewKetQua1.setText("You Win");
                        addScore();
                    }
                }
// 1: kéo ; 2: búa ; 3: bao
                if (checkR == 3) { // bao
                    imageViewKeoR1.setVisibility(View.INVISIBLE);
                    imageViewBaoR1.setVisibility(View.VISIBLE);
                    imageViewBuaR1.setVisibility(View.INVISIBLE);

                    textViewMayChon.setVisibility(View.VISIBLE);
                    textViewMayChon.setText("máy chọn bao");

                    if (checkP == 2) {
                        textViewKetQua1.setText("You Lost");
                        minus();
                    }
                    if (checkP == 1) {
                        textViewKetQua1.setText("You Win");
                        addScore();
                    }
                }

                if (checkP == checkR) {
                    textViewKetQua1.setText("Hòa");
                }

                if(score<0){

                    buttonPLayAgain1.setVisibility(View.VISIBLE);
                    textViewMayChon.setText("Best Score"+bestScore);

                    buttonConfirm1.setVisibility(View.INVISIBLE);
                    checkBoxKeo1.setVisibility(View.INVISIBLE);
                    checkBoxBua1.setVisibility(View.INVISIBLE);
                    checkBoxBao1.setVisibility(View.INVISIBLE);

                    textViewKetQua1.setVisibility(View.INVISIBLE);

                    imageViewkeoP1.setVisibility(View.INVISIBLE);
                    imageViewBuaP1.setVisibility(View.INVISIBLE);
                    imageViewBaoP1.setVisibility(View.INVISIBLE);
                    imageViewKeoR1.setVisibility(View.INVISIBLE);
                    imageViewBuaR1.setVisibility(View.INVISIBLE);
                    imageViewBaoR1.setVisibility(View.INVISIBLE);
                }

            }
        }
    });
       //== sự kiện  nếu hết điểm


    buttonPLayAgain1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            buttonPLayAgain1.setVisibility(View.INVISIBLE);
            textViewKetQua1.setText("");
            score=0;
            textViewScore1.setText("0");

            buttonConfirm1.setVisibility(View.VISIBLE);
            checkBoxKeo1.setVisibility(View.VISIBLE);
            checkBoxBua1.setVisibility(View.VISIBLE);
            checkBoxBao1.setVisibility(View.VISIBLE);

            textViewMayChon.setVisibility(View.VISIBLE);

            imageViewkeoP1.setVisibility(View.VISIBLE);
            imageViewBuaP1.setVisibility(View.VISIBLE);
            imageViewBaoP1.setVisibility(View.VISIBLE);
            imageViewKeoR1.setVisibility(View.VISIBLE);
            imageViewBuaR1.setVisibility(View.VISIBLE);
            imageViewBaoR1.setVisibility(View.VISIBLE);;

        }
    });

    }

    //   ========== add Scoreee || || || || || || || || || || ||
    private void addScore(){
        score+=1;

        textViewScore1.setText(score+"");
        if(score>bestScore){
            bestScore=score;
            textViewBestScore1.setText(bestScore+"");
            SharedPreferences.Editor editor =sharedPreferences.edit();
            editor.putInt("bestscore",bestScore); //   bestScore=sharedPreferences.getInt("bestscore",0);

            editor.commit();
        }
    }
//== trừ minus
    private void minus(){
        score-=1;
        textViewScore1.setText(score+"");
    }



    private void anhXa(){
        buttonConfirm1=(Button) findViewById(R.id.buttonConfirm);
        buttonPLayAgain1=(Button) findViewById(R.id.buttonPlayAgain);

        checkBoxBao1=(CheckBox) findViewById(R.id.checkBoxBao);
        checkBoxBua1=(CheckBox) findViewById(R.id.checkBoxBua);
        checkBoxKeo1=(CheckBox) findViewById(R.id.checkBoxKeo);

        textViewKetQua1=(TextView) findViewById(R.id.textViewKetQua);
        textViewMayChon=(TextView) findViewById(R.id.textViewMayChon);
        textViewScore1=(TextView) findViewById(R.id.textViewScore);
        textViewBestScore1=(TextView) findViewById(R.id.textViewBestScore);

        imageViewBaoP1=(ImageView) findViewById(R.id.imageViewBaoPeople);
        imageViewBuaP1=(ImageView) findViewById(R.id.imageViewBuaPeople);
        imageViewkeoP1=(ImageView) findViewById(R.id.imageViewKeoPeople);

        imageViewKeoR1=(ImageView) findViewById(R.id.imageViewKeoRobot);
        imageViewBuaR1=(ImageView) findViewById(R.id.imageViewBuaRobot);
        imageViewBaoR1=(ImageView) findViewById(R.id.imageViewBaoRobot);

    }
}
