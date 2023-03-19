package com.example.eatproductmanager.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eatproductmanager.Adapter.CategoryAdapter;
import com.example.eatproductmanager.Adapter.FoodAdapter;
import com.example.eatproductmanager.Domain.CategoryDomain;
import com.example.eatproductmanager.Domain.FoodDomain;
import com.example.eatproductmanager.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class FoodsFragment extends Fragment {

    RecyclerView recyclerView;
    FoodAdapter foodAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_foods, container, false);

        // Tham chieu den recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.rvFoods);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // ======== Truy xuất dữ liệu trong firebase =======
        FirebaseRecyclerOptions<FoodDomain> options = new FirebaseRecyclerOptions.Builder<FoodDomain>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Food"), FoodDomain.class).build();

        foodAdapter = new FoodAdapter(options);
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
}