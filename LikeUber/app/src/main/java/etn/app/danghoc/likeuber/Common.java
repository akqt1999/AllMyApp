package etn.app.danghoc.likeuber;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.firebase.geofire.GeoFire;

import etn.app.danghoc.likeuber.Mode.DriverInfo;
import etn.app.danghoc.likeuber.servies.MyFirebaseMessagingService;

public class Common {
    public static final String DRIVER_INFO_REF="DriverInfo";
    public static final String DRIVER_LOCATION_REF ="DriverLocation" ;
    public static final String TOKEN_REFERENCE ="Tokens" ;
    public static final String NOTI_TITLE ="tile" ;
    public static final String NOTI_CONTENT ="body" ;
    public static DriverInfo currentUser;
    public static GeoFire geoFire;

    public static StringBuilder buildWelcomeMessage() {
        if(Common.currentUser!=null){
            return new StringBuilder("Welcome ")
                    .append(Common.currentUser.getFirstName())
                    .append(" ")
                    .append(Common.currentUser.getLastName());
        }else{
            return new StringBuilder("");
        }
        
    }

    public static void showNotificaton(Context context, int id, String title, String body, Intent intent) {
        PendingIntent pendingIntent=null;
        if(intent!=null){
            pendingIntent=PendingIntent.getActivity(context,id,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            String NOTIFICATION_CHANGE_ID="notification";
            NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                NotificationChannel notificationChannel=new NotificationChannel(NOTIFICATION_CHANGE_ID,
                        "Uber Remake",NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.setDescription("Uber cc");
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
                notificationChannel.enableVibration(true);

                notificationManager.createNotificationChannel(notificationChannel);
            }

            NotificationCompat.Builder builder=new NotificationCompat.Builder(context,NOTIFICATION_CHANGE_ID);
            builder.setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(false)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                    .setSmallIcon(R.drawable.ic_car_24)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_car_24));
            if(pendingIntent!=null){
                builder.setContentIntent(pendingIntent);
            }
            Notification notification=builder.build();
            notificationManager.notify(id,notification);


        }
    }
}
