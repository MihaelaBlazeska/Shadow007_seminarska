package com.example.shadow007;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth=FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Spinner spinner=findViewById(R.id.spinner);


    }


    public void signup(View view) {
        EditText namet=findViewById(R.id.name);
        String name= namet.getText().toString().trim();
        EditText surnamet=findViewById(R.id.surname);
        String surname= surnamet.getText().toString().trim();
        EditText phonet=findViewById(R.id.phone);
        String phone= phonet.getText().toString().trim();
        EditText emailt=findViewById(R.id.email);
        String email= emailt.getText().toString().trim();
        EditText passt1=findViewById(R.id.password1);
        String pass= String.valueOf(passt1.getText());
        EditText passt2=findViewById(R.id.password2);
        String pass2= String.valueOf(passt2.getText());

        if(pass.equals(pass2)){
            mAuth.createUserWithEmailAndPassword(email,pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("registracija", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                //Toast.makeText(getApplicationContext(), kor+" "+name+" "+surname+" "+phone,
                                //Toast.LENGTH_SHORT).show();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName("user " + name+" "+surname+" "+phone+" "+email)
                                        .build();


                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("dopolni", "User profile updated.");
                                                    user(user);
                                                }
                                            }
                                        });
                                Float rating=(float)0.0;
                                User user1=new User(user.getUid(),name,surname,phone,email,"user");

                                databaseReference = FirebaseDatabase.getInstance().getReference("User");
                                databaseReference.push().setValue(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText( getApplicationContext(), "You've successfully registered in realtime database", Toast.LENGTH_SHORT).show();

                                    }
                                });


                            }


                            else {
                                // If sign in fails, display a message to the user.
                                Log.w("registracija", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });}
        else{
            Toast.makeText(getApplicationContext(), "Passwords do not match",
                    Toast.LENGTH_SHORT).show();
            passt1.setText("");
            passt2.setText("");

        }

    }

    public void loginpage(View view) {
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
    public void user(FirebaseUser user){

        Intent intent=new Intent(getApplicationContext(),ProductsActivity.class);
        intent.putExtra("user",user);
        startActivity(intent);

    }}