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
    FirebaseRecyclerAdapter<ModelClass_RentPage , rentpagerecyclerviewAdapter> firebaseRecyclerAdapter;
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

        FirebaseRecyclerOptions<ModelClass_RentPage> options = new FirebaseRecyclerOptions.Builder<ModelClass_RentPage>().setQuery(databaseReference,ModelClass_RentPage.class).build();
        firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ModelClass_RentPage, rentpagerecyclerviewAdapter>(options) {
                    @NonNull
                    @Override
                    public rentpagerecyclerviewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rentpage_recycler_view_list_item , parent, false);
                        return  new rentpagerecyclerviewAdapter(v);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull rentpagerecyclerviewAdapter holder, int position, @NonNull ModelClass_RentPage model) {
                        holder.setDetails(getApplicationContext(),model.getName(),model.getPhone_no(),model.getUri());
                    }
                };

        firebaseRecyclerAdapter.startListening();
        firebaseRecyclerAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }
}
//
//        rentrevyclerview.setLayoutManager(new LinearLayoutManager(this));
//
//        getSupportActionBar().hide();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        String [] sample = {"Java","JavaScript","C","c++","Python","Java","JavaScript",
//                            "C","c++","Python","Java","JavaScript","C","c++","Python"};
//        rentrevyclerview.setAdapter(new rentpagerecyclerviewAdapter(sample));
//