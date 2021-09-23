package etn.app.danghoc.parsejson;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterTiGia extends ArrayAdapter<TyGia> {
    Activity context; int resource; List<TyGia> objects;
    public AdapterTiGia(Activity context, int resource, List<TyGia> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
        this.resource = resource;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater(); View item = inflater.inflate(this.resource, null);
        TyGia tygia = this.objects.get(position);
        ImageView imgHinh = (ImageView) item.findViewById(R.id.img);
        TextView txtType = (TextView) item.findViewById(R.id.txtUsd); TextView txtMuaTM = (TextView) item.findViewById(R.id.txtMuaTm);
        TextView txtBanTM = (TextView) item.findViewById(R.id.txtBanTm);
        TextView txtMuaCK = (TextView) item.findViewById(R.id.txtMuaCk);
        TextView txtBanCK = (TextView) item.findViewById(R.id.txtBanCk); imgHinh.setImageBitmap(tygia.getBitmap()); txtType.setText(tygia.getType()); txtMuaTM.setText(tygia.getMuatienmat()); txtBanTM.setText(tygia.getBantuenmat()); txtMuaCK.setText(tygia.getMuack()); txtBanCK.setText(tygia.getBanck());
        return item;}


}
