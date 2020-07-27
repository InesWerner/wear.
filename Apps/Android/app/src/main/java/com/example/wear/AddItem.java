package com.example.wear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Statics;
import com.example.bo.Item;
import com.example.bo.Tag;
import com.example.bo.User;
import com.example.database.ItemMapper;
import com.example.database.TagMapper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class AddItem extends AppCompatActivity {

   //GRAPHICS
    private BottomNavigationView bottomNavigationView;
    private Spinner spinner;
    private EditText name;
    private EditText category;
    private EditText description;
    private EditText size;
    private EditText color;
    private EditText brand;
    private Button createItemButton;
    private TextView errorText;

    //DATA
    private User user;
    private TagMapper tagMapper;
    private ItemMapper itemMapper;
    private ArrayList<Tag> tags;
    private ArrayAdapter<Tag> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        user = (User) getIntent().getSerializableExtra(Statics.SERIZABLE_ID_USER);


        spinner = findViewById(R.id.spinner_inputItemTag);
        name = findViewById(R.id.TextField_inputItemName);
        category = findViewById(R.id.TextField_inputItemCategory);
        description = findViewById(R.id.TextField_inputItemDescription);
        size = findViewById(R.id.TextField_inputItemSize);
        color = findViewById(R.id.TextField_inputItemColor);
        brand = findViewById(R.id.TextField_inputItemBrand);
        errorText = findViewById(R.id.addItemErrorMessage);
        createItemButton = findViewById(R.id.button_createItem);
        createItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean requieredValuesExistence = checkValueExistence();
                if(requieredValuesExistence == true){
                    errorText.setVisibility(View.INVISIBLE);
                    Item item = createItem();
                    if(item.isCreateStatus() == true){
                        lockInputs();
                        showToastMessage();
                         (new Handler()).postDelayed(this::switchToHome, 4000);
                    }else{
                        showErrorMessage();
                    }

                }else{
                    errorText.setText(Statics.ERROR_MISSING_TAG_NAME_ITEM);
                    errorText.setVisibility(View.VISIBLE);
                }
            }
            private void switchToHome() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(Statics.SERIZABLE_ID_USER, user);
                startActivity(intent);
                overridePendingTransition(0, 0);
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


        tagMapper = TagMapper.tagMapper();
        itemMapper = ItemMapper.itemMapper();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        tags = tagMapper.findAllFreeTags();
        loadSpinnerData();


    }

    private void showErrorMessage() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.item_create_toast_root));

        TextView toastText = layout.findViewById(R.id.item_create_text);


        layout.setBackgroundColor(Color.RED);
        toastText.setText(Statics.CREATE_ITEM_ERROR);


        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }

    private void showToastMessage() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.item_create_toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP,0,0);
        toast.show();

    }

    private boolean checkValueExistence() {
        boolean checked = false;
        if (!name.getText().toString().matches(Statics.NONE) && !spinner.getSelectedItem().toString().matches(Statics.NONE)) {
            checked = true;
        }
        return  checked;

    }

    private void loadSpinnerData() {
        if(tags.isEmpty()){
            // IF EMPTY
           // dataAdapter = new ArrayAdapter<Tag>(this, android.R.layout.simple_list_item_1, );

        }else{
            dataAdapter = new ArrayAdapter<Tag>(this, android.R.layout.simple_list_item_1, tags);
            spinner.setAdapter(dataAdapter);
        }



    }

    private Item createItem(){
        boolean status = false;
        Tag tag = null;
        Item item = new Item();

        item.setName(name.getText().toString());
        item.setCategory(category.getText().toString());
        item.setDescription(description.getText().toString());
        item.setNumberOfUsage(0);
        item.setStatus(0);
        item.setSize(size.getText().toString());
        item.setColor(color.getText().toString());
        item.setBrand(brand.getText().toString());
        for(int i = 0; i<tags.size();i++){
            if(spinner.getSelectedItem().toString().matches(String.valueOf(tags.get(i).getTagKey()))){
                tag = tags.get(i);
            }
        }
        item.setTagID(tag.getTagID());

        try{
           item = itemMapper.create(item, tag);

        }catch (Exception e){
            e.printStackTrace();
        }
        return item;
    }

    private void lockInputs(){
        name.setClickable(false);
        category.setClickable(false);
        description.setClickable(false);
        size.setClickable(false);
        color.setClickable(false);
        brand.setClickable(false);
        createItemButton.setClickable(false);
        spinner.setClickable(false);

        name.setBackgroundColor(Statics.LOCKCOLOR);
        category.setBackgroundColor(Statics.LOCKCOLOR);
        description.setBackgroundColor(Statics.LOCKCOLOR);
        size.setBackgroundColor(Statics.LOCKCOLOR);
        color.setBackgroundColor(Statics.LOCKCOLOR);
        brand.setBackgroundColor(Statics.LOCKCOLOR);
        createItemButton.setBackgroundColor(Statics.LOCKCOLOR);
        spinner.setBackgroundColor(Statics.LOCKCOLOR);
    }



}