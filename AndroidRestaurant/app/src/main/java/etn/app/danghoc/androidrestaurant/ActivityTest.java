package etn.app.danghoc.androidrestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;
import butterknife.OnClick;
import etn.app.danghoc.androidrestaurant.Common.Common;

public class ActivityTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toast.makeText(this, "khoi dong test activity", Toast.LENGTH_SHORT).show();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_sign_out)
    void signOut()
    {
        Toast.makeText(this, "sign out", Toast.LENGTH_SHORT).show();
        FirebaseAuth.getInstance().signOut();
        Common.currentUser=null;
        Intent intent=new Intent(ActivityTest.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}