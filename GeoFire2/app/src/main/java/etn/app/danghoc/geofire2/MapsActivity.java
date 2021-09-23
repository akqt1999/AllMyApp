package etn.app.danghoc.geofire2;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import etn.app.danghoc.geofire2.Interface.IOnLocationListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GeoQueryEventListener, IOnLocationListener {

    private GoogleMap mMap;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Marker currentUser;
    private DatabaseReference myLocationRef,myCity;
    private GeoFire geoFire;
    private List<LatLng>dangerousArea;
    LatLng locationCircle;
    Circle circle;
    IOnLocationListener listener;
    Location lastLocation;
    GeoQuery geoQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        buildLocationRequest();
                        buildLocationCallBack();
                        settingGeoFire();
                        init();
                        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(MapsActivity.this, "you must enable permission", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                }).check();

    }

    private void init() {
        dangerousArea=new ArrayList<>();
        listener=this;

        myCity=FirebaseDatabase.getInstance()
                .getReference("DangerousArea")
                .child("Mycity");

        //load location from firebase

//                myCity.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        List<MyLatLng>latLngs=new ArrayList<>();
//                        for(DataSnapshot snapshot1:snapshot.getChildren()){
//                            MyLatLng latLng=snapshot1.getValue(MyLatLng.class);
//                            latLngs.add(latLng);
//                        }
//                        listener.onLoadLocationSuccess(latLngs);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        listener.onLoadLocationFail(error.getMessage());
//                    }
//                });

                myCity.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<MyLatLng>latLngs=new ArrayList<>();
                        for(DataSnapshot locationDataSnapshot:snapshot.getChildren()){
                            MyLatLng latLng=locationDataSnapshot.getValue(MyLatLng.class);
                            latLngs.add(latLng);
                        }
                        dangerousArea.clear();
                        listener.onLoadLocationSuccess(latLngs);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        // after submit this area on FIreBase , we comment it
        /*
        FirebaseDatabase.getInstance()
                .getReference("DangerousArea")
                .child("Mycity")
                .setValue(dangerousArea)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MapsActivity.this, "success", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MapsActivity.this, "fail : "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        */
    }

    private void addMarker() {
        geoFire.setLocation("You", new GeoLocation(lastLocation.getLatitude(),
                lastLocation.getLongitude()), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                if (currentUser != null) currentUser.remove();
                currentUser = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lastLocation.getLatitude(),
                                lastLocation.getLongitude()))
                        .title("You"));
                // after add marker move camera
                mMap.animateCamera(CameraUpdateFactory
                        .newLatLngZoom(currentUser.getPosition(), 12.0f));
            }
        });
    }

    private void addCircle() {
        if(geoQuery!=null){
            geoQuery.removeGeoQueryEventListener(this);
            geoQuery.removeAllListeners();
        }


        for(LatLng latLng:dangerousArea){
            Circle circle;
            CircleOptions circleOptions = new CircleOptions().radius(500)//500m
                    .fillColor(0x220000FF)
                    .strokeWidth(5.0f)
                    .strokeColor(Color.BLUE)
                    .center(latLng);
            circle=mMap.addCircle(circleOptions);

             geoQuery=geoFire.queryAtLocation(new GeoLocation(latLng.latitude,latLng.longitude),0.5f);//500m
            geoQuery.addGeoQueryEventListener(MapsActivity.this);
        }
    }

    private void settingGeoFire() {
        myLocationRef= FirebaseDatabase.getInstance().getReference("MyLocation");
        geoFire=new GeoFire(myLocationRef);
    }

    private void buildLocationCallBack() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(final LocationResult locationResult) {
                if (mMap != null) {
                    lastLocation=locationResult.getLastLocation();
                addMarker();
// muon them vo thi phai de o ngoai
//                    locationCircle=new LatLng(locationResult.getLastLocation().getLatitude(),
//                            locationResult.getLastLocation().getLongitude());
//                    CircleOptions circleOptions=new CircleOptions().center(locationCircle)
//                            .fillColor(0x220000FF)
//                            .strokeWidth(5.0f)
//                            .radius(500)
//                            .strokeColor(Color.BLUE);
//                    circle=mMap.addCircle(circleOptions);
                }
            }
        };
    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10f);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (fusedLocationProviderClient != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }

        addCircle();


    }



    @Override
    protected void onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        super.onStop();
    }

    @Override
    public void onKeyEntered(String key, GeoLocation location) {
            sendNotification("GeoFire2",String.format("%s enter the dangerous area",key));
        checkToast("entered");

    }

    @Override
    public void onKeyExited(String key) {
        sendNotification("GeoFire2",String.format("%s exited the dangerous area",key));
        checkToast("exit");

    }

    @Override
    public void onKeyMoved(String key, GeoLocation location) {
        sendNotification("GeoFire2",String.format("%s move within the dangerous area",key));
        checkToast("move");
    }



    @Override
    public void onGeoQueryReady() {

    }

    @Override
    public void onGeoQueryError(DatabaseError error) {
        Toast.makeText(this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void checkToast(String content){
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    private void sendNotification(String title, String content) {
        String NOTIFICATION_ID_CHANNEL="location_geofire";
        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel(NOTIFICATION_ID_CHANNEL,"My Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,NOTIFICATION_ID_CHANNEL);
        builder.setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(false)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        Notification notification=builder.build();
        notificationManager.notify(new Random().nextInt(),notification);
    }

    @Override
    public void onLoadLocationSuccess(List<MyLatLng> latLngs) {
        for(MyLatLng myLatLng:latLngs){
            LatLng convert=new LatLng(myLatLng.getLatitude(),myLatLng.getLongitude());
            dangerousArea.add(convert);
        }
        // after dangerous area is have data , we will call map display
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
        if(mMap!=null){
            mMap.clear();

            addMarker();
            addCircle();
        }
    }


    @Override
    public void onLoadLocationFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}