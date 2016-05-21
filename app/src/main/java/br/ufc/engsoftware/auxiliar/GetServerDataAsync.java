package br.ufc.engsoftware.auxiliar;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by limaneto on 17/04/16.
 */
public class GetServerDataAsync extends AsyncTask<String, String, Void> {
    static String result;
    public String url;
    private final String USER_AGENT = "Mozilla/5.0";

    // you may separate this or combined to caller class.
    public interface AsyncResponse {
        void processFinish(String output);
    }

    public AsyncResponse delegate = null;

    public GetServerDataAsync(AsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected Void doInBackground(String... params) {

        InputStream inputStream = null;
        try {
            URL url = new URL(params[0]);

            // Set up HTTP post
            HttpURLConnection httpClient = (HttpURLConnection) url.openConnection();
            inputStream = new BufferedInputStream(httpClient.getInputStream());
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder sBuilder = new StringBuilder();

            String line = null;
            while ((line = bReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }

            inputStream.close();
            result = sBuilder.toString();
            httpClient.disconnect();

        } catch (Exception e4) {
            Log.e("Exception", e4.toString());
            e4.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        delegate.processFinish(result);
    }
}