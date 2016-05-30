//package br.ufc.engsoftware.DAO;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.os.AsyncTask;
//import android.util.Log;
//import android.widget.ListView;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Vector;
//
//import br.ufc.engsoftware.BDLocalManager.DuvidaBDManager;
//import br.ufc.engsoftware.BDLocalManager.MonitoriaBDManager;
//import br.ufc.engsoftware.BDLocalManager.SubtopicoBDManager;
//import br.ufc.engsoftware.auxiliar.Statics;
//import br.ufc.engsoftware.auxiliar.WebRequest;
//import br.ufc.engsoftware.models.Duvida;
//import br.ufc.engsoftware.models.Monitoria;
//import br.ufc.engsoftware.models.Subtopico;
//import br.ufc.engsoftware.views.DuvidaListView;
//import br.ufc.engsoftware.views.MonitoriaListView;
//import br.ufc.engsoftware.views.SubtopicoListView;
//
//import static java.lang.Integer.parseInt;
//
///**
// * Created by Thiago on 21/05/2016.
// */
//public class MonitoriaDAO extends AsyncTask<Void, Void, Void> {
//
//    // URL para pegar os Subtopicos via JSON
//    // private static String url = "http://avalan.herokuapp.com/tasabido/listar_materias/?format=json";
//    private static String url = Statics.LISTAR_MONITORIAS + "?format=json";
//
//    // Contexto da activity que chamou esta classe
//    Context context;
//
//    //Activiy the chamou essa classe
//    Activity activity;
//
//    // Referencia ao ListView do ListaSubtopicosActivity
//    ListView listviewMonitorias;
//
//    // Classe responsável por montar o ListView
//    MonitoriaListView gerenciadorMonitoriasLV;
//
//    // Lista dos subtopicos obtidos do web service
//    Vector<Monitoria> listaMonitorias;
//
//    // Dialog com barra de progresso mostrado na tela
//    ProgressDialog proDialog;
//
//    public MonitoriaDAO(Activity activity, Context context, ListView listviewMonitorias) {
//        this.activity = activity;
//        this.context = context;
//        this.listviewMonitorias = listviewMonitorias;
//    }
//
//    // Mostra a barra de progresso na tela
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        // Showing progress loading dialog
//        proDialog = new ProgressDialog(context);
//        proDialog.setMessage("Carregando Informações...");
//        proDialog.setCancelable(false);
//        proDialog.show();
//    }
//
//    // Pega o JSON do web service com a lista de subtopicos
//    @Override
//    protected Void doInBackground(Void... arg0) {
//        String jsonStr = null;
//
//        // Fazendo requisição para o web service pelo metodo estatico httpGet
//        try {
//            jsonStr = WebRequest.httpGet(url);
//        } catch (IOException e) {
//            /** TODO analizar o tratamento de erro */
//            e.printStackTrace();
//        }
//
//        Log.d("Response: ", "> " + jsonStr);
//
//        // Monta a lista de subtopicos
//        listaMonitorias = parseJsonMonitorias(jsonStr);
//
//        return null;
//    }
//
//    // Metodo chamado ou terminar o doInBackground
//    @Override
//    protected void onPostExecute(Void requestresult) {
//        super.onPostExecute(requestresult);
//        // Tira o dialog de progresso da tela
//        if (proDialog.isShowing())
//            proDialog.dismiss();
//
//        //atualiza o banco de dados local com os dados vindos do servidor
//        MonitoriaBDManager sinc = new MonitoriaBDManager();
//        sinc.atualizarMonitorias(activity, listaMonitorias);
//
//        // Monta o ListView com os dados obtidos do web service
//        gerenciadorMonitoriasLV = new MonitoriaListView(listviewMonitorias, context, listaMonitorias);
//
//    }
//
//    // Metodo responsavel por quebrar o JSON em Subtopicos
//    private Vector<Monitoria> parseJsonMonitorias(String json){
//        if (json != null)
//        {
//            try {
//
//                Vector<Monitoria> listarMonitorias = new Vector<>();
//
//                // Transforma a string JSON em objeto
//                JSONObject jsonObj = new JSONObject(json);
//
//                // Extrai o array results do objeto JSON
//                JSONArray monitoriasJson = jsonObj.getJSONArray("results");
//
//                // Percorrendo todas os Subtopicos
//                for (int i = 0; i < monitoriasJson.length(); i++)
//                {
//                    // Extrai o i-esimo objeto
//                    JSONObject sJson = monitoriasJson.getJSONObject(i);
//
//                    // Extrai as informações do objeto
//                    int id_monitoria = parseInt(sJson.getString("id"));
//                    int id_usuario = parseInt(sJson.getString("usuario"));
//                    int id_materia = parseInt(sJson.getString("materia"));
//                    int id_subtopico = parseInt(sJson.getString("subtopico"));
//                    String titulo = sJson.getString("titulo");
//                    String descricao = sJson.getString("descricao");
//                    String data = sJson.getString("data_monitoria");
//
//                    // Adiciona o Subtopico obtido da lista de subtopicos
//                    listarMonitorias.add(new Monitoria(id_monitoria, id_usuario, id_materia, id_subtopico, titulo, descricao, data));
//                }
//
//                return listarMonitorias;
//            } catch (JSONException e) {
//                e.printStackTrace();
//                return null;
//            }
//        } else {
//            Log.e("ServiceHandler", "No data received from HTTP request");
//            return null;
//        }
//
//    }
//
//    public MonitoriaListView getGerenciadorMonitoriasLVLV() {
//        return gerenciadorMonitoriasLV;
//    }
//}
