package etn.app.danghoc.docbaorss;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import etn.app.danghoc.docbaorss.Model.Item;

public class AdapterDocBao extends ArrayAdapter<Item> {
    private Context context;
    private int resource ;
    private ArrayList<Item> items;
    public AdapterDocBao(@NonNull Context context, int resource, @NonNull ArrayList<Item> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.items=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder holder;
            if(convertView==null){
                convertView= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
                holder=new ViewHolder();
                holder.txtInfo=convertView.findViewById(R.id.txtInfo);
                holder.txtTitle=convertView.findViewById(R.id.txtTitle);
                holder.img=convertView.findViewById(R.id.img);
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }

            final Item item=items.get(position);
            holder.txtTitle.setText(item.getTitle());
            holder.txtInfo.setText(item.getInfo());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLink()));
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView txtTitle,txtInfo;
        ImageView img;
    }


}
