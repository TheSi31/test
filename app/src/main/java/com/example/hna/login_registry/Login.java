package com.example.hna.login_registry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.hna.R;
import com.example.hna.admin.Admin;
import com.example.hna.db.DBHelper;

import java.util.Objects;

public class Login extends AppCompatActivity {

    private EditText login,password;
    private Button bth;
    private DBHelper dbHelper;
    private RadioGroup radioGroup;
    private int a=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        click();
    }

    public void init(){
        login=findViewById(R.id.editTextTextPersonName2);
        password=findViewById(R.id.editTextTextPassword);
        bth=findViewById(R.id.button);
        radioGroup=findViewById(R.id.A);
        dbHelper=new DBHelper(this);
    }
    public void click() {
        bth.setOnClickListener(new View.OnClickListener() {
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

                SQLiteDatabase database = dbHelper.getWritableDatabase();
                Cursor cursor = database.query(DBHelper.TABLE_CONTACTS,null,null,null,null,null,null);

                String logins = login.getText().toString();
                String passwords = password.getText().toString();
                int i = 0;
                if (cursor.moveToFirst()) {
                    if (i == 0) {
                        int loginIndex = cursor.getColumnIndex(DBHelper.LOGIN);
                        int passwordIndex = cursor.getColumnIndex(DBHelper.PASSWORD);
                        do {
                            if (Objects.equals(logins, cursor.getString(loginIndex)) & Objects.equals(passwords, cursor.getString(passwordIndex)) & Objects.equals(a, 1)) {
                                i++;
                                Log.e("Student = ", String.valueOf(a) + "I = " + i);
                                Intent intent = new Intent(Login.this, List.class);
                                startActivity(intent);
                                break;
                            } else {
                                if (Objects.equals(logins, cursor.getString(loginIndex)) & Objects.equals(passwords, cursor.getString(passwordIndex)) & Objects.equals(a, 0)) {
                                    i++;
                                    Log.e("Teacher = ", String.valueOf(a) + "I = " + i);
                                    Intent intent = new Intent(Login.this, Admin.class);
                                    startActivity(intent);
                                    break;
                                } else {
                                    Toast.makeText(getApplicationContext(), "Неправильный логин или пароль", Toast.LENGTH_LONG).show();
                                }
                            }
                        } while (cursor.moveToNext());
                    }
                }
                cursor.close();
                dbHelper.close();
            }
        });
    }
}