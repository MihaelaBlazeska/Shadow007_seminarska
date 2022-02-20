package com.example.shadow007;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.shadow007.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback, GoogleMap.OnMarkerClickListener {


    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private LatLng myLocation;
    private static final int req_code = 1;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        String uid=user.getUid();
        String ostanato = user.getDisplayName().toString();
        String[] podatoci = ostanato.split(" ");
        String kor=podatoci[0];
        email=podatoci[4];


        databaseReference = FirebaseDatabase.getInstance().getReference("Order");



        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLoadedCallback(this);
    }

    @Override
    public void onMapLoaded() {

        LatLngBounds bounds = new LatLngBounds(new LatLng(41.513707, 20.890427), new LatLng(42.071378, 21.919809));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));
        mMap.setOnMarkerClickListener(this);

        //myLocation = getMyLocation();
        myLocation=new LatLng(41.9981,21.4254);
        if (myLocation == null) {
            Toast.makeText(this, "Не може да се пристапи до локација. Проверете Settings.", Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(this,"Postavuvam marker"+myLocation,Toast.LENGTH_LONG).show();
            Marker m=mMap.addMarker(new MarkerOptions()
                    .position(myLocation)
                    .title("ЈАС!").draggable(true));
            mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDrag(@NonNull Marker marker) {

                }

                @Override
                public void onMarkerDragEnd(@NonNull Marker marker) {

                    // TODO Auto-generated method stub
                    myLocation=new LatLng(marker.getPosition().latitude,marker.getPosition().longitude);
                    Toast.makeText(getApplicationContext(),"Location: ("+marker.getPosition().latitude+","+marker.getPosition().longitude+")",Toast.LENGTH_LONG).show();


                }

                @Override
                public void onMarkerDragStart(@NonNull Marker marker) {

                }
            });

        }


    }


    private LatLng getMyLocation() {
        // обид за добивање локација на еден од три начини: GPS, cell/wifi мрежа и пасивен режим
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, req_code);
        }

        Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (loc == null) {
            loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (loc == null) {
            loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        }
        if (loc == null) {
            return null;
        } else {
            double myLat = loc.getLatitude();
            double myLng = loc.getLongitude();
            return new LatLng(myLat, myLng);
        }
    }

    public void onRequestPermissionsResult(int reqCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(reqCode, permissions, grantResults);
    }

    public boolean onMarkerClick(Marker marker) {
        if (myLocation != null) {
            LatLng markerLatLng = marker.getPosition();
            mMap.addPolyline(new PolylineOptions()
                    .add(myLocation)
                    .add(markerLatLng)
            );
            return true;
        } else {
            return false;
        }
    }
    public void funk(View view) {
        User delivery=new User();
        Random rand=new Random();
        int id= rand.nextInt(1000000000);
        com.example.shadow007.LatLng delivery_add=new com.example.shadow007.LatLng(myLocation.latitude,myLocation.longitude);
        Order order=new Order(ProductsActivity.cart,delivery_add,user.getUid(),"/",CartViewActivity.final_price,id,ProductsActivity.quan);


        databaseReference.push().setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText( getApplicationContext(), "You've successfully added new Order", Toast.LENGTH_SHORT).show();
                ProductsActivity.cart=new ArrayList<Product>();
                ProductsActivity.quan=new ArrayList<Integer>();
                CartViewActivity.final_price=0;
                String[] TO=new String[1];
                TO[0]=email;

                sendEmail("Thank you for your order. You will soon receive an email with a confirmation.",TO,"Order "+String.valueOf(id)+" received");
                bye();

            }
        });


    }

    private void bye() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
        View viewInflated = LayoutInflater.from(MapsActivity.this).inflate(R.layout.success,null);

        builder.setView(viewInflated);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent2=new Intent(MapsActivity.this,ProductsActivity.class);
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
            Toast.makeText(MapsActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}