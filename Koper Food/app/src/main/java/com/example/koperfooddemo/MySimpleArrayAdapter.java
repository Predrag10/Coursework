package com.example.koperfooddemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public MySimpleArrayAdapter(Context context, String[] values) {
        super(context, R.layout.comment_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.comment_item, parent, false);
        final String[] valuesSplit = values[position].split(",");

        TextView textView1 = rowView.findViewById(R.id.commentText);
        TextView textView2 = rowView.findViewById(R.id.numberOfLikes);
        TextView textView3 = rowView.findViewById(R.id.numberOfDislikes);

        ImageView like = rowView.findViewById(R.id.likeButton);
            like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLikes(context, valuesSplit[3]);
                Toast.makeText(context, "Comment liked", Toast.LENGTH_SHORT).show();
            }
        });

            ImageView dislike = rowView.findViewById(R.id.dislikeButton);
            dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDislikes(context, valuesSplit[3]);
                Toast.makeText(context, "Comment disliked", Toast.LENGTH_SHORT).show();
            }
        });


            textView1.setText(valuesSplit[0]);
            textView2.setText(valuesSplit[1]);
            textView3.setText(valuesSplit[2]);
        return rowView;
    }


    public void updateLikes(Context context, String commentID){
        String url = "https://www.studenti.famnit.upr.si/~89171025/updateLike.php?commentID=" + commentID;
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

    public void updateDislikes(Context context, String commentID){
        String url = "https://www.studenti.famnit.upr.si/~89171025/updateDisike.php?commentID=" + commentID;
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
}
