package etn.app.danghoc.checkbookkk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
  CheckBox cbAndoid,cbIos,cbWindown;
  ConstraintLayout ctloHinhNen;
  Button btConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();


        cbAndoid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                   // Toast.makeText(MainActivity.this,"this is android",Toast.LENGTH_LONG).show();
                }
            }

        });

       cbIos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked){
                  // Toast.makeText(MainActivity.this, "this is ios", Toast.LENGTH_SHORT).show();
               }
           }
       });
       cbWindown.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//               if(isChecked){
//                   ctloHinhNen.setBackgroundColor(Color.BLUE);
//               }
//               else {
//                   ctloHinhNen.setBackgroundResource(R.drawable.hinhnendt);
//               }
           }
       });
       btConfirm.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(cbAndoid.isChecked()){
                   Toast.makeText(MainActivity.this, "you choose android", Toast.LENGTH_SHORT).show();
               }
                if(cbIos.isChecked()){
                   Toast.makeText(MainActivity.this, "you choose ios", Toast.LENGTH_SHORT).show();
               }
                if(cbWindown.isChecked()){
                   ctloHinhNen.setBackgroundResource(R.drawable.hinhnendt);
               }
               if(cbWindown.isChecked()==false){
                   ctloHinhNen.setBackgroundColor(Color.GREEN);
               }
           }
       });
    }

     private void AnhXa(){
        cbAndoid=(CheckBox)findViewById(R.id.checkBoxAndroid);
        cbIos=(CheckBox) findViewById(R.id.checkBoxIos);
        cbWindown=(CheckBox) findViewById(R.id.checkBoxWindown);
         ctloHinhNen=(ConstraintLayout) findViewById(R.id.hinhnen);
        btConfirm=(Button) findViewById(R.id.buttonConfirm);
    }

}
