package etn.app.danghoc.webservicetolisview_recycelerview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ActivityAdd extends AppCompatActivity {
EditText edtTen,edtDiaChi,edtNamSinh;
String urlInsert="http://192.168.1.9:8080/androidwebservice/insert.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        anhXa();

    }

    private void anhXa(){
        edtNamSinh=(EditText)findViewById(R.id.edit_text_namsinh);
        edtDiaChi=(EditText)findViewById(R.id.edit_text_diachi);
        edtTen=(EditText)findViewById(R.id.edit_text_ten);

    }

    public void buttonOk(View view) {
    if(edtTen.getText().toString().trim().isEmpty()||edtDiaChi.getText().toString().trim().isEmpty()||edtNamSinh.getText().toString().trim().isEmpty()){
        Toast.makeText(this, "chua nhap du thong tin", Toast.LENGTH_SHORT).show();
    }else {
        themSinhVienVaoDatabase(urlInsert);
        MainActivity.studentList.add(new ItemStudent(edtTen.getText().toString().trim(),edtDiaChi.getText().toString().trim(),edtNamSinh.getText().toString().trim()));
        MainActivity.adapter.notifyDataSetChanged();
        finish();
    }

    }

    public void buttonCancel(View view) {
        finish();
    }

    private void themSinhVienVaoDatabase(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              if(response.equals("success")){
                  Toast.makeText(ActivityAdd.this, "them thanh cong", Toast.LENGTH_SHORT).show();
              }else{
                  Toast.makeText(ActivityAdd.this, "them that bai", Toast.LENGTH_SHORT).show();
              }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityAdd.this, "loi them", Toast.LENGTH_SHORT).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params=new HashMap<>();
                params.put("hotenSV",edtTen.getText().toString().trim());
                params.put("namsinhSV",edtNamSinh.getText().toString().trim());
                params.put("diachiSV",edtDiaChi.getText().toString().trim());


                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


}
