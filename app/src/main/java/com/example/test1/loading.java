package com.example.test1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

public class loading extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);

        ImageView splashGif = (ImageView)findViewById(R.id.imageView);
        DrawableImageViewTarget gifImage = new DrawableImageViewTarget(splashGif);
        Glide.with(this).load(R.raw.loading_cyworld).into(splashGif);

        startLoading();
    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run(){
                Intent intent = new Intent(getBaseContext(), Launcher.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
