package com.example.test1;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class upload_Clothes_page extends AppCompatActivity {
EditText brand , timeUsed ,desc;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__clothes_page);

    brand  = findViewById(R.id.brand);
    next = findViewById(R.id.nextTopage2_button);
    timeUsed  = findViewById(R.id.timeUsed);
    desc= findViewById(R.id.desc);

        next.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String brand_input = brand.getText().toString().trim();
            String timeUsed_input = timeUsed.getText().toString().trim();
            String desc_input = desc.getText().toString().trim();
            if (checkIfEmpty(brand_input , timeUsed_input , desc_input)) {
                Intent intent = new Intent(upload_Clothes_page.this, upload_clothes_page2.class);
                intent.putExtra("Brand", brand_input);
                intent.putExtra("timeUsed", timeUsed_input);
                intent.putExtra("Desc", desc_input);
                startActivity(intent);
            }
            else{
                Toast.makeText(upload_Clothes_page.this , "Fields cant be empty" , Toast.LENGTH_LONG).show();
            }
        }
    });

    }

    private boolean checkIfEmpty(String brand_input, String timeUsed_input, String desc_input) {
        return !brand_input.isEmpty() && !timeUsed_input.isEmpty() && !desc_input.isEmpty();
    }

}
