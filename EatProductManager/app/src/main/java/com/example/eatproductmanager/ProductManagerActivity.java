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

import com.example.eatproductmanager.Common.Common;

public class ProductManagerActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewCategoryList;

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

        recyclerViewCategory();

    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.rvCategoriesHome);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnOptions:
                startActivity(new Intent(ProductManagerActivity.this, HandleManagerActivity.class ));
                break;
        }
    }
}