package br.ufc.engsoftware.serverDAO;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import br.ufc.engsoftware.auxiliar.Statics;
import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.auxiliar.WebRequest;

/**
 * Created by Thiago on 26/05/2016.
 */
public class PostPagamento extends AsyncTask<Void, Void, Void> {

    // URL para pegar os Subtopicos via JSON
     private static String url = Statics.PAGAR;

    public AsyncResponse delegate = null;

    // Contexto da activity que chamou esta classe
    Context context;
    String response, mensagem;
    Utils util;

    JSONObject json;

    public interface AsyncResponse {
        void processFinish(String output, String mensagem);
    }

    public PostPagamento(AsyncResponse delegate){
        this.delegate = delegate;
    }

    // Dialog com barra de progresso mostrado na tela
    ProgressDialog proDialog;

    public PostPagamento(Context context, JSONObject json, String url, AsyncResponse delegate){
        this.context = context;
        this.json = json;
        this.url = url;
        this.delegate = delegate;
    }

    // Mostra a barra de progresso na tela
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress loading dialog
        proDialog = new ProgressDialog(context);
        proDialog.setMessage("Realizando Pagamento...");
        proDialog.setCancelable(false);
        proDialog.show();
        util = new Utils(this.context);
    }

    // Pega o JSON do web service com a lista de subtopicos
    @Override
    protected Void doInBackground(Void... arg0) {
        String jsonStr = null;

        // Fazendo requisição para o web service pelo metodo estatico httpGet
        try {
            response = WebRequest.httpPostJson(url, json.toString());
        } catch (IOException e) {
            /** TODO analizar o tratamento de erro */
            e.printStackTrace();
        }
        saveMoedasFromJson(response);

        return null;
    }


    // Metodo responsavel por quebrar o JSON em Subtopicos
    private void saveMoedasFromJson(String json){
        if (json != null)
        {
            try {
                JSONObject jsonObj = new JSONObject(json);
                response = jsonObj.getString("success");
                int quantia = jsonObj.getInt("moeda");
                mensagem = jsonObj.getString("message");

                util.saveIntInSharedPreferences("moedas", quantia);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    // Metodo chamado ou terminar o doInBackground
    @Override
    protected void onPostExecute(Void requestresult) {
        super.onPostExecute(requestresult);
        // Tira o dialog de progresso da tela
        if (proDialog.isShowing())
            proDialog.setMessage(response);
        proDialog.dismiss();
        delegate.processFinish(response, mensagem);
    }

}