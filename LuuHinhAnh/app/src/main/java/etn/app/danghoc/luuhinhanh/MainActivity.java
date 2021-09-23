package etn.app.danghoc.luuhinhanh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Recycler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import etn.app.danghoc.luuhinhanh.Adapter.Adapter;

public class MainActivity extends AppCompatActivity {
    public static List<ItemImage> itemImageList;
    RecyclerView rcvImage;
    DatabseImage database;
   public static Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new DatabseImage(this);
        itemImageList = new ArrayList<>();
        itemImageList=database.getData();
        rcvImage = findViewById(R.id.recycleview_image);
        rcvImage.setHasFixedSize(true);
        rcvImage.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Adapter(itemImageList);
        rcvImage.setAdapter(adapter);


    }

    public void addImage(View view) {
        startActivity(new Intent(MainActivity.this, ThemDoVat.class));
    }



}
