package etn.app.danghoc.note_appp1.fagment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import etn.app.danghoc.note_appp1.ActivityHome;
import etn.app.danghoc.note_appp1.EventSqLite;

import etn.app.danghoc.note_appp1.R;
import etn.app.danghoc.note_appp1.adapter.AdapterNote;
import etn.app.danghoc.note_appp1.adapter.AdapterRecyclerviewFavorite;
import etn.app.danghoc.note_appp1.activity_text;
import etn.app.danghoc.note_appp1.model.ItemFavorite;
import etn.app.danghoc.note_appp1.model.ItemTitle;

public class FragmentFavorite extends Fragment implements AdapterRecyclerviewFavorite.OnCallBack  {
    private AdapterRecyclerviewFavorite mAdapter;
    private RecyclerView mRcvFavorite;
    private androidx.appcompat.widget.Toolbar toolbar;
    private List<ItemFavorite> mListFavorite;
    private ImageButton imgBtDelete, imgBtCancel;
    EventSqLite listen;
    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        mRcvFavorite = view.findViewById(R.id.recyclerview_favorite);

        mListFavorite=new ArrayList<>();
        mListFavorite.addAll(ActivityHome.listAddFavorite);


        toolbar = view.findViewById(R.id.tool_bar_frag);
        imgBtDelete = view.findViewById(R.id.img_bt_delete);
        imgBtDelete.setVisibility(View.INVISIBLE);
        imgBtCancel = view.findViewById(R.id.img_bt_cancel);
        imgBtCancel.setVisibility(View.INVISIBLE);
        eventButton();

        mRcvFavorite.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new AdapterRecyclerviewFavorite(this, mListFavorite);
        mRcvFavorite.setAdapter(mAdapter);
// bây giờ muốn theo dõi nhiều lược xem trung bình lâu thì phải làm cho ngưởi ta theo dõi câu chuyện của mình
        // kiểu nói câu chuyenj cho tưng video se khiến người ta cuốn vào đó hơn con đã rút ra được một bài học , tuy hơi lâu nhưn g nó chất lượng
        // giấ trị của sản phảm tỉ lệ tuận với thời gian làm ra sản phẩm đó

        return view;
    }


    @Override
    public void onItemClick(int position) {
        ItemFavorite itemFavorite = mListFavorite.get(position);
        if (mAdapter.isCheck()) {
            itemFavorite.setCheck(!itemFavorite.isCheck());
            mAdapter.setCheckNotify(true);

        } else {
            Intent intent = new Intent(getActivity(), activity_text.class);
            Bundle bundle = new Bundle();
            String title = itemFavorite.getTitle();
            if (title.length() > 11) { // nếu title > 12 thì xóa tất cả các từ phía sau , và thêm dấu ... ở đằng sau
                title = title.substring(0, 12) + "...";
            }
            for (int i=0;i<ActivityHome.list.size(); i++) {
                if(itemFavorite.getTitle().equalsIgnoreCase(ActivityHome.list.get(i).getmTitle())){
                    bundle.putInt("noteId", i);
                    break;
                }
            }
            bundle.putString("noteTitle", title);
            intent.putExtra("bundle", bundle);
            startActivity(intent);
        }
    }

    @Override
    public void onItemLongClick(int position) {
        ItemFavorite itemFavorite = mListFavorite.get(position);
        itemFavorite.setCheck(!itemFavorite.isCheck());
        mAdapter.setCheck(true);
        mAdapter.setCheckNotify(true);
        imgBtCancel.setVisibility(View.VISIBLE);
        imgBtDelete.setVisibility(View.VISIBLE);
    }


    // enabled menu in fragment


    // handle delete item favorite
    private void deleteItem() {
            for (int i = 0; i < mListFavorite.size(); ) {
                ItemFavorite itemFavorite = ActivityHome.listAddFavorite.get(i);
                if (itemFavorite.isCheck()) {
                    ActivityHome.listAddFavorite.remove(itemFavorite);
                    mListFavorite.remove(itemFavorite);//1 add , 0 no add
                    mAdapter.notifyDataSetChanged();
                    listen.deleteFavorite(itemFavorite.getId(),0);
//                    if(itemFavorite.getTitle().equalsIgnoreCase(ActivityHome.list.get(i).getmTitle())){
//
//                        listen.deleteFavorite(ActivityHome.list.get(i).getId(),0);
//                    }
                } else {
                    i++;
                }
            }
        }




    private void eventButton() {
        imgBtDelete.setOnClickListener(new View.OnClickListener() { // button delete
            @Override
            public void onClick(View v) { // button delete
                deleteItem();
                imgBtCancel.setVisibility(View.INVISIBLE);
                imgBtDelete.setVisibility(View.INVISIBLE);

            }
        });

        imgBtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 imgBtCancel.setVisibility(View.INVISIBLE);
                 imgBtDelete.setVisibility(View.INVISIBLE);
                mAdapter.setCheck(false);
                mAdapter.setCheckNotify(false);
            }
        });
    }

    public void setListen(EventSqLite listen) {
        this.listen = listen;
    }
}

/*

 */