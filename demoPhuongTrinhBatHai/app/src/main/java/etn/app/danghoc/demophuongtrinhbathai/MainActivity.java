package etn.app.danghoc.demophuongtrinhbathai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
Button btnTiepTuc,btnGiaiPt,btnThoat;
EditText edtA,edtB,edtC;
TextView txtKq;
String kq="";
int a,b,c;
    double detal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btnGiaiPt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=Integer.parseInt(edtA.getText().toString());
                b=Integer.parseInt(edtB.getText().toString());
                c=Integer.parseInt(edtC.getText().toString());
                DecimalFormat decimalFormat=new DecimalFormat("#.00");
                if(a==0){
                    if(b==0){
                        if(c==0){
                            kq="pt vo so nghiem";
                        }else{
                            kq="pt vo nghiem";
                        }
                    }else{
                        kq="pt co 1 No , x="+decimalFormat.format(-c/b);
                    }
                }else{
                    detal=b*b-4*a*c;
                    if(detal<0){
                        kq="pt vo nghiep";
                    }else if(detal==0){
                        kq="pt co nghiem kep x1=x2="+decimalFormat.format(-b/(2*a));
                    }else{
                        kq="pt co 2 nghiem x1="+decimalFormat.format((-b+Math.sqrt(detal))/(2*a))+"," +
                                "x2="+decimalFormat.format((-b-Math.sqrt(detal))/(2*a));
                    }
                }
                txtKq.setText(kq);
            }
        });
    btnTiepTuc.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            edtA.setText("");
            edtB.setText("");
            edtC.setText("");
            edtA.requestFocus();
        }
    });
    btnThoat.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });
    }

    private void init() {
       btnGiaiPt=findViewById(R.id.btn_giaipt);
       btnTiepTuc=findViewById(R.id.btn_tieptuc);
       btnThoat=findViewById(R.id.btn_thoat);

       edtA=findViewById(R.id.edt_a);
       edtB=findViewById(R.id.edt_b);
       edtC=findViewById(R.id.edt_c);

       txtKq=findViewById(R.id.txtKetQua);

    }
}