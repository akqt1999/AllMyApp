package etn.app.danghoc.luuhinhanh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabseImage extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database_image";
    private static final String TABLE_NAME = "image";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String IMAGE = "image";
    private static final int version = 1;
    private Context context;
    String sqlQuery = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " integer primary key, " +
            TITLE + " TEXT, " +
            IMAGE + " BLOB )";


    public DatabseImage(@Nullable Context context) {
        super(context, DATABASE_NAME, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addData(ItemImage itemImage){
            SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(TITLE,itemImage.getTitle());
        values.put(IMAGE,itemImage.getImage());
        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public List<ItemImage>getData(){
        List<ItemImage> imageList=new ArrayList<>();
        String selectQuery="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
            ItemImage itemImage=new ItemImage();
            itemImage.setId(cursor.getInt(0));
            itemImage.setTitle(cursor.getString(1));
            itemImage.setImage(cursor.getBlob(2));
            imageList.add(itemImage);
            }while (cursor.moveToNext());
        }
       db.close();
        return imageList;
    }


}
