package com.example.florahub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.florahub.Model.Users;
import com.example.florahub.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private Button register_btn,login_btn;
    private EditText phone,password;
    private ProgressDialog loadingBar;
    private String parentDbName="Users";
    private TextView adminLink,notAdminLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        register_btn=(Button) findViewById(R.id.register_btn);
        login_btn=(Button) findViewById(R.id.login_btn);
        phone=(EditText) findViewById(R.id.phone);
        password=(EditText) findViewById(R.id.password);
        adminLink=(TextView) findViewById(R.id.adminLink);
        notAdminLink=(TextView)findViewById(R.id.notAdminLink);
        loadingBar= new ProgressDialog(this);

        Paper.init(this);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignup();
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        adminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                login_btn.setText("Login Admin");
                register_btn.setText("Add Admin");
                adminLink.setVisibility(View.INVISIBLE);
                notAdminLink.setVisibility(View.VISIBLE);
                parentDbName="Admins";
            }
        });
        notAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_btn.setText("Login User");
                register_btn.setText("NOT YET REGISTERED??");
                notAdminLink.setVisibility(View.INVISIBLE);
                adminLink.setVisibility(View.VISIBLE);
                parentDbName="Users";
            }
        });
    }

    public void openSignup(){
        Intent intent=new Intent(this, UserSignupActivity.class);
        startActivity(intent);
    }

    private void loginUser(){
        String Phone=phone.getText().toString();
        String passward=password.getText().toString();

          if(TextUtils.isEmpty(Phone)){
            Toast.makeText(this, "enter phone number?", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(passward)){
            Toast.makeText(this, "Security is not an illusion!", Toast.LENGTH_SHORT).show();
        }
        else
          {
              loadingBar.setTitle("Login Account");
              loadingBar.setMessage("Hold your Horses,We are checking your credentials.");
              loadingBar.setCanceledOnTouchOutside(true);
              loadingBar.show();
              AllowAccessToAccount(phone,password);
          }
    }

    private void AllowAccessToAccount(final EditText phone, final EditText password)
    {


        final DatabaseReference RootRef;                                              /**   firebase connectivity  */
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.child(parentDbName).child(phone.getText().toString()).exists())
                {
                        Users usersData = dataSnapshot.child(parentDbName).child(phone.getText().toString()).getValue(Users.class);

                        if (usersData.getPhone().equals(phone.getText().toString()))
                        {
                            if (usersData.getPassword().equals(password.getText().toString()))
                            {
                                if (parentDbName.equals("Admins"))
                                {
                                    Toast.makeText(LoginActivity.this, "Logged In Successfully.Welcome Admin", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();

                                    Intent intent = new Intent(LoginActivity.this,AdminCategoryActivity.class);
                                    startActivity(intent);
                                } else if (parentDbName.equals("Users"))
                                {
                                    Toast.makeText(LoginActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();

                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    Prevalent.currentOnlineUser=usersData;
                                    startActivity(intent);
                                }
                            }else
                            {
                                Toast.makeText(LoginActivity.this, "Please enter correct password", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                        }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
