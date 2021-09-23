package etn.app.danghoc.likeuber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dmax.dialog.SpotsDialog;
import etn.app.danghoc.likeuber.Mode.User;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    Button btnLogin, btnSignUp;
    RelativeLayout rootLayout;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignupDialog();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginDialog();
            }
        });
    }

    private void showLoginDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("Login");
        dialog.setMessage("Please user email to login");
        LayoutInflater inflater=LayoutInflater.from(this);
        View login_layout=inflater.inflate(R.layout.activity_login,null);
        final EditText edtEmail = login_layout.findViewById(R.id.edtEmail);
        final EditText edtPassword = login_layout.findViewById(R.id.edtPassword);
        dialog.setView(login_layout);

        //set button
        dialog.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
                    //set disable button  login if is processing
                    btnLogin.setEnabled(false);


                if (edtEmail.getText().toString().isEmpty()) {
                    Snackbar.make(rootLayout, "Please enter email address", Snackbar.LENGTH_SHORT).show();
                    return;
                }if(edtPassword.getText().toString().isEmpty()){
                    Snackbar.make(rootLayout, "Please enter password ", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                 final SpotsDialog waitingDialog=new SpotsDialog(MainActivity.this);
                waitingDialog.show(); // that mat cua minh da duoc giai thich o day
                auth.signInWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        waitingDialog.dismiss();
                        btnLogin.setEnabled(true);
                        Toast.makeText(MainActivity.this, "login success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,ActivityWelcom.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        waitingDialog.dismiss();
                        btnLogin.setEnabled(true);
                        Toast.makeText(MainActivity.this, "login fail", Toast.LENGTH_SHORT).show();
                        Snackbar.make(rootLayout,"Fail"+e.getMessage(),Snackbar.LENGTH_SHORT);
                    }
                });

            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showSignupDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("SIGN UP");
        dialog.setMessage("Please use email to sign up");
        LayoutInflater inflater = LayoutInflater.from(this);
        View sign_up_layout = inflater.inflate(R.layout.activity_signup, null);
        final EditText edtEmail = sign_up_layout.findViewById(R.id.edtEmail);
        final EditText edtPassword = sign_up_layout.findViewById(R.id.edtPassword);
        final EditText edtPhone = sign_up_layout.findViewById(R.id.edtPhone);
        final EditText edtName = sign_up_layout.findViewById(R.id.edtName);

        dialog.setView(sign_up_layout);

        //set button
        dialog.setPositiveButton("SIGN UP", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //check validation
                if (edtEmail.getText().toString().isEmpty()) {
                    Snackbar.make(rootLayout, "Please enter email address", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (edtName.getText().toString().isEmpty()) {
                    Snackbar.make(rootLayout, "Please enter name ", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (edtPhone.getText().toString().isEmpty()) {
                    Snackbar.make(rootLayout, "Please enter number phone", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (edtPassword.getText().toString().length() < 6) {
                    Snackbar.make(rootLayout, "Password to short", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                //sign up new user
                auth.createUserWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                User user=new User();
                                user.setEmail(edtEmail.getText().toString());
                                user.setPassword(edtPassword.getText().toString());
                                user.setName(edtName.getText().toString());
                                user.setNumberPhone(edtPhone.getText().toString());

                                //User email to key
                                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                            Snackbar.make(rootLayout,"Sign up success !!!",Snackbar.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Snackbar.make(rootLayout,"Sign up Fail !!!"+e.getMessage(),Snackbar.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(rootLayout,"Sign up Fail !!!"+e.getMessage(),Snackbar.LENGTH_SHORT).show();
                        Log.d("aaaaduoi",e.getMessage());
                    }
                });

            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
            dialog.show();
    }

    private void init() {
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        rootLayout = findViewById(R.id.rootActivity);

        // init database
        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        users=db.getReference("User");
    }
}