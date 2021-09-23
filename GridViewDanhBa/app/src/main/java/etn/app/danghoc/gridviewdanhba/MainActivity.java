package etn.app.danghoc.gridviewdanhba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ListView listView;
ArrayList<DanhBa>arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arr=new ArrayList<>();
        arr.add(new DanhBa("dfkfk","0393784"));
        arr.add(new DanhBa("dddd","0999898"));
        arr.add(new DanhBa("fff","0000000"));
        arr.add(new DanhBa("c","0009"));
        arr.add(new DanhBa("v","55"));
        arr.add(new DanhBa("v","55"));
        arr.add(new DanhBa("g","44"));
        listView=findViewById(R.id.lvDanhBa);
        AdapterDanhBa adapter=new AdapterDanhBa(this,R.layout.item,arr);
        listView.setAdapter(adapter);
    }
}