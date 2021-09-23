//package etn.app.danghoc.appshopping_demo;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AbsListView;
//import android.widget.Button;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import etn.app.danghoc.appshopping_demo.adapter.AdapterSanPham;
//import etn.app.danghoc.appshopping_demo.DungChung.DungChung;
//import etn.app.danghoc.appshopping_demo.model.ModelItemSanPham;
//import etn.app.danghoc.appshopping_demo.ultil.CheckConnect;
//
//public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterSanPham.OnCallBack {
//    RecyclerView mRcv;
//    AdapterSanPham adapter;
//    List<ModelItemSanPham> list;
//    public ModelItemSanPham sanPhamIntent;
//    SwipeRefreshLayout refreshLayout;
//    GridLayoutManager gridLayoutManager;
//    ProgressBar progressBar;
//
//    String urlGetData="http://192.168.1.8:8080/demobanhang/getsp.php?page=";
//    String urlGetDataBasket="http://192.168.1.8:8080/demobanhang/getbasket.php";
//    int page=1,currentItem,totalItem,scrollOutItem;
//    boolean isScrolling=false; // flase la no chua  cuon xuong
//    boolean isLoading=false;// false la khi no chua load du lieu // tru la khi no dang load du lieu
//    boolean limitData=false;
//    mHandler mHandler;
//    Button mBtnSell;
//    Toolbar toolbar;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_home);
//        list = new ArrayList<>(); // nay gio khong duoc la do cai nay de trong ham get data nay khong duoc tai vi cai nay de o trong nen no khong co
//
//        refreshLayout=findViewById(R.id.refresh_layout);
//        init();
//        if(CheckConnect.haveNetworkConnection(getApplicationContext())){
//            getData(urlGetData,page);
//            getDataBasket(urlGetDataBasket);
//            loadMoreData();
//        }else{
//            CheckConnect.showToast(getApplicationContext(),"no have internet");
//        }
//
//        refreshLayout();
//    }
//
//
//    @Override
//
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_basket, menu);
//      //  SearchView searchView=m
//        return true;
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.item_basket:
//                startActivity(new Intent(this, ActivityPay.class));
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void init() {
//
//
//
//        adapter = new AdapterSanPham(list, this);
//        mRcv = findViewById(R.id.recycleview_products);
//        mRcv.setHasFixedSize(true);// fix vi tri
//        gridLayoutManager = new GridLayoutManager(this, 2);
//        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
//        mRcv.setLayoutManager(gridLayoutManager);
//        mRcv.setAdapter(adapter);
//
//        //LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//        //footerView=inflater.inflate(R.layout.progessbar,null);
//
//        progressBar=findViewById(R.id.progressbar);
//        progressBar.setVisibility(View.GONE);
//
//        mHandler=new mHandler();
//
//
//        toolbar=findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//
//        mBtnSell=findViewById(R.id.button_sell);
//        mBtnSell.setOnClickListener(this);
//
//    }
//
//    @Override
//    public void itemOnClick(int position) {
//        Intent intent = new Intent(this, ActivityInfoProduct.class);
//        sanPhamIntent = list.get(position);
//
//        Bundle bundle = new Bundle();
//        intent.putExtra("dulieu", sanPhamIntent);
//        startActivity(intent);
//    }
//
//
//
//
//
//    public void getData(String urlGetData,int page){
//        RequestQueue requestQueue= Volley.newRequestQueue(this);
//        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, urlGetData+page, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        if(response.length()>2){
//                            progressBar.setVisibility(View.GONE);
//                            for (int i = 0; i <response.length() ; i++) {
//                                try {// link picture , ten , gia, mo ta
//                                    JSONObject jsonObject=response.getJSONObject(i);
//                                    list.add(new ModelItemSanPham(
//                                            Integer.parseInt(jsonObject.getString("id")) ,
//                                            jsonObject.getString("hinhAnh"),
//                                            jsonObject.getString("tenSp"),
//                                            Double.parseDouble( jsonObject.getString("giaSp")),
//                                            jsonObject.getString("moTa"),
//                                            jsonObject.getString("nameUser"))
//                                    );
//                                    adapter.notifyDataSetChanged();
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }else{
//                            limitData=true;
//                            progressBar.setVisibility(View.GONE);
//                            Toast.makeText(MainActivity.this, "over data", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this, "loi : "+error, Toast.LENGTH_SHORT).show();
//            }
//        }
//        );
//        requestQueue.add(jsonArrayRequest);
//    }
//
//
//    private void loadMoreData() {
//       mRcv.addOnScrollListener(new RecyclerView.OnScrollListener() {
//           @Override
//           public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//               super.onScrollStateChanged(recyclerView, newState);
//               if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
//                   isScrolling=true; //
//               }
//           }
//
//           @Override
//           public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//               super.onScrolled(recyclerView, dx, dy);
//               currentItem=gridLayoutManager.getChildCount();
//               totalItem=gridLayoutManager.getItemCount();
//               scrollOutItem=gridLayoutManager.findFirstVisibleItemPosition();// co tim nhung item da xem
//               if(isScrolling&&currentItem+scrollOutItem==totalItem&&!isLoading&&!limitData){
//                    isScrolling=false;
//                    isLoading=true;
//                    ThreadData threadData=new ThreadData();
//                    threadData.start();
//               }
//               hideButtonSell(dy);
//
//
//
//           }
//       });
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.button_sell:
//                //startActivity(new Intent(this,SellActivity2.class));
//                Intent intent = new Intent(this, ActivitySell.class);
//                startActivity(intent);
//                break;
//        }
//    }
//
//
//
//    public class mHandler extends Handler{
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            switch (msg.what){
//                case 0: progressBar.setVisibility(View.VISIBLE);
//                break;
//                case 1 :
//                    page++;
//                    getData(urlGetData,page);
//                isLoading=false;
//                break;
//
//            }
//            super.handleMessage(msg);
//        }
//    }
//
//    public class ThreadData extends Thread{
//        @Override
//        public void run() {
//            mHandler.sendEmptyMessage(0);
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            Message message=mHandler.obtainMessage(1);
//            mHandler.sendMessage(message);
//            super.run();
//        }
//    }
//
//    public void refreshLayout(){
//
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                page=1;
//                limitData=false;
//                if(list.size()>0){
//                    list.clear();
//                    adapter.notifyDataSetChanged();
//                }
//                if(CheckConnect.haveNetworkConnection(getApplicationContext())){
//                    getData(urlGetData,page);
//                }else{
//                    CheckConnect.showToast(getApplicationContext(),"no have internet");
//                }
//                refreshLayout.setRefreshing(false);
//            }
//        });
//    }
//
//    private void getDataBasket(String url){
//        RequestQueue requestQueue= Volley.newRequestQueue(this);
//        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                for (int i = 0; i <response.length() ; i++) {
//                    try {
//                        JSONObject jsonObject=response.getJSONObject(i);
//                        DungChung.listBasket.add(new ModelItemSanPham(Integer.parseInt(jsonObject.getString("id")), // id la id basket
//                                Integer.parseInt(jsonObject.getString("maSanPham")) ,
//                                Integer.parseInt(jsonObject.getString("soLuong")),
//                                Double.parseDouble(jsonObject.getString("tongTien")),
//                                jsonObject.getString("tenSp"),
//                                jsonObject.getString("hinhAnhSanPham"),
//                                Double.parseDouble(jsonObject.getString("giaSp"))));
//                        adapter.notifyDataSetChanged();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        });
//        requestQueue.add(jsonArrayRequest);
//    }
//
//    private void hideButtonSell(int dy){
//        // scroll dow hide // scroll up show
//        if(dy<0){// scroll up
//            mBtnSell.setVisibility(View.VISIBLE);
//        }else if(dy>0){ // scroll down
//            mBtnSell.setVisibility(View.INVISIBLE);
//        }
//    }
//
//}
