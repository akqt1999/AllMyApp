package etn.app.danghoc.truyen_data_hai_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActyvityB extends AppCompatActivity {
   TextView textViewTitle,textViewContent;
   Button buttonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_actyvity_b);
        anhXa();
     //showByExtra();
     showbyBundle();

     buttonBack.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(MainActyvityB.this,MainActivity.class);
             startActivity(intent);
         }
     });
    }

    // ánh xạ
    void anhXa(){
        textViewContent=(TextView) findViewById(R.id.textView_content);
        textViewTitle=(TextView) findViewById(R.id.textView_title);
        buttonBack=(Button) findViewById(R.id.button_back);
    }

    void showByExtra(){
        Intent intent=getIntent();
         String title=intent.getStringExtra(MainActivity.TITLE);
         String content=intent.getStringExtra(MainActivity.CONTENT);
         textViewTitle.setText(title);
         textViewContent.setText(content);
    }

    // show by bundle
    void showbyBundle(){
        Intent intent=getIntent();
          Bundle bundle=intent.getBundleExtra(MainActivity.BUNDLE);

        String content=bundle.getString(MainActivity.CONTENT);
        String title=bundle.getString(MainActivity.TITLE);
        textViewTitle.setText(title);
        textViewContent.setText(content);

    }

}
