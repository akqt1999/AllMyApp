package etn.app.danghoc.gridviewcustom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    AdapterImage adapter;
    ArrayList<Image> images;
    private int []imageName = {R.drawable.sky,R.drawable.samsung,R.drawable.ip,R.drawable.htc,R.drawable.lg,R.drawable.wp,R.drawable.sky,R.drawable.samsung,R.drawable.ip,R.drawable.htc,R.drawable.lg,R.drawable.wp};
    private String []stt={"image 1","image 2","image 3","image 4","image 5","image 6","image 7","image 8","image 9","image 10","image 11","image 12"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView=findViewById(R.id.grImage);
        images=new ArrayList<>();
        adapter=new AdapterImage(this,R.layout.item_image,images);
        gridView.setAdapter(adapter);
        for(int i=0;i<stt.length;i++){
            images.add(new Image(imageName[i],stt[i]));
            adapter.notifyDataSetChanged();
        }
    }
}