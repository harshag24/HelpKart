package com.example.test1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ItemDisplay extends AppCompatActivity {
    TextView name , phone , desc;
    ImageView image;
    Button add_to_cart;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_display);

        add_to_cart = findViewById(R.id.add_to_cart);
        name = findViewById(R.id.name_disp);
        phone = findViewById(R.id.phone_disp);
        desc = findViewById(R.id.desc_disp);
        image = findViewById(R.id.image_disp);
        String user_id =  getIntent().getStringExtra("user");
        assert user_id != null;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Seller").child(user_id);
        databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String username = Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString().trim();
               String userphone = Objects.requireNonNull(dataSnapshot.child("phone_no").getValue()).toString().trim();
               String userdesc = Objects.requireNonNull(dataSnapshot.child("desc").getValue()).toString().trim();
               String url = Objects.requireNonNull(dataSnapshot.child("url").getValue()).toString().trim();
               name.setText(username);
               phone.setText(userphone);
               desc.setText(userdesc);
               Picasso.get().load(url).into(image);
           }
           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

    }

}
