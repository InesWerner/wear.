package com.example.wear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.Statics;
import com.example.bo.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Add extends AppCompatActivity {

    private Button addItemButton;
    private Button addOutfitButton;

    private BottomNavigationView bottomNavigationView;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        user = (User) getIntent().getSerializableExtra(Statics.SERIZABLE_ID_USER);


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_add); //Set Home as default when running the app

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.action_home:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra(Statics.SERIZABLE_ID_USER, user);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.action_statistic:
                        Intent intent1 = new Intent(getApplicationContext(), Profil.class);
                        intent1.putExtra(Statics.SERIZABLE_ID_USER, user);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.action_outfit:
                        Intent intent2 = new Intent(getApplicationContext(), Outfits.class);
                        intent2.putExtra(Statics.SERIZABLE_ID_USER, user);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });

        addItemButton = findViewById(R.id.addItemButton);
        addOutfitButton = findViewById(R.id.addOutfitButton);

        addOutfitButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                showAddOutfitView();
            }


        });


        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddItemView();
            }


        });

    }


    private void showAddOutfitView() {
        Intent intent = new Intent(getApplicationContext(), AddOutfit.class);
        intent.putExtra(Statics.SERIZABLE_ID_USER, user);
        startActivity(intent);
        overridePendingTransition(0, 0);

    }

    private void showAddItemView() {
        Intent intent = new Intent(getApplicationContext(), AddItem.class);
        intent.putExtra(Statics.SERIZABLE_ID_USER, user);
        startActivity(intent);
        overridePendingTransition(0, 0);

    }
}