package etn.app.danghoc.likeuberrider.Model;

public class RiderModel {
    public String getImageAvatar() {
        return ImageAvatar;
    }

    public void setImageAvatar(String imageAvatar) {
        ImageAvatar = imageAvatar;
    }

    private String firstName,LastName,phoneNumber,ImageAvatar;

    public RiderModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}
