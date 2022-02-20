package com.example.shadow007;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DescActivity2 extends AppCompatActivity {
    private DatabaseReference databaseReference;
    ArrayList<Product> Products;
    private FirebaseAuth mAuth;
    int count=0;
    Product product;
    String key,key2;
    int quantity=1;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc2);
         Toolbar mytoolbar=findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent=getIntent();
        String model=intent.getStringExtra("model");
        databaseReference = FirebaseDatabase.getInstance().getReference(Product.class.getSimpleName());
        Query q=FirebaseDatabase.getInstance().getReference("Product").orderByChild("name").equalTo(model);
        if(q!=null){
            //Toast.makeText(getApplicationContext(),"pROCITAV",Toast.LENGTH_LONG).show();
            q.addListenerForSingleValueEvent(valueEventListener);
        }
        else{
        }

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
                Intent intent=new Intent(this,SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    com.google.firebase.database.ValueEventListener valueEventListener=new com.google.firebase.database.ValueEventListener() {

        @Override
        public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {

            if (snapshot.exists()) {

                for (com.google.firebase.database.DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    product = dataSnapshot.getValue(Product.class);

                    if (product.equals(null)) {

                        Toast.makeText(getApplicationContext(), "Nemse nisto", Toast.LENGTH_LONG).show();
                    } else {
                        key = dataSnapshot.getKey();
                        EditText t1 = findViewById(R.id.brand);
                        t1.setHint(product.getBrand());
                        EditText t2 = findViewById(R.id.model);
                        t2.setHint(product.getName());
                        EditText t3 = findViewById(R.id.desc);
                        t3.setHint(product.getDesc());
                        //treba sika
                        EditText t4 = findViewById(R.id.price);
                        t4.setHint(String.valueOf(product.getPrice()));
                        EditText t5 = findViewById(R.id.quantity);
                        t5.setHint(String.valueOf(product.getQuantity()));
                        ImageView img=findViewById(R.id.slika);
                        Resources res = DescActivity2.this.getResources();
                        String mDrawableName = product.getImage();
                        int resID = res.getIdentifier(mDrawableName , "drawable", DescActivity2.this.getPackageName());
                        Drawable drawable = res.getDrawable(resID );
                        img.setImageDrawable(drawable );

                        databaseReference = FirebaseDatabase.getInstance().getReference(User.class.getSimpleName());
                        Query q = FirebaseDatabase.getInstance().getReference("Product").orderByChild("name").equalTo(product.getName());
                        if (q != null) {
                            //Toast.makeText(getApplicationContext(),"pROCITAV",Toast.LENGTH_LONG).show();
                            q.addListenerForSingleValueEvent(valueEventListener);

                        } else {
                        }


                        //Toast.makeText(getApplicationContext(),"Staviv",Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), "Snapshot ne postoi", Toast.LENGTH_LONG).show();
                count = 0;
            }
        }




        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }


    };





    public void cart(View view) {
        ProductsActivity.cart.add(product);
        EditText quan=findViewById(R.id.quantity);
        ProductsActivity.quan.add(quantity);
//        but2.setVisibility(View.GONE);
//        but3.setVisibility(View.GONE);
//        databaseReference = FirebaseDatabase.getInstance().getReference("Need");
//        databaseReference.child(key).child("status").setValue("in progress");
//        but2.setVisibility(View.GONE);
//        but3.setVisibility(View.GONE);
        Toast.makeText(DescActivity2.this,"You successfully added to cart",Toast.LENGTH_LONG).show();


        //databaseReference.child(key).child("vid").setValue("/");

    }
    public void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DescActivity2.this);

// I'm using fragment here so I'm using getView() to provide ViewGroup
// but you can provide here any other instance of ViewGroup from your Fragment / Activity
        View viewInflated = LayoutInflater.from(DescActivity2.this).inflate(R.layout.logout,null);
// Set up the input


        builder.setView(viewInflated);

// Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(DescActivity2.this,"Bye",Toast.LENGTH_SHORT).show();
                Intent intent2=new Intent(DescActivity2.this,MainActivity.class);
                startActivity(intent2);


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // cancel button
            }
        });


        builder.show();

    }


    public void edit(View view) {
        EditText t1 = findViewById(R.id.brand);
        String brand=t1.getText().toString();
        if(!t1.getText().toString().isEmpty()){
        if(brand!= product.getBrand()){
            databaseReference = FirebaseDatabase.getInstance().getReference("Product");
            databaseReference.child(key).child("brand").setValue(brand);
            t1.setHint(brand);
            t1.setText("");
            t1.clearFocus();
        }}
        EditText t2 = findViewById(R.id.model);
        String model=t2.getText().toString();
        if(!t2.getText().toString().isEmpty()){
        if(model!= product.getName()){
            databaseReference = FirebaseDatabase.getInstance().getReference("Product");
            databaseReference.child(key).child("name").setValue(model);
            t2.setHint(model);
            t2.setText("");
            t2.clearFocus();
        }}
        EditText t3 = findViewById(R.id.desc);
        String desc=t3.getText().toString();
        if(!t3.getText().toString().isEmpty()){
        if(desc!= product.getDesc()){
            databaseReference = FirebaseDatabase.getInstance().getReference("Product");
            databaseReference.child(key).child("desc").setValue(desc);
            t3.setHint(desc);
            t3.setText("");
            t3.clearFocus();
        }}

        //treba sika
        EditText t4 = findViewById(R.id.price);
        String price=t4.getText().toString();
        if(!t4.getText().toString().isEmpty()){
        if(Integer.valueOf(price)!= product.getPrice()){
            databaseReference = FirebaseDatabase.getInstance().getReference("Product");
            databaseReference.child(key).child("price").setValue(Integer.valueOf(price));
            t4.setHint(price);
            t4.setText("");
            t4.clearFocus();
        }}

        EditText t5 = findViewById(R.id.quantity);
        String quan=t5.getText().toString();
        if(!t5.getText().toString().isEmpty()){
        if(Integer.valueOf(quan)!= product.getQuantity()){
            databaseReference = FirebaseDatabase.getInstance().getReference("Product");
            databaseReference.child(key).child("quantity").setValue(Integer.valueOf(quan));
            t5.setHint(quan);
            t5.setText("");
            t5.clearFocus();
        }}


            databaseReference = FirebaseDatabase.getInstance().getReference("Product");
            databaseReference.child(key).child("discount").setValue(Integer.valueOf(0));


    }
}