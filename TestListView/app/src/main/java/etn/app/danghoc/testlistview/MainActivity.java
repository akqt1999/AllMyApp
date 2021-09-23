package etn.app.danghoc.testlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
Button btnAdd;
EditText edtTest;
ListView lvTest;
ArrayList<String>arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        final ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arr);
        lvTest.setAdapter(adapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arr.add(0,edtTest.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void init() {
        arr =new ArrayList<>();
        btnAdd =findViewById(R.id.btn_add);
        edtTest=findViewById(R.id.edt_test);
        lvTest=findViewById(R.id.lv_test);

    }
}