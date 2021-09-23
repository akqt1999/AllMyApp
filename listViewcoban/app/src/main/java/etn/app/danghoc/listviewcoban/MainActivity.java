package etn.app.danghoc.listviewcoban;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
 ListView listViewHdh ;
 EditText editTextName;
 Button buttonAdd,buttonUpdate;
     ArrayAdapter adapter;
     ArrayList<String> arrayListHdh;
 int vitri=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      // anh xa
        listViewHdh=(ListView) findViewById(R.id.listViewhdh);
       editTextName=(EditText) findViewById(R.id.editText);
       buttonAdd=(Button) findViewById(R.id.buttonAdd);
       buttonUpdate=(Button) findViewById(R.id.buttonUpdate);



       arrayListHdh=new ArrayList<>();
addArayList();

       adapter = new ArrayAdapter(
               MainActivity.this,android.R.layout.simple_list_item_1,arrayListHdh);
       listViewHdh.setAdapter(adapter);

       listViewHdh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               editTextName.setText(arrayListHdh.get(position));
               vitri=position;
           }
       });

       // ======= xóa
        listViewHdh.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
              vitri=position;
              thongbaoXoas();
                return false;
            }
        });

       //  ======== add
    buttonAdd.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            arrayListHdh.add(editTextName.getText().toString());
            thongBaoAlert();
        }
    });

    //============ update
     buttonUpdate.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String text="";
                    text= editTextName.getText().toString().trim();

             if(text.equals("")) {
                 Toast.makeText(MainActivity.this, "chua nhap noi dung", Toast.LENGTH_SHORT).show();

             }
             else {
                 arrayListHdh.set(vitri, editTextName.getText().toString());
                 thongBaoAlert();
             }
         }
     });


    }

    // = =----------adđ arraylisst
private void addArayList(){

    arrayListHdh.add("android");
    arrayListHdh.add("windown");
    arrayListHdh.add("ios");
    arrayListHdh.add("Lixnus");
    arrayListHdh.add("Lixnus");
}

       void thongBaoAlert(){
            AlertDialog.Builder builder=new AlertDialog.Builder  (this);
            builder.setTitle("NOte");
            builder.setMessage("dơ you want to change");
            builder.setNegativeButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.notifyDataSetChanged();
                }
            });
            builder.setPositiveButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();

        }

        private void thongbaoXoas(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("note");
        builder.setMessage("dơ you want to delete");
        builder.setPositiveButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
            }
        });
        builder.setNegativeButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                arrayListHdh.remove(vitri);
                 adapter.notifyDataSetChanged();
            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();

        }
}
