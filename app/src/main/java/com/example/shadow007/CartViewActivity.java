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
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class CartViewActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;


    RecyclerView mRecyclerView;
    String uid;
    String kor;
    myAdapter2 mAdapter;
    Spinner spinner;
    int quantity;
    public static int final_price=0;


    private RecyclerViewReadyCallback recyclerViewReadyCallback;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view);
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        mRecyclerView = (RecyclerView) findViewById(R.id.view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(CartViewActivity.this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new myAdapter2(CartViewActivity.this,ProductsActivity.cart,"user",ProductsActivity.quan);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (recyclerViewReadyCallback != null) {
                    recyclerViewReadyCallback.onLayoutReady();
                }
                mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        recyclerViewReadyCallback = new RecyclerViewReadyCallback() {
            @Override
            public void onLayoutReady() {
                TextView final_pr=findViewById(R.id.total_price);
                final_pr.setText(String.valueOf(final_price)+" mkd");
            }

        };









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

    public void accept(View view) {
    }

    public void showmap(View view) {
        Intent intent = new Intent(this, MapsActivity2.class);
        startActivity(intent);
    }

    public void accept_for_del(View view) {
    }

    public interface RecyclerViewReadyCallback {
        void onLayoutReady();
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





    public void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CartViewActivity.this);

// I'm using fragment here so I'm using getView() to provide ViewGroup
// but you can provide here any other instance of ViewGroup from your Fragment / Activity
        View viewInflated = LayoutInflater.from(CartViewActivity.this).inflate(R.layout.logout,null);
// Set up the input


        builder.setView(viewInflated);

// Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(CartViewActivity.this,"Bye",Toast.LENGTH_SHORT).show();
                Intent intent2=new Intent(CartViewActivity.this,MainActivity.class);
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


    public void order(View view) {
        //sendEmail();
        Intent intent=new Intent(CartViewActivity.this,MapsActivity.class);
        startActivity(intent);

    }

}