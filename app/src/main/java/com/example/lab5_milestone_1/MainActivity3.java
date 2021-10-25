package com.example.lab5_milestone_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity3 extends AppCompatActivity {

    TextView editText;
    int noteid = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        editText = (TextView) findViewById(R.id.editText);
        Intent intent = getIntent();
            int noteid = intent.getIntExtra("noteid", -1);

        if (noteid != -1) {
            Note note = MainActivity2.notes.get(noteid);
            String noteContent = note.getContent();
            // Use editText.setText() to display the contents of this note on screen.
            editText.setText(noteContent);
        }
    }
    public void saveMethod(View view){
        // Get editText view and the content that user entered
        editText = (TextView) findViewById(R.id.editText);
        String content = editText.getText().toString();

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        SharedPreferences sharedPreferences = getSharedPreferences("user_1", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        // Save information to database.
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1){ //Add note.
            title = "NOTE_" + (MainActivity2.notes.size() + 1);
            dbHelper.saveNotes(username, title, content, date);
        }else{ //update note.
            title = "NOTE_" + (noteid + 1);
            dbHelper.updateNote(title, date, content, username);
        }

        // Go to the second activity using intents.
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

}