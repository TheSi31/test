package com.example.hna.login_registry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hna.R;
import com.example.hna.db.Tema;

import java.util.ArrayList;

public class ListItem extends AppCompatActivity {

    private Tema tema;
    private ListView listView;
    private ArrayList temas = new ArrayList();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
        init();
        list();
    }
    public void init(){
        Bundle arguments = getIntent().getExtras();
        String a = arguments.get("dataname").toString();
        tema=new Tema(this,a);
        listView=findViewById(R.id.list_item);
    }
    public void list(){
        SQLiteDatabase database = tema.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Tema.PREDMET,"ggg");
        database.insert(Tema.TABLE_CONTACTS,null,contentValues);
        Cursor cursor = database.query(Tema.TABLE_CONTACTS,null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            int predmetIndex = cursor.getColumnIndex(Tema.PREDMET);
            do{
                temas.add(cursor.getString(predmetIndex));
            }while (cursor.moveToNext());
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, (java.util.List<String>) tema);
            listView.setAdapter(adapter);
        }
    }
}