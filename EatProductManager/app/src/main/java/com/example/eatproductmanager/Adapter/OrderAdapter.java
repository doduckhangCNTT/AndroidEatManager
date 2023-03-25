package com.example.eatproductmanager.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eatproductmanager.Domain.CategoryDomain;
import com.example.eatproductmanager.Domain.OrderDomain;
import com.example.eatproductmanager.OrderDetailActivity;
import com.example.eatproductmanager.R;
import com.example.eatproductmanager.Roles.Status;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderAdapter extends FirebaseRecyclerAdapter<OrderDomain, OrderAdapter.orderViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context contextOrder;
    View view;

    class OrderHolderTest {
        orderViewHolder holder;
        OrderDomain orderDomain;

        public OrderHolderTest() {
        }

        public OrderHolderTest(orderViewHolder holder, OrderDomain orderDomain) {
            this.holder = holder;
            this.orderDomain = orderDomain;
        }

        public orderViewHolder getHolder() {
            return holder;
        }

        public void setHolder(orderViewHolder holder) {
            this.holder = holder;
        }

        public OrderDomain getOrderDomain() {
            return orderDomain;
        }

        public void setOrderDomain(OrderDomain orderDomain) {
            this.orderDomain = orderDomain;
        }
    }
    ArrayList<OrderAdapter.OrderHolderTest> orders = new ArrayList<>();

    public OrderAdapter(@NonNull FirebaseRecyclerOptions<OrderDomain> options, Context context) {
        super(options);
        contextOrder = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderAdapter.orderViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull OrderDomain model) {
        holder.txtOrderItemId.setText(model.getOrderID());
        holder.txtUserIdOrderItem.setText(model.getUserID());
        holder.txtStatusOrderItem.setText(model.getStatus());
        holder.txtTotalPriceOrderItem.setText(model.getTotalPrice() + "");

        if(model.getStatus().equals("Completed")) {
            holder.cbStatusOrder.setChecked(true);
        }

        orders.add(new OrderAdapter.OrderHolderTest(
                holder,
                model
        ));

        // === Xu li cb status ===
        holder.cbStatusOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.cbStatusOrder.isChecked()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("addressID", model.getAddressID());
                    map.put("deliveryFee", model.getDeliveryFee());
                    map.put("orderDate", model.getOrderDate());
                    map.put("orderID", model.getOrderID());
                    map.put("paymentID", model.getPaymentID());
                    map.put("status", Status.Completed);
                    map.put("totalPrice", model.getTotalPrice());
                    map.put("userID", model.getUserID());

                    FirebaseDatabase.getInstance().getReference().child("Order")
                            .child(getRef(position).getKey())
                            .updateChildren(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(contextOrder, "Data Update Successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(contextOrder, "Error while updating", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("addressID", model.getAddressID());
                    map.put("deliveryFee", model.getDeliveryFee());
                    map.put("orderDate", model.getOrderDate());
                    map.put("orderID", model.getOrderID());
                    map.put("paymentID", model.getPaymentID());
                    map.put("status", Status.Waitting);
                    map.put("totalPrice", model.getTotalPrice());
                    map.put("userID", model.getUserID());

                    FirebaseDatabase.getInstance().getReference().child("Order")
                            .child(getRef(position).getKey())
                            .updateChildren(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(contextOrder, "Data Update Successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(contextOrder, "Error while updating", Toast.LENGTH_SHORT).show();
                                }
                            });
                    }
            }
        });
    };


    @NonNull
    @Override
    public OrderAdapter.orderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // === Tao doi tuong cho order_item ===
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new orderViewHolder(view);
    }

    class orderViewHolder extends RecyclerView.ViewHolder {

        TextView txtOrderItemId;
        TextView txtUserIdOrderItem;
        TextView txtStatusOrderItem;
        TextView txtTotalPriceOrderItem;
        CheckBox cbStatusOrder;
        TextView btnDetailOrderItem;

        public orderViewHolder(@NonNull View itemView) {
            super(itemView);

            txtOrderItemId = (TextView) itemView.findViewById(R.id.txtOrderItemId);
            txtUserIdOrderItem = (TextView) itemView.findViewById(R.id.txtUserIdOrderItem);
            txtStatusOrderItem = (TextView) itemView.findViewById(R.id.txtStatusOrderItem);
            txtTotalPriceOrderItem = (TextView) itemView.findViewById(R.id.txtTotalPriceOrderItem);
            cbStatusOrder = (CheckBox) itemView.findViewById(R.id.cbStatusOrder);
            btnDetailOrderItem = (TextView) itemView.findViewById(R.id.btnDetailOrderItem);

            // === Xu li order detail ===
            btnDetailOrderItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(contextOrder, OrderDetailActivity.class);
                    Bundle b = new Bundle();

                    b.putString("orderId", orders.get(getAdapterPosition()).orderDomain.getOrderID());
                    b.putString("addressId", orders.get(getAdapterPosition()).orderDomain.getAddressID());
                    b.putInt("deliveryFee", orders.get(getAdapterPosition()).orderDomain.getDeliveryFee());
                    b.putString("orderDate", orders.get(getAdapterPosition()).orderDomain.getOrderDate());
                    b.putString("paymentID", orders.get(getAdapterPosition()).orderDomain.getPaymentID());
                    b.putString("status", orders.get(getAdapterPosition()).orderDomain.getStatus());
                    b.putInt("totalPrice", orders.get(getAdapterPosition()).orderDomain.getTotalPrice());
                    b.putString("userID", orders.get(getAdapterPosition()).orderDomain.getUserID());
                    intent.putExtras(b);

                    contextOrder.startActivity(intent);
                }
            });




        }
    }
}































