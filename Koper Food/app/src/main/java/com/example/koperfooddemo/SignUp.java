package com.example.koperfooddemo;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

@RequiresApi(api = Build.VERSION_CODES.N)
public class SignUp extends AppCompatActivity {
    private Button signUpBt;
    private EditText fullName, email, username, password, confirmPassword;
    private TextView birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        pickBirthday();

        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        birthday = findViewById(R.id.selectedDate);
        confirmPassword = findViewById(R.id.password2);

        signUpBt = (Button) findViewById(R.id.signup);
        signUpBt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!(checkUserInput(fullName.getText().toString()) && checkUserInput(email.getText().toString()) && checkUserInput(username.getText().toString()) && checkUserInput(password.getText().toString()) && checkUserInput(birthday.getText().toString()))) {
                    Toast.makeText(SignUp.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();

                }else if (!(password.getText().toString().equals(confirmPassword.getText().toString()))) {
                    Toast.makeText(SignUp.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    saveNewUser(SignUp.this, fullName.getText().toString().replaceAll(" ", "_"), email.getText().toString().replaceAll(" ", "_"), username.getText().toString().replaceAll(" ", "_"), EncryptPassword.encrypt(password.getText().toString(), EncryptPassword.ksa("Key")), birthday.getText().toString());
                    startActivity(new Intent(SignUp.this, MainActivity.class));
                }
            }
        });
    }



    public void pickBirthday() {
        final ImageView birthdate;
        final TextView birthday;
        birthdate = findViewById(R.id.calendarBt);
        birthday = findViewById(R.id.selectedDate);
        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                final int cyear = calendar.get(Calendar.YEAR);
                final int cmonth = calendar.get(Calendar.MONTH);
                final int cdayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUp.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                month += 1;
                                if (day > cdayOfMonth && month > cmonth && year >= cyear) {
                                    day = cdayOfMonth;
                                    Toast.makeText(SignUp.this,"Cannot select that date, changed to today ", Toast.LENGTH_SHORT).show();
                                } if(month > cmonth && year >= cyear) {
                                    month = cmonth + 1;
                                } if(year > cyear) {
                                    year = cyear;
                                    Toast.makeText(SignUp.this,"Cannot select that date, changed to today ", Toast.LENGTH_SHORT).show();
                                }

                                birthday.setText(day + "/" + month + "/" + year);
                            }
                        }, cyear, cmonth, cdayOfMonth);
                datePickerDialog.show();
            }
        });
    }

    public boolean checkUserInput(String input){
        if(input.equals(""))
            return false;
        else
            return true;
    }

    public void saveNewUser(Context context, String name, String email, String username, String password, String birthday){
        String url = "https://www.studenti.famnit.upr.si/~89171025/newUser.php?name="+name+"&birthday="+birthday+"&email="+email+"&username="+username+"&password="+password;
        Log.d("GEN_URL", url);
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("OUTPUT :", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("OUTPUT :","WOPSIE: " + error);
            }
        });
        Log.d("WORKS?", "YES?");
        queue.add(request);
    }
}
