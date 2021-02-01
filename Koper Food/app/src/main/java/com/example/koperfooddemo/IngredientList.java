package com.example.koperfooddemo;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class IngredientList extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Button close;

    public IngredientList(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ingredient_list);
        close = (Button) findViewById(R.id.closeList);
        close.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}