package etn.app.danghoc.testactioincall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;

public class ChildActivity extends AppCompatActivity {
    EditText edtKq;
    Button btnBack;
    DecimalFormat decimalFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
        edtKq=findViewById(R.id.edt_kq);
        btnBack=findViewById(R.id.btn_back);

        Intent callIntent=getIntent();
        Bundle bundle=callIntent.getBundleExtra("pt");
        double a=bundle.getDouble("a");
        double b=bundle.getDouble("b");
        String kq="";
        if(a==0&&b==0){
            kq="pt vo so nghiem";
        }else if(a==0&&b!=0){
            kq="vo nghiem";
        }else{
             decimalFormat=new DecimalFormat("0.##");
            kq=decimalFormat.format(-b/a);
        }
        edtKq.setText(kq);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}