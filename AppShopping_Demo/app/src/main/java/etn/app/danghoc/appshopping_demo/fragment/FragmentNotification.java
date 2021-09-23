package etn.app.danghoc.appshopping_demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import etn.app.danghoc.appshopping_demo.CheckLogin;
import etn.app.danghoc.appshopping_demo.DungChung.DungChung;
import etn.app.danghoc.appshopping_demo.Notification2;
import etn.app.danghoc.appshopping_demo.R;
import etn.app.danghoc.appshopping_demo.Retrofit.APIUtil;
import etn.app.danghoc.appshopping_demo.Retrofit.DataClient;
import etn.app.danghoc.appshopping_demo.adapter.AdapterNotification;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static etn.app.danghoc.appshopping_demo.DungChung.DungChung.LIST_NOTIFICATION;

public class FragmentNotification extends Fragment {
    RecyclerView recyclerNotification;
    CheckLogin checkLogin;
    ArrayList<Notification2>listNotificationAdapter;
    AdapterNotification adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_notification,container,false);
        checkLogin=new CheckLogin(getActivity());
        listNotificationAdapter=new ArrayList<>();
        getDataNotification();

        recyclerNotification=view.findViewById(R.id.recycleview_notification);
        recyclerNotification.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerNotification.setLayoutManager(gridLayoutManager);

        return view;
    }



    private void getDataNotification(){
        DataClient dataClient= APIUtil.getData();
        Call<List<Notification2>> callBack=dataClient.GetNotification("b4");
        callBack.enqueue(new Callback<List<Notification2>>() {
            @Override
            public void onResponse(Call<List<Notification2>> call, Response<List<Notification2>> response) {
                DungChung.LIST_NOTIFICATION = response.body();
                listNotificationAdapter= (ArrayList<Notification2>) response.body();
                adapter=new AdapterNotification(listNotificationAdapter ,getActivity());
                recyclerNotification.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }


            @Override
            public void onFailure(Call<List<Notification2>> call, Throwable t) {
                Toast.makeText(getActivity(), "loi "+t.toString(), Toast .LENGTH_SHORT).show();
            }
        });

    }




}
