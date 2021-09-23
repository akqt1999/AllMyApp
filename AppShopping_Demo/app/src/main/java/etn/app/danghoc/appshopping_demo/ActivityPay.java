package etn.app.danghoc.appshopping_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import etn.app.danghoc.appshopping_demo.BottomSheet.BottomSheetSelectPay;
import etn.app.danghoc.appshopping_demo.adapter.AdapterPay;
import etn.app.danghoc.appshopping_demo.DungChung.DungChung;
import etn.app.danghoc.appshopping_demo.model.ModelItemSanPham;
import etn.app.danghoc.appshopping_demo.url.Url;

public class ActivityPay extends AppCompatActivity implements AdapterPay.onCallBack, View.OnClickListener, BottomSheetSelectPay.onCallback {
    private RecyclerView mRcvProduct;
    private AdapterPay adapter;
    private List<ModelItemSanPham> mList;
    private TextView mTvSelectPay, mTvTotalPay;
    private EditText mEdtAddress, mEdtNote, mEdtNumberPhone;
    private double mTotalPay = 0;
    private Button mBtnPay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        init();
    }

    private void init() {
        mList = new ArrayList<>();
//        mList.add(new ModelItemSanPham("rau muong", "shop rau ", 300000, 3));
//        mList.add(new ModelItemSanPham("rau ma", "shop ngu ", 400000, 2));
//        mList.add(new ModelItemSanPham("rau den", "shop dit ", 500000, 5));
//        mList.add(new ModelItemSanPham("rau lon", "shop oc ", 340000, 2));
//        mList.add(new ModelItemSanPham("rau nung", "shop lon ", 350000, 4));
//        mList.add(new ModelItemSanPham("rau lan", "shop ngu ", 330000, 2));
//        mList.add(new ModelItemSanPham("rau muong", "shop rau ", 300000, 3));
//        mList.add(new ModelItemSanPham("rau ma", "shop ngu ", 400000, 2));
//        mList.add(new ModelItemSanPham("rau den", "shop dit ", 500000, 5));
//        mList.add(new ModelItemSanPham("rau lon", "shop oc ", 340000, 2));
//        mList.add(new ModelItemSanPham("rau nung", "shop lon ", 350000, 4));
//        mList.add(new ModelItemSanPham("rau lan", "shop ngu ", 330000, 2));
//        mList.add(new ModelItemSanPham("rau muong", "shop rau ", 300000, 3));
//        mList.add(new ModelItemSanPham("rau ma", "shop ngu ", 400000, 2));
//        mList.add(new ModelItemSanPham("rau den", "shop dit ", 500000, 5));
//        mList.add(new ModelItemSanPham("rau lon", "shop oc ", 340000, 2));
//        mList.add(new ModelItemSanPham("rau nung", "shop lon ", 350000, 4));
//        mList.add(new ModelItemSanPham("rau lan", "shop ngu ", 330000, 2));
//        mList.add(new ModelItemSanPham("rau muong", "shop rau ", 300000, 3));
//        mList.add(new ModelItemSanPham("rau ma", "shop ngu ", 400000, 2));
//        mList.add(new ModelItemSanPham("rau den", "shop dit ", 500000, 5));
//        mList.add(new ModelItemSanPham("rau lon", "shop oc ", 340000, 2));
//        mList.add(new ModelItemSanPham("rau nung", "shop lon ", 350000, 4));
//        mList.add(new ModelItemSanPham("rau lan", "shop ngu ", 330000, 2));

        adapter = new AdapterPay(DungChung.LISTBASKET, this);

        mRcvProduct = findViewById(R.id.recycleview_product_pay);
        mRcvProduct.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRcvProduct.setLayoutManager(gridLayoutManager);
        mRcvProduct.setAdapter(adapter);

        mTvSelectPay = findViewById(R.id.textview_select_pay);
        mTvSelectPay.setOnClickListener(this);

       mTotalPay= totalMoney();
        mTvTotalPay = findViewById(R.id.textview_total_pay);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        mTvTotalPay.setText(decimalFormat.format(mTotalPay));

        mEdtAddress = findViewById(R.id.edittext_address_pay);
        mEdtNote = findViewById(R.id.edittext_note);
        mEdtNumberPhone = findViewById(R.id.edittext_number_phone);

        mBtnPay=findViewById(R.id.button_pay);
        mBtnPay.setOnClickListener(this);
    }

    @Override
    public void itemNameStoreClick() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_select_pay:
                BottomSheetSelectPay bottomSheet = new BottomSheetSelectPay(this);
                bottomSheet.show(getSupportFragmentManager(), "bottomSheetSelectPay");
                break;
            case R.id.button_pay:
                if(checkInfo()){
                    insertDataInfoOder(Url.URL_INSERT_INFO_ODER);
                    startActivity(new Intent(ActivityPay.this,ActivityPaySuccess.class));
                }else{
                    Toast.makeText(this, "please type full info", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void clickSelectPay(String pay) {
        mTvSelectPay.setText(pay);
    }

    private void insertDataInfoOder(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityPay.this, "loi "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < DungChung.LISTBASKET.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("diaChiNguoiMua", mEdtAddress.getText().toString());
                        jsonObject.put("soDienThoai", mEdtNumberPhone.getText().toString());
                        jsonObject.put("idSanPham", DungChung.LISTBASKET.get(i).getIdSanPham());
                        jsonObject.put("soLuongSanPham", DungChung.LISTBASKET.get(i).getAmount());
                        jsonObject.put("gia", DungChung.LISTBASKET.get(i).getPrice());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(jsonObject);
                }
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("json", jsonArray.toString());
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }



    private double totalMoney() {
        double sum = 0;
        for (ModelItemSanPham cc : DungChung.LISTBASKET) {
            sum += cc.getTotalMoney();
        }
        return sum;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(DungChung.FINISH_ACTIVITY){
            finish();
        }
    }


    private boolean checkInfo(){
        if(mEdtAddress.getText().toString().equalsIgnoreCase("")||mEdtNote.getText().toString().equalsIgnoreCase("")||
        mEdtNumberPhone.getText().toString().equalsIgnoreCase("")){
            return false;
        }else{
            return true;
        }
    }

}
