package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Sell_clothes_page extends AppCompatActivity {
    Button traditional , party ,general;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_clothes_page);

    traditional =findViewById(R.id.traditionalwear_button);
    party = findViewById(R.id.partywear_button);
    general = findViewById(R.id.generalwear_button);

    }

    public void goto_upload_page(View v)
    {
        Intent intent = new Intent(this , upload_Clothes_page.class);
        startActivity(intent);
    }
}
