package etn.app.danghoc.likeuberrider.Common;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import etn.app.danghoc.likeuberrider.Model.AnimationModel;
import etn.app.danghoc.likeuberrider.Model.DriverGeoModel;
import etn.app.danghoc.likeuberrider.Model.RiderModel;
import etn.app.danghoc.likeuberrider.R;

public class Common {

    public static final String RIDER_INFO_REF = "Riders";
    public static final String TOKEN_REF = "Tokens";
    public static final String NOTI_TITLE ="notiTitle" ;
    public static final String NOTI_CONTENT ="notiContent" ;
    public static final String DRIVER_LOCATION_REF ="DriverLocation" ;//same as Driver app
    public static final String DRIVER_INFO_REF = "DriverInfo";
    public static RiderModel currentRider;
    public static Set<DriverGeoModel> driversFound=new HashSet<DriverGeoModel>();
    public static HashMap<String, Marker> marketList=new HashMap<>();
    public static HashMap<String, AnimationModel> driverLocationSubcribe=new HashMap<String, AnimationModel>( );

    public static StringBuilder buildWelcomeMessage() {
        if(Common.currentRider!=null){
            return new StringBuilder().append(Common.currentRider.getFirstName())
                    .append(" ")
                    .append(Common.currentRider.getLastName());
        }else{
            return new StringBuilder("");
        }
    }
    public static void showNotification(Context context, int id, String title, String body, Intent intent){
        PendingIntent pendingIntent=null;
        if(intent!=null) {
            pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
            String NOTIFICATION_CHANGE_ID="notification_re";
            NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel(NOTIFICATION_CHANGE_ID,
                    "Rider demo",NotificationManager.IMPORTANCE_HIGH);
                        notificationChannel.setDescription("Uber demo");
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

    public static String buildName(String firstName, String lastName) {
        return new StringBuilder(firstName).append(" ").append(lastName).toString();
    }

    public static List<LatLng> decodePoly(String encoded) {
        List poly = new ArrayList();
        int index=0,len=encoded.length();
        int lat=0,lng=0;
        while(index < len)
        {
            int b,shift=0,result=0;
            do{
                b=encoded.charAt(index++)-63;
                result |= (b & 0x1f) << shift;
                shift+=5;

            }while(b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1):(result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do{
                b = encoded.charAt(index++)-63;
                result |= (b & 0x1f) << shift;
                shift +=5;
            }while(b >= 0x20);
            int dlng = ((result & 1)!=0 ? ~(result >> 1): (result >> 1));
            lng +=dlng;

            LatLng p = new LatLng((((double)lat / 1E5)),
                    (((double)lng/1E5)));
            poly.add(p);
        }
        return poly;
    }

    public static float getBearing(LatLng begin, LatLng end) {
        double lat = Math.abs(begin.latitude - end.latitude);
        double lng = Math.abs(begin.longitude - end.longitude);

        if (begin.latitude < end.latitude && begin.longitude < end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)));
        else if (begin.latitude >= end.latitude && begin.longitude < end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90);
        else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)) + 180);
        else if (begin.latitude < end.latitude && begin.longitude >= end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270);
        return -1;
    }

    public static void setWelcomMessage(TextView txtWelcome) {
        int hour= Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if(hour>=1&&hour<12){
            txtWelcome.setText("Good morning.");
        }else if(hour>=13&&hour<=17){
            txtWelcome.setText("Good afternoon.");
        }else {
            txtWelcome.setText("Good evening.");
        }
    }
}
