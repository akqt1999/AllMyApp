package etn.app.danghoc.luu_listview2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import etn.app.danghoc.luu_listview2.R;
import etn.app.danghoc.luu_listview2.mode.ExampleItem;
import etn.app.danghoc.luu_listview2.R;
import etn.app.danghoc.luu_listview2.mode.ExampleItem;

public class ExampleAdapter extends ArrayAdapter {
    private Context context;
    private int resource;
    private List <ExampleItem> listObjects;
    public ExampleAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ExampleItem> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.listObjects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
            holder.textViewTitle=(TextView) convertView.findViewById(R.id.textview_title);
            holder.textVirewContent=(TextView) convertView.findViewById(R.id.textview_content);
            convertView.setTag(holder);
        }
        else {
            holder= (ViewHolder) convertView.getTag();
        }

        ExampleItem exampleItem=listObjects.get(position);
        holder.textViewTitle.setText(exampleItem.getTitle());
        holder.textVirewContent.setText(exampleItem.getContent());
        return convertView;
    }

    public class ViewHolder{
        private TextView textViewTitle;
        private TextView textVirewContent;
    }
}

