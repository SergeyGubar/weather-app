package com.example.sergey.weatherapp.utilities;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Sergey on 9/21/2017.
 */

public class IOUtilities {

    private static final String TAG = IOUtilities.class.getSimpleName();

    public static void writeToCache(String fileName, String data, Context ctx) {
        try {
            FileOutputStream fileOutputStream = ctx.openFileOutput(fileName, MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        } catch (IOException ex) {
            Log.e(TAG, "Write file exception!");
            ex.printStackTrace();
        }
    }

    public static String getDataFromCache(String fileName, Context ctx) {

        try (FileInputStream fileInputStream = ctx.openFileInput(fileName)) {
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException ex) {
            Log.e(TAG, "File wasn't found!");
            ex.printStackTrace();
        }
        return null;
    }


}
