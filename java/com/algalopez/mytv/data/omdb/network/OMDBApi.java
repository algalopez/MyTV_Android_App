package com.algalopez.mytv.data.omdb.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/25/16
 */


public class OMDBApi {


    private final static String LOGTAG = OMDBApi.class.getSimpleName();


    public static JSONObject jsonDownload(URL url){

        // Outside try block to close at finally block
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        JSONObject ret = null;

        // Build OMDB URL
        try {

            // Create the request to OmDB, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder builder = new StringBuilder();
            if (inputStream == null) { return null; } // Nothing to do.
            reader = new BufferedReader(new InputStreamReader(inputStream));

            // Copy the input stream into a String
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            if (builder.length() == 0) { return null; } // Stream was empty.  No point in parsing.

            // parse raw json string to json and return it
            ret = new JSONObject(builder.toString());

        } catch (java.io.IOException e) {
            Log.e(LOGTAG, "Error fetching: ", e);
        } catch (JSONException e) {
            Log.e(LOGTAG, "Error parsing: ", e);

        } finally {

            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOGTAG, "Error closing stream", e);
                }
            }
        }
        return ret;
    }




    public static Bitmap imageDownload(URL url)  {

        Bitmap img;

        if (url == null){
                return null;
        }

        // Download image
        try  {
            InputStream is = (InputStream) url.getContent();
            img = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            Log.d(LOGTAG, "imageDownload: Error downloading image");
            return null;
        }

        // Return downloaded image
        return img;
    }
}
