package com.example.group_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

import javax.xml.transform.sax.TemplatesHandler;

public class MainActivity extends AppCompatActivity {
    Button login;
    CompoundButton theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.userlogin);
        theme = findViewById(R.id.switchtheme);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userIntent = new Intent(MainActivity.this, userLoginActivity.class);
                startActivity(userIntent);
            }
        });
        SharedPreferences sharedPreferences=getSharedPreferences("theme",MODE_PRIVATE);
        theme.setChecked(sharedPreferences.getBoolean("darkmode",false));

        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (theme.isChecked())
                {
                    Toast.makeText(getApplicationContext(),"Dark Mode turned ON",Toast.LENGTH_SHORT).show();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    SharedPreferences.Editor editor=getSharedPreferences("theme",MODE_PRIVATE).edit();
                    editor.putBoolean("darkmode",true);
                    editor.apply();
                    theme.setChecked(true);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Dark Mode turned OFF",Toast.LENGTH_SHORT).show();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    SharedPreferences.Editor editor=getSharedPreferences("theme",MODE_PRIVATE).edit();
                    editor.putBoolean("darkmode",false);
                    editor.apply();
                    theme.setChecked(false);
                }
            }
        });
    }
}