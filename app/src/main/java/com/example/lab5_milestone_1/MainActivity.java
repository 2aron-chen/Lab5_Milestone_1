package com.example.lab5_milestone_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    final public static String usernameKey = "username";

    public void onButtonClick(View view){
        EditText myTextField = (EditText) findViewById(R.id.editTextTextPersonName);
        String str = myTextField.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("user_1", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", str).apply();
        //goToActivity2(str);
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("message", str); startActivity(intent);
    }

    public void goToActivity2(){
        Intent intent = new Intent(this, MainActivity2.class);
        //intent.putExtra("message", s);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("user_1", Context.MODE_PRIVATE);

        if(!sharedPreferences.getString(usernameKey, "").equals("")){
            //String username = sharedPreferences.getString(usernameKey, "");
            goToActivity2();
        } else{
            setContentView(R.layout.activity_main);
        }

    }

}