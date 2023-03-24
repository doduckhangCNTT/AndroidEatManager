package com.example.eatproductmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eatproductmanager.Common.Common;
import com.example.eatproductmanager.Domain.UserDomain;
import com.example.eatproductmanager.Roles.Role;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnLogin;
    EditText edtPhone, edtPassword;

    FirebaseDatabase db;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // === Tham Chieu ===
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        findViewById(R.id.btnLogin).setOnClickListener(this);

        // ==== Init Firebase =====
        db = FirebaseDatabase.getInstance();
        users = db.getReference("User");
    }

    // ====== Xu li su kien =======
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnLogin:
                Intent login = new Intent(SignInActivity.this, ProductManagerActivity.class);
                startActivity(login);

//                signInUser(edtPhone.getText().toString(), edtPassword.getText().toString());
                break;
        }
    }

    // ==== Xử lí đăng nhập ====
    private void signInUser(String phone, String password) {
        final ProgressDialog mDialog = new ProgressDialog(SignInActivity.this);
        mDialog.setMessage("Please waiting ...");
        mDialog.show();

        final String localPhone = phone;
        final String localPassword = password;

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(localPhone).exists()) {
                    mDialog.dismiss();
                    UserDomain user = snapshot.child(localPhone).getValue(UserDomain.class);
                    user.setPhone(localPhone);

                    if(user.getRole().toString().equals(Role.Admin)) { // -> If isStaff == true
                        if(user.getPassword().equals(localPassword)) {
                            // ===== Login Successfully =====
                            Intent login = new Intent(SignInActivity.this, ProductManagerActivity.class);
                            Common.currentUser = user;
                            startActivity(login);
                            Toast.makeText(SignInActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(SignInActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignInActivity.this, "Please login with Staff account", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    mDialog.dismiss();
                    Toast.makeText(SignInActivity.this, "User not exist in Database", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}