package etn.app.danghoc.likeuberrider;

import androidx.annotation.BinderThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import etn.app.danghoc.likeuberrider.Common.Common;
import etn.app.danghoc.likeuberrider.Model.RiderModel;
import etn.app.danghoc.likeuberrider.Model.UserUtils;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.operators.observable.ObservableNever;

public class ActivitySplashActivity extends AppCompatActivity {
    private final static int LOGIN_REQUEST_CODE = 425;
    private List<AuthUI.IdpConfig> providers;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener listener;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    FirebaseDatabase database;
    DatabaseReference riderInfoRef;


    @Override
    protected void onStart() {
        super.onStart();
        delaySplashScreen();
    }

    @Override
    protected void onStop() {
        if (firebaseAuth != null && listener != null)
            firebaseAuth.removeAuthStateListener(listener);
        super.onStop();
    }

    private void delaySplashScreen() {
        progressBar.setVisibility(View.VISIBLE);
//        Completable.timer(3, TimeUnit.SECONDS,
//                AndroidSchedulers.mainThread())
//                .subscribe(() ->
//                        firebaseAuth.addAuthStateListener(listener)
//                );
        firebaseAuth.addAuthStateListener(listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();
    }

    private void init() {
        ButterKnife.bind(this);

        database = FirebaseDatabase.getInstance();
        riderInfoRef = database.getReference(Common.RIDER_INFO_REF);

        providers = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()

        );

        firebaseAuth = FirebaseAuth.getInstance();
        listener = myFirebaseAuth -> {
            FirebaseUser user = myFirebaseAuth.getCurrentUser();
            if (user != null) {
                //update token

                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ActivitySplashActivity.this, "[error] "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        UserUtils.updateToken(ActivitySplashActivity.this,instanceIdResult.getToken());
                    }
                });

                checkUserFromFirebase();
            } else {
                showLoginLayout();
            }
        };
    }

    private void showLoginLayout() {
        AuthMethodPickerLayout authMethodPickerLayout = new AuthMethodPickerLayout
                .Builder(R.layout.layout_sign_in)
                .setPhoneButtonId(R.id.btn_phone_sign_in)
                .setGoogleButtonId(R.id.btn_google_sign_in)
                .build();

        startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAuthMethodPickerLayout(authMethodPickerLayout)
                .setIsSmartLockEnabled(false)
                .setTheme(R.style.LoginTheme)
                .setAvailableProviders(providers)
                .build(),LOGIN_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==LOGIN_REQUEST_CODE){
            IdpResponse response=IdpResponse.fromResultIntent(data); //
            if(resultCode==RESULT_OK){
                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
            }else{
                Toast.makeText(this,response.getError().getMessage() , Toast.LENGTH_SHORT).show();
            }
        }
    }
 // new dangd nhap ma chua tao thi vao dang fku
    private void checkUserFromFirebase() {

        riderInfoRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Log.d("aaa","co");
                            RiderModel riderModel = snapshot.getValue(RiderModel.class);
                            goToHomeActivity(riderModel);
                        } else {
                            Log.d("aaa","khong co");
                            showRegisterLayout();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("aaa","huy");
                        Log.d("aaa",error.getMessage()+"");
                        Toast.makeText(ActivitySplashActivity.this, "[ERROR]"+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void showRegisterLayout() {
        Log.d("aaa","register");
        AlertDialog.Builder builder=new AlertDialog.Builder(this,R.style.DialogTheme);
        View view= LayoutInflater.from(this).inflate(R.layout.layout_register,null);
        TextInputEditText edtFirstName=view.findViewById(R.id.edt_first_name);
        TextInputEditText edtLastName=view.findViewById(R.id.edt_last_name);
        TextInputEditText edtPhone=view.findViewById(R.id.edt_phone);
        MaterialButton btnContinue=view.findViewById(R.id.btn_continue);

        //set data
        if(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()!=null&&
        !TextUtils.isEmpty(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())){
            edtPhone.setText(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
        }

        //set view
        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.show();

        // set event button
        btnContinue.setOnClickListener(v -> {
            if(TextUtils.isEmpty(edtFirstName.getText().toString())){
                Toast.makeText(this, "Please enter first name", Toast.LENGTH_SHORT).show();
            }else if(TextUtils.isEmpty(edtLastName.getText().toString())){
                Toast.makeText(this, "Please enter last name", Toast.LENGTH_SHORT).show();
            }else if(TextUtils.isEmpty(edtPhone.getText().toString())){
                Toast.makeText(this, "Please enter phone number", Toast.LENGTH_SHORT).show();
            }else{
                RiderModel model=new RiderModel();
                model.setFirstName(edtFirstName.getText().toString());
                model.setLastName(edtLastName.getText().toString());
                model.setPhoneNumber(edtPhone.getText().toString());

                riderInfoRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(model)
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "[ERROR]"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }).addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Register success", Toast.LENGTH_SHORT).show();
                    goToHomeActivity(model);
                        });
            }

            progressBar.setVisibility(View.INVISIBLE);
            dialog.dismiss();
        });


    }

    private void goToHomeActivity(RiderModel riderModel) {
        Common.currentRider = riderModel;
        startActivity(new Intent(this,ActivityHome.class));
        finish();
    }


}