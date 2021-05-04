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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class upload_clothes_page2 extends AppCompatActivity {

    Button choosepic  , next;
    ImageView selectedpic;
    ProgressDialog progressDialog;
    StorageReference mStorageRef;
    Uri selectedImage;
    FirebaseUser user;
    DatabaseReference databaseReference , databaseReference1;
    StorageReference Ref;
    String Brand , Desc , timeUsed , price ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_clothes_page2);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        Brand =  bundle.getString("Brand");
        timeUsed = bundle.getString("timeUsed");
        Desc = bundle.getString("Desc");
        price = bundle.getString("price");


        mStorageRef= FirebaseStorage.getInstance().getReference("Images");
        progressDialog = new ProgressDialog(this);
        selectedpic = findViewById(R.id.selectedpic_imageview);
        choosepic = findViewById(R.id.selectpic_button);
        next = findViewById(R.id.nextTopage3_button);
        user = FirebaseAuth.getInstance().getCurrentUser();
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

                        Log.e("TAG", "onClick: "+user.getUid() );
                        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
                        HashMap<String, Object> update_user = new HashMap<>();
                        update_user.put("isSeller" , "true");
                        databaseReference1.updateChildren(update_user);

                        addSeller();
                        progressDialog.dismiss();
                        Intent intent = new Intent(upload_clothes_page2.this , Home_Page.class);
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


                String url = String.valueOf(uri);
                String  current_date;
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:SSS");
                current_date = simpleDateFormat.format(calendar.getTime());

                HashMap<String, Object> add_product = new HashMap<>();
                add_product.put("brand" , Brand );
                add_product.put("desc" , Desc);
                add_product.put("timeUsed" , timeUsed);
                add_product.put("price" , price);
                add_product.put("url" , url);

                databaseReference = FirebaseDatabase.getInstance().getReference().child("Products").child(user.getUid()).child(current_date);
                databaseReference.updateChildren(add_product);
            }
        });
}
}
