package etn.app.danghoc.listviewdienthoai22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import etn.app.danghoc.listviewdienthoai22.R;

public class ActivitySub extends AppCompatActivity {
    TextView txtNamePhone,txtGia;
    Bundle getBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        txtNamePhone=findViewById(R.id.txtNamePhone);
        txtGia=findViewById(R.id.txtGia);
        getBundle=getIntent().getExtras();
        String namePhone=getBundle.getString("NAME");
        String gia="gia ban : "+getBundle.getString("GIA");
        txtNamePhone.setText(namePhone);
        txtGia.setText(gia);


    }
}
