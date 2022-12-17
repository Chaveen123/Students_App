package com.example.group_project;

import android.os.Bundle;
import android.view.View;
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

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class FactsFragment extends Fragment {

    Button btnHit;
    TextView txtJson;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_facts, container, false);

        btnHit = view.findViewById(R.id.btnHit);
        txtJson = view.findViewById(R.id.tvJsonItem);

        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFact();
            }
        });
        return view;
    }
    private void loadFact() {
        RequestQueue requestQueue;

        requestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://uselessfacts.jsph.pl/random.json?language=en", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    String fact = response.getString("text");

                    txtJson.setText(fact);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add((jsonObjectRequest));
    }
}