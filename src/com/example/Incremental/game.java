package com.example.Incremental;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.*;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Timer;
import java.util.TimerTask;

public class game extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */

    //public static BigDecimal cubes;
    //public static BigDecimal clickNumber;
    //public static BigDecimal cps;
    public static long cubes;
    public static long clickNumber;
    public static long cps;

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

        Button shop = (Button) findViewById(R.id.shopButton);
        Button settings = (Button) findViewById(R.id.settings);

        shop.setOnClickListener(this);
        settings.setOnClickListener(this);

        loadData();
        if(clickNumber == 0)
            clickNumber = 1;

        numCubes.setText(cubes + " cubes");
        cpsView.setText(cps + " cubes/sec");

        TimerTask task = new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() //run on ui thread
                {
                    public void run() {
                        saveData();
                        Toast toast = Toast.makeText(context, getString(R.string.game_saved), Toast.LENGTH_SHORT);
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

                cubes = cubes + clickNumber;
                numCubes.setText(cubes + " cubes");
                cpsView.setText(cps + " cubes/sec");
                break;

            case R.id.shopButton:
                Intent intent = new Intent(getBaseContext(), shop.class);
                startActivity(intent);
                break;
        }
    }

    public void saveData() {
        String fileName = "mainData";
        byte[] bytes = new byte[12];

        bytes[0] = (byte) cubes;
        bytes[1] = (byte) clickNumber;
        bytes[2] = (byte) cps;
        try {

            FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(bytes);
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadData() {

        try {
            final TextView numCubes = (TextView) findViewById(R.id.numCubes);
            TextView cpsView = (TextView) findViewById(R.id.cps);

            String fileName = "mainData";

            File file = new File(fileName);
            byte[] shopBytes = getBytesFromFile(file);

            cubes = (long) shopBytes[0];
            clickNumber = (long) shopBytes[1];
            cps = (long) shopBytes[2];

            numCubes.setText(cubes + " cubes");
            cpsView.setText(cps + " cubes/sec");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }
}
