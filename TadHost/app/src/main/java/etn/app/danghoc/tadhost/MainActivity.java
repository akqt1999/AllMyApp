package etn.app.danghoc.tadhost;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
Button btnCong,btnTru;
TabHost tabHost;
ListView lvHis;
EditText edtA,edtB;
List<String>list;
TextView txtKq;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCong=findViewById(R.id.btnCong);
        btnTru=findViewById(R.id.btnTru);
        tabHost=findViewById(R.id.tabHost);
        lvHis=findViewById(R.id.lvHis);
        list=new ArrayList<>();
        edtA=findViewById(R.id.edtA);
        edtB=findViewById(R.id.edtB);
        txtKq=findViewById(R.id.txtKq);
        addControl();
        btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float a=0,b=0;
                a= Float.parseFloat(edtA.getText().toString());
                b= Float.parseFloat(edtB.getText().toString());
                String kq="";
                kq=a+" + "+b+" = "+(a+b);
                list.add(kq);
                adapter.notifyDataSetChanged();
                txtKq.setText(a+b+"");
            }
        });

        btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float a=0,b=0;
                a= Float.parseFloat(edtA.getText().toString());
                b= Float.parseFloat(edtB.getText().toString());
                String kq="";
                kq=a+" - "+b+" = "+(a-b);
                list.add(kq);
                adapter.notifyDataSetChanged();
                txtKq.setText(a-b+"");

            }
        });

    }

    private void addControl() {
        tabHost.setup();
        TabHost.TabSpec tab1=tabHost.newTabSpec("t1");
        TabHost.TabSpec tab2=tabHost.newTabSpec("t2");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("Calculator");
        tabHost.addTab(tab1);
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("History");
        tabHost.addTab(tab2);
         adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        lvHis.setAdapter(adapter);


    }
}