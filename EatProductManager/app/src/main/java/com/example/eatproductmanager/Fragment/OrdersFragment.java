package com.example.eatproductmanager.Fragment;

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
import com.example.eatproductmanager.Adapter.OrderAdapter;
import com.example.eatproductmanager.Domain.CategoryDomain;
import com.example.eatproductmanager.Domain.FoodDomain;
import com.example.eatproductmanager.Domain.OrderDomain;
import com.example.eatproductmanager.R;
import com.example.eatproductmanager.Roles.Status;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    RecyclerView recyclerView;
    OrderAdapter orderAdapter;
    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdersFragment newInstance(String param1, String param2) {
        OrdersFragment fragment = new OrdersFragment();
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
        view = inflater.inflate(R.layout.fragment_orders, container, false);

        // Tham chieu den recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.rvOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Button btnStatusDelivery = (Button) view.findViewById(R.id.btnStatusDelivery);
        Button btnSortedOrderItemDelivery = (Button) view.findViewById(R.id.btnSorted);
        btnStatusDelivery.setOnClickListener(this);
        btnSortedOrderItemDelivery.setOnClickListener(this);


        // ======== Truy xuất dữ liệu trong firebase =======
        FirebaseRecyclerOptions<OrderDomain> options = new FirebaseRecyclerOptions.Builder<OrderDomain>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Order"), OrderDomain.class).build();

        orderAdapter = new OrderAdapter(options, view.getContext());
        // Xet view cua tung gia tri vao danh sach view recycler
        recyclerView.setAdapter(orderAdapter);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStatusDelivery:
                showPopupMenu(v);
                break;
            case R.id.btnSorted:
                showPopupMenuSorted(v);
                break;
        }
    }

    // === Xử lí Menu Popup ===
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.action_menu_order_status);

        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    private void showPopupMenuSorted(View view) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.action_menu_order_sorted);

        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    private void showPopupMenuStatus(View view) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.action_menu_order_status);

        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.mnuStatusWaitting:
                FirebaseRecyclerOptions<OrderDomain> options = new FirebaseRecyclerOptions.Builder<OrderDomain>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Order").orderByChild("status").startAt(Status.Waitting).endAt(Status.Waitting+"~"), OrderDomain.class)
                        .build();

                orderAdapter = new OrderAdapter(options, view.getContext());
                orderAdapter.startListening();
                recyclerView.setAdapter(orderAdapter);
                return true;
            case R.id.mnuStatusCompleted:
                FirebaseRecyclerOptions<OrderDomain> options4 = new FirebaseRecyclerOptions.Builder<OrderDomain>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Order").orderByChild("status").startAt(Status.Completed).endAt(Status.Completed+"~"), OrderDomain.class)
                        .build();

                orderAdapter = new OrderAdapter(options4, view.getContext());
                orderAdapter.startListening();
                recyclerView.setAdapter(orderAdapter);
                return true;
            case R.id.mnuStatusOnGoing:
                FirebaseRecyclerOptions<OrderDomain> options5 = new FirebaseRecyclerOptions.Builder<OrderDomain>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Order").orderByChild("status").startAt(Status.OnGoing).endAt(Status.OnGoing+"~"), OrderDomain.class)
                        .build();

                orderAdapter = new OrderAdapter(options5, view.getContext());
                orderAdapter.startListening();
                recyclerView.setAdapter(orderAdapter);
                return true;

            case R.id.mnuTotalPriceAsc:
                FirebaseRecyclerOptions<OrderDomain> options1 = new FirebaseRecyclerOptions.Builder<OrderDomain>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Order").orderByChild("totalPrice"), OrderDomain.class)
                        .build();

                orderAdapter = new OrderAdapter(options1, view.getContext());
                orderAdapter.startListening();
                recyclerView.setAdapter(orderAdapter);
                return true;
            case R.id.mnuTotalPriceDec:
                FirebaseRecyclerOptions<OrderDomain> options2 = new FirebaseRecyclerOptions.Builder<OrderDomain>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Order").orderByChild("totalPrice").limitToLast(50), OrderDomain.class)
                        .build();

                orderAdapter = new OrderAdapter(options2, view.getContext());
                orderAdapter.startListening();
                recyclerView.setAdapter(orderAdapter);

                return true;
            default:
                return false;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        orderAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        orderAdapter.stopListening();
    }
}