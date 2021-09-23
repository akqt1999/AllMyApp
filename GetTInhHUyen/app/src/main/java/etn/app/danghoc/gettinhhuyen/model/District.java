package etn.app.danghoc.gettinhhuyen.model;

public class District {
   int  DistrictID;
   String DistrictName;

    public District(int districtID, String districtName) {
        DistrictID = districtID;
        DistrictName = districtName;
    }

    public int getDistrictID() {
        return DistrictID;
    }

    public void setDistrictID(int districtID) {
        DistrictID = districtID;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }
}
