package com.example.wear;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.Statics;
import com.example.bo.Item;
import com.example.bo.Outfit;
import com.example.bo.User;
import com.example.database.ItemMapper;
import com.example.database.OutfitMapper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;

public class AddOutfit extends AppCompatActivity {


    //GRAPHICS
    private BottomNavigationView bottomNavigationView;
    private ListView itemListView;
    private Button createOutfitButton;
    private TextView errorText;
    private EditText name;
    private EditText category;
    private EditText description;
    private EditText search;

    //DATA
    private User user;
    private ItemMapper itemMapper;
    private OutfitMapper outfitMapper;
    private ArrayList<Item> items;
    private ItemListArrayAdapter arrayAdapter;

    private ArrayList<Item> selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_outfit);


        user = (User) getIntent().getSerializableExtra(Statics.SERIZABLE_ID_USER);


        selected = new ArrayList<>();

        name = findViewById(R.id.TextField_inputOutfitName);
        category = findViewById(R.id.TextField_inputOutfitCategory);
        description = findViewById(R.id.TextField_inputOutfitDescription);

        itemListView = findViewById(R.id.addOutfit_itemList);
        errorText = findViewById(R.id.addOutfitErrorMessage);
        createOutfitButton = findViewById(R.id.button_createOutfit);
        createOutfitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean ameExistence = checkNameExistence();
                boolean selectExistence = checkSelectItemExistence();
                if(ameExistence && selectExistence){
                      createOutfit();
                }else{
                    if(!ameExistence){
                        errorText.setText(Statics.ERROR_MISSING_NAME);
                    }
                    if(!selectExistence){
                        errorText.setText(Statics.ERROR_MISSING_ITEM);
                    }
                    if(!ameExistence && !selectExistence){
                        errorText.setText(Statics.ERROR_MISSING_NAME_ITEM);
                    }

                    errorText.setVisibility(View.VISIBLE);
                }
            }

        });

        search = findViewById(R.id.TextField_inputOutfitSearch);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                AddOutfit.this.arrayAdapter.getFilter().filter(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_add); //Set Home as default when running the app

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
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
                    case R.id.action_add:
                        Intent intent3 = new Intent(getApplicationContext(), Add.class);
                        intent3.putExtra(Statics.SERIZABLE_ID_USER, user);
                        startActivity(intent3);
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });


        itemMapper = ItemMapper.itemMapper();
        outfitMapper = OutfitMapper.outfitMapper();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        items = itemMapper.findAll();
        loadListData();



    }

    private boolean checkNameExistence() {
        boolean checked = false;
        if (!name.getText().toString().matches(Statics.NONE)) {
            checked = true;
        }
        return  checked;
    }

    private boolean checkSelectItemExistence() {
        boolean checked = false;
        if (!selected.isEmpty()) {
            checked = true;
        }
        return  checked;
    }

    private void showErrorMessage() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.item_create_toast_root));
        TextView toastText = layout.findViewById(R.id.item_create_text);
        layout.setBackgroundColor(Color.RED);
        toastText.setText(Statics.CREATE_OUTFIT_ERROR);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    private void showToastMessage(int outfit_id) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.item_create_toast_root));
        TextView toastText = layout.findViewById(R.id.item_create_text);
        toastText.setText(Statics.CREATE_OUTFIT_SUCCESSFUL);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP,0,0);
        toast.show();
    }

    private void lockInputs() {
        name.setClickable(false);
        category.setClickable(false);
        description.setClickable(false);
        createOutfitButton.setClickable(false);

    }

    private void createOutfit() {
        errorText.setVisibility(View.INVISIBLE);
        Outfit outfit = new Outfit();
        outfit.setOutfit_Name(name.getText().toString());
        outfit.setDescription(description.getText().toString());
        outfit.setCategory(category.getText().toString());
        for(int i = 0; i<selected.size();i++){
            outfit.add(selected.get(i));
        }

        try{
            outfit = outfitMapper.create(outfit);
        }catch(Exception e){
            e.printStackTrace();
        }

        if(outfit.isCreateStatus() == true){
            lockInputs();
            showToastMessage(outfit.getOutfit_ID());
            (new Handler()).postDelayed(this::switchToHome, 4000);
        }else{
            showErrorMessage();
        }
    }

    private void switchToHome() {
        Intent intent2 = new Intent(getApplicationContext(), Outfits.class);
        intent2.putExtra(Statics.SERIZABLE_ID_USER, user);
        startActivity(intent2);
        overridePendingTransition(0, 0);
    }


    private void loadListData() {
        //dataItemAdapter = new ArrayAdapter<Item>(this, R.layout.row_item_add_outfit, items);
        if(arrayAdapter != null){
            itemListView.setAdapter(arrayAdapter);
        }else{
            arrayAdapter = new ItemListArrayAdapter(this,items);
            itemListView.setAdapter(arrayAdapter);
        }

    }


    class ItemListArrayAdapter extends ArrayAdapter<Item> {

        public ItemListArrayAdapter(@NonNull Context context, ArrayList<Item> items) {
            super(context, R.layout.row_item_add_outfit, items);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            boolean clicked = false;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View rowItem = inflater.inflate(R.layout.row_item_add_outfit, parent, false);

            Item item = getItem(position);

            TextView itemTitle = rowItem.findViewById(R.id.addOutfit_Item_Title);
            TextView itemDescription = rowItem.findViewById(R.id.addOutfit_Item_Description);
            TextView itemCategory = rowItem.findViewById(R.id.addOutfit_Item_Category);
            TextView itemColor = rowItem.findViewById(R.id.addOutfit_Item_Color);
            ImageView check = rowItem.findViewById(R.id.addOutfit_Icon_Added_To_List);

            itemTitle.setText(item.getName());
            itemDescription.setText(item.getDescription());
            itemCategory.setText(item.getCategory());
            itemColor.setText(item.getColor());

            RowClickListener rowClickListener = new RowClickListener(rowItem, check, clicked, item);
            rowItem.setOnClickListener(rowClickListener);

            for(int i = 0; i<selected.size();i++){
                if(item.getId() == selected.get(i).getId()){
                    check.setVisibility(View.VISIBLE);
                }
            }

            return rowItem;

        }

        class RowClickListener implements View.OnClickListener {

            View rowItem = null;
            ImageView check = null;
            boolean clicked = false;
            Item item = null;

            public RowClickListener(View rowItem, ImageView check, boolean clicked, Item item) {
                this.rowItem = rowItem;
                this.check = check;
                this.clicked = clicked;
                this.item = item;

            }

            @Override
            public void onClick(View view) {
                    if (!selected.contains(item)) {
                        selected.add(item);
                        check.setVisibility(View.VISIBLE);
                        clicked = true;

                    } else {
                        check.setVisibility(View.INVISIBLE);
                        clicked = false;
                        selected.remove(item);

                    }

                }
            }

        }

    }

