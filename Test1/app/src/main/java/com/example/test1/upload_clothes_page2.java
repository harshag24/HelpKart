package com.example.test1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class upload_clothes_page2 extends AppCompatActivity {

    Button choosepic  , next;
    ImageView selectedpic;
    ProgressDialog progressDialog;
    StorageReference mStorageRef;
    Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_clothes_page2);
        mStorageRef= FirebaseStorage.getInstance().getReference("Images");

        progressDialog = new ProgressDialog(this);
        selectedpic = findViewById(R.id.selectedpic_imageview);
        choosepic = findViewById(R.id.selectpic_button);
        next = findViewById(R.id.nextTopage3_button);
        mStorageRef=FirebaseStorage.getInstance().getReference();
        choosepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryintent = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryintent , 1 );

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog.setMessage("Uploading...");
                        progressDialog.show();
                        Fileuploader();
                    }
                });

            }
        });

    }
    private String getExtension(Uri uri)
    {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1 &&resultCode== RESULT_OK && data!=null)
        {
             selectedImage = data.getData();
            selectedpic.setImageURI(selectedImage);
        }
    }

    public void Fileuploader()
    {
StorageReference Ref = mStorageRef.child(System.currentTimeMillis()+"."+getExtension(selectedImage));
        Ref.putFile(selectedImage)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(upload_clothes_page2.this,"Successfully Uploaded",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(upload_clothes_page2.this,"Upload Unsuccessful!! Please Try Again ",Toast.LENGTH_LONG).show();
                    }
                });
    }
}
