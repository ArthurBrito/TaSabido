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

import br.ufc.engsoftware.Ormlite.IdSubtopico;
import br.ufc.engsoftware.Ormlite.Monitoria;
import br.ufc.engsoftware.auxiliar.MonitoriaOpenDatabaseHelper;
import br.ufc.engsoftware.auxiliar.Statics;
import br.ufc.engsoftware.auxiliar.WebRequest;
import br.ufc.engsoftware.views.MonitoriaListView;
import static java.lang.Integer.parseInt;

/**
 * Created by Thiago on 26/05/2016.
 */
public class GetMonitoriasServer extends AsyncTask<Void, Void, Void> {

    // URL para pegar os Subtopicos via JSON
    // private static String url = "http://avalan.herokuapp.com/tasabido/listar_materias/?format=json";
    private static String url = Statics.LISTAR_MONITORIAS + "?format=json";

    // Contexto da activity que chamou esta classe
    Context context;

    // Lista dos subtopicos obtidos do web service
    Vector<Monitoria> listaMonitorias;

    // Dialog com barra de progresso mostrado na tela
    ProgressDialog proDialog;

    public GetMonitoriasServer(Context context) {
        this.context = context;
    }

    // Mostra a barra de progresso na tela
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress loading dialog
        proDialog = new ProgressDialog(context);
        proDialog.setMessage("Sincronizando Monitorias...");
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
        try {
            parseJsonMonitorias(jsonStr);
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
//        MonitoriaBDManager sinc = new MonitoriaBDManager();
//        if (listaMonitorias == null)
//            listaMonitorias = new Vector<>();
//        sinc.atualizarMonitorias(context, listaMonitorias);

    }

    // Metodo responsavel por quebrar o JSON em Subtopicos
    private void parseJsonMonitorias(String json) throws SQLException {
        if (json != null)
        {
            try {

                Vector<Monitoria> listarMonitorias = new Vector<>();

                // Transforma a string JSON em objeto
                JSONObject jsonObj = new JSONObject(json);

                // Extrai o array results do objeto JSON
                JSONArray monitoriasJson = jsonObj.getJSONArray("results");

                // Percorrendo todas os Subtopicos
                for (int i = 0; i < monitoriasJson.length(); i++)
                {
                    // Extrai o i-esimo objeto
                    JSONObject sJson = monitoriasJson.getJSONObject(i);

                    JSONArray subtopicosJson = sJson.getJSONArray("subtopico");

                    int id_subtopico = 0;

                    for (int index = 0; index < subtopicosJson.length(); index++) {
                        id_subtopico = subtopicosJson.getInt(0);
                    }

                    int id_monitoria = parseInt(sJson.getString("id"));
                    int id_usuario = parseInt(sJson.getString("usuario"));
                    int id_materia = parseInt(sJson.getString("materia"));
                    String username = sJson.getString("username");
                    String titulo = sJson.getString("titulo");
                    String dia = sJson.getString("dia");
                    String hora = sJson.getString("horario");
                    String endereco = sJson.getString("endereco");
                    String descricao = sJson.getString("descricao");
                    String data = sJson.getString("data_monitoria");

                    Monitoria monitoria = new Monitoria(id_monitoria, id_usuario, id_materia, id_subtopico, titulo, username, descricao, data, dia, hora, endereco);

                    salvarMonitoriaOrmlite(monitoria);

                    try {
                        salvarMonitoriaOrmlite(monitoria);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "No data received from HTTP request");
        }
    }

    public void salvarMonitoriaOrmlite(Monitoria monitoriaParam) throws SQLException {
        MonitoriaOpenDatabaseHelper monitoriaOpenDatabaseHelper = OpenHelperManager.getHelper(context,
                MonitoriaOpenDatabaseHelper.class);

        Dao<Monitoria, Long> monitoriaDao = monitoriaOpenDatabaseHelper.getDao();

        monitoriaDao.create(monitoriaParam);

        List<Monitoria> todos = monitoriaDao.queryForAll();
        String a = "ServiceHandler";
    }

    public MonitoriaListView getGerenciadorMonitoriasLVLV() {
        return getGerenciadorMonitoriasLVLV();
    }
}