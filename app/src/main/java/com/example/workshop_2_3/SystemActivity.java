package com.example.workshop_2_3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import java.util.concurrent.atomic.AtomicBoolean;

public class SystemActivity extends AppCompatActivity
{
    public static Intent getIntent(Context c)
    {
        return (new Intent(c, SystemActivity.class));
    }

    Button impB, metB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);

        AtomicBoolean system = new AtomicBoolean(true);

        impB = findViewById(R.id.impButton);
        metB = findViewById(R.id.metButton);

        /* Imperial: set system to false, load next UI */
        impB.setOnClickListener(view -> {
            system.set(false);
            loadThirdUI(system.get());
        });

        /* Metric: set system to true, load next UI */
        metB.setOnClickListener(view -> {
            system.set(true);
            loadThirdUI(system.get());
        });
    }

    /*
     * Loads third UI
     * IMPORTS: system(boolean)
     * EXPORTS: none
     */
    private void loadThirdUI(boolean system)
    {
        Intent thirdUI = MeasureActivity.getIntent(SystemActivity.this, system);
        startActivity(thirdUI);
    }
}