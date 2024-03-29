package com.example.Incremental;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by Dan Chick on 1/22/2015.
 */


public class shop extends Activity implements View.OnClickListener {
    long cubes = game.cubes;
    long clickNumber = game.clickNumber;
    long cps = game.cps;

    int numSunglasses;
    int numHats;
    int numSP;
    int numGC;

    long sunPrice;
    long hatPrice;
    long spPrice;
    long gcPrice;

    long sunCPS;
    long hatCPS;
    long spCPS;
    long gcCPS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.shop);

        TextView sunglassesText = (TextView) findViewById(R.id.sunglassesText);
        TextView hatsText = (TextView) findViewById(R.id.hatsText);
        TextView spText = (TextView) findViewById(R.id.spText);
        TextView gcText = (TextView) findViewById(R.id.gcText);

        TextView shopCPS = (TextView) findViewById(R.id.shopCPS);
        TextView shopCubes = (TextView) findViewById(R.id.shopCubes);

        loadData();

        Button sunglasses = (Button) findViewById(R.id.sunglasses);
        Button hats = (Button) findViewById(R.id.hats);
        Button spyPlanes = (Button) findViewById(R.id.spy_planes);
        Button goldChains = (Button) findViewById(R.id.gold_chains);

        sunglasses.setOnClickListener(this);
        hats.setOnClickListener(this);
        spyPlanes.setOnClickListener(this);
        goldChains.setOnClickListener(this);


        sunPrice = (long) ((numSunglasses * 1.5) + 1);
        hatPrice = (long) ((numHats * 2.5) + 1);
        spPrice = (long) ((numSP * 3.5) + 1);
        gcPrice = (long) ((numGC * 4.5) + 1);

        sunCPS = numSunglasses+1;
        hatCPS = (long) (1 + numHats * 1.5);
        spCPS = 1 + numSP * 3;
        gcCPS = 1 + numGC * 5;

        sunglassesText.setText("Owned: " + numSunglasses + " Price: " + sunPrice + " Added CPS: " + sunCPS);
        hatsText.setText("Owned: " + numHats + " Price: " + hatPrice + " Added CPS: " + hatCPS);
        spText.setText("Owned: " + numSP + " Price: " + spPrice + " Added CPS: " + spCPS);
        gcText.setText("Owned: " + numGC + " Price: " + gcPrice + " Added CPS: " + gcCPS);

        shopCPS.setText(cps + " cubes/sec");
        shopCubes.setText(cubes + " cubes");

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sunglasses:
                if (cubes>sunPrice) {
                    cubes = cubes - sunPrice;
                    numSunglasses++;
                    cps = cps + sunCPS;
                    saveData();
                }
                break;
            case R.id.hats:
                if (cubes>hatPrice) {
                    cubes = cubes - hatPrice;
                    numHats++;
                    cps = cps + hatCPS;
                    saveData();
                }
                break;
            case R.id.spy_planes:
                if (cubes>spPrice) {
                    cubes = cubes - spPrice;
                    numHats++;
                    cps = cps + spCPS;
                    saveData();
                }
                break;
            case R.id.gold_chains:
                if (cubes>gcPrice) {
                    cubes = cubes - gcPrice;
                    numHats++;
                    cps = cps + gcCPS;
                    saveData();
                }
                break;
        }
        TextView sunglassesText = (TextView) findViewById(R.id.sunglassesText);
        TextView hatsText = (TextView) findViewById(R.id.hatsText);
        TextView spText = (TextView) findViewById(R.id.spText);
        TextView gcText = (TextView) findViewById(R.id.gcText);

        TextView shopCPS = (TextView) findViewById(R.id.shopCPS);
        TextView shopCubes = (TextView) findViewById(R.id.shopCubes);

        sunglassesText.setText("Owned: " + numSunglasses + " Price: " + sunPrice + " Added CPS: " + sunCPS);
        hatsText.setText("Owned: " + numHats + " Price: " + hatPrice + " Added CPS: " + hatCPS);
        spText.setText("Owned: " + numSP + " Price: " + spPrice + " Added CPS: " + spCPS);
        gcText.setText("Owned: " + numGC + " Price: " + gcPrice + " Added CPS: " + gcCPS);

        shopCPS.setText(cps + " cubes/sec");
        shopCubes.setText(cubes + " cubes");
    }


    public void saveData() {
        String fileName = "shopData";
        byte[] shopBytes = new byte[12];

        shopBytes[0] = (byte) numSunglasses;
        shopBytes[1] = (byte) numHats;
        shopBytes[2] = (byte) numSP;
        shopBytes[3] = (byte) numGC;
        /*
        shopBytes[4] = (byte) sunPrice;
        shopBytes[5] = (byte) hatPrice;
        shopBytes[6] = (byte) spPrice;
        shopBytes[7] = (byte) gcPrice;
        shopBytes[8] = (byte) sunCPS;
        shopBytes[9] = (byte) hatCPS;
        shopBytes[10] = (byte) spCPS;
        shopBytes[11] = (byte) gcCPS;
        */
        try {

            FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(shopBytes);
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadData() {

        try {
            String fileName = "shopData";

            File file = new File(fileName);
            byte[] shopBytes = getBytesFromFile(file);

            numSunglasses = (int) shopBytes[0];
            numHats = (int) shopBytes[1];
            numSP = (int) shopBytes[2];
            numGC = (int) shopBytes[3];
            /*
            sunPrice = (long) shopBytes[4];
            hatPrice = (long) shopBytes[5];
            spPrice = (long) shopBytes[6];
            gcPrice = (long) shopBytes[7];
            sunCPS = (long) shopBytes[8];
            hatCPS = (long) shopBytes[9];
            spCPS = (long) shopBytes[10];
            gcCPS = (long) shopBytes[11];
            */

            TextView sunglassesText = (TextView) findViewById(R.id.sunglassesText);
            TextView hatsText = (TextView) findViewById(R.id.hatsText);
            TextView spText = (TextView) findViewById(R.id.spText);
            TextView gcText = (TextView) findViewById(R.id.gcText);

            TextView shopCPS = (TextView) findViewById(R.id.shopCPS);
            TextView shopCubes = (TextView) findViewById(R.id.shopCubes);

            sunglassesText.setText("Owned: " + numSunglasses + " Price: " + sunPrice + " Added CPS: " + sunCPS);
            hatsText.setText("Owned: " + numHats + " Price: " + hatPrice + " Added CPS: " + hatCPS);
            spText.setText("Owned: " + numSP + " Price: " + spPrice + " Added CPS: " + spCPS);
            gcText.setText("Owned: " + numGC + " Price: " + gcPrice + " Added CPS: " + gcCPS);

            game.cubes = cubes;
            game.cps = cps;
            game.clickNumber = clickNumber;

            shopCPS.setText(cps + " cubes/sec");
            shopCubes.setText(cubes + " cubes");

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
