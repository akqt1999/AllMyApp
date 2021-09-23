package etn.app.danghoc.viewflipper;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class ListenerSwipe extends GestureDetector.SimpleOnGestureListener {
    private static int MIN_SWIPE_DISTANCE_X=100;
    private static int MIN_SWIPE_DISTANCE_Y=100;

    private static int MAX_SWIPE_DISTANCE_X=1000;
    private static int MAX_SWIPE_DISTANCE_Y=100;

    private MainActivity mainActivity=null;

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
    float deltaX=e1.getX()-e2.getX(); // deltaX >0 : từ phải lước sang trái left | deltaX<0 : từ trái sang phải right
    float deltaY=e1.getY()-e2.getY();

    float deltaXABS=Math.abs(deltaX);
    float deltaYABS=Math.abs(deltaY);

    if(deltaXABS>=MIN_SWIPE_DISTANCE_X&&deltaYABS<=MAX_SWIPE_DISTANCE_X){
        if(deltaX>0){
            MainActivity.viewFlipperDemo.showNext();
        }else{
            MainActivity.viewFlipperDemo.showPrevious();
        }
    }

    return true;
    }
}
