package com.example.wear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.Statics;
import com.example.bo.Item;
import com.example.bo.User;
import com.example.database.ItemMapper;
import com.example.database.UserMapper;

public class Registry extends AppCompatActivity {


    // GRAPHICS
    private EditText nickname;
    private EditText name;
    private EditText mail;
    private EditText password;
    private Button account;
    private TextView error;
    private TextView login;

    // DATA
    private User user;
    private UserMapper userMapper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        userMapper = UserMapper.userMapper();


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        name = findViewById(R.id.registry_name);
        nickname = findViewById(R.id.registry_nickname);
        mail = findViewById(R.id.registry_mail);
        password = findViewById(R.id.registry_password);
        account = findViewById(R.id.registry_account);
        login = findViewById(R.id.registry_backToLogin);
        error = findViewById(R.id.registry_errorText);

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchBackToLogin();
            }
        });



    }



    private void createAccount() {
        if(name.getText().toString() == null ||
         nickname.getText().toString() == null ||
        mail.getText().toString() == null ||
        password.getText().toString() == null){
            error.setText(Statics.ERROR_ACCOUNT_VALUES_MISSING);
            error.setVisibility(View.VISIBLE);
        }else{
            user = userMapper.create(name.getText().toString(), nickname.getText().toString(), mail.getText().toString(), password.getText().toString());
            if(user.isCreateStatus()){
                error.setVisibility(View.INVISIBLE);
                switchBackToLogin();
            }else{
                error.setText(Statics.ERROR_ACCOUNT);
                error.setVisibility(View.VISIBLE);
            }
        }


    }

    private void switchBackToLogin() {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        intent.putExtra(Statics.SERIZABLE_ID_USERMAPPER, userMapper);
        intent.putExtra(Statics.SERIZABLE_ID_USER, user);
        startActivity(intent);
        overridePendingTransition(0, 0);

    }

}