package etn.app.danghoc.luuhinhanhtrenlocalhost.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import etn.app.danghoc.luuhinhanhtrenlocalhost.ActivityShowImage;
import etn.app.danghoc.luuhinhanhtrenlocalhost.MainActivity;
import etn.app.danghoc.luuhinhanhtrenlocalhost.R;
import etn.app.danghoc.luuhinhanhtrenlocalhost.model.ItemImage;

public class AdapterImage extends RecyclerView.Adapter<AdapterImage.viewHodel> {
    private Context context;
    private List<ItemImage> mList;

    public AdapterImage(List<ItemImage> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public viewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        context = parent.getContext();
        return new viewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHodel holder, int position) {
        ItemImage itemImage = mList.get(position);
        Picasso.with(context).load(itemImage.getURL()).into(holder.mImImage);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class viewHodel extends RecyclerView.ViewHolder {
        ImageView mImImage;

        public viewHodel(@NonNull View itemView) {
            super(itemView);
            mImImage = (ImageView) itemView.findViewById(R.id.imageview_hinh);
        }
    }
}






