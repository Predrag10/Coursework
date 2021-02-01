package com.example.koperfooddemo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Browse extends AppCompatActivity {

    public static GridView gridViewRecipes;
    public static ArrayAdapter gridViewAdapter;

    public static ArrayList<String[]> RecipeList = new ArrayList<String[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        FloatingActionButton fab = findViewById(R.id.fab);

        FloatingActionButton fab2 = findViewById(R.id.fab2);

        Intent intent = getIntent();
        if (intent.getStringExtra("recipeInfo").equals("")) {
            Toast.makeText(Browse.this, "No results found", Toast.LENGTH_SHORT).show();
        } else {
            String[] separatedRecipes = intent.getStringExtra("recipeInfo").split("<br>");
            for (int j = 0; j < separatedRecipes.length; j++) {
                String[] tmpArray = separatedRecipes[j].split("%");
                RecipeList.add(tmpArray);
            }

            String[] displayNameOfRecipes = new String[RecipeList.size()];
            String[] displayPriceOfRecipes = new String[RecipeList.size()];
            String[] displayDifficultyOfRecipes = new String[RecipeList.size()];
            final String[] gridView = new String[3 * displayNameOfRecipes.length];
            for (int i = 0; i < displayNameOfRecipes.length; i++) {
                displayNameOfRecipes[i] = RecipeList.get(i)[1];
                displayPriceOfRecipes[i] = RecipeList.get(i)[3] + "€";
                displayDifficultyOfRecipes[i] = RecipeList.get(i)[4];
            }

            int j = 0;
            for (int i = 0; i < gridView.length; i += 3) {
                gridView[i] = displayNameOfRecipes[j];
                gridView[i + 1] = displayPriceOfRecipes[j];
                gridView[i + 2] = displayDifficultyOfRecipes[j];
                j++;
            }

            gridViewRecipes = (GridView) findViewById(R.id.gridView);
            gridViewAdapter = new ArrayAdapter(this, R.layout.list_item, R.id.tv, gridView);
            gridViewRecipes.setAdapter(gridViewAdapter);

            gridViewRecipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)

                @Override
                public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                    Intent intent = new Intent(Browse.this, Recipe.class);
                    String str = String.join("%", RecipeList.get(position / 3));
                    intent.putExtra("recipeInfo", str);
                    Browse.this.startActivity(intent);
                }
            });
        }
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Browse.this, AddMeal.class));
                }
            });

            fab2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RecipeList.clear();
                    startActivity(new Intent(Browse.this, Search.class));
                }
            });
    }
    @Override
    public void onBackPressed() {
        RecipeList.clear();
        super.onBackPressed();
    }
}
