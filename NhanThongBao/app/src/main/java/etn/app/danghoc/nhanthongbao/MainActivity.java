package etn.app.danghoc.nhanthongbao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import etn.app.danghoc.nhanthongbao.common.Common;

public class MainActivity extends AppCompatActivity {

    EditText EmailTB, PassTB;
    Button LoginB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //subcribeToPic(Common.createTopicOrder());
        EmailTB = findViewById(R.id.EmailTB);
        PassTB = findViewById(R.id.PassTB);
        LoginB = findViewById(R.id.Login);
        if (FirebaseAuth.getInstance().getCurrentUser()!= null) {
            startActivity(new Intent(this, SendNotif.class));
        } else {
            LoginB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(EmailTB.getText().toString().trim(), PassTB.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            startActivity(new Intent(MainActivity.this, SendNotif.class));
                        }
                    });
                }
            });
        }

/*
        FirebaseMessaging.getInstance()
                .subscribeToTopic("weather")
                .addOnCompleteListener(task -> {
                    if(!task.isSuccessful())
                        Toast.makeText(this, "fail success"+task.isSuccessful(), Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
            Toast.makeText(this, "fail:"+e.getMessage(), Toast.LENGTH_SHORT).show();

        });

 */

    }


}

