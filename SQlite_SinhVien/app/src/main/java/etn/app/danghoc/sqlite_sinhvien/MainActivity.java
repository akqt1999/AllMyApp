package etn.app.danghoc.sqlite_sinhvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
SQLiteDatabase database=null;
Button btnInsert,btnUpdate,btnShow,btnDelete;
EditText edtMaSv,edtName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database=openOrCreateDatabase("sinhvien",MODE_PRIVATE,null);
        if(database!=null){
            createSinhVienTable();
        }
        init();
    }

    private void init() {
        edtMaSv=findViewById(R.id.edtMasv);
        edtName=findViewById(R.id.edtTen);

        btnDelete=findViewById(R.id.btnDelete);
        btnInsert=findViewById(R.id.btnAdd);
        btnUpdate=findViewById(R.id.btnUpDate);
        btnShow=findViewById(R.id.btnShow);

        btnShow.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnInsert.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

    }

    private void createSinhVienTable() {
        String sql="CREATE TABLE tbsv (" +
                "masv TEXT PRIMARY KEY , " +
                "ten TEXT)";
        if(database==null&&!database.isOpen()){
            database.execSQL(sql);
        }
    }
    private void insertTable(String masv,String ten){
        ContentValues values=new ContentValues();
        values.put("masv",masv);
        values.put("ten",ten);
        if(database.insert("tbsv",null,values)==-1){
            Toast.makeText(this, "add fail", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "add success", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateTable(String ma,String ten){
        ContentValues values=new ContentValues();
        values.put("ten",ten);
        if(database.update("tbsv",values,"masv=?",new String[]{ma})==0){
            Toast.makeText(this, "update fail", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "update sucess", Toast.LENGTH_SHORT).show();
        }
    }

    private void delete(String ma){
        if(database.delete("tbsv","masv=?",new String[]{ma})==0){
            Toast.makeText(this, "delete fail", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "delete success", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadTable(){
        Cursor c=database.query("tbsv",null,null,null,null,null,null);
        c.moveToFirst();
        String data="";
        while (!c.isAfterLast()){
         data+=c.getString(0)+"--"+c.getString(1);
         data+="\n";
         c.moveToNext();
        }
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        c.close();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAdd:
                insertTable(edtMaSv.getText().toString(),edtName.getText().toString());
                break;
            case R.id.btnDelete:
                delete(edtMaSv.getText().toString());
                break;
            case R.id.btnShow:
                loadTable();
                break;
            case R.id.btnUpDate:
                updateTable(edtMaSv.getText().toString(),edtName.getText().toString());
                break;

        }

    }
}