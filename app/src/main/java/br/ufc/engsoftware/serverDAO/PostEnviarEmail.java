package br.ufc.engsoftware.serverDAO;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import br.ufc.engsoftware.auxiliar.NoSSLv3SocketFactory;


public class PostEnviarEmail extends AsyncTask<String, String, Void>{
    static String result;
    public String param;

    // you may separate this or combined to caller class.
    public interface AsyncResponse {
        void processFinish(String output);
    }


    public AsyncResponse delegate = null;

    public PostEnviarEmail(Context context, String param, AsyncResponse delegate){
        this.delegate = delegate;
        this.param = param;
    }

    public PostEnviarEmail(String param, AsyncResponse delegate){
        this.delegate = delegate;
        this.param = param;
    }

    @Override
    protected Void doInBackground(String... params) {

        SSLContext sslcontext = null;
        try {
            sslcontext = SSLContext.getInstance("TLSv1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            sslcontext.init(null,
                    null,
                    null);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        SSLSocketFactory NoSSLv3Factory = new NoSSLv3SocketFactory(sslcontext.getSocketFactory());

        try {
            URL url = new URL(params[0]);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
            outputStreamWriter.write(param);
            outputStreamWriter.flush();

            int responseCode = connection.getResponseCode();

            result = String.valueOf(responseCode);

        } catch (Exception e) {
            result = e.toString();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        delegate.processFinish(result);

    }
}
