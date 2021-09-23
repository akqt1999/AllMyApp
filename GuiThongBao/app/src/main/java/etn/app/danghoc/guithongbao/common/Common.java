package etn.app.danghoc.guithongbao.common;

public class Common {
    public static final String NOTI_TITILE = "Title";
    public static final String NOTI_CONTENT = "Content";

    public static String createTopicOrder() {
        return new StringBuilder("/topics/new_order").toString();
    }
}
