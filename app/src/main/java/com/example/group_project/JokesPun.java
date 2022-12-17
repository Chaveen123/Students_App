package com.example.group_project;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class JokesPun extends Activity {

    private TextView set, del;
    private Button find, answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokes_popup);

        set = findViewById(R.id.set);
        del = findViewById(R.id.del);
        find = findViewById(R.id.find);
        answer = findViewById(R.id.answer);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x=0;
        params.y=50;

        getWindow().setAttributes(params);

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                del.setText("");
                loadJoke();
            }
        });

    }
    private void loadJoke() {
        RequestQueue requestQueue;

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://v2.jokeapi.dev/joke/Pun?blacklistFlags=nsfw,religious,political,racist,sexist,explicit&type=twopart", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String setup = response.getString("setup");
                    String delivery = response.getString("delivery");

                    set.setText(setup);

                    answer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            del.setText(delivery);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add((jsonObjectRequest));
    }
}