package com.example.test1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class buyonrentpage extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseRecyclerAdapter<Model_Class, rentpagerecyclerviewAdapter> firebaseRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyonrentpage);
        recyclerView = findViewById(R.id.recyclerviewrentpage);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Seller");
        showData();
    }

    protected void showData() {
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
                    protected void onBindViewHolder(@NonNull rentpagerecyclerviewAdapter holder, int position, @NonNull Model_Class model) {
                        holder.setDetails(model.getName(),model.getPhone_no(),model.getBrand() , model.getTimeUsed() , model.getDesc(),model.getUrl() );
                    }
                };
        firebaseRecyclerAdapter.startListening();
        firebaseRecyclerAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}