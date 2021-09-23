package etn.app.danghoc.sqlite;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import etn.app.danghoc.sqlite.model.PhoneBook;

public class AdapterPhoneBook extends ArrayAdapter<PhoneBook> {
    MainActivity context;
    int resource;
    List<PhoneBook> phoneBookList;

    public AdapterPhoneBook(@NonNull MainActivity context, int resource, @NonNull List<PhoneBook> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.phoneBookList=objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.pb_layout,parent,false);
            viewHolder.textViewName=(TextView) convertView.findViewById(R.id.textView_name);
            viewHolder.textViewNumbberPhone=(TextView) convertView.findViewById(R.id.textView_number_phone);
            viewHolder.buttonUpdate=(Button) convertView.findViewById(R.id.button_update);
            viewHolder.buttonDelete=(Button) convertView.findViewById(R.id.button_delete);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
           final PhoneBook phoneBook=phoneBookList.get(position);
        viewHolder.textViewNumbberPhone.setText(phoneBook.getmNumberPhone());
        viewHolder.textViewName.setText(phoneBook.getmName());
        viewHolder.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 context.changeButton();
                context.eventButtonEdit(position);
            }
        });
        viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.eventButtonDelete(phoneBook.getmID(),position);
            }
        });
        return convertView;
    }

    private class ViewHolder{
        private TextView textViewName;
        private TextView textViewNumbberPhone;
        private Button buttonUpdate,buttonDelete;
    }
}
