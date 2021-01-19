package com.example.fake_real;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class test {

    public static String res;
    public static boolean notdone = true;
    public static Context con;
    public static void getres(String res){
        Log.d("FINAL ",res);

        String[] resarr = res.split("I");
        Intent i = new Intent(con,Results.class);
        i.putExtra("realper",resarr[0]);
        i.putExtra("realavg",resarr[1]);
        i.putExtra("fakeper",resarr[2]);
        i.putExtra("fakeavg",resarr[3]);
        i.putExtra("real?",resarr[4]);
        Log.d("TEST",""+resarr.length);
        Log.d("TEST",""+resarr[1]);
        con.startActivity(i);


    }


    public static void GO_DB_BOY(Context context, String name, int perc, String rf){

        RequestQueue queue = Volley.newRequestQueue(context);
        String url="http://www.studenti.famnit.upr.si/~89171101/RealFakeDatabaseMaster.php?name="+name+"&perc="+perc+"&rf="+rf;
        Log.d("GEN_URL",url);
        notdone=true;
        con=context;
        StringRequest request = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("OUTPUT :",response);
                test.getres(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("OUTPUT: ","ERROR: " + error);
            }
        });

        queue.add(request);
      //  while(notdone){}


    }



}
