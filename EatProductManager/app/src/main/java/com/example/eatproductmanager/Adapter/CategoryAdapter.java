package com.example.eatproductmanager.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eatproductmanager.Domain.CategoryDomain;
import com.example.eatproductmanager.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends FirebaseRecyclerAdapter<CategoryDomain, CategoryAdapter.categoryViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CategoryAdapter(@NonNull FirebaseRecyclerOptions<CategoryDomain> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull categoryViewHolder holder, int position, @NonNull CategoryDomain model) {
        // Xet image
        Glide.with(holder.imgCategoryItem.getContext())
                .load(model.getImage())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.imgCategoryItem);

        // Xet du lieu den tung thanh phan trong view "main_item"
        holder.txtNameCategoryItem.setText(model.getName());
//
//        holder.btnCreateCategoryItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final DialogPlus dialogPlusCategory = DialogPlus.newDialog(holder.imgCategoryItem.getContext())
//                        .setContentHolder(new ViewHolder(R.layout.popup_category))
//                        .setExpanded(true, 1200).create();
//
//                dialogPlusCategory.show();
//            }
//        });
    }

    @NonNull
    @Override
    public categoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // === Tao doi tuong cho category_item ===
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new categoryViewHolder(view);
    }

    class categoryViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgCategoryItem;
        TextView txtNameCategoryItem;
//        Button btnCreateCategoryItem;

        public categoryViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCategoryItem = (CircleImageView) itemView.findViewById(R.id.imgCategoryItem);
            txtNameCategoryItem = (TextView) itemView.findViewById(R.id.txtNamFood);
//            btnCreateCategoryItem = (Button) itemView.findViewById(R.id.btnCreateCategory);
        }
    }
}
