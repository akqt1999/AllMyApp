package etn.app.danghoc.likeuber;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.Auth;
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

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import etn.app.danghoc.likeuber.Mode.DriverInfo;
import etn.app.danghoc.likeuber.Mode.UserUtils;

public class ActivitySignIn extends AppCompatActivity {
    private final static  int LOGIN_REQUEST_CODE=12312;
    private List<AuthUI.IdpConfig>providers;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener listener;
    private FirebaseDatabase database;
    private DatabaseReference driverInfoRef;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(listener);
    }

    @Override
    protected void onStop() {
        if(firebaseAuth!=null&&listener!=null){
                firebaseAuth.removeAuthStateListener(listener);
        }
        super.onStop();
    }

    private void init() {

        ButterKnife.bind(this);

        database=FirebaseDatabase.getInstance();
        driverInfoRef=database.getReference(Common.DRIVER_INFO_REF);

        providers= Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());
        firebaseAuth=FirebaseAuth.getInstance();
        listener=myFirebaseAuth->{
            FirebaseUser user=myFirebaseAuth.getCurrentUser();
            if(user==null){
                showSignInLayout();
                //     startActivity(new Intent(this,ActivityMap.class));
            } else{

                //update token

                updateToken();
                progressBar.setVisibility(View.VISIBLE);
                checkUserFromFirebase();
            }
        };
    }

    private void updateToken() {
        FirebaseInstanceId.getInstance()
                .getInstanceId()
                .addOnSuccessListener(instanceIdResult -> {
                    Log.d("TOKEN",instanceIdResult.getId());
                    UserUtils.updateToken(ActivitySignIn.this,instanceIdResult.getToken());
                })
                .addOnFailureListener(e -> {
                    Log.d("aaa",e.getMessage()+"____2");
                    Toast.makeText(this, "[ERROR] "+e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


    private void checkUserFromFirebase() {
        driverInfoRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                               // Toast.makeText(ActivitySignIn.this, "User Already Register", Toast.LENGTH_SHORT).show();
                                DriverInfo driverInfo=snapshot.getValue(DriverInfo.class); // đưa hết giá trị của database ở chỗ này
                                goToHomeActivity(driverInfo);

                            }else{
                                showRegisterLayout();
                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("aaa",error.getMessage()+"____1");
                        Toast.makeText(ActivitySignIn.this, "[ERROR] : "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void goToHomeActivity(DriverInfo driverInfo) {
        Common.currentUser=driverInfo;//init value
        startActivity(new Intent(ActivitySignIn.this,ActivityHome.class));
        finish();
    }

    private void showRegisterLayout() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this,R.style.DialogTheme);
        View view= LayoutInflater.from(this).inflate(R.layout.layout_register,null);
        TextInputEditText edtFistName=view.findViewById(R.id.edt_first_name);
        TextInputEditText edtLastName=view.findViewById(R.id.edt_last_name);
        TextInputEditText edtPhone=view.findViewById(R.id.edt_phone);
        MaterialButton btnContinue=view.findViewById(R.id.btn_continue);

        //set data
        if(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()!=null&&
        !TextUtils.isEmpty(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())){
            edtPhone.setText(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
        }

        //set View
        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.show();

        //set Event button
        btnContinue.setOnClickListener(v -> {
                    if(TextUtils.isEmpty(edtFistName.getText().toString())){
                        Toast.makeText(this, "Please enter first name", Toast.LENGTH_SHORT).show();
                        return;
                    }else if(TextUtils.isEmpty(edtLastName.getText().toString())){
                        Toast.makeText(this, "Please enter last name", Toast.LENGTH_SHORT).show();
                        return;
                    }else if(TextUtils.isEmpty(edtPhone.getText().toString())){
                        Toast.makeText(this, "Please enter phone number", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        DriverInfo model=new DriverInfo();
                        model.setFirstName(edtFistName.getText().toString());
                        model.setLastName(edtLastName.getText().toString());
                        model.setPhoneNumber(edtPhone.getText().toString());
                        model.setRating(0.0);
                        driverInfoRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())// moi lan se child len mot id khac nhau
                                .setValue(model)
                                .addOnFailureListener(e -> {

                                    Toast.makeText(this, "[ERROR] : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                })
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(this, "Register success", Toast.LENGTH_SHORT).show();
                                    goToHomeActivity(model);
                                });
                    }
            progressBar.setVisibility(View.INVISIBLE);
            dialog.dismiss();
        });

    }

    private void showSignInLayout() {
        AuthMethodPickerLayout authMethodPickerLayout=new AuthMethodPickerLayout
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
            IdpResponse response=IdpResponse.fromResultIntent(data);
                if(resultCode==RESULT_OK){
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                }else{
                    Log.d("aaa",response.getError().getMessage()+"____4");
                    Toast.makeText(this, "[ERROR] : "+response.getError().getMessage(), Toast.LENGTH_SHORT).show();
                }
        }
    }
}
