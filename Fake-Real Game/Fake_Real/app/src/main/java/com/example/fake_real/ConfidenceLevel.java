package com.example.fake_real;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class ConfidenceLevel extends AppCompatActivity implements View.OnTouchListener {

    ImageView imageView;
    ImageView realFake;
    TextView conf;
    Button buttonConfirm;
    ImageView scroll;

    Random r;
    static Integer[] images = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image8,
            R.drawable.image9,
    };

    Integer[] pickedImages = new Integer[images.length];
    int reset = 0;
    int confidencePercent = 0;
    static int confidenceDBBoi, imageToDBBoi;
    static String isReal;
    String currentImage = "image1";
    int randomImage = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confidence_level);

        imageView =  findViewById(R.id.imageView);
        realFake = findViewById(R.id.realFake);
        scroll = findViewById(R.id.droid);
        buttonConfirm = findViewById(R.id.button);

        for(int i = 0; i < images.length; i++){
            pickedImages[i] = 0;
        }


        imageView.setImageResource(images[0]);
        pickedImages[0] = 1;


        r = new Random();

        buttonConfirm.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v){
                imageToDBBoi = randomImage;
                if(confidencePercent <= 0){
                    Toast.makeText(ConfidenceLevel.this, "Confidence level is 0%", Toast.LENGTH_LONG).show();
                }else{
                    if(reset == images.length){
                        for(int i = 0; i < images.length; i++)
                            pickedImages[i] = 0;
                        reset = 0;
                    }

                    randomImage = r.nextInt(images.length);
                    if(randomImage == 0)
                        randomImage++;
                    if(pickedImages[randomImage] == 1){
                        while (pickedImages[randomImage] == 1)
                            randomImage = r.nextInt(images.length);
                    }
                    reset++;
                    pickedImages[randomImage] = 1;
                    confidenceDBBoi = confidencePercent;
                    confidencePercent = 0;
                    conf.setText("Confidence level: " + confidencePercent + "%");
                    scroll.setX(firstX);
                    scroll.setY(firstY);
                    currentImage = "image" + imageToDBBoi;
                    realFake.setImageDrawable(null);
                    test.GO_DB_BOY(ConfidenceLevel.this,currentImage,confidenceDBBoi,isReal);
                    imageView.setImageResource(images[randomImage]);
                }
            }
        });

        scroll.setOnTouchListener(this);
    }


    float x = 0, y = 0;
    float startX, startY, endX, endY, firstX, firstY;
    float prevx = 0.0f, prevy = 0.0f;
    boolean ready = false;
    @Override
    public boolean onTouch(View view, MotionEvent event){
        conf = findViewById(R.id.conf);
        scroll = findViewById(R.id.droid);
        switch(event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                if(!ready){
                    firstX = view.getX();
                    firstY = view.getY();
                    ready = true;
                }
                prevx = view.getX() - event.getRawX();
                startX = view.getX();
                prevy = view.getY() - event.getRawY();
                startY = view.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                view.setX(Math.min(800, Math.max(event.getRawX() + prevx, -100)));
                view.setY(Math.min(1700, Math.max(event.getRawY() + prevy, 750)));
                endX = view.getX();
                endY = view.getY();

                if(firstX > endX){
                    realFake.setImageResource(R.drawable.fake);
                    realFake.setAlpha((float) confidencePercent / 100);
                }else{
                    realFake.setImageResource(R.drawable.real);
                    realFake.setAlpha((float) confidencePercent / 100);
                }


                if ((startY < endY) || (endY >= 1700)) {
                    if(confidencePercent > 0)
                        confidencePercent -= 5;

                    confidencePercent=Math.max(0, confidencePercent);
                    startX = view.getX();
                    startY = view.getY();
                } else {

                    if(confidencePercent < 100)
                        confidencePercent += 5;
                    confidencePercent=Math.min(100, confidencePercent);
                    startX = view.getX();
                    startY = view.getY();
                }
                break;
            default:
                return false;
        }
        if(firstX > endX) {
            isReal = "fake";
        } else {
            isReal = "real";
        }
        conf.setText("Confidence level: " + confidencePercent + "%");
        return true;
    }
}

