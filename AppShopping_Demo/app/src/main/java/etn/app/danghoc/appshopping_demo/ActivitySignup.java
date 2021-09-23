package etn.app.danghoc.appshopping_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

import etn.app.danghoc.appshopping_demo.url.Url;

public class ActivitySignup extends AppCompatActivity implements View.OnClickListener {
        EditText edtNameUser,edtPassWord,edtPassWordAgain;
        Button btnSignUp,btnLogin;
        ImageButton imgBtnHidePassWord,imgBtnShowPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
    }

    private void init() {
        edtNameUser=findViewById(R.id.edittext_name_user);
        edtPassWord=findViewById(R.id.edittext_password);
        edtPassWordAgain=findViewById(R.id.edittext_password_again);
        btnLogin=findViewById(R.id.button_login);btnLogin.setOnClickListener(this);
        btnSignUp=findViewById(R.id.button_signup);btnSignUp.setOnClickListener(this);
        imgBtnHidePassWord=findViewById(R.id.image_button_hide_password);imgBtnHidePassWord.setOnClickListener(this);
        imgBtnShowPassword=findViewById(R.id.image_button_show_password);imgBtnShowPassword.setOnClickListener(this);

        imgBtnHidePassWord.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_signup:
                if(isDuplicatePassword()){
                    signup(Url.URL_SIGNUP);
                }else{
                    Toast.makeText(this, "password not duplicate", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_login:
                finish();
                break;
            case R.id.image_button_hide_password :
                imgBtnShowPassword.setVisibility(View.VISIBLE);
                imgBtnHidePassWord.setVisibility(View.INVISIBLE);
                edtPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                break;
            case R.id.image_button_show_password:
                imgBtnShowPassword.setVisibility(View.INVISIBLE);
                imgBtnHidePassWord.setVisibility(View.VISIBLE);
                edtPassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                break;
        }
    }

    public boolean isDuplicatePassword(){
        if(edtPassWordAgain.getText().toString().trim().equalsIgnoreCase(edtPassWord.getText().toString().trim())){
            return true;
        }else{
            return false;
        }
    }

    public void signup(String url){
        final RequestQueue requestQueue= Volley.newRequestQueue(this);
     StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
         @Override
         public void onResponse(String response) {
             if(response.equalsIgnoreCase("duplicatename")){
                 Toast.makeText(ActivitySignup.this, "duplicate name user ", Toast.LENGTH_SHORT).show();
             }else if(response.equalsIgnoreCase("success")){

             }else{
                 Toast.makeText(ActivitySignup.this, "create do not success", Toast.LENGTH_SHORT).show();
             }
         }
     }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {
             Toast.makeText(ActivitySignup.this, "loi \n"+error.toString(), Toast.LENGTH_SHORT).show();
         }
     }
     ){
         @Override
         protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> params=new HashMap<>();
            params.put("name",edtNameUser.getText().toString().trim());
            params.put("password",edtPassWord.getText().toString().trim());
            params.put("email","conket@gmail.com");
             return params;
         }
     };
     requestQueue.add(stringRequest);
    }
}
