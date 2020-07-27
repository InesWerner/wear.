package com.example.wear;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.Statics;
import com.example.bo.Outfit;
import com.example.bo.User;
import com.example.database.OutfitMapper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Outfits extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;

    private User user;
    private ArrayList<Outfit> allOutfits;
    private OutfitMapper outfitMapper;
    private OutfitListArrayAdapter outfitListArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfits);
        ListView listView = findViewById(R.id.listviewOutfits);

        user = (User) getIntent().getSerializableExtra(Statics.SERIZABLE_ID_USER);



        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_outfit);

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
                    case R.id.action_add:
                        Intent intent1 = new Intent(getApplicationContext(), Add.class);
                        intent1.putExtra(Statics.SERIZABLE_ID_USER, user);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.action_statistic:
                        Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                        intent2.putExtra(Statics.SERIZABLE_ID_USER, user);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });


        outfitMapper = OutfitMapper.outfitMapper();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        allOutfits = outfitMapper.findAll();

        outfitListArrayAdapter = new OutfitListArrayAdapter(this,allOutfits);
        listView.setAdapter(outfitListArrayAdapter);

    }

    class OutfitListArrayAdapter extends ArrayAdapter<Outfit> {

        public OutfitListArrayAdapter(@NonNull Context context, ArrayList<Outfit> outfits) {
            super(context, R.layout.row_outfit_list, outfits);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            boolean clicked = false;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View rowOutfit = inflater.inflate(R.layout.row_outfit_list, parent, false);

            Outfit outfit = getItem(position);

            ImageView image = rowOutfit.findViewById(R.id.outfitList_imageView);

            TextView outfitTitle = rowOutfit.findViewById(R.id.listItem_outfit_name);
            TextView outfitCategory = rowOutfit.findViewById(R.id.listItem_outfit_category);

            outfitTitle.setText(outfit.getOutfitName());
            outfitCategory.setText((outfit.getCategory()));

            Outfits.OutfitListArrayAdapter.RowClickListener rowClickListener = new Outfits.OutfitListArrayAdapter.RowClickListener(outfit);
            rowOutfit.setOnClickListener(rowClickListener);


            return rowOutfit;
        }

        class RowClickListener implements View.OnClickListener {
            private Outfit outfit = null;

            public RowClickListener(Outfit outfit) {
                this.outfit = outfit;

            }

            @Override
            public void onClick(View view) {
                switchToDetailOutfit(outfit);

            }
        }

    }

    private void switchToDetailOutfit(Outfit outfit) {
            Intent intent = new Intent(getApplicationContext(), OutfitDetail.class);
            intent.putExtra(Statics.SERIZABLE_ID_OUTFIT, outfit);
            intent.putExtra(Statics.SERIZABLE_ID_USER, user);
            intent.putExtra(Statics.SERIZABLE_ID_OUTFITMAPPER, outfitMapper);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }

    }
