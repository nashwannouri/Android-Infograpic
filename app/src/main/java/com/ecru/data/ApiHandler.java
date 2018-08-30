package com.ecru.data;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ecru.infographic.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ayman on 24/11/2015.
 */
public class ApiHandler extends AsyncTask<String, Void, String> {

    private String response;
    private String filename;
    private Activity activity;
    private String urlName;
    private ProgressBar progressBar;


    /**
     * Constuctor for ApiHandler class
     * @param filename used to reference where the string has been stored
     * @param urlName the url from which the data should be fetched
     * @param activity
     */
    public ApiHandler(String filename, String urlName, Activity activity) {
        this.urlName = urlName;
        this.activity = activity;
        this.filename = filename;
        progressBar = (ProgressBar) this.activity.findViewById(R.id.progress_bar);
    }


    /**
     * Called by .execute, in this method fetch json string
     * @param Params
     * @return string containing json from url
     */
    @Override
    protected String doInBackground(String... Params) {

        Log.d("doInBackground", "Attempting to load stored data...");
        String returnString = loadCachedData();
        if (returnString == null) {
            try {
                Log.d("doInBackground", "Failed to load offline data. Attempting to retrieve online data...");
                returnString = "";
                //gets url
                URL urlName = new URL(this.urlName);

                HttpURLConnection connection = (HttpURLConnection) urlName.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();

                BufferedReader in = new BufferedReader(new InputStreamReader(
                        urlName.openStream()));

                //appends the response to the request to the return string
                //N.B. no need for loop here as only one line will be returned
                returnString += in.readLine();

                //closes the connection
                in.close();
                connection.disconnect();
                //calls the saveData method to store the string
                saveData(returnString);
            } catch (IOException e) {
                Log.d("doInBackground", "Failed to retrieve online data");
                e.printStackTrace();
            }
        }
        //temp, used to see response from server in logCat
        Log.d("returnSting", returnString);

        return returnString;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String returnString) {
        progressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * Method to store the string persistently
     * @param jsonString the json string to save
     * @return
     */
    public boolean saveData(String jsonString) {
        try {
            FileOutputStream fos = activity.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(jsonString.getBytes());
            fos.close();
            Log.d("saveData", "Data has been saved to: " + filename);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * method to load the cached string
     * @return string containing json data
     */
    public String loadCachedData() {
        String returnString = null;
        try {
            FileInputStream fis = activity.openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            returnString = reader.readLine();
            Log.d("loadCachedData", "Data has been read");
        } catch (FileNotFoundException e) {
            String message = "Unable to load offline data";
//            Toast toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
   //         toast.show();
        } catch (IOException e) {
            String message = "Unable to load offline data";
            Toast toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
            toast.show();
        }

        return returnString;
    }

}