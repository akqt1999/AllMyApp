package etn.app.danghoc.note_appp1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import etn.app.danghoc.note_appp1.EventListenerClick;
import etn.app.danghoc.note_appp1.R;
import etn.app.danghoc.note_appp1.model.ItemTitle;

public class AdapterNote extends ArrayAdapter<ItemTitle> implements Filterable {
    Context context;
    int resource;
    List<ItemTitle> itemTitles;
    public List<ItemTitle> itemTitlesFull;
    public boolean multiCheckMode = false;
    public static int POSITION_INTENT=-1;
   EventListenerClick listener;
    public AdapterNote(@NonNull Context context, int resource, @NonNull List<ItemTitle> itemTitles) {
        super(context, resource, itemTitles);
        this.context = context;
        this.resource = resource;
        this.itemTitles = itemTitles;
        this.itemTitlesFull = new ArrayList<ItemTitle>();
        this.itemTitlesFull.addAll(itemTitles);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_title, parent, false);

            holder.textViewTitle = (TextView) convertView.findViewById(R.id.textview_title);
            holder.textViewToday = (TextView) convertView.findViewById(R.id.textview_today);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_d);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ItemTitle itemTitle = itemTitles.get(position);
        holder.textViewToday.setText(itemTitle.getmToday());
        holder.textViewTitle.setText(itemTitle.getmTitle());

        // event isCheckBox
            if (multiCheckMode) {
                holder.checkBox.setVisibility(View.VISIBLE);
                holder.checkBox.setChecked(itemTitle.getCheck());
            } else {
                holder.checkBox.setVisibility(View.INVISIBLE);
                holder.checkBox.setChecked(false);
            }



        // envent click item listview
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                POSITION_INTENT=position;
                if(multiCheckMode){
                    itemTitle.setMCheck(!itemTitle.getCheck()); // inverse selected
                    holder.checkBox.setChecked(itemTitle.getCheck());
                    if (holder.checkBox.isChecked()) {
                        holder.checkBox.setBackgroundColor(Color.parseColor("#d7ed0e"));
                        holder.textViewTitle.setBackgroundColor(Color.parseColor("#d7ed0e")); // màu đậm
                        holder.textViewToday.setBackgroundColor(Color.parseColor("#d7ed0e"));
                        // true is delete
//
                    } else {
                        // tro ve nhu cu
                        holder.checkBox.setBackgroundColor(Color.parseColor("#D3C0D6"));
                        holder.textViewTitle.setBackgroundColor(Color.parseColor("#D3C0D6")); // màu nhạc
                        holder.textViewToday.setBackgroundColor(Color.parseColor("#D3C0D6"));
                    }
                }
                listener.onClick(itemTitle);
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(itemTitle);

                    holder.checkBox.setBackgroundColor(Color.parseColor("#d7ed0e"));
                    holder.textViewTitle.setBackgroundColor(Color.parseColor("#d7ed0e")); // màu đậm
                    holder.textViewToday.setBackgroundColor(Color.parseColor("#d7ed0e"));
                    // true is delete
                return false;
            }
        });

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    itemTitle.setMCheck(true);
                    holder.checkBox.setBackgroundColor(Color.parseColor("#d7ed0e"));
                    holder.textViewTitle.setBackgroundColor(Color.parseColor("#d7ed0e")); // màu đậm
                    holder.textViewToday.setBackgroundColor(Color.parseColor("#d7ed0e"));
                   // true is delete
//
                } else {
                    itemTitle.setMCheck(false);
                    // tro ve nhu cu
                    holder.checkBox.setBackgroundColor(Color.parseColor("#D3C0D6"));
                    holder.textViewTitle.setBackgroundColor(Color.parseColor("#D3C0D6")); // màu nhạc
                    holder.textViewToday.setBackgroundColor(Color.parseColor("#D3C0D6"));

                }
            }

        });



        if (!multiCheckMode) { //
            holder.checkBox.setChecked(false);
            holder.checkBox.setBackgroundColor(Color.parseColor("#D3C0D6"));
            holder.textViewTitle.setBackgroundColor(Color.parseColor("#D3C0D6")); // màu nhạc
            holder.textViewToday.setBackgroundColor(Color.parseColor("#D3C0D6"));
            notifyDataSetChanged();
        }
        // event check


        return convertView;
    }//link https://demonuts.com/listview-searchview/    https://www.javatpoint.com/java-string-tolowercase

    public class ViewHolder {
        TextView textViewTitle;
        TextView textViewToday;
        CheckBox checkBox;
    }

    public void setMultiCheckMode(boolean multiCheckMode) {
        this.multiCheckMode = multiCheckMode;
        notifyDataSetChanged();
    }

    public void setListener(EventListenerClick listener) {
        this.listener = listener;
    }

    public int getPositionIntent(){
        return POSITION_INTENT;
    }
    //create filter
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault()).trim();
        itemTitles.clear();
        if (charText.length() == 0) {
            itemTitles.addAll(itemTitlesFull);
        } else {
            for (ItemTitle cc : itemTitlesFull) {
                if (cc.getmTitle().toLowerCase().trim().contains(charText)) {
                    itemTitles.add(cc);
                }
            }
        }
        notifyDataSetChanged();
    }

// back  in android


// delete


}

/*

 */
// https://gist.github.com/fjfish/3024308           https://codinginflow.com/tutorials/android/searchview-recyclerview  https://stackoverflow.com/questions/24769257/custom-listview-adapter-with-filter-android






