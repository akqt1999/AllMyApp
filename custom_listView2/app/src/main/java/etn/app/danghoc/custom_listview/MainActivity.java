package etn.app.danghoc.custom_listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import etn.app.danghoc.custom_listview.adapter.AdapterName;
import etn.app.danghoc.custom_listview.model.Item;

public class MainActivity extends AppCompatActivity {
 ListView listViewName;
 List<Item> itemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewName=(ListView) findViewById(R.id.listview_name);
        Item item=new Item("1","nguyen xuant ri");
        Item item1=new Item("2","nguyen xjdn lọd");
        Item item2=new Item("4","nguyen xuant fdj");
        Item item3=new Item("5","nguyen xuant dfdf");
        itemList=new ArrayList<>();
        itemList.add(item);
        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);

        AdapterName adapterName =new AdapterName(this,R.layout.item_list_view,itemList);
        listViewName.setAdapter(adapterName);
    }
}
