package com.example.test1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class first_page extends AppCompatActivity {

    Button buyrent, doantebutt, logout ;
    TextView namedisp;
    private AdView mAdView;
    FirebaseUser user;
    DatabaseReference databaseReference;
    BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        mAdView = findViewById(R.id.adView);
        buyrent = findViewById(R.id.rentbutton);
        doantebutt = findViewById(R.id.donatebutton);

        namedisp = findViewById(R.id.namedisplay);
        logout = findViewById(R.id.logout_button);


        navigation = findViewById(R.id.bottomNavigationView);
        navigation.getMenu().findItem(R.id.bottomnav_home).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String dispname = dataSnapshot.child("name").getValue().toString().trim();
                namedisp.setText(dispname);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        buyrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(first_page.this, buyonrentpage.class);
                startActivity(intent);
            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(first_page.this , MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(intent);
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener

            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override

        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottomnav_home:
                    break;
                case R.id.bottomnav_profile:
                    Intent intent = new Intent(first_page.this , Profile.class);
                    finish();
                    startActivity(intent);
                    break;
                case R.id.bottomnav_sell:
                    Intent intent1 = new Intent(first_page.this , Sell_clothes_page.class);
                    startActivity(intent1);
                    break;
                case R.id.bottomnav_cart:
                    Intent intent2 = new Intent(first_page.this , Cart.class);
                    startActivity(intent2);
                    break;
            }
            return false;
        }
    };

}
