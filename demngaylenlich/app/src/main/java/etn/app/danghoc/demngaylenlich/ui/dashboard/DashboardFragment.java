package etn.app.danghoc.demngaylenlich.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import etn.app.danghoc.demngaylenlich.ActivitySplash;
import etn.app.danghoc.demngaylenlich.Commonn.Commonn;
import etn.app.danghoc.demngaylenlich.R;
import etn.app.danghoc.demngaylenlich.SharedPreferences.CheckLogin;

public class DashboardFragment extends Fragment {
    TextView txtNameUser;
    Button btnLogout;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
          txtNameUser = root.findViewById(R.id.txtNameUser);
          btnLogout=root.findViewById(R.id.btnLogout);

        txtNameUser.setText("Name User : "+ Commonn.checkLogin.getNameUser());
        btnLogout.setOnClickListener(v -> {
           CheckLogin.sharedPreferences.edit().remove(CheckLogin.KEY_NAME_USER).commit();
            startActivity(new Intent(getContext(), ActivitySplash.class));
            getActivity().finish();
        });

        return root;
    }
}