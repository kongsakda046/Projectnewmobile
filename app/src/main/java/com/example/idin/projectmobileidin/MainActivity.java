package com.example.idin.projectmobileidin;

import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth FirebaseMember;
    private EditText email,password,name;
    private Button sigin,sigup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMember=FirebaseAuth.getInstance();

        sigin=(Button)findViewById(R.id.login_btn_login);
        sigup=(Button)findViewById(R.id.Sign_btn_up);
        email=(EditText)findViewById(R.id.login_email);
        password=(EditText)findViewById(R.id.login_password);
        name =(EditText)findViewById(R.id.edittext);

        if (FirebaseMember.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),SignIn.class));
        }
        sigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getemail = email.getText().toString().trim();
                String getpassword=password.getText().toString().trim();
                callsigin(getemail,getpassword);


            }
        });
        sigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getemail = email.getText().toString().trim();
                String getpassword=password.getText().toString().trim();
                callsignup(getemail,getpassword);

            }
        });

    }





    private void callsignup(String email,String password){
        FirebaseMember.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        Log.d("TESTING","Sing up Success"+task.isSuccessful());

                        if (!task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Sign up Fail",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            userProfile();
                            Toast.makeText(MainActivity.this,"Created Account",Toast.LENGTH_SHORT).show();
                            Log.d("TESTING","Create Account");
                        }
                    }



                });

    }

    private void userProfile() {
        FirebaseUser user=FirebaseMember.getCurrentUser();
        if (user!=null){
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name.getText().toString().trim())
                    .build();
            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Log.d("Testing","User profile update");
                            }
                        }
                    });
        }
    }
    private void callsigin(String email,String password) {
        FirebaseMember.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TESTING", "Sing in Success" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.v("TESTING","signINwithEmail:failed",task.getException());
                            Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                        } else {
                            Intent i = new Intent(MainActivity.this, SignIn.class);
                            finish();
                            startActivity(i);
                        }
                    }


                });
    }

}