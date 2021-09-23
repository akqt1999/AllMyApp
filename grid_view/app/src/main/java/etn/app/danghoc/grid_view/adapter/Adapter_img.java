package etn.app.danghoc.grid_view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import etn.app.danghoc.grid_view.R;
import etn.app.danghoc.grid_view.model.Image;

public class Adapter_img extends BaseAdapter {
       private Context context;
       private int layout;
       private List<Image> imageList;

    public Adapter_img(Context context, int layout, List<Image> imageList) {
        this.context = context;
        this.layout = layout;
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
                 ViewHordel hordel;
                 if(convertView==null){

                     convertView= LayoutInflater.from(context).inflate(R.layout.layout_image,parent,false);

                     hordel =new ViewHordel();
                     hordel.imageViewItems=(ImageView) convertView.findViewById(R.id.image_view_img);
                     hordel.textViewInfo=(TextView) convertView.findViewById(R.id.textview_info_img);
                     convertView.setTag(hordel);
                 }
                 else{
                     hordel= (ViewHordel) convertView.getTag();
                 }
                 // set giá trị

                Image image=imageList.get(position);
                 hordel.textViewInfo.setText(image.getInfoimage());
                 hordel.imageViewItems.setImageResource(image.getImage());
                 ///////////================

        return convertView;
    }

    private class ViewHordel{
      private ImageView imageViewItems;
      private TextView textViewInfo;
    }
}
