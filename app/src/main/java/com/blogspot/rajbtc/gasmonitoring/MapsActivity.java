package com.blogspot.rajbtc.gasmonitoring;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String TAG="===Map===";
    private LatLng firstLoc,secLoc;
    MarkerOptions firstMarker,secondMarker;
    private double lat1=24.021843,lon1=90.418759,lat2=22.567176,lon2=91.922845;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        downFire();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        firstLoc = new LatLng(lat1, lon1);
        firstMarker=new MarkerOptions().position(firstLoc).title("First Circuit").icon(BitmapDescriptorFactory.fromResource(R.drawable.robo1));
        mMap.addMarker(firstMarker);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(firstLoc));

        secLoc= new LatLng(lat2, lon2);
        mMap.addMarker(new MarkerOptions().position(secLoc).title("Second Circuit").icon(BitmapDescriptorFactory.fromResource(R.drawable.robo2)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(secLoc));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getApplicationContext(),marker.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MapsActivity.this,DetailsActivity.class);
                if(marker.getTitle().toLowerCase().contains("first"))
                     intent.putExtra("circuit","first");
                else
                    intent.putExtra("circuit","second");
                startActivity(intent);
                return true;
            }
        });

    }




    void downFire(){
        // Read from the database
        FirebaseDatabase.getInstance().getReference("Location").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                try {
                    extractMap(snapshot);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Map Loading\nPlease wait",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                try {
                    extractMap(snapshot);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Map Loading\nPlease wait",Toast.LENGTH_SHORT).show();
                }
                 Log.e(TAG,"Data: "+snapshot.getValue().toString()+"   Key: "+snapshot.getKey().toString());

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void firstLocClick(View view) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(firstLoc));
    }

    public void secondLocClick(View view) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(secLoc));
    }



    void extractMap(DataSnapshot dataSnapshot){
        String val=dataSnapshot.getKey().toLowerCase();
        Log.e(TAG,"Key: "+dataSnapshot.getKey()+"   Value: "+dataSnapshot.getValue().toString());

        if(val.contains("-1")){


            if(val.contains("lat"))
            {

                lat1=Double.parseDouble(dataSnapshot.getValue().toString());


            }
            else if(val.contains("lon")){
                lon1=Double.parseDouble(dataSnapshot.getValue().toString());
            }

            firstLoc=new LatLng(lat1,lon1);
            mMap.clear();
            onMapReady(mMap);



        }else if(val.contains("-2")){



            if(val.contains("lat"))
            {

                lat2=Double.parseDouble(dataSnapshot.getValue().toString());


            }
            else if(val.contains("lon")){
                lon2=Double.parseDouble(dataSnapshot.getValue().toString());
            }

            secLoc=new LatLng(lat2,lon2);
            mMap.clear();

            try{
                onMapReady(mMap);
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Map Loading",Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),"Please wait...",Toast.LENGTH_SHORT).show();
            }


        }




    }


    }



