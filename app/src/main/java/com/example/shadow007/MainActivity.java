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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    String kor;
    private  FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_favourite:
                Toast.makeText(getApplicationContext(),"You chose favourite",Toast.LENGTH_SHORT).show();

                return true;
            case R.id.settings:
                Toast.makeText(getApplicationContext(),"You chose settings",Toast.LENGTH_SHORT).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public void login(View view) {
        EditText emailt=findViewById(R.id.email);
        String email= String.valueOf(emailt.getText());
        EditText passt=findViewById(R.id.password);
        String pass= String.valueOf(passt.getText());
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("najava", "signInWithEmail:success");
                            FirebaseUser us = mAuth.getCurrentUser();
                            String ostanato = us.getDisplayName().toString();
                            String[] podatoci = ostanato.split(" ");
                            if(podatoci[0].equals("user")){
                                user(us);}
                            if(podatoci[0].equals("admin")){
                                admin(us);}
                            if(podatoci[0].equals("delivery")){
                                delivery(us);}

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("najava", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });





    }
   private void admin(FirebaseUser user) {
       Intent intent=new Intent(getApplicationContext(),AdminActivity.class);

        startActivity(intent);

    }
    public void user(FirebaseUser user){
        Intent intent=new Intent(this,ProductsActivity.class);

       startActivity(intent);

    }
    public void delivery(FirebaseUser user){
        Intent intent=new Intent(this,  OrdersActivity.class);

        startActivity(intent);

    }

    public void regpage(View view) {

        Intent intent=new Intent(this,RegisterActivity.class);

       startActivity(intent);
    }
}