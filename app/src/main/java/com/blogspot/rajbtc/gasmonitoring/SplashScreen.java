package com.blogspot.rajbtc.gasmonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {
    private ProgressBar progressBar;
    private Handler handler;
    private Runnable runnable;
    int i=0;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBar=findViewById(R.id.loginProg);
        sp=getSharedPreferences("active",MODE_PRIVATE);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //startActivity(new Intent(MainActivity.this,LoadControl.class));
        handler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(i);
                if(i++<100)
                    handler.postDelayed(runnable,10);
                else{
                    finish();
                    if(sp.getString("is","0").equals("0")){
                        startActivity(new Intent(SplashScreen.this,IntroActivity.class));
                        sp.edit().putString("is","1").apply();
                        finish();
                    }
                    else{
                        finish();
                        startActivity(new Intent(SplashScreen.this,MapsActivity.class));
                    }

                }


            }
        };handler.post(runnable);
    }



}