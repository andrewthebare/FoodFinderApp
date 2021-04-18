package com.example.foodfinderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class loginScreen extends AppCompatActivity {

    EditText userNameText;
    EditText pwText;
    Button goButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        userNameText = (EditText) findViewById(R.id.editTextTextUserName);
        pwText = (EditText) findViewById(R.id.editTextTextPassword);
        goButton = (Button) findViewById(R.id.goBTN);
        goButton.setOnClickListener(this::login);
    }

    private void login(View view){
        String uName = userNameText.getText().toString();
        String pw = pwText.getText().toString();

        if(Database.getInstance().validateUser(uName, pw)){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }
}