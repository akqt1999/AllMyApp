package etn.app.danghoc.com;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String name = "xuan tri";
        Log.d("hoten", name);
        ArrayList<String >Arrayname=new ArrayList<>();

      ThongBao("con ket");
      ThongBao("hello cc");
     }
     private void ThongBao(String name){
        Log.d("hoten","hello world "+name);
     }
    }
