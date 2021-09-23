package etn.app.danghoc.nhanthongbao.SendNotificationPack;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import etn.app.danghoc.nhanthongbao.MainActivity;
import etn.app.danghoc.nhanthongbao.R;
import etn.app.danghoc.nhanthongbao.common.Common;
import io.reactivex.annotations.NonNull;

public class MyFireBaseMessagingService extends FirebaseMessagingService {
    String title, message;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        title=remoteMessage.getData().get("Title");
        message=remoteMessage.getData().get("Message");

        Intent intent   =new Intent(this, MainActivity.class);
        intent.putExtra(Common.IS_OPEN_ACTIVITY_NEW_ORDER,true);

//                NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(getApplicationContext())
//                        .setSmallIcon(R.drawable.ic_baseline_restaurant_24)
//                        .setContentTitle(title)
//                        .setContentText(message);
//        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(0, builder.build());


        Common.showNotifiCation(this,new Random().nextInt(),
                title,message,intent);
    }
}
