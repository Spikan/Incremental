package com.example.Incremental;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
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

public class game extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */

    public static BigDecimal cubes;
    public static BigDecimal clickNumber;
    public static BigDecimal cps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final Context context = this;

        TextView numCubes = (TextView) findViewById(R.id.numCubes);
        TextView cpsView = (TextView) findViewById(R.id.cps);

        numCubes.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        cpsView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

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
        if(cps == null)
        {
            cps = BigDecimal.ZERO;
        }

        numCubes.setText(cubes + " cubes");
        cpsView.setText(cps + " cubes/sec");

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
                TextView cpsView = (TextView) findViewById(R.id.cps);

                cubes = cubes.add(clickNumber);
                numCubes.setText(cubes + " cubes");
                cpsView.setText(cps + " cubes/sec");
                break;
            case R.id.shop:
            {
            Intent intent = new Intent(this, shop.class);
                startActivity(intent);
            }
        }
    }

    public void saveData() {
        String cubeName = "cubeData";
        String clickName = "clickData";
        String cpsName = "cpsData";

        String cubesString = cubes.toString();
        String clickString = clickNumber.toString();
        String cpsString = cps.toString();

        try {

            FileOutputStream outputStream = openFileOutput(cubeName, Context.MODE_PRIVATE);
            outputStream.write(cubesString.getBytes());
            outputStream.close();

            outputStream = openFileOutput(clickName, Context.MODE_PRIVATE);
            outputStream.write(clickString.getBytes());
            outputStream.close();

            outputStream = openFileOutput(cpsName, Context.MODE_PRIVATE);
            outputStream.write(cpsString.getBytes());
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

        TextView numCubes = (TextView) findViewById(R.id.numCubes);
        TextView cpsView = (TextView) findViewById(R.id.cps);



        try {
            String cubeName = "cubeData";
            String clickName = "clickData";
            String cpsName = "cpsData";

            FileInputStream inputStream = openFileInput(cubeName);
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine())!= null) {
                total.append(line);
            }
            r.close();
            inputStream.close();
            cubes = (BigDecimal) decimalFormat.parse(total.toString());

            inputStream = openFileInput(clickName);
            r = new BufferedReader(new InputStreamReader(inputStream));
            total = new StringBuilder();
            while ((line = r.readLine())!= null) {
                total.append(line);
            }
            r.close();
            inputStream.close();
            clickNumber = (BigDecimal) decimalFormat.parse(total.toString());

            inputStream = openFileInput(cpsName);
            r = new BufferedReader(new InputStreamReader(inputStream));
            total = new StringBuilder();
            while ((line = r.readLine())!= null) {
                total.append(line);
            }
            r.close();
            inputStream.close();
            cps = (BigDecimal) decimalFormat.parse(total.toString());

            numCubes.setText(cubes + " cubes");
            cpsView.setText(cps + " cubes/sec");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
