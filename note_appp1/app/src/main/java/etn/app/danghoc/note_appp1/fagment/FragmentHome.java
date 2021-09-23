package etn.app.danghoc.note_appp1.fagment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import etn.app.danghoc.note_appp1.ActivityHome;
import etn.app.danghoc.note_appp1.EventListenerClick;
import etn.app.danghoc.note_appp1.EventSqLite;
import etn.app.danghoc.note_appp1.R;
import etn.app.danghoc.note_appp1.adapter.AdapterNote;
import etn.app.danghoc.note_appp1.activity_text;
import etn.app.danghoc.note_appp1.model.ItemFavorite;
import etn.app.danghoc.note_appp1.model.ItemTitle;

public class FragmentHome extends Fragment implements EventListenerClick  {

    public  ListView listViewNote;
    private ImageButton buttonAdd;
    public  AdapterNote adapter;
    TextView textViewempty;
    Dialog dialog;
    public  List<ItemTitle> list;

    Intent intent;
    androidx.appcompat.widget.Toolbar toolbar;
    private ActionMode mActionMode;
    public static boolean checkFlag = false;
    public static boolean checkDelete = false;
    private MenuItem itemSearch;
    EventSqLite listen;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        init(view);
        setHasOptionsMenu(true);

        adapter = new AdapterNote(getActivity(), R.layout.item_title, list);
        adapter.setListener(this);

        listViewNote.setAdapter(adapter);


        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity)getActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(false);// remove on toolbar
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

        //setAdapter();
        if (list.size() == 0) {
            textViewempty.setVisibility(View.VISIBLE);
        } else textViewempty.setVisibility(View.INVISIBLE);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        clickActionMode();
        return view;
    }

    private void init(View v) {
        //list.addAll(data.getData());
        textViewempty = v.findViewById(R.id.textview_empty);
        listViewNote = v.findViewById(R.id.listview_note);
        buttonAdd = v.findViewById(R.id.button_add);
        toolbar = v.findViewById(R.id.tool_bar_main);

        list=new ArrayList<>();
        list.addAll(ActivityHome.list);
    }

    private String getToDay() {
        Calendar c = Calendar.getInstance();
        int mDay = c.get(Calendar.DATE);
        int mmonth = c.get(Calendar.MONTH) + 1;
        int mYear = c.get(Calendar.YEAR);
        return mDay + "-" + mmonth + "-" + mYear;
    }
    public void setAdapter() {
        adapter.notifyDataSetChanged();
        listViewNote.setSelection(adapter.getCount());
    }

    public void showDialog() {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog);
        dialog.show();
        final EditText editTextTitle = (EditText) dialog.findViewById(R.id.editText_create);
        Button buttonOk = (Button) dialog.findViewById(R.id.button_ok);
        Button buttonCancel = (Button) dialog.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ItemTitle itemTitle = new ItemTitle(editTextTitle.getText().toString(), getToDay() + "", "",0);
                //{//data.addData(itemTitle);
                    //list.clear();
                    //list.addAll(data.getData());
                    //setAdapter();
                //}

                list.clear();
                listen.addItem(itemTitle);
                list.addAll(ActivityHome.list);
                adapter.notifyDataSetChanged();
                if (list.size() == 0) {
                    textViewempty.setVisibility(View.VISIBLE);
                } else {
                    textViewempty.setVisibility(View.INVISIBLE);
                }
                adapter.itemTitlesFull.clear();
                adapter.itemTitlesFull.addAll(list);
                listViewNote.setSelection(adapter.getCount());
                dialog.dismiss();


            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu_search, menu);
        itemSearch = menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) itemSearch.getActionView();
        searchView.setQueryHint("search title...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {

                    adapter.filter("");
                    listViewNote.clearTextFilter();
                } else {

                    adapter.filter(newText);
                }
                return true;
            }
        });

    }


    public void clickActionMode() {
        if (adapter.multiCheckMode) {
            listViewNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ItemTitle itemTitle = list.get(position);
                    itemTitle.setMCheck(!itemTitle.getCheck());//inverse selected
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    private ActionMode.Callback mActionModeCallBack = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);
            itemSearch.setVisible(false);
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
            buttonAdd.setEnabled(false);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, final MenuItem item) {
            clickActionMode();
            switch (item.getItemId()) {

                case R.id.item_delete_title:
                    /*
                     checkDelete = true;
                    list.clear();
                    list.addAll(data.getData());
                     */
                    deleteItem();

                   // adapter.notifyDataSetChanged();
                    if (list.size() == 0) {
                        textViewempty.setVisibility(View.VISIBLE);
                    } else {
                        textViewempty.setVisibility(View.INVISIBLE);
                    }
                    mode.finish();
                    return true;
                case R.id.item_add_farourite:
                    ActivityHome.listAddFavorite.clear(); // xóa hết tất cả
                    addFavorite(); // kiểm tra chuẩn bị add vào list
                    loadFavorite(); // đưa vào list
                    mode.finish();// kết thúc
                    return true;
                default:
                    return false;
            }

        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            checkFlag = false;
            adapter.setMultiCheckMode(false);
            buttonAdd.setEnabled(true);
            itemSearch.setVisible(true);
            ((AppCompatActivity)getActivity()).getSupportActionBar().show();
            for (int i = 0; i < list.size(); i++) { // when action mode destroy >> setCheck =false
                ItemTitle itemTitle = list.get(i);
                itemTitle.setMCheck(false);
            }

        }
    };

    public void deleteItem() {//ok
        for (int i = 0; i < list.size(); ) {
            ItemTitle itemTitle = list.get(i);
            if (itemTitle.getCheck()) {
//                data.deleteItemData(itemTitle.getId());
                listen.deleteItem(itemTitle.getId());
                list.remove(itemTitle);
                adapter.itemTitlesFull.remove(itemTitle);

            } else {
                i++;
            }
        }
       ActivityHome.list.clear();
       ActivityHome.list.addAll(list);
        ActivityHome.listAddFavorite.clear();
        loadFavorite();
        adapter.notifyDataSetChanged();
    }


    private void addFavorite() {     // add list favourite
        for (int i = 0; i < list.size(); i++) {
            ItemTitle itemTitle = list.get(i);
            if (itemTitle.getCheck()) { // 1 is true , true is add ; 0 is false
                itemTitle.setmCheckFav(1);
                listen.addFavorite(itemTitle.getId(),itemTitle.getCheckFav());
            }
        }
    }


    @Override
    public void onClick(ItemTitle itemTitle) {

        if (!adapter.multiCheckMode) {
            intent = new Intent(getActivity(), activity_text.class);
            Bundle bundle=new Bundle();
            bundle.putInt("noteId",adapter.getPositionIntent());
            String title=itemTitle.getmTitle();
            if(title.length()>11){ // nếu title > 12 thì xóa tất cả các từ phía sau , và thêm dấu ... ở đằng sau
                title=title.substring(0,12)+"...";
            }
            bundle.putString("noteTitle",title);
            intent.putExtra("bundle",bundle);
            startActivity(intent);
        }
    }

    @Override
    public void onLongClick(ItemTitle itemTitle) {

        adapter.setMultiCheckMode(true);
        itemTitle.setMCheck(true);
        mActionMode = ((AppCompatActivity)getActivity()).startActionMode(mActionModeCallBack);
        buttonAdd.setEnabled(false);
    }




    private void loadFavorite() { // load list favourite
        for (int i = 0; i < list.size(); i++) {
            ItemTitle itemTitle = list.get(i);
            if (itemTitle.getCheckFav() == 1) {
                ItemFavorite itemFavorite = new ItemFavorite(itemTitle.getmTitle(),itemTitle.getId());
                ActivityHome.listAddFavorite.add(itemFavorite);
            }
        }
    }



    public void setListen(EventSqLite listen) {
        this.listen = listen;
    }
}


