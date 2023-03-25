package com.example.eatproductmanager.Fragment;

import static com.example.eatproductmanager.Adapter.CategoryAdapter.positionsCategoryChecked;
import static com.example.eatproductmanager.Adapter.FoodAdapter.positionsFoodChecked;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatproductmanager.Adapter.CategoryAdapter;
import com.example.eatproductmanager.Adapter.FoodAdapter;
import com.example.eatproductmanager.Domain.CategoryDomain;
import com.example.eatproductmanager.Domain.FoodDomain;
import com.example.eatproductmanager.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class FoodsFragment extends Fragment implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    RecyclerView recyclerView;
    FoodAdapter foodAdapter;
    Dialog dialog;
    Button btnUpdateFoodItem;
    Button btnDeleteFood;
    Button btnCreateFood;
    Button btnSortedFood;
    TextView txtSearchFood;
    TextView txtSearchCategoryIdFood;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_foods, container, false);

        // Tham chieu den recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.rvFoods);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        btnCreateFood = (Button) view.findViewById(R.id.btnCreateFood);
        btnDeleteFood = (Button) view.findViewById(R.id.btnDeleteFoodChoice);
        btnSortedFood = (Button) view.findViewById(R.id.btnSortedFood);
        txtSearchFood = (TextView) view.findViewById(R.id.edtSearchFood);
        txtSearchCategoryIdFood = (TextView) view.findViewById(R.id.edtSearchCategoryIdFood);

        // === Xu li tim kiem ===
        txtSearchFood.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String txtSearch = txtSearchFood.getText().toString();
                // Tìm kiếm trên Firebase
                FirebaseRecyclerOptions<FoodDomain> options = new FirebaseRecyclerOptions.Builder<FoodDomain>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Food").orderByChild("name").startAt(txtSearch).endAt(txtSearch+"~"), FoodDomain.class)
                        .build();

                foodAdapter = new FoodAdapter(options, view.getContext());
                foodAdapter.startListening();
                recyclerView.setAdapter(foodAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtSearchCategoryIdFood.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String txtSearch = txtSearchCategoryIdFood.getText().toString();
                // Tìm kiếm trên Firebase
                FirebaseRecyclerOptions<FoodDomain> options = new FirebaseRecyclerOptions.Builder<FoodDomain>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Food").orderByChild("categoryID").startAt(txtSearch).endAt(txtSearch+"~"), FoodDomain.class)
                        .build();

                foodAdapter = new FoodAdapter(options, view.getContext());
                foodAdapter.startListening();
                recyclerView.setAdapter(foodAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // === Lang nghe su kien ===
        btnCreateFood.setOnClickListener(this);
        btnDeleteFood.setOnClickListener(this);
        btnSortedFood.setOnClickListener(this);

        dialog = new Dialog(view.getContext());

        // ======== Truy xuất dữ liệu trong firebase =======
        FirebaseRecyclerOptions<FoodDomain> options = new FirebaseRecyclerOptions.Builder<FoodDomain>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Food"), FoodDomain.class).build();

        foodAdapter = new FoodAdapter(options, view.getContext());
        // Xet view cua tung gia tri vao danh sach view recycler
        recyclerView.setAdapter(foodAdapter);

        return view;
    }


    // === Xu li khi click vao button ===
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateFood:
                dialog.setContentView(R.layout.popup_create_food);
                // === khong cho phep thuc thi nut Update ===
                 btnUpdateFoodItem = (Button) dialog.findViewById(R.id.btnUpdateFoodItem);
                 btnUpdateFoodItem.setEnabled(false);

                EditText edtFoodName = (EditText) dialog.findViewById(R.id.edtNameFoodItem);
                EditText edtPriceFood = (EditText) dialog.findViewById(R.id.edtPriceFoodItem);
                EditText edtDesFood = (EditText) dialog.findViewById(R.id.edtDescriptionFoodItem);
                EditText edtDiscountFood = (EditText) dialog.findViewById(R.id.edtDiscountFoodItem);
                EditText edtImageUrlFood = (EditText) dialog.findViewById(R.id.edtImgUrlFoodItem);
                EditText edtCategoryIdFood = (EditText) dialog.findViewById(R.id.edtCategoryFoodItem);

                Button btnCreateFoodItem = (Button) dialog.findViewById(R.id.btnCreateFoodItem);

                btnCreateFoodItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", edtFoodName.getText().toString());
                        map.put("image", edtImageUrlFood.getText().toString());
                        map.put("price", Integer.parseInt(edtPriceFood.getText().toString()));
                        map.put("description", edtDesFood.getText().toString());
                        map.put("discount", Integer.parseInt(edtDiscountFood.getText().toString()));
                        map.put("categoryID", edtCategoryIdFood.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Food").push()
                                .setValue(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(v.getContext(), "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(v.getContext(), "Error while inserted", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        dialog.dismiss();
                    }
                });

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
                break;
            case R.id.btnSortedFood:
                showPopupMenu(v);
                break;
            case R.id.btnDeleteFoodChoice:
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Are you sure");
                builder.setMessage("Deleted data can't be undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Thuc hien xoa du lieu tren Firebase
                        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference("Food");

                        for(int i : positionsFoodChecked) {
                            // Specify the position of the product you want to delete
                            int position = i; // Replace 0 with the actual position

                            // Get the key of the product at the specified position
                            Query query = productsRef.orderByKey().limitToFirst(position + 1);
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String productKey = null;
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        productKey = snapshot.getKey();
                                    }

                                    // Delete the product with the retrieved key
                                    if (productKey != null) {
                                        DatabaseReference productRef = productsRef.child(productKey);
                                        productRef.removeValue();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    // Handle errors here
                                }
                            });
                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder.show();
                break;
        }
    }


    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.action_menu_food);

        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.mnuSortedNameFood:
                FirebaseRecyclerOptions<FoodDomain> options = new FirebaseRecyclerOptions.Builder<FoodDomain>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Food").orderByChild("name"), FoodDomain.class)
                        .build();

                foodAdapter = new FoodAdapter(options, view.getContext());
                foodAdapter.startListening();
                recyclerView.setAdapter(foodAdapter);

                return true;
            case R.id.mnuSortedPriceAscending:
                FirebaseRecyclerOptions<FoodDomain> optionsFoodAsc = new FirebaseRecyclerOptions.Builder<FoodDomain>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Food").orderByChild("price"), FoodDomain.class)
                        .build();

                foodAdapter = new FoodAdapter(optionsFoodAsc, view.getContext());
                foodAdapter.startListening();
                recyclerView.setAdapter(foodAdapter);
                return true;
            case R.id.mnuSortedPriceDecrease:
                FirebaseRecyclerOptions<FoodDomain> optionsFoodDec = new FirebaseRecyclerOptions.Builder<FoodDomain>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Food").orderByChild("price").limitToLast(50), FoodDomain.class)
                        .build();

                foodAdapter = new FoodAdapter(optionsFoodDec, view.getContext());
                foodAdapter.startListening();
                recyclerView.setAdapter(foodAdapter);
                return true;
            default:
                return false;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        foodAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        foodAdapter.stopListening();
    }
}