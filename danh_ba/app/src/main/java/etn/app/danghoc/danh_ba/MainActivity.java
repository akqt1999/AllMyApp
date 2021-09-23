package etn.app.danghoc.danh_ba;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import etn.app.danghoc.danh_ba.adapter.AdapterPeople;
import etn.app.danghoc.danh_ba.model.info_people;

public class MainActivity<intent> extends AppCompatActivity {
    EditText editTextSetName,editTextSetNumberPhone;
           TextView editTextSex;
    Button buttonAdd ,buttonCall,buttonSendMessa ;
    RadioGroup radioGroupSex;
    RadioButton radioButtonBoy,radioButtonGirl;
    String namee,numberPhone;
    boolean sex;
    ListView listViewDanhBa;
    ArrayList<info_people> arrayList;
    Dialog dialog;


    AdapterPeople adapterPeople;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        // xin cấp quyền
        checkAndRequestPermission();
        ///
        arrayList=new ArrayList<>();

         //addCOntact();
        adapterPeople= new AdapterPeople(this,R.layout.ten_sdt,arrayList);
        listViewDanhBa.setAdapter(adapterPeople);

        ///

        listViewDanhBa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialogCallOrSendMessages(position);
            }
        });

    }

// chán vãi viết code vui hơn
    public void anhXa(){
        listViewDanhBa=(ListView) findViewById(R.id.listview_danh_ba);
        editTextSetName=(EditText) findViewById(R.id.editText_set_name);
        editTextSetNumberPhone=(EditText) findViewById(R.id.editText_set_number_phone);
        editTextSex=(TextView) findViewById(R.id.textview_sex);
        buttonAdd =(Button) findViewById(R.id.button_add);
        radioGroupSex=(RadioGroup) findViewById(R.id.radio_group_sex);
        radioButtonBoy=(RadioButton) findViewById(R.id.radioButton_boy);
        radioButtonGirl=(RadioButton) findViewById(R.id.radioButton_girl);
    }

    /// them du lieu vao danh ba tháng làm d
    private void addDanhBa(){
//        namee=editTextSetName.getText().toString().trim();
//        numberPhone=editTextSetNumberPhone.getText().toString().trim();
//       if(radioButtonGirl.isChecked()){
//           sex=false;
//       }
//       if(radioButtonBoy.isChecked()){
//           sex=true;
//       }
//       info_people info_people1=new info_people(namee,numberPhone,sex);
//       arrayList.add(info_people1);

    }

    private void showDialogCallMess(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);

          builder.setMessage("do you want to call or send message");

        builder.setNegativeButton("call", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "call", Toast.LENGTH_SHORT).show();
                   dialog.dismiss();
            }
        });

        builder.setPositiveButton(" send message", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "message", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
     AlertDialog alertDialog=builder.create();
   alertDialog.show();
    }

    public void showDialogCallOrSendMessages(final int posotion){
        dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog_call_sendmessges);
        dialog.show();
        buttonCall=(Button)dialog.findViewById(R.id.button_call);
        buttonSendMessa=(Button)dialog.findViewById(R.id.button_sendmessages);

        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentCall(posotion);
                dialog.dismiss();
            }
        });

        buttonSendMessa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentSendMessages(posotion);
                dialog.dismiss();
            }
        });
    }
public void addCOntact(View view){
        if(view.getId()==R.id.button_add){
             namee=editTextSetName.getText().toString().trim();
            numberPhone=editTextSetNumberPhone.getText().toString().trim();
            if(radioButtonBoy.isChecked()){ // boy= true ; girl=false
                sex=true;
            }
            if(radioButtonGirl.isChecked()){// girl = false ; boy=true
                    sex=false;
            }

            if(namee.equals("")||numberPhone.equals("")){
                Toast.makeText(this, "please input name or numberphone", Toast.LENGTH_SHORT).show();
            }
            else{
                      info_people info_people = new info_people(namee,numberPhone,sex);
                      arrayList.add(info_people);
            }
            adapterPeople.notifyDataSetChanged();
        }
}
//////////////https://youtu.be/oignv_mCaeo?t=2348
//// yêu cầu cáp quyền
private void checkAndRequestPermission() {
    String[] permission = new String[]{
            Manifest.permission.CALL_PHONE,
            Manifest.permission.SEND_SMS
    };
    List <String> ListPermissionNeed=new ArrayList<>();
    for(String permissioned :permission){
        if(ContextCompat.checkSelfPermission(this,permissioned)!= PackageManager.PERMISSION_GRANTED){
            ListPermissionNeed.add(permissioned);
        }
    }
    if(!ListPermissionNeed.isEmpty()){
        ActivityCompat.requestPermissions(this,ListPermissionNeed.toArray(new String [ListPermissionNeed.size()]),1);
    }
}


    //gọi điện
    public void intentCall(int position){
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+arrayList.get(position).getNumberPhone()));
        startActivity(intent);
    }

    private void intentSendMessages(int position){
        Intent intent =new Intent(Intent.ACTION_VIEW,Uri.parse("tel:"+arrayList.get(position).getNumberPhone()));
      //  intent.setAction(Intent.ACTION_SEND,Uri.parse("tel:"+arrayList.get(position).getNumberPhone()));
      //  intent.setData(Uri.parse("tel:"+arrayList.get(position).getNumberPhone()));
        startActivity(intent);
    }
}
