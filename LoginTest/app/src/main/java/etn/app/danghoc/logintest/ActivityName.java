package etn.app.danghoc.logintest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import etn.app.danghoc.logintest.Retrofit2.APIUtil;
import etn.app.danghoc.logintest.Retrofit2.DataClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityName extends AppCompatActivity {
    TextView mTvName;
    Button mBtnLogin,mBtnLogout,mBtnDelete;
    CheckLogin checkLogin;
    ImageView imgFace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        checkLogin=new CheckLogin(getApplication());
      //  arrUser=new ArrayList<>();

        init();
        getData();
        mTvName.setText("name : "+checkLogin.getKeyNameUser());
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityName.this, ActivitySignUp.class));
                finish();
            }
        });

        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin.setKeyLogin(false);
                checkLogin.setKeyNameUser("");
                startActivity(new Intent(ActivityName.this, ActivitySignUp.class));
                finish();
            }
        });



        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameFolder=checkLogin.getKeyUrlImage();
                nameFolder=nameFolder.substring(nameFolder.lastIndexOf("/"));
                DataClient dataClient= APIUtil.getData();
                Call<String>callBack=dataClient.Delete(checkLogin.getKeyNameUser(),nameFolder);
                callBack.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.body().equalsIgnoreCase("success")){
                            Toast.makeText(ActivityName.this, "xoa thanh cong", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ActivityName.this, "xoa khong thanh cong", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(ActivityName.this, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void getData(){
        DataClient dataClient=APIUtil.getData();
        Call<List<User>>callBack=dataClient.GetUer();
        callBack.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
            ArrayList<User>arrUser= (ArrayList<User>) response.body();
                if(arrUser!=null){
                    Toast.makeText(ActivityName.this, arrUser.get(0).getTaikhoan(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ActivityName.this, "null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(ActivityName.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        if(!checkLogin.isCheck()){
            Toast.makeText(this, "chua dang nhap", Toast.LENGTH_SHORT).show();
        }
        mBtnLogin=findViewById(R.id.btn_login);
        mTvName=findViewById(R.id.txt_name);
        mBtnLogout =findViewById(R.id.button_logout);
        mBtnDelete=findViewById(R.id.button_delete);
        imgFace=findViewById(R.id.img_face);
// Glide.with(context).load(itemSanPham.getUrlImage()).centerCrop().override(200,200).into(holder.mImgProduct)
        Glide.with(this).load(checkLogin.getKeyUrlImage()).centerCrop().override(150,150).into(imgFace);

    }
}
