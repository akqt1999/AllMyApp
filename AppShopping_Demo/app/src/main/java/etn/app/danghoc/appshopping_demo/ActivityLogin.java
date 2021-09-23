package etn.app.danghoc.appshopping_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class ActivityLogin extends AppCompatActivity implements View.OnClickListener {
    EditText edtNameUser,edtPassWord;
    Button btnLogin,btnSignup;
    CheckLogin checkLogin;
    OnCallBackk listen;
    ImageButton imgBtnHidePassWord,imgBtnShowPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        edtNameUser=findViewById(R.id.edittext_name_user);
        edtPassWord=findViewById(R.id.edittex_password);
        btnLogin=findViewById(R.id.button_login); btnLogin.setOnClickListener(this);
        btnSignup=findViewById(R.id.button_signup);btnSignup.setOnClickListener(this);
        checkLogin=new CheckLogin(getApplicationContext());

        imgBtnHidePassWord=findViewById(R.id.image_button_hide_password);imgBtnHidePassWord.setOnClickListener(this);
        imgBtnShowPassword=findViewById(R.id.image_button_show_password);imgBtnShowPassword.setOnClickListener(this);

        imgBtnHidePassWord.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_login:
                loginUser(Url.URL_LOGIN);
                break;
            case R.id.button_signup :
                startActivity(new Intent(this,ActivitySignup.class));
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
    public void setListen(OnCallBackk listen) {
        this.listen = listen;
    }

    private void loginUser(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("fail")){
                    Toast.makeText(ActivityLogin.this, "login fail", Toast.LENGTH_SHORT).show();
                }else{
                    checkLogin.setIsLogin(true);
                    checkLogin.setNameUser(response);
                // listen.onLogin(5);
                   ContainFragment.checkOnFragmentDashboardInLogin=true;
                    finish();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityLogin.this, "loi\n"+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String>params=new HashMap<>();
                    params.put("name",edtNameUser.getText().toString());
                    params.put("password",edtPassWord.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }





}
