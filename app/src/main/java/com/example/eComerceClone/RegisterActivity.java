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

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private Button createAccountButton;
    private EditText imputName,imputNumberPhone,imputPassword;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createAccountButton= (Button) findViewById(R.id.register_btn);
        imputName=(EditText) findViewById(R.id.register_user_name_imput);
        imputNumberPhone= (EditText)  findViewById(R.id.register_phone_number_imput);
        imputPassword=(EditText) findViewById(R.id.register_phone_password_imput);

        loadingBar =new ProgressDialog(this);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }


    private  void createAccount(){
      String name =imputName.getText().toString();
      String phone =imputNumberPhone.getText().toString();
      String password=imputPassword.getText().toString();
      if(TextUtils.isEmpty(name)){
          Toast.makeText(this,"please write your Name..",Toast.LENGTH_LONG).show();
      }else if(TextUtils.isEmpty(phone)){
          Toast.makeText(this,"please write your Phone..",Toast.LENGTH_LONG).show();
      }else  if(TextUtils.isEmpty(password)){
          Toast.makeText(this,"please write your Password.",Toast.LENGTH_LONG).show();
      }else{
                    loadingBar.setTitle("create Account");
                    loadingBar.setMessage("Please wait ,while we are checking the credencial");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    validatePhoneNumber(name,phone,password);

      }

    }

    private void    validatePhoneNumber(final String name, final String phone, final String password){
     final DatabaseReference rootRef;
     rootRef = FirebaseDatabase.getInstance().getReference() ;
      rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              if(!dataSnapshot.child("Users").child(phone).exists()){
                  HashMap<String,Object> userDataMap =new HashMap<>();
                  userDataMap.put("phone",phone);
                  userDataMap.put("passWord",password);
                  userDataMap.put("name",name);
                  Log.d("teste ","telefone"+phone);
                  rootRef.child("Users").child(phone).updateChildren(userDataMap)
                          .addOnCompleteListener(new OnCompleteListener<Void>() {
                              @Override
                              public void onComplete(@NonNull Task task) {
                                  if(task.isSuccessful()){
                                      Toast.makeText(RegisterActivity.this,"Congratulation you account has created succesufuly",Toast.LENGTH_SHORT).show();
                                      loadingBar.dismiss();

                                      Intent intent=  new Intent(RegisterActivity.this,LoginActivity.class);
                                      startActivity(intent);

                                  }else {
                                      loadingBar.dismiss();
                                      Toast.makeText(RegisterActivity.this,"Network error please try again",Toast.LENGTH_SHORT).show();
                                  }
                              }
                          });

              }else{
                  Toast.makeText(RegisterActivity.this,"This "+phone + "already existe",Toast.LENGTH_SHORT).show();
                  loadingBar.dismiss();
                  Toast.makeText(RegisterActivity.this,"Please Try again using another phone number",Toast.LENGTH_LONG).show();

                  Intent intent=  new Intent(RegisterActivity.this,MainActivity.class);
                  startActivity(intent);
                  finish();
              }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });
    }
}
