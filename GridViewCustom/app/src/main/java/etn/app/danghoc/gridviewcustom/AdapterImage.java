package etn.app.danghoc.gridviewcustom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AdapterImage extends ArrayAdapter<Image> {
    Context context;
    int resource;
    ArrayList<Image> images;

    public AdapterImage(@NonNull Context context, int resource, @NonNull ArrayList<Image> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.images=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_image,parent,false);
            holder=new ViewHolder();
            holder.img=convertView.findViewById(R.id.img);
            holder.txtTitle=convertView.findViewById(R.id.txtTitle);
            convertView.setTag(holder);

        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        Image image=images.get(position);
        holder.txtTitle.setText(image.getTitle());
        holder.img.setImageResource(image.getImage());
        return convertView;
    }

    private class ViewHolder{
        TextView txtTitle;
        ImageView img;
    }
}
