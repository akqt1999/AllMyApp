package etn.app.danghoc.gettinhhuyen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import etn.app.danghoc.gettinhhuyen.Retrofit.IMyShoppingAPI;
import etn.app.danghoc.gettinhhuyen.Retrofit.RetrofitClient;
import etn.app.danghoc.gettinhhuyen.adapter.CategoryAdapter;
import etn.app.danghoc.gettinhhuyen.adapter.DistrictAdapter;
import etn.app.danghoc.gettinhhuyen.adapter.WardAdapter;
import etn.app.danghoc.gettinhhuyen.model.District;
import etn.app.danghoc.gettinhhuyen.model.Tinh;
import etn.app.danghoc.gettinhhuyen.model.TinhModel;
import etn.app.danghoc.gettinhhuyen.model.Ward;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    IMyShoppingAPI myRestaurantAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    List<Tinh> provinceList = new ArrayList<>();
    List<District> districtList = new ArrayList<>();
    List<Ward> wardList = new ArrayList<>();
    CategoryAdapter adapter;
    DistrictAdapter districtAdapter;
    WardAdapter wardAdapter;

    Spinner spinner, spinner_district, spinner_ward;
    String ward = "", district = "", province = "";
    TextView txt_address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display2);


        init1();

        displayProvince();


    }

    private void displayWard(int district_id) {
        compositeDisposable.add(myRestaurantAPI.getWord(
                "8ce54678-f9b7-11eb-bfef-86bbb1a09031",
                "application/json",
                district_id
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    try {
                        if (wardList.size() > 0)
                            wardList.clear();
                        wardList = s.getData();


                        wardAdapter = new WardAdapter(this, R.layout.item_selected_province, wardList);

                        spinner_ward.setAdapter(wardAdapter);

                        wardAdapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }, throwable -> {
                    Toast.makeText(this, "loi" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }));
        spinner_ward.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ward = wardList.get(i).getWardName();
                displayAddress();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void displayDistrict(int province_id) {
        compositeDisposable.add(myRestaurantAPI.getDistrict(
                "8ce54678-f9b7-11eb-bfef-86bbb1a09031",
                "application/json",
                province_id
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    try {
                        if (districtList.size() > 0)
                            districtList.clear();

                        districtList = s.getData();

                        //xoa cac huyen loi
                        deleteErrorDistrict();


                        Toast.makeText(this, "district_id" + districtList.get(0).getDistrictID(), Toast.LENGTH_SHORT).show();

                        districtAdapter = new DistrictAdapter(this, R.layout.item_selected_province, districtList);

                        spinner_district.setAdapter(districtAdapter);

                        districtAdapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }, throwable -> {
                    Toast.makeText(this, "loi" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }));
        spinner_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int district_id = districtList.get(i).getDistrictID();
                displayWard(district_id);
                district = districtList.get(i).getDistrictName();
                displayAddress();
                Toast.makeText(MainActivity.this, "district_id" + districtList.get(i).getDistrictID(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void deleteErrorDistrict() {
        if (districtList.get(0).getDistrictID() == 3451)
            districtList.remove(0);
        if (districtList.get(0).getDistrictID() == 3450)
            districtList.remove(0);
        if (districtList.get(0).getDistrictID() == 3442)
            districtList.remove(0);
        if (districtList.get(0).getDistrictID() == 3447)
            districtList.remove(0);

        // xoa hcm
        for (int i = 0; i < districtList.size(); i++) {
            if (districtList.get(i).getDistrictID() == 1580)
                districtList.remove(i);
        }
        for (int i = 0; i < districtList.size(); i++) {
            if (districtList.get(i).getDistrictID() == 3448)
                districtList.remove(i);
        }

        for (int i = 0; i < districtList.size(); i++) {
            if (districtList.get(i).getDistrictID() == 3449)
                districtList.remove(i);
        }



    }


    private void displayProvince() {
        compositeDisposable.add(myRestaurantAPI.getProvince("8ce54678-f9b7-11eb-bfef-86bbb1a09031",
                "application/json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    try {
                        if (provinceList.size() > 0)
                            provinceList.clear();

                        provinceList = s.getResult();
                        Toast.makeText(this, "province_id" + provinceList.get(0).getProvinceID(), Toast.LENGTH_SHORT);

                        adapter = new CategoryAdapter(this, R.layout.item_selected_province, provinceList);

                        spinner.setAdapter(adapter);


                    } catch (Exception e) {
                        Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }, throwable -> {
                    Toast.makeText(this, "loi" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }));


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                spinner_district.setVisibility(View.VISIBLE);
                int province_id = provinceList.get(position).getProvinceID();
                displayDistrict(province_id);
                province = provinceList.get(position).getProvinceName();
                Toast.makeText(MainActivity.this, "province_id" + provinceList.get(position).getProvinceID(), Toast.LENGTH_SHORT).show();
                displayAddress();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spinner_district.setVisibility(View.GONE);
            }
        });

    }

    private void displayAddress() {
        txt_address.setText(new StringBuilder().append(ward)
                .append(" ")
                .append(district)
                .append(" ")
                .append(province));
    }

    private void init1() {
        myRestaurantAPI = RetrofitClient.getInstance("https://dev-online-gateway.ghn.vn/").create(IMyShoppingAPI.class);

        spinner = findViewById(R.id.spinner);
        spinner_district = findViewById(R.id.spinner_district);
        spinner_ward = findViewById(R.id.spinner_ward);
        spinner_district.setVisibility(View.GONE);

        txt_address = findViewById(R.id.txt_address);


    }

}

//ok fix xong loi