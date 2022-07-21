package com.example.onlinevotingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import java.util.ArrayList;
import java.util.List;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class PieChart extends AppCompatActivity {
    PieChartView pieChartView;
    Button back;
    String first="Kavana",second="Shrinidhi";
    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        mydb=new DatabaseHelper(this);

        pieChartView = findViewById(R.id.chart);
        back=(Button)findViewById(R.id.back);

        int icon1 = mydb.viewResult(first);
        int icon2 = mydb.viewResult(second);

        List pieData = new ArrayList<>();
        pieData.add(new SliceValue(icon1, Color.RED).setLabel("Kavana:"+icon1));
        pieData.add(new SliceValue(icon2, Color.GREEN).setLabel("Shrinidhi:"+icon2));

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartData.setHasCenterCircle(true).setCenterText1("Vote Result").setCenterText1FontSize(30).setCenterText1Color(Color.parseColor("#000000"));
        pieChartView.setPieChartData(pieChartData);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PieChart.this,AdminPage.class));
                finish();
            }
        });
    }
}