package com.blogspot.rajbtc.gasmonitoring;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.vaibhavlakhera.circularprogressview.CircularProgressView;

public class DetailsActivity extends AppCompatActivity {

    String TAG="===Details Activity===";
    String firstCir;
    CircularProgressView CO2,CH4,CO,TEMP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if(getIntent().getStringExtra("circuit").toLowerCase().contains("first"))
            firstCir="-1";
        else
            firstCir="-2";


        initView();
        downFire();
    }



    void initView(){
        CO2=findViewById(R.id.co2Pb);
        CH4=findViewById(R.id.ch4Pb);
        CO=findViewById(R.id.coPb);
        TEMP=findViewById(R.id.tempPv);
        CO2.setAnimation(null);
        CH4.setAnimation(null);
        CO.setAnimation(null);

    }





    void setChange(String field, String data){
        Log.e(TAG,"Data: "+data+"   Key: "+field);
        int percent=0;
        try {
             percent=(int) Double.parseDouble(data);
        }catch (Exception e){

        }


        if(field.equals("ch4"))
            CH4.setProgress(percent,true);

        else if(field.equals("co"))
            CO.setProgress(percent,true);

        else if(field.equals("co2"))
            CO2.setProgress(percent,true);
        else if(field.contains("temp"))
            TEMP.setProgress(percent,true);

    }



    void downFire(){


        FirebaseDatabase.getInstance().getReference("Data").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String key=snapshot.getKey().toLowerCase();
                if(key.contains(firstCir))
                    setChange(key.substring(0,key.length()-2),snapshot.getValue().toString());



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String key=snapshot.getKey().toLowerCase();
                if(key.contains(firstCir))
                    setChange(key,snapshot.getValue().toString());


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String key=snapshot.getKey().toLowerCase();
                if(key.contains(firstCir))
                    setChange(key,snapshot.getValue().toString());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String key=snapshot.getKey().toLowerCase();
                if(key.contains(firstCir))
                    setChange(key.substring(0,key.length()-2),snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }



}