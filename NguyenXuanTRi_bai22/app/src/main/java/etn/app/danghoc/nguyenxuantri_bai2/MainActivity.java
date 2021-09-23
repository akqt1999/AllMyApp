package etn.app.danghoc.nguyenxuantri_bai2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText edtTen,edtSoLuongSach,edtTongKH,edtTongKHVip,edtTongDoanhThu;
    CheckBox cbKHVip;
    TextView tvThanhTien;
    Button btnTinhTT,btnTiep,btnThongKe;
    ImageButton imgBtnClose;
    double tinhTien;
    double soLUong;
    int demKH=0,demVip=0;
    double tongDoanhThu=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btnTinhTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbKHVip.isChecked()){
                    soLUong=Double.parseDouble(edtSoLuongSach.getText().toString());
                    tinhTien=soLUong*20000*0.9;
                }else{
                    soLUong=Double.parseDouble(edtSoLuongSach.getText().toString());
                    tinhTien=soLUong*20000;
                }
                tvThanhTien.setText(tinhTien+"");
            }
        });
        btnTiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTen.setText("");
                edtSoLuongSach.setText("");
                edtTongDoanhThu.setText("");
                edtTongKHVip.setText("");
                edtTongKH.setText("");
                edtTen.requestFocus();
                demKH++;
                if(cbKHVip.isChecked()){
                    demVip++;
                }
                tongDoanhThu=tongDoanhThu+tinhTien;
            }
        });
        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTongKH.setText(demKH+"");
                edtTongKHVip.setText(demVip+"");
                edtTongDoanhThu.setText(tongDoanhThu+"");
            }
        });
        imgBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b=new AlertDialog.Builder(MainActivity.this);
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
        });
    }

    private void init() {
        edtTen=findViewById(R.id.edt_tenKhachHang);
        edtSoLuongSach=findViewById(R.id.edt_soLuong);
        edtTongKH=findViewById(R.id.edt_tong_soKH);
        edtTongKHVip=findViewById(R.id.edt_tong_soKH_vip);
        edtTongDoanhThu=findViewById(R.id.edt_tong_doanh_thu);

        tvThanhTien=findViewById(R.id.tv_thanhTien);
        cbKHVip=findViewById(R.id.cb_vip);

        btnThongKe=findViewById(R.id.btn_thongKe);
        btnTiep=findViewById(R.id.btn_tiep);
        btnTinhTT=findViewById(R.id.btn_tt);
        imgBtnClose=findViewById(R.id.imgBtn_close);
    }
}