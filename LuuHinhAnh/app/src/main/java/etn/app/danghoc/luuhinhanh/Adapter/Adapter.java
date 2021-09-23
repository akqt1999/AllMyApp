package etn.app.danghoc.luuhinhanh.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import etn.app.danghoc.luuhinhanh.ItemImage;
import etn.app.danghoc.luuhinhanh.R;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {
    private List<ItemImage> listImage;

    public Adapter(List<ItemImage> listImage) {
        this.listImage = listImage;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ItemImage itemImage = listImage.get(position);
        holder.tvTitle.setText(itemImage.getTitle());
        byte[] image =itemImage.getImage();
      //byte--->>> bitmap
        Bitmap bitmap= BitmapFactory.decodeByteArray(image,0,image.length);
        holder.imageView.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return listImage.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
TextView tvTitle;
ImageView imageView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle=(TextView) itemView.findViewById(R.id.textview_title);
            imageView=(ImageView)itemView.findViewById(R.id.imageview_item);
        }
    }
}
