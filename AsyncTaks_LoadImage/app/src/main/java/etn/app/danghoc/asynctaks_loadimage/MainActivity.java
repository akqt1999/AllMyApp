package etn.app.danghoc.asynctaks_loadimage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
   private Button btLoad;
   private ImageView imvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btLoad=(Button)findViewById(R.id.button_load);
        imvTest=(ImageView) findViewById(R.id.image_view_test);
        btLoad.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
               new loadImageInternet().execute("https://znews-photo.zadn.vn/w1024/Uploaded/kbd_bcvi/2019_11_23/5d828d976f24eb1a752053b5.jpg");
            }
        });

    }

    private class loadImageInternet extends AsyncTask<String,Void, Bitmap>{
           Bitmap bitmapImage=null;
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url=new URL(strings[0]);
                InputStream inputStream=url.openConnection().getInputStream();
                bitmapImage= BitmapFactory.decodeStream(inputStream);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmapImage;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imvTest.setImageBitmap(bitmap);
        }
    }
}
