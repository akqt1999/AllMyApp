package etn.app.danghoc.appshopping_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import etn.app.danghoc.appshopping_demo.DungChung.DungChung;

public class ActivityPaySuccess extends AppCompatActivity {
Button btnOk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);
        btnOk=findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DungChung.FINISH_ACTIVITY=true;
                finish();
            }
        });
    }
}