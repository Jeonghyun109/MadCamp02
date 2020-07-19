package com.example.test1.ui.gallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.bumptech.glide.Glide;
import com.example.test1.R;
import com.github.chrisbanes.photoview.PhotoView;

public class PopupImg extends Activity {
    PhotoView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.img_popup);
        imageView = (PhotoView) findViewById(R.id.expanded_img);

        Intent intent = getIntent();
        String uri = intent.getStringExtra("url");
        Glide.with(this).load(uri).into(imageView);

    }

}