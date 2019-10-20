package com.example.test1;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class upload_Clothes_page extends AppCompatActivity {

    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__clothes_page);

    next = findViewById(R.id.nextTopage2_button);

    next.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent change_page_intent = new Intent(upload_Clothes_page.this , upload_clothes_page2.class);
        startActivity(change_page_intent);
        }
    });

    }
}
