package com.example.wear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.Statics;
import com.example.bo.User;
import com.example.database.UserMapper;

public class Login extends AppCompatActivity {


    // GRAPHICS
    private EditText username;
    private EditText password;
    private Button login;
    private Button account;
    private TextView error;

    // DATA
    private User user;
    private UserMapper userMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userMapper = UserMapper.userMapper();


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        username = findViewById(R.id.login_mail);
        password = findViewById(R.id.login_password);
        login = findViewById(R.id.signin);
        account = findViewById(R.id.account);
        error = findViewById(R.id.login_errorText);

        try{
            user = (User) getIntent().getSerializableExtra(Statics.SERIZABLE_ID_USER);
            if(user != null){
                username.setText(user.getUsername());
                password.setText(user.getPassword());
            }
        }catch (Exception e){

        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();

            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToRegistryView();

            }
        });



    }

    private void switchToRegistryView() {
        Intent intent = new Intent(getApplicationContext(), Registry.class);
        intent.putExtra(Statics.SERIZABLE_ID_USERMAPPER, userMapper);
        startActivity(intent);
        overridePendingTransition(0, 0);

    }

    private void login() {
        if(username.getText().toString() != Statics.NONE || password.getText().toString() != Statics.NONE){
            user = userMapper.login(username.getText().toString(), password.getText().toString());
            if(user.isLoginStatus()){
                error.setVisibility(View.INVISIBLE);
                switchToHomeView();
            }else{
                error.setText(Statics.ERROR_LOGIN);
                error.setVisibility(View.VISIBLE);
            }
        }else{
            error.setText(Statics.ERROR_LOGIN_VALUES_MISSING);
            error.setVisibility(View.VISIBLE);
        }



    }

    private void switchToHomeView() {

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        overridePendingTransition(0, 0);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(Statics.SERIZABLE_ID_USERMAPPER, userMapper);
        intent.putExtra(Statics.SERIZABLE_ID_USER, user);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }


    public Drawable resizeImage(int imageResource) {// R.drawable.large_image
        // Get device dimensions
        Display display = getWindowManager().getDefaultDisplay();
        double deviceWidth = display.getWidth();

        BitmapDrawable bd = (BitmapDrawable) this.getResources().getDrawable(
                imageResource);
        double imageHeight = bd.getBitmap().getHeight();
        double imageWidth = bd.getBitmap().getWidth();

        double ratio = deviceWidth / imageWidth;
        int newImageHeight = (int) (imageHeight * ratio);

        Bitmap bMap = BitmapFactory.decodeResource(getResources(), imageResource);
        Drawable drawable = new BitmapDrawable(this.getResources(),
                getResizedBitmap(bMap, newImageHeight, (int) deviceWidth));

        return drawable;
    }

    /************************ Resize Bitmap *********************************/
    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

        int width = bm.getWidth();
        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();

        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);

        return resizedBitmap;
    }

}