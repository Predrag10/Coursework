package com.example.koperfooddemo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;


public class AddMeal extends AppCompatActivity {

    private EditText name, description, ingredient, price, restaurant, market;
    private SeekBar difficulty;

    ArrayList<Ingredient> ingredientList = new ArrayList<>();
    ArrayList<String> RestaurantList = new ArrayList<>();
    ArrayList<String> MarketList = new ArrayList<>();

    String ingredientsDB = "", restaurantsDB = "", marketsDB = "";
    int finalPrice = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button postRecipe;

        Button addMeal, editingredients, addRestaurant, editRestaurants, addMarket, editMarkets;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        name = findViewById(R.id.mealName);
        difficulty = findViewById(R.id.seekBar);
        description = findViewById(R.id.description);
        ingredient = findViewById(R.id.ingredient);
        price = findViewById(R.id.ingredientPrice);
        restaurant = findViewById(R.id.restaurant);
        market = findViewById(R.id.market);

        addMeal = findViewById(R.id.addMealButton);
        addRestaurant = findViewById(R.id.addRestaurantButton);
        addMarket = findViewById(R.id.addMarketButton);

        editingredients = findViewById(R.id.editIngredientsButton);
        editRestaurants = findViewById(R.id.editRestaurantsButton);
        editMarkets = findViewById(R.id.editWhereToFindButton);

        postRecipe = findViewById(R.id.postRecipeButton);

        addMeal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!ingredient.getText().toString().equals("") && !price.getText().toString().equals("")) {
                    Ingredient tmp = new Ingredient(ingredient.getText().toString(), Float.parseFloat(price.getText().toString()));
                    ingredientList.add(tmp);
                    finalPrice += tmp.getPrice();
                    if (ingredientsDB.equals("")) {
                        ingredientsDB += tmp.getName() + "  " + tmp.getPrice() + "€";
                    } else {
                        ingredientsDB += "," + tmp.getName() + "  " + tmp.getPrice() + "€";
                    }
                }
            }
        });

        addRestaurant.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!restaurant.getText().toString().equals("")) {
                    RestaurantList.add(restaurant.getText().toString());
                    if (restaurantsDB.equals("")) {
                        restaurantsDB += restaurant.getText().toString();
                    } else {
                        restaurantsDB += "," + restaurant.getText().toString();
                    }
                }
            }
        });

        addMarket.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!market.getText().toString().equals("")) {
                    MarketList.add(market.getText().toString());
                    if (marketsDB.equals("")) {
                        marketsDB += market.getText().toString();
                    } else {
                        marketsDB += "," + market.getText().toString();
                    }
                }
            }
        });


        editingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogIngredients(AddMeal.this);
            }
        });

        editRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogRestaurants(AddMeal.this);
            }
        });

        editMarkets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogMarkets(AddMeal.this);
            }
        });

        postRecipe.setOnClickListener(new View.OnClickListener() {

            void GO_DB_BOY(Context context, String name, String description, int difficulty, String ingredients, String restaurants, String markets){
                String url = "https://www.studenti.famnit.upr.si/~89171025/postRecipe.php?name="+name+"&description="+description+"&price="+finalPrice+"&difficulty="+difficulty+"&ingredients="+ingredients+"&markets="+markets+"&restaurants="+restaurants+"&username="+MainActivity.userID+"";
                finalPrice = 0;
                Log.d("GEN_URL", url);
                RequestQueue queue = Volley.newRequestQueue(context);

                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("WADUP :", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("WADUP :","WOPSIE: " + error);
                    }
                });
                Log.d("WORKS?", "YES?");
                queue.add(request);
            }

            @Override
            public void onClick(View v) {
                Log.d("ingredients: ", ingredientsDB);
                Log.d("Restaurants: ", restaurantsDB);
                Log.d("Markets: ", marketsDB);
                GO_DB_BOY(AddMeal.this, name.getText().toString(), description.getText().toString(), difficulty.getProgress(), ingredientsDB, restaurantsDB, marketsDB);
                finish();
            }
        });
    }

    public void showDialogIngredients(Activity activity) {

        final Dialog dialog = new Dialog(activity);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_ingredient_list);


        Button btndialog = (Button) dialog.findViewById(R.id.closeList);
        btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

    final ListView listView = (ListView) dialog.findViewById(R.id.ingredientList);
    final ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.list_item, R.id.tv, ingredientList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ingredientList.remove(position);
            arrayAdapter.notifyDataSetChanged();

        }
    });
        Toast.makeText(activity, "Click on item to remove it from the list", Toast.LENGTH_SHORT).show();
        dialog.show();
}

    public void showDialogRestaurants(Activity activity) {

        final Dialog dialog = new Dialog(activity);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_ingredient_list);

        Button btndialog = (Button) dialog.findViewById(R.id.closeList);
        btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final ListView listViewingredients = (ListView) dialog.findViewById(R.id.ingredientList);
        final ArrayAdapter arrayAdapter= new ArrayAdapter(this,R.layout.list_item, R.id.tv, RestaurantList);
        listViewingredients.setAdapter(arrayAdapter);

        listViewingredients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RestaurantList.remove(position);
                arrayAdapter.notifyDataSetChanged();
            }
        });
        Toast.makeText(activity, "Click on item to remove it from the list", Toast.LENGTH_SHORT).show();
        dialog.show();

    }

    public void showDialogMarkets(Activity activity) {

        final Dialog dialog = new Dialog(activity);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_ingredient_list);

        Button btndialog = (Button) dialog.findViewById(R.id.closeList);
        btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final ListView listView = (ListView) dialog.findViewById(R.id.ingredientList);
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.list_item, R.id.tv, MarketList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MarketList.remove(position);
                arrayAdapter.notifyDataSetChanged();
            }
        });
        Toast.makeText(activity, "Click on item to remove it from the list", Toast.LENGTH_SHORT).show();
        dialog.show();

    }


}