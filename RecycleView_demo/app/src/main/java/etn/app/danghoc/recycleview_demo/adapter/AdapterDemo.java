package etn.app.danghoc.recycleview_demo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;

import etn.app.danghoc.recycleview_demo.MainActivity;
import etn.app.danghoc.recycleview_demo.R;
import etn.app.danghoc.recycleview_demo.model.ItemDemo;

public class AdapterDemo extends RecyclerView.Adapter<AdapterDemo.viewHolder> {

    private List<ItemDemo> mListDemo;
    private OnCallBack mListener;
    private boolean mCheck = false;

    public AdapterDemo(OnCallBack mListener, List<ItemDemo> mListDemo) {
        this.mListDemo = mListDemo;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_demo, parent, false);
        return new viewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, final int position) {
        final ItemDemo itemDemo = mListDemo.get(position);
        holder.tvTitle.setText(itemDemo.getName());
        holder.imgDone.setVisibility(View.INVISIBLE);
        if(mCheck) {
            if (itemDemo.isCheck()) { // true is visible
                holder.imgDone.setVisibility(View.VISIBLE);
            } else { // false is invisible
                holder.imgDone.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mListDemo.size();
    }

    public class viewHolder extends ViewHolder {
        TextView tvTitle;
        ImageView imgDone;
        OnCallBack listener;

        public viewHolder(@NonNull final View itemView, final OnCallBack listener) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.textview_title);
            imgDone = (ImageView) itemView.findViewById(R.id.imageview_done);
            this.listener = listener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onITemLongClick(getAdapterPosition());
                    return false;
                }
            });
        }


    }

    public interface OnCallBack {
        void onItemClick(int position);
        void onITemLongClick(int position);
    }

    public void setCheck(boolean mCheck) {
        this.mCheck = mCheck;
        notifyDataSetChanged();
    }

    public boolean getCheck() {
        return mCheck;
    }
}

/*

 */