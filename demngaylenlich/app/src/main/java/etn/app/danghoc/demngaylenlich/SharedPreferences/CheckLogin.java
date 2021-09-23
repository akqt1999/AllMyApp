package etn.app.danghoc.demngaylenlich.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class CheckLogin {
    private Context context;
    public static   SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String NAME = "nameCheck123", KEY_NAME_USER = "name_user";

    public CheckLogin(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    public void setNameUser(String nameUser) {
        editor.putString(KEY_NAME_USER, nameUser);
        editor.commit();
    }

    public String getNameUser() {
        return sharedPreferences.getString(KEY_NAME_USER, null);
    }


}
