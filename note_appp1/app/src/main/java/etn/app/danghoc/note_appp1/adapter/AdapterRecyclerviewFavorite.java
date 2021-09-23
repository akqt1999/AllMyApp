package etn.app.danghoc.note_appp1.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import etn.app.danghoc.note_appp1.R;
import etn.app.danghoc.note_appp1.model.ItemFavorite;

public class AdapterRecyclerviewFavorite extends RecyclerView.Adapter<AdapterRecyclerviewFavorite.viewHolder> {
    private List<ItemFavorite> mList;
    private OnCallBack mListener;
    private boolean mCheck =false;
    private boolean checkNotify=false;
    public AdapterRecyclerviewFavorite( OnCallBack mListener, List<ItemFavorite> mList) {
        this.mList = mList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_rcv, parent, false);
        return new viewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
        final ItemFavorite itemFavorite = mList.get(position);
        holder.mTvTitle.setText(itemFavorite.getTitle());
       holder.mImvDone.setVisibility(View.INVISIBLE);

        if(mCheck){ // set backgroud cho item
            Log.d("check action mode  ",mCheck+"");
            if(checkNotify){
                Log.d("check notify  ",checkNotify+"");
                Log.d("check item favorite  ",itemFavorite.isCheck()+"");
                if(itemFavorite.isCheck()){
                Log.d("check item favorite in ",itemFavorite.isCheck()+"");
                holder.mTvTitle.setBackgroundColor(Color.parseColor("#ecee06")); // mau vang
                holder.mImvDone.setVisibility(View.VISIBLE);
            }else{
                    Log.d("else favorite in ",itemFavorite.isCheck()+"");
                    holder.mTvTitle.setBackgroundColor(Color.parseColor("#CB82CD")); // mau hong
//mau hong
                    holder.mImvDone.setVisibility(View.INVISIBLE);
                }}
        }else{
            itemFavorite.setCheck(false);
            holder.mTvTitle.setBackgroundColor(Color.parseColor("#CB82CD")); // mau hong

        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView mTvTitle;
        ImageView mImvDone;
        OnCallBack mListener;

        public viewHolder(@NonNull View itemView, final OnCallBack mListener) {
            super(itemView);
            this.mListener = mListener;
            mTvTitle = (TextView) itemView.findViewById(R.id.textview_title_favo);
            mImvDone = (ImageView) itemView.findViewById(R.id.imageview_done_favo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mListener.onItemLongClick(getAdapterPosition());
                    return false;
                }
            });
        }
    }

    public interface OnCallBack {
        void onItemClick(int position);

        void onItemLongClick(int position);
    }

    public boolean isCheck() {
        return mCheck;
    }

    public void setCheck(boolean mCheck) {
        this.mCheck = mCheck;
        notifyDataSetChanged();
    }
    public void setCheckNotify(boolean checkNotify){
        this.checkNotify=checkNotify;
        notifyDataSetChanged();
    }

}
/*
minh tu noi la minh lam ra tien roi thi minh khong can ve que nua
nhung tai sao minhh thay hoc ic lai co cam giac muon ve que hon
rang toi nam 3 hoc full lun
minh muon ve que hon la vi m chua co tien do
 */