package com.example.eatproductmanager.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eatproductmanager.Domain.FoodDomain;
import com.example.eatproductmanager.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class FoodAdapter extends FirebaseRecyclerAdapter<FoodDomain, FoodAdapter.foodViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FoodAdapter(@NonNull FirebaseRecyclerOptions<FoodDomain> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull foodViewHolder holder, int position, @NonNull FoodDomain model) {
        // Xet image
        Glide.with(holder.imgFoodItem.getContext())
                .load(model.getImage())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.imgFoodItem);

        // Xet du lieu den tung thanh phan trong view "main_item"
        holder.txtNameFoodItem.setText(model.getName());
        holder.txtDesFoodItem.setText(model.getDescription());
        holder.txtPriceFoodItem.setText(model.getPrice());
    }

    @NonNull
    @Override
    public foodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        return new FoodAdapter.foodViewHolder(view);
    }

    class foodViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgFoodItem;
        TextView txtNameFoodItem;
        TextView txtDesFoodItem;
        TextView txtPriceFoodItem;

        public foodViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFoodItem = (CircleImageView) itemView.findViewById(R.id.imgFoodItem);
            txtNameFoodItem = (TextView) itemView.findViewById(R.id.txtNameFood);
            txtDesFoodItem = (TextView) itemView.findViewById(R.id.txtDesFoodItem);
            txtPriceFoodItem = (TextView) itemView.findViewById(R.id.txtFoodPrice);
        }
    }
}
