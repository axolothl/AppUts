package com.example.setditjenp2mkt.apputs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button admin, user;
    EditText nama;
    String username;
    int status_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        admin = (Button)findViewById(R.id.admin);
        user = (Button)findViewById(R.id.user);
        nama = (EditText)findViewById(R.id.editNama) ;


        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = nama.getText().toString();
                if (username.length() != 0){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    status_check = 1;
                    intent.putExtra("status", status_check);
                    intent.putExtra("username", username);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "Masukkan nama anda", Toast.LENGTH_SHORT).show();
                }

            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = nama.getText().toString();
                if (username.length() != 0){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    status_check = 0;
                    intent.putExtra("status", status_check);
                    intent.putExtra("username", username);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "Masukkan nama anda", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
