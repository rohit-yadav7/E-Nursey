package com.example.florahub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {
    private ImageView nplants,pots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        nplants=(ImageView) findViewById(R.id.nplants);
        pots=(ImageView) findViewById(R.id.pots);

        nplants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminCategoryActivity.this,AdminPanalActivity.class);
                intent.putExtra("category","nplants");
                startActivity(intent);
            }
        });

        pots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategoryActivity.this,AdminPanalActivity.class);
                intent.putExtra("category","pots");
                startActivity(intent);
            }
        });


    }
}
