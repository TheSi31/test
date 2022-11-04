package com.example.hna.login_registry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hna.R;
import com.example.hna.db.DBHelper;

public class MainActivity extends AppCompatActivity {

    private EditText fio,login,password,otel,group;
    private TextView text;
    private Button zalogins,reg;
    private DBHelper dbHelper;
    private RadioGroup radioGroup;
    private int a=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        anim();
        click();
    }
    public void init(){
        text=findViewById(R.id.textView);
        fio=findViewById(R.id.fio);
        login=findViewById(R.id.login);
        password=findViewById(R.id.password);
        otel=findViewById(R.id.otel);
        group=findViewById(R.id.group);
        zalogins=findViewById(R.id.zalogins);
        reg=findViewById(R.id.reg);
        radioGroup=findViewById(R.id.A);
        dbHelper=new DBHelper(this);
    }
    public void anim(){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.text);
        text.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this, R.anim.fio);
        fio.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this, R.anim.login);
        login.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this, R.anim.password);
        password.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this, R.anim.otel);
        otel.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this, R.anim.otel);
        otel.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,R.anim.group);
        group.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,R.anim.radio);
        radioGroup.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,R.anim.bth);
        reg.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,R.anim.bthavto);
        zalogins.startAnimation(animation);
    }
    public void click(){
            reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int id) {
                        switch (id){
                            case R.id.teacher:
                                a = 0;
                                break;
                            case R.id.student:
                                a = 1;
                                break;
                        }
                    }
                });
                if(fio.getText().toString().equals("")){
                    Toast toast=Toast.makeText(getApplicationContext(), "Введенно неверное имя", Toast.LENGTH_LONG);
                    toast.show();
                }else if(password.getText().toString().equals("")){
                    Toast toast=Toast.makeText(getApplicationContext(), "Пароль пуст", Toast.LENGTH_LONG);
                    toast.show();
                }else if(login.getText().toString().equals("")){
                    Toast toast=Toast.makeText(getApplicationContext(), "Логин пуст", Toast.LENGTH_LONG);
                    toast.show();
                }else if(group.getText().toString().equals("")){
                    Toast toast=Toast.makeText(getApplicationContext(), "Группа пуста", Toast.LENGTH_LONG);
                    toast.show();
                }else if(otel.getText().toString().equals("")){
                    Toast toast=Toast.makeText(getApplicationContext(), "ОТделение пустое", Toast.LENGTH_LONG);
                    toast.show();
                }

                boolean b = false;
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                Cursor cursor = database.query(DBHelper.TABLE_CONTACTS,null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    int loginIndex = cursor.getColumnIndex(DBHelper.LOGIN);
                    if(cursor.getString(loginIndex).equals(login.getText().toString())){
                        b=true;
                    }
                }
                if(b==true){
                    Toast toast=Toast.makeText(getApplicationContext(), "Такой логин уже есть", Toast.LENGTH_LONG);
                    toast.show();
                }else {
                    String fios = fio.getText().toString();
                    String logins = login.getText().toString();
                    String passwords = password.getText().toString();
                    String otels = otel.getText().toString();
                    String groups = group.getText().toString();

                    ContentValues contentValues = new ContentValues();

                    contentValues.put(DBHelper.FIO, fios);
                    contentValues.put(DBHelper.LOGIN, logins);
                    contentValues.put(DBHelper.PASSWORD, passwords);
                    contentValues.put(DBHelper.OTEL, otels);
                    contentValues.put(DBHelper.GROUP, groups);
                    contentValues.put(DBHelper.A, a);

                    database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                    Intent intent = new Intent(MainActivity.this, List.class);
                    /*//Проверка базы
                cursor = database.query(DBHelper.TABLE_CONTACTS,null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int fioIndex = cursor.getColumnIndex(DBHelper.FIO);
                    int loginIndex = cursor.getColumnIndex(DBHelper.LOGIN);
                    int passwordIndex = cursor.getColumnIndex(DBHelper.PASSWORD);
                    int otelIndex = cursor.getColumnIndex(DBHelper.OTEL);
                    int groupIndex = cursor.getColumnIndex(DBHelper.GROUP);
                    int AIndex = cursor.getColumnIndex(DBHelper.A);
                        do {
                            Log.d("mLog","ID = " + cursor.getInt(idIndex)+", fio = "+ cursor.getString(fioIndex)+
                                    ", login = "+ cursor.getString(loginIndex)+", password = "+ cursor.getString(passwordIndex)+
                                    ", otel = "+ cursor.getString(otelIndex)+ ", group = "+ cursor.getString(groupIndex) + ", A = " + cursor.getInt(AIndex));
                        }while (cursor.moveToNext());
                }else{
                    Log.d("mLog","0 rows");
                }*/
                cursor.close();
                    //database.delete(DBHelper.TABLE_CONTACTS,null,null);//удаления базы данных
                    dbHelper.close();
                }
            }
        });
        zalogins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.close();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });
    }
}