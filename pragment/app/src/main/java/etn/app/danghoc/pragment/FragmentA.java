package etn.app.danghoc.pragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import etn.app.danghoc.pragment.adapter.AdapterListA;
import etn.app.danghoc.pragment.model.ItemListViewA;

public class FragmentA extends Fragment {
  private   Button buttonA;
    AdapterListA adapterA;
    ListView listViewA;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_a, container, false);
        buttonA = (Button) view.findViewById(R.id.button_fragment_a);
        listViewA = (ListView) view.findViewById(R.id.listview_a);
        // setDataList();

        if(MainActivity.listA!=null){
            adapterA = new AdapterListA(getActivity(), R.layout.itemlist_view_a,MainActivity.listA);
            listViewA.setAdapter(adapterA);
        }
         listViewA.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Toast.makeText(getActivity(), "position  fragment : "+position, Toast.LENGTH_SHORT).show();
             }
         });




        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "AAAAAAAA", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void setDataList() {
        String mName = null, mNumber = null;

        Bundle bundle = getArguments();
        if (bundle != null) {
            mNumber = bundle.getString("NUMBER", "");

            mName = bundle.getString("NAME", "");
        }
        Toast.makeText(getActivity(), "name:" + mName, Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), "number: " + mNumber, Toast.LENGTH_SHORT).show();

//
    }

}
/*
ch??? l?? th??ng n??m r???i m??nh h???a v???i ??ng c??? l?? h???t n??m nay m??nh s??? l??m ???????c nh??ng g???n n???a th???i gian r???i m?? v???n  m???i s?? s??
l??c m??nh n??i v???i ??ng c??? l?? ??ng c??? b???o ph???i th???t c?? c??? c??? g???ng c??? g???ng 1/3 ch???n ???????ng r???i
 */