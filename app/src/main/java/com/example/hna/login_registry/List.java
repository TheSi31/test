package com.example.hna.login_registry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hna.R;
import com.example.hna.db.Tematike;

import java.util.ArrayList;

public class List<P> extends AppCompatActivity {

    private ListView listView;
    private Tematike tematike;
    private ArrayList tema = new ArrayList();
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        init();
        list();
    }
    public void init(){
        listView=findViewById(R.id.list);
        tematike=new Tematike(this);
    }
    public void list(){
        SQLiteDatabase database = tematike.getWritableDatabase();
        Cursor cursor = database.query(Tematike.TABLE_CONTACTS,null,null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            int temaIndex = cursor.getColumnIndex(Tematike.PREDMET);
            do{
                Log.e("Index", String.valueOf(temaIndex));
                Log.e("Index", cursor.getString(temaIndex));
                tema.add(cursor.getString(temaIndex));
            }while(cursor.moveToNext());
            adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,tema);
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
                Intent intent = new Intent(List.this, ListItem.class);
                intent.putExtra("dataname",cursor1.getString(0));
                startActivity(intent);
            }
        });
    }

}
