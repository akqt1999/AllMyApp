package etn.app.danghoc.hoctrentruongcannang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    Button btnChuanDoan;
    EditText edtTen,edtChieuCao,edtCanNang,edtBmi,edtChuanDoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btnChuanDoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double h=Double.parseDouble(edtChieuCao.getText().toString());
                double w=Double.parseDouble(edtCanNang.getText().toString());
                double bmi=w/Math.pow(h,2);
                String chuanDoan="";
                if(bmi<18){
                    chuanDoan="ban gay";
                }else if(bmi<=24.9){
                    chuanDoan="ban binh thuong";
                }else if(bmi<29.9){
                    chuanDoan="ban beo phi do 1";
                }else if(bmi<=34.9){
                    chuanDoan="ban beo phi do 2";
                }else {
                    chuanDoan="ban beo phi do 3";
                }
                DecimalFormat decimalFormat=new DecimalFormat("#.0");
                edtChuanDoan.setText(chuanDoan);
                edtBmi.setText(decimalFormat.format(bmi)+"");
            }
        });
    }

    private void init() {
        btnChuanDoan=findViewById(R.id.btn_tinh_bmi);

        edtTen=findViewById(R.id.edt_ten);
        edtChieuCao=findViewById(R.id.edt_chieu_cao);
        edtCanNang=findViewById(R.id.edt_cang_nang);
        edtChuanDoan=findViewById(R.id.edt_chuan_doan);
        edtBmi=findViewById(R.id.edt_bmi);
    }
}