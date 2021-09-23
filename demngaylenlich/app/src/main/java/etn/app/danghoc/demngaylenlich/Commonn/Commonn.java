package etn.app.danghoc.demngaylenlich.Commonn;

import com.google.firebase.FirebaseApp;

import java.util.List;

import etn.app.danghoc.demngaylenlich.Model.User;
import etn.app.danghoc.demngaylenlich.SharedPreferences.CheckLogin;

public class Commonn {

    public static final String COLLECTION_REF = "InfoUser";
    public static CheckLogin checkLogin;
    public static List<User> userList;
    public static User user;
}
