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
import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.auxiliar.WebRequest;
import br.ufc.engsoftware.models.Duvida;
import br.ufc.engsoftware.views.DuvidaListView;

import static java.lang.Integer.parseInt;

/**
 * Created by Thiago on 26/05/2016.
 */
public class GetMoedasServer extends AsyncTask<Void, Void, Void> {

    // URL para pegar os Subtopicos via JSON
    // private static String url = "http://avalan.herokuapp.com/tasabido/listar_materias/?format=json";
    private String url = Statics.BUSCAR_MOEDA;

    // Contexto da activity que chamou esta classe
    Context context;

    Utils util;

    // Lista dos subtopicos obtidos do web service
    Vector<Duvida> listaDuvidas;

    public GetMoedasServer(Context context) {
        this.context = context;
    }

    // Mostra a barra de progresso na tela
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    // Pega o JSON do web service com a lista de subtopicos
    @Override
    protected Void doInBackground(Void... arg0) {
        String jsonStr = null;
        util = new Utils(this.context);
        String id_usuario_string = util.getFromSharedPreferences("id_usuario","");
        url += id_usuario_string;

        // Fazendo requisição para o web service pelo metodo estatico httpGet
        try {
            jsonStr = WebRequest.httpGet(url);
        } catch (IOException e) {
            /** TODO analizar o tratamento de erro */
            e.printStackTrace();
        }

        Log.d("Response: ", "> " + jsonStr);

        // Monta a lista de subtopicos
         saveMoedasFromJson(jsonStr);

        return null;
    }

    // Metodo responsavel por quebrar o JSON em Subtopicos
    private void saveMoedasFromJson(String json){
        if (json != null)
        {
            try {
                JSONObject jsonObj = new JSONObject(json);
                int quantia = jsonObj.getInt("quantia");
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

        //atualiza o banco de dados local com os dados vindos do servidor
        DuvidaBDManager sinc = new DuvidaBDManager();
        if (listaDuvidas == null)
            listaDuvidas = new Vector<>();
        sinc.atualizarDuvidas(context, listaDuvidas);

    }
}
