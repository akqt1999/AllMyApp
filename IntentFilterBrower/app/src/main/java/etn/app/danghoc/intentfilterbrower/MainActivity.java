package etn.app.danghoc.intentfilterbrower;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        webView=findViewById(R.id.myweb);
        Intent intent=getIntent();
        Uri data=intent.getData();
        URL url=null;
        try {
            url=new URL(data.getScheme(),data.getHost(),data.getPath());
            webView.loadUrl(url.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}