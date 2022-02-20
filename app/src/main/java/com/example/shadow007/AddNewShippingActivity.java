package com.example.shadow007;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class AddNewShippingActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    public static ArrayList<Product> cart = new ArrayList<Product>();
    public static ArrayList<Integer> quan = new ArrayList<Integer>();
    RecyclerView mRecyclerView;
    String uid;
    String kor;
    myAdapterOrders mAdapter;
    private ArrayList<Order> orders;
    Query q;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String ostanato = user.getDisplayName().toString();
        String[] podatoci = ostanato.split(" ");
        String kor=podatoci[0];





        databaseReference = FirebaseDatabase.getInstance().getReference("Order");
        q=FirebaseDatabase.getInstance().getReference("Order").orderByChild("delivery").equalTo("/");


        if(q!=null){
            //Toast.makeText(getApplicationContext(),"pROCITAV",Toast.LENGTH_LONG).show();
            q.addListenerForSingleValueEvent(valueEventListener);
        }
        else{
        }





        Toolbar mytoolbar=findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
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
            case R.id.action_cart:
                cartview();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void cartview() {
        Intent intent = new Intent(this, CartViewActivity.class);
        startActivity(intent);
    }


    com.google.firebase.database.ValueEventListener valueEventListener=new com.google.firebase.database.ValueEventListener() {

        @Override
        public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
            orders=new ArrayList<>();
            if(snapshot.exists()) {
                for (com.google.firebase.database.DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    //Toast.makeText(getApplicationContext(),"Ja update list",Toast.LENGTH_LONG).show();
                    Order order = dataSnapshot.getValue(Order.class);

                    if(order.equals(null)){

                        Toast.makeText(getApplicationContext(),"Nemse nisto",Toast.LENGTH_LONG).show();
                    }
                    else{
                        if(order.getProcessed()==1&&order.getDelivered()==0){
                        orders.add(order);}
                        //Toast.makeText(getApplicationContext(),"Staviv"+Needs.contains(need),Toast.LENGTH_LONG).show();
                    }
                }
                mRecyclerView = (RecyclerView) findViewById(R.id.view);
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(AddNewShippingActivity.this));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mAdapter = new myAdapterOrders(AddNewShippingActivity.this,orders);
                mRecyclerView.setAdapter(mAdapter);


            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }


    };
    public void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddNewShippingActivity.this);

// I'm using fragment here so I'm using getView() to provide ViewGroup
// but you can provide here any other instance of ViewGroup from your Fragment / Activity
        View viewInflated = LayoutInflater.from(AddNewShippingActivity.this).inflate(R.layout.logout,null);
// Set up the input


        builder.setView(viewInflated);

// Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(AddNewShippingActivity.this,"Bye",Toast.LENGTH_SHORT).show();
                Intent intent2=new Intent(AddNewShippingActivity.this,MainActivity.class);
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


    public void novi(View view) {
        Intent intent = new Intent(this, AddProductActivity.class);
        startActivity(intent);
    }
}