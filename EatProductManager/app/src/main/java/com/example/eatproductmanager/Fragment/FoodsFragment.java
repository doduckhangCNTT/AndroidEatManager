package com.example.eatproductmanager.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;

import com.example.eatproductmanager.Adapter.CategoryAdapter;
import com.example.eatproductmanager.Adapter.FoodAdapter;
import com.example.eatproductmanager.Domain.CategoryDomain;
import com.example.eatproductmanager.Domain.FoodDomain;
import com.example.eatproductmanager.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class FoodsFragment extends Fragment implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    RecyclerView recyclerView;
    FoodAdapter foodAdapter;
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_foods, container, false);

        // Tham chieu den recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.rvFoods);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Button btnCreateFood = (Button) view.findViewById(R.id.btnCreateFood);
        Button btnSortedFood = (Button) view.findViewById(R.id.btnSortedFood);

        btnCreateFood.setOnClickListener(this);
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

    // === Xu li khi click vao button ===
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateFood:
                dialog.setContentView(R.layout.popup_create_food);
                // === khong cho phep thuc thi nut Update ===
                // btnUpdateCategoryItem = (Button) v.findViewById(R.id.btnUpdateCategoryItem);
                // btnUpdateCategoryItem.setEnabled(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
                break;
            case R.id.btnSortedFood:
                showPopupMenu(v);
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
                Log.d("Sort", "Sort Food");
                return true;
            case R.id.mnuSortedPriceFood:
                Log.d("Sort", "Sort Price Food");
                return true;
            default:
                return false;
        }
    }
}