package etn.app.danghoc.pragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import etn.app.danghoc.pragment.R;
import etn.app.danghoc.pragment.model.ItemListViewA;

public class AdapterListA extends ArrayAdapter<ItemListViewA> {
    private Context context;
    private int resource;
    private List<ItemListViewA> itemListViewAList;

    public AdapterListA(@NonNull Context context, int resource, @NonNull List<ItemListViewA> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.itemListViewAList = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.itemlist_view_a, parent, false);
            holder = new ViewHolder();
            holder.textViewName=(TextView)convertView.findViewById(R.id.textview_name);
            holder.textViewNumber=(TextView)convertView.findViewById(R.id.textview_number);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
         ItemListViewA item =itemListViewAList.get(position);
        holder.textViewNumber.setText(item.getmNumber());
         holder.textViewName.setText(item.getmName());
        return convertView;
    }

    public class ViewHolder {
        public TextView textViewName, textViewNumber;
    }
}