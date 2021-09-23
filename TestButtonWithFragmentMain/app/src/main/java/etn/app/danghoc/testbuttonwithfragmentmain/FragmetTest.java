package etn.app.danghoc.testbuttonwithfragmentmain;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmetTest extends Fragment implements Lisener{
    private    Lisener lisener;
    Button buttonShow;
    private boolean checkClick = false;//false is hide ,true is show


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        buttonShow = view.findViewById(R.id.button_show_and_hide);
        eventClick();
        MainActivity mainActivity=new MainActivity();
        mainActivity.setListener(this);
        return view;
    }

    public FragmetTest(Lisener lisener) {

        this.lisener = lisener;
    }

    public FragmetTest() {

    }

    public void showAndHide(View view) {


    }
    public void setLisener(Lisener lisener) {
        this.lisener = lisener;
    }
    private void eventClick() {
        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCheckClick()) {
                    lisener.checkOnCick(true); // true is show
                    setCheckClick(!isCheckClick());
                } else {
                    lisener.checkOnCick(false);// false is hide
                    setCheckClick(!isCheckClick());
                }
            }
        });
    }

    public boolean isCheckClick() {
        return checkClick;
    }

    public void setCheckClick(boolean checkClick) {
        this.checkClick = checkClick;
    }


    @Override
    public void checkOnCick(boolean vv) {
        if(vv){
            buttonShow.setVisibility(View.VISIBLE);
        }else{
            buttonShow.setVisibility(View.INVISIBLE);
        }
    }
}
