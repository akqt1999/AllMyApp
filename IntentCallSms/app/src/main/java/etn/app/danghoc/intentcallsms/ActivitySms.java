package etn.app.danghoc.intentcallsms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ActivitySms extends AppCompatActivity {
    EditText edtNumber;
    Button btnBack;
    ImageButton imgBtnSms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        init();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgBtnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent=new Intent(Intent.ACTION_SENDTO,
                        Uri.parse("smsto:"+edtNumber.getText().toString()));
                startActivity(smsIntent);
            }
        });
    }

    private void init() {
        edtNumber=findViewById(R.id.edt_phone_number);
        btnBack=findViewById(R.id.btn_back);
        imgBtnSms=findViewById(R.id.imgBtn_sms);

    }
}