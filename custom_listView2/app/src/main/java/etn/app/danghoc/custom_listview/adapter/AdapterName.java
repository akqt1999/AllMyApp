package etn.app.danghoc.custom_listview.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import etn.app.danghoc.custom_listview.R;
import etn.app.danghoc.custom_listview.model.Item;

public class AdapterName extends ArrayAdapter<Item> {
    private Context context;
    int resource;
    List<Item> itemList;

    public AdapterName(@NonNull Context context, int resource, @NonNull List<Item> objects) {
        super(context, resource, objects);
         this.context=context;
         this.resource=resource;
         this.itemList=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_list_view,parent,false);
            holder=new ViewHolder();
            holder.textViewName=(TextView) convertView.findViewById(R.id.textview_name);
            holder.textViewStt=(TextView) convertView.findViewById(R.id.textview_stt);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }
          final Item item =itemList.get(position);
        holder.textViewStt.setText(item.getmNumber());
        holder.textViewName.setText(item.getmName());

        return convertView;
    }

    public class ViewHolder{
            public TextView textViewStt,textViewName;

    }
}
