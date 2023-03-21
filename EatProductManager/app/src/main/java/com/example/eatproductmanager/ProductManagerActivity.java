package com.example.eatproductmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatproductmanager.Adapter.CategoryAdapter;
import com.example.eatproductmanager.Adapter.CategoryHomeAdapter;
import com.example.eatproductmanager.Adapter.FoodHomeAdapter;
import com.example.eatproductmanager.Common.Common;
import com.example.eatproductmanager.Common.Constanst;
import com.example.eatproductmanager.Domain.CategoryDomain;
import com.example.eatproductmanager.Domain.FoodDomain;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ProductManagerActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewCategoryList;
    CategoryHomeAdapter categoryHomeAdapter;
    private RecyclerView recyclerViewFoodList;
    FoodHomeAdapter foodHomeAdapter;

    LinearLayout btnOptions;
    TextView txtAdminName;
    TextView txtRole;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_manager);

        // ==== Tham Chieu ====
        findViewById(R.id.btnOptions).setOnClickListener(this);
        txtAdminName = (TextView) findViewById(R.id.txtAdminName);
        txtRole = (TextView) findViewById(R.id.txtRole);

        txtAdminName.setText(Common.currentUser.getName());
        txtRole.setText(Common.currentUser.getRole());

        // === Xử lí đẩy dữ liệu từ view_item vào recylerview trong page Home ===
        recyclerViewCategory();
        recyclerViewFood();

    }

    private void recyclerViewCategory() {
        recyclerViewCategoryList = findViewById(R.id.rvCategoriesHome);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        // ======== Truy xuất dữ liệu trong firebase =======
        FirebaseRecyclerOptions<CategoryDomain> options = new FirebaseRecyclerOptions.Builder<CategoryDomain>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Category").limitToFirst(Constanst.LIMIT_CATEGORIES_ON_HOME), CategoryDomain.class).build();

        categoryHomeAdapter = new CategoryHomeAdapter(options);
        // Xet view cua tung gia tri vao danh sach view recycler
        recyclerViewCategoryList.setAdapter(categoryHomeAdapter);
    }

    private void recyclerViewFood() {
        recyclerViewFoodList = findViewById(R.id.rvFoodsHome);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewFoodList.setLayoutManager(linearLayoutManager);

        // ======== Truy xuất dữ liệu trong firebase =======
        FirebaseRecyclerOptions<FoodDomain> options = new FirebaseRecyclerOptions.Builder<FoodDomain>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Food").limitToFirst(Constanst.LIMIT_FOODS_ON_HOME), FoodDomain.class).build();

        foodHomeAdapter = new FoodHomeAdapter(options);
        // Xet view cua tung gia tri vao danh sach view recycler
        recyclerViewFoodList.setAdapter(foodHomeAdapter);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnOptions:
                startActivity(new Intent(ProductManagerActivity.this, HandleManagerActivity.class ));
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        categoryHomeAdapter.startListening();
        foodHomeAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        categoryHomeAdapter.stopListening();
        foodHomeAdapter.stopListening();
    }
}