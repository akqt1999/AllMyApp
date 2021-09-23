package etn.app.danghoc.likeuber.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import etn.app.danghoc.likeuber.Common;
import etn.app.danghoc.likeuber.Mode.CheckOnlineModel;
import etn.app.danghoc.likeuber.R;
import io.reactivex.Completable;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private HomeViewModel homeViewModel;

    //button
    private Button btnOnLocation, btnOffLocation, btnUpdateLocation;

    //city name
    String cityName, district;
    String locationName;
    boolean checkOnline;



    //check color
    CheckOnlineModel checkOnlineModel = new CheckOnlineModel();


    //location
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient, fusedLocationProviderClient2;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private LocationCallback locationCallback2;
    SupportMapFragment mapFragment;

    private boolean isFirstTime = true;

    //online system
    DatabaseReference onlineRef;
    DatabaseReference currentUserRef, currentUserCheck;
    DatabaseReference driverLocationRef;
    DatabaseReference checkColor;
    FirebaseDatabase database;
    GeoFire geoFire;
    ValueEventListener onlineValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists() && currentUserRef != null) {
                currentUserRef.onDisconnect().removeValue();
                isFirstTime = true;
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Snackbar.make(mapFragment.getView(), error.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    };


    @Override
    public void onDestroy() {


        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        fusedLocationProviderClient2.removeLocationUpdates(locationCallback2);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Common.geoFire.removeLocation(FirebaseAuth.getInstance().getCurrentUser().getUid());
            geoFire.removeLocation(FirebaseAuth.getInstance().getCurrentUser().getUid());
        }

        // change check color

        onlineRef.removeEventListener(onlineValueEventListener);
        super.onDestroy();
    }


    @Override
    public void onResume() {
        super.onResume();
        registerOnlineSystem();

    }


    private void registerOnlineSystem() {
        onlineRef.addValueEventListener(onlineValueEventListener);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        init();
//        checkPermission();
        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // init btn
        btnOnLocation = root.findViewById(R.id.btnOn);
        btnOffLocation = root.findViewById(R.id.btnOff);
        btnUpdateLocation = root.findViewById(R.id.btnUpdateLocation);
        btnOnLocation.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());


        });

        btnOffLocation.setOnClickListener(v -> {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    Common.geoFire.removeLocation(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    fusedLocationProviderClient.removeLocationUpdates(locationCallback);

                }

        );

        btnUpdateLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkOnlineModel= getCurrentLocation();
               checkColor.child(locationName).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(checkOnlineModel);
            }
        });
        return root;
    }

    private CheckOnlineModel getCurrentLocation() {
        CheckOnlineModel checkOnlineModel2=new CheckOnlineModel();
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                // fireBase true   tru else false thi no false

                if(cityName!=null)
                    locationName=cityName;
                else locationName=district;
                FirebaseDatabase.getInstance()
                        .getReference(Common.CHECK_ONLINE)
                        .child(locationName)
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                CheckOnlineModel checkOnlineModel1=snapshot.getValue(CheckOnlineModel.class);
                                checkOnline=checkOnlineModel1.isOnline();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });


                checkOnlineModel2.setLat(locationResult.getLastLocation().getLatitude());
                checkOnlineModel2.setLng(locationResult.getLastLocation().getLongitude());
                checkOnlineModel2.setOnline(checkOnline);

            }
        };
      return   checkOnlineModel2;
    }


    private void init() {

      
        onlineRef = FirebaseDatabase.getInstance().getReference().child(".info/connected");

        database = FirebaseDatabase.getInstance();
        checkColor = database.getReference(Common.CHECK_ONLINE);

        checkOnlineModel.setOnline(true);
        //  checkColor.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(checkOnlineModel)

        locationRequest = new LocationRequest();
        locationRequest.setSmallestDisplacement(50f); //50m
        locationRequest.setInterval(15000);           //15 sec
        locationRequest.setFastestInterval(10000);   // 10sec
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                LatLng newPosition = new LatLng(locationResult.getLastLocation().getLatitude(),
                        locationResult.getLastLocation().getLongitude());

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newPosition, 18f));

                //get name address
                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                List<Address> addressList;

                try {

                    //set lat,lng ..
                    checkOnlineModel.setLat(locationResult.getLastLocation().getLatitude());
                    checkOnlineModel.setLng(locationResult.getLastLocation().getLongitude());
                    // checkColor.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(checkOnlineModel);


                    addressList = geocoder.getFromLocation(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude(), 1);
                     cityName = addressList.get(0).getLocality();
                     district = addressList.get(0).getSubAdminArea();

                    if (cityName != null) {
                        driverLocationRef = FirebaseDatabase.getInstance().getReference(Common.DRIVER_LOCATION_REF).
                                child(cityName);
                        //child check online
                        checkColor.child(cityName).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(checkOnlineModel);
                    } else {
                        driverLocationRef = FirebaseDatabase.getInstance().getReference(Common.DRIVER_LOCATION_REF).
                                child(district);
                        //child check online
                        checkColor.child(district).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(checkOnlineModel);
                    }

//                    currentUserCheck=checkColor.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                    currentUserCheck.setValue(checkOnlineModel);

                    currentUserRef = driverLocationRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid());//id user


                    Common.geoFire = new GeoFire(driverLocationRef);


                    //  update location
                    Common.geoFire.setLocation(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                            new GeoLocation(locationResult.getLastLocation().getLatitude(),
                                    locationResult.getLastLocation().getLongitude()),
                            (key, error) -> {
                                if (error != null) {

                                    Snackbar.make(mapFragment.getView(), error.getMessage(), Snackbar.LENGTH_LONG).show();
                                } else {

                                }
                            });

                    registerOnlineSystem();// just register when done setup
                } catch (IOException e) {
                    Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_LONG).show();
                }


            }
        };

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        fusedLocationProviderClient2 = LocationServices.getFusedLocationProviderClient(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //  fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        // fusedLocationProviderClient.removeLocationUpdates(locationCallback);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        //check permission


        try {
            boolean success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.uber_maps_style));
            if (!success)
                Log.e("ERROR", "Style Parsing error");
        } catch (Resources.NotFoundException e) {
            Log.e("ERROR", e.getMessage());
        }
        Dexter.withContext(getContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }

                        // move camera current position
                        locationCallback2 = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                LatLng newPosition = new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newPosition, 18f));

                            }
                        };
                        fusedLocationProviderClient2.requestLocationUpdates(locationRequest, locationCallback2, Looper.myLooper());

                        mMap.setMyLocationEnabled(true);
                        mMap.getUiSettings().setMyLocationButtonEnabled(true);
                        mMap.setOnMyLocationButtonClickListener(() -> {
                            fusedLocationProviderClient.getLastLocation()
                                    .addOnFailureListener(e -> {

                                        Toast.makeText(getContext(), e.getMessage() + "", Toast.LENGTH_SHORT).show();
                                    }).addOnSuccessListener(location -> {
                                LatLng userLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 18f));
                            });
                            return false;
                        });
                        //set Layout Button
                        View locationButton = ((View) mapFragment.getView().findViewById(Integer.parseInt("1"))
                                .getParent()).findViewById(Integer.parseInt("2"));
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
                        //right bottom
                        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                        params.setMargins(0, 0, 0, 50);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(getContext(), "Permission " + permissionDeniedResponse.getPermissionName() + "" +
                                "was denied!!!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                    }
                }).check();
        Snackbar.make(mapFragment.getView(), "You're online", Snackbar.LENGTH_LONG).show();

    }


}