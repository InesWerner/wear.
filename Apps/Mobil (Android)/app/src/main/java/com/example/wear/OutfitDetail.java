package com.example.wear;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
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


public class OutfitDetail extends AppCompatActivity {

    //GRAPHICS
    private BottomNavigationView bottomNavigationView;
    private TextView name;
    private TextView description;
    private TextView category;
    private EditText nameEdit;
    private EditText descriptionEdit;
    private EditText categoryEdit;
    private ListView itemList;
    private Button delete;
    private Button edit;
    private Button addItems;
    private ArrayList <View> rows;
    private OnCloseItemListClickListener onItemListCancelListener;
    private ListView addItemsList;


    //DATA
    private User user;
    private Outfit outfit;
    private boolean editMode = true;
    private OutfitMapper outfitMapper;
    private ItemMapper itemMapper;
    private ItemListArrayAdapter itemListArrayAdapter;
    private AddItemListArrayAdapter addItemListArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit_detail);


        user = (User) getIntent().getSerializableExtra(Statics.SERIZABLE_ID_USER);



        outfit = (Outfit) getIntent().getSerializableExtra(Statics.SERIZABLE_ID_OUTFIT);
        outfitMapper = (OutfitMapper) getIntent().getSerializableExtra(Statics.SERIZABLE_ID_OUTFITMAPPER);
        itemMapper = ItemMapper.itemMapper();


        name = findViewById(R.id.outfitDetail_Name);
        description = findViewById(R.id.outfitDetail_Description);
        category = findViewById(R.id.outfitDetail_Category);
        nameEdit = findViewById(R.id.outfitDetail_editName);
        descriptionEdit = findViewById(R.id.outfitDetail_editDescription);
        categoryEdit = findViewById(R.id.outfitDetail_editCategory);
        itemList = findViewById(R.id.outfitDetail_itemList);
        delete = findViewById(R.id.outfitEdit_deleteButton);
        edit = findViewById(R.id.outfitEdit_editButton);
        addItems = findViewById(R.id.outfitEdit_addItemsButton);



        name.setText(outfit.getOutfitName());
        description.setText(outfit.getDescription());
        category.setText(outfit.getCategory());

        rows = new ArrayList<>();
        itemListArrayAdapter = new OutfitDetail.ItemListArrayAdapter(this,outfit.getItems());
        itemList.setAdapter(itemListArrayAdapter);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder db = new AlertDialog.Builder(getThis());
                db.setTitle(Statics.DELETE_QUESTION);
                db.setPositiveButton(Statics.DELETE_POPUP_BUTTON, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        outfit = outfitMapper.delete(outfit);
                        if(!outfit.isCreateStatus()){
                            showMessage(Statics.DELETE_OUTFIT_SUCCESSFUL, outfit.getOutfit_ID(), Color.GREEN);
                            (new Handler()).postDelayed(this::switchToHome, 4000);

                        }else{
                            showMessage(Statics.DELETE_OUTFIT_ERROR, outfit.getOutfit_ID(), Color.RED);
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

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editMode){
                    System.out.println("IN EDIT MODE: " + editMode);
                    edit.setText(Statics.SAVE_BUTTON);

                    edit.setBackgroundColor(Color.parseColor(Statics.COLOR_EDIT_ACTIVE));
                    delete.setVisibility(View.VISIBLE);
                    name.setVisibility(View.INVISIBLE);
                    category.setVisibility(View.INVISIBLE);
                    description.setVisibility(View.INVISIBLE);
                    nameEdit.setVisibility(View.VISIBLE);
                    descriptionEdit.setVisibility(View.VISIBLE);
                    categoryEdit.setVisibility(View.VISIBLE);
                    addItems.setVisibility(View.VISIBLE);
                    for(int i = 0; i < rows.size();i++){
                        rows.get(i).findViewById(R.id.delete_item_from_outfit_button).setVisibility(View.VISIBLE);
                    }

                    nameEdit.setText(name.getText().toString());
                    descriptionEdit.setText(description.getText().toString());
                    categoryEdit.setText(category.getText().toString());
                    editMode = false;

                }else{

                    outfit.setOutfit_Name(nameEdit.getText().toString());
                    outfit.setCategory(categoryEdit.getText().toString());
                    outfit.setDescription(descriptionEdit.getText().toString());

                    name.setText(outfit.getOutfitName());
                    category.setText(outfit.getCategory());
                    description.setText(outfit.getDescription());

                    name.setVisibility(View.VISIBLE);
                    category.setVisibility(View.VISIBLE);
                    description.setVisibility(View.VISIBLE);
                    nameEdit.setVisibility(View.INVISIBLE);
                    descriptionEdit.setVisibility(View.INVISIBLE);
                    categoryEdit.setVisibility(View.INVISIBLE);
                    delete.setVisibility(View.INVISIBLE);
                    addItems.setVisibility(View.INVISIBLE);
                    for(int i = 0; i < rows.size();i++){
                        rows.get(i).findViewById(R.id.delete_item_from_outfit_button).setVisibility(View.INVISIBLE);
                    }

                    edit.setText(Statics.EDIT_BUTTON);
                    edit.setBackgroundResource(R.drawable.ic_baseline_edit_24);
                    edit.setBackgroundColor(Color.parseColor(Statics.COLOR_EDIT_NOT_ACTIVE));
                    System.out.println(outfit.toString());
                    for(int j = 0; j < outfit.size();j++){
                        System.out.println(outfit.get(j).toString());
                    }
                    outfit = outfitMapper.update(outfit);
                    if(outfit.isCreateStatus()){
                        showMessage(Statics.UPDATE_OUTFIT_SUCCESSFUL, outfit.getOutfit_ID(), Color.GREEN);
                    }else{
                        showMessage(Statics.UPDATE_OUTFIT_ERROR, outfit.getOutfit_ID(), Color.RED);
                    }

                    editMode = true;



                }

            }
        });

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToastAddItemView();

            }
        });


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
                        Intent intent2 = new Intent(getApplicationContext(), Profil.class);
                        intent2.putExtra(Statics.SERIZABLE_ID_USER, user);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.action_outfit:
                        Intent intent3 = new Intent(getApplicationContext(), Outfits.class);
                        intent3.putExtra(Statics.SERIZABLE_ID_USER, user);
                        startActivity(intent3);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    private void showMessage(String message , int id, int color) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.item_create_toast_root));

        TextView toastText = layout.findViewById(R.id.item_create_text);
        toastText.setText(message);
        layout.setBackgroundColor(color);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP,0,0);
        toast.show();
    }


    private void showToastAddItemView() {
        AlertDialog.Builder db = new AlertDialog.Builder(getThis());
        db.setTitle(Statics.SELECT_ITEM);
        ArrayList<Item> allItems = itemMapper.findAll();
        addItemsList = new ListView(this);
        ArrayList<Item> noDuplicates = seperateDuplicates(allItems);
        addItemListArrayAdapter = new AddItemListArrayAdapter(this, noDuplicates);
        addItemsList.setAdapter(addItemListArrayAdapter);
        db.setView(addItemsList);
        onItemListCancelListener = new OnCloseItemListClickListener(this);
        db.setPositiveButton(Statics.CLOSE, onItemListCancelListener);
        db.show();
    }



    private AppCompatActivity getThis(){
        return this;
    }

    private ArrayList<Item> seperateDuplicates(ArrayList<Item> allItems){
        ArrayList<Item> noDuplicates = new ArrayList<>();
        for (Item a1 : allItems) {
            boolean isInSecondArray = false;
            for (Item a2 : outfit.getAllItems()) {
                if (a1.getId() == a2.getId())  {
                    isInSecondArray = true;
                    break;
                }
            }

            if (!isInSecondArray)
                noDuplicates.add(a1);
        }
        return noDuplicates;

    }







    class ItemListArrayAdapter extends ArrayAdapter<Item> {

        Button deleteButton;
        TextView itemTitle;
        TextView itemDescription;
        TextView itemCategory;
        TextView itemColor;
        TextView itemBrand;
        TextView itemUsage;

        public ItemListArrayAdapter(@NonNull Context context, ArrayList<Item> items) {
            super(context, R.layout.row_outfitdetail_item, items);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View rowItem = inflater.inflate(R.layout.row_outfitdetail_item, parent, false);

            Item item = getItem(position);

            itemTitle = rowItem.findViewById(R.id.Item_List_Name);
            itemDescription = rowItem.findViewById(R.id.Item_List_Description);
            itemCategory = rowItem.findViewById(R.id.Item_List_Category);
            itemColor = rowItem.findViewById(R.id.Item_List_Color);
            itemBrand = rowItem.findViewById(R.id.Item_List_Brand);
            itemUsage = rowItem.findViewById(R.id.item_List_Numb);
            deleteButton = rowItem.findViewById(R.id.delete_item_from_outfit_button);

            itemTitle.setText(item.getName());
            itemDescription.setText(item.getDescription());
            itemCategory.setText(item.getCategory());
            itemColor.setText(item.getColor());
            itemBrand.setText(item.getBrand());
            itemUsage.setText(String.valueOf(item.getNumberOfUsage()));

            rows.add(rowItem);

            if(!editMode){
                deleteButton.setVisibility(View.VISIBLE);
            }else{
                deleteButton.setVisibility(View.INVISIBLE);
            }

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    outfit.remove(item);
                    itemListArrayAdapter.remove(item);
                    rows.remove(rowItem);
                }
            });
            return rowItem;
        }

        }



    class AddItemListArrayAdapter extends ArrayAdapter<Item> {

        public AddItemListArrayAdapter(@NonNull Context context, ArrayList<Item> items) {
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

            OutfitDetail.AddItemListArrayAdapter.RowClickListener rowClickListener = new  OutfitDetail.AddItemListArrayAdapter.RowClickListener(rowItem, check, clicked, item, this);
            rowItem.setOnClickListener(rowClickListener);

            return rowItem;

        }

        class RowClickListener implements View.OnClickListener {

            ArrayAdapter<Item> itemArrayAdapter = null;
            View rowItem = null;
            ImageView check = null;
            boolean clicked = false;
            Item item = null;

            public RowClickListener(View rowItem, ImageView check, boolean clicked, Item item, ArrayAdapter<Item> itemArrayAdapter) {
                this.itemArrayAdapter = itemArrayAdapter;
                this.rowItem = rowItem;
                this.check = check;
                this.clicked = clicked;
                this.item = item;
            }

            @Override
            public void onClick(View view) {
                if(outfit.getAllItems().contains(item)){
                    itemArrayAdapter.remove(item);
                    addItemsList.setAdapter(itemArrayAdapter);
                    itemList.setAdapter(itemListArrayAdapter);
                    clicked = false;

                }else{
                    outfit.add(item);
                    //itemListArrayAdapter.add(item);
                    itemList.setAdapter(itemListArrayAdapter);
                    check.setVisibility(View.VISIBLE);
                    clicked = true;

                }

                }

        }

    }





    class OnCloseItemListClickListener implements DialogInterface.OnClickListener{

        private AppCompatActivity context;

        public OnCloseItemListClickListener(AppCompatActivity context){

        }
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }

    }


    }




