package etn.app.danghoc.note_appp1.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import etn.app.danghoc.note_appp1.model.ItemTitle;

public class DatabaseNote extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "data_note";
    public static final String TABLE_NAME = "data_note_table";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String TODAY = "today";
    public static final String TEXT_CONTENT = "text_content";
    public static final String CHECK_FAVORITE = "check_favorite";
    public static final int version = 1;
    private Context context;
    String sqlQuery = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " integer primary key, " +
            TITLE + " TEXT, " +
            TODAY + " TEXT, " +
            TEXT_CONTENT + " TEXT, " +
            CHECK_FAVORITE + " integer)";


    public DatabaseNote(@Nullable Context context) {
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

    // add
    public void addData(ItemTitle title) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, title.getmTitle());
        contentValues.put(TODAY, title.getmToday());
        contentValues.put(TEXT_CONTENT, title.getmContent());
        contentValues.put(CHECK_FAVORITE, title.getCheckFav());

        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    // getData
    public List<ItemTitle> getData() {
        List<ItemTitle> itemTitleList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do { // id tit today text
                ItemTitle title = new ItemTitle();
                title.setmId(cursor.getInt(0));
                title.setmTitle(cursor.getString(1));
                title.setmToday(cursor.getString(2));
                title.setmContent(cursor.getString(3));
                title.setmCheckFav(cursor.getInt(4));
                itemTitleList.add(title);
            } while (cursor.moveToNext());

        } //
        db.close();
        return itemTitleList;
    }

    // get content data lưu sai chứ k phải đọc sai


    // update content
    public int updateContent(int id, String contentText) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEXT_CONTENT, contentText);
        int number = db.update(TABLE_NAME, contentValues, ID + " =?", new String[]{id + ""});
        db.close();
        return number;
    }

    public int updateCheckFav(int id, int mCheckFav) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CHECK_FAVORITE, mCheckFav);
        int number = db.update(TABLE_NAME, contentValues, ID + "=?", new String[]{id + ""});
        return number;
    }

    public int deleteItemData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int number = db.delete(TABLE_NAME, ID + " =?", new String[]{id + ""});
        db.close();
        return number;
    }

}











