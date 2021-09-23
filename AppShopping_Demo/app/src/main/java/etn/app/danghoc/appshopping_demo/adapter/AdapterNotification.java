package etn.app.danghoc.appshopping_demo.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import etn.app.danghoc.appshopping_demo.CheckLogin;
import etn.app.danghoc.appshopping_demo.Notification2;
import etn.app.danghoc.appshopping_demo.R;

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.viewHoder> {
    List<Notification2>notificationList;
    private Context context;
    CheckLogin checkLogin;
    public AdapterNotification(List<Notification2> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context=context;
    }


    @NonNull
    @Override
    public viewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notication,parent,false);
       context=parent.getContext();
        return new viewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHoder holder, int position) {
        Notification2 notification=notificationList.get(position);
        String contentNotification=""; /* 1 is "Nguyen Xuan Tri muon mua don hang Bo Rau Muong cua ban"
                                           2 is "Nguyen Van Ngu da xac nhan don hang cua ban"
                                         */
        String notificationText="";


        checkLogin=new CheckLogin(context);

        if(checkLogin.getNameUser().equalsIgnoreCase(notification.getIdNguoiBan())){
            contentNotification="muon mua don hang";
             notificationText=notification.getIdNguoiMu()+" "+contentNotification+" "+notification.getTenSp()+" cua ban";
            Toast.makeText(context, notificationList.size()+"", Toast.LENGTH_SHORT).show();
            SpannableString spannable=new SpannableString(notificationText);
            StyleSpan boldNameUser=new StyleSpan(Typeface.BOLD);
            StyleSpan boldNameProduct=new StyleSpan(Typeface.BOLD);
            spannable.setSpan(boldNameUser,0,notification.getIdNguoiMu().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(boldNameProduct,notification.getIdNguoiMu().length()+contentNotification.length()+1,
                    notification.getIdNguoiMu().length()+contentNotification.length()+notification.getTenSp().length()+2,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.txtNotification.setText(spannable);

        }else{
            if(notification.getIdLoaiContent().equalsIgnoreCase("2")){
                contentNotification="da xac nhan don hang";
                notificationText=notification.getIdNguoiBan()+" "+contentNotification+" "+notification.getTenSp()+" cua ban";
                holder.txtNotification.setText(notification.getIdNguoiBan());
                Toast.makeText(context, notificationList.size()+"", Toast.LENGTH_SHORT).show();
                SpannableString spannable=new SpannableString(notificationText);
                StyleSpan boldNameUser=new StyleSpan(Typeface.BOLD);
                StyleSpan boldNameProduct=new StyleSpan(Typeface.BOLD);
                spannable.setSpan(boldNameUser,0,notification.getIdNguoiBan().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(boldNameProduct,notification.getIdNguoiBan().length()+contentNotification.length()+1,
                        notification.getIdNguoiBan().length()+contentNotification.length()+notification.getTenSp().length()+2,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.txtNotification.setText(spannable);

            }
        }



    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class viewHoder extends RecyclerView.ViewHolder {
        TextView txtNotification;
        public viewHoder(@NonNull View itemView) {
            super(itemView);
            txtNotification=itemView.findViewById(R.id.txt_content_notificaion);
        }
    }

}
