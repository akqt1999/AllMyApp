package etn.app.danghoc.appshopping_demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import etn.app.danghoc.appshopping_demo.R;
import etn.app.danghoc.appshopping_demo.model.ModelItemSanPham;

public class AdapterBasket extends RecyclerView.Adapter<AdapterBasket.viewHolder> {
    private List<ModelItemSanPham> mList;
    private OnCallBack mListener;
    private int amount=0;
    private double totalMoney=0;
    private Context context;

    public AdapterBasket(List<ModelItemSanPham> mList, OnCallBack mListener) {
        this.mList = mList;
        this.mListener = mListener;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_basket, parent, false);
        context=parent.getContext();
        return new viewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterBasket.viewHolder holder, final int position) {
         final ModelItemSanPham itemSanPham=mList.get(position);
         amount=itemSanPham.getAmount();
         totalMoney=itemSanPham.getTotalMoney();
         mListener.totalMoney(totalMoney);
        if(amount==0){ holder.mBtnMinus.setEnabled(false); }

        holder.mTvAmount.setText(itemSanPham.getAmount()+"");
        holder.mTvTotalMoney.setText(" "+itemSanPham.getAmount()*itemSanPham.getPrice()+ "vnd");
        holder.mTvName.setText(itemSanPham.getNameSanPham());

        holder.mBtnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount--;
                totalMoney=0;
                int amountSP=itemSanPham.getAmount();
                    amountSP--;
                itemSanPham.setAmount(amountSP);
                holder.mTvAmount.setText(itemSanPham.getAmount()+"");

                itemSanPham.setTotalMoney(itemSanPham.getAmount()*itemSanPham.getPrice());
                holder.mTvTotalMoney.setText(" "+itemSanPham.getAmount()*itemSanPham.getPrice()+ "vnd");
                if(itemSanPham.getAmount()>0){
                    holder.mBtnMinus.setEnabled(true);
                }
                if(itemSanPham.getAmount()==0){
                    holder.mBtnMinus.setEnabled(false);
                }
                for(ModelItemSanPham cc : mList){
                    totalMoney+=cc.getTotalMoney();
                }
                mListener.totalMoney(totalMoney);
            }
        });

        holder.mBtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount++;
                totalMoney=0;
                int amountSP=itemSanPham.getAmount();
                amountSP++;
                itemSanPham.setAmount(amountSP);
                itemSanPham.setTotalMoney(itemSanPham.getAmount()*itemSanPham.getPrice());

                holder.mTvAmount.setText(itemSanPham.getAmount()+"");
                holder.mTvTotalMoney.setText(" "+itemSanPham.getAmount()*itemSanPham.getPrice()+" vnd");

                if(itemSanPham.getAmount()>0){
                    holder.mBtnMinus.setEnabled(true);
                }

                for(ModelItemSanPham cc : mList){
                    totalMoney+=cc.getTotalMoney();
                }
                mListener.totalMoney(totalMoney);

            }
        });

        holder.mBtnBuyThis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        holder.mBtnBuyLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mListener.clickBuyLater(position);
            }
        });
        holder.mImgBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Glide.with(context).load(itemSanPham.getUrlImage()).centerCrop().override(200,200).into(holder.mImgProduct);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView mImgProduct;
        TextView mTvName, mTvAmount, mTvTotalMoney;
        Button mBtnBuyThis, mBtnBuyLater, mBtnPlus, mBtnMinus;
        ImageButton mImgBtnDelete;

        public viewHolder(@NonNull View itemView, final OnCallBack mListener) {
            super(itemView);
            mImgProduct = (ImageView) itemView.findViewById(R.id.imageview_product_basket);

            mTvName = (TextView) itemView.findViewById(R.id.textview_name_basket);
            mTvAmount = (TextView) itemView.findViewById(R.id.textview_amount_basket);
            mTvTotalMoney = (TextView) itemView.findViewById(R.id.textview_price_basekt);

            mBtnBuyLater = (Button) itemView.findViewById(R.id.button_buy_later_basket);
            mBtnBuyThis = (Button) itemView.findViewById(R.id.button_buy_this_basket);
            mBtnPlus = (Button) itemView.findViewById(R.id.button_plus_basket);
            mBtnMinus = (Button) itemView.findViewById(R.id.button_minus_basket);

            mImgBtnDelete = (ImageButton) itemView.findViewById(R.id.image_button_delete_basket);

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
        void totalMoney(double total);
        void clickBuyLater(int position);
    }


}
