package com.example.eatproductmanager.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eatproductmanager.Domain.FoodDomain;
import com.example.eatproductmanager.FoodDetailActivity;
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

public class FoodAdapter extends FirebaseRecyclerAdapter<FoodDomain, FoodAdapter.foodViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    // === Truyen content activity ===
    Context mcontext;

    private int positionFoodItem;

    public static ArrayList<Integer> positionsFoodChecked = new ArrayList<Integer>();

    public FoodAdapter(@NonNull FirebaseRecyclerOptions<FoodDomain> options, Context context) {
        super(options);
        mcontext = context;
    }

    class FoodHolderTest {
        foodViewHolder holder;
        FoodDomain foodDomain;

        public FoodHolderTest() {
        }

        public FoodHolderTest(foodViewHolder holder, FoodDomain foodDomain) {
            this.holder = holder;
            this.foodDomain = foodDomain;
        }

        public foodViewHolder getHolder() {
            return holder;
        }

        public void setHolder(foodViewHolder holder) {
            this.holder = holder;
        }

        public FoodDomain getFoodDomain() {
            return foodDomain;
        }

        public void setFoodDomain(FoodDomain foodDomain) {
            this.foodDomain = foodDomain;
        }
    }
    ArrayList<FoodHolderTest> foods = new ArrayList<>();

    @Override
    protected void onBindViewHolder(@NonNull foodViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull FoodDomain model) {
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
        holder.txtPriceFoodItem.setText(model.getPrice() + "");

        foods.add(new FoodHolderTest (
            holder,
            model
        ));

        holder.imgOptionsFoodItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionFoodItem = position;
                holder.showPopupMenu(v);
            }
        });

        holder.cbFoodItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(holder.cbFoodItem.isChecked()) {
                    positionsFoodChecked.add(position);
                    Log.d("Checked", "checked: " + positionsFoodChecked.toString());
                } else {
                    ArrayList<Integer> positionsRestChecked = new ArrayList<Integer>();
                    for(int p : positionsFoodChecked) {
                        if(p != position) {
                            positionsRestChecked.add(p);
                        }
                    }
                    positionsFoodChecked = positionsRestChecked;
                    Log.d("Checked", "not checked "  + positionsFoodChecked.toString());
                }
            }
        });
    }

    @NonNull
    @Override
    public foodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        return new FoodAdapter.foodViewHolder(view);
    }

    class foodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        CircleImageView imgFoodItem;
        TextView txtNameFoodItem;
        TextView txtDesFoodItem;
        TextView txtPriceFoodItem;
        ImageView imgOptionsFoodItem;
        CheckBox cbFoodItem;

        private static final String TAG = "FoodViewHolder";

        public foodViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFoodItem = (CircleImageView) itemView.findViewById(R.id.imgFoodItem);
            txtNameFoodItem = (TextView) itemView.findViewById(R.id.txtNameFood);
            txtDesFoodItem = (TextView) itemView.findViewById(R.id.txtDesFoodItem);
            txtPriceFoodItem = (TextView) itemView.findViewById(R.id.txtFoodPrice);
            imgOptionsFoodItem = (ImageView)itemView.findViewById(R.id.imgOptionsFoodItem);
            cbFoodItem = (CheckBox) itemView.findViewById(R.id.cbFoodItem);
            // === Xử lí trên option Menu ===
//            imgOptionsFoodItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
//                case R.id.imgOptionsFoodItem:
//                    showPopupMenu(v);
//                    break;
            }
        }

        private void showPopupMenu(View view) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.food_menu_item);

            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch(item.getItemId()) {
                case R.id.edtMenuFoodItem:
                    final DialogPlus dialogPlusFood = DialogPlus.newDialog(foods.get(getAdapterPosition()).holder.imgOptionsFoodItem.getContext())
                            .setContentHolder(new ViewHolder(R.layout.popup_create_food))
                            .setExpanded(true, 1200).create();

                    // === Tạo đối tượng cho view dialogplust ===
                    View view = dialogPlusFood.getHolderView();

                    // === Tham chiếu các thành phần trong dialogplus ===
                    EditText name = view.findViewById(R.id.edtNameFoodItem);
                    EditText price = view.findViewById(R.id.edtPriceFoodItem);
                    EditText description = view.findViewById(R.id.edtDescriptionFoodItem);
                    EditText discount = view.findViewById(R.id.edtDiscountFoodItem);
                    EditText category = view.findViewById(R.id.edtCategoryFoodItem);
                    EditText image = view.findViewById(R.id.edtImgUrlFoodItem);

                    // === Khong cho phep thuc hien create category ===
                    Button btnCreate = view.findViewById(R.id.btnCreateFoodItem);
                    btnCreate.setEnabled(false);

                    // === Đặt giá trị vào dialogplus ===
                    name.setText(foods.get(getAdapterPosition()).foodDomain.getName());
                    price.setText(foods.get(getAdapterPosition()).foodDomain.getPrice() + "");
                    description.setText(foods.get(getAdapterPosition()).foodDomain.getDescription());
                    discount.setText(foods.get(getAdapterPosition()).foodDomain.getDiscount() + "");
                    category.setText(foods.get(getAdapterPosition()).foodDomain.getCategoryID());
                    image.setText(foods.get(getAdapterPosition()).foodDomain.getImage());

                    Button updateFoodItem = (Button) view.findViewById(R.id.btnUpdateFoodItem);
                    updateFoodItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Map<String, Object> map = new HashMap<>();
                            map.put("name", name.getText().toString());
                            map.put("image", image.getText().toString());
                            map.put("price", Integer.parseInt(price.getText().toString()));
                            map.put("description", description.getText().toString());
                            map.put("discount", Integer.parseInt(discount.getText().toString()));
                            map.put("categoryID", category.getText().toString());

                            FirebaseDatabase.getInstance().getReference().child("Food")
                                    .child(getRef(positionFoodItem).getKey())
                                    .updateChildren(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(view.getContext(), "Data Update Successfully", Toast.LENGTH_SHORT).show();
                                            dialogPlusFood.dismiss(); // -> Dong dialogsPlus
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(view.getContext(), "Error while updating", Toast.LENGTH_SHORT).show();
                                            dialogPlusFood.dismiss(); // -> Dong dialogsPlus
                                        }
                                    });
                            }
                        });

                    dialogPlusFood.show();
                    return true;
                case R.id.deleteMenuFoodItem:
                    Log.d(TAG, "Delete Category");
                    AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                    builder.setTitle("Are you sure");
                    builder.setMessage("Deleted data can't be undo");

                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Thuc hien xoa du lieu tren Firebase
                            FirebaseDatabase.getInstance().getReference().child("Food")
                                    .child(getRef(positionFoodItem).getKey()).removeValue();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(mcontext, "Cancelled", Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.show();
                    return true;
                case R.id.detailMenuFoodItem:
                    Intent intent = new Intent(mcontext, FoodDetailActivity.class);
                    Bundle b = new Bundle();

                    b.putString("NameFood", foods.get(getAdapterPosition()).foodDomain.getName());
                    b.putInt("PriceFood", foods.get(getAdapterPosition()).foodDomain.getPrice());
                    b.putString("DesFood", foods.get(getAdapterPosition()).foodDomain.getDescription());
                    b.putInt("DiscountFood", foods.get(getAdapterPosition()).foodDomain.getDiscount());
                    b.putString("CategoryIdFood", foods.get(getAdapterPosition()).foodDomain.getCategoryID());
                    b.putString("ImageFood", foods.get(getAdapterPosition()).foodDomain.getImage());
                    intent.putExtras(b);
                    mcontext.startActivity(intent);
                    return true;
                default:
                    return false;
            }
        }
    }
}
