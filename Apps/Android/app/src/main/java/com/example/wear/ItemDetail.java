package com.example.wear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Statics;
import com.example.bo.Item;
import com.example.bo.User;
import com.example.database.ItemMapper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ItemDetail extends AppCompatActivity {

    //GRAPHICS
    private BottomNavigationView bottomNavigationView;
    private TextView id;
    private TextView name;
    private TextView brand;
    private TextView category;
    private TextView color;
    private TextView description;
    private TextView usage;
    private TextView size;
    private EditText sizeEdit;
    private EditText brandEdit;
    private EditText nameEdit;
    private EditText catEdit;
    private EditText colorEdit;
    private EditText descriptionEdit;
    private Button edit;
    private Button delete;

    //DATA
    private User user;
    private Item item;
    private boolean editMode = true;
    private ItemMapper itemMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        user = (User) getIntent().getSerializableExtra(Statics.SERIZABLE_ID_USER);
        item = (Item) getIntent().getSerializableExtra(Statics.SERIZABLE_ID_ITEM);
        itemMapper = (ItemMapper) getIntent().getSerializableExtra(Statics.SERIZABLE_ID_ITEMMAPPER);
        id = findViewById(R.id.itemDetail_ID);
        name = findViewById(R.id.itemDetail_Name);
        brand = findViewById(R.id.itemDetail_Brand);
        category = findViewById(R.id.itemDetail_Category);
        color = findViewById(R.id.itemDetail_Color);
        description = findViewById(R.id.itemDetail_Description);
        usage = findViewById(R.id.itemDetail_Number);
        size = findViewById(R.id.itemDetail_Size);
        sizeEdit = findViewById(R.id.itemDetail_editSize);
        brandEdit = findViewById(R.id.itemDetail_editBrand);
        nameEdit = findViewById(R.id.itemDetail_editName);
        catEdit = findViewById(R.id.itemDetail_editCategory);
        colorEdit = findViewById(R.id.itemDetail_editColor);
        descriptionEdit = findViewById(R.id.itemDetail_editDescription);

        edit = findViewById(R.id.itemEdit_editButton);
        delete = findViewById(R.id.itemEdit_deleteButton);



        id.setText(String.valueOf(item.getId()));
        name.setText(item.getName());
        brand.setText(item.getBrand());
        category.setText(item.getCategory());
        color.setText(item.getColor());
        description.setText(item.getDescription());
        usage.setText(String.valueOf(item.getNumberOfUsage()));
        size.setText(item.getSize());

        edit.setHintTextColor(Color.WHITE);
        delete.setHintTextColor(Color.WHITE);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editMode){
                    System.out.println("IN EDIT MODE: " + editMode);
                    edit.setText(Statics.SAVE_BUTTON);
                    delete.setVisibility(View.VISIBLE);
                    edit.setBackgroundColor(Color.parseColor(Statics.COLOR_EDIT_ACTIVE));
                    brand.setVisibility(View.INVISIBLE);
                    name.setVisibility(View.INVISIBLE);
                    category.setVisibility(View.INVISIBLE);
                    color.setVisibility(View.INVISIBLE);
                    description.setVisibility(View.INVISIBLE);
                    size.setVisibility(View.INVISIBLE);
                    brandEdit.setVisibility(View.VISIBLE);
                    nameEdit.setVisibility(View.VISIBLE);
                    catEdit.setVisibility(View.VISIBLE);
                    colorEdit.setVisibility(View.VISIBLE);
                    descriptionEdit.setVisibility(View.VISIBLE);
                    sizeEdit.setVisibility(View.VISIBLE);
                    brandEdit.setText(brand.getText().toString());
                    nameEdit.setText(name.getText().toString());
                    catEdit.setText(category.getText().toString());
                    colorEdit.setText(color.getText().toString());
                    descriptionEdit.setText(description.getText().toString());
                    sizeEdit.setText(size.getText().toString());
                    editMode = false;

                }else{

                    item.setName(nameEdit.getText().toString());
                    item.setBrand(brandEdit.getText().toString());
                    item.setCategory(catEdit.getText().toString());
                    item.setDescription(descriptionEdit.getText().toString());
                    item.setColor(colorEdit.getText().toString());
                    item.setSize(sizeEdit.getText().toString());


                    name.setText(item.getName());
                    brand.setText(item.getBrand());
                    category.setText(item.getCategory());
                    color.setText(item.getColor());
                    description.setText(item.getDescription());
                    size.setText(item.getSize());

                    brandEdit.setVisibility(View.INVISIBLE);
                    brand.setVisibility(View.VISIBLE);
                    name.setVisibility(View.VISIBLE);
                    category.setVisibility(View.VISIBLE);
                    color.setVisibility(View.VISIBLE);
                    description.setVisibility(View.VISIBLE);
                    size.setVisibility(View.VISIBLE);
                    nameEdit.setVisibility(View.INVISIBLE);
                    catEdit.setVisibility(View.INVISIBLE);
                    colorEdit.setVisibility(View.INVISIBLE);
                    descriptionEdit.setVisibility(View.INVISIBLE);
                    sizeEdit.setVisibility(View.INVISIBLE);
                    delete.setVisibility(View.INVISIBLE);


                    edit.setText(Statics.EDIT_BUTTON);
                    edit.setBackgroundResource(R.drawable.ic_baseline_edit_24);
                    edit.setBackgroundColor(Color.parseColor(Statics.COLOR_EDIT_NOT_ACTIVE));
                    System.out.println(item.toString());
                    item = itemMapper.update(item);
                    if(item.isCreateStatus()){
                        showToastUpdateMessage();
                    }else{
                        showErrorMessage(Statics.UPDATE_ITEM_ERROR);
                    }

                    editMode = true;



                }






            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder db = new AlertDialog.Builder(getThis());
                db.setTitle(Statics.DELETE_QUESTION);
                db.setPositiveButton(Statics.DELETE_POPUP_BUTTON, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        item = itemMapper.delete(item);
                        if(!item.isCreateStatus()){
                            showToastDeleteMessage();
                            (new Handler()).postDelayed(this::switchToHome, 4000);

                        }else{
                            showErrorMessage(Statics.DELETE_ITEM_ERROR);
                        }
                    }

                    private void switchToHome() {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra(Statics.SERIZABLE_ID_USER, user);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    }
                });
                db.show();

            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_home); //Set Home as default when running the app
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



    }

    private AppCompatActivity getThis(){
        return this;
    }

    private void showToastUpdateMessage() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.item_create_toast_root));

        TextView toastText = layout.findViewById(R.id.item_create_text);
        toastText.setText(Statics.UPDATE_ITEM_SUCCESSFUL);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP,0,0);
        toast.show();
    }

    private void showToastDeleteMessage() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.item_create_toast_root));
        TextView toastText = layout.findViewById(R.id.item_create_text);
        toastText.setText(Statics.DELETE_ITEM_SUCCESSFUL);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP,0,0);
        toast.show();
    }




    private void showErrorMessage(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.item_create_toast_root));
        TextView toastText = layout.findViewById(R.id.item_create_text);
        layout.setBackgroundColor(Color.RED);
        toastText.setText(message);


        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }


}