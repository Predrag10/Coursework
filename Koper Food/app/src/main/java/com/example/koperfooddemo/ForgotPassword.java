package com.example.koperfooddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {

    private Button confirmBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        confirmBt = (Button) findViewById(R.id.confirm);
        confirmBt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Link has been sent to your email",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ForgotPassword.this, MainActivity.class));
            }
        });
    }
}
