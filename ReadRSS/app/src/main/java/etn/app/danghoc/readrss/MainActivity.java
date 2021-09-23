package etn.app.danghoc.readrss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {
    ListView mLvTitle;
    ArrayList<String> arrayListTitle, arrayLink;
    ArrayAdapter adapter;

    AdapterDocBao adapter1;
    ArrayList<Item> items;
    String nodeName;
    String title = "", link = "", description = "", des = "", urlStr = "",
            URL = "https://vnexpress.net/rss/thoi-su.rss";
    Bitmap icon = null;
    ListView lvDocBao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mLvTitle = (ListView) findViewById(R.id.listview_title);
//        arrayListTitle = new ArrayList();
//        arrayLink = new ArrayList();
//        adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, arrayListTitle);
//        mLvTitle.setAdapter(adapter);
//        mLvTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
//                intent.putExtra("link", arrayLink.get(position));
//                startActivity(intent);
//            }
//        });

        new ReadRSS().execute("https://vnexpress.net/rss/thoi-su.rss");
        items=new  ArrayList<>();
        adapter1 =new AdapterDocBao(this,R.layout.item,items);
        lvDocBao=findViewById(R.id.listview_title);
        lvDocBao.setAdapter(adapter1);

    }

    private class ReadRSS extends AsyncTask<String, Void, String> {
        StringBuilder content = new StringBuilder();

        @Override
        protected String doInBackground(String... strings) {
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
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            NodeList  nodeListDecription=document.getElementsByTagName("description");
          //  String title = "";
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                title = parser.getValue(element, "title");
          //      arrayListTitle.add(title);
                String cdata= nodeListDecription.item(i+1).getTextContent();
                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher matcher=p.matcher(cdata);
                if(matcher.find()){
                    urlStr=matcher.group(1);
                //    Toast.makeText(MainActivity.this, urlStr, Toast.LENGTH_SHORT).show();
                    Log.d("aaa",urlStr);

                }
                des= cdata.substring(cdata.indexOf("</br>")+5);
             //   Toast.makeText(MainActivity.this, des, Toast.LENGTH_SHORT).show();
                link=parser.getValue(element,"link");

//                try {
//                    urlStr = cdata.substring((cdata.indexOf("src=") + 5), (cdata.indexOf("8.jpg") + 5));
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//                try {
//                    URL newurl = new URL(urlStr.toString() + "");
//                    icon = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                items.add(new Item(urlStr,title,des,link));
                adapter1.notifyDataSetChanged();

            }

            }


// cai lon jv
    }
}
