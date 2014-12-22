// Main Activity.java
// Calculates bills using 15% and custom percentage tips.
package com.elleryjustin.tipcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.text.NumberFormat;


public class MainActivity extends Activity
{
    // currency and percent formatters
    private static final NumberFormat CURRENCYFORMAT = NumberFormat.getCurrencyInstance();
    private static final NumberFormat PERCENTFORMAT = NumberFormat.getPercentInstance();

    private double billAmount = 0.0;
    private double customPercent = 0.18;
    private TextView amountDisplayTextView;
    private TextView percentCustomTextView;
    private TextView tip15TextView;
    private TextView total15TextView;
    private TextView tipCustomTextView;
    private TextView totalCustomTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up references to the TextViews
        amountDisplayTextView = (TextView) findViewById(R.id.amountDisplayTextView);
        percentCustomTextView = (TextView) findViewById(R.id.percentCustomTextView);
        tip15TextView = (TextView) findViewById(R.id.tip15TextView);
        total15TextView = (TextView) findViewById(R.id.total15TextView);
        tipCustomTextView = (TextView) findViewById(R.id.tipCustomTextView);
        totalCustomTextView = (TextView) findViewById(R.id.totalCustomTextView);

        // update the GUI based on billAmount and customPercent
        amountDisplayTextView.setText(CURRENCYFORMAT.format(billAmount));
        updateStandard();
        updateCustom();

        // set amountEditText's Textwatcher
        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        // set customTipSeekBar's OnSeekBarChangeListener
        SeekBar customTipSeekBar = (SeekBar) findViewById(R.id.customTipSeekBar);
        customTipSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
    }
    private void updateStandard()
    {
        // calculate 15% tip and total
        double fifteenPercentTip = billAmount*0.15;
        double fifteenPercentTotal = billAmount + fifteenPercentTip;

        //display 15% tip and total formatted as currency
        tip15TextView.setText(CURRENCYFORMAT.format(fifteenPercentTip));
        total15TextView.setText(CURRENCYFORMAT.format(fifteenPercentTotal));
    }
    private void updateCustom()
    {
        // show customPercent in percentCustomTextView formatted as %
        percentCustomTextView.setText(PERCENTFORMAT.format(customPercent));

        // calculate the custom tip and total
        double customTip = billAmount*customPercent;
        double customTotal = billAmount+customTip;

        // display custom tip and total formatted as currency
        tipCustomTextView.setText(CURRENCYFORMAT.format(customTip));
        totalCustomTextView.setText(CURRENCYFORMAT.format(customTotal));
    }
    // anon inner class for seek bar
    private OnSeekBarChangeListener customSeekBarListener = new OnSeekBarChangeListener()
    {
        //update customPercent, then call updateCustom
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            // sets customPercent to position of the seekBar's thumb
            customPercent = progress/100.0;
            updateCustom(); // update the custom tip TextViews
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar){}
        @Override
        public void onStopTrackingTouch(SeekBar seekBar){}
    };

    // event-handling object that responds to amountEditText's events
    private TextWatcher amountEditTextWatcher = new TextWatcher()
    {
        // called when the user enters a number
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            // convert amountEditText's text to double
            try
            {
                billAmount=Double.parseDouble(s.toString()) /100.0;
            }
            catch (Exception ex)
            {
                billAmount = 0.0;
            }

            // display currency formatted bill amount
            amountDisplayTextView.setText(CURRENCYFORMAT.format(billAmount));
            updateStandard();
            updateCustom();
        }

        @Override
        public void afterTextChanged(Editable arg0) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    };


    protected void onDestroy() {
        super.onDestroy();
    }




}
