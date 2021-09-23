package etn.app.danghoc.testgridview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView txtSelect;
    GridView gridView;
    String arr[]={"a","b","c","f","t","y","y","w","h","h","v","t"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtSelect=findViewById(R.id.txtSelect);
        gridView=findViewById(R.id.gridView);
        ArrayAdapter<String>adapter=new ArrayAdapter<String >(this,android.R.layout.simple_list_item_1,arr);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtSelect.setText(arr[position]);
            }
        });

    }
}