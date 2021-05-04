package com.example.test1;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Objects;

public class reset_pass_dialog extends AppCompatDialogFragment
{
private EditText email;
private FirebaseAuth firebaseAuth;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.reset_password_dialog, null);
        email = view.findViewById(R.id.reset_email);

        builder.setView(view)
                .setTitle("Reset Password")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.out.println("Cancel Clicked");
                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String useremail = email.getText().toString();
                        firebaseAuth = FirebaseAuth.getInstance();
                        firebaseAuth.sendPasswordResetEmail(useremail);
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful())
//                                    Toast.makeText(getContext() , "Reset Link Sent to"+useremail , Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
                    }
                });
        return builder.create();
    }


}
