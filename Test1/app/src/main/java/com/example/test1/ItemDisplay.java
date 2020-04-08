package com.example.test1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

public class ItemDisplay extends AppCompatActivity {
    TextView name , phone , desc , price;
    ImageView image;
    Button add_to_cart;
    FirebaseUser user;
    DatabaseReference databaseReference , databaseReference1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_display);

        add_to_cart = findViewById(R.id.add_to_cart);
        name = findViewById(R.id.name_disp);
        phone = findViewById(R.id.phone_disp);
        desc = findViewById(R.id.desc_disp);
        image = findViewById(R.id.image_disp);
        price = findViewById(R.id.price_disp);

        user = FirebaseAuth.getInstance().getCurrentUser();

        final String parent_key = getIntent().getStringExtra("parent_key");
        final String userdesc = getIntent().getStringExtra("description");
        final String url = getIntent().getStringExtra("url");
        final String userprice = getIntent().getStringExtra("price");
        final String product_id = getIntent().getStringExtra("product_id");
        final String userbrand = getIntent().getStringExtra("brand");
        final String usertimeUsed = getIntent().getStringExtra("timeUsed");



        assert parent_key != null;
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(parent_key);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String username = Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();
                String userphone = Objects.requireNonNull(dataSnapshot.child("phone_no").getValue()).toString();
                name.setText("Name - "+username);
                phone.setText("Phone Number - "+userphone);
                price.setText("Price - "+userprice);
                desc.setText("Description - "+userdesc);
                Picasso.get().load(url).into(image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert product_id != null;
                databaseReference1 = FirebaseDatabase.getInstance().getReference("User_Cart")
                        .child(user.getUid())
                        .child(parent_key)
                        .child(product_id);

                HashMap<String, Object> add_product = new HashMap<>();
                add_product.put("brand" , userbrand);
                add_product.put("desc" , userdesc);
                add_product.put("price" , userprice);
                add_product.put("timeUsed" , usertimeUsed);
                add_product.put("url" , url);

                databaseReference1.updateChildren(add_product)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Snackbar.make( findViewById(R.id.ItemDisplayLayout), "Added to Cart" , Snackbar.LENGTH_LONG).show();
                            }
                        });

            }
        });

    }


}
