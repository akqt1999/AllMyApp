package etn.app.danghoc.intentcallsms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ActivityCall extends AppCompatActivity {
 EditText edtNumber;
 Button btnBack;
 ImageButton imgBtnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        init();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgBtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+edtNumber.getText().toString()));
                if(ActivityCompat.checkSelfPermission(ActivityCall.this,
                        Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ActivityCall.this,new String[]{
                            Manifest.permission.CALL_PHONE},1);
                    return;

                }
                startActivity(callIntent);
            }
        });
    }

    private void init() {
        edtNumber=findViewById(R.id.edt_phone_number);
        btnBack=findViewById(R.id.btn_back);
        imgBtnCall=findViewById(R.id.imgBtn_call);

    }
}