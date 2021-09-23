package etn.app.danghoc.custom_listview.adapter;

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

import etn.app.danghoc.custom_listview.R;
import etn.app.danghoc.custom_listview.model.Contact;

public class CustomAdapter extends ArrayAdapter<Contact> {

   private Context context; //tiep xuc
   private int resource;
   private ArrayList<Contact> arrContact;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Contact> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.arrContact=objects;
    }
 /// phải c
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.row_item_contact,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.textViewName =(TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.textViewNumberPhone=(TextView) convertView.findViewById(R.id.tv_number_phone);
            viewHolder.textViewStt=(TextView) convertView.findViewById(R.id.tv_stt);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder) convertView.getTag();
        }


        Contact contact=arrContact.get(position);
        viewHolder.textViewName.setText(contact.getmName());
        viewHolder.textViewNumberPhone.setText(contact.getmNumberPhone());
        viewHolder.textViewStt.setText((position+1)+"");
        viewHolder.textViewStt.setBackgroundColor(contact.getmStt());// cái này là màu

       return convertView ;
    }

    public class ViewHolder{
        TextView textViewName;
        TextView textViewNumberPhone;
        TextView textViewStt; // cũng là màu
    }
}