package etn.app.danghoc.likeuberrider.ui.home;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.snackbar.Snackbar;
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
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.internal.GwtIncompatible;
import etn.app.danghoc.likeuberrider.CallBack.IFirebaseDriverInfoListener;
import etn.app.danghoc.likeuberrider.CallBack.IFirebaseFailedListener;
import etn.app.danghoc.likeuberrider.Common.Common;
import etn.app.danghoc.likeuberrider.Model.AnimationModel;
import etn.app.danghoc.likeuberrider.Model.DriverGeoModel;
import etn.app.danghoc.likeuberrider.Model.DriverInfoModel;
import etn.app.danghoc.likeuberrider.Model.GeoQueryModel;
import etn.app.danghoc.likeuberrider.R;
import etn.app.danghoc.likeuberrider.Remote.IGoogleAPI;
import etn.app.danghoc.likeuberrider.Remote.RetrofitClient;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment implements OnMapReadyCallback, IFirebaseFailedListener, IFirebaseDriverInfoListener {

    @BindView(R.id.activity_main)
    SlidingUpPanelLayout slidingUpPanelLayout;

    @BindView(R.id.txt_welcome)
    TextView txtWelcome;

    private AutocompleteSupportFragment autocompleteSupportFragment;

    private HomeViewModel homeViewModel;
    SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private boolean firstTime = true;
    //location
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;


    //load driver
    private double distance = 1.0;//default in km
    private static final double LIMIT_RANGE = 10.0;//km
    private Location previousLocation, currentLocation;// use to calculate distance (tinh khoang cah)

    //listener
    IFirebaseDriverInfoListener iFirebaseDriverInfoListener;
    IFirebaseFailedListener iFirebaseFailedListener;
    private String cityName;


    //
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private IGoogleAPI iGoogleAPI;


    //get user online
    private DatabaseReference getUserRef;


    @Override
    public void onDestroy() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        super.onDestroy();
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        init();
        initViews(root);
        return root;
    }

    private void initViews(View root) {
        ButterKnife.bind(this, root);
        Common.setWelcomMessage(txtWelcome);
    }

    private void init() {

    //getUserRef=FirebaseDatabase.getInstance().getReference(Common.DRIVER_INFO_REF).addChildEventListener();


        Places.initialize(getContext(), getString(R.string.google_api_key));
        autocompleteSupportFragment = (AutocompleteSupportFragment) getChildFragmentManager()
                .findFragmentById(R.id.autocomplete_fragment);
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.ADDRESS,Place.Field.NAME,Place.Field.LAT_LNG));
        autocompleteSupportFragment.setHint(getString(R.string.where_to));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Snackbar.make(getView(),place.getLatLng()+"",Snackbar.LENGTH_LONG).show();

            }

            @Override
            public void onError(@NonNull Status status) {
                Snackbar.make(getView(),status.getStatusMessage()+"",Snackbar.LENGTH_LONG).show();
            }
        });


        iGoogleAPI = RetrofitClient.getInstance().create(IGoogleAPI.class);

        iFirebaseDriverInfoListener = this;
        iFirebaseFailedListener = this;

        locationRequest = new LocationRequest();
        locationRequest.setSmallestDisplacement(10f);//10m
        locationRequest.setInterval(5000);         //5 sec
        locationRequest.setFastestInterval(3000);   //10 sec
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                LatLng newPosition = new LatLng(locationResult.getLastLocation().getLatitude(),
                        locationResult.getLastLocation().getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newPosition, 18f));

                // if user has change location ,calculate and load driver again
                if (firstTime) {
                    previousLocation = currentLocation = locationResult.getLastLocation();
                    firstTime = false;

                    setRestrictPlacesInCountry(locationResult.getLastLocation());


                } else {
                    previousLocation = currentLocation;
                    currentLocation = locationResult.getLastLocation();
                }

                if (previousLocation.distanceTo(currentLocation) / 1000 <= LIMIT_RANGE) //do tra ve m nen doi ra km phai chia cho 1000
                    loadAvailableDrivers();
                else {
                    //do nothing
                }

            }
        };


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

    }

    private void setRestrictPlacesInCountry(Location location) {
            try {
                Geocoder geocoder=new Geocoder(getContext(),Locale.getDefault());
                List<Address>addressList=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1 );
                if(addressList.size()>0)
                    autocompleteSupportFragment.setCountry(addressList.get(0).getCountryCode());
            }catch (IOException e){

            }
    }


    private void loadAvailableDrivers() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnFailureListener(e -> Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_LONG).show()).addOnSuccessListener(location -> {
            //load all driver in city
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addressList;
            try {
                addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                cityName = addressList.get(0).getLocality();

                if(!TextUtils.isEmpty(cityName)) {
                    //query
                    DatabaseReference diver_location_ref = FirebaseDatabase.getInstance()
                            .getReference(Common.DRIVER_LOCATION_REF)
                            .child(cityName);
                    GeoFire gf = new GeoFire(diver_location_ref);
                    GeoQuery geoQuery = gf.queryAtLocation(new GeoLocation(location.getLatitude(),
                            location.getLongitude()), distance);
                    geoQuery.removeAllListeners();

                    geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
                        @Override
                        public void onKeyEntered(String key, GeoLocation location) {
                            Common.driversFound.add(new DriverGeoModel(key, location));
                        }

                        @Override
                        public void onKeyExited(String key) {

                        }

                        @Override
                        public void onKeyMoved(String key, GeoLocation location) {

                        }

                        @Override
                        public void onGeoQueryReady() {
                            if (distance <= LIMIT_RANGE) {
                                distance++;
                                loadAvailableDrivers();//continue search in new distance
                            } else {
                                distance = 1.0;
                                addDriverMarker();
                            }
                        }

                        @Override
                        public void onGeoQueryError(DatabaseError error) {
                            Snackbar.make(getView(), error.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    });

                    //listen to new driver in city and range (khi dang mo may ma nguoi khac vao thi no se hien len)
                    diver_location_ref.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            // have new driver
                            GeoQueryModel geoQueryModel = snapshot.getValue(GeoQueryModel.class);
                            GeoLocation geoLocation = new GeoLocation(geoQueryModel.getL().get(0), geoQueryModel.getL().get(1));
                            DriverGeoModel driverGeoModel = new DriverGeoModel(snapshot.getKey(),
                                    geoLocation);
                            Location newDriverLocation = new Location("");
                            newDriverLocation.setLatitude(geoLocation.latitude);
                            newDriverLocation.setLongitude(geoLocation.longitude);
                            float newDistance = location.distanceTo(newDriverLocation) / 1000;
                            if (newDistance <= LIMIT_RANGE)
                                findDriverByKey(driverGeoModel);
                        }


                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else
                    Snackbar.make(getView(),R.string.city_name_empty,Snackbar.LENGTH_LONG).show();

            } catch (IOException e) {
                e.printStackTrace();
                Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void addDriverMarker() {
        if (Common.driversFound.size() > 0) {
            Observable.fromIterable(Common.driversFound)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(driverGeoModel -> {
                        //on next
                        findDriverByKey(driverGeoModel);
                    }, throwable -> {
                        Snackbar.make(getView(), throwable.getMessage(), Snackbar.LENGTH_LONG).show();
                    }, () -> {
                    });
        } else {

            Snackbar.make(getView(), getString(R.string.diver_not_found), Snackbar.LENGTH_LONG).show();
        }
    }

    private void findDriverByKey(DriverGeoModel driverGeoModel) {
        FirebaseDatabase.getInstance()
                .getReference(Common.DRIVER_INFO_REF)
                .child(driverGeoModel.getKey())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChildren()) {     //(hasChildren) kiem tra xem co gia tri khong
                            driverGeoModel.setDriverInfoModel(snapshot.getValue(DriverInfoModel.class));
                            iFirebaseDriverInfoListener.onDriverInfoLoadSuccess(driverGeoModel);
                        } else {
                            iFirebaseFailedListener.onFirebaseLoadFailed(getString(R.string.not_found_key) + driverGeoModel.getKey());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        iFirebaseFailedListener.onFirebaseLoadFailed(error.getMessage());
                    }
                });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Dexter.withContext(getContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        mMap.setMyLocationEnabled(true);
                        mMap.getUiSettings().setMyLocationButtonEnabled(true);
                        mMap.setOnMyLocationButtonClickListener(() -> {
                            fusedLocationProviderClient.getLastLocation()
                                    .addOnFailureListener(e -> Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_LONG).show())
                                    .addOnSuccessListener(location -> {
                                        LatLng userLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 18f));
                                    });
                            return true;
                        });
                        //layout button
                        View locationButton = ((View) mapFragment.getView().findViewById(Integer.parseInt("1")).getParent())
                                .findViewById(Integer.parseInt("2"));
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();

                        //Right button
                        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                        params.setMargins(0, 0, 0, 250);


                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Snackbar.make(getView(), permissionDeniedResponse.getPermissionName() + " need enable",
                                Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                }).check();


        mMap.getUiSettings().setZoomControlsEnabled(true);

        try {
            boolean success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(),
                    R.raw.uber_maps_style));

            if (!success) {
                Snackbar.make(getView(), "Load map style failed", Snackbar.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Snackbar.make(getView(), e.getMessage() + "", Snackbar.LENGTH_LONG).show();
        }

    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDriverInfoLoadSuccess(DriverGeoModel driverGeoModel) {
        //if already have marker with this key , don't set again
        if (!Common.marketList.containsKey(driverGeoModel.getKey())) {
            Common.marketList.put(driverGeoModel.getKey(),
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(driverGeoModel.getGeoLocation().latitude,
                                    driverGeoModel.getGeoLocation().longitude))
                            .flat(false)
                            .title(Common.buildName(driverGeoModel.getDriverInfoModel().getFirstName(),
                                    driverGeoModel.getDriverInfoModel().getLastName()))
                            .snippet(driverGeoModel.getDriverInfoModel().getPhoneNumber())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.car_top_view))));
        }

        if (!TextUtils.isEmpty(cityName)) {
            DatabaseReference driverLocation = FirebaseDatabase.getInstance()
                    .getReference(Common.DRIVER_LOCATION_REF)
                    .child(cityName)
                    .child(driverGeoModel.getKey());
            driverLocation.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.hasChildren()) {
                        if (Common.marketList.get(driverGeoModel.getKey()) != null) {
                            Common.marketList.get(driverGeoModel.getKey()).remove();
                        }
                        Common.marketList.remove(driverGeoModel.getKey());
                        Common.driverLocationSubcribe.remove(driverGeoModel.getKey());//remove driver information too
                        driverLocation.removeEventListener(this);

                    } else {
                        if (Common.marketList.get(driverGeoModel.getKey()) != null) {
                            GeoQueryModel geoQueryModel = snapshot.getValue(GeoQueryModel.class);
                            AnimationModel animationModel = new AnimationModel(false, geoQueryModel);
                            if (Common.driverLocationSubcribe.get(driverGeoModel.getKey()) != null) {
                                Marker currentMarker = Common.marketList.get(driverGeoModel.getKey());
                                AnimationModel oldPosition = Common.driverLocationSubcribe.get(driverGeoModel.getKey());
                                String from = new StringBuilder()
                                        .append(oldPosition.getGeoQueryModel().getL().get(0))
                                        .append(",")
                                        .append(oldPosition.getGeoQueryModel().getL().get(1))
                                        .toString();

                                String to = new StringBuilder()
                                        .append(animationModel.getGeoQueryModel().getL().get(0))
                                        .append(",")
                                        .append(animationModel.getGeoQueryModel().getL().get(1))
                                        .toString();

                                moveMarkerAnimation(driverGeoModel.getKey(), animationModel, currentMarker, from, to);
                            } else {
                                Common.driverLocationSubcribe.put(driverGeoModel.getKey(), animationModel);
                            }

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Snackbar.make(getView(), error.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            });

        }

    }


    private void moveMarkerAnimation(String key, AnimationModel animationModel, Marker currentMarker, String from, String to) {
        if (!animationModel.isRun()) {
            //request Api
            compositeDisposable.add(iGoogleAPI.getDirections("driving",
                    "less_driving",
                    from, to,
                    getString(R.string.google_api_key))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(returnResult -> {
                        Log.d("API_RETURN", returnResult);
                        try {

                            //parse json
                            JSONObject jsonObject = new JSONObject(returnResult);
                            JSONArray jsonArray = jsonObject.getJSONArray("routes");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject route = jsonArray.getJSONObject(i);
                                JSONObject poly = route.getJSONObject("overview_polyline");
                                String polyline = poly.getString("points");
                                //polylineList=Common.decodePoly(polyline);
                                animationModel.setPolylineList(Common.decodePoly(polyline));
                            }

                            //Moving
                            //index=-1;
                            //next=1;
                            animationModel.setIndex(-1);
                            animationModel.setNext(1);

                            Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    if (animationModel.getPolylineList() != null && animationModel.getPolylineList().size() > 1) {
                                        if (animationModel.getIndex() < animationModel.getPolylineList().size() - 2) {
                                            //index++;
                                            animationModel.setIndex(animationModel.getIndex() + 1);
                                            //next=index+1;
                                            animationModel.setNext(animationModel.getIndex() + 1);
                                            //start=polylineList.get(index);
                                            animationModel.setStart(animationModel.getPolylineList().get(animationModel.getIndex()));
                                            //end=polylineList.get(next);
                                            animationModel.setEnd(animationModel.getPolylineList().get(animationModel.getNext()));
                                        }
                                        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 1);
                                        valueAnimator.setDuration(3000);
                                        valueAnimator.setInterpolator(new LinearInterpolator());
                                        valueAnimator.addUpdateListener(value -> {
                                            //v=value.getAnimatedFraction();
                                            animationModel.setV(value.getAnimatedFraction());
                                            //lat=v*end.latitude+(1-v)*start.latitude;
                                            animationModel.setLat(animationModel.getV() * animationModel.getEnd().latitude
                                                    + (1 - animationModel.getV()) * animationModel.getStart().latitude);
                                            //lng=v*end.longitude+(1-v)*start.longitude;
                                            animationModel.setLng(animationModel.getV() * animationModel.getEnd().longitude
                                                    + (1 - animationModel.getV()) * animationModel.getStart().longitude);
                                            LatLng newPos = new LatLng(animationModel.getLat(), animationModel.getLng());
                                            currentMarker.setPosition(newPos);
                                            currentMarker.setAnchor(0.5f, 0.5f);
                                            currentMarker.setRotation(Common.getBearing(animationModel.getStart(), newPos));
                                        });

                                        valueAnimator.start();
                                        if (animationModel.getIndex() < animationModel.getPolylineList().size() - 2)//reach destination
                                            animationModel.getHandler().postDelayed(this, 1500);
                                        else if (animationModel.getIndex() < animationModel.getPolylineList().size() - 1) {  //done
                                            animationModel.setRun(false);
                                            Common.driverLocationSubcribe.put(key, animationModel);
                                        }
                                    }
                                }
                            };

                            //run handel
                            animationModel.getHandler().postDelayed(runnable, 1500);
                        } catch (Exception e) {
                            Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    })
            );

        }

    }


}


/*
hong co tien buon qua ong co oi /:(((((
uoc gi thoi gian nay troi qua that nhanh con chan voi cai canh nay lam roi ong co oi

 */