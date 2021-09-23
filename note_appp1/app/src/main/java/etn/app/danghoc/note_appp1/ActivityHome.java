package etn.app.danghoc.note_appp1;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import etn.app.danghoc.note_appp1.data.DatabaseNote;
import etn.app.danghoc.note_appp1.fagment.FragmentFavorite;
import etn.app.danghoc.note_appp1.fagment.FragmentHome;
import etn.app.danghoc.note_appp1.model.ItemFavorite;
import etn.app.danghoc.note_appp1.model.ItemTitle;

public class ActivityHome extends AppCompatActivity implements EventSqLite, BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView nav;
    public static List<ItemFavorite> listAddFavorite;
    public static List<ItemTitle>list;
    DatabaseNote data=new DatabaseNote(this);
    FragmentHome fragmentHome=new FragmentHome();
    FragmentFavorite fragmentFavorite=new FragmentFavorite();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contain_fragment);
        list=new ArrayList<>();
        listAddFavorite=new ArrayList<>();
        if(data.getData()!=null){
            list.addAll(data.getData());
            loadFavorite();
        }
        nav=findViewById(R.id.nav_note);
        nav.setItemIconTintList(null);

        nav.setOnNavigationItemSelectedListener(this);

        fragmentHome.setListen(this);
        fragmentFavorite.setListen(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.contain_fragment,fragmentHome).commit();


    }
//    BottomNavigationView.OnNavigationItemSelectedListener navListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//            switch (menuItem.getItemId()){
//                case R.id.nav_home :
//                        getSupportFragmentManager().beginTransaction().replace(R.id.contain_fragment,fragmentHome).commit();
//                    break;
//                case R.id.nav_favorite:
//                    getSupportFragmentManager().beginTransaction().replace(R.id.contain_fragment,fragmentFavorite).commit();
//
//            }
//            return false;
//        }
//
//
//    };



    private void loadFavorite() { // load list favourite
        for (int i = 0; i < list.size(); i++) {
            ItemTitle itemTitle = list.get(i);
            if (itemTitle.getCheckFav() == 1) {
                ItemFavorite itemFavorite = new ItemFavorite(itemTitle.getmTitle(),itemTitle.getId());
                listAddFavorite.add(itemFavorite);
            }
        }
    }

    @Override
    public void deleteItem(int posotion) {
        data.deleteItemData(posotion);
    }

    @Override
    public void addItem(ItemTitle itemTitle) {
            data.addData(itemTitle);
            list.clear();
            list.addAll(data.getData());
    }

    @Override
    public void addFavorite(int id, int checkFavorite) {
        data.updateCheckFav(id,checkFavorite);
        list.clear();
        list.addAll(data.getData());
    }

    @Override
    public void deleteFavorite(int id, int checkFavorite) {
        data.updateCheckFav(id,checkFavorite);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home :
                getSupportFragmentManager().beginTransaction().replace(R.id.contain_fragment,fragmentHome).commit();
                break;
            case R.id.nav_favorite :
                getSupportFragmentManager().beginTransaction().replace(R.id.contain_fragment,fragmentFavorite).commit();
                break;

        }

        return false;
    }
}

