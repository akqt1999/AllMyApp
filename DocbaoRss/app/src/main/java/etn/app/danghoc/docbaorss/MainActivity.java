package etn.app.danghoc.docbaorss;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import etn.app.danghoc.docbaorss.Model.Item;

public class MainActivity extends AppCompatActivity {
    AdapterDocBao adapter;
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
        init();
        LoadTask task=new LoadTask();
        task.execute();
        Log.d("aaa",items.size()+"");
    }


    private void init() {
        items = new ArrayList<>();
        adapter = new AdapterDocBao(this, R.layout.item, items);
        lvDocBao = findViewById(R.id.lvDocBao);
        lvDocBao.setAdapter(adapter);
    }


    class LoadTask extends AsyncTask<Void, Void, ArrayList<Item>> {
        @Override
        protected ArrayList<Item> doInBackground(Void... voids)
        { try { // Tạo đối tượng Parser để chứa dữ liệu từ file XMLXmlPullParserFactory
            XmlPullParserFactory fc=XmlPullParserFactory.newInstance();
            fc=XmlPullParserFactory.newInstance();
            XmlPullParser parser= fc.newPullParser();
//Tạo mới và gọi đến phương thức getXmlFromUrl(URL)
            XMLParse myparser = new XMLParse();
// getting XML from URLString
            String xml=myparser.getXmlFromUrl(URL);
xml = myparser.getXmlFromUrl(URL);
//Copy dữ liệu từ String xml vào đối tượng parserparser.setInput(new StringReader(xml));
//Bắt đầu duyệt parserint
int eventType=-1;
            while(eventType!=XmlPullParser.END_DOCUMENT) { eventType=parser.next();
                switch(eventType){ case XmlPullParser.START_DOCUMENT: break; case XmlPullParser.END_DOCUMENT: break; case XmlPullParser.START_TAG: nodeName=parser.getName();if(nodeName.equals("title")){ title=parser.nextText();
                } else if(nodeName.equals("link")){ link = parser.nextText();
                } else if(nodeName.equals("description")){ description=parser.nextText();
                    try { urlStr = description.substring((description.indexOf("src=") + 5), (description.indexOf("8.jpg") + 5)); }
                    catch (Exception e1){ e1.printStackTrace();

                    } des= description.substring(description.indexOf("</br>")+5);
                    try { URL newurl = new URL(urlStr.toString()+"");
                        icon = BitmapFactory.decodeStream(newurl.openConnection().getInputStream()); }
                    catch (IOException e1) { e1.printStackTrace();}
                } break;
                    case XmlPullParser.END_TAG: nodeName=parser.getName();
                        if(nodeName.equals("item")){ items.add(new Item(icon, title, des,link)); } break; } } } catch (XmlPullParserException | IOException e) { e.printStackTrace(); }
            Log.d("aa4",items.size()+"");

            return items; }



        @Override
        protected void onPostExecute(ArrayList<Item> lists) {
            super.onPostExecute(lists);
            adapter.clear();
            adapter.addAll(lists);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            adapter.clear();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}