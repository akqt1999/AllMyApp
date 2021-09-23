package etn.app.danghoc.appshopping_demo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import etn.app.danghoc.appshopping_demo.R;
import etn.app.danghoc.appshopping_demo.model.ModelItemSanPham;

public class AdapterPay extends RecyclerView.Adapter<AdapterPay.viewHolder> {
    List<ModelItemSanPham>mList;
    onCallBack mListener;

    public AdapterPay(List<ModelItemSanPham> mList, onCallBack mListener) {
        this.mList = mList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pay,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
                ModelItemSanPham sanPham=mList.get(position);
                holder.mTvNameProduct.setText(sanPham.getNameSanPham());
                holder.mTvNameStore.setText(sanPham.getNameStore());
                holder.mTvPrice.setText("price : "+sanPham.getPrice()+"");
                holder.mTvAmount.setText("amount : "+sanPham.getAmount()+"");

                holder.mTvNameStore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView mTvNameProduct;
        TextView mTvNameStore;
        TextView mTvPrice;
        TextView mTvAmount;

        public viewHolder(@NonNull View itemView ) {
            super(itemView);
        mTvNameProduct=itemView.findViewById(R.id.textview_name_pay);
        mTvNameStore=itemView.findViewById(R.id.textview_name_store_pay);
        mTvPrice=itemView.findViewById(R.id.textview_price_pay);
        mTvAmount=itemView.findViewById(R.id.textview_amount_pay);

        }
    }
    public interface onCallBack{
      void itemNameStoreClick();
    };

}
