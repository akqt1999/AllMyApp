package etn.app.danghoc.recycleview_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

import etn.app.danghoc.recycleview_demo.adapter.AdapterDemo;
import etn.app.danghoc.recycleview_demo.model.ItemDemo;

public class MainActivity extends AppCompatActivity implements AdapterDemo.OnCallBack {
   private RecyclerView mRcvDemo;
   private AdapterDemo mAdapter;
   private List<ItemDemo> mList;
    LinearLayoutManager linearLayoutManager;
    SharedPreferences.Editor editor;
    Context context;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
        int a=sharedPreferences.getInt("vt",0);
        Toast.makeText(this, a+"", Toast.LENGTH_SHORT).show();
        initView();
        mRcvDemo.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                 position=linearLayoutManager.findFirstVisibleItemPosition();
            }
        });
    }

    private void initView() {
        mList=new ArrayList<>();
        for(int i=0;i<20;i++){
            mList.add(new ItemDemo("hello everyone "));
            mList.add(new ItemDemo("hello guy "));
            mList.add(new ItemDemo("hello i feel so good "));
            mList.add(new ItemDemo("hello i wanna fuck you "));
            mList.add(new ItemDemo("hello kon cet "));
        }
        mRcvDemo = findViewById(R.id.recyclerview_demo);
        mRcvDemo.setHasFixedSize(true);// fix vi tri
         linearLayoutManager=new LinearLayoutManager(this);
        mRcvDemo.setLayoutManager(linearLayoutManager);
        mAdapter=new AdapterDemo(this,mList);
        mRcvDemo.setAdapter(mAdapter);

    }

    @Override
    public void onItemClick(int position) {
        ItemDemo itemDemo=mList.get(position);
        mAdapter.setCheck(true);
        itemDemo.setCheck(!itemDemo.isCheck());

    }

    @Override
    public void onITemLongClick(int position) {
         ItemDemo itemDemo=mList.get(position);
        Toast.makeText(this, "long click : "+position, Toast.LENGTH_SHORT).show();
         mList.remove(itemDemo);
         mAdapter.notifyDataSetChanged();

        //itemDemo.setCheck(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("vt",position);
        editor.apply();
    }
}
