package etn.app.danghoc.testlistviewxam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView txtDuocChon;
    ListView lvDienThoai;
    String arrDienThoai[]={"iphone 6","samSung s9","xiaomi note 9","oppo f9","HTC One E9","SamSung a7"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        ArrayAdapter<String>adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrDienThoai);
        lvDienThoai.setAdapter(adapter);
        lvDienThoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtDuocChon.setText(arrDienThoai[position]);
            }
        });
    }

    private void init() {

        txtDuocChon=findViewById(R.id.txtDienThoai);
        lvDienThoai=findViewById(R.id.listView);
    }
}