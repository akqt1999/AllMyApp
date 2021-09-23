package etn.app.danghoc.grid_view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import etn.app.danghoc.grid_view.adapter.Adapter_img;
import etn.app.danghoc.grid_view.model.Image;

public class MainActivity extends AppCompatActivity {
    GridView gridViewWold;
    ArrayList<Image> imageArrayList;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // anh xa
         gridViewWold=(GridView) findViewById(R.id.gridview_word);

          Image image=new Image(R.drawable.bua,"cau bua");
           Image image1=new Image(R.drawable.bua,"cai rua");
           Image image2=new Image(R.drawable.bua,"vai nopi");
        Image image4=new Image(R.drawable.bua,"vai nopi");
        Image image3=new Image(R.drawable.bua,"vai nopi");
        Image image5=new Image(R.drawable.bua,"vai nopi");
        Image image6=new Image(R.drawable.bua,"vai nopi");
        Image image7=new Image(R.drawable.bua,"vai nopi");
        Image image8=new Image(R.drawable.bua,"vai nopi");
        Image image9=new Image(R.drawable.bua,"vai nopi");
        Image image10=new Image(R.drawable.bua,"vai nopi");

        imageArrayList=new ArrayList<>();
        imageArrayList.add(image);
        imageArrayList.add(image1);
        imageArrayList.add(image2);
        imageArrayList.add(image3);
        imageArrayList.add(image4);
        imageArrayList.add(image5);
        imageArrayList.add(image6);
        imageArrayList.add(image7);
        imageArrayList.add(image8);
        imageArrayList.add(image9);
        imageArrayList.add(image10);


        Adapter_img adapterImg=new Adapter_img(this,R.layout.layout_image,imageArrayList);
        gridViewWold.setAdapter(adapterImg);
        /// bắt sự kiện

    }


}
