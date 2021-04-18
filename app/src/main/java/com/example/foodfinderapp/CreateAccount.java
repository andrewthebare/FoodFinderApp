/**
 *   Names: Matthew Held and Andrew Bare
 *   Emails: mjheld@clemson.edu
 *           abare@clemson.edu
 */

package com.example.foodfinderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity {

    EditText username;
    EditText password;
    EditText name;
    Button createBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        username = (EditText) findViewById(R.id.newUsernameEdit);
        password = (EditText) findViewById(R.id.newPasswordEdit);
        name = (EditText) findViewById(R.id.newNameEdit);
        createBTN = (Button) findViewById(R.id.createNewBTN);
        createBTN.setOnClickListener(this::createNewAccount);
    }

    private void createNewAccount(View view){
        String uName = username.getText().toString();
        String pw = password.getText().toString();
        String name = this.name.getText().toString();


        //make sure the credentials are alright
        if(uName.length()<1){
            Toast.makeText(this, "username empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(pw.length()<6){
            Toast.makeText(this, "password is too short", Toast.LENGTH_SHORT).show();
            return;
        }
        if(name.length()<1){
            Toast.makeText(this, "Name is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        //check if the username exists
        if(Database.getInstance().UsernameExists(uName)){
            Toast.makeText(this, "Username Already Exists!", Toast.LENGTH_SHORT).show();
            return;
        }

        Database.getInstance().createNewUser(name, uName, pw);
        Intent intent = new Intent(this, loginScreen.class);
        this.startActivity(intent);
    }
}