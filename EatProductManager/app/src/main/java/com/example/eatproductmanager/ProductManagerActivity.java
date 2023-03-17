package com.example.eatproductmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ProductManagerActivity extends AppCompatActivity {
    RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewCategoryList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_manager);

        recyclerViewCategory();

    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.rvCategoriesHome);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);


    }
}