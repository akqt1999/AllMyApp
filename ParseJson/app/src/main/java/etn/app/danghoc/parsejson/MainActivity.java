package etn.app.danghoc.parsejson;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvTigia;
    ArrayList<TyGia> dstygia;
    AdapterTiGia myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        TyGiaTask tyGiaTask =new TyGiaTask();
        tyGiaTask.execute();

    }

    private void init() {
        lvTigia=findViewById(R.id.lv);
        dstygia=new ArrayList<>();
        myadapter=new AdapterTiGia(this,R.layout.item,dstygia);
        lvTigia.setAdapter(myadapter);
    }

    class TyGiaTask extends AsyncTask<Void,Void,ArrayList<TyGia>>{

        @Override
        protected ArrayList<TyGia> doInBackground(Void... voids) {
            ArrayList<TyGia> ds=new ArrayList<>();
            try {
                URL url=new URL("http://dongabank.com.vn/exchange/export");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-type", " application/json; charset=utf-8");
                connection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
                connection.setRequestProperty("Accept", "*/*");
                InputStream is= connection.getInputStream();
                InputStreamReader isr=new InputStreamReader(is,"UTF-8");
                BufferedReader br=new BufferedReader(isr);
                String line=br.readLine();
                StringBuilder builder=new StringBuilder();
                while (line!=null){
                    builder.append(line);
                    line=br.readLine();}
                String json=builder.toString();
                json=json.replace("(", "");
                json=json.replace(")","");
                JSONObject jsonObject=new JSONObject(json);
                JSONArray jsonArray= jsonObject.getJSONArray("items");
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject item=jsonArray.getJSONObject(i);
                    TyGia tiGia=new TyGia();
                    tiGia.setType(item.getString("type"));
                    if(item.has("imageurl")){
                        tiGia.setImageurl(item.getString("imageurl"));
                        url=new URL(tiGia.getImageurl());
                        connection= (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
                        connection.setRequestProperty("Accept", "*/*");
                        Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                        tiGia.setBitmap(bitmap);
                    }
                    if(item.has("muatienmat")) {
                        tiGia.setMuatienmat(item.getString("muatienmat")); }
                    if(item.has("muack")) {
                        tiGia.setMuack(item.getString("muack")); }
                    if(item.has("bantienmat")) {
                        tiGia.setBantuenmat(item.getString("bantienmat")); }
                    if(item.has("banck")) {
                        tiGia.setBanck(item.getString("banck")); }
                    dstygia.add(tiGia);
                    Toast.makeText(MainActivity.this, dstygia.size()+"", Toast.LENGTH_SHORT).show();
                    myadapter.notifyDataSetChanged();

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return ds;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            myadapter.clear();
        }
        @Override
        protected void onPostExecute(ArrayList<TyGia> result){
            super.onPostExecute(result);myadapter.clear();myadapter.addAll(result);

        }
        @Override
        protected void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }
    }


}