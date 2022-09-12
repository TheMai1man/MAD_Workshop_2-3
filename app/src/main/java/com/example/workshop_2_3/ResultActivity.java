package com.example.workshop_2_3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity
{
    public static final String SYSTEM = "system";
    public static final String HEIGHT = "height";
    public static final String WEIGHT = "weight";

    public static Intent getIntent(Context c, double weight, double height, boolean system)
    {
        Intent intent = new Intent(c, ResultActivity.class);
        intent.putExtra(SYSTEM, system);
        intent.putExtra(HEIGHT, height);
        intent.putExtra(WEIGHT, weight);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView wView, hView, bmiView, resultView;
        wView = findViewById(R.id.weightResult);
        hView = findViewById(R.id.heightResult);
        bmiView = findViewById(R.id.bmiResult);
        resultView = findViewById(R.id.bmiSummary);

        Intent intent = getIntent();
        boolean system = intent.getBooleanExtra(SYSTEM, true);
        double height = intent.getDoubleExtra(HEIGHT, 170);
        double weight = intent.getDoubleExtra(WEIGHT, 80);

        double bmi;
        String wText, hText, resultText;

        /* Text must reflect preferred system */
        if(system)
        {
            bmi = calcMetricBMI(weight, height);
            wText = "Your weight is " + weight + "KG";
            hText = "Your height is " + height + "CM";
        }
        else
        {
            bmi = calcImperialBMI(weight, height);
            wText = "Your weight is " + weight + "lB";
            hText = "Your height is " + height + "Inch";
        }

        if(bmi <= 18.5)
        {
            /*Underweight, blue*/
            resultText = "Underweight";
            resultView.setBackgroundColor(getResources().getColor(R.color.blue));
            resultView.setTextColor(getResources().getColor(R.color.black));
        }
        else if (bmi <= 24.9)
        {
            /*Healthy, green*/
            resultText = "Healthy";
            resultView.setBackgroundColor(getResources().getColor(R.color.green));
            resultView.setTextColor(getResources().getColor(R.color.black));
        }
        else if (bmi <= 29.9)
        {
            /*Overweight but not obese, yellow*/
            resultText = "Overweight\nbut not obese";
            resultView.setBackgroundColor(getResources().getColor(R.color.yellow));
            resultView.setTextColor(getResources().getColor(R.color.black));
        }
        else if (bmi <= 34.9)
        {
            /*Obese class 1, red*/
            resultText = "Obese\nClass I";
            resultView.setBackgroundColor(getResources().getColor(R.color.red));
            resultView.setTextColor(getResources().getColor(R.color.black));
        }
        else if (bmi <= 39.9)
        {
            /*Obese class 2, purple*/
            resultText = "Obese\nClass II";
            resultView.setBackgroundColor(getResources().getColor(R.color.purple_500));
            resultView.setTextColor(getResources().getColor(R.color.black));
        }
        else
        {
            /*Obese class 3, black*/
            resultText = "Obese\nClass III";
            resultView.setBackgroundColor(getResources().getColor(R.color.black));
            resultView.setTextColor(getResources().getColor(R.color.white));
        }

        wView.setText(wText);
        hView.setText(hText);
        bmiView.setText("Your BMI\n" + String.format("%.2f", bmi));
        resultView.setText(resultText);
    }

    /*
     * Calculates BMI in metric or imperial
     * IMPORTS: w(double), h(double)
     * EXPORTS: bmi(double)
     */
    public double calcMetricBMI(double w, double h)
    {
        return (w / Math.pow((h/100), 2));
    }

    public double calcImperialBMI(double w, double h)
    {
        return ( ( w / Math.pow(h, 2) ) * 703 );
    }

}