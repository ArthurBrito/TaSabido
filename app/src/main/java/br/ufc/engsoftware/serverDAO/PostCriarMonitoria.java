package br.ufc.engsoftware.serverDAO;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import br.ufc.engsoftware.BDLocalManager.DuvidaBDManager;
import br.ufc.engsoftware.auxiliar.Statics;
import br.ufc.engsoftware.auxiliar.WebRequest;
import br.ufc.engsoftware.models.Duvida;
import br.ufc.engsoftware.views.DuvidaListView;

import static java.lang.Integer.parseInt;

/**
 * Created by Thiago on 26/05/2016.
 */
public class PostCriarMonitoria extends AsyncTask<String, String, Void> {

    private static int id;

    public AsyncResponse delegate = null;

    // Contexto da activity que chamou esta classe
    Context context;
    String response;

    JSONObject json;

    public interface AsyncResponse {
        void processFinish(String output, int id);
    }

    public PostCriarMonitoria(AsyncResponse delegate){
        this.delegate = delegate;
    }

    // Dialog com barra de progresso mostrado na tela
    ProgressDialog proDialog;

    public PostCriarMonitoria(Context context, JSONObject json, AsyncResponse delegate){
        this.context = context;
        this.json = json;
        this.delegate = delegate;
    }

    // Pega o JSON do web service com a lista de subtopicos
    @Override
    protected Void doInBackground(String... params) {
        String jsonStr = null;
        URL _url = null;
        try {
            _url = new URL(params[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String url = _url.toString();


        // Fazendo requisição para o web service pelo metodo estatico httpGet
        try {
            response = WebRequest.httpPostJson(url, json.toString());
        } catch (IOException e) {
            /** TODO analizar o tratamento de erro */
            e.printStackTrace();
        }

        JSONObject jsonResponse = null;
        try {
            jsonResponse = new JSONObject(response);
            response = jsonResponse.getString("success");
            if (response.equals("true")){
                id = jsonResponse.getInt("id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Metodo chamado ou terminar o doInBackground
    @Override
    protected void onPostExecute(Void requestresult) {
        super.onPostExecute(requestresult);
        // Tira o dialog de progresso da tela
            delegate.processFinish(response, id);
    }
}