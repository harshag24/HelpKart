package com.example.test1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    Button login, signup;
    EditText username, passw;
    String email, password;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    FirebaseUser user;

    @Override
    protected void onStart() {
        super.onStart();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(this);
        username = findViewById(R.id.Username_editText);
        passw = findViewById(R.id.Pass_editText);
        login = findViewById(R.id.buttonLogin);
        signup = findViewById(R.id.buttonSignUp);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = username.getText().toString().trim();
                password = passw.getText().toString().trim();
                if (validateEmail() && validatePassword()) {
                    progressDialog.setMessage("Logging In...");
                    progressDialog.show();
                    setLogin();
                }
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, signup.class);
                startActivity(intent);
            }
        });
    }

    public void setLogin() {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.w("MSG", "onComplete: Success");
                            getUserName();
                        } else {
                            Log.w("MSG", "signInWithEmail:failure", task.getException());
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public boolean validateEmail() {
        if (email.isEmpty()) {
            username.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            username.setError("Please enter a valid email address");
            return false;

        } else {
            return true;
        }
    }

    public void getUserName() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                Intent intent = new Intent(MainActivity.this, first_page.class);
                intent.putExtra("Name", name);
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Successfully Logged In", Toast.LENGTH_LONG).show();
                finish();
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Client doesn't have permission to read from that database reference.", Toast.LENGTH_LONG).show();
            }
        });

    }

    public boolean validatePassword() {

        if (password.isEmpty()) {
            passw.setError("Field can't be empty");
            return false;
        } else if (password.length() < 6) {
            passw.setError("Password Should be more than 6 digits");
            return false;
        } else {
            return true;
        }
    }

}


