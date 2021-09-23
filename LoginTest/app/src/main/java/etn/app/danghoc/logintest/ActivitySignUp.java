package etn.app.danghoc.logintest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
//ngulon@gmail.com

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import etn.app.danghoc.logintest.Retrofit2.APIUtil;
import etn.app.danghoc.logintest.Retrofit2.DataClient;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySignUp extends AppCompatActivity {
        private EditText mEdtName,mEdtPassWord,mEdtEmail;
        private Button mBtnLogin,mBtnSignUp;
        private ImageView imgFace;
        private String nameUser;
        private final String ipWifi="192.168.1.7";
        private final String urlSignUp ="http://"+ipWifi+":8080/demobanhang/signupuser.php";
        private final String urlLogIn="http://"+ipWifi+":8080/demobanhang/loginuser.php";
        CheckLogin checkLogin;
        static final int REQUEST_CODE_IMAGE=123;
        String realPath="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();





    }

    private void init() {
        mEdtEmail =findViewById(R.id.edt_email);
        mEdtPassWord =findViewById(R.id.edt_password);
        mEdtName =findViewById(R.id.edt_name);
        imgFace=findViewById(R.id.img_face);
        imgFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(ActivitySignUp.this,Manifest.permission.READ_EXTERNAL_STORAGE)) {// khong hien lai khi cho phep roi
//                    Toast.makeText(ActivitySignUp.this, "permanently denied", Toast.LENGTH_SHORT).show();
//                    Intent intent=new Intent(Intent.ACTION_PICK);
//                    intent.setType("image/*");
//                    startActivityForResult(intent,REQUEST_CODE_IMAGE);
//                }else{
                    ActivityCompat.requestPermissions(ActivitySignUp.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_IMAGE);
//                }



            }
        });


        mBtnLogin=findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEdtName.getText().toString().length()>0&&mEdtPassWord.getText().toString().length()>0){
                     DataClient dataClient=APIUtil.getData();
                     Call<List<User>>callBack=dataClient.Login(mEdtName.getText().toString(),mEdtPassWord.getText().toString());
                     callBack.enqueue(new Callback<List<User>>() {
                         @Override
                         public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                             ArrayList<User>arrUser= (ArrayList<User>) response.body();
                             if(arrUser.size()>0){
                                 checkLogin.setKeyLogin(true);
                                 checkLogin.setKeyNameUser(arrUser.get(0).getTaikhoan());
                                 checkLogin.setKeyUrlImage(arrUser.get(0).getImage());
                                    startActivity(new Intent(ActivitySignUp.this,ActivityName.class));
                                    finish();
                             }else{
                                 Toast.makeText(ActivitySignUp.this, "name or password wrong", Toast.LENGTH_SHORT).show();
                             }
                         }

                         @Override
                         public void onFailure(Call<List<User>> call, Throwable t) {

                         }
                     });
                }

            }
        });
        checkLogin=new CheckLogin(getApplication());

        mBtnSignUp=findViewById(R.id.btn_confirm);
        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //signUpUser(urlSignUp);
                File file=new File(realPath);
                String file_path=file.getAbsolutePath();
                String []arrNameFile=file_path.split("\\.");
                file_path=arrNameFile[0]+System.currentTimeMillis()+"."+arrNameFile[1];
                 RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/from-data"),file);
                MultipartBody.Part body=MultipartBody.Part.createFormData("upload_file",file_path,requestBody);
                DataClient dataClient= APIUtil.getData();
                Call<String>callBack=dataClient.UploadImage(body);

                callBack.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response!=null){
                            String message=response.body();
                            if(message.length()>0){
                                DataClient insertData=APIUtil.getData();
                                Call<String>callBack=insertData.InsertData(mEdtName.getText().toString()
                                                                            ,mEdtPassWord.getText().toString()
                                                                            ,APIUtil.Base_Url+"image/"+message );
                                callBack.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        if(response.body().equalsIgnoreCase("success")){
                                            Toast.makeText(ActivitySignUp.this, "dang ky thanh cong", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(ActivitySignUp.this, "dang ky that bai", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Toast.makeText(ActivitySignUp.this, t.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("aaa","loi"+t.getMessage());
                    }
                });

            }
        });
    }

    private boolean checkEmptyEdt(){
        if(mEdtName.getText().toString().length()>0&&mEdtPassWord.getText().length()>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_CODE_IMAGE&&resultCode==RESULT_OK&&data!=null){
            Uri uri=data.getData();
            realPath=getRealPathFromURI(uri);
            try {
                InputStream inputStream=getContentResolver().openInputStream(uri);
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                imgFace.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


            switch (requestCode) {

                case REQUEST_CODE_IMAGE:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, REQUEST_CODE_IMAGE);
                    } else {
                        Toast.makeText(this, "ban khong cho quyen ", Toast.LENGTH_SHORT).show();
                    }


            }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


//    public void signUpUser(String url){
//        RequestQueue requestQueue= Volley.newRequestQueue(this);
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if(response.equals("success")){
//                    Toast.makeText(ActivitySignUp.this, "success", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(ActivitySignUp.this, "fail", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(ActivitySignUp.this, "loi\n"+error, Toast.LENGTH_SHORT).show();
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String>params=new HashMap<>();
//                params.put("name",mEdtName.getText().toString());
//                params.put("password",mEdtPassWord.getText().toString());
//                params.put("email",mEdtEmail.getText().toString());
//                return params;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }

   /* public void loginUser(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               nameUser=response;
               //Toast.makeText(MainActivity.this, nameUser, Toast.LENGTH_SHORT).show();7
                if(nameUser!=null){
                    if(nameUser.equals("fail")){
                        Toast.makeText(ActivitySignUp.this, "wrong password or wrong user name", Toast.LENGTH_SHORT).show();
                    }else{
                        ActivityName.NAME=nameUser;
                        checkLogin.setKeyLogin(true);
                        checkLogin.setKeyNameUser(nameUser);
                        startActivity(new Intent(ActivitySignUp.this,ActivityName.class));
                        finish();
                    }
                }else{
                    Toast.makeText(ActivitySignUp.this, "vai lon", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ActivitySignUp.this, "loi\n"+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<>();
                params.put("name",mEdtName.getText().toString());
                params.put("password",mEdtPassWord.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

*/
    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

}



