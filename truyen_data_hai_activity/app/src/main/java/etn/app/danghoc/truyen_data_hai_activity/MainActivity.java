package etn.app.danghoc.truyen_data_hai_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
   EditText  editTextTitle,editTextContent;
   Button buttonSend,buttonShow;
   Intent   intent;
   public static final String TITLE="t";
   public static final String CONTENT="c";
   public static final String  BUNDLE="b";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         anhXa();
        intent=new Intent(MainActivity.this,MainActyvityB.class);

     buttonSend.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             //sendByExtra();
             sendByBundle();
         }
     });

     buttonShow.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             startActivity(intent);
         }
    });

    }


    // ánh xa
    private void  anhXa(){
        editTextContent= findViewById(R.id.editText_content);
        editTextTitle=(EditText) findViewById(R.id.editText_title);

        buttonSend=(Button) findViewById(R.id.button_send);
        buttonShow=(Button) findViewById(R.id.button_show);
    }

    private void sendByExtra(){
        intent=new Intent(MainActivity.this,MainActyvityB.class);
        String title=editTextTitle.getText().toString();
        String content=editTextContent.getText().toString();

        intent.putExtra(TITLE,title);
        intent.putExtra(CONTENT,content);
       // startActivity(intent);

    }

    // su dung bundle
    private void sendByBundle(){
        intent=new Intent(MainActivity.this,MainActyvityB.class);
        Bundle bundle=new Bundle();
        String title=editTextTitle.getText().toString();
        String contten=editTextContent.getText().toString();
        bundle.putString(TITLE,title);
        bundle.putString(CONTENT,contten);
        intent.putExtra(BUNDLE,bundle);
    }


}
