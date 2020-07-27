package com.example.wear;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.Statics;
import com.example.bo.User;
import com.example.database.UserMapper;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Profil extends AppCompatActivity {


    // GRAPHICS
    private BottomNavigationView bottomNavigationView;
    private TextView name;
    private TextView username;
    private TextView mail;
    private TextView password;
    private TextView items;
    private TextView outfits;
    private ImageView image;

    private EditText nameEdit;
    private EditText usernameEdit;
    private EditText mailEdit;
    private EditText passwordEdit;
    private Button edit;
    private Button delete;
    private Button addImage;

    // DATA
    private User user;
    private boolean editMode = true;
    private UserMapper userMapper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        userMapper = UserMapper.userMapper();
        user = (User) getIntent().getSerializableExtra(Statics.SERIZABLE_ID_USER);
        user = userMapper.findProfilPicture(user);
        System.out.println(user.toString());

        image = findViewById(R.id.profil_image);
        name = findViewById(R.id.profil_name);
        username = findViewById(R.id.profil_username);
        mail = findViewById(R.id.profil_mail);
        items = findViewById(R.id.profil_count_items);
        outfits = findViewById(R.id.profil_count_outfits);
        password = findViewById(R.id.profil_password);

        nameEdit = findViewById(R.id.profil_edit_name2);
        usernameEdit = findViewById(R.id.profil_edit_username);
        mailEdit = findViewById(R.id.profil_edit_mail2);
        passwordEdit = findViewById(R.id.profil_edit_password);
        edit = findViewById(R.id.profil_edit);
        delete = findViewById(R.id.profil_delete);
        addImage = findViewById(R.id.profil_addImage);

        name.setText(user.getName());
        username.setText(user.getUsername());
        mail.setText(user.getMail());
        password.setText(user.getPassword());

        if(user.getProfilImage() == null){
            image.setVisibility(View.INVISIBLE);
            addImage.setVisibility(View.VISIBLE);
            addImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(ContextCompat.checkSelfPermission(Profil.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(Profil.this, new String[]{
                                Manifest.permission.CAMERA
                        }, 100);
                    }
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 100);
                }
            });
        }else{
            image.setImageBitmap(user.getProfilImage());
            image.setVisibility(View.VISIBLE);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(ContextCompat.checkSelfPermission(Profil.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(Profil.this, new String[]{
                                Manifest.permission.CAMERA
                        }, 100);
                    }
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 100);

                }
            });

        }



        if (android.os.Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.design_default_color_primary_dark));
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(editMode){
                    System.out.println("IN PROFIL EDIT MODE: " + editMode);
                    edit.setText(Statics.SAVE_BUTTON);
                    edit.setBackgroundColor(Color.parseColor(Statics.COLOR_EDIT_ACTIVE));
                    name.setVisibility(View.INVISIBLE);
                    username.setVisibility(View.INVISIBLE);
                    mail.setVisibility(View.INVISIBLE);
                    password.setVisibility(View.INVISIBLE);


                    nameEdit.setVisibility(View.VISIBLE);
                    usernameEdit.setVisibility(View.VISIBLE);
                    mailEdit.setVisibility(View.VISIBLE);
                    passwordEdit.setVisibility(View.VISIBLE);
                    delete.setVisibility(View.VISIBLE);

                    nameEdit.setText(user.getName());
                    usernameEdit.setText(user.getUsername());
                    mailEdit.setText(user.getMail());
                    passwordEdit.setText(user.getPassword());

                    editMode = false;

                }else{

                    user.setName(nameEdit.getText().toString());
                    user.setUsername(usernameEdit.getText().toString());
                    user.setMail(mailEdit.getText().toString());
                    user.setPassword(passwordEdit.getText().toString());

                    name.setText(user.getName());
                    username.setText(user.getUsername());
                    mail.setText(user.getMail());
                    password.setText(user.getPassword());


                    name.setVisibility(View.VISIBLE);
                    username.setVisibility(View.VISIBLE);
                    mail.setVisibility(View.VISIBLE);
                    password.setVisibility(View.VISIBLE);

                    nameEdit.setVisibility(View.INVISIBLE);
                    usernameEdit.setVisibility(View.INVISIBLE);
                    mailEdit.setVisibility(View.INVISIBLE);
                    passwordEdit.setVisibility(View.INVISIBLE);
                    delete.setVisibility(View.INVISIBLE);


                    edit.setText(Statics.EDIT_BUTTON);
                    edit.setBackgroundColor(Color.parseColor(Statics.COLOR_EDIT_NOT_ACTIVE));
                    System.out.println(user.toString());
                    user = userMapper.update(user);
                    if(user.isCreateStatus()){
                        showToastUpdateMessage();
                    }else{
                        showErrorMessage();
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
                        user = userMapper.delete(user);
                        if(!user.isCreateStatus()){
                            showToastDeleteMessage();
                            (new Handler()).postDelayed(this::switchToRegistery, 4000);

                        }else{
                            showErrorMessage();
                        }
                    }

                    private void switchToRegistery() {
                        Intent intent = new Intent(getApplicationContext(), Registry.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    }
                });
                db.show();

            }
        });



        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_statistic); //Set Home as default when running the app

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
    }


  @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if (requestCode == 100) {

                Bitmap captureImage = (Bitmap) data.getExtras().get("data");
                user.setProfilImage(captureImage);
                user = userMapper.saveProfilPicture(user);
                if(user.isCreateStatus()){
                    user = userMapper.findProfilPicture(user);
                    image.setImageBitmap(user.getProfilImage());
                    image.setVisibility(View.VISIBLE);
                    addImage.setVisibility(View.INVISIBLE);

                }else{
                    showErrorMessage();

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        /*
        image.setImageBitmap(captureImage);
        image.setVisibility(View.VISIBLE);
         */

    }



    private Context getThis() {
        return this;
    }

    private void showErrorMessage() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.item_create_toast_root));
        TextView toastText = layout.findViewById(R.id.item_create_text);
        layout.setBackgroundColor(Color.RED);
        toastText.setText(Statics.ERROR_USER_UPDATE);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    private void showToastUpdateMessage() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.item_create_toast_root));

        TextView toastText = layout.findViewById(R.id.item_create_text);
        toastText.setText(Statics.UPDATE_USER_SUCCESSFUL);
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
        toastText.setText(Statics.DELETE_USER_SUCCESSFUL);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP,0,0);
        toast.show();
    }


}