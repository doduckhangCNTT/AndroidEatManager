package com.example.eatproductmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ProductManagerActivity extends AppCompatActivity {
    RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewCategoryList;

    LinearLayout btnOptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_manager);

        btnOptions = (LinearLayout) findViewById(R.id.btnOptions);
        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProductManagerActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ProductManagerActivity.this, HandleManagerActivity.class ));
            }
        });


        recyclerViewCategory();

    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.rvCategoriesHome);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);
    }
}