package com.example.eatproductmanager.Fragment;


import static com.example.eatproductmanager.Adapter.CategoryAdapter.positionsCategoryChecked;

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
import android.view.Menu;
import android.view.MenuInflater;
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
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesFragment extends Fragment implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    Button btnCreateCategoryItem;
    Button btnUpdateCategoryItem;
    Button btnSortCategory;
    Button btnDeleteCategoryItem;
    Dialog dialog;
    View view;

    TextView txtSearchCategory;

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
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true); // Cho phep hien thi menu tren thanh navbar

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_categories, container, false);

        // Tham chieu den recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.rvCategories);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        btnCreateCategoryItem = (Button) view.findViewById(R.id.btnCreateCategory);
        btnDeleteCategoryItem = (Button) view.findViewById(R.id.btnDeleteCategoryChoice);
        btnSortCategory = (Button) view.findViewById(R.id.btnSortedCategory);
        txtSearchCategory = (TextView) view.findViewById(R.id.edtSearchCategory);

        dialog = new Dialog(view.getContext());

        // === Lang nghe su kien ===
        btnCreateCategoryItem.setOnClickListener(this);
        btnDeleteCategoryItem.setOnClickListener(this);
        btnSortCategory.setOnClickListener(this);
        // === Xu li tim kiem ===
        txtSearchCategory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String txtSearch = txtSearchCategory.getText().toString();
                // Tìm kiếm trên Firebase
                FirebaseRecyclerOptions<CategoryDomain> options = new FirebaseRecyclerOptions.Builder<CategoryDomain>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Category").orderByChild("name").startAt(txtSearch).endAt(txtSearch+"~"), CategoryDomain.class)
                        .build();

                categoryAdapter = new CategoryAdapter(options, view.getContext());
                categoryAdapter.startListening();
                recyclerView.setAdapter(categoryAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // ======== Truy xuất dữ liệu trong firebase =======
        FirebaseRecyclerOptions<CategoryDomain> options = new FirebaseRecyclerOptions.Builder<CategoryDomain>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Category"), CategoryDomain.class).build();

        categoryAdapter = new CategoryAdapter(options, view.getContext());
        // Xet view cua tung gia tri vao danh sach view recycler
        recyclerView.setAdapter(categoryAdapter);
        return view;
    }

    // === Xu li khi click vao button ===
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateCategory:
                dialog.setContentView(R.layout.popup_create_category);
                // === khong cho phep thuc thi nut Update ===
                 btnUpdateCategoryItem = (Button) dialog.findViewById(R.id.btnUpdateCategoryItem);
                 btnUpdateCategoryItem.setEnabled(false);

                TextView nameCategoryItem = (TextView) dialog.findViewById(R.id.edtNameCategoryItem);
                TextView imgUrlCategoryItem = (TextView) dialog.findViewById(R.id.edtImageCategoryItem);
                Button btnCreateCategoryItem = (Button) dialog.findViewById(R.id.btnCreateCategoryItem);

                btnCreateCategoryItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Test: ", nameCategoryItem.getText().toString() + " " + imgUrlCategoryItem.getText().toString());

                        Map<String, Object> map = new HashMap<>();
                        map.put("name", nameCategoryItem.getText().toString());
                        map.put("image", imgUrlCategoryItem.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Category").push()
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
                                        Toast.makeText(v.getContext(), "Erorr while inserted", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                break;
            case R.id.btnSortedCategory:
                showPopupMenu(v);
                break;
            case R.id.btnDeleteCategoryChoice:
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Are you sure");
                builder.setMessage("Deleted data can't be undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Thuc hien xoa du lieu tren Firebase
                        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference("Category");

                        for(int i : positionsCategoryChecked) {
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

    // === Xử lí Menu Popup ===
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.action_menu_category);

        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.mnuSortCategoryName:
                FirebaseRecyclerOptions<CategoryDomain> options = new FirebaseRecyclerOptions.Builder<CategoryDomain>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Category").orderByChild("name"), CategoryDomain.class)
                        .build();

                categoryAdapter = new CategoryAdapter(options, view.getContext());
                categoryAdapter.startListening();
                recyclerView.setAdapter(categoryAdapter);
                return true;
            case R.id.mnuSortCategoryPopulation:
                Log.d("Sort", "Sort population Category");
                return true;
            default:
                return false;
        }
    }

    // === Handle Option Menu ===
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.action_menu_category,menu);
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuSortCategoryName:
                Log.d("Hello", "Hello Sort");
                return true;
            case R.id.mnuSortCategoryPopulation:
                return true;
            default:
                break;
        }
//        return super.onOptionsItemSelected(item);
        return false;
    }
    // ==========================

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