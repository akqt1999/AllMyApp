package etn.app.danghoc.adddataforeatit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    public static final String POPULAR_CATEGORY_REF = "MostPopular";
    public static final String BEST_DEALS_REF ="BestDeal" ;
    private FirebaseDatabase database;
    private DatabaseReference popularRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database=FirebaseDatabase.getInstance();
        popularRef=database.getReference(BEST_DEALS_REF);

        //string menuId, String foodId, String name, String image)
        ModelPopular deal1=new ModelPopular("menu1","food1","chicken","http://denhatfoods.com/thucpham_thihang_t5/upload/images/cung-cap-thit-ga-chat-luong-tai-quan-7.png");
        ModelPopular deal2=new ModelPopular("menu2","food2","beef"
                ,"https://product.hstatic.net/1000191320/product/thit-bo-bap2_master.jpg");
        ModelPopular deal3=new ModelPopular("menu3","food3","beef2"
                ,"https://product.hstatic.net/1000191320/product/thit-bo-bap2_master.jpg");

    popularRef.child("deal_1").setValue(deal1);
    popularRef.child("deal_2").setValue(deal2);
    popularRef.child("deal_3").setValue(deal3);
    }

}