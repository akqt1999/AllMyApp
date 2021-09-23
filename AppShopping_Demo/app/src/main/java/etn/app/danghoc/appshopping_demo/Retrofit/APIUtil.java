package etn.app.danghoc.appshopping_demo.Retrofit;

public class APIUtil {
    public static String ip="192.168.1.4";
    public static final String URL="http://"+ip+":8080/demobanhang/";
    public static DataClient getData(){
        return RetrofitClient.getClient(URL).create(DataClient.class);
    }
}
