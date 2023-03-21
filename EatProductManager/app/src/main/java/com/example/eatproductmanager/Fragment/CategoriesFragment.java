package com.example.eatproductmanager.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.eatproductmanager.Adapter.CategoryAdapter;
import com.example.eatproductmanager.Domain.CategoryDomain;
import com.example.eatproductmanager.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesFragment extends Fragment {

    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    Button btnCreateCategoryItem;
    Button btnUpdateCategoryItem;
    Dialog dialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriesFragment newInstance(String param1, String param2) {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        // Tham chieu den recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.rvCategories);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        btnCreateCategoryItem = (Button) view.findViewById(R.id.btnCreateCategory);
        dialog = new Dialog(view.getContext());


        btnCreateCategoryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.popup_create_category);

                // === khong cho phep thuc thi nut Update ===
//                btnUpdateCategoryItem = (Button) v.findViewById(R.id.btnUpdateCategoryItem);
//                btnUpdateCategoryItem.setEnabled(false);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        // ======== Truy xuất dữ liệu trong firebase =======
        FirebaseRecyclerOptions<CategoryDomain> options = new FirebaseRecyclerOptions.Builder<CategoryDomain>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Category"), CategoryDomain.class).build();


        categoryAdapter = new CategoryAdapter(options);
        // Xet view cua tung gia tri vao danh sach view recycler
        recyclerView.setAdapter(categoryAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        categoryAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        categoryAdapter.stopListening();
    }
}