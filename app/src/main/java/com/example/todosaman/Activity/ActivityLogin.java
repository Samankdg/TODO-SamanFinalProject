package com.example.todosaman.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todosaman.Database.AppDatabase;
import com.example.todosaman.R;
import com.example.todosaman.Tables.User;
import com.example.todosaman.Database.Dao.UserDao;


public class ActivityLogin extends AppCompatActivity {
    EditText editTextEmail, editTextPassword;
    Button buttonLogin;
    TextView textViewRegister;
    UserDao db;
    AppDatabase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        textViewRegister = findViewById(R.id.textViewRegister);

        dataBase = Room.databaseBuilder(this, AppDatabase.class, "mi-database.db")
                .allowMainThreadQueries()
                .build();

        db = dataBase.getUserDao();


        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityLogin.this, RegisterActivity.class));
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                User User = db.getUser(email, password);
                if (User != null) {
                    Intent i = new Intent(ActivityLogin.this, MainActivity.class);
                    i.putExtra("User", User);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(ActivityLogin.this, "Unregistered User, or incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
