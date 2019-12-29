package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class first_page extends AppCompatActivity {

    Button buyrent, doantebutt, sellbut;
    TextView namedisp;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        mAdView = findViewById(R.id.adView);
        buyrent = findViewById(R.id.rentbutton);
        doantebutt = findViewById(R.id.donatebutton);
        sellbut = findViewById(R.id.buttonsell);
        namedisp = findViewById(R.id.namedisplay);


        Bundle bundle = getIntent().getExtras();

        String dispname = bundle.getString("Name");
        namedisp.setText(dispname);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        buyrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(first_page.this, buyonrentpage.class);
                startActivity(intent);
            }
        });

        sellbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(first_page.this, Sell_clothes_page.class);
                startActivity(intent);
            }
        });
    }

}
