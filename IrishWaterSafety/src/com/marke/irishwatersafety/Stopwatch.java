package com.marke.irishwatersafety;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;

public class Stopwatch extends Activity {
    Chronometer mChronometer, mChronometer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.stopwatch_layout);

        Button button;

        mChronometer = (Chronometer) findViewById(R.id.chronometer);
        mChronometer2 = (Chronometer) findViewById(R.id.chronometer2);

        // Watch for button clicks.
        button = (Button) findViewById(R.id.btnStart);
        button.setOnClickListener(mStartListener);
        button = (Button) findViewById(R.id.btnStart2);
        button.setOnClickListener(mStartListener2);

        button = (Button) findViewById(R.id.btnStop);
        button.setOnClickListener(mStopListener);
        button = (Button) findViewById(R.id.btnStop2);
        button.setOnClickListener(mStopListener2);

        button = (Button) findViewById(R.id.btnReset);
        button.setOnClickListener(mResetListener);
        button = (Button) findViewById(R.id.btnReset2);
        button.setOnClickListener(mResetListener2);
      
    }
    
    private void showStopButton(){
    	((Button)findViewById(R.id.btnStart)).setVisibility(View.GONE);
    	((Button)findViewById(R.id.btnStop)).setVisibility(View.VISIBLE);	
    }
    
    private void hideStopButton(){
    	((Button)findViewById(R.id.btnStart)).setVisibility(View.VISIBLE);
    	((Button)findViewById(R.id.btnReset)).setVisibility(View.VISIBLE);
    	((Button)findViewById(R.id.btnStop)).setVisibility(View.GONE);	
    }

    View.OnClickListener mStartListener = new OnClickListener() {
        public void onClick(View v) {
            mChronometer.start();
            showStopButton();
        }
    };

    View.OnClickListener mStopListener = new OnClickListener() {
        public void onClick(View v) {
            mChronometer.stop();
            hideStopButton();
        }
    };

    View.OnClickListener mResetListener = new OnClickListener() {
        public void onClick(View v) {
            mChronometer.setBase(SystemClock.elapsedRealtime());
        }
    };
    
    //*********Second stopwatch**********//
    
    private void showStopButton2(){
    	((Button)findViewById(R.id.btnStart2)).setVisibility(View.GONE);
    	((Button)findViewById(R.id.btnStop2)).setVisibility(View.VISIBLE);	
    }
    
    private void hideStopButton2(){
    	((Button)findViewById(R.id.btnStart2)).setVisibility(View.VISIBLE);
    	((Button)findViewById(R.id.btnReset2)).setVisibility(View.VISIBLE);
    	((Button)findViewById(R.id.btnStop2)).setVisibility(View.GONE);	
    }

    View.OnClickListener mStartListener2 = new OnClickListener() {
        public void onClick(View v) {
        	mChronometer2.setBase(SystemClock.elapsedRealtime());
            mChronometer2.start();
            showStopButton2();
        }
    };

    View.OnClickListener mStopListener2 = new OnClickListener() {
        public void onClick(View v) {
            mChronometer2.stop();
            hideStopButton2();
        }
    };

    View.OnClickListener mResetListener2 = new OnClickListener() {
        public void onClick(View v) {
            mChronometer2.setBase(SystemClock.elapsedRealtime());
        }
    };
    
}
