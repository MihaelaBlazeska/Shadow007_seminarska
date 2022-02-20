package com.example.shadow007;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
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
import com.example.shadow007.databinding.ActivityMaps2Binding;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ActivityMaps2Binding binding;
    private LatLng myLocation;
    private static final int req_code = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMaps2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
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
                    .title("ЈАС!"));



        }
        Intent intent=getIntent();
        Bundle bundle = intent.getParcelableExtra("latlng");
        com.google.android.gms.maps.model.LatLng loc = bundle.getParcelable("latlng2");
        com.example.shadow007.LatLng location=new com.example.shadow007.LatLng(loc.latitude,loc.longitude);
        LatLng user=new LatLng(location.getLatitude(),location.getLongitude());
        if (user == null) {
            Toast.makeText(this, "Не може да се пристапи до локација. Проверете Settings.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"Postavuvam marker"+user,Toast.LENGTH_LONG).show();
            Marker m=mMap.addMarker(new MarkerOptions()
                    .position(user)
                    .title("User"));



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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return false;
    }
    public void funk(View view) {

        Intent intent=new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }
}