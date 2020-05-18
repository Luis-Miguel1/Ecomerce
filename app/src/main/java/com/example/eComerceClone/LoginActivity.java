package com.example.eComerceClone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eComerceClone.Model.Users;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText imputNumberPhone, imputPassWord;
    private Button loginButon;
    private String parenDatbaseName = "Users";
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButon = (Button) findViewById(R.id.login_btn);
        imputNumberPhone = (EditText) findViewById(R.id.login_phone_number_imput);
        imputPassWord = (EditText) findViewById(R.id.login_phone_password_imput);

        loadingBar = new ProgressDialog(this);

        loginButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });


    }


    private void loginUser() {

        String phone = imputNumberPhone.getText().toString();
        String password = imputPassWord.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "please write your Phone..", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "please write your Password.", Toast.LENGTH_LONG).show();
        } else {
            loadingBar.setTitle("Login");
            loadingBar.setMessage("Please wait ,while we are checking the credencial");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            allowAccesAccount(phone,password);

        }
    }

    private void allowAccesAccount(final String phone, final String passWord) {
        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parenDatbaseName).child(phone).exists()) {
                    Users userData = dataSnapshot.child(parenDatbaseName).child(phone).getValue(Users.class);

                    if (userData.getPhone().equals(phone)) {
                        Log.i("number ",""+phone);
                        if (userData.getPassWord().equals(passWord)) {
                            Toast.makeText(LoginActivity.this,"Login successyfuly",Toast.LENGTH_LONG).show();
                            Intent intent=  new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(intent);
                        }else{
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this,"PassWord is Incorrect",Toast.LENGTH_LONG).show();
                        }
                    } else {
                        loadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "This account whith this " + phone + "number do not exist", Toast.LENGTH_SHORT).show();


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
