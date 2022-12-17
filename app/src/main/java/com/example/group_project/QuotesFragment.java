package com.example.group_project;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class QuotesFragment extends Fragment implements CopyListener {

    RecyclerView recycler_quotes;
    QuoteRequestManager manager;
    QuoteAdapter adapter;
//    ProgressDialog dialog;
    private RecyclerView recyclerView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_quotes, container, false);

        recyclerView = view.findViewById(R.id.recycler_facts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://uselessfacts.jsph.pl/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        recycler_quotes = view.findViewById(R.id.recycler_quotes);

        manager = new QuoteRequestManager(getActivity());
        manager.GrabQuotes(listener);
//        dialog = new ProgressDialog(getActivity());
//        dialog.setTitle("Loading Quotes");
//        dialog.show();

        return view;
    }

    private final QuoteListener listener = new QuoteListener() {
        @Override
        public void Fetch(List<QuoteResponse> responses, String message) {
            showData(responses);
//            dialog.dismiss();
        }

        @Override
        public void Error(String message) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    };

    private void showData(List<QuoteResponse> responses) {
        recycler_quotes.setHasFixedSize(true);
        recycler_quotes.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        adapter = new QuoteAdapter(getActivity(), responses, this);
        recycler_quotes.setAdapter(adapter);
    }

    @Override
    public void onCopyText(String text) {
        ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("Copies data", text);
        clipboardManager.setPrimaryClip(data);
        Toast.makeText(getActivity(),"Copied To Clipboard!!", Toast.LENGTH_SHORT).show();

    }
}

