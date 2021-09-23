package etn.app.danghoc.likeuberrider.CallBack;

import etn.app.danghoc.likeuberrider.Model.DriverGeoModel;

public interface IFirebaseDriverInfoListener {
    void onDriverInfoLoadSuccess(DriverGeoModel driverGeoModel);
}
