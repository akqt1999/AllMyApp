package etn.app.danghoc.testlistviewdienthoai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
public class ActivitySub extends AppCompatActivity {
    TextView txtNamePhone;
    Bundle getBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        txtNamePhone=findViewById(R.id.txtNamePhone);
        getBundle=getIntent().getExtras();
        String namePhone=getBundle.getString("NAME");
        txtNamePhone.setText(namePhone);

    }
}