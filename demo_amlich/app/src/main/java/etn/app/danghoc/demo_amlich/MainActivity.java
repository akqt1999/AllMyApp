package etn.app.danghoc.demo_amlich;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
Button btnChuyenDoi;
EditText edtNamDuong;
TextView txtNamAm;
int namDuong=0;
String can="",chi="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btnChuyenDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namDuong = Integer.parseInt(edtNamDuong.getText().toString());
                chuyenCan();
                chuyenChi();
                txtNamAm.setText(can+" "+chi);
            }
        });
    }

    private void chuyenChi() {
        switch (namDuong%12){
            case 0 :chi="than";
            break;
            case 1 :chi="dau";
                break;
            case 2 :chi="tuat";
                break;
            case 3 :chi="hoi";
                break;
            case 4 :chi="ty";
                break;
            case 5 :chi="suu";
                break;
            case 6 :chi="dan";
                break;
            case 7 :chi="meo";
                break;
            case 8 :chi="thin";
                break;
            case 9 :chi="ti";
                break;
            case 10 :chi="ngo";
                break;
            case 11 :chi="mui";
                break;
        }
    }

    private void chuyenCan() {
            switch (namDuong%10){
                case 0:can="canh";
                    break;
                case 1 :can="tan";
                    break;
                case 2 :can="nham";
                    break;
                case 3 :can="quy";
                    break;
                case 4 :can="giap";
                    break;
                case 5 :can="at";
                    break;
                case 6 :can="binh";
                    break;
                case 7 :can="dinh";
                    break;
                case 8 :can="mau";
                    break;
                case 9 :can="ky";
                    break;
            }
        }



    private void init() {
        btnChuyenDoi=findViewById(R.id.btn_chuyendoi);
        edtNamDuong=findViewById(R.id.edt_namduong);
        txtNamAm=findViewById(R.id.txt_amlich);
    }
}