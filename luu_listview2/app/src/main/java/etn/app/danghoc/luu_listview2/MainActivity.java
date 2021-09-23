package etn.app.danghoc.luu_listview2;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import etn.app.danghoc.luu_listview2.adapter.ExampleAdapter;
import etn.app.danghoc.luu_listview2.mode.ExampleItem;
import etn.app.danghoc.luu_listview2.R;
import etn.app.danghoc.luu_listview2.adapter.ExampleAdapter;

public class MainActivity extends AppCompatActivity {
    ListView listViewExample;
    Button buttonAdd,buttonSave;
    EditText editTextTitle,editTextContent;
    ExampleItem item;
    ExampleAdapter adapter;
    ArrayList<ExampleItem> itemArrayList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        loadData();
        delete();
        adapter=new ExampleAdapter(this,R.layout.activity_main,itemArrayList);
        listViewExample.setAdapter(adapter);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

    }

public void delete(){
        listViewExample.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDialogg(position);
                return false;
            }
        });
}

    public void showDialogg(final int positon){
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.show();

        Button buttonNo=(Button) dialog.findViewById(R.id.button_no);
        Button buttonYes=(Button) dialog.findViewById(R.id.button_yes);

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemArrayList.remove(positon);
                 adapter.notifyDataSetChanged();
                 saveData();
                 dialog.dismiss();
            }
        });
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    public void loadData(){
        SharedPreferences sharedPreferences=getSharedPreferences("save_data",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("savee",null);
        Type type=new TypeToken<ArrayList<ExampleItem>>() {}.getType();
        itemArrayList=gson.fromJson(json,type);
        if(itemArrayList==null){
            itemArrayList=new ArrayList<>();
        }
    }

    public  void saveData(){
        SharedPreferences sharedPreferences =getSharedPreferences("save_data",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=gson.toJson(itemArrayList);
        editor.putString("savee",json);
        editor.apply();
    }


    public void addData(){
        String title=editTextTitle.getText().toString();
        String content=editTextContent.getText().toString();
        item=new ExampleItem(title,content);
        itemArrayList.add(item);
        adapter.notifyDataSetChanged();
    }

    public void anhXa(){
        listViewExample=(ListView) findViewById(R.id.listView_example);

        buttonAdd=(Button) findViewById(R.id.button_add);
        buttonSave=(Button) findViewById(R.id.button_save);

        editTextContent=(EditText) findViewById(R.id.editText_content);
        editTextTitle=(EditText) findViewById(R.id.editText_title);
    }
}
