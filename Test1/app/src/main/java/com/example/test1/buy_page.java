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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class buy_page extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference , databaseReference1;
    FirebaseRecyclerAdapter<Model_Class, rentpagerecyclerviewAdapter> firebaseRecyclerAdapter;
    Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_page);
        recyclerView = findViewById(R.id.recyclerviewrentpage);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference1 = FirebaseDatabase.getInstance().getReference("Users");

        query = firebaseDatabase.getReference("Users").orderByChild("isSeller").equalTo("true");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
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

    }

    protected void showData(final String key ) {

        databaseReference= firebaseDatabase.getReference("Products").child(key);
        FirebaseRecyclerOptions<Model_Class> options = new FirebaseRecyclerOptions.Builder<Model_Class>().setQuery(databaseReference, Model_Class.class).build();

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

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String product_id = Objects.requireNonNull(getRef(position).getKey());

                                Intent intent = new Intent(buy_page.this,ItemDisplay.class);

                                intent.putExtra("parent",key);
                                intent.putExtra("description",model.getDesc());
                                intent.putExtra("url",model.getUrl());
                                intent.putExtra("price",model.getPrice());

                                startActivity(intent);
                            }
                        });
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
        firebaseRecyclerAdapter.notifyDataSetChanged();

    }
}