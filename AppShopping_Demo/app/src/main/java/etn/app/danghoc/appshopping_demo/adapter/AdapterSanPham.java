package etn.app.danghoc.appshopping_demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

import javax.security.auth.callback.Callback;

import etn.app.danghoc.appshopping_demo.R;
import etn.app.danghoc.appshopping_demo.model.ModelItemSanPham;

public class AdapterSanPham extends RecyclerView.Adapter<AdapterSanPham.viewHolder> {
    private List<ModelItemSanPham> mListSP;
    private OnCallBack mListener;
    private Context context;


    public AdapterSanPham(List<ModelItemSanPham> mListSP, OnCallBack mListener) {
        this.mListSP = mListSP;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_san_pham,parent,false);
        context=parent.getContext();
        return new viewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ModelItemSanPham itemSanPham=mListSP.get(position);
        holder.mTvName.setText(itemSanPham.getNameSanPham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.mTvPrice.setText("price : "+decimalFormat.format(itemSanPham.getPrice()) +" dong");
       //Picasso.with(context).load(itemSanPham.getUrlImage()).resize(241,151) .into(holder.mImgProduct);
        Glide.with(context)
                .load(itemSanPham.getUrlImage())
                .centerCrop()
                .override(200,200)
                .into(holder.mImgProduct);
    }

    @Override
    public int getItemCount() {
        return mListSP.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView mImgProduct;
        TextView mTvPrice, mTvName;
        OnCallBack mListener;
        public viewHolder(@NonNull View itemView, final OnCallBack mListener) {
            super(itemView);
            mImgProduct = itemView.findViewById(R.id.imageview_san_pham);
            mTvName= itemView.findViewById(R.id.textview_name);
            mTvPrice= itemView.findViewById(R.id.textview_price);
            this.mListener=mListener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.itemOnClick(getAdapterPosition());
                }
            });
        }
    }

    public interface OnCallBack {
        void itemOnClick(int position);

        boolean onCreateOptionsMenu(Menu menu);
    }
}
