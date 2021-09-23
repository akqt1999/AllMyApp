package etn.app.danghoc.button_with_listview.adapterr;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.List;

import etn.app.danghoc.button_with_listview.R;
import etn.app.danghoc.button_with_listview.model.info_fruit;

public class Adapter_fruit extends ArrayAdapter <info_fruit> {

  Context context;
  int resousource;
List<info_fruit> info_fruitList;
    public Adapter_fruit(@NonNull Context context, int resource, @NonNull List<info_fruit> objects) {
        super(context, resource, objects);

        this.context=context;
        this.resousource=resource;
        this.info_fruitList=objects;
    }

    @NonNull
    @Override // ghi tắt view get View tab
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHoder viewHoder;

        if(convertView==null){
             convertView= LayoutInflater.from(context).inflate(R.layout.layout_fruit,parent,false);
            viewHoder=new ViewHoder();
            viewHoder.buttonAdd=(Button) convertView.findViewById(R.id.button_add);
            viewHoder.buttonMinus=(Button) convertView.findViewById(R.id.button_minus);
            viewHoder.textViewName=(TextView) convertView.findViewById(R.id.textview_name);
            viewHoder.textViewNumber=(TextView) convertView.findViewById(R.id.textview_number);
            convertView.setTag(viewHoder);

        }
        else{
           viewHoder=(ViewHoder) convertView.getTag();
        }

        final info_fruit info_fruits=info_fruitList.get(position);
          viewHoder.textViewNumber.setText(info_fruits.getNumber()+"");
          viewHoder.textViewName.setText(info_fruits.getNameFruit());

          // sự kiện cho button
        // nhạc hay

        viewHoder.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override // nếu nhấn vào thì số lượng tăng lên
            public void onClick(View v) {
                   int amount=Integer.parseInt( viewHoder.textViewNumber.getText().toString());
                   amount=amount+1;
                   viewHoder.textViewNumber.setText(amount+"");
            }
        });

        // minus -- trừ luc t
        viewHoder.buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount= Integer.parseInt(viewHoder.textViewNumber.getText().toString());
                amount=amount-1;
                viewHoder.textViewNumber.setText(amount+"");
            }
        });

        return convertView;
    }

    public class ViewHoder{
         public TextView textViewName,textViewNumber;

         public Button buttonAdd,buttonMinus;
    }
}



