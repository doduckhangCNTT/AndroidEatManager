package com.example.eatproductmanager.Adapter;

import static com.example.eatproductmanager.Common.Common.categoriesCommon;

import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
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

import java.util.ArrayList;

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

    class CategoryHolderTest {
        categoryViewHolder holder;
        CategoryDomain categoryDomain;

        public CategoryHolderTest() {
        }

        public CategoryHolderTest(categoryViewHolder holder, CategoryDomain category) {
            this.holder = holder;
            this.categoryDomain = category;
        }

        public categoryViewHolder getHolder() {
            return holder;
        }

        public void setHolder(categoryViewHolder holder) {
            this.holder = holder;
        }

        public CategoryDomain getCategory() {
            return categoryDomain;
        }

        public void setCategory(CategoryDomain category) {
            this.categoryDomain = category;
        }
    }
    ArrayList<CategoryHolderTest> categories = new ArrayList<>();

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

        categories.add(new CategoryHolderTest(
            holder,
            model
        ));

        // === Cập nhật vào danh sách chung ===
        categoriesCommon.add(model);

    }

    @NonNull
    @Override
    public categoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // === Tao doi tuong cho category_item ===
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new categoryViewHolder(view);
    }

    class categoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        CircleImageView imgCategoryItem;
        TextView txtNameCategoryItem;
        ImageView imgMenuCategoryItem;

        private static final String TAG = "CategoryViewHolder";

        public categoryViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCategoryItem = (CircleImageView) itemView.findViewById(R.id.imgCategoryItem);
            txtNameCategoryItem = (TextView) itemView.findViewById(R.id.txtNameCategoryItem);
            imgMenuCategoryItem = (ImageView) itemView.findViewById(R.id.imgMenuCategoryItem);

            // === Xử lí trên option Menu ===
            imgMenuCategoryItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgMenuCategoryItem:
                    showPopupMenu(v);
                    break;
            }
        }

        private void showPopupMenu(View view) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.category_menu_item);

            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        // === Xu li tren tung item cua menu ===
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch(item.getItemId()) {
                case R.id.editCategoryItem:
                    Log.d(TAG, "Edit Category" + categories.get(getAdapterPosition()).holder.txtNameCategoryItem.getText());

                    final DialogPlus dialogPlusCategory = DialogPlus.newDialog(categories.get(getAdapterPosition()).holder.imgCategoryItem.getContext())
                            .setContentHolder(new ViewHolder(R.layout.popup_create_category))
                            .setExpanded(true, 1200).create();

                    // === Tạo đối tượng cho view dialogplust ===
                    View view = dialogPlusCategory.getHolderView();
                    // === Tham chiếu các thành phần trong dialogplus ===
                    EditText name = view.findViewById(R.id.edtNameCategoryItem);
                    EditText image = view.findViewById(R.id.edtImageCategoryItem);
                    Button btnCreate = view.findViewById(R.id.btnCreateCategoryItem);

                    // === Khong cho phep thuc hien create category ===
                    btnCreate.setEnabled(false);

                    // === Đặt giá trị vào dialogplus ===
                    name.setText(categories.get(getAdapterPosition()).holder.txtNameCategoryItem.getText());
                    image.setText(categories.get(getAdapterPosition()).categoryDomain.getImage());

                    dialogPlusCategory.show();
                    return true;
                case R.id.deleteCategoryItem:
                    Log.d(TAG, "Delete Category");
                    return true;
                default:
                    return false;
            }
        }
    }
}
