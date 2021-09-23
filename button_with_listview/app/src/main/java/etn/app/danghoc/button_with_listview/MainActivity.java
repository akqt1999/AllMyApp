package etn.app.danghoc.button_with_listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;

import java.util.ArrayList;

import etn.app.danghoc.button_with_listview.adapterr.Adapter_fruit;
import etn.app.danghoc.button_with_listview.model.info_fruit;

public class MainActivity extends AppCompatActivity {
  ListView listViewAmountFruit;
  ArrayList<info_fruit> info_fruitArrayList;
  Adapter_fruit adapterFruit ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
          listViewAmountFruit=(ListView) findViewById(R.id.listview_fruit);
          info_fruitArrayList=new ArrayList<>();
/// chẵn l
        info_fruit info_fruit1=new info_fruit(12,"apple");
        info_fruit info_fruit3=new info_fruit(14,"banana");
        info_fruit info_fruit2=new info_fruit(1,"fruit dragon");
        info_fruit info_fruit5=new info_fruit(2,"strawberry");
        info_fruit info_fruit4=new info_fruit(2,"apple");

        info_fruitArrayList.add(info_fruit1);
        info_fruitArrayList.add(info_fruit2);
        info_fruitArrayList.add(info_fruit3);
        info_fruitArrayList.add(info_fruit4);
        info_fruitArrayList.add(info_fruit5);

        adapterFruit=new Adapter_fruit(this,R.layout.layout_fruit,info_fruitArrayList);
        listViewAmountFruit.setAdapter(adapterFruit);
    }
}
