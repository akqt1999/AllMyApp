package etn.app.danghoc.demngaylenlich;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import butterknife.BindView;
import butterknife.ButterKnife;
import etn.app.danghoc.demngaylenlich.CallBack.IInfoUserLisener;
import etn.app.danghoc.demngaylenlich.Commonn.Commonn;
import etn.app.danghoc.demngaylenlich.Model.User;
import etn.app.danghoc.demngaylenlich.SharedPreferences.CheckLogin;

public class ActivitySplash extends AppCompatActivity implements IInfoUserLisener {

    ProgressBar progressBar;
    boolean checkExist = true, checkRun = false;

    IInfoUserLisener iInfoUserLisener;

    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    //firebase user
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference colRefUser;
    DocumentReference docRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {

        iInfoUserLisener = this;

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        ButterKnife.bind(this);

        Commonn.checkLogin = new CheckLogin(this);
        if (Commonn.checkLogin.getNameUser() == null) {
            showButton();
            progressBar.setVisibility(View.INVISIBLE);
        } else {
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void goToHomeActivity(User user) {
        Commonn.user = user;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void showButton() {
        btnLogin.setVisibility(View.VISIBLE);
        btnRegister.setVisibility(View.VISIBLE);

        btnLogin.setOnClickListener(v -> {
            showLayoutLogin();
        });

        btnRegister.setOnClickListener(v -> showLayoutRegister());

    }

    private void showLayoutRegister() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Register");
        dialog.setMessage("Please User to Register");

        LayoutInflater inflater = LayoutInflater.from(this);
        View login_layout = inflater.inflate(R.layout.layout_login, null);

        TextInputEditText edtNameUser = login_layout.findViewById(R.id.edtNameUser);
        ProgressBar progressBar1 = login_layout.findViewById(R.id.progressBar);
        Button btnCancel = login_layout.findViewById(R.id.btnCancel);
        Button btnRegister = login_layout.findViewById(R.id.btnLogin);

        btnRegister.setText("Register");

        dialog.setView(login_layout);

        AlertDialog alertDialog = dialog.create();

        btnCancel.setOnClickListener(v -> alertDialog.dismiss());

        btnRegister.setOnClickListener(v -> {
            checkExist = true;
            progressBar1.setVisibility(View.VISIBLE);

            if (TextUtils.isEmpty(edtNameUser.getText().toString())) {
                Toast.makeText(ActivitySplash.this, "Please enter name user", Toast.LENGTH_SHORT).show();
                progressBar1.setVisibility(View.VISIBLE);
            } else {

                checkDuplicate(edtNameUser.getText().toString(), progressBar1);

            }
        });


        alertDialog.show();

    }

    private void setDataUser(String toString) {
        String nameUser = toString;
        if (checkRun) {
            Log.d("indexx", "1.3");
            if (checkExist) {

                Log.d("indexx", "2");
                Log.d("indexx", checkRun + "");
                checkRun = false;
                docRef = firebaseFirestore.collection(Commonn.COLLECTION_REF).document(nameUser);
                User user = new User();
                docRef.set(user);
                user.setNameUser(nameUser);
                goToHomeActivity(user);
                Commonn.checkLogin.setNameUser(nameUser);

            }

        }
    }

    private void checkDuplicate(String nameUser1, ProgressBar progressBar1) {
        String nameUser = nameUser1;
        colRefUser = firebaseFirestore.collection(Commonn.COLLECTION_REF);
        Log.d("indexx", "-2");
        colRefUser.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (queryDocumentSnapshots != null) {
                Log.d("indexx", "-1");
                checkRun = true;
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Log.d("indexx", "0");
                    checkRun = true;
                    if (documentSnapshot.getId().equalsIgnoreCase(nameUser)) {
                        Toast.makeText(ActivitySplash.this, "duplicate name user", Toast.LENGTH_SHORT).show();
                        progressBar1.setVisibility(View.VISIBLE);
                        checkExist = false;
                        checkRun = false;
                        Log.d("indexx", "1");
                        break;
                    }
                }
                if (checkRun) {
                    iInfoUserLisener.onCheckDuplicate(true, nameUser);
                    progressBar1.setVisibility(View.INVISIBLE);

                } else {
                    iInfoUserLisener.onCheckDuplicate(false, nameUser);
                    progressBar1.setVisibility(View.INVISIBLE);
                }

            }

        }).addOnFailureListener(e -> Toast.makeText(ActivitySplash.this, e.getMessage(), Toast.LENGTH_SHORT)
                .show());
    }


    private void showLayoutLogin() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Login");
        dialog.setMessage("Please enter User to login");

        LayoutInflater inflater = LayoutInflater.from(this);
        View login_layout = inflater.inflate(R.layout.layout_login, null);

        TextInputEditText edtNameUser = login_layout.findViewById(R.id.edtNameUser);
        ProgressBar progressBar1 = login_layout.findViewById(R.id.progressBar);
        Button btnCancel = login_layout.findViewById(R.id.btnCancel);
        Button btnLogin = login_layout.findViewById(R.id.btnLogin);
        btnLogin.setText("Login");


        dialog.setView(login_layout);

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();

        btnLogin.setOnClickListener(v -> {
            progressBar1.setVisibility(View.VISIBLE);

            String nameUser = edtNameUser.getText().toString();

            docRef = firebaseFirestore.collection(Commonn.COLLECTION_REF).document(nameUser);
            docRef.get()
                    .addOnFailureListener(e -> {
                        Toast.makeText(ActivitySplash.this, "[ERROR]" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("loi", e.getMessage());
                        progressBar1.setVisibility(View.INVISIBLE);

                    }).addOnSuccessListener(documentSnapshot -> {

                if (documentSnapshot != null) {
                    alertDialog.dismiss();
                    progressBar1.setVisibility(View.INVISIBLE);
                    User user = documentSnapshot.toObject(User.class);
                    if (user != null) {
                        goToHomeActivity(user);
                        Commonn.checkLogin.setNameUser(nameUser);

                    }
                    else
                        Toast.makeText(this, "not exist name", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "not exist name", Toast.LENGTH_SHORT).show();
                }

            });
        });

        btnCancel.setOnClickListener(v -> alertDialog.dismiss());


    }


    @Override
    public void onCheckDuplicate(boolean check, String nameUSer) {
        if (check) {
            setDataUser(nameUSer);
        }
    }
}






