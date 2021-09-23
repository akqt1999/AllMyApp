package etn.app.danghoc.testlistviewdienthoai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lvPhone;
    List<Phone>phones;
    private String [] arrayName = {"Điện thoại Sky","Điện thoại SamSung","Điện thoại IP","Điện thoại HTC","Điện thoại LG","Điện thoại WP"};
    private int []imageName = {R.drawable.sky,R.drawable.samsung,R.drawable.ip,R.drawable.htc,R.drawable.lg,R.drawable.wp};
    AdapterPhone adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvPhone=findViewById(R.id.lvPhone);
        phones=new ArrayList<>();
        adapter=new AdapterPhone(this,R.layout.item_phone,phones);
        lvPhone.setAdapter(adapter);
        for(int i=0;i<arrayName.length;i++){
            phones.add(new Phone(imageName[i],arrayName[i]));
            adapter.notifyDataSetChanged();
        }
        lvPhone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,ActivitySub.class);
                Bundle bundle=new Bundle();
                bundle.putString("NAME",arrayName[position]);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}