package com.example.foodfinderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginScreen extends AppCompatActivity {

    EditText userNameText;
    EditText pwText;
    Button goButton;
    Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        userNameText = (EditText) findViewById(R.id.editTextTextUserName);
        pwText = (EditText) findViewById(R.id.editTextTextPassword);
        goButton = (Button) findViewById(R.id.goBTN);
        goButton.setOnClickListener(this::login);
        createButton = (Button) findViewById(R.id.createAccountBTN);
        createButton.setOnClickListener(this::create);
    }

    private void create(View view){
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }

    private void login(View view){
        String uName = userNameText.getText().toString();
        String pw = pwText.getText().toString();

        if(Database.getInstance().validateUser(uName, pw)){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Invalid Login Credentials!", Toast.LENGTH_SHORT).show();
        }

    }
}