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
public class GetDuvidasServer extends AsyncTask<Void, Void, Void> {

    // URL para pegar os Subtopicos via JSON
    // private static String url = "http://avalan.herokuapp.com/tasabido/listar_materias/?format=json";
    private static String url = Statics.LISTAR_DUVIDAS + "?format=json";

    // Contexto da activity que chamou esta classe
    Context context;

    // Lista dos subtopicos obtidos do web service
    Vector<Duvida> listaDuvidas;

    // Dialog com barra de progresso mostrado na tela
    ProgressDialog proDialog;

    public GetDuvidasServer(Context context) {
        this.context = context;
    }

    // Mostra a barra de progresso na tela
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress loading dialog
        proDialog = new ProgressDialog(context);
        proDialog.setMessage("Sincronizando Duvidas...");
        proDialog.setCancelable(false);
        proDialog.show();
    }

    // Pega o JSON do web service com a lista de subtopicos
    @Override
    protected Void doInBackground(Void... arg0) {
        String jsonStr = null;

        // Fazendo requisição para o web service pelo metodo estatico httpGet
        try {
            jsonStr = WebRequest.httpGet(url);
        } catch (IOException e) {
            /** TODO analizar o tratamento de erro */
            e.printStackTrace();
        }

        Log.d("Response: ", "> " + jsonStr);

        // Monta a lista de subtopicos
        listaDuvidas = parseJsonDuvidas(jsonStr);

        return null;
    }

    // Metodo chamado ou terminar o doInBackground
    @Override
    protected void onPostExecute(Void requestresult) {
        super.onPostExecute(requestresult);
        // Tira o dialog de progresso da tela
        if (proDialog.isShowing())
            proDialog.dismiss();

        //atualiza o banco de dados local com os dados vindos do servidor
        DuvidaBDManager sinc = new DuvidaBDManager();
        if (listaDuvidas == null)
            listaDuvidas = new Vector<>();
        sinc.atualizarDuvidas(context, listaDuvidas);

    }

    // Metodo responsavel por quebrar o JSON em Subtopicos
    private Vector<Duvida> parseJsonDuvidas(String json){
        if (json != null)
        {
            try {

                Vector<Duvida> listarDuvidas = new Vector<>();

                // Transforma a string JSON em objeto
                JSONObject jsonObj = new JSONObject(json);

                // Extrai o array results do objeto JSON
                JSONArray subtopicosJson = jsonObj.getJSONArray("results");

                // Percorrendo todas os Subtopicos
                for (int i = 0; i < subtopicosJson.length(); i++)
                {
                    // Extrai o i-esimo objeto
                    JSONObject sJson = subtopicosJson.getJSONObject(i);

                    // Extrai as informações do objeto
                    int id_duvida = parseInt(sJson.getString("id"));
                    int id_usuario = parseInt(sJson.getString("usuario"));
                    int id_materia = parseInt(sJson.getString("materia"));
                    int id_subtopico = parseInt(sJson.getString("subtopico"));
                    String titulo = sJson.getString("titulo");
                    String descricao = sJson.getString("descricao");

                    // Adiciona o Subtopico obtido da lista de subtopicos
                    listarDuvidas.add(new Duvida(id_duvida, id_usuario, id_materia, id_subtopico, titulo, descricao));
                }

                return listarDuvidas;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Log.e("ServiceHandler", "No data received from HTTP request");
            return null;
        }

    }

}
