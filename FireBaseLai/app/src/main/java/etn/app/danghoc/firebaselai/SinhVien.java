package etn.app.danghoc.firebaselai;

public class SinhVien {
    private String mName,mAddress;
    private double mScore;

    public SinhVien(String mName, String mAddress, double mScore) {
        this.mName = mName;
        this.mAddress = mAddress;
        this.mScore = mScore;
    }

    public SinhVien() {}

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public double getScore() {
        return mScore;
    }

    public void setScore(double mScore) {
        this.mScore = mScore;
    }
}
