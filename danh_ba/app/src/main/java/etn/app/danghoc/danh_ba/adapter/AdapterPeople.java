package etn.app.danghoc.danh_ba.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

import etn.app.danghoc.danh_ba.R;
import etn.app.danghoc.danh_ba.model.info_people;

public class AdapterPeople extends ArrayAdapter<info_people> {
   private Context context;
   private  int resource;
  List<info_people> listInfoPeople;

    public AdapterPeople(@NonNull Context context, int resource, @NonNull List<info_people> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.listInfoPeople=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
// mong
     ViewHoder viewHoder;
        if(convertView==null){
          convertView= LayoutInflater.from(context).inflate(R.layout.ten_sdt,parent, false);
          viewHoder=new ViewHoder();
           viewHoder.textViewName=(TextView) convertView.findViewById(R.id.textview_name);
           viewHoder.textViewNumberPhone=(TextView) convertView.findViewById(R.id.textview_number_phone);
           viewHoder.textViewSex=(TextView) convertView.findViewById(R.id.textview_sex);

           convertView.setTag(viewHoder);

        }
        else {
            viewHoder=(ViewHoder) convertView.getTag();
        }
        info_people info_people =listInfoPeople.get(position);
       viewHoder.textViewNumberPhone.setText(info_people.getNumberPhone());
       viewHoder.textViewName.setText(info_people.getName());

       if(info_people.isBoy()){
             viewHoder.textViewSex.setText("Boy");
       }
       else{
           viewHoder.textViewSex.setText("Girl");
       }
        return convertView;

    }


    public class ViewHoder {
        TextView textViewName;
        TextView textViewNumberPhone;
        TextView textViewSex;
    }


}

