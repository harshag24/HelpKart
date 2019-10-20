package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

public class buyonrentpage extends AppCompatActivity {

    RecyclerView rentrevyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyonrentpage);
     rentrevyclerview = findViewById(R.id.recyclerviewrentpage);
        rentrevyclerview.setLayoutManager(new LinearLayoutManager(this));

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        String [] sample = {"Java","JavaScript","C","c++","Python","Java","JavaScript","C","c++","Python","Java","JavaScript","C","c++","Python"};
        rentrevyclerview.setAdapter(new rentpagerecyclerviewAdapter(sample));
    }
}
