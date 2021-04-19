package com.blogspot.rajbtc.gasmonitoring;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        addSlide(AppIntroFragment.newInstance("Hi there","welcome to our project",R.drawable.iot_icon, Color.RED));
        showSkipButton(true);
    }

    @Override
    public void onSkipPressed() {
        super.onSkipPressed();
        finish();
        startActivity(new Intent(getApplicationContext(),MapsActivity.class));
    }

    @Override
    public void onDonePressed() {
        super.onDonePressed();
        finish();
        startActivity(new Intent(getApplicationContext(),MapsActivity.class));
    }
}
