package com.example.idin.projectmobileidin;

/**
 * Created by I'din na on 12/20/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by I'din na on 12/20/2017.
 */

public class SignIn extends AppCompatActivity {
    Button signout,upload_btn,showData;
    private FirebaseAuth FirebaseMember;
    TextView username;

    @Override
    public void onCreate(@NonNull Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_welcome);

        FirebaseMember=FirebaseAuth.getInstance();
        signout=(Button)findViewById(R.id.signout);
        username=(TextView)findViewById(R.id.textView);
        upload_btn=(Button)findViewById(R.id.upload_btn);
        showData=(Button)findViewById(R.id.Btn_showdata);



        if (FirebaseMember.getCurrentUser()==null){
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        FirebaseUser user=FirebaseMember.getCurrentUser();
        if (user!=null){
            username.setText("Welcome,"+user.getDisplayName());
        }
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseMember.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),Uploadinfo.class));
            }
        });
        showData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  startActivity(new Intent(getApplicationContext(),Showdata.class));
            }
        });
    }
}