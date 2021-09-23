package etn.app.danghoc.luuhinhanhtrenlocalhost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import etn.app.danghoc.luuhinhanhtrenlocalhost.adapter.AdapterImage;
import etn.app.danghoc.luuhinhanhtrenlocalhost.model.ItemImage;

public class ActivityShowImage extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterImage adapter;
    private List<ItemImage> list;
    private final String URL="http://192.168.1.5:8080/androidwebservice/getDataImage.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        getDataFromDatabase(URL);
        init();
    }

    private void init(){
        list=new ArrayList<>();
        list.add(new ItemImage("https://vuahatgiong.com/wp-content/uploads/2015/05/rau-bi-na.jpg"));
        list.add(new ItemImage("https://alaskavietnam.net/wp-content/uploads/2018/12/bao-quan-trai-cay-trong-tu-mat-the-nao-cho-dung.jpg"));
        adapter=new AdapterImage(list);
        recyclerView=findViewById(R.id.recycleviewImage);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

private void getDataFromDatabase(String url){
    JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            for (int i = 0; i <response.length() ; i++) {
                try {
                    JSONObject jsonObject=response.getJSONObject(i);
                    list.add(new ItemImage(jsonObject.getString("URL")));
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(ActivityShowImage.this, error+"", Toast.LENGTH_SHORT).show();
            Log.d("AAA",error+"");
        }
    });
    RequestQueue queue= Volley.newRequestQueue(this);
    queue.add(jsonArrayRequest);
}






}
