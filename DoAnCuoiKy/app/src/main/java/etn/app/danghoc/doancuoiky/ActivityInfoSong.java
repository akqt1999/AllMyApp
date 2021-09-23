package etn.app.danghoc.doancuoiky;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityInfoSong extends AppCompatActivity {
    TextView txtNumber,txtTitle,txtLyrics,txtAuthor;
    ImageButton btnLike,btnUnlike;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_song);
        init();
        Intent callIntent=getIntent();
        Bundle getBundle=callIntent.getBundleExtra("data");
        final String maso=getBundle.getString("maso");
        Cursor c = MainActivity.database.rawQuery("SELECT * FROM ArirangSongList  WHERE MABH LIKE'"+maso+"'", null);
        txtNumber.setText(maso);
        c.moveToFirst();
        txtTitle.setText(c.getString(2));
        txtLyrics.setText(c.getString(3));
        txtAuthor.setText(c.getString(4));
        if(c.getInt(6)==0){
            btnUnlike.setVisibility(View.VISIBLE);
            btnLike.setVisibility(View.INVISIBLE);
        }else{
            btnUnlike.setVisibility(View.INVISIBLE);
            btnLike.setVisibility(View.VISIBLE);
        }
        c.close();
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values=new ContentValues();
                values.put("YEUTHICH",0);
                MainActivity.database.update("ArirangSongList",values,
                        "MABH=?",new String[]{maso});
                btnUnlike.setVisibility(View.VISIBLE);
                btnLike.setVisibility(View.INVISIBLE);
            }
        });
        btnUnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values=new ContentValues();
                values.put("YEUTHICH",1);
                MainActivity.database.update("ArirangSongList",values,
                        "MABH=?",new String[]{maso});
                btnUnlike.setVisibility(View.INVISIBLE);
                btnLike.setVisibility(View.VISIBLE);
            }
        });
    }

    private void init() {
        txtAuthor=findViewById(R.id.txtAuthor);
        txtTitle=findViewById(R.id.txtTitle);
        txtNumber=findViewById(R.id.txtNumber);
        txtLyrics=findViewById(R.id.txtLyrics);

        btnLike=findViewById(R.id.btnLike);
        btnUnlike=findViewById(R.id.btnUnLike);
    }
}