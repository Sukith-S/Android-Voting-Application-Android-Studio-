package com.example.onlinevotingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class VotersList extends AppCompatActivity {
    ListView voters;
    Button back;
    ArrayAdapter arrayAdapter;
    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voters_list);
        mydb=new DatabaseHelper(this);

        voters=(ListView)findViewById(R.id.voters);
        back=(Button)findViewById(R.id.back);

        ArrayList<String> out=mydb.voterlist();
        arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,out);
        voters.setAdapter(arrayAdapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VotersList.this,AdminPage.class));
                finish();
            }
        });
    }
}