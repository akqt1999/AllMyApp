package etn.app.danghoc.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import etn.app.danghoc.sqlite.data.ManageData;
import etn.app.danghoc.sqlite.model.PhoneBook;

public class MainActivity extends AppCompatActivity {
     EditText editTextName,editTextNumberPhone;
     Button buttonSave,buttonSaveUpdate;
     ListView listViewPhoneBook;
     int id,positonn=-1;

   public static List<PhoneBook> phoneBookList;
    AdapterPhoneBook adapter;
    final ManageData  manageData=new ManageData(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
      //  phoneBookList=new ArrayList<>();
       // buildButtonSave();f

            phoneBookList=manageData.getData();




//
        setAdapter();
        eventButtonUpdate();
          buttonSaveUpdate.setVisibility(View.INVISIBLE);  
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneBook phoneBook = createPB();
                if (phoneBook != null) {
                    manageData.addData(phoneBook);
                }
                phoneBookList.add(phoneBook);
                setAdapter();
            }
        });



    }

    private void setAdapter(){
            if(adapter==null) {
                adapter = new AdapterPhoneBook(this, R.layout.pb_layout, phoneBookList);
            }
            else{
                adapter.notifyDataSetChanged();
            }
        listViewPhoneBook.setAdapter(adapter);
            listViewPhoneBook.setSelection(adapter.getCount()-1);
    }

private PhoneBook createPB(){
        String name=editTextName.getText().toString();
        String numberPhone=editTextNumberPhone.getText().toString();
        PhoneBook phone=new PhoneBook(name,numberPhone);
        return phone;
}


public  void eventButtonUpdate(){
         buttonSaveUpdate.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String name=editTextName.getText()+"";
                 String numberPhone=editTextNumberPhone.getText().toString();
                 PhoneBook phoneBook=new PhoneBook(id,name,numberPhone);
                int result= manageData.updateData(phoneBook);
                if(result>0){
                    phoneBookList.set(positonn,phoneBook);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "update success", Toast.LENGTH_SHORT).show();
                    buttonSaveUpdate.setVisibility(View.INVISIBLE);
                    buttonSave.setVisibility(View.VISIBLE);
                   editTextName.setText("");
                   editTextNumberPhone.setText("");
                }

             }
         });
}

public void eventButtonDelete(int id,int posotion){
        phoneBookList.remove(posotion);
        adapter.notifyDataSetChanged();
        manageData.deleteData(id);
    Toast.makeText(this, "delete success", Toast.LENGTH_SHORT).show();
}

public void eventButtonEdit(int position){
        positonn=position;
   PhoneBook phoneBook=phoneBookList.get(position);
   editTextName.setText(phoneBook.getmName());
   editTextNumberPhone.setText(phoneBook.getmNumberPhone());
   id=phoneBook.getmID();
}
public  void changeButton() {
        buttonSaveUpdate.setVisibility(View.VISIBLE);
        buttonSave.setVisibility(View.INVISIBLE);
}

public void anhXa(){
        editTextName=(EditText) findViewById(R.id.editText_name);
        editTextNumberPhone=(EditText) findViewById(R.id.editText_number_phone);
        listViewPhoneBook=(ListView) findViewById(R.id.listview_pb);
        buttonSave=(Button) findViewById(R.id.button_save);
        buttonSaveUpdate=(Button) findViewById(R.id.button_save_update);
}

}
