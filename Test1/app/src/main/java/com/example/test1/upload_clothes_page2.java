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
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class upload_clothes_page2 extends AppCompatActivity {

    Button choosepic  , next;
    ImageView selectedpic;
    ProgressDialog progressDialog;
    StorageReference mStorageRef;
    Uri selectedImage;
    FirebaseUser user;
    DatabaseReference databaseReference  , databaseReference1;
    StorageReference Ref;
    String Brand , Desc , timeUsed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_clothes_page2);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        Brand =  bundle.getString("Brand");
        Desc = bundle.getString("Desc");
        timeUsed = bundle.getString("timeUsed");

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
        Ref = mStorageRef.child(System.currentTimeMillis()+"."+getExtension(selectedImage));
        Ref.putFile(selectedImage)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(upload_clothes_page2.this,"Successfully Uploaded",Toast.LENGTH_LONG).show();
                        addSeller();
                        progressDialog.dismiss();
                        Intent intent = new Intent(upload_clothes_page2.this , first_page.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("EXIT", true);
                        finish();
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(upload_clothes_page2.this,"Upload Unsuccessful!! Please Try Again ",Toast.LENGTH_LONG).show();
                    }
                });

    }

    public void addSeller()
    {
        Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(final Uri uri) {
                Log.d("TAG", String.valueOf(uri));
                user = FirebaseAuth.getInstance().getCurrentUser();
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String name = Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString().trim();
                        String phone = Objects.requireNonNull(dataSnapshot.child("phone_no").getValue()).toString().trim();
                        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Seller");
                        String url = String.valueOf(uri);
                        Model_Class modelClass = new Model_Class(name,phone,Brand,timeUsed,Desc,url);
                        databaseReference1.child(user.getUid()).setValue(modelClass);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        System.out.println("User Transaction Cancelled");
                    }
                });

            }
        });

}
}
