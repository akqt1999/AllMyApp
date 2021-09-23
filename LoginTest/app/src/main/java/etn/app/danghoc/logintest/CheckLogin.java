package etn.app.danghoc.logintest;


import android.content.Context;
import android.content.SharedPreferences;

public class CheckLogin {
    private static String TAG=CheckLogin.class.getName();
    SharedPreferences preferences;
    Context context;
    SharedPreferences.Editor editor;
    public static final String NAME="check";
    public static final String KEY_LOGIN="islogin";
    public static final String KEY_NAME_USER="nameuser";
    public static final   String KEY_URL_IMAGE="urlimage";

    public CheckLogin (Context context){
        this.context=context;
        preferences=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        editor=preferences.edit();
    }

    public void setKeyLogin(boolean isLogin){
        editor.putBoolean(KEY_LOGIN,isLogin);
        editor.commit();
    }
    public boolean isCheck(){
        return preferences.getBoolean(KEY_LOGIN,false);
    }

    public void setKeyNameUser(String userName){
        editor.putString(KEY_NAME_USER,userName);
        editor.commit();
    }
    public String getKeyNameUser(){
        return preferences.getString(KEY_NAME_USER,"");
    }

   public void setKeyUrlImage(String urlImage){
        editor.putString(KEY_URL_IMAGE,urlImage);
        editor.commit();
   }
   public String getKeyUrlImage(){
        return preferences.getString(KEY_URL_IMAGE,"");
   }
}
