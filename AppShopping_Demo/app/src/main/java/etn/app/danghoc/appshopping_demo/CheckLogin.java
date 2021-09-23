package etn.app.danghoc.appshopping_demo;

import android.content.Context;
import android.content.SharedPreferences;

import javax.xml.namespace.QName;

public class CheckLogin {
    SharedPreferences preferences;
    Context context;
    SharedPreferences.Editor editor;
    public static String NAME = "check";
    public static String KEY_IS_LOGIN = "islogin";
    public static String KEY_NAME_USER = "nameuser";

    public CheckLogin(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setIsLogin(boolean isLogin) {
        editor.putBoolean(KEY_IS_LOGIN, isLogin);
        editor.commit();
    }

    public boolean isLogin() {
        return preferences.getBoolean(KEY_IS_LOGIN, false);
    }

    public void setNameUser(String nameUser) {
        editor.putString(KEY_NAME_USER, nameUser);
        editor.commit();
    }

    public String getNameUser(){
        return preferences.getString(KEY_NAME_USER,"");
    }




}
