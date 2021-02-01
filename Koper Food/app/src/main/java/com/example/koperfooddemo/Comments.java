package com.example.koperfooddemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class Comments extends AppCompatActivity {

    public Button postComment;
    public EditText commentText;
    public static ListView listView;
    public static ArrayAdapter commentAdapter;
    public static ArrayList<String[]> CommentsList = new ArrayList<String[]>();
    public static String[] comments;
    public static int recipeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        Intent intent = getIntent();
        recipeID = Integer.parseInt(intent.getStringExtra("recipeID"));
        if (!intent.getStringExtra("commentsInfo").equals("No entry")) {
            String[] separatedRecipes = intent.getStringExtra("commentsInfo").split("<br>");

            for (int j = 0; j < separatedRecipes.length; j++) {
                String[] tmpArray = separatedRecipes[j].split("%");
                CommentsList.add(tmpArray);
            }


            comments = new String[CommentsList.size()];

            for (int i = 0; i < CommentsList.size(); i++) {
                comments[i] = CommentsList.get(i)[2].replaceAll("_", " ") + "," + CommentsList.get(i)[3] + "," + CommentsList.get(i)[4] + ", " + CommentsList.get(i)[0];
            }

            listView = findViewById(R.id.listViewComments);
            commentAdapter = new MySimpleArrayAdapter(this, comments);
            listView.setAdapter(commentAdapter);

        } else {
            Toast.makeText(this, "No comments", Toast.LENGTH_SHORT).show();
        }

        postComment = findViewById(R.id.postCommentButton);
        commentText = findViewById(R.id.postCommentText);
        postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentT = commentText.getText().toString();

                postCommentToDB(Comments.this, commentT, recipeID);
                Toast.makeText(Comments.this, "Comment posted, reload Comments to see yours", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void postCommentToDB(Context context, String comment, int recipeID) {
        String url = "https://www.studenti.famnit.upr.si/~89171025/postComment.php?comment=" + comment.replaceAll(" ", "_") + "&recipeID=" + recipeID;
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
                Log.d("OUTPUT :", "WOPSIE: " + error);
            }
        });
        Log.d("WORKS?", "YES?");
        queue.add(request);

    }


    @Override
    public void onBackPressed() {
        CommentsList.clear();
        super.onBackPressed();
    }
}