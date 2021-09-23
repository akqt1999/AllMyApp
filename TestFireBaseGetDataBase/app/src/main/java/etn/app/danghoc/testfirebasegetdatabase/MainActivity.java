package etn.app.danghoc.testfirebasegetdatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    DatabaseReference data;
    EditText edtNameUser,edtPassword;
    public static final String ipWifi="192.168.1.8";
    public static final String URL_LOGIN="http://"+ipWifi+":8080/demobanhang/loginuser.php";
    Button btnPush;
    TextView txtResult;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        data=FirebaseDatabase.getInstance().getReference();

        btnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 user=new User(edtNameUser.getText().toString(),edtPassword.getText().toString());
                data.child("idNguoiBan").push().setValue(user);
            }
        });

        data.child("idNguoiBan").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    User user=dataSnapshot.getValue(User.class);
                    //txtResult.setText(user.nameUser+"\n"+user.password+"\n");
                loginUser(URL_LOGIN,user);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loginUser(final String url, final User user){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("fail")){
                    txtResult.setText("password or nameuser wrong");
                }else{
                    txtResult.setText(response);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txtResult.setText(error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<>();
                params.put("name",user.getNameUser());
                params.put("password",user.getPassword());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void init() {
        edtNameUser=findViewById(R.id.edt_nameuser);
        edtPassword=findViewById(R.id.edt_password);

        btnPush=findViewById(R.id.btn_push);
        txtResult=findViewById(R.id.txt_result);
    }


}