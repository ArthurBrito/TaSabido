package br.ufc.engsoftware.serverDAO;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import br.ufc.engsoftware.BDLocalManager.DuvidaBDManager;
import br.ufc.engsoftware.BDLocalManager.MateriaBDManager;
import br.ufc.engsoftware.BDLocalManager.SubtopicoBDManager;
import br.ufc.engsoftware.Ormlite.Monitoria;
import br.ufc.engsoftware.auxiliar.MonitoriaOpenDatabaseHelper;
import br.ufc.engsoftware.auxiliar.Statics;
import br.ufc.engsoftware.auxiliar.WebRequest;
import br.ufc.engsoftware.models.Duvida;
import br.ufc.engsoftware.models.Materia;
import br.ufc.engsoftware.models.Subtopico;

import static java.lang.Integer.parseInt;

/**
 * Created by Thiago on 25/05/2016.
 */
// Classe para pegar os dados do servidor e salvar no BD local
public class GetMateriasServer extends AsyncTask<Void, Void, Void> {

    // URL para pegar as materias via JSON
    private static String url = Statics.LISTAR_MATERIAS + "?format=json";

    // Referencia ao contexto que está sendo mostrado
    Context context;

    // Lista das materias obbtidas do web service
    Vector<Materia> listaMaterias;
    Vector<Subtopico> listaSubtopicos;
    Vector<Duvida> listaDuvidas;

    // Dialog com barra de progresso mostrado na tela
    ProgressDialog proDialog;

    public GetMateriasServer(Context context) {
        this.context = context;
    }

    // Mostra a barra de progresso na tela
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress loading dialog
        proDialog = new ProgressDialog(context);
        proDialog.setMessage("Sincronizando Matérias...");
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
         parseJsonMaterias(jsonStr);

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
        MateriaBDManager sinc = new MateriaBDManager();
        SubtopicoBDManager sincSub = new SubtopicoBDManager();
        DuvidaBDManager sincDuv = new DuvidaBDManager();
        sinc.atualizarMaterias(context, listaMaterias);
        sincSub.atualizarSubtopicos(context, listaSubtopicos);
        sincDuv.atualizarDuvidas(context, listaDuvidas);

    }

    // Metodo responsavel por quebrar o JSON em Materias
    private void parseJsonMaterias(String json){
        if (json != null)
        {
            try {
                listaSubtopicos = new Vector<>();
                listaDuvidas = new Vector<>();
                listaMaterias = new Vector<Materia>();

                // Transforma a string JSON em objeto
                JSONObject jsonObj = new JSONObject(json);

                // Extrai o array results do objeto JSON
                JSONArray materiasJson = jsonObj.getJSONArray("results");

                // Percorrendo todas as Materias
                for (int i = 0; i < materiasJson.length(); i++)
                {
                    JSONObject materiaJson = materiasJson.getJSONObject(i);
                    int id = parseInt(materiaJson.getString("id"));
                    String nome = materiaJson.getString("nome");
                    listaMaterias.add(new Materia(id, nome));


                    JSONArray subtopicosJson = materiaJson.getJSONArray("subtopicos");
                    //PERCORRER OS SUBTÓPICOS DE CARA MATÉRIA
                    for (int a = 0; a < subtopicosJson.length(); a++){
                        JSONObject subtopicoJson = subtopicosJson.getJSONObject(a);
                        int id_s = parseInt(subtopicoJson.getString("id"));
                        int materia_s = parseInt(subtopicoJson.getString("materia"));
                        String nome_s = subtopicoJson.getString("nome");
                        listaSubtopicos.add(new Subtopico(id_s, materia_s, nome_s));


                        JSONArray duvidasJson = subtopicoJson.getJSONArray("duvidas");
                        //PERCORRER AS DÚVIDAS DE CARA SUBTÓPICO
                        for (int c = 0; c < duvidasJson.length(); c++){
                            JSONObject duvidaJson = duvidasJson.getJSONObject(c);
                            int id_d = parseInt(duvidaJson.getString("id"));
                            int usuario_d = parseInt(duvidaJson.getString("usuario"));
                            int subtopico_d = parseInt(duvidaJson.getString("subtopico"));
                            String titulo_s = duvidaJson.getString("titulo");
                            String descricao_s = duvidaJson.getString("descricao");
                            String data_duvida_s = duvidaJson.getString("data_duvida");
                            String username_s = duvidaJson.getString("username");
                            listaDuvidas.add(new Duvida(id_d, usuario_d, subtopico_d, titulo_s, descricao_s, data_duvida_s, username_s ));
                        }

                        JSONArray monitoriasJson = subtopicoJson.getJSONArray("monitorias");
                        //PERCORRER AS MONITORIAS DE CADA SUBTÓPICO
                        for (int c = 0; c < monitoriasJson.length(); c++){
                            JSONObject monitoriaJson = monitoriasJson.getJSONObject(c);
                            int id_d = parseInt(monitoriaJson.getString("id"));
                            int usuario_d = parseInt(monitoriaJson.getString("usuario"));
                            int materia = parseInt(monitoriaJson.getString("materia"));
                            String titulo_s = monitoriaJson.getString("titulo");
                            String descricao_s = monitoriaJson.getString("descricao");
                            String data_monitoria_s = monitoriaJson.getString("data_monitoria");
                            String dia = monitoriaJson.getString("dia");
                            String horario = monitoriaJson.getString("horario");
                            String endereco = monitoriaJson.getString("endereco");
                            String username_s = monitoriaJson.getString("username");



                            JSONArray subtopicosMonitoriaJson = monitoriaJson.getJSONArray("subtopico");
                            //PERCORRER AS DÚVIDAS DE CARA SUBTÓPICO
//                                JSONObject tJson = subtopicosMonitoriaJson.getJSONObject(0);
//                            for (int index = 0; index < subtopicosMonitoriaJson.length(); index++){
                                int subtopico = (int) subtopicosMonitoriaJson.get(0);
//                            }

                            Monitoria monitoria = new Monitoria(id_d, usuario_d, materia, subtopico, titulo_s, username_s, descricao_s, data_monitoria_s, dia, horario, endereco);


                            salvarMonitoriaOrmlite(monitoria);


                        }
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "No data received from HTTP request");
        }
    }

    public void salvarMonitoriaOrmlite(Monitoria monitoriaParam){
        MonitoriaOpenDatabaseHelper monitoriaOpenDatabaseHelper = OpenHelperManager.getHelper(context,
                MonitoriaOpenDatabaseHelper.class);

        Dao<Monitoria, Long> monitoriaDao = null;
        try {
            monitoriaDao = monitoriaOpenDatabaseHelper.getDao();
            monitoriaDao.create(monitoriaParam);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
