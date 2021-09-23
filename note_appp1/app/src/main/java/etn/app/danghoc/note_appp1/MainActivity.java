//package etn.app.danghoc.note_appp1;
//
//import android.app.Dialog;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.ActionMode;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.SearchView;
//import androidx.fragment.app.Fragment;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//import java.util.Objects;
//
//import etn.app.danghoc.note_appp1.adapter.AdapterNote;
//import etn.app.danghoc.note_appp1.data.DatabaseNote;
//import etn.app.danghoc.note_appp1.fagment.FragmentFavorite;
//import etn.app.danghoc.note_appp1.model.ItemFavorite;
//import etn.app.danghoc.note_appp1.model.ItemTitle;
//
//public class MainActivity extends AppCompatActivity implements EventListenerClick {
//    public static ListView listViewNote;
//   private ImageButton buttonAdd;
//    private Button mBtRemoveFavo;
//    public static AdapterNote adapter;
//    TextView textViewempty;
//    Dialog dialog;
//    DatabaseNote data = new DatabaseNote(this);
//    public static List<ItemTitle> list;
//    Intent intent;
//    androidx.appcompat.widget.Toolbar toolbar;
//    private ActionMode mActionMode;
//    public static boolean checkFlag = false;
//    public static boolean checkDelete = false;
//    public static List<ItemFavorite> listAddFavorite;
//    private MenuItem itemSearch;
//    private BottomNavigationView mBottomNavMain;
//    // this is main
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        anhXa();
//        mBottomNavMain.setOnNavigationItemSelectedListener(mBottomNavListener);
//        list = new ArrayList<>();
//        listAddFavorite = new ArrayList<>();
//
//        //load data
//        list.addAll(data.getData());
//        loadFavorite();
//        adapter = new AdapterNote(this, R.layout.item_title, list);
//        adapter.setListener(this);
//
//        listViewNote.setAdapter(adapter);
//        setSupportActionBar(toolbar);
//        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);// remove on toolbar
//        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
//
//        //setAdapter();
//        if (list.size() == 0) {
//            textViewempty.setVisibility(View.VISIBLE);
//        } else textViewempty.setVisibility(View.INVISIBLE);
//
//        buttonAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDialog();
//            }
//        });
//        clickActionMode();
//    }
//    BottomNavigationView.OnNavigationItemSelectedListener mBottomNavListener =
//            new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                    Fragment fragmentSelect = new FragmentFavorite();
//                    switch (menuItem.getItemId()) {
//                        case R.id.nav_favorite:
//                            getSupportFragmentManager().
//                                    beginTransaction().
//                                    replace(R.id.fragment_content, fragmentSelect, "fragF").commit();
//
//                            break;
//                        case R.id.nav_home:
//                            FragmentFavorite fragmentFavorite = (FragmentFavorite) getSupportFragmentManager().
//                                    findFragmentByTag("fragF");
//                            getSupportFragmentManager().beginTransaction().remove(fragmentFavorite).commit();
//
//
//                            break;
//                    }
//                    return false;
//                }
//            };
//
//
//
//
//    // get day
//    private String getToDay() {
//        Calendar c = Calendar.getInstance();
//        int mDay = c.get(Calendar.DATE);
//        int mmonth = c.get(Calendar.MONTH) + 1;
//        int mYear = c.get(Calendar.YEAR);
//        return mDay + "-" + mmonth + "-" + mYear;
//    }
//
//    // dialog
//    public void showDialog() {
//        dialog = new Dialog(this);
//        dialog.setContentView(R.layout.dialog);
//        dialog.show();
//        final EditText editTextTitle = (EditText) dialog.findViewById(R.id.editText_create);
//        Button buttonOk = (Button) dialog.findViewById(R.id.button_ok);
//        Button buttonCancel = (Button) dialog.findViewById(R.id.button_cancel);
//        buttonCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        buttonOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                ItemTitle itemTitle = new ItemTitle(editTextTitle.getText().toString(), getToDay() + "", "",0);
//                Toast.makeText(MainActivity.this, "add success", Toast.LENGTH_SHORT).show();
//                data.addData(itemTitle);
//                list.clear();
//                list.addAll(data.getData());
//                setAdapter();
//
//                if (list.size() == 0) {
//                    textViewempty.setVisibility(View.VISIBLE);
//                } else {
//                    textViewempty.setVisibility(View.INVISIBLE);
//                }
//                adapter.itemTitlesFull.clear();
//                adapter.itemTitlesFull.addAll(list);
//                listViewNote.setSelection(adapter.getCount());
//                dialog.dismiss();
///*
//ban đầu cho vào main nó sẽ cho list = add data
//lần hai không gọi phương thức add nữa
//nếu gọi phương thức addData thì nó sẽ làm được
//tại sao ??
//  vấn để ở đây là tại vì list nó phỉa có cái add data thì nó mới chịu nhưng mình k muốn à mình đã hiểu tại vid nó k có id
//
// */
//
//
//            }
//        });
//    }
//
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public void anhXa() {
//        textViewempty = (TextView) findViewById(R.id.textview_empty);
//        listViewNote = (ListView) findViewById(R.id.listview_note);
//        buttonAdd = (ImageButton) findViewById(R.id.button_add);
//        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.tool_bar_main);
//        mBottomNavMain = (BottomNavigationView) findViewById(R.id.bottom_navigation_main);
//        mBtRemoveFavo=findViewById(R.id.button_remove_favo_main);
//        mBtRemoveFavo.setVisibility(View.INVISIBLE);
//    }
//
//    //event select item bottomNavigation
//
//
//    // setAdapter
//    public void setAdapter() {
//        adapter.notifyDataSetChanged();
//        listViewNote.setSelection(adapter.getCount());
//    }
//
//    // event menu search
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu_search, menu);
//        itemSearch = menu.findItem(R.id.action_search);
//        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) itemSearch.getActionView();
//        searchView.setQueryHint("search title...");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if (TextUtils.isEmpty(newText)) {
//
//                    adapter.filter("");
//                    listViewNote.clearTextFilter();
//                } else {
//
//                    adapter.filter(newText);
//                }
//                return true;
//            }
//        });
//        return true;
//
//    }
//
//    // event longclick
//
//
//    public void clickActionMode() {
//        if (adapter.multiCheckMode) {
//            listViewNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    ItemTitle itemTitle = list.get(position);
//                    itemTitle.setMCheck(!itemTitle.getCheck());//inverse selected
//                    adapter.notifyDataSetChanged();
//                }
//            });
//        }
//    }
//
//    // aciton mode
//    private ActionMode.Callback mActionModeCallBack = new ActionMode.Callback() {
//        @Override
//        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);
//            itemSearch.setVisible(false);
//            getSupportActionBar().hide();
//            buttonAdd.setEnabled(false);
//            mBottomNavMain.setVisibility(View.INVISIBLE);
//            return true;
//        }
//
//        @Override
//        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//            return false;
//        }
//
//        @Override
//        public boolean onActionItemClicked(ActionMode mode, final MenuItem item) {
//            clickActionMode();
//            switch (item.getItemId()) {
//
//                case R.id.item_delete_title:
//                    Toast.makeText(MainActivity.this, "delete success", Toast.LENGTH_SHORT).show();
//                    checkDelete = true;
//                    deleteItem();
//                    list.clear();
//                    list.addAll(data.getData());
//
//                    adapter.notifyDataSetChanged();
//                    if (list.size() == 0) {
//                        textViewempty.setVisibility(View.VISIBLE);
//                    } else {
//                        textViewempty.setVisibility(View.INVISIBLE);
//                    }
//                    mode.finish();
//                    return true;
//                case R.id.item_add_farourite:
//                    Toast.makeText(MainActivity.this, "add farourite success", Toast.LENGTH_SHORT).show();
//                    listAddFavorite.clear(); // xóa hết tất cả
//                    addFavorite(); // kiểm tra chuẩn bị add vào list
//                    loadFavorite(); // đưa vào list
//                    mode.finish();// kết thúc
//                    return true;
//                default:
//                    return false;
//            }
//
//        }
//
//        @Override
//        public void onDestroyActionMode(ActionMode mode) {
//            mActionMode = null;
//            checkFlag = false;
//            adapter.setMultiCheckMode(false);
//            buttonAdd.setEnabled(true);
//            itemSearch.setVisible(true);
//            getSupportActionBar().show();
//            mBottomNavMain.setVisibility(View.VISIBLE);
//            for (int i = 0; i < list.size(); i++) { // when action mode destroy >> setCheck =false
//                ItemTitle itemTitle = list.get(i);
//                itemTitle.setMCheck(false);
//            }
//
//        }
//    };
//
//    public void deleteItem() {//ok
//        for (int i = 0; i < list.size(); ) {
//            ItemTitle itemTitle = list.get(i);
//            if (itemTitle.getCheck()) {
//                data.deleteItemData(itemTitle.getId());
//                list.remove(itemTitle);
//                adapter.itemTitlesFull.remove(itemTitle);
//                listAddFavorite.clear();
//                loadFavorite();
//            } else {
//                i++;
//            }
//        }
//        adapter.notifyDataSetChanged();
//    }
//
//
//    private void addFavorite() {     // add list favourite
//        for (int i = 0; i < list.size(); i++) {
//            ItemTitle itemTitle = list.get(i);
//            if (itemTitle.getCheck()) { // 1 is true , true is add ; 0 is false
//                itemTitle.setmCheckFav(1);
//                data.updateCheckFav(itemTitle.getId(), 1);
//            }
//        }
//    }
//
//    private void loadFavorite() { // load list favourite
//        for (int i = 0; i < list.size(); i++) {
//            ItemTitle itemTitle = list.get(i);
//            if (itemTitle.getCheckFav() == 1) {
//                ItemFavorite itemFavorite = new ItemFavorite(itemTitle.getmTitle());
//                listAddFavorite.add(itemFavorite);
//            }
//        }
//    }
//
//
//    // back in android toi co mo mot
//    @Override
//    public void onBackPressed() {
//        Toast.makeText(this, "ban da thoat", Toast.LENGTH_SHORT).show();
//        MainActivity.this.finish();
//        list.clear();
//        // super.onBackPressed();
//    }
//
//    @Override
//    public void onClick(ItemTitle itemTitle) {
//
//        if (!adapter.multiCheckMode) {
//            intent = new Intent(MainActivity.this, activity_text.class);
//            Bundle bundle=new Bundle();
//            bundle.putInt("noteId",adapter.getPositionIntent());
//            String title=itemTitle.getmTitle();
//            if(title.length()>11){ // nếu title > 12 thì xóa tất cả các từ phía sau , và thêm dấu ... ở đằng sau
//                title=title.substring(0,12)+"...";
//            }
//           bundle.putString("noteTitle",title);
//            intent.putExtra("bundle",bundle);
//            startActivity(intent);
//        }
//    }
//
//    @Override
//    public void onLongClick(ItemTitle itemTitle) {
//
//        adapter.setMultiCheckMode(true);
//        itemTitle.setMCheck(true);
//        mActionMode = startActionMode(mActionModeCallBack);
//        buttonAdd.setEnabled(false);
//    }
//    // dấu ngoặt cuối cùng
//
//    //setter
//
//
//
//
//
//
//
//
//}
///*
//// fragmen https://www.youtube.com/watch?v=k3MrvcZbbuI
//https://codinginflow.com/tutorials/android/bottomnavigationview
//https://www.youtube.com/watch?v=tPV8xA7m-iw&feature=youtu.be
//
//
// */