package com.example.wear;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.example.Statics;
import com.example.bo.Item;
import com.example.bo.User;
import com.example.database.ItemMapper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.io.Serializable;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    //GRAPHICS
    private Button itemOutsideButton;
    private Button itemInsideButton;
    private BottomNavigationView bottomNavigationView;
    private ListView listView;
    private EditText searchView;

    //DATA
    private User user;
    private ItemMapper itemMapper;
    private ArrayList<Item> allItems;
    private ArrayList<Item> inside;
    private ArrayList<Item> outside;
    private ItemListArrayAdapter itemListArrayAdapter;
    private boolean insideStatus = true;


    
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (User) getIntent().getSerializableExtra(Statics.SERIZABLE_ID_USER);

        System.out.println("User "+ user.toString());

        //SETTING UP GRAPHICS
        itemOutsideButton = findViewById(R.id.outsideButton);
        itemInsideButton = findViewById(R.id.insideButton);
        itemInsideButton.setBackgroundColor(Color.parseColor(Statics.COLOR_CLICKED));
        listView =findViewById(R.id.listview);

        itemOutsideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insideStatus = false;
                loadItemOutside();
                itemOutsideButton.setBackgroundColor(Color.parseColor(Statics.COLOR_CLICKED));
                itemInsideButton.setBackgroundColor(Color.parseColor(Statics.COLOR_NOT_CLICKED));

            }
        });

        itemInsideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insideStatus = true;
                loadItemInside();
                itemInsideButton.setBackgroundColor(Color.parseColor(Statics.COLOR_CLICKED));
                itemOutsideButton.setBackgroundColor(Color.parseColor(Statics.COLOR_NOT_CLICKED));

            }
        });




        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.action_home); //Set Home as default when running the app


        // INITALIZE MAPPER AND GET CONNECTION WORKING
        itemMapper = ItemMapper.itemMapper();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        //GET AND ORGANIZE DATA
        allItems = new ArrayList<>();
        allItems = itemMapper.findAll();
        inside = getInsideItems(allItems);
        outside = getOutsidetems(allItems);


        itemListArrayAdapter = new ItemListArrayAdapter(this,inside);
        listView.setAdapter(itemListArrayAdapter);


        searchView = findViewById(R.id.EditText_items_search);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainActivity.this.itemListArrayAdapter.getFilter().filter(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });














    }



    //Handler for BottomNavigation
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_add:
                Intent intent = new Intent(getApplicationContext(), Add.class);
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





    private void loadItemInside() {
        itemListArrayAdapter = new ItemListArrayAdapter(this,inside);
        listView.setAdapter(itemListArrayAdapter);


    }


    private void loadItemOutside() {
        itemListArrayAdapter = new ItemListArrayAdapter(this,outside);
        listView.setAdapter(itemListArrayAdapter);

    }

    private ArrayList<Item> getInsideItems(ArrayList<Item> allItems){
        ArrayList<Item> inside = new ArrayList<>();
        if(allItems!= null){
            for(int i = 0; i<allItems.size(); i++){
                if(allItems.get(i).getStatus()==Statics.INSIDE_NUMB){
                    inside.add(allItems.get(i));
                }
            }
        }
        return inside;
    }

    private ArrayList<Item> getOutsidetems(ArrayList<Item> allItems){
        ArrayList<Item> outside = new ArrayList<>();
        if(allItems!= null){
            for(int i = 0; i<allItems.size(); i++){
                if(allItems.get(i).getStatus()==Statics.OUTSIDE_NUMB){
                    outside.add(allItems.get(i));
                }
            }
        }
        return outside;
    }


    class ItemListArrayAdapter extends ArrayAdapter<Item> implements Serializable {

        public ItemListArrayAdapter(@NonNull Context context, ArrayList<Item> items) {
            super(context, R.layout.row_item_add_outfit, items);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View rowItem = inflater.inflate(R.layout.row_item_list, parent, false);

            Item item = getItem(position);

            TextView itemTitle = rowItem.findViewById(R.id.Item_List_Name);
            TextView itemDescription = rowItem.findViewById(R.id.Item_List_Description);
            TextView itemCategory = rowItem.findViewById(R.id.Item_List_Category);
            TextView itemColor = rowItem.findViewById(R.id.Item_List_Color);
            TextView itemBrand = rowItem.findViewById(R.id.Item_List_Brand);
            TextView itemUsage = rowItem.findViewById(R.id.item_List_Numb);

            itemTitle.setText(item.getName());
            itemDescription.setText(item.getDescription());
            itemCategory.setText(item.getCategory());
            itemColor.setText(item.getColor());
            itemBrand.setText(item.getBrand());
            itemUsage.setText(String.valueOf(item.getNumberOfUsage()));

            RowClickListener rowClickListener = new RowClickListener(item);
            rowItem.setOnClickListener(rowClickListener);


            return rowItem;

        }

        class RowClickListener implements View.OnClickListener, Serializable {

            private Item item = null;

            public RowClickListener(Item item) {
                this.item = item;
            }

            @Override
            public void onClick(View view) {
                switchToDetailItem(item);


            }
        }

    }

    private void switchToDetailItem(Item item) {
        Intent intent = new Intent(getApplicationContext(), ItemDetail.class);
        intent.putExtra(Statics.SERIZABLE_ID_ITEM, item);
        intent.putExtra(Statics.SERIZABLE_ID_USER, user);
        intent.putExtra(Statics.SERIZABLE_ID_ITEMMAPPER, itemMapper);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

}
