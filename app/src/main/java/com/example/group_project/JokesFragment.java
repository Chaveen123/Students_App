package com.example.group_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class JokesFragment extends Fragment {

    private Button programming, misc, dark, pun, spooky, christmas;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_jokes, container, false);

        programming = view.findViewById(R.id.programming);
        misc = view.findViewById(R.id.misc);
        dark = view.findViewById(R.id.dark);
        pun = view.findViewById(R.id.pun);
        spooky = view.findViewById(R.id.spooky);
        christmas = view.findViewById(R.id.christmas);

        programming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), JokesProgramming.class);
                startActivity(intent);
            }
        });
        misc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), JokesMisc.class);
                startActivity(intent);
            }
        });
        dark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), JokesDark.class);
                startActivity(intent);
            }
        });
        pun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), JokesPun.class);
                startActivity(intent);
            }
        });
        spooky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), JokesSpooky.class);
                startActivity(intent);
            }
        });
        christmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), JokesChristmas.class);
                startActivity(intent);
            }
        });

        return view;
    }
}