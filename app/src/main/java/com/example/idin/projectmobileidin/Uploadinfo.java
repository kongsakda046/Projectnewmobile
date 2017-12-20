package com.example.idin.projectmobileidin;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by I'din na on 12/20/2017.
 */

public class Uploadinfo extends AppCompatActivity {

    Button select_image, upload_button;
    ImageView user_image;
    TextView title;

    public static final int READ_EXTERNAL_STORAGE = 0;
    private static final int GALLERY_INTENT = 2;
    private ProgressDialog mProgressDialog;
    private Firebase mRoofRef;
    private Uri mImageUrl = null;
    private DatabaseReference mdatabaseRef;
    private StorageReference mStorage;


    public void onCreate(@NonNull Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.upload_layout);

        Firebase.setAndroidContext(this);

        select_image = (Button) findViewById(R.id.select_image);
        upload_button = (Button) findViewById(R.id.upload_btn);
        user_image = (ImageView) findViewById(R.id.user_image);
        title = (TextView) findViewById(R.id.etTitle);

        //Init Progress Bar
        mProgressDialog = new ProgressDialog(Uploadinfo.this);
        //select image From Strogrss

        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Call For Permision", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
                    } else {
                        callgalary();
                    }
                }

            }


        });
        //Install Database

        mdatabaseRef = FirebaseDatabase.getInstance().getReference();
        mRoofRef = new Firebase("https://data-employee-a5657.firebaseio.com/").child("User_Details").push();
        mStorage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://data-employee-a5657.appspot.com/ ");

        //Click on Upload Button Title
        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mName = title.getText().toString().trim();
                if (mName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill all Filed", Toast.LENGTH_SHORT).show();
                    return;
                }
                Firebase childRef_name = mRoofRef.child("Image_Title");
                childRef_name.setValue(mName);

                Toast.makeText(getApplicationContext(), "Update info", Toast.LENGTH_SHORT).show();
            }

        });

    }


    //check return permision
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                 callgalary();
                    return;

                }
                Toast.makeText(getApplicationContext(), "...", Toast.LENGTH_SHORT).show();
        }

    }

    private void callgalary() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_INTENT);
    }

    //After selecter
    public void onActivityResult(int requsetCode, int resultCode, Intent data) {
        super.onActivityResult(requsetCode, resultCode, data);
        if (requsetCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            mImageUrl = data.getData();
            user_image.setImageURI(mImageUrl);
            StorageReference fillPath = mStorage.child("User_image").child(mImageUrl.getLastPathSegment());

            mProgressDialog.setMessage("Uploading image...");
            mProgressDialog.show();

            fillPath.putFile(mImageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl(); // Igonre
                    mRoofRef.child("Image_URL").setValue(downloadUri.toString());

                    Glide.with(getApplicationContext()).load(downloadUri).crossFade()
                            .placeholder(R.drawable.ic_launcher_background)
                            .diskCacheStrategy(DiskCacheStrategy.RESULT)
                            .into(user_image);
                    Toast.makeText(getApplicationContext(), "Update", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }
            });

        }


    }
}
