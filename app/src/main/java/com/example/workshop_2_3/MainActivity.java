package com.example.workshop_2_3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{

    Snackbar mySnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySnackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout),
                "You must be at least 20!\n", Snackbar.LENGTH_SHORT);
        Button yesB = (Button) findViewById(R.id.yesButton);
        Button noB = (Button) findViewById(R.id.noButton);

        yesB.setOnClickListener(v -> {
            /* Load second UI */
            Intent secondUI = SystemActivity.getIntent(MainActivity.this);
            startActivity(secondUI);
        });

        noB.setOnClickListener(v -> {
            /*pop up message*/
            mySnackbar.show();
        });

    }
}