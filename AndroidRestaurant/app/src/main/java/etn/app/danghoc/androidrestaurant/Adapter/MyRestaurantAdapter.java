package etn.app.danghoc.androidrestaurant.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import etn.app.danghoc.androidrestaurant.Interface.IOnRecycleViewClickListener;
import etn.app.danghoc.androidrestaurant.Model.Restaurant;
import etn.app.danghoc.androidrestaurant.R;

public class MyRestaurantAdapter extends RecyclerView.Adapter<MyRestaurantAdapter.MyViewHolder> {

    private List<Restaurant>restaurantList;
    Context context;

    public MyRestaurantAdapter( Context context,List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_restaurant,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        Picasso.get().load(restaurantList.get(position).getImage()).into(holder.img_restaurant);
        holder.txt_restaurant_address.setText(restaurantList.get(position).getAddress());
        holder.txt_restaurant_name.setText(restaurantList.get(position).getName());

        holder.setListener((view, position1) -> {
            Toast.makeText(context, restaurantList.get(position1).getName(), Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.txt_restaurant_name)
        TextView txt_restaurant_name;
        @BindView(R.id.txt_restaurant_address)
        TextView txt_restaurant_address;

        @BindView(R.id.img_restaurant)
        ImageView img_restaurant;

        Unbinder unbinder;

        IOnRecycleViewClickListener listener;

        public void setListener(IOnRecycleViewClickListener listener) {
            this.listener = listener;
        }

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            unbinder= ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());
        }
    }
}
