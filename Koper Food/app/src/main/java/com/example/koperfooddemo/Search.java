package com.example.koperfooddemo;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static com.example.koperfooddemo.GetRecipes.RetrieveRecipes;
import static com.example.koperfooddemo.GetRecipes.RetrieveSearches;
import static com.example.koperfooddemo.GetRecipes.RetrieveSearchesByIngredient;


public class Search extends AppCompatActivity {
    private Button goName, addIngredientToSearch, editSearchIngredients, searchIngredientsButton;
    public static EditText searchName;
    EditText searchIngredient;

    ArrayList<String> ingredientListSearch = new ArrayList<String>();
    String searchIngredients = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        goName = (Button) findViewById(R.id.goName);
        searchIngredient = (EditText) findViewById(R.id.ingredientSearch);
        addIngredientToSearch = (Button) findViewById(R.id.addIngredientToSearch);
        editSearchIngredients = (Button) findViewById(R.id.editSearchIngredients);
        searchIngredientsButton = (Button) findViewById(R.id.searchIngredientsButton);
        searchName = findViewById(R.id.searchName);

        goName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(searchName.getText().toString().equals("")){
                    RetrieveRecipes(Search.this);
                }else {
                    RetrieveSearches(Search.this);
                }
                ingredientListSearch.clear();
            }
        });

        addIngredientToSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(!searchIngredient.getText().toString().equals("")) {
                    ingredientListSearch.add(searchIngredient.getText().toString());
                    if (searchIngredients.equals("")) {
                        searchIngredients += searchIngredient.getText().toString();
                    } else {
                        searchIngredients += "," + searchIngredient.getText().toString();
                    }
                }
            }
        });

        editSearchIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchIngredientsDialog(Search.this);
            }
        });

        searchIngredientsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(ingredientListSearch.isEmpty()){
                    RetrieveRecipes(Search.this);
                }else {
                    ingredientListSearch.clear();
                    RetrieveSearchesByIngredient(Search.this, searchIngredients);
                    searchIngredients = "";
                }

            }
        });




        ///////////
        Spinner spinner = (Spinner) findViewById(R.id.priceSpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.lowandhigh, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        Spinner spinner2 = (Spinner) findViewById(R.id.difficultySpinner);


        spinner2.setAdapter(adapter);
    }

    public void showSearchIngredientsDialog(Activity activity) {

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
        final ArrayAdapter arrayAdapter= new ArrayAdapter(this,R.layout.list_item, R.id.tv, ingredientListSearch);
        listViewingredients.setAdapter(arrayAdapter);

        listViewingredients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ingredientListSearch.remove(position);
                arrayAdapter.notifyDataSetChanged();
            }
        });
        Toast.makeText(activity, "Click on item to remove it from the list", Toast.LENGTH_SHORT).show();
        dialog.show();

    }
}
