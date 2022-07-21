package com.example.onlinevotingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {
    Button adlogin,adcancel;
    EditText aduname,adpwd;
    DatabaseHelper mydb;
    boolean status;
    String ADMIN,admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        mydb=new DatabaseHelper(this);

        adlogin=(Button)findViewById(R.id.adlogin);
        adcancel=(Button)findViewById(R.id.adcancel);
        aduname=(EditText)findViewById(R.id.aduname);
        adpwd=(EditText)findViewById(R.id.adpwd);
        ADMIN = "ADMIN";
        admin= "admin";
        mydb.insertData(ADMIN,admin);

        adlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=aduname.getText().toString();
                String password=adpwd.getText().toString();
                boolean result;
                if(username.equals("") || password.equals(""))
                {
                    Toast.makeText(AdminLogin.this, "Please enter all the details!!", Toast.LENGTH_SHORT).show();
                }
                else if(result=mydb.searchadmin(username,password)) {
                    startActivity(new Intent(AdminLogin.this,AdminPage.class));
                    Toast.makeText(AdminLogin.this, "Admin Login Successfull!!!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(AdminLogin.this, "Invalid Credentials!!!", Toast.LENGTH_SHORT).show();
            }
        });
        adcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminLogin.this,MainActivity.class));
                finish();
            }
        });
    }
}