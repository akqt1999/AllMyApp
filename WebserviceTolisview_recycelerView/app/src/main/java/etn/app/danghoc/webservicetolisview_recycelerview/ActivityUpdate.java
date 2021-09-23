package etn.app.danghoc.webservicetolisview_recycelerview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class ActivityUpdate extends AppCompatActivity {
    EditText edtTen, edtDiaChi, edtNamSinh;
    int id = -1;
    String urlUpdate = "http://192.168.1.9:8080/androidwebservice/update.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        anhXa();
        Intent intent = getIntent();
        ItemStudent itemStudent = (ItemStudent) intent.getSerializableExtra("dataSinhVien");
        edtDiaChi.setText(itemStudent.getAddress());
        edtNamSinh.setText(itemStudent.getBirth());
        edtTen.setText(itemStudent.getName());
        id = itemStudent.getId();
    }

    public void buttonOk(View view) {
        ItemStudent itemStudent = MainActivity.studentList.get(AdapterStudent.positionEdit);
        if(edtTen.getText().toString().trim().isEmpty()||edtNamSinh.getText().toString().trim().isEmpty()||edtDiaChi.getText().toString().isEmpty()){
            Toast.makeText(this, "chua nhap du thong tin", Toast.LENGTH_SHORT).show();
        }else{
            itemStudent.setName(edtTen.getText().toString().trim());
            itemStudent.setBirth(edtNamSinh.getText().toString().trim());
            itemStudent.setAddress(edtDiaChi.getText().toString());
            updateStudent(urlUpdate);
            MainActivity.adapter.notifyDataSetChanged();
            finish();
        }

    }

    public void buttonCancel(View view) {
        finish();
    }

    public void updateStudent(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            Toast.makeText(ActivityUpdate.this, "cap nhap nhap thanh cong ", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(ActivityUpdate.this, "cap nhap that bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityUpdate.this, error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("aaa",error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idSV", String.valueOf(id));
                params.put("hotenSV", edtTen.getText().toString().trim());
                params.put("diachiSV", edtDiaChi.getText().toString().trim());
                params.put("namsinhSV", edtNamSinh.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void anhXa() {
        edtNamSinh = (EditText) findViewById(R.id.edit_text_namsinh);
        edtDiaChi = (EditText) findViewById(R.id.edit_text_diachi);
        edtTen = (EditText) findViewById(R.id.edit_text_ten);

    }
}
