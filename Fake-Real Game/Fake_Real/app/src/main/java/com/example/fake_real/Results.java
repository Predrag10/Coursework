package com.example.fake_real;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.fake_real.ConfidenceLevel.imageToDBBoi;

public class Results extends AppCompatActivity {

    TextView first;
    TextView second;
    TextView third;
    TextView fourth;
    TextView fifth;
    TextView sixth;
    TextView seventh;

    Button buttonNext;
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent i = getIntent();

        buttonNext = findViewById(R.id.buttonNext);
        imageView2 = findViewById(R.id.imageView2);
        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        third = findViewById(R.id.third);
        fourth = findViewById(R.id.fourth);
        fifth = findViewById(R.id.fifth);
        sixth = findViewById(R.id.sixth);
        seventh = findViewById(R.id.seventh);

        first.setText("You think the \n image is " + ConfidenceLevel.isReal);
        second.setText("With confidence \n level: " + ConfidenceLevel.confidenceDBBoi + "%");
        third.setText(i.getStringExtra("realper") + "% of people \n think this is real");
        fourth.setText("With confidence \n level: " + i.getStringExtra("realavg") + "%");
        fifth.setText(i.getStringExtra("fakeper") + "% of people \n think this is fake");
        sixth.setText("With confidence \n level: " + i.getStringExtra("fakeavg") + "%");
        seventh.setText("THIS IMAGE IS " + i.getStringExtra("real?"));

        imageView2.setImageResource(ConfidenceLevel.images[imageToDBBoi]);


        buttonNext.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                finish();
            }
        });
    }
}