package etn.app.danghoc.custom_listview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import etn.app.danghoc.custom_listview.adapter.CustomAdapter;
import etn.app.danghoc.custom_listview.model.Contact;

public class MainActivity extends AppCompatActivity {
  ListView listViewSdt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewSdt1=(ListView) findViewById(R.id.listview_sdt);


        final ArrayList<Contact>arrayList=new ArrayList<>();

        Contact contact = new  Contact(Color.BLUE,"cai lin bạnh","0122332423");
        Contact contact1=new Contact(Color.YELLOW,"tran van tks","0145454333");
        Contact contact2=new Contact(Color.GREEN,"le yig skd","0393939393");
        Contact contact3=new Contact(Color.CYAN,"vai lon mdi","0432323322");
        Contact contact4=new Contact(Color.BLUE,"chan thoa qia","0393939393");
        Contact contact5=new Contact(Color.BLACK,"khong ngo mo","0422223233");
        Contact contact6=new Contact(Color.CYAN,"sao lam vrra","0304049494");


        arrayList.add(contact);
        arrayList.add(contact1);
        arrayList.add(contact2);
        arrayList.add(contact3);
        arrayList.add(contact4);
        arrayList.add(contact5);
        arrayList.add(contact6);

        final CustomAdapter customAdapter =new CustomAdapter(this,R.layout.row_item_contact,arrayList);
        listViewSdt1.setAdapter(customAdapter);
      //

        listViewSdt1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                arrayList.remove(position);
                customAdapter.notifyDataSetChanged();
                return false;
            }
        });

    }
}
