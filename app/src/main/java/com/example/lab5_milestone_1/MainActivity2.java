package com.example.lab5_milestone_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textView = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
        String str = intent.getStringExtra("message");
        textView.setText("Hello " + str);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public class Note{

        private String date;
        private String username;
        private String title;
        private String content;

        public Note(String date, String username, String title, String content){
            this.date = date;
            this.username = username;
            this.title = title;
            this.content = content;
        }

        public String getDate(){
            return date;
        }

        public String getUsername(){
            return username;
        }

        public String getTitle(){
            return title;
        }

        public String getContent(){
            return content;
        }
    }

    Context context = getApplicationContext();
    SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("note", Context.MODE_PRIVATE, null);

    public class DBHelper {

        SQLiteDatabase sqLiteDatabase;

        public DBHelper(SQLiteDatabase sqliteDatabase) {
            this.sqLiteDatabase = sqLiteDatabase;
        }
        public void createTable() {
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS notes " +
                    "(id INTEGER PRIMARY KEY, username TEXT , date TEXT, title TEXT, content TEXT, src TEXT)");
        }
        public ArrayList<Note> readNotes (String username) {
            createTable();
            Cursor c = sqLiteDatabase.rawQuery(String.format("SELECT * from notes where username like '%s'", username), null);

            int dateIndex = c.getColumnIndex("date");
            int titleIndex = c.getColumnIndex("title");
            int contentIndex = c.getColumnIndex("content");

            c.moveToFirst();

            ArrayList<Note> notesList = new ArrayList<>();

            while (!c.isAfterLast()){
                String title = c.getString(titleIndex);
                String date = c.getString(dateIndex);
                String content = c.getString(contentIndex);

                Note note= new Note(date, username, title, content) ;
                notesList.add(note);
                c.moveToNext() ;
            }
            c.close();
            sqLiteDatabase.close();

            return notesList;
        }
        public void saveNotes (String username, String title, String content, String date) {
            createTable();
            sqLiteDatabase.execSQL(String.format("INSERT INTO notes (username, date, title, content) VALUES ('%s', '%s', '%s', '%s')", username, date, title, content));
        }

        public void updateNote(String title, String date , String content , String username) {
            createTable();
            sqLiteDatabase.execSQL(String.format("UPDATE notes set content = '%s', date = '%s' where title = '%s' and username = '%s'", content, date, title, username));
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.ic_logout:
                Intent intent = new Intent(this, MainActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences("user_1", Context.MODE_PRIVATE);
                sharedPreferences.edit().remove(MainActivity.usernameKey).apply();
                startActivity(intent);
                return true;
            case R.id.ic_addnote:
                Intent intent2 = new Intent(this, MainActivity3.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
