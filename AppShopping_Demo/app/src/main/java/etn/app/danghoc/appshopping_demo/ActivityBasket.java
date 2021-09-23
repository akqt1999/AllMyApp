package etn.app.danghoc.appshopping_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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

import etn.app.danghoc.appshopping_demo.adapter.AdapterBasket;
import etn.app.danghoc.appshopping_demo.DungChung.DungChung;
import etn.app.danghoc.appshopping_demo.model.ModelItemSanPham;
import etn.app.danghoc.appshopping_demo.url.Url;

public class ActivityBasket extends AppCompatActivity implements AdapterBasket.OnCallBack, View.OnClickListener {

    public AdapterBasket  adapter;
    private RecyclerView mRcvBasket;
    private TextView mTvTotalMoney;
    private Button mBtnPayALl;
    private Toolbar toolbar;
    ImageButton mImBtnBack;
    TextView mTvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        DungChung.LISTBASKET=new ArrayList<>();
        getDataBasket(Url.URL_GET_BASKET);
        init();



    }

    private void init() {
        adapter=new AdapterBasket(DungChung.LISTBASKET, this);

        mRcvBasket=findViewById(R.id.recycleview_products_basket);
        mRcvBasket.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRcvBasket.setLayoutManager(gridLayoutManager);
        mRcvBasket.setAdapter(adapter);

        mTvTotalMoney=(TextView) findViewById(R.id.textview_total_money_basket);
        toolbar=findViewById(R.id.toolbar_basket);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mImBtnBack=findViewById(R.id.imageButton_back);
        mImBtnBack.setOnClickListener(this);

        mTvTitle=findViewById(R.id.textview_title_toolbar);
        mTvTitle.setText("basket");

        mBtnPayALl=findViewById(R.id.button_pay_all_basket);
        mBtnPayALl.setOnClickListener(this);
    }




    @Override
    public void itemOnClick(int position) {

    }

    @Override
    public void totalMoney(double total) {
        mTvTotalMoney.setText("Total \n"+total );
    }

    @Override
    public void clickBuyLater(int position) {
        DungChung.LISTBASKET.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.imageButton_back:
              finish();
              break;
          case R.id.button_pay_all_basket :
              Intent intent=new Intent(this, ActivityPay.class);
              startActivity(intent);
      }
    }


    private void getDataBasket(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i <response.length() ; i++) {
                    try {
                        JSONObject jsonObject=response.getJSONObject(i);
                       DungChung.LISTBASKET.add(new ModelItemSanPham(Integer.parseInt(jsonObject.getString("id")), // id la id basket
                                Integer.parseInt(jsonObject.getString("maSanPham")) ,
                                Integer.parseInt(jsonObject.getString("soLuong")),
                                Double.parseDouble(jsonObject.getString("tongTien")),
                                jsonObject.getString("tenSp"),
                                jsonObject.getString("hinhAnhSanPham"),
                                Double.parseDouble(jsonObject.getString("giaSp"))));
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(DungChung.FINISH_ACTIVITY){
            finish();
        }
    }
}
