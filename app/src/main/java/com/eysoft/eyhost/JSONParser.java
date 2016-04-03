package com.eysoft.eyhost;

import android.annotation.SuppressLint;
import android.util.Log;
import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by EySoft IT Solution on 2/1/2016.
 */
public class JSONParser {
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    /*
    * This function will accept URL, Method and Values (if have any)
    * then it will check method type
    * then it will do the operation regarding methods
    * after that it will return json response.
    * */
    @SuppressLint("NewApi")
    public JSONObject makeHttpRequest(String url_string, String method, List<Pair<String,String>> params) {

        HttpURLConnection urlConnection = null;
        InputStream is = null;
        try {
            if(method == "POST"){
                String urlParameters  = getQuery(params);
                byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
                URL    url            = new URL( url_string );
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setInstanceFollowRedirects(false);
                conn.setRequestMethod("POST");
                conn.setUseCaches(false);
                try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
                    wr.write( postData );
                }
                catch (Exception ex){
                    Log.d("Saikat",ex.toString());
                }
                is = conn.getInputStream();
            }
            else if(method == "GET"){
                URL url = new URL( url_string );
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setInstanceFollowRedirects(false);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "text/plain");
                conn.setRequestProperty("charset", "utf-8");
                is = conn.getInputStream();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*
        * Making input stream as JSON response. We have used StringBuilder function
        * to convert data from InputStream to String, which holds the functionality
        * of JSON response.
        * */
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
       return jObj;

    }


    /*
    * This function create the URL if link has any values. Basically, this function
    * reformat the values to submit through the URL. Its juts like URL creating of values
    * for POST method.
    * */
    private String getQuery(List<Pair<String,String>> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Pair<String, String> pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.first, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.second, "UTF-8"));
        }

        return result.toString();
    }
}
