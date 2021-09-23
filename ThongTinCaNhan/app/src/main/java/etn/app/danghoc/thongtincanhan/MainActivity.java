package etn.app.danghoc.thongtincanhan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edtHoTen,edtCMND,edtThongTinBoSung;
   // RadioButton radioButtonTrungCap,radioButtonCaoDang,radioButtonDaiHoc;
    RadioButton radioButton;
    RadioGroup radioGroup;
    CheckBox checkBoxDocBao,checkBoxDocSach,checkBoxDOcCoding;
    Button btnGThongTin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        edtCMND=findViewById(R.id.edt_cmnd);
        edtHoTen=findViewById(R.id.edt_name);
        edtThongTinBoSung=findViewById(R.id.edt_thongTinBoSung);

//        radioButtonCaoDang=findViewById(R.id.radBtnCaoDang);
//        radioButtonTrungCap=findViewById(R.id.radBtnTrungCap);
//        radioButtonDaiHoc=findViewById(R.id.radBtnDaiHoc);
//        radioButtonDaiHoc.setSelected(true);

        radioGroup=findViewById(R.id.radioGroupBangCap);

        checkBoxDocBao=findViewById(R.id.checkboxDocBao);
        checkBoxDOcCoding=findViewById(R.id.checkboxDocCodinng);
        checkBoxDocSach=findViewById(R.id.checkboxDocSach);

        btnGThongTin=findViewById(R.id.btn_gui_thong_tin);
        btnGThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo();
            }
        });
    }

    private void showInfo(){
        String ten=edtHoTen.getText().toString();
        ten.trim();

        if(ten.length()<3){
            edtHoTen.requestFocus();
            edtHoTen.selectAll();
            Toast.makeText(this, "ten phai lon hon 3 ky tu", Toast.LENGTH_SHORT).show();
        }

        String cmnd=edtCMND.getText().toString();
        cmnd.trim();
        if(cmnd.length()!=9){
            edtCMND.requestFocus();
            edtCMND.selectAll();
            Toast.makeText(this, "CMND phai 9 ky tu", Toast.LENGTH_SHORT).show();
        }

        String bang="";
        int id=radioGroup.getCheckedRadioButtonId();
        if(id==-1){
            Toast.makeText(this, "phai chon bang cap", Toast.LENGTH_SHORT).show();
        }
        radioButton=findViewById(id);
        bang=radioButton.getText().toString();

        String soThich="";
        if(checkBoxDocBao.isChecked()){
            soThich+=checkBoxDocBao.getText().toString();
        }else if(checkBoxDocSach.isChecked()){
            soThich+=checkBoxDocSach.getText().toString();
        }else{
            soThich+=checkBoxDOcCoding.getText().toString();
        }
        String boxung="";
        boxung=edtThongTinBoSung.getText().toString();

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("thong tin ca nhan");
        builder.setPositiveButton("Dong", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        String mess=ten+"\n";
        mess+=cmnd+"\n";
        mess+=bang+"\n";
        mess+=soThich+"\n";
        mess+="_________________________"+"\n";
        mess+="thong tin bo sung"+"\n";
        mess+="_________________________"+"\n";
        mess+=boxung;
        builder.setMessage(mess);
        builder.create();
        if(cmnd.length()==9&&ten.length()>=3){
            builder.show();
        }


    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder b=new AlertDialog.Builder(this);
        b.setTitle("question");
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