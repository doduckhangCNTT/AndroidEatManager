package com.example.eatproductmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class OrderDetailActivity extends AppCompatActivity {

    TextView txtOrderIdOrderDetail;
    TextView txtUserNameOrderDetail;
    TextView txtAddressOrderDetail;
    TextView txtPaymentOrderDetail;
    TextView txtStatusOrderDetail;
    TextView txtTotalPriceOrderDetail;
    TextView txtOrderDateOrderDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail2);

        txtOrderIdOrderDetail = (TextView) findViewById(R.id.txtOrderIdOrderDetail);
        txtUserNameOrderDetail = (TextView) findViewById(R.id.txtUserNameOrderDetail);
        txtAddressOrderDetail = (TextView) findViewById(R.id.txtAddressOrderDetail);
        txtPaymentOrderDetail = (TextView) findViewById(R.id.txtPaymentOrderDetail);
        txtStatusOrderDetail = (TextView) findViewById(R.id.txtStatusOrderDetail);
        txtTotalPriceOrderDetail = (TextView) findViewById(R.id.txtTotalPriceOrderDetail);
        txtOrderDateOrderDetail = (TextView) findViewById(R.id.txtOrderDateOrderDetail);

        //lấy intent từ FoodAdapter chuyển sang
        Intent intent = getIntent();
        //Lấy bundle
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String orderId = bundle.getString("orderId");
            Integer addressId = bundle.getInt("addressId");
            String deliveryFee = bundle.getString("deliveryFee");
            String orderDate = bundle.getString("orderDate");
            String paymentID = bundle.getString("paymentID");
            String status = bundle.getString("status");
            Integer totalPrice = bundle.getInt("totalPrice");
            String userID = bundle.getString("userID");

            txtOrderIdOrderDetail.setText(orderId);

            // === Thực hiện tham chiếu đến bảng User và lấy dữ liệu theo Id tương ứng lấy từ Food ===
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("User").child(userID);

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    // Get the value of the data as a Map
                    Map<String, Object> data = (Map<String, Object>) snapshot.getValue();

                    // Do something with the data
                    if (data != null) {
                        txtUserNameOrderDetail.setText((String) data.get("name"));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException());
                }
            });


            // === Thực hiện tham chiếu đến bảng Address và lấy dữ liệu theo Id tương ứng lấy từ Food ===
            DatabaseReference ref1 = database.getReference("Address").child(addressId + "");

            ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    // Get the value of the data as a Map
                    Map<String, Object> data = (Map<String, Object>) snapshot.getValue();

                    // Do something with the data
                    if (data != null) {
                        txtAddressOrderDetail.setText((String) data.get("detail"));

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException());
                }
            });


            DatabaseReference ref2 = database.getReference("Payment").child(paymentID);

            ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    // Get the value of the data as a Map
                    Map<String, Object> data = (Map<String, Object>) snapshot.getValue();

                    // Do something with the data
                    if (data != null) {
                        txtPaymentOrderDetail.setText((String) data.get("name"));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException());
                }
            });

            txtStatusOrderDetail.setText(status);
            txtTotalPriceOrderDetail.setText(totalPrice+ "");
            txtOrderDateOrderDetail.setText(orderDate);

        }
    }


}