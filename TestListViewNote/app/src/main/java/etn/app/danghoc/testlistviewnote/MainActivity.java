package etn.app.danghoc.testlistviewnote;

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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtWork,edtHour,edtMinute;
    Button btnAddWork;
    ListView lvWork;
    ArrayList listWork;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        final ArrayAdapter<String>adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listWork);
        lvWork.setAdapter(adapter);
        btnAddWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtHour.getText().toString().equals("")||edtMinute.getText().toString().equals("")||edtWork.getText().toString().equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Info missing");
                    builder.setMessage("Please enter all information of the work");
                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }else{
                    String time=edtWork.getText().toString()+" - "+edtHour.getText().toString()+":"+edtMinute.getText().toString();
                    listWork.add(0,time);
                    adapter.notifyDataSetChanged();
                    edtMinute.setText("");
                    edtHour.setText("");
                    edtWork.setText("");
                }
            }
        });
    lvWork.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            listWork.remove(position);
            adapter.notifyDataSetChanged();
        }
    });
    }

    private void init() {
        edtHour=findViewById(R.id.edtHour);
        edtWork=findViewById(R.id.edtWork);
        edtMinute=findViewById(R.id.edtMinite);

        btnAddWork=findViewById(R.id.btnAddWork);
        lvWork=findViewById(R.id.lvWork);

        listWork=new ArrayList();
    }
}