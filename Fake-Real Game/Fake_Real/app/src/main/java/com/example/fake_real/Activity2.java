package com.example.fake_real;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Activity2 extends AppCompatActivity {

    Button buttonStart;
    AnimationDrawable instructionAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        buttonStart = (Button)findViewById(R.id.button2);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });
        ImageView imageView = (ImageView)findViewById(R.id.image);
        imageView.setBackgroundResource(R.drawable.animation);
        instructionAnimation = (AnimationDrawable) imageView.getBackground();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        instructionAnimation.start();
    }
    public void openActivity3(){
        Intent intent = new Intent(this, ConfidenceLevel.class);
        startActivity(intent);
    }
}
