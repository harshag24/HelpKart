package com.example.test1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Cart extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference, databaseReference1;
    FirebaseRecyclerAdapter<Model_Class, rentpagerecyclerviewAdapter> firebaseRecyclerAdapter;
    FirebaseUser user;
    Button place_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        place_order = findViewById(R.id.place_order_button);
        recyclerView = findViewById(R.id.cart_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("User_Cart")
                                                          .child(user.getUid());


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String key = dataSnapshot1.getKey();

                    showData(key );

                    Log.e("TAG", "onCreate: key :" + key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot removeSnapshot: dataSnapshot.getChildren()) {
                            removeSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Toast.makeText(Cart.this , "Order Placed , Thank you for shopping with us!!" , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Cart.this , Home_Page.class);
                finish();
                startActivity(intent);
            }
        });
    }

    protected void showData(final String key ) {

        databaseReference1= databaseReference.child(key);
        FirebaseRecyclerOptions<Model_Class> options = new FirebaseRecyclerOptions.Builder<Model_Class>().setQuery(databaseReference1, Model_Class.class).build();

        firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Model_Class, rentpagerecyclerviewAdapter>(options) {
                    @NonNull
                    @Override
                    public rentpagerecyclerviewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rentpage_recycler_view_list_item , parent, false);
                        return  new rentpagerecyclerviewAdapter(v);
                    }
                    @Override
                    protected void onBindViewHolder(@NonNull rentpagerecyclerviewAdapter holder, final int position, @NonNull final Model_Class model) {
                        holder.setDetails(model.getBrand() , model.getTimeUsed() ,model.getUrl(), model.getPrice());

                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
        firebaseRecyclerAdapter.notifyDataSetChanged();

    }
}
