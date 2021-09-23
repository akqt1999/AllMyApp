package etn.app.danghoc.jsonobject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
   Button btVN,btEN;
   TextView tvJson;
   String noiDung="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        btEN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readObjectLanguage("en");
            }
        });

        btVN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readObjectLanguage("vn");

            }
        });
        new ReadJSONObject().execute("https://khoapham.vn/KhoaPhamTraining/json/tien/demo3.json");
    }


    private void anhXa(){
        btEN =(Button)findViewById(R.id.button_en);
        btVN =(Button)findViewById(R.id.button_vn);
        tvJson=(TextView)findViewById(R.id.textview_json);

    }
    private class ReadJSONObject extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
          noiDung=s;
            readObjectLanguage("vn");
        }



    }
    private void readObjectLanguage(String language){
        try {
            JSONObject object=new JSONObject(noiDung);
              /*  JSONArray array=object.getJSONArray("language"); // cai nay la get array
                for (int i = 0; i <array.length() ; i++) {
                    JSONObject objectKH=array.getJSONObject(i);
                    String name=objectKH.getString("khoahoc");

               */
            //

            JSONObject objectLanguage=object.getJSONObject("language");
            JSONObject objectEN=objectLanguage.getJSONObject(language);
            String address=objectEN.getString("address");
            tvJson.setText(address);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




}
