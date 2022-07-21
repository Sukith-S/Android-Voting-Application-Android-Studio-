package com.example.onlinevotingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VoterLogin extends AppCompatActivity {
    EditText usn,vopwd,phone;
    Button vologin,vocancel,forgot;
    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_login);
        mydb=new DatabaseHelper(this);

        usn=(EditText)findViewById(R.id.usn);
        vopwd=(EditText)findViewById(R.id.vopwd);
        vologin=(Button)findViewById(R.id.vologin);
        vocancel=(Button)findViewById(R.id.vocancel);
        forgot=(Button)findViewById(R.id.forgot);
        phone=(EditText)findViewById(R.id.phone);

        vologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vo_usn=usn.getText().toString();
                String vo_pwd=vopwd.getText().toString();
                String num=phone.getText().toString();
                boolean result;
                if(usn.equals("") || vo_pwd.equals("") || phone.equals(""))
                {
                    Toast.makeText(VoterLogin.this, "Please enter all the details!!", Toast.LENGTH_SHORT).show();
                }
                else if (!isValidUSN(vo_usn)){
                    Toast.makeText(VoterLogin.this, "Invalid USN", Toast.LENGTH_SHORT).show();
                }
                else if (phone.getText().length() < 10) {
                    Toast.makeText(getApplicationContext(),"Invalid phone number!!",Toast.LENGTH_SHORT).show();
                }
                else if(result=mydb.searchvoter(vo_usn,vo_pwd)) {
                    boolean output=mydb.addphno(vo_usn,num);
                    if(output) {
                        Intent intent = new Intent(VoterLogin.this, VoterPage.class);
                        intent.putExtra("vo_usn", vo_usn);
                        intent.putExtra("ph_no", num);
                        startActivity(intent);
                        Toast.makeText(VoterLogin.this, "Voter Login Successfull!!!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                else
                    Toast.makeText(VoterLogin.this, "Please make sure your credentials are added by admin!!!", Toast.LENGTH_SHORT).show();
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VoterLogin.this,ForgotPassword.class));
                finish();
            }
        });
        vocancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VoterLogin.this,MainActivity.class));
                finish();
            }
        });
    }
    public boolean isValidUSN(final String usn){
        Pattern pattern;
        Matcher matcher;
        String USN_PATTERN="^(4SO)[1-2][0-9](CS)[0-9]{3}$";
        pattern=Pattern.compile(USN_PATTERN);
        matcher=pattern.matcher(usn);
        return matcher.matches();
    }
}