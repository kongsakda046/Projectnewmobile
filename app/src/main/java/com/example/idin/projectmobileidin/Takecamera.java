package com.example.idin.projectmobileidin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Takecamera extends AppCompatActivity {
    ImageView ImageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takecamera);

        Button btnCamera = (Button) findViewById(R.id.btnCamera);
        ImageView2 = (ImageView) findViewById(R.id.imageView2);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });
    }
        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            Bitmap bitmat = (Bitmap) data.getExtras().get("data");
            ImageView2.setImageBitmap(bitmat);
        }
    }

