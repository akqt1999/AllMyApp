package etn.app.danghoc.doancuoiky;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static String DATABASE_NAME="arirang.sqlite";
    public static String DB_PATH_SUFFIX="/databases/";
    public static SQLiteDatabase database=null;
    ListView lvFav,lvList,lvSearch;
    ArrayList<Item>listFav,listList,listSearch;
    TabHost tabHost;
    ImageButton btnDeleteSearch;
    AdapterKarake adapterSearch,adapterList,adapterFav;
    EditText edtSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        processCopy();
        database=openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        addControl();
        addEvent();
        eventSearch();

    }

    private void eventSearch() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1=s.toString();
                getData();
            }
               private void getData(){
                String dulieunhap=edtSearch.getText().toString();
                listSearch.clear();
                if(!dulieunhap.equalsIgnoreCase("")){
                    Cursor c = database.rawQuery("SELECT * FROM ArirangSongList WHERE TENBH1 LIKE '"+"%"+dulieunhap+
                            "%"+"' OR MABH LIKE '"+"%"+dulieunhap+"%"+"'", null); c.moveToFirst();
                        c.moveToFirst();
                        while (!c.isAfterLast()){
                            listSearch.add(new Item(c.getString(1),c.getString(2),c.getInt(6)));
                            c.moveToNext();
                        }
                        c.close();
                }
                adapterSearch.notifyDataSetChanged();
               }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void addEvent() {
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabId.equalsIgnoreCase("tablist")){
                    addList();
                }
                if(tabId.equalsIgnoreCase("tabfav")){
                    addFav();
                }
            }
        });
        btnDeleteSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSearch.setText("");
            }
        });

    }

    private void addFav() {
        listFav.clear();
        Cursor c = database.rawQuery("SELECT * FROM ArirangSongList  WHERE YEUTHICH = 1", null);
        c.moveToFirst();
        while (c.isAfterLast()==false){
                listFav.add(new Item(c.getString(1),c.getString(2),c.getInt(6)));
                c.moveToNext();
        }
        c.close();
        adapterFav.notifyDataSetChanged();
    }

    private void addList() {
        listList.clear();
        Cursor c = database.rawQuery("SELECT * FROM ArirangSongList", null);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            listList.add(new Item(c.getString(1),c.getString(2),c.getInt(6)));
            c.moveToNext();
        }
        c.close();
        adapterList.notifyDataSetChanged();
    }

    private void addControl() {
        tabHost.setup();
        TabHost.TabSpec tabSearch=tabHost.newTabSpec("tabsearch");
        TabHost.TabSpec tabList=tabHost.newTabSpec("tablist");
        TabHost.TabSpec tabFav=tabHost.newTabSpec("tabfav");



        tabSearch.setContent(R.id.tabSearch);
        tabFav.setContent(R.id.tabFavorite);
        tabList.setContent(R.id.tabList);

        tabFav.setIndicator("",getResources().getDrawable(R.drawable.favourite));
        tabSearch.setIndicator("",getResources().getDrawable(R.drawable.search));
        tabList.setIndicator("",getResources().getDrawable(R.drawable.list));

        tabHost.addTab(tabSearch);
        tabHost.addTab(tabFav);
        tabHost.addTab(tabList);

        adapterSearch=new AdapterKarake(this,R.layout.item,listSearch);
        adapterFav=new AdapterKarake(this,R.layout.item,listFav);
        adapterList=new AdapterKarake(this,R.layout.item,listList);

        lvSearch.setAdapter(adapterSearch);
        lvFav.setAdapter(adapterFav);
        lvList.setAdapter(adapterList);
    }

    private void init() {
        lvFav=findViewById(R.id.lvFav);
        lvList=findViewById(R.id.lvList);
        lvSearch=findViewById(R.id.lvSearch);
        listFav=new ArrayList<>();
        listList=new ArrayList<>();
        listSearch=new ArrayList<>();
        tabHost=findViewById(R.id.tabHost);
        btnDeleteSearch=findViewById(R.id.btnDelete);
        edtSearch=findViewById(R.id.edtTim);

    }

    private void processCopy() {
        //private app
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists())
        {
            try{CopyDataBaseFromAsset();
                Toast.makeText(this, "Copying sucess from Assets folder", Toast.LENGTH_LONG).show();
            }
            catch (Exception e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;
    }
    public void CopyDataBaseFromAsset() {
        // TODO Auto-generated method stub
        try {
            InputStream myInput;
            myInput = getAssets().open(DATABASE_NAME);
            // Path to the just created empty db
            String outFileName = getDatabasePath();
            //if the path doesn't exist first, create it
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();
            //Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);
            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0)
            {
                myOutput.write(buffer, 0, length);
            }
            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}