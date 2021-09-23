package etn.app.danghoc.sqlite.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import etn.app.danghoc.sqlite.model.PhoneBook;

public class ManageData extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="manager_phone_book";
    private static final String TABLE_NAME="phone_book";
    private static final String ID="id";
    private static final String NAME="name";
    private static final String NUMBER_PHONE="number_phone";
    private static final int VERSION=1;
    private static final String TAG ="databaseeee" ;
    private Context context;

    String sqlQuery="CREATE TABLE " +TABLE_NAME+ " ("+
            ID +" integer primary key, "+
            NAME +" TEXT, "+
            NUMBER_PHONE+" TEXT)";

    public ManageData(@Nullable Context context) {
        super(context, DATABASE_NAME, null , VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addData(PhoneBook phoneBook){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(NAME,phoneBook.getmName());
        values.put(NUMBER_PHONE,phoneBook.getmNumberPhone());
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
// như một trò đùa
    public List<PhoneBook> getData(){
        List<PhoneBook> bookList =new ArrayList<>();
        String selectQuery="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                PhoneBook phoneBook=new PhoneBook();
                phoneBook.setmID(cursor.getInt(0));
                phoneBook.setmName(cursor.getString(1).toString());
                phoneBook.setmNumberPhone(cursor.getString(2));
                bookList.add(phoneBook);
            }while (cursor.moveToNext());
        }
        db.close();
        return bookList;
    }


    // cái đaii\
    public int updateData(PhoneBook phoneBook){
         SQLiteDatabase db=this.getWritableDatabase();
         ContentValues contentValues=new ContentValues();
         contentValues.put(NAME,phoneBook.getmName());
         contentValues.put(NUMBER_PHONE,phoneBook.getmNumberPhone());
         int number= db.update(TABLE_NAME,contentValues,ID+"=?",new String []{phoneBook.getmID()+""});
          db.close();
          return number;
    }

    public int deleteData(int id){
        SQLiteDatabase db =this.getWritableDatabase();
       return db.delete(TABLE_NAME,ID+"=?",new String[]{id+""});

    }




}
