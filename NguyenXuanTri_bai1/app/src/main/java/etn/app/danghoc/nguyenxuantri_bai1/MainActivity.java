package etn.app.danghoc.nguyenxuantri_bai1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    EditText edtVnd,edtNgoaiTe;
    Button btnVNDToNgoaiTe,btnNgoaiTeTOVND,btnClear;
    RadioButton radioButtonUsd,radioButtonEur,radioButtonJpy;
    RadioGroup radioGroup;
    DecimalFormat decimalFormat;
    int id=0;
    double vnd,ngoaiTe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        decimalFormat=new DecimalFormat("#.0");
        init();
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vnd=0;
                edtNgoaiTe.setText("");
                edtVnd.setText("");
            }
        });
        btnVNDToNgoaiTe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vnd=0;
                vnd=Double.parseDouble(edtVnd.getText().toString()) ;
                if(kiemTraId().equalsIgnoreCase("usd")){
                    vnd=vnd/22280;
                }else if(kiemTraId().equalsIgnoreCase("eur")){
                    vnd=vnd/24280;
                }else{
                    vnd=vnd/204;
                }
                edtNgoaiTe.setText(decimalFormat.format(vnd)+"");
            }
        });

        btnNgoaiTeTOVND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vnd=0;
                vnd=Double.parseDouble(edtNgoaiTe.getText().toString()) ;
                if(kiemTraId().equalsIgnoreCase("usd")){
                    vnd=vnd*22280;
                }else if(kiemTraId().equalsIgnoreCase("eur")){
                    vnd=vnd*24280;
                }else{
                    vnd=vnd*204;
                }
                edtVnd.setText(decimalFormat.format(vnd)+"");
            }
        });
    }

    private void init() {
        edtNgoaiTe=findViewById(R.id.edt_Ngoai_te);
        edtVnd=findViewById(R.id.edt_vnDong);
        btnNgoaiTeTOVND=findViewById(R.id.btn_ngoaite_to_vnd);
        btnVNDToNgoaiTe=findViewById(R.id.btn_vnd_to_nogaite);
        btnClear=findViewById(R.id.btn_clear);
        radioGroup=findViewById(R.id.radioGroup);

        radioButtonEur=findViewById(R.id.radBtnEur);
        radioButtonJpy=findViewById(R.id.radBtnJpy);
        radioButtonUsd=findViewById(R.id.radBtnUsd);

    }

    private String kiemTraId(){
        id=radioGroup.getCheckedRadioButtonId();
        if(id==R.id.radBtnUsd){
            return "usd";
        }else if(id==R.id.radBtnEur){
            return "eur";
        }else {
            return "jpy";
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder b=new AlertDialog.Builder(this);
        b.setTitle("question?");
        b.setMessage("ban co muon thoat?");
        b.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        b.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        b.create().show();
    }
}