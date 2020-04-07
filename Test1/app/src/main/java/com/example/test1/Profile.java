package com.example.test1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Profile extends AppCompatActivity {

    EditText name , email , phone;
    Button submit , edit;
    TextView reset;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference databaseReference ;
    BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        edit = findViewById(R.id.edit_button);
        submit = findViewById(R.id.submit_profile);
        reset = findViewById(R.id.reset_pass_field);
        name = findViewById(R.id.profile_name_field);
        email = findViewById(R.id.profile_email_field);
        phone = findViewById(R.id.profile_phone_field);

        navigation = findViewById(R.id.bottomNavigationView_profile);
        navigation.getMenu().findItem(R.id.bottomnav_profile).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String username = Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();
                String userphone = Objects.requireNonNull(dataSnapshot.child("phone_no").getValue()).toString();
                String useremail = user.getEmail();
                name.setText(username);
                email.setText(useremail);
                phone.setText(userphone);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Profile.this, "Client doesn't have permission to read from that database reference.", Toast.LENGTH_LONG).show();
            }
        });

        name.setFocusable(false);
        name.setCursorVisible(false);

        email.setFocusable(false);
        email.setCursorVisible(false);

        phone.setFocusable(false);
        phone.setCursorVisible(false);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setFocusableInTouchMode(true);
                name.setCursorVisible(true);
                phone.setFocusableInTouchMode(true);
                phone.setCursorVisible(true);
                submit.setVisibility(View.VISIBLE);
                edit.setVisibility(View.GONE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String username = name.getText().toString();
            String userphone = phone.getText().toString();
            Model_Class modelClass = new Model_Class(username,userphone);
            databaseReference.setValue(modelClass)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Log.d("TAG", "onSuccess:Updated in Users ");
                        }
                    });



            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.sendPasswordResetEmail(Objects.requireNonNull(user.getEmail()))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                { Toast.makeText(Profile.this , "Reset link sent to "+user.getEmail() , Toast.LENGTH_SHORT).show();
                                finish();
//                                    Toast.makeText(Profile.this , "Please Log in Again" , Toast.LENGTH_LONG).show();
                                }
                                else
                                    Toast.makeText(Profile.this , "Unable to send reset link" , Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener

            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override

        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottomnav_home:
                    Intent intent = new Intent(Profile.this , Home_Page.class);
                    finish();
                    startActivity(intent);
                    break;
                case R.id.bottomnav_profile:
                    break;
                case R.id.bottomnav_cart:
                    Intent intent1 = new Intent(Profile.this , Cart.class);
                    startActivity(intent1);
                    break;
                case R.id.bottomnav_sell:
                    Intent intent2 = new Intent(Profile.this , Sell_clothes_page.class);
                    startActivity(intent2);
                    break;

            }
            return false;
        }
    };

}
