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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Home_Page extends AppCompatActivity {

    Button buyrent, logout ;
    TextView namedisp;
    FirebaseUser user;
    DatabaseReference databaseReference ;
    BottomNavigationView navigation;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        buyrent = findViewById(R.id.rentbutton);


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
                String dispname = Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString().trim();
                namedisp.setText(dispname);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



        buyrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Page.this, buy_page.class);
                startActivity(intent);
            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Home_Page.this , Login_Page.class);
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
                    Intent intent = new Intent(Home_Page.this , Profile.class);
                    finish();
                    startActivity(intent);
                    break;
                case R.id.bottomnav_sell:
                    Intent intent1 = new Intent(Home_Page.this , Sell_clothes_page.class);
                    startActivity(intent1);
                    break;
                case R.id.bottomnav_cart:
                    Intent intent2 = new Intent(Home_Page.this , Cart.class);
                    startActivity(intent2);
                    break;
            }
            return false;
        }
    };


}
