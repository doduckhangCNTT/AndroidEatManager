package com.example.eatproductmanager.Adapter;

import static com.example.eatproductmanager.Common.Common.categoriesCommon;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eatproductmanager.Domain.CategoryDomain;
import com.example.eatproductmanager.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends FirebaseRecyclerAdapter<CategoryDomain, CategoryAdapter.categoryViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    Context contextFood;
    public CategoryAdapter(@NonNull FirebaseRecyclerOptions<CategoryDomain> options, Context context) {
        super(options);

        contextFood = context;
    }

    private int positionCategoryItem;

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
    protected void onBindViewHolder(@NonNull categoryViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull CategoryDomain model) {
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

        holder.imgMenuCategoryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CategoryViewHolder", "Position: " + position);
                positionCategoryItem = position;

                holder.showPopupMenu(v);
            }
        });

        // === Cập nhật vào danh sách chung ===
        // categoriesCommon.add(model);
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
//            imgMenuCategoryItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
//                case R.id.imgMenuCategoryItem:
//                    showPopupMenu(v);
//                    break;
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
                    Log.d(TAG, "Test: " + categories.get(getAdapterPosition()).holder.txtNameCategoryItem.getText());

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
                    name.setText(categories.get(getAdapterPosition()).categoryDomain.getName());
                    image.setText(categories.get(getAdapterPosition()).categoryDomain.getImage());

                    Button updateCategoryItem = (Button) view.findViewById(R.id.btnUpdateCategoryItem);

                    updateCategoryItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Map<String, Object> map = new HashMap<>();
                            map.put("name", name.getText().toString());
                            map.put("image", image.getText().toString());

                            FirebaseDatabase.getInstance().getReference().child("Category")
                                    .child(getRef(positionCategoryItem).getKey())
                                    .updateChildren(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(view.getContext(), "Data Update Successfully", Toast.LENGTH_SHORT).show();
                                            dialogPlusCategory.dismiss(); // -> Dong dialogsPlus
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(view.getContext(), "Error while updating", Toast.LENGTH_SHORT).show();
                                            dialogPlusCategory.dismiss(); // -> Dong dialogsPlus
                                        }
                                    });

                        }
                    });

                    dialogPlusCategory.show();
                    return true;
                case R.id.deleteCategoryItem:
                    Log.d(TAG, "Delete Category");
                    AlertDialog.Builder builder = new AlertDialog.Builder(contextFood);
                    builder.setTitle("Are you sure");
                    builder.setMessage("Deleted data can't be undo");

                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Thuc hien xoa du lieu tren Firebase
                            FirebaseDatabase.getInstance().getReference().child("Category")
                                    .child(getRef(positionCategoryItem).getKey()).removeValue();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(contextFood, "Cancelled", Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.show();
                    return true;
                default:
                    return false;
            }
        }
    }
}
