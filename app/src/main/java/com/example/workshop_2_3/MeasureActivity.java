package com.example.workshop_2_3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MeasureActivity extends AppCompatActivity
{

    private static final String SYSTEM = "system";

    private static double height, weight;

    TextView selection, wUnit, hUnit;
    Button next;
    EditText heightEdit, weightEdit;
    SeekBar weightSeek, heightSeek;

    Snackbar mySnackbar;

    public static Intent getIntent(Context c, boolean system)
    {
        Intent intent = new Intent(c, MeasureActivity.class);
        intent.putExtra(SYSTEM, system);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);

        mySnackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout),
                "You must enter a weight and height over 0!\n", Snackbar.LENGTH_SHORT);

        wUnit = findViewById(R.id.weightUnit);
        hUnit = findViewById(R.id.heightUnit);
        selection = findViewById(R.id.textViewNotice);
        next = findViewById(R.id.buttonNext);
        heightEdit = findViewById(R.id.editTextHeight);
        weightEdit = findViewById(R.id.editTextWeight);
        heightSeek = findViewById(R.id.seekBarHeight);
        weightSeek = findViewById(R.id.seekBarWeight);

        Intent intent = getIntent();
        boolean system = intent.getBooleanExtra(SYSTEM, true);

        /* Change text to reflect preferred system */
        if(system)
        {
            wUnit.setText(R.string.weight_unit_metric);
            hUnit.setText(R.string.height_unit_metric);
            selection.setText("You have selected\nmetric system");
        }
        else
        {
            wUnit.setText(R.string.weight_unit_imperial);
            hUnit.setText(R.string.height_unit_imperial);
            selection.setText("You have selected\nimperial system");
        }

        /* Weight and Height slider [0,300]
         * Moving sliders must update EditTexts and vice versa */
        weightSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                if (b)
                {
                    weightEdit.setText(Integer.toString(i * 3));
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar){}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar){}
        });
        heightSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b)
                {
                    heightEdit.setText(Integer.toString(i * 3));
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar){}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar){}
        });

        weightEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){}
            @Override
            public void afterTextChanged(Editable editable)
            {
                updateSeekBar(weightSeek, weightEdit);
            }
        });
        heightEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){}
            @Override
            public void afterTextChanged(Editable editable)
            {
                updateSeekBar(heightSeek, heightEdit);
            }
        });

        /* Load pop up message if exception caught */
        next.setOnClickListener(view -> {
            String inputH, inputW;
            inputH = (heightEdit.getText()).toString();
            inputW = (weightEdit.getText()).toString();

            /*Verify inputs for weight and height*/
            if( inputH.endsWith(".") )
            {
                inputH.concat("0");
            }
            else if( inputH.startsWith(".") )
            {
                inputH = "0" + inputH;
            }
            if( inputW.endsWith(".") )
            {
                inputW.concat("0");
            }
            else if( inputW.startsWith(".") )
            {
                inputW = "0" + inputW;
            }

            if( ! inputH.equals("") && ! inputW.equals("") )
            {
                height = Double.parseDouble(inputH);
                weight = Double.parseDouble(inputW);

                if(weight > 0 && height > 0)
                {
                    /*Load fourth UI, pass system, weight and height*/
                    Intent fourthUI = ResultActivity.getIntent(MeasureActivity.this, weight,
                            height, system);
                    startActivity(fourthUI);
                }
                else
                {
                    /*pop up*/
                    mySnackbar.show();
                }
            }
            else
            {
                /*pop up*/
                mySnackbar.show();
            }
        });

    }

    private void updateSeekBar(SeekBar seekBar, EditText editText)
    {
        double a;
        String input = (editText.getText()).toString();

        if ( input.equals("") || input.startsWith(".") )
        {
            a = 0;
        }
        else
        {
            a = Double.parseDouble( (editText.getText()).toString() );
        }

        if(a <= 0)
        {
            seekBar.setProgress(0);
        }
        else if(a >= 300)
        {
            seekBar.setProgress(100);
        }
        else
        {
            seekBar.setProgress((int)a/3);
        }
    }

}