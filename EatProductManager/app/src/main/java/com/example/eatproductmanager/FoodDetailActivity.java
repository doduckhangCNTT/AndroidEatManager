package com.example.eatproductmanager;

import static com.example.eatproductmanager.Common.Common.categoriesCommon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eatproductmanager.Domain.CategoryDomain;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class FoodDetailActivity extends AppCompatActivity {
    TextView nameFoodDetail;
    TextView priceFoodDetail;
    TextView desFoodDetail;
    TextView discountFoodDetail;
    TextView categoryIdFoodDetail;
    ImageView imageFoodDetail;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        // === Tham chieu ===
        nameFoodDetail = (TextView) findViewById(R.id.txtNameFoodDetail);
        priceFoodDetail = (TextView) findViewById(R.id.txtPriceFoodDetail);
        desFoodDetail = (TextView) findViewById(R.id.txtDesFoodDetail);
        discountFoodDetail = (TextView) findViewById(R.id.txtDiscountFoodDetail);
        categoryIdFoodDetail = (TextView) findViewById(R.id.txtCategoryFoodDetail);
        imageFoodDetail = (ImageView) findViewById(R.id.imgFoodDetail);

        //lấy intent từ FoodAdapter chuyển sang
        Intent intent = getIntent();
        //Lấy bundle
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            String nameFood = bundle.getString("NameFood");
            Integer priceFood = bundle.getInt("PriceFood");
            String desFood = bundle.getString("DesFood");
            Integer discountFood = bundle.getInt("DiscountFood");
            String categoryIdFood = bundle.getString("CategoryIdFood");
            String imageFood = bundle.getString("ImageFood");

            // === Thực hiện tham chiếu đến bảng Category và lấy dữ liệu theo Id tương ứng lấy từ Food ===
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("Category").child(categoryIdFood);

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    // Get the value of the data as a Map
                    Map<String, Object> data = (Map<String, Object>) snapshot.getValue();

                    // Do something with the data
                    if (data != null) {
                        nameFoodDetail.setText(nameFood);
                        priceFoodDetail.setText(priceFood + "");
                        desFoodDetail.setText(desFood);
                        discountFoodDetail.setText(discountFood + "");
                        categoryIdFoodDetail.setText((String) data.get("name"));

                        Glide.with(imageFoodDetail)
                                .load(imageFood)
                                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                                .into(imageFoodDetail);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException());
                }
            });
        }
    }
}