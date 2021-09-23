package etn.app.danghoc.webservicetolisview_recycelerview;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterStudent extends RecyclerView.Adapter<AdapterStudent.viewHolder> {
    private List<ItemStudent> itemStudentList;
    public static int positionEdit=-1;
    MainActivity mainActivity=new MainActivity();
   String urlDelete="http://192.168.1.9:8080/androidwebservice/deleteData.php";

    public AdapterStudent(List<ItemStudent> itemStudentList) {
        this.itemStudentList = itemStudentList;
    }



    @NonNull
    @Override
    public AdapterStudent.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sinhvien, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterStudent.viewHolder holder, final int position) {
        final ItemStudent itemStudent = itemStudentList.get(position);
        holder.tvName.setText(itemStudent.getName());
        holder.tvAddress.setText(itemStudent.getAddress());
        holder.tvBirth.setText("nam sinh : "+itemStudent.getBirth());

        holder.imgbtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),ActivityUpdate.class);
                intent.putExtra("dataSinhVien",itemStudent);
                v.getContext().startActivity(intent);
                positionEdit=position;
            }
        });

        holder.imgbtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.studentList.remove(itemStudent);
                MainActivity.adapter.notifyDataSetChanged();
                mainActivity.delete(urlDelete,itemStudent.getId(),v.getContext());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemStudentList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvBirth;
        ImageButton imgbtEdit, imgbtDelete;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.text_view_name);
            tvAddress = (TextView) itemView.findViewById(R.id.text_view_address);
            tvBirth = (TextView) itemView.findViewById(R.id.text_view_birth);
           imgbtDelete=(ImageButton)itemView.findViewById(R.id.imagebutton_delete);
           imgbtEdit=(ImageButton)itemView.findViewById(R.id.imagebutton_edit);
        }
    }
}
