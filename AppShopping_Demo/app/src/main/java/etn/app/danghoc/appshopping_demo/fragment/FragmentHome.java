package etn.app.danghoc.appshopping_demo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import etn.app.danghoc.appshopping_demo.ActivityBasket;
import etn.app.danghoc.appshopping_demo.ActivityInfoProduct;
import etn.app.danghoc.appshopping_demo.ContainFragment;
import etn.app.danghoc.appshopping_demo.DungChung.DungChung;
import etn.app.danghoc.appshopping_demo.R;
import etn.app.danghoc.appshopping_demo.ActivitySell;
import etn.app.danghoc.appshopping_demo.adapter.AdapterSanPham;
import etn.app.danghoc.appshopping_demo.model.ModelItemSanPham;
import etn.app.danghoc.appshopping_demo.ultil.CheckConnect;
import etn.app.danghoc.appshopping_demo.url.Url;


public class FragmentHome extends Fragment implements AdapterSanPham.OnCallBack ,View.OnClickListener {
    RecyclerView mRcv;
    AdapterSanPham adapter;
    public ModelItemSanPham sanPhamIntent;
    SwipeRefreshLayout refreshLayout;
    GridLayoutManager gridLayoutManager;
    ProgressBar progressBar;
    ImageButton mImgBtnBasket;
    int page=1,currentItem,totalItem,scrollOutItem;
    boolean isScrolling=false; // flase la no chua  cuon xuong
    boolean isLoading=false;// false la khi no chua load du lieu // tru la khi no dang load du lieu
    boolean limitData=false;
    mHandler mHandler;
    Button mBtnSell;
    Toolbar toolbar;
    int position=-1;
    Context  context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        refreshLayout=view.findViewById(R.id.refresh_layout);
        context=getActivity();
        init(view);
        if(ContainFragment.bundle.getInt("position")!=-1){
            //mRcv.scrollToPosition(ContainFragment.bundle.getInt("position"));
            mRcv.scrollToPosition();
            limitData=ContainFragment.bundle.getBoolean("limitdata");
            page=ContainFragment.bundle.getInt("page");
        }else{
            if(context!=null){
                getData(Url.URL_GET_SP,page);
            }
        }
        if(CheckConnect.haveNetworkConnection(getActivity())){
            loadMoreData();
        }else{
            CheckConnect.showToast(getActivity(),"no have internet");
        }
        refreshLayout();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void init(View view) {


        adapter = new AdapterSanPham(DungChung.LIST_PRODUCT, this);
        mRcv = view.findViewById(R.id.recycleview_products);
        mRcv.setHasFixedSize(true);// fix vi tri
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRcv.setLayoutManager(gridLayoutManager);
        mRcv.setAdapter(adapter);


        progressBar=view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        mHandler=new mHandler();

        //mTvSeach=findViewById(R.id.textview_seach);

        toolbar=view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        mBtnSell=view.findViewById(R.id.button_sell);
        mBtnSell.setOnClickListener(this);

        mImgBtnBasket=view.findViewById(R.id.imagebutton_basket);
        mImgBtnBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ActivityBasket.class));
            }
        });

    }

    @Override
    public void itemOnClick(int position) {
        Intent intent = new Intent(getActivity(), ActivityInfoProduct.class);
        sanPhamIntent = DungChung.LIST_PRODUCT.get(position);

        Bundle bundle = new Bundle();
        intent.putExtra("dulieu", sanPhamIntent);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_sell:
                //startActivity(new Intent(this,SellActivity2.class));
                Intent intent = new Intent(getActivity(), ActivitySell.class);
                startActivity(intent);
                break;
        }

    }


    public class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0: progressBar.setVisibility(View.VISIBLE);
                    break;
                case 1 :
                    page++;
                    if(context!=null){
                        getData(Url.URL_GET_SP,page);
                    }
                    isLoading=false;
                    break;

            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message=mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }

    public void refreshLayout(){

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                limitData=false;
                if(DungChung.LIST_PRODUCT.size()>0){
                    DungChung.LIST_PRODUCT.clear();
                    adapter.notifyDataSetChanged();
                }
                if(CheckConnect.haveNetworkConnection(getActivity())){
                    getData(Url.URL_GET_SP,page);
                }else{
                    CheckConnect.showToast(getActivity(),"no have internet");
                }
                refreshLayout.setRefreshing(false);
            }
        });
    }




    public void getData(String urlGetData,int page){
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, urlGetData+page, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if(response.length()>2){
                            progressBar.setVisibility(View.GONE);
                            for (int i = 0; i <response.length() ; i++) {
                                try {// link picture , ten , gia, mo ta
                                    JSONObject jsonObject=response.getJSONObject(i);
                                    DungChung.LIST_PRODUCT.add(new ModelItemSanPham(
                                            Integer.parseInt(jsonObject.getString("id")) ,
                                            jsonObject.getString("hinhAnh"),
                                            jsonObject.getString("tenSp"),
                                            Double.parseDouble( jsonObject.getString("giaSp")),
                                            jsonObject.getString("moTa"),
                                            jsonObject.getString("nameUser")
                                    ));
                                    adapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else{
                            limitData=true;
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "over data", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "loi : "+error, Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(jsonArrayRequest);
    }


    private void loadMoreData() {

        mRcv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                    position=gridLayoutManager.findFirstVisibleItemPosition();
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling=true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItem=gridLayoutManager.getChildCount();
                totalItem=gridLayoutManager.getItemCount();
                scrollOutItem=gridLayoutManager.findFirstVisibleItemPosition();// co tim nhung item da xem
                if(isScrolling&&currentItem+scrollOutItem==totalItem&&!isLoading&&!limitData){
                    isScrolling=false;
                    isLoading=true;
                    ThreadData threadData=new ThreadData();
                    threadData.start();
                }
                hideButtonSell(dy);



            }
        });
    }



    private void hideButtonSell(int dy){
        // scroll dow hide // scroll up show
        if(dy<0){// scroll up
            mBtnSell.setVisibility(View.VISIBLE);
        }else if(dy>0){ // scroll down
            mBtnSell.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ContainFragment.bundle.putInt("position",position);
        ContainFragment.bundle.putBoolean("limitdata",limitData);
        ContainFragment.bundle.putInt("page",page);
    }
}
