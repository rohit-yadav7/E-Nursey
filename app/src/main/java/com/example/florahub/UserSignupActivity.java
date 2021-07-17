package com.example.florahub;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class UserSignupActivity extends AppCompatActivity {
    private Button register_btn1,goToLoginActivity;
    private EditText r_name,r_emailid,r_phone,r_password;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        goToLoginActivity=(Button) findViewById(R.id.goToLoginActivity);
        r_name=(EditText) findViewById(R.id.r_name);
        r_phone=(EditText) findViewById(R.id.r_phone);
        r_emailid=(EditText) findViewById(R.id.r_emailid);
        r_password=(EditText) findViewById(R.id.r_password);
        register_btn1=(Button) findViewById(R.id.register_btn1);
        loadingBar= new ProgressDialog(this);

        register_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createAccount();
            }
        });

        goToLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });
    }

    public void openLoginActivity()
    {
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void createAccount(){

        String name=r_name.getText().toString();
        String Phone=r_phone.getText().toString();
        String Email=r_emailid.getText().toString();
        String passward=r_password.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please Enter Your Name..", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Email)){
            Toast.makeText(this, " Please Enter Email Id", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Phone)){
            Toast.makeText(this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(passward)){
            Toast.makeText(this, "Security is not an illusion!", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please Wait.. ");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            ValidatephoneNumber(name,Phone,passward,Email);
        }
    }

    private void ValidatephoneNumber(final String name, final String phone, final String passward, final String email) {

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(phone).exists()))
                {
                    HashMap<String,Object>userdataMap =new HashMap<>();
                    userdataMap.put("phone",phone);
                    userdataMap.put("password",passward);
                    userdataMap.put("name",name);
                    userdataMap.put("email",email);

                    RootRef.child("Users").child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if(task.isSuccessful())
                                    {

                                        Toast.makeText(UserSignupActivity.this, "Congratulation Your Account Has Been Created", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent =new Intent(UserSignupActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(UserSignupActivity.this, "network error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(UserSignupActivity.this, "This " + phone  + " alredy exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(UserSignupActivity.this, "please try again using another phone number", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

