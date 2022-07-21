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

public class AddVoter extends AppCompatActivity {
    EditText addusn,addpwd;
    Button add;
    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_voter);
        mydb=new DatabaseHelper(this);

        addusn=(EditText)findViewById(R.id.addusn);
        addpwd=(EditText)findViewById(R.id.addpwd);
        add=(Button)findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usn=addusn.getText().toString();
                String pwd=addpwd.getText().toString();
                boolean status;
                if(usn.equals("") || pwd.equals(""))
                {
                    Toast.makeText(AddVoter.this, "Please enter all the details!!", Toast.LENGTH_SHORT).show();
                }
                else if (!isValidUSN(usn)){
                    Toast.makeText(AddVoter.this, "Invalid USN", Toast.LENGTH_SHORT).show();
                }
                else {
                    status = mydb.addVoter(addusn.getText().toString(), addpwd.getText().toString());
                    if (status) {
                        Toast.makeText(AddVoter.this, "Voter Added", Toast.LENGTH_SHORT).show();
                        addusn.setText("");
                        addpwd.setText("");
                        startActivity(new Intent(AddVoter.this,AdminPage.class));
                        finish();
                    } else {
                        Toast.makeText(AddVoter.this, "Failed to add Voter!!!", Toast.LENGTH_SHORT).show();
                    }
                }
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