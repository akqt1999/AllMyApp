package etn.app.danghoc.demngaylenlich.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import etn.app.danghoc.demngaylenlich.ActivitySplash;
import etn.app.danghoc.demngaylenlich.Commonn.Commonn;
import etn.app.danghoc.demngaylenlich.Model.User;
import etn.app.danghoc.demngaylenlich.R;
import etn.app.danghoc.demngaylenlich.ui.home.HomeFragment;

public class AdapterNote extends RecyclerView.Adapter<AdapterNote.viewHolder> {
    Context context;
    List<User> userList;
    IOnCallBack listener;
    public boolean multiCheckMode = false;


    public AdapterNote(List<User> list, IOnCallBack listener) {
        this.listener = listener;
        this.userList = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recy_note, parent, false);


        return new viewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        User user = userList.get(position);

        holder.txtContent.setText(user.getContent());
        holder.txtDayStart.setText("day start : " + user.getDayStart());
        holder.txtDaySuccess.setText("day success : " + user.getDaySuccess());

        int day = 0;
        int month = 0;
        int year;
        long dayProcess = -1;

        Calendar calNow = Calendar.getInstance();

        day = calNow.get(Calendar.DAY_OF_MONTH);
        month = calNow.get(Calendar.MONTH) + 1;
        year = calNow.get(Calendar.YEAR);

        String dateNow = day + "/" + month + "/" + year;

        dayProcess = getDifferenceDays(stringToDate(dateNow), stringToDate(user.getDaySuccess()));


        if (dayProcess > 0)
            holder.txtProcess.setText(dayProcess + " day left");
        else holder.txtProcess.setText("done");

        if (!multiCheckMode) {
            holder.txtProcess.setBackgroundColor(Color.parseColor("#FFFFFF"));//mau nhac
            holder.txtContent.setBackgroundColor(Color.parseColor("#FFFFFF"));
            //    notifyDataSetChanged();
        }


        holder.itemView.setOnClickListener(v -> {
            Log.d("longg", "onClick_true_" + position);
            listener.itemOnClick(position);
            if (HomeFragment.isChooseItem) {

                if (Commonn.userList.get(position).isSelect()) {
                    Commonn.userList.get(position).setSelect(false);
                    holder.txtProcess.setBackgroundColor(Color.parseColor("#FFFFFF"));//mau nhac
                    holder.txtContent.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    HomeFragment.userListChoose.remove(Commonn.userList.get(position));

                    Log.d("chon", "_duocCHon");
                } else if (!Commonn.userList.get(position).isSelect()) {
                    Commonn.userList.get(position).setSelect(true);
                    HomeFragment.userListChoose.add(Commonn.userList.get(position));
                    holder.txtProcess.setBackgroundColor(Color.parseColor("#CB82CD"));//mau dam
                    holder.txtContent.setBackgroundColor(Color.parseColor("#CB82CD"));

                    Log.d("chon", "_kduocCHon");
                }
            } else {
                ///todo........
            }
        });


        holder.itemView.setOnLongClickListener(v -> {
            Log.d("longg", "longClick_true_" + position);

            holder.txtProcess.setBackgroundColor(Color.parseColor("#CB82CD"));//mau dam
            holder.txtContent.setBackgroundColor(Color.parseColor("#CB82CD"));

//            listener.itemOnLongClick(position);
            Commonn.userList.get(position).setSelect(true);
            HomeFragment.userListChoose.add(Commonn.userList.get(position));

            HomeFragment.isChooseItem = true;
            HomeFragment.btnEdit.setVisibility(View.VISIBLE);
            HomeFragment.btnCancel.setVisibility(View.VISIBLE);
            HomeFragment.btnDelete.setVisibility(View.VISIBLE);
            multiCheckMode = true;

            return true;
        });


    }


    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    private Date stringToDate(String day) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(day);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txtContent, txtDayStart, txtDaySuccess, txtProcess;
        IOnCallBack listener;
        CardView itemRecy;

        public viewHolder(@NonNull View itemView, final IOnCallBack listener) {

            super(itemView);
            txtContent = itemView.findViewById(R.id.txtContent);
            txtDayStart = itemView.findViewById(R.id.txtDayStart);
            txtDaySuccess = itemView.findViewById(R.id.txtDaySuccess);
            txtProcess = itemView.findViewById(R.id.txtProcess);
            itemView = itemView.findViewById(R.id.item_recy);

            this.listener = listener;
            itemView.setOnClickListener(v -> {
                listener.itemOnClick(getAdapterPosition());


            });

            itemView.setOnLongClickListener(v -> {
                listener.itemOnLongClick(getAdapterPosition());
                return true;
            });

        }
    }

    public void setMultiCheckMode(boolean checkMode) {
        this.multiCheckMode = checkMode;
        notifyDataSetChanged();
    }

    public interface IOnCallBack {
        void itemOnClick(int position);

        void itemOnLongClick(int position);
    }


}
