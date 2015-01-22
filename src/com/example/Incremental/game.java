package com.example.Incremental;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import static java.lang.Thread.sleep;

public class game extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */

    double cubes;

    public ProgressBar progress;
    public int progressStatus = 0;

    public Handler pHandler = new Handler();

    public int progressSpeed = 3000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button iceCube = (Button) findViewById(R.id.button);
        iceCube.setOnClickListener(this);

        progress = (ProgressBar) findViewById(R.id.progressBar);


        new Thread(new Runnable(){
            @Override
            public void run(){
                while(progressStatus<100)
                    try {
                        sleep(progressSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                pHandler.post(new Runnable(){
                    public void run(){
                        progress.setProgress(progressStatus);
                    }
                });
            }
        }).start();

    }

    public void onClick(final View v){

        switch (v.getId()){
            case R.id.button:
                final TextView numCubes = (TextView) findViewById(R.id.numCubes);
                cubes = cubes + 1;
                numCubes.setText("" + cubes);
                break;
        }
    }
}
