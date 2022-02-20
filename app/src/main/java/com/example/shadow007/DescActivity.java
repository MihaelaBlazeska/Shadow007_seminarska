package com.example.shadow007;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
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

public class DescActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_desc);
        TextView title=findViewById(R.id.title);
        title.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),android.R.anim.fade_in));
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
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                quantity = Integer.parseInt(spinner.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

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
                        TextView t1 = findViewById(R.id.brand);
                        t1.setText(product.getBrand());
                        TextView t2 = findViewById(R.id.model);
                        t2.setText(product.getName());
                        TextView t3 = findViewById(R.id.desc);
                        t3.setText(product.getDesc());
                        //treba sika
                        TextView t4 = findViewById(R.id.price);
                        t4.setText(String.valueOf(product.getPrice()) + "mkd");
                        ImageView img=findViewById(R.id.slika);
                        Resources res = DescActivity.this.getResources();
                        String mDrawableName = product.getImage();
                        int resID = res.getIdentifier(mDrawableName , "drawable", DescActivity.this.getPackageName());
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
      Toast.makeText(DescActivity.this,"You successfully added to cart",Toast.LENGTH_LONG).show();


        //databaseReference.child(key).child("vid").setValue("/");

    }
    public void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DescActivity.this);

// I'm using fragment here so I'm using getView() to provide ViewGroup
// but you can provide here any other instance of ViewGroup from your Fragment / Activity
        View viewInflated = LayoutInflater.from(DescActivity.this).inflate(R.layout.logout,null);
// Set up the input


        builder.setView(viewInflated);

// Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(DescActivity.this,"Bye",Toast.LENGTH_SHORT).show();
                Intent intent2=new Intent(DescActivity.this,MainActivity.class);
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
    }
}