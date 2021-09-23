package etn.app.danghoc.doancuoiky;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AdapterKarake extends ArrayAdapter<Item> {
Activity  context;
List<Item>list;
int resource;

    public AdapterKarake(@NonNull Activity context, int resource, @NonNull ArrayList<Item> objects) {
        super(context, resource, objects);
        this.context=context;
        this.list=objects;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
         final ViewHolder holder;
        if(convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.item,parent,false);
            holder=new ViewHolder();
            holder.txtNumber=convertView.findViewById(R.id.txtNumber);
            holder.txtTitle=convertView.findViewById(R.id.txtTitle);
            holder.btnLike=convertView.findViewById(R.id.btnLike);
            holder.btnUnlike=convertView.findViewById(R.id.btnUnLike);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        Item item=list.get(position);
        holder.txtNumber.setText(item.getNumber());
        holder.txtTitle.setText(item.getTitle());
        int like=item.getThich();
        //thich==0 chua like ,thich==1 like roi
        if(like==0){
            holder.btnUnlike.setVisibility(View.VISIBLE);
            holder.btnLike.setVisibility(View.INVISIBLE);
        }else{
            holder.btnUnlike.setVisibility(View.INVISIBLE);
            holder.btnLike.setVisibility(View.VISIBLE);
        }
        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values=new ContentValues();
                values.put("YEUTHICH",0);
                MainActivity.database.update("ArirangSongList",values,
                        "MABH=?",new String[]{holder.txtNumber.getText().toString()});
                holder.btnUnlike.setVisibility(View.VISIBLE);
                holder.btnLike.setVisibility(View.INVISIBLE);
            }
        });
        holder.btnUnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values=new ContentValues();
                values.put("YEUTHICH",1);
                MainActivity.database.update("ArirangSongList",values,
                        "MABH=?",new String[]{holder.txtNumber.getText().toString()});
                holder.btnUnlike.setVisibility(View.INVISIBLE);
                holder.btnLike.setVisibility(View.VISIBLE);
            }
        });
        holder.txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.txtNumber.setTextColor(Color.RED);
                holder.txtTitle.setTextColor(Color.RED);
                Intent intent=new Intent(context, ActivityInfoSong.class);
                Bundle bundle=new Bundle();
                bundle.putString("maso",holder.txtNumber.getText().toString());
                intent.putExtra("data",bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    private class ViewHolder{
        TextView txtNumber,txtTitle;
        ImageButton btnLike,btnUnlike;
    }
}
