package com.example.onlinevotingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import papaya.in.sendmail.SendMail;

public class ForgotPassword extends AppCompatActivity {
    EditText forgot_usn,email;
    Button send,vocancel;
    String msg;
    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mydb=new DatabaseHelper(this);

        forgot_usn=(EditText)findViewById(R.id.forgot_usn);
        email=(EditText)findViewById(R.id.email);
        send=(Button)findViewById(R.id.send);
        vocancel=(Button)findViewById(R.id.vocancel);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usn=forgot_usn.getText().toString();
                String fmail=email.getText().toString();
                String pwd=mydb.fetchpwd(usn);
                if(pwd=="None") {
                    msg="Hello Voter, Your details are not available. Make sure you have the Right to Vote.";
                } else {
                    msg="Hey Voter, Kindly use this password to login:"+pwd;
                }
                final String username="avoting19@gmail.com";
                final String password="admin@voting";
                if(usn.equals("") || fmail.equals(""))
                    Toast.makeText(ForgotPassword.this, "Please enter all the details!!", Toast.LENGTH_SHORT).show();
                else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    Toast.makeText(getApplicationContext(),"Invalid Email!!",Toast.LENGTH_SHORT).show();
                }
                else {
                    SendMail mail = new SendMail(username, password, email.getText().toString(), "Password Recovery", msg);
                    mail.execute();
                    Toast.makeText(ForgotPassword.this, "Successful..!! Please wait for the email with your password.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ForgotPassword.this, VoterLogin.class));
                    finish();
                }
            }
        });
        vocancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPassword.this,VoterLogin.class));
                finish();
            }
        });
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}