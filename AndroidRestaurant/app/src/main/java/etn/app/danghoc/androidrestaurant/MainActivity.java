package etn.app.danghoc.androidrestaurant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import etn.app.danghoc.androidrestaurant.Common.Common;
import etn.app.danghoc.androidrestaurant.Retrofit.IMyRestraurantAPI;
import etn.app.danghoc.androidrestaurant.Retrofit.RetrofitClient;
import io.paperdb.Paper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final int APP_REQUEST_CODE = 1121;
    private   FirebaseAuth.AuthStateListener listener;
    private   FirebaseAuth firebaseAuth ;
    private   List<AuthUI.IdpConfig>providers;

    AlertDialog dialog;

    IMyRestraurantAPI myRestaurantAPI;
    CompositeDisposable compositeDisposable=new CompositeDisposable();

    @BindView(R.id.btn_sign_in)
    Button btn_sign_in;

    @OnClick(R.id.btn_sign_in)
    void loginUser()
    {
        //Intent intent=
        Toast.makeText(this, "di vao login", Toast.LENGTH_SHORT).show();
//        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
//                .setAvailableProviders(providers).build(),APP_REQUEST_CODE);


        FirebaseAuth  mAuth = FirebaseAuth.getInstance();
// set this to remove reCaptcha web // xoa cai captcha
        mAuth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);

        someActivityResultLauncher.launch(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false)
                .build());

        }

//      ActivityResultLauncher<Intent>launcher=registerForActivityResult(
//               new
//      );



    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        // There are no request codes
                        Intent data = result.getData();
                        Toast.makeText(MainActivity.this, "ket cai phone number", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
//boi vi day la moi lan khoi tao khac nhauy
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==APP_REQUEST_CODE)
//        {
//            IdpResponse response=IdpResponse.fromResultIntent(data);
//
//            if(resultCode==RESULT_OK)
//            {
//                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
//            }
//            else
//            {
//                Toast.makeText(this, "fail to sign in", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//        else
//        {
//
//        }
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this, "this is manin", Toast.LENGTH_SHORT);
        initt();
    }

    private void initt() {
        providers= Arrays.asList(new AuthUI.IdpConfig.PhoneBuilder().build());

        firebaseAuth=FirebaseAuth.getInstance();

        ButterKnife.bind(this);


        listener=firebaseAuth1 -> { // cai nay la lang nghe su kien login
            FirebaseUser user=firebaseAuth1.getCurrentUser();

            if(user!=null) //user really login
            {
                    compositeDisposable.add(myRestaurantAPI.getUser(Common.API_KEY,user.getPhoneNumber())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(userModel -> {
                        if(userModel.isSuccess())
                        {
                            Common.currentUser=userModel.getResult().get(0);
                            startActivity(new Intent(MainActivity.this,HomeActivity.class));
                          //  startActivity(new Intent(MainActivity.this,ActivityTest.class));
                            Toast.makeText(this, "ket thuc o hang trn", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else
                            {
                                Toast.makeText(this, "ket thuc o hang duoi", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this,UpdateInfoActivity.class));
                                finish();
                        }
                    }));
            }
            else
            {
             //   loginUser(); //
                //khi vua vao nó sẽ vào đây , có nghĩa là nó đang kiểm tra cái này đã có user hay chwua
                // chwua có thì tự động vào lun
            }
        };

        Paper.init(this);
        dialog= new SpotsDialog.Builder().setContext(this).setCancelable(false).build();
        myRestaurantAPI= RetrofitClient.getInstance(Common.API_RESTAURANT_ENDPOINT).create(IMyRestraurantAPI.class);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(listener!=null && firebaseAuth!=null)
            firebaseAuth.addAuthStateListener(listener);
    }

    @Override
    protected void onStop() {

        if(listener!=null&&firebaseAuth!=null)
        firebaseAuth.removeAuthStateListener(listener);

        super.onStop();

    }


}
/*
cái này nhấn vào nút đăng ký là sẽ nhập sđt vs cái mã ,
khi mà nhấn xác nhạn cái mã xong thì nó sẽ vào cái phần listener
cái này là đê tạo user cho cái getCurrent user ,
xong nó sẽ kiểm tra cái phone Number của cái user đó có tồn tại trên datase chưa nếu có thì nó sẽ vào home
nếu chwua có thì nó sẽ vào phần UpdateActivity

 */
