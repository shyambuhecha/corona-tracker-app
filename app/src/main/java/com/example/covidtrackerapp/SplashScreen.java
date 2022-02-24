package com.example.covidtrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sharedPreferences = this.getSharedPreferences("splash",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        ImageView imageView = findViewById(R.id.img);


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                if(sharedPreferences.getBoolean("isMain",false)){
                    startActivity(new Intent(SplashScreen.this,MainActivity.class));
                    finish();
                }else {
                    editor.putBoolean("isMain",true);
                    editor.apply();

                    TaskStackBuilder.create(SplashScreen.this)
                            .addNextIntentWithParentStack(new Intent(SplashScreen.this,MainActivity.class))
                            .startActivities();
                }
            }
        },3000);
    }
}