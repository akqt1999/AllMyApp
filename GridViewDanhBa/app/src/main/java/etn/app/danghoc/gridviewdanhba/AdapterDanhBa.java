package etn.app.danghoc.gridviewdanhba;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import java.util.List;

public class AdapterDanhBa extends ArrayAdapter<DanhBa> {
    Context context;
    int resource;
    List<DanhBa> danhBas;

    public AdapterDanhBa(@NonNull Context context, int resource, List<DanhBa> object) {
        super(context, resource, object);
        this.context = context;
        this.resource = resource;
        this.danhBas = object;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
            holder=new ViewHolder();
            holder.txtName=convertView.findViewById(R.id.txtName);
            holder.txtSDT=convertView.findViewById(R.id.txtSDT);
            holder.btnCall=convertView.findViewById(R.id.btnCall);
            holder.btnSend=convertView.findViewById(R.id.btnSms);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        final DanhBa danhBa=danhBas.get(position);
        holder.txtName.setText(danhBa.getName());
        holder.txtSDT.setText(danhBa.getSdt());
        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+danhBa.getSdt().toString()));
                if(ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions((Activity) context,new String[]{
                            Manifest.permission.CALL_PHONE},1);
                    return;

                }
                context.startActivity(callIntent);
            }
        });
        holder.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent=new Intent(Intent.ACTION_SENDTO,
                        Uri.parse("smsto:"+danhBa.getSdt()));
                context.startActivity(smsIntent);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView txtName;
        private TextView txtSDT;
        private ImageButton btnCall,btnSend;
    }
}
