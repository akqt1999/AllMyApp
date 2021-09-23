package etn.app.danghoc.radio_button;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
   RadioGroup radioGroupTime ;
   RadioButton rbSang,rbTrua,rbChieu;
   Button buttonConfirm;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       radioGroupTime=(RadioGroup ) findViewById(R.id.radioButtonTime);
       buttonConfirm=(Button) findViewById(R.id.buttonConfirm);
       rbSang=(RadioButton) findViewById(R.id.radioButtonSang);
       rbTrua=(RadioButton) findViewById(R.id.radioButtonTrua);
       rbChieu=(RadioButton) findViewById(R.id.radioButtonChieu);

       //=========================
        radioGroupTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if(checkedId==R.id.radioButtonSang){
//                    Toast.makeText(MainActivity.this, "morning", Toast.LENGTH_SHORT).show();
//                }
//                if(checkedId==R.id.radioButtonTrua){
//                    Toast.makeText(MainActivity.this, "lunch", Toast.LENGTH_SHORT).show();
//                }
//                if(checkedId==R.id.radioButtonChieu){
//                    Toast.makeText(MainActivity.this, "afternoon", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(rbSang.isChecked()){
                    Toast.makeText(MainActivity.this, "moring", Toast.LENGTH_SHORT).show();
                }
                if(rbTrua.isChecked()){
                    Toast.makeText(MainActivity.this, "lunch", Toast.LENGTH_SHORT).show();
                }
                if(rbChieu.isChecked()){
                    Toast.makeText(MainActivity.this, "afternoon", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
