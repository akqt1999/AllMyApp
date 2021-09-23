package etn.app.danghoc.logintest.Retrofit2;

public class APIUtil {
    public static  String ip="192.168.1.8";
    public static final String Base_Url="http://"+ip+":8080/retrofit/";
    public static String url="http://192.168.1.8:8080/retrofit/";
    public static DataClient getData(){
        return RetrofitClient.getClient(Base_Url).create(DataClient.class);
    }
}
