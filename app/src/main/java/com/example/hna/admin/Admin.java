package com.example.hna.admin;

import static com.example.hna.R.layout.activity_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.hna.R;
import com.example.hna.databinding.ActivityMainBinding;
import com.example.hna.db.Tematike;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {

    private ListView listView;
    private Tematike tematike;
    private ArrayList tema = new ArrayList();
    private ArrayAdapter<String> adapter;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_admin);
        init();
        list();
    }

    public void init() {
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        listView = findViewById(R.id.listadmin);
        tematike = new Tematike(this);
    }

    public void list() {
        SQLiteDatabase database = tematike.getWritableDatabase();
        Cursor cursor = database.query(Tematike.TABLE_CONTACTS, null, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int temaIndex = cursor.getColumnIndex(Tematike.PREDMET);
            do {
                Log.e("Index", String.valueOf(temaIndex));
                Log.e("Index", cursor.getString(temaIndex));
                tema.add(cursor.getString(temaIndex));
            } while (cursor.moveToNext());
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tema);
            listView.setAdapter(adapter);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                SQLiteDatabase db = tematike.getReadableDatabase();
                Cursor cursor1 = db.query(Tematike.TABLE_CONTACTS, new String[]{Tematike.PREDMET},Tematike.KEY_ID +"=?",new String[]{String.valueOf(position+1)},null,null,null,null);
                if (cursor1!=null){
                    cursor1.moveToFirst();
                }
                Log.e("MyLog=   ",cursor1.getString(0)+"   ");
            }
        });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.addmenu:
                        add();
                        break;
                    case R.id.deletemenu:
                        delete();
                        break;
                }
                return false;
            }
        });
    }
    public void add(){
        SQLiteDatabase database = tematike.getWritableDatabase();
        final EditText input = new EditText(Admin.this);
        new AlertDialog.Builder(Admin.this)
                .setTitle("Ввод")
                .setMessage("Введите название предмета которого хочете добавить")
                .setView(input)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ContentValues contentValues = new ContentValues();
                        String value = input.getText().toString();
                        contentValues.put(Tematike.PREDMET,value);
                        database.insert(tematike.TABLE_CONTACTS, null, contentValues);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Do nothing.
                    }
                }).show();
    }
    public void delete(){
        SQLiteDatabase database = tematike.getWritableDatabase();
        final EditText input = new EditText(Admin.this);
        new AlertDialog.Builder(Admin.this)
                .setTitle("Удаление")
                .setMessage("Введите id предмета для удаления")
                .setView(input)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Editable a = input.getText();
                        database.delete(Tematike.TABLE_CONTACTS,Tematike.KEY_ID+"="+a,null);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Do nothing.
                    }
                }).show();
    }
}