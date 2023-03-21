package com.example.eatproductmanager.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eatproductmanager.Domain.CategoryDomain;
import com.example.eatproductmanager.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryHomeAdapter  extends FirebaseRecyclerAdapter<CategoryDomain, CategoryHomeAdapter.categoryViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CategoryHomeAdapter(@NonNull FirebaseRecyclerOptions<CategoryDomain> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CategoryHomeAdapter.categoryViewHolder holder, int position, @NonNull CategoryDomain model) {
        // Xet image
        Glide.with(holder.imgCategoryItem.getContext())
                .load(model.getImage())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.imgCategoryItem);

        // Xet du lieu den tung thanh phan trong view "main_item"
        holder.txtNameCategoryItem.setText(model.getName());
    }

    @NonNull
    @Override
    public CategoryHomeAdapter.categoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // === Tao doi tuong cho category_item ===
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_home_item, parent, false);
        return new CategoryHomeAdapter.categoryViewHolder(view);
    }

    class categoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCategoryItem;
        TextView txtNameCategoryItem;

        public categoryViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCategoryItem = (ImageView) itemView.findViewById(R.id.imgCategoryHome);
            txtNameCategoryItem = (TextView) itemView.findViewById(R.id.txtNameCategoryHome);
        }
    }
}
