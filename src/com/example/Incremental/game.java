package com.example.Incremental;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

public class game extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */

    public BigDecimal cubes;
    public BigDecimal clickNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final Context context = this;

        TextView numCubes = (TextView) findViewById(R.id.numCubes);

        ImageView iceCube = (ImageView) findViewById(R.id.button);
        iceCube.setOnClickListener(this);

        loadData();
        if (cubes == null)
        {
            cubes = BigDecimal.ZERO;
        }
        if(clickNumber == null)
        {
            clickNumber = BigDecimal.ONE;
        }

        numCubes.setText("" + cubes);

        TimerTask task = new TimerTask()
        {
            public void run()
            {
                runOnUiThread(new Runnable() //run on ui thread
                {
                    public void run()
                    {
                        saveData();
                        Toast toast = Toast.makeText(context,getString(R.string.game_saved),Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 7000, 30000);

    }

    public void onClick(final View v) {

        switch (v.getId()) {
            case R.id.button:

                final TextView numCubes = (TextView) findViewById(R.id.numCubes);

                cubes = cubes.add(clickNumber);
                numCubes.setText("" + cubes);
                break;
        }
    }

    public void saveData() {
        String filename = "cubeData";

        String cubesString = cubes.toString();

        try {
            FileOutputStream outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(cubesString.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadData(){

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        //symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        String pattern = "#,##0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

// parse the string



        try {
            String filename = "cubeData";

            FileInputStream inputStream = openFileInput(filename);
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine())!= null) {
                total.append(line);
            }
            r.close();
            inputStream.close();
            cubes = (BigDecimal) decimalFormat.parse(total.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
