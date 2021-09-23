package etn.app.danghoc.appshopping_demo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ActivitySell extends AppCompatActivity implements View.OnClickListener {
    EditText mEdtNameProduct, mEdtPrice, mEdtDescription;
    ImageButton mImgBtnUploadImage,mImgBtnBack;
    ImageView mImgProduct;
    Button mBtnSave;
    final int REQUEST_CODE_FOLDER = 123;
    Bitmap bitmap;
    final String URL_INSERT_SANPHAM="http://192.168.1.8:8080/demobanhang/insertsellproduct.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell2);
        init();
    }

    private void init() {
        mEdtNameProduct = findViewById(R.id.edittext_nameproduct);
        mEdtDescription = findViewById(R.id.edittext_description);
        mEdtPrice = findViewById(R.id.edittext_price);
        mImgBtnUploadImage = findViewById(R.id.imageButton_uploadImage);
        mImgBtnUploadImage.setOnClickListener(this);
        mImgProduct = findViewById(R.id.imageview_product);
        mBtnSave = findViewById(R.id.button_post);
        mBtnSave.setOnClickListener(this);
        mImgBtnBack=findViewById(R.id.imageButton_back);
        mImgBtnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton_uploadImage:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
                break;
            case R.id.button_post:
                insertTableSanPham(URL_INSERT_SANPHAM);
                break;
            case R.id.imageButton_back:
                finish();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_CODE_FOLDER&&resultCode==RESULT_OK&&data!=null){
            Uri uri=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(uri);
                bitmap= BitmapFactory.decodeStream(inputStream);
                mImgProduct.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void insertTableSanPham(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivitySell.this, "loi"+error, Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<>();
                params.put("image",convertImageToString(bitmap));
                params.put("nameproduct",mEdtNameProduct.getText().toString());
                params.put("description",mEdtDescription.getText().toString());
                params.put("priceproduct",mEdtPrice.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

public String convertImageToString(Bitmap bitmap){
    ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
    byte[]imageBytes=outputStream.toByteArray();
    String encodedImage= Base64.encodeToString(imageBytes,Base64.DEFAULT);
    return encodedImage;
}





}




