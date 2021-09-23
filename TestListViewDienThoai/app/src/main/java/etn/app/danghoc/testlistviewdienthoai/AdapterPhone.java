package etn.app.danghoc.testlistviewdienthoai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.List;

public class AdapterPhone extends ArrayAdapter<Phone> {
    Context context;
    int resource;
    List<Phone> phones;

    public AdapterPhone(@NonNull Context context, int resource, List<Phone> object) {
        super(context, resource, object);
        this.context = context;
        this.resource = resource;
        this.phones = object;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_phone,parent,false);
            holder=new ViewHolder();
            holder.txtNamePhone=convertView.findViewById(R.id.txtNamePhone);
            holder.imgPhone=convertView.findViewById(R.id.imgPhone);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        Phone phone=phones.get(position);
            holder.txtNamePhone.setText(phone.getNamePhone());
            holder.imgPhone.setImageResource(phone.getImage());
            return convertView;
    }

    private class ViewHolder {
        private TextView txtNamePhone;
        private ImageView imgPhone;
    }
}
