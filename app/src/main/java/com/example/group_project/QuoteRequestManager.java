package com.example.group_project;

import android.content.Context;
import android.location.GnssAntennaInfo;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class QuoteRequestManager {

    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://type.fit/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public QuoteRequestManager(Context context) {
        this.context = context;
    }

    public void GrabQuotes(QuoteListener listener ){
        CallQuotes callQuotes = retrofit.create(CallQuotes.class);
        Call<List<QuoteResponse>> call = callQuotes.callQuotes();
        call.enqueue(new Callback<List<QuoteResponse>>() {
            @Override
            public void onResponse(Call<List<QuoteResponse>> call, Response<List<QuoteResponse>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(context, "Request Failed!, Please Try Again", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.Fetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<List<QuoteResponse>> call, Throwable t) {
                listener.Error(t.getMessage());
            }
        });
    }
    private interface CallQuotes{
        @GET("api/quotes")
        Call<List<QuoteResponse>> callQuotes();
    }
}
