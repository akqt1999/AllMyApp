package etn.app.danghoc.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap map;
    ArrayList<LatLng>points=new ArrayList<>();
    SearchView searchView;
    Circle circle;
    List<LatLng>polylinePaths=new ArrayList<>();
    Button btnDraw,btnGo;
    PolylineOptions polylineOptions;
    Marker marker;
    Handler handler;
    int index=-1,next=1;
    float v;
    double lat,lng;
    LatLng startPosition,endPosition;
    boolean condition=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView=findViewById(R.id.searchView);
        btnDraw=findViewById(R.id.btnDraw);
        btnGo=findViewById(R.id.btnGo);
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //search view tim vi tri nhung chua hoat dong duoc
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, searchView.getQuery().toString(), Toast.LENGTH_SHORT).show();
                String location=searchView.getQuery().toString();
                List<Address>addresses=null;
                if(location!=null&&location.equals("")){
                    Geocoder geocoder=new Geocoder(MainActivity.this);
                    try {
                        addresses=geocoder.getFromLocationName(location,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address=addresses.get(0);
                    LatLng latLng=new LatLng(address.getLatitude(),address.getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng).title(location));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));

                }
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, points.size()+"", Toast.LENGTH_SHORT).show();
                polylineOptions=new PolylineOptions();
                polylineOptions.width(10).color(Color.RED);
                polylineOptions.addAll(points);
                Polyline polyline;
                map.clear();
                polyline=map.addPolyline(polylineOptions);

            }
        });
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index=-1;
                next=1;
                runCar();
               condition=false;
            }
        });
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng dhDuyTan = new LatLng(16.060033, 108.209770); // tao vi tri , them danh dau
        map.addMarker(new MarkerOptions().position(dhDuyTan).title("my place")
                        .icon(bitmapDescriptorFromVector(this,R.drawable.ic_baseline_accessibility_24))// set hinh  cho marker
        );
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(dhDuyTan, 17));
        LatLng bunCha=new LatLng(16.061337, 108.210649);
//        map.addPolyline(new PolylineOptions().add(
//                dhDuyTan,
//                new LatLng(16.059744, 108.209856),
//                new LatLng(16.059909, 108.211111),
//                new LatLng(16.061342, 108.210896),
//                bunCha
//        ).width(10).color(Color.RED)
//
//        );
        polylinePaths.add(new LatLng(16.059744, 108.209856));
        polylinePaths.add(new LatLng(16.059909, 108.211111));
        polylinePaths.add(new LatLng(16.061342, 108.210896));



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);// xem vi tri hien tai

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Toast.makeText(MainActivity.this, " click map", Toast.LENGTH_SHORT).show();
//                if(points.size()==2){
//                    points.clear();
//                    map.clear();
//                }
                points.add(latLng);
                MarkerOptions markerOptions=new MarkerOptions();
                markerOptions.position(latLng);
                map.addMarker(markerOptions);

            }
        });




        CircleOptions circleOptions=new CircleOptions();
        circleOptions.center(dhDuyTan);
        circleOptions.strokeWidth(4);
        circleOptions.strokeColor(Color.argb(255, 255, 0, 0));
        circleOptions.fillColor(Color.argb(32, 255, 0, 0));
        circleOptions.radius(100);
        circle=map.addCircle(circleOptions);
    }

    private void runCar(){

        marker=map.addMarker(new MarkerOptions().position(points.get(0))
                                .flat(true)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.redcar)));

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!condition){
                    if(index<points.size()-1){
                        index++;
                        next=index+1;
                    }
                    if(index<points.size()-1){
                        startPosition=points.get(index);
                        endPosition=points.get(next);
                    }
                    if(index==points.size()-2){
                        condition=true;
                    }
                    ValueAnimator animator=ValueAnimator.ofFloat(0,1);
                    animator.setDuration(3000);
                    animator.setInterpolator(new LinearInterpolator());
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            v=animation.getAnimatedFraction();
                            lng=v*endPosition.longitude+(1-v)
                                    *startPosition.longitude;
                            lat=v*endPosition.latitude+(1-v)
                                    *startPosition.latitude;
                            LatLng newPost=new LatLng(lat,lng);
                            marker.setPosition(newPost);
                            marker.setAnchor(0.5f,0.5f);
                            marker.setRotation(getBearing(startPosition,newPost));
//                        map.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().
//                                target(newPost)
//                        .zoom(17.0f)
//                        .build()));
                        }
                    });
                    animator.start();
                    handler.postDelayed(this,3000);
                }

            }
        },3000);

    }

    private float getBearing(LatLng startPosition, LatLng newPost) {
        double lat=Math.abs(startPosition.latitude-newPost.latitude);
        double lng=Math.abs(startPosition.longitude-newPost.longitude);
        if(startPosition.latitude<newPost.latitude&&startPosition.longitude<newPost.longitude){
            return (float)(Math.toDegrees(Math.atan(lng/lat)));
        }else if(startPosition.latitude>=newPost.latitude&&startPosition.longitude<newPost.longitude){
            return (float)((90-Math.toDegrees(Math.atan(lng/lat)))+90);
        }else if(startPosition.latitude>=newPost.latitude&&startPosition.longitude>=newPost.longitude){
            return (float)(Math.toDegrees(Math.atan(lng/lat))+180);
        }else if(startPosition.latitude<newPost.latitude&&startPosition.longitude>=newPost.longitude){
            return (float)((90-Math.toDegrees(Math.atan(lng/lat)))+270);
        }
        return -1;
    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) { // chuyen doi hinh
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}