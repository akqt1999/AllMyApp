package etn.app.danghoc.appshopping_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import etn.app.danghoc.appshopping_demo.DungChung.DungChung;
import etn.app.danghoc.appshopping_demo.model.ModelItemSanPham;
import etn.app.danghoc.appshopping_demo.url.Url;

public class ActivityInfoProduct extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImgProduct;
    private ImageButton mImbBtnPlus, mImbBtnMinus, mImBtnBack,mImgBtnBasket;
    private Button mBntAddBasket, mBtnBuyNow;
    private TextView mTvNameProduct, mTvPrice, mTvDescription, mTvAmount, mTvTitle,mTvSeller;
    public int mAmount = 1, maSp;
    ModelItemSanPham sanPham;
    private Toolbar toolbar;
    CheckLogin checkLogin;
    private String tenSp;
    double money = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_product);
        Intent intent = getIntent();
        sanPham = (ModelItemSanPham) intent.getSerializableExtra("dulieu");
        init();


    }



    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mImBtnBack = findViewById(R.id.imageButton_back);
        mImBtnBack.setOnClickListener(this);
        mImgBtnBasket=findViewById(R.id.imagebutton_basket);
        mImgBtnBasket.setOnClickListener(this);
        mTvTitle = findViewById(R.id.textview_title_toolbar);
        mTvTitle.setText("info product");
        mTvSeller=findViewById(R.id.textview_seller);

        if (sanPham != null) {
            maSp = sanPham.getIdSanPham();
            tenSp = sanPham.getNameSanPham();
            mTvAmount = (TextView) findViewById(R.id.textview_amount);
            mTvAmount.setText(mAmount + "");
            mBntAddBasket = (Button) findViewById(R.id.button_add_basket);
            mBntAddBasket.setOnClickListener(this);
            mBtnBuyNow = findViewById(R.id.button_buy_now);
            mBtnBuyNow.setOnClickListener(this);
            mImbBtnMinus = (ImageButton) findViewById(R.id.imagebutton_minus);
            mImbBtnMinus.setOnClickListener(this);
            mImbBtnPlus = (ImageButton) findViewById(R.id.imagebutton_plus);
            mImbBtnPlus.setOnClickListener(this);
            mImgProduct = (ImageView) findViewById(R.id.imageview_product);
            mTvDescription = (TextView) findViewById(R.id.textview_description_info);
            mTvDescription.setText(sanPham.getDescription());
            mTvNameProduct = (TextView) findViewById(R.id.textview_name_product_info);
            mTvNameProduct.setText(sanPham.getNameSanPham());
            mTvPrice = (TextView) findViewById(R.id.textview_price_info);
            mTvPrice.setText(sanPham.getPrice() + " vnd");
            mTvSeller.setText("seller : "+sanPham.getNameUserSell());
            if (mAmount <= 0) {
                mImbBtnMinus.setEnabled(false);
            }
            Glide.with(this).load(sanPham.getUrlImage()).centerCrop().override(200, 200).into(mImgProduct);

        } else {
            Toast.makeText(this, "san pham null", Toast.LENGTH_SHORT).show();
        }

        checkLogin = new CheckLogin(this);
        money=sanPham.getPrice();
    }

    @Override
    public void onClick(View v) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");


        switch (v.getId()) {
            case R.id.button_add_basket:
                if (sanPham.getNameUserSell().equalsIgnoreCase(checkLogin.getNameUser())) {
                    Toast.makeText(this, "Can't buy my own product", Toast.LENGTH_SHORT).show();
                }else{
                    insertDataTableBasket(Url.URL_INSERT_BASKET);
                    Toast.makeText(this, "add basket success", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.imagebutton_plus:
                mAmount++;
                mTvAmount.setText(mAmount + "");
                money = mAmount * sanPham.getPrice();
                mTvPrice.setText(decimalFormat.format(money) + " vnd");
                if (mAmount > 0) {
                    mImbBtnMinus.setEnabled(true);
                }
                break;
            case R.id.imagebutton_minus:
                mAmount--;
                mTvAmount.setText(mAmount + "");
                money = mAmount * sanPham.getPrice();
                mTvPrice.setText(decimalFormat.format(money) + " vnd");
                if (mAmount <= 0) {
                    mImbBtnMinus.setEnabled(false);
                }
                break;
            case R.id.imageButton_back:
                finish();
                break;
            case R.id.imagebutton_basket:
                startActivity(new Intent(ActivityInfoProduct.this,ActivityBasket.class));
                break;
        }
    }




    private void insertDataTableBasket(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityInfoProduct.this, "loi : " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("maSanPham", maSp + "");
                params.put("soLuong", mTvAmount.getText().toString());
                params.put("tongTien", money + "");
                params.put("tenSp", tenSp);
                params.put("usersell", sanPham.getNameUserSell());
                params.put("userbuy", checkLogin.getNameUser());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(DungChung.FINISH_ACTIVITY){
            finish();
        }
    }


}






