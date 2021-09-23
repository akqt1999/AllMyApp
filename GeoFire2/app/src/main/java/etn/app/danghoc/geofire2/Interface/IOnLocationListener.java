package etn.app.danghoc.geofire2.Interface;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import etn.app.danghoc.geofire2.MyLatLng;

public interface IOnLocationListener {
    void onLoadLocationSuccess(List<MyLatLng>latLngs);
    void onLoadLocationFail(String message);
}
