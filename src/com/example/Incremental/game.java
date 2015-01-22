package com.example.Incremental;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class game extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */

    double cubes;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button iceCube = (Button) findViewById(R.id.button);
        iceCube.setOnClickListener(this);




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
