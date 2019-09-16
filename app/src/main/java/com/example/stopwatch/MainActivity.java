package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {
    private Button startStop;
    private Button reset;
    private Chronometer timer;
    private boolean isClicked;
    public static final String KEY_CHRONOMETER_BASE = "chronometer base";
    public static final String KEY_CHRONOMETER_PAUSETIME = "chronometer pausetime";
    public static final String KEY_CHRONOMETER_RUNNING = "chronometer is running"; //or clicked same thing
    private long pausedTime;
    /*
    look up the Log class for android
    list all the levels of logging and when they're used
    PRIORITIES:
    verbose Log.v
    debug Log.d
    info Log.i
    warning Log.w
    error Log.e
    assert Log.a

    launched the app ---> onCreate, onStart, onResume
    1. rotate ---> onPause onStop onDestroy onCreate onStart onResume
    2. square button ----> onStop
    3. click back from square ----> onPause onStop
    4. hit the circle app ----> onPause, onStop
    5. relaunch from phone navigation ----> onStart onResume
    6. hit back button -----> onStop onDestroy
    */
    
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");
        isClicked = false;
        pausedTime = 0;


        wirewidgets();
        setListeners();

        /*
        check to see if saved instance state is null
        if it is not null, pull out the value we saved from the Bundle
        set the chronometer's base to that value
        start the chronometer

        also store if it was running or stop
        */
        if(savedInstanceState != null) {
            isClicked= savedInstanceState.getBoolean(KEY_CHRONOMETER_RUNNING);
            pausedTime = savedInstanceState.getLong(KEY_CHRONOMETER_PAUSETIME);
            if (isClicked) {
                timer.setBase(savedInstanceState.getLong(KEY_CHRONOMETER_BASE));
                isClicked = true;
                timer.start();
            }
            else
            {
                timer.setBase(savedInstanceState.getLong(KEY_CHRONOMETER_BASE) + SystemClock.elapsedRealtime() - pausedTime);
                pausedTime = SystemClock.elapsedRealtime();


            }
        }

    }

    private void wirewidgets() {
        timer = findViewById(R.id.chronometer_main_timer);

        reset = findViewById(R.id.button_main_reset);

        startStop = findViewById(R.id.button_main_startStop);
    }

    private void setListeners() {
        startStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isClicked = !isClicked;
                if(isClicked)
                {
                    if(pausedTime == 0){ timer.setBase(SystemClock.elapsedRealtime());
                    timer.start();
                    startStop.setText(getString(R.string.main_stop));}
                    else{
                        timer.setBase(timer.getBase() + SystemClock.elapsedRealtime() - pausedTime);
                        timer.start();
                        startStop.setText(getString(R.string.main_stop));
                    }

                }
                else
                {
                    timer.stop();
                    pausedTime= SystemClock.elapsedRealtime();
                    startStop.setText(getString(R.string.main_start));
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.stop();
                timer.setBase(SystemClock.elapsedRealtime());
                pausedTime = 0;
                isClicked = false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_CHRONOMETER_BASE, timer.getBase());
        outState.putLong(KEY_CHRONOMETER_PAUSETIME, pausedTime);
        outState.putBoolean(KEY_CHRONOMETER_RUNNING, isClicked);


    }
    
}
