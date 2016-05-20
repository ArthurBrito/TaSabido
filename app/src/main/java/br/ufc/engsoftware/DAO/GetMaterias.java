package br.ufc.engsoftware.DAO;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Vector;

import br.ufc.engsoftware.models.Materia;
import br.ufc.engsoftware.tasabido.MateriaFragment;
import br.ufc.engsoftware.tasabido.MateriaListView;


import static java.lang.Integer.parseInt;

/**
 * Created by Thiago on 18/05/2016.
 */
public class GetMaterias extends AsyncTask<Void, Void, Void> {

    // URL para pegar as materias via JSON
    private static String url = "http://avalan.herokuapp.com/tasabido/listar_materias/?format=json";

    // Referencia ao fragmenta que está sendo mostrado
    Fragment fragment;
    Context context;

    // Referencia ao ListView do MateriaFragment
    ListView listviewMaterias;

    public MateriaListView getGerenciadorMateriasLV() {
        return gerenciadorMateriasLV;
    }

    // Classe responsável por montar o ListView
    MateriaListView gerenciadorMateriasLV;

    // Lista das materias obbtidas do web service
    Vector<Materia> listaMaterias;

    // Dialog com barra de progresso mostrado na tela
    ProgressDialog proDialog;

    public GetMaterias(Context context, ListView listviewMaterias, Fragment fragment) {
        this.context = context;
        this.listviewMaterias = listviewMaterias;
        this.fragment = fragment;
    }

    // Mostra a barra de progresso na tela
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress loading dialog
        proDialog = new ProgressDialog(context);
        proDialog.setMessage("Please wait...");
        proDialog.setCancelable(false);
        proDialog.show();
    }

    // Pega o JSON do web service com a lista de materias
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

        // Monta a lista de materias
        listaMaterias = parseJsonMaterias(jsonStr);

        return null;
    }

    // Metodo chamado ou terminar o doInBackground
    @Override
    protected void onPostExecute(Void requestresult) {
        super.onPostExecute(requestresult);
        // Tira o dialog de progresso da tela
        if (proDialog.isShowing())
            proDialog.dismiss();

        // Monta o ListView com os dados obtidos do web service
        gerenciadorMateriasLV = new MateriaListView(listviewMaterias, context, listaMaterias);

        // Chama o metodo do MateriaFragment responsavel por configurar o SearchView
        ((MateriaFragment)fragment).montarSearchViewMaterias();
    }

    // Metodo responsavel por quebrar o JSON em Materias
    private Vector<Materia> parseJsonMaterias(String json){
        if (json != null)
        {
            try {

                Vector<Materia> listaMaterias = new Vector<Materia>();

                // Transforma a string JSON em objeto
                JSONObject jsonObj = new JSONObject(json);

                // Extrai o array results do objeto JSON
                JSONArray materiasJson = jsonObj.getJSONArray("results");

                // Percorrendo todas as Materias
                for (int i = 0; i < materiasJson.length(); i++)
                {
                    // Extrai o i-esimo objeto
                    JSONObject mJson = materiasJson.getJSONObject(i);

                    // Extrai as informações do objeto
                    int id = parseInt(mJson.getString("id"));
                    String nome = mJson.getString("nome");

                    // Adiciona a Materia obtida da lista de materias
                    listaMaterias.add(new Materia(id, nome));
                }
                return listaMaterias;
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
