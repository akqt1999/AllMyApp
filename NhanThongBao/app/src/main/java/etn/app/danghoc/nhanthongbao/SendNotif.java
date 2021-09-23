package etn.app.danghoc.nhanthongbao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import etn.app.danghoc.nhanthongbao.SendNotificationPack.APIService;
import etn.app.danghoc.nhanthongbao.SendNotificationPack.Client;
import etn.app.danghoc.nhanthongbao.SendNotificationPack.Client2;
import etn.app.danghoc.nhanthongbao.SendNotificationPack.Data;
import etn.app.danghoc.nhanthongbao.SendNotificationPack.MyResponse;
import etn.app.danghoc.nhanthongbao.SendNotificationPack.NotificationSender;
import etn.app.danghoc.nhanthongbao.SendNotificationPack.Token;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendNotif extends AppCompatActivity {

    EditText UserTB,Title,Message;
    Button send;
    EditText edt_id;
    private APIService apiService;

    CompositeDisposable compositeDisposable=new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        compositeDisposable=new CompositeDisposable();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notif);
        UserTB=findViewById(R.id.UserID);
        Title=findViewById(R.id.Title);
        Message=findViewById(R.id.Message);
        edt_id=findViewById(R.id.txt_id);
        edt_id.setText(FirebaseAuth.getInstance().getCurrentUser().getUid());

                send=findViewById(R.id.button);
      //  apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        apiService= Client2.getInstance().create(APIService.class);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Tokens")
                        .child(UserTB.getText().toString().trim()).child("token")
                        .addListenerForSingleValueEvent(new ValueEventListener() { //get token cua user nguoi nhan
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       String usertoken=dataSnapshot.getValue(String.class);
                        Toast.makeText(SendNotif.this, usertoken, Toast.LENGTH_SHORT).show();
                        sendNotifications(usertoken, Title.getText()
                                .toString().trim(),Message.getText().toString().trim());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        UpdateToken();
    }
    private void UpdateToken(){

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if(!task.isSuccessful())
                        Toast.makeText(this, "Fetching FCM registration token failed"+task.getException(), Toast.LENGTH_SHORT).show();

                    String refreshToken=task.getResult();
                    Token token= new Token(refreshToken);
                    FirebaseDatabase.getInstance().getReference("Tokens")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);
                });
    }

    public void sendNotifications(String usertoken, String title, String message) {
        Data data = new Data(title, message);
        NotificationSender sender = new NotificationSender(data, usertoken);

        apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(SendNotif.this, "Failed ", Toast.LENGTH_LONG);
                    }
                    else{
                        Toast.makeText(SendNotif.this, "success dsdsd", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                Toast.makeText(SendNotif.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


//        compositeDisposable.add(apiService.sendNotifcation2(sender)
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe(myResponse -> {
//            Toast.makeText(this, "send success", Toast.LENGTH_SHORT).show();
//        },throwable -> {
//            Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
//        }));

    }

}