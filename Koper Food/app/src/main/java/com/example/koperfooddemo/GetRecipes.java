package com.example.koperfooddemo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import static com.example.koperfooddemo.Search.searchName;

public class GetRecipes {

    public static Context con;

    public static void getRecipeInfo(String res){
        Intent i = new Intent(con, Browse.class);
        i.putExtra("recipeInfo", res);
            con.startActivity(i);
    }
//
    public static void RetrieveRecipes(Context context) {

        String url = "https://www.studenti.famnit.upr.si/~89171025/getRecipes.php";
        Log.d("GEN_URL", url);
        RequestQueue queue = Volley.newRequestQueue(context);
        con = context;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getRecipeInfo(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("OUTPUT :", "WOPSIE: " + error);
            }
        });
        queue.add(request);
    }

    public static void RetrieveSearches(Context context) {

            String url = "https://www.studenti.famnit.upr.si/~89171025/searchResults.php?search=" + searchName.getText().toString();
            Log.d("GEN_URL", url);
            RequestQueue queue = Volley.newRequestQueue(context);
            con = context;
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    getRecipeInfo(response);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("OUTPUT :", "WOPSIE: " + error);
                }
            });
            queue.add(request);
    }

    public static void RetrieveSearchesByIngredient(Context context, String ingredientsString) {

        String url = "https://www.studenti.famnit.upr.si/~89171025/searchResultsIngredient.php?search=" + ingredientsString;
        Log.d("GEN_URL", url);
        RequestQueue queue = Volley.newRequestQueue(context);
        con = context;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getRecipeInfo(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("OUTPUT :", "WOPSIE: " + error);
            }
        });
        queue.add(request);
    }
}
