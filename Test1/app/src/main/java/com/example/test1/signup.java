package com.example.test1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class signup extends AppCompatActivity {
    private static final String TAG = "Msg:";
    Button signup2;
    EditText name, email, pass, phone;
    String emailInput, passwordInput, nameInput,phoneInput;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        progressDialog = new ProgressDialog(this);
        signup2 = findViewById(R.id.signupbutton2);
        name = findViewById(R.id.editText_name);
        pass = findViewById(R.id.editText_pass);
        email = findViewById(R.id.editText_email);
        phone = findViewById(R.id.editText_phone);



        mAuth = FirebaseAuth.getInstance();
        signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailInput = email.getText().toString().trim();
                passwordInput = pass.getText().toString().trim();
                nameInput = name.getText().toString().trim();
                phoneInput=phone.getText().toString().trim();
                if (validateName() && validatePassword() && validateEmail() && validatePhone()) {
                    progressDialog.setMessage("Signing Up...");
                    progressDialog.show();
                    createAccount(emailInput, passwordInput);
                }
            }
        });

    }


    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Log.i(TAG, "createUserWithEmail:success ");
                            saveUserInfo();
                            Toast.makeText(signup.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            openhomepage();
                        }
                        else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            progressDialog.dismiss();
                            Toast.makeText(signup.this, "Registration failed.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void saveUserInfo()
    {
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserInfoOnLoginPage userInfoOnLoginPage = new UserInfoOnLoginPage(nameInput,phoneInput);

        databaseReference.child(user.getUid()).setValue(userInfoOnLoginPage);
    }


    public void openhomepage() {
        final Intent intent = new Intent(signup.this, first_page.class);
        intent.putExtra("Name", nameInput);
        finish();
        startActivity(intent);
    }

    public boolean validateEmail() {
        if (emailInput.isEmpty()) {
            email.setError("Field can't be empty");
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("Please enter a valid email address");
            return false;

        } else {
            return true;
        }
    }



    public boolean validatePassword() {

        if (passwordInput.isEmpty()) {
            pass.setError("Field can't be empty");
            return false;
        } else if (passwordInput.length() < 6) {
            pass.setError("Password Should be more than 6 digits");
            return false;
        } else {
            return true;
        }
    }

    public boolean validateName() {
        if (nameInput.isEmpty()) {
            name.setError("Field can't be empty");
            return false;
        } else {
            return true;
        }
    }

    public boolean validatePhone() {
        if (phoneInput.isEmpty()) {
            phone.setError("Field can't be empty");
            return false;
        }
        else if(phoneInput.length()<10)
        {
            phone.setError("Enter 10 digits");
            return false;
        }
        else {
            return true;
        }
    }

}

