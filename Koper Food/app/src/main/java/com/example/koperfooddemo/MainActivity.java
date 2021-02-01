package com.example.koperfooddemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import static com.example.koperfooddemo.GetRecipes.RetrieveRecipes;

public class MainActivity extends AppCompatActivity {

    public static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginBt, signUpBt;
        TextView forgotPw;
        final EditText username, password;
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBt = findViewById(R.id.login);
        signUpBt = findViewById(R.id.signup);
        forgotPw = findViewById(R.id.forgotPw);

        String encryptPW = EncryptPassword.encrypt(password.getText().toString(), EncryptPassword.ksa("Key"));
        loginBt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TryLogin(MainActivity.this, username.getText().toString(), EncryptPassword.encrypt(password.getText().toString(), EncryptPassword.ksa("Key")));
            }


            public Context con;
            public void SaveComments(String res){
                if(res.equals("Login successful")) {
                    userID = username.getText().toString();
                    RetrieveRecipes(MainActivity.this);
                } else
                    Toast.makeText(MainActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
            }


            public void TryLogin(Context context, String username, String password) {

                String url = "https://www.studenti.famnit.upr.si/~89171025/login.php?username=" + username + "&password=" + password;
                Log.d("GEN_URL", url);
                RequestQueue queue = Volley.newRequestQueue(context);
                con = context;
                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        SaveComments(response);
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("OUTPUT :", "WOPSIE: " + error);
                    }
                });
                queue.add(request);
            }
        });

        signUpBt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUp.class));
            }
        });

        forgotPw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForgotPassword.class));
            }
        });
    }
}





