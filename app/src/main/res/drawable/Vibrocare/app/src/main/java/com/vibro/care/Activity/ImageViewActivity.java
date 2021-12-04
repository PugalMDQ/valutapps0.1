package com.vibro.care.Activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.bumptech.glide.Glide;
import com.vibro.care.R;

import java.util.Objects;

public class ImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();

        final ImageView image_zoom = findViewById(R.id.image_zoom);
        final TextView text_zoom = findViewById(R.id.text_zoom);

        String url = getIntent().getStringExtra("url");
        String text = getIntent().getStringExtra("text");

        text_zoom.setText(text);

        Glide.with(this)
                .load(url)
                .placeholder(R.mipmap.placeholder)
                .into(image_zoom);

        runOnUiThread(() -> image_zoom.setOnTouchListener(new ImageMatrixTouchHandler(image_zoom.getContext())));
    }
}