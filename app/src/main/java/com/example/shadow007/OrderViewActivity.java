package com.example.shadow007;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class OrderViewActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    int stavaj=0;

    public static final int OPEN_NEW_ACTIVITY = 123456;


    RecyclerView mRecyclerView;
    String uid;
    String kor;
    myAdapter2 mAdapter;
    User us;
    Spinner spinner;
    String od="";
    int moze=1;
    int i;
    int j=0,k=0;
    Product product;
    String []TO=new String[1];
    String key;
    Order order;
    int count=0;
    String ord_key;
    ArrayList<Product> orders=new ArrayList<>();
    ArrayList<Integer> quan=new ArrayList<>();
    int quantity;
    public static int final_price=0;

    private CartViewActivity.RecyclerViewReadyCallback recyclerViewReadyCallback;

    private DatabaseReference databaseReference;
    com.google.android.gms.maps.model.LatLng myLocation,loc2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_view);
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();
        String ostanato = user.getDisplayName().toString();
        String[] podatoci = ostanato.split(" ");
        kor = podatoci[0];


        ConstraintLayout layout=findViewById(R.id.map);
        if(kor.equals("delivery")){
            layout.setVisibility(View.VISIBLE);}
        Intent intent=getIntent();
        String id=intent.getStringExtra("order");
        databaseReference = FirebaseDatabase.getInstance().getReference(Order.class.getSimpleName());
        Query q=FirebaseDatabase.getInstance().getReference("Order").orderByChild("id").equalTo(Integer.valueOf(id));
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

    public void accept(View view) {
        view.setVisibility(View.GONE);


        for(int i=0;i<orders.size();i++){

            String name=orders.get(i).getName();
            databaseReference = FirebaseDatabase.getInstance().getReference(Product.class.getSimpleName());
            count=1;
            Query q=FirebaseDatabase.getInstance().getReference("Product").orderByChild("name").equalTo(name);
            if(q!=null){
                //Toast.makeText(getApplicationContext(),"pROCITAV",Toast.LENGTH_LONG).show();
                q.addListenerForSingleValueEvent(valueEventListener);
            }
            else{

            }


        }
        if(moze==1){
            stavaj=1;
            k=0;
            for(int i=0;i<orders.size();i++){
                String name=orders.get(i).getName();
                databaseReference = FirebaseDatabase.getInstance().getReference(Product.class.getSimpleName());
                count=1;
                Query q=FirebaseDatabase.getInstance().getReference("Product").orderByChild("name").equalTo(name);
                if(q!=null){
                    //Toast.makeText(getApplicationContext(),"pROCITAV",Toast.LENGTH_LONG).show();
                    q.addListenerForSingleValueEvent(valueEventListener);
                }
                else{

                }







            }
            databaseReference = FirebaseDatabase.getInstance().getReference("Order");
            databaseReference.child(ord_key).child("processed").setValue(1);
            TextView t1=findViewById(R.id.wait_del);
            t1.setVisibility(View.VISIBLE);
            view.setVisibility(View.GONE);
            Toast.makeText(OrderViewActivity.this,"Success",Toast.LENGTH_LONG).show();
            od="processed";
            Query q=FirebaseDatabase.getInstance().getReference("User").orderByChild("uid").equalTo(order.getUser());
            if(q!=null){
                //Toast.makeText(getApplicationContext(),"pROCITAV",Toast.LENGTH_LONG).show();
                q.addListenerForSingleValueEvent(valueEventListener2);
            }
            else{
            }


        }else{
            Toast.makeText(OrderViewActivity.this,"The order can't be processed right now, you don't have the required quantity of the products.",Toast.LENGTH_LONG).show();
        }

    }




        public void accept_for_del(View view) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Order");
        databaseReference.child(ord_key).child("delivery").setValue(uid);
        view.setVisibility(View.GONE);
        od="shipped";
            Query q=FirebaseDatabase.getInstance().getReference("User").orderByChild("uid").equalTo(order.getUser());
            if(q!=null){
                //Toast.makeText(getApplicationContext(),"pROCITAV",Toast.LENGTH_LONG).show();
                q.addListenerForSingleValueEvent(valueEventListener2);
            }
            else{
            }

    }

    public void ship(View view) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Order");
        databaseReference.child(ord_key).child("delivered").setValue(1);
        TextView t1=findViewById(R.id.shipped);
        t1.setVisibility(View.VISIBLE);
        view.setVisibility(View.GONE);


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
    com.google.firebase.database.ValueEventListener valueEventListener=new com.google.firebase.database.ValueEventListener() {

        @Override
        public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {

            if (snapshot.exists()) {

                for (com.google.firebase.database.DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if(count==0){

                    order = dataSnapshot.getValue(Order.class);
                    ord_key=dataSnapshot.getKey();


                    if (order.equals(null)) {

                        Toast.makeText(getApplicationContext(), "Nemse nisto", Toast.LENGTH_LONG).show();
                    } else {
                        //Toast.makeText(getApplicationContext(), String.valueOf(order.getId()), Toast.LENGTH_LONG).show();
                        orders=order.getOrder();
                        quan=order.getQuantity();
                        final_price=order.getFinal_price();
                        if(kor.equals("admin")&&order.getProcessed()==0){
                            Button accept=findViewById(R.id.accept);
                            accept.setVisibility(View.VISIBLE);

                        }
                        if(kor.equals("admin")&&order.getProcessed()==1&&order.getDelivered()==0){
                            TextView t1=findViewById(R.id.wait_del);
                            t1.setVisibility(View.VISIBLE);
                        }
                        if(order.getProcessed()==1&&order.getDelivered()==1){
                            TextView t1=findViewById(R.id.shipped);
                            t1.setVisibility(View.VISIBLE);
                        }
                        if(kor.equals("delivery")&&order.getDelivery().equals("/")){
                            Button accept_del=findViewById(R.id.accept2);
                            accept_del.setVisibility(View.VISIBLE);

                        }

                        if(kor.equals("delivery")&&order.getDelivery().equals(uid)&&order.getDelivered()==0){
                            Button ship=findViewById(R.id.ship);
                            ship.setVisibility(View.VISIBLE);

                        }
                        mRecyclerView = (RecyclerView) findViewById(R.id.view);
                        mRecyclerView.setHasFixedSize(true);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(OrderViewActivity.this));
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mAdapter = new myAdapter2(OrderViewActivity.this,orders,"user",quan);
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
                        recyclerViewReadyCallback = new CartViewActivity.RecyclerViewReadyCallback() {
                            @Override
                            public void onLayoutReady() {
                                TextView final_pr=findViewById(R.id.total_price);
                                final_pr.setText(String.valueOf(final_price)+" mkd");
                                //myLocation=getMyLocation();

                                myLocation=new com.google.android.gms.maps.model.LatLng(41.9981,21.4254);
                                Location startPoint=new Location("locationA");
                                startPoint.setLatitude(myLocation.latitude);
                                startPoint.setLongitude(myLocation.longitude);
                                Location endPoint=new Location("locationA");
                                if(order.getDelivery_add()!=null){
                                    endPoint.setLatitude(order.getDelivery_add().getLatitude());
                                    endPoint.setLongitude(order.getDelivery_add().getLongitude());
                                    loc2=new LatLng(order.getDelivery_add().getLatitude(),order.getDelivery_add().getLongitude());

                                    double distance=startPoint.distanceTo(endPoint)/1000;


                                    TextView t5=findViewById(R.id.distance);
                                    t5.setText("User is "+distance+" km away");


                                }
                                Query q=FirebaseDatabase.getInstance().getReference("User").orderByChild("uid").equalTo(order.getUser());
                                if(q!=null){
                                    //Toast.makeText(getApplicationContext(),"pROCITAV",Toast.LENGTH_LONG).show();
                                    q.addListenerForSingleValueEvent(valueEventListener2);
                                }
                                else{
                                }
                            }



                        };


                       }}
                    else{

                        product=dataSnapshot.getValue(Product.class);
                        key=dataSnapshot.getKey();
                        if(stavaj==0){
                            Log.d("sporeduvam",product.getName());
                            if(j<orders.size()){
                                Log.d("sporeduvam",product.getName());
                        if(product.getQuantity()<quan.get(j)){
                            moze=0; j++;


                        }

                        }}
                        if(stavaj==1){

                            Log.d("k=",String.valueOf(k));


                            databaseReference = FirebaseDatabase.getInstance().getReference("Product");
                        if(k<orders.size()){
                            Log.d("odzemam",product.getName());

                            databaseReference.child(key).child("quantity").setValue(product.getQuantity()-quan.get(k));
                            k++;}
                        }




                    }

                }
            } else {
                Toast.makeText(getApplicationContext(), "Snapshot ne postoi", Toast.LENGTH_LONG).show();

            }
        }





        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }


    };
    com.google.firebase.database.ValueEventListener valueEventListener2=new com.google.firebase.database.ValueEventListener() {

        @Override
        public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {

            if (snapshot.exists()) {

                for (com.google.firebase.database.DataSnapshot dataSnapshot : snapshot.getChildren()) {


                     us= dataSnapshot.getValue(User.class);



                    if (us.equals(null)) {

                        Toast.makeText(getApplicationContext(), "Nemse nisto", Toast.LENGTH_LONG).show();
                    } else {
                        TO[0]=us.getEmail();
                        String text="";
                        if(od.equals("processed")){
                            text="Your order is processed. You will receive an email when the order is shipped.";

                        }
                        if(od.equals("shipped")){
                            text="Your order is shipped. You will receive it soon.";
                        }
                        else{
                            TextView t1=findViewById(R.id.voul);
                            t1.setText(us.getName()+" "+us.getSurname());
                            TextView t2=findViewById(R.id.phone);
                            t2.setText(us.getPhone());
                            TextView t3=findViewById(R.id.email);
                            t3.setText(us.getEmail());
                            break;
                        }
                        sendEmail(text,TO,"Order "+String.valueOf(order.id)+" processed");



                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), "Snapshot ne postoi", Toast.LENGTH_LONG).show();

            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

        private void cartview() {
        Intent intent = new Intent(this, CartViewActivity.class);
        startActivity(intent);
    }





    public void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderViewActivity.this);

// I'm using fragment here so I'm using getView() to provide ViewGroup
// but you can provide here any other instance of ViewGroup from your Fragment / Activity
        View viewInflated = LayoutInflater.from(OrderViewActivity.this).inflate(R.layout.logout,null);
// Set up the input


        builder.setView(viewInflated);

// Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(OrderViewActivity.this,"Bye",Toast.LENGTH_SHORT).show();
                Intent intent2=new Intent(OrderViewActivity.this,MainActivity.class);
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
    public void showmap(View view) {
        Bundle args = new Bundle();
        args.putParcelable("latlng2",loc2);
        Intent intent = new Intent(this, MapsActivity2.class);
        intent.putExtra("latlng",args);
        startActivityForResult(intent,OPEN_NEW_ACTIVITY);
    }
    protected void sendEmail(String text,String[] TO,String subject) {
        Log.i("Send email", "");

        //String[] CC = {"joveskim@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        //emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, text);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(OrderViewActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

}