package br.ufc.engsoftware.DAO;

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

import br.ufc.engsoftware.BDLocalManager.SubtopicoBDManager;
import br.ufc.engsoftware.auxiliar.Statics;
import br.ufc.engsoftware.auxiliar.WebRequest;
import br.ufc.engsoftware.models.Subtopico;
import br.ufc.engsoftware.views.SubtopicoListView;

import static java.lang.Integer.parseInt;

/**
 * Created by Thiago on 21/05/2016.
 */
public class SubtopicosDAO extends AsyncTask<Void, Void, Void> {

    // URL para pegar os Subtopicos via JSON
    // private static String url = "http://avalan.herokuapp.com/tasabido/listar_materias/?format=json";
    private static String url = Statics.LISTAR_SUBTOPICOS + "?format=json";

    // Contexto da activity que chamou esta classe
    Context context;

    //Activiy the chamou essa classe
    Activity activity;

    // Referencia ao ListView do ListaSubtopicosActivity
    ListView listviewSubtopicos;

    // Classe responsável por montar o ListView
    SubtopicoListView gerenciadorSubtopicosLV;

    // Lista dos subtopicos obtidos do web service
    Vector<Subtopico> listaSubtopicos;

    // Dialog com barra de progresso mostrado na tela
    ProgressDialog proDialog;

    public SubtopicosDAO(Activity activity, Context context, ListView listviewSubtopicos) {
        this.activity = activity;
        this.context = context;
        this.listviewSubtopicos = listviewSubtopicos;
    }

    // Mostra a barra de progresso na tela
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress loading dialog
        proDialog = new ProgressDialog(context);
        proDialog.setMessage("Carregando Informações...");
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
        listaSubtopicos = parseJsonMaterias(jsonStr);

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
        SubtopicoBDManager sinc = new SubtopicoBDManager();
        if (listaSubtopicos == null)
            listaSubtopicos = new Vector<>();
        sinc.atualizarSubtopicos(activity, listaSubtopicos);

        // Monta o ListView com os dados obtidos do web service
        gerenciadorSubtopicosLV = new SubtopicoListView(listviewSubtopicos, context, listaSubtopicos);

    }

    // Metodo responsavel por quebrar o JSON em Subtopicos
    private Vector<Subtopico> parseJsonMaterias(String json){
        if (json != null)
        {
            try {

                Vector<Subtopico> listarSubtopicos = new Vector<>();

                // Transforma a string JSON em objeto
                JSONObject jsonObj = new JSONObject(json);


                //tem que bolar a logica do next depois viu
//                String nextPage = jsonObj.getString("next");

                // Extrai o array results do objeto JSON
                JSONArray subtopicosJson = jsonObj.getJSONArray("results");

                // Percorrendo todas os Subtopicos
                for (int i = 0; i < subtopicosJson.length(); i++)
                {
                    // Extrai o i-esimo objeto
                    JSONObject sJson = subtopicosJson.getJSONObject(i);

                    // Extrai as informações do objeto
                    int id_subtopico = parseInt(sJson.getString("id"));
                    int id_materia = parseInt(sJson.getString("materia"));
                    String nome = sJson.getString("nome");

                    // Adiciona o Subtopico obtido da lista de subtopicos
                    listarSubtopicos.add(new Subtopico(id_subtopico, id_materia, nome));
                }

                return listarSubtopicos;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Log.e("ServiceHandler", "No data received from HTTP request");
            return null;
        }

    }

    public SubtopicoListView getGerenciadorSubtopicosLV() {
        return gerenciadorSubtopicosLV;
    }
}
