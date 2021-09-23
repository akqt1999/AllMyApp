package etn.app.danghoc.nhanthongbao.service;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

import etn.app.danghoc.nhanthongbao.R;
import etn.app.danghoc.nhanthongbao.common.Common;

public class MyFCMServices  extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
//        Map<String, String> dataRecv = remoteMessage.getData();
//        if (dataRecv != null) {
//            if (dataRecv.get(Common.IS_SEND_IMAGE) != null && dataRecv.get(Common.IS_SEND_IMAGE).equals("true")) {
//
//                Glide.with(this)
//                        .asBitmap()
//                        .load(dataRecv.get(Common.IMAGE_URL))
//                        .into(new CustomTarget<Bitmap>() {
//
//                            @Override
//                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                                Common.showNotifiCationBigStyle(MyFCMServices.this,new Random().nextInt(),
//                                        dataRecv.get(Common.NOTI_TITILE),
//                                        dataRecv.get(Common.NOTI_CONTENT),
//                                        resource,
//                                        null);
//                            }
//
//                            @Override
//                            public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                            }
//                        });
//
//            } else {
//                Common.showNotifiCation(this, new Random().nextInt(),
//                        dataRecv.get(Common.NOTI_TITILE),
//                        dataRecv.get(Common.NOTI_CONTENT),
//                        null);
//
//            }
//        }

        // tren youtube
        super.onMessageReceived(remoteMessage);
        getFirebaseMessage(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
    }

    private void getFirebaseMessage(String title, String body) {
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"testMessage")
                .setSmallIcon(R.drawable.ic_baseline_restaurant_24)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true);

        NotificationManagerCompat manager=NotificationManagerCompat.from(this) ;
        manager.notify(101,builder.build());


    }


//    @Override
//    public void onNewToken(@NonNull String s) {
//        super.onNewToken(s);
//        //Common.updateToken(this, s);
//
//    }
}
