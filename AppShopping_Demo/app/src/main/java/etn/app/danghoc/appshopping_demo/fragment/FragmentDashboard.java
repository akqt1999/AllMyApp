package etn.app.danghoc.appshopping_demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import etn.app.danghoc.appshopping_demo.ActivityBasket;
import etn.app.danghoc.appshopping_demo.ActivityLogin;
import etn.app.danghoc.appshopping_demo.CheckLogin;
import etn.app.danghoc.appshopping_demo.R;
import etn.app.danghoc.appshopping_demo.ActivitySell;

public class FragmentDashboard extends Fragment implements View.OnClickListener  {
    Button btnSellYourProduct,btnLogin,btnLogout;
    CheckLogin checkLogin;
    Toolbar toolbar;
    TextView txtNameUser;
    ImageButton imgBtnBasket;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_dashboard,container,false);
        init(view);
        return view;
    }

    private void init(View view) {
        btnLogin=view.findViewById(R.id.button_login); btnLogin.setOnClickListener(this);
        btnLogout=view.findViewById(R.id.button_logout);btnLogout.setOnClickListener(this);
        btnSellYourProduct=view.findViewById(R.id.button_sell_your_product); btnSellYourProduct.setOnClickListener(this);
        checkLogin=new CheckLogin(getActivity());
        txtNameUser=view.findViewById(R.id.textview_name_user);
        if(checkLogin.isLogin()){
            btnLogout.setEnabled(true);
            btnLogin.setEnabled(false);
            txtNameUser.setText( checkLogin.getNameUser());
        }else{
            btnLogout.setEnabled(false);
            btnLogin.setEnabled(true);
            txtNameUser.setText("not logined in");
        }

        toolbar=view.findViewById(R.id.toolbar);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        imgBtnBasket=view.findViewById(R.id.imagebutton_basket);
        imgBtnBasket.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.button_sell_your_product:
                    startActivity(new Intent(getActivity(), ActivitySell.class));
                    break;
                case R.id.button_login :
                    startActivity(new Intent(getActivity(), ActivityLogin.class));
                    break;
                case R.id.button_logout:
                    checkLogin.setNameUser("");
                    checkLogin.setIsLogin(false);
                    btnLogout.setEnabled(false);
                    btnLogin.setEnabled(true);
                    txtNameUser.setText("not logined in");
                    break;
                case R.id.imagebutton_basket :
                    startActivity(new Intent(getActivity(), ActivityBasket.class));
            }
    }

}
