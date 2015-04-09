package com.daniel.test.earthquakemonitor;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Daniel on 4/8/2015.
 */
public class RequestTask extends AsyncTask<String, String, String> {

    InputStream inputStream = null;
    ProgressDialog progressDialog = null;
    private Context mContext;

    public RequestTask(Context context){
        mContext = context;
    }

    @Override
    protected String doInBackground(String... params){
        String parsedInfo = "";
        try{
            HttpClient newClient = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setHeader("Content-Type","text/plain; charset=utf-8");
            request.setURI(new URI(params[0]));
            HttpResponse httpResponse = newClient.execute(request);
            HttpEntity httpEntity = httpResponse.getEntity();

            inputStream = httpEntity.getContent();

        }
        catch (UnsupportedEncodingException e1) {
            Log.e("EXCEPTION", "UnsupportedEncodingException" + e1.toString());
            e1.printStackTrace();
        } catch (ClientProtocolException e2) {
            Log.e("EXCEPTION", "ClientProtocolException" + e2.toString());
            e2.printStackTrace();
        } catch (IllegalStateException e3) {
            Log.e("EXCEPTION", "IllegalStateException" + e3.toString());
            e3.printStackTrace();
        } catch (IOException e4) {
            Log.e("EXCEPTION", "IOException" + e4.toString());
            e4.printStackTrace();
        }catch (URISyntaxException e5) {
            Log.e("EXCEPTION", "URISyntaxException" + e5.toString());
            e5.printStackTrace();
        }

        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"),8);
            String line;
            while((line = bufferedReader.readLine()) !=  null){
                parsedInfo += (line + "\n" );
            }
            inputStream.close();
        } catch (Exception e){
            Log.e("DBG" , "Error converting result");
        }
        return parsedInfo;
    }

    @Override
    protected void onPostExecute(String result){
        EarthquakeMonitor.currentInfo = result;
        progressDialog.cancel();
        EarthquakeDataManager.populateInfo(EarthquakeMonitor.currentInfo);
        super.onPostExecute(result);
    }

    @Override
    protected void onPreExecute(){
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Fetching data . . .");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

}//class
