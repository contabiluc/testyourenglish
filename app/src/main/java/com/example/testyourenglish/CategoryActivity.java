package com.example.testyourenglish;

import static com.example.testyourenglish.SpalshActivity.catList;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private GridView catGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        catGrid = findViewById(R.id.catGridView);



        CatgridAdapter adapter = new CatgridAdapter(catList); //acum e catList de la Spalsh Activity
        catGrid.setAdapter(adapter);




    }
    //backbutton
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){

            CategoryActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}