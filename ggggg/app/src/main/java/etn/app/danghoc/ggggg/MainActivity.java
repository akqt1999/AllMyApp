package etn.app.danghoc.ggggg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtC,edtF;
    Button btnC,btnF,btnClear;
    double c,f,kq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        edtC=findViewById(R.id.edt_celcius);
        edtF=findViewById(R.id.edt_fahrenheit);
        btnC=findViewById(R.id.btn_convert_to_c);
        btnC.setOnClickListener(this);
        btnF=findViewById(R.id.btn_convert_to_f);
        btnF.setOnClickListener(this);
        btnClear=findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(this);

         kq=0;
    }



    @Override
    public void onClick(View v) {
        DecimalFormat decimalFormat=new DecimalFormat("#.00");
        switch (v.getId()){
            case R.id.btn_clear:
                edtF.setText("");
                edtC.setText("");
                break;
            case R.id.btn_convert_to_f:
                c=Double.parseDouble(edtC.getText().toString());
                kq=c*1.8+32;
                edtF.setText(decimalFormat.format(kq)+"");
                break;
            case R.id.btn_convert_to_c:
                f=Double.parseDouble(edtF.getText().toString());
                kq= (f-32)*5/9;
               edtC.setText(decimalFormat.format(kq)+"");
               break;
        }
    }
}