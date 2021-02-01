package com.example.koperfooddemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Recipe extends AppCompatActivity {



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Intent intent = getIntent();

        Button commentSection;
        ImageButton seeIngredients, seeRestaurants, seeMarkets;
        TextView name, description;
        SeekBar difficulty;

        commentSection = findViewById(R.id.goToCommentsButton);
        seeIngredients = findViewById(R.id.ingredientsDropDown);
        seeRestaurants = findViewById(R.id.restaurantsListDropDown);
        seeMarkets = findViewById(R.id.marketsListDropDown);

        description = findViewById(R.id.recipeText);
        name = findViewById(R.id.recipeName);
        difficulty = findViewById(R.id.difficultySeekBar);


        description.setMovementMethod(new ScrollingMovementMethod());
        String retrieveRecipeInformation = intent.getStringExtra("recipeInfo");
        final String[] recipeInformation = retrieveRecipeInformation.split("%");
        description.setText(recipeInformation[2]);
        name.setText(recipeInformation[1]);
        difficulty.setProgress(Integer.parseInt(recipeInformation[4]));
        difficulty.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        seeIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDropDown(Recipe.this, recipeInformation[5]);
            }
        });

        seeMarkets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDropDown(Recipe.this, recipeInformation[6]);
            }
        });

        seeRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDropDown(Recipe.this, recipeInformation[7]);
            }
        });
        commentSection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GetComments(Recipe.this);
    }


            public Context con;
            public void SaveComments(String res){
                Intent i = new Intent(con, Comments.class);
                i.putExtra("commentsInfo", res);
                i.putExtra("recipeID", recipeInformation[0]);
                con.startActivity(i);
            }


            public void GetComments(Context context) {
                String url = "https://www.studenti.famnit.upr.si/~89171025/getComments.php?recipeID="+recipeInformation[0];
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
    }

    public void showDropDown(Activity activity, String information) {

        final Dialog dialog = new Dialog(activity);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_ingredient_list);

        Button btndialog = (Button) dialog.findViewById(R.id.closeList);
        btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        String[] array = information.split(",");

        final ListView listView = (ListView) dialog.findViewById(R.id.ingredientList);
        final ArrayAdapter arrayAdapter= new ArrayAdapter(this,R.layout.list_item, R.id.tv, array);
        listView.setAdapter(arrayAdapter);

        dialog.show();

    }
}