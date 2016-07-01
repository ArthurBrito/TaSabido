//package br.ufc.engsoftware.serverDAO;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Vector;
//
//import br.ufc.engsoftware.BDLocalManager.MateriaBDManager;
//import br.ufc.engsoftware.auxiliar.Statics;
//import br.ufc.engsoftware.auxiliar.WebRequest;
//import br.ufc.engsoftware.models.Materia;
//
//import static java.lang.Integer.parseInt;
//
///**
// * Created by Thiago on 25/05/2016.
// */
//// Classe para pegar os dados do servidor e salvar no BD local
//public class GetMateriasServer extends AsyncTask<Void, Void, Void> {
//
//    // URL para pegar as materias via JSON
//    private static String url = Statics.LISTAR_MATERIAS + "?format=json";
//
//    // Referencia ao contexto que está sendo mostrado
//    Context context;
//
//    // Lista das materias obbtidas do web service
//    Vector<Materia> listaMaterias;
//
//    // Dialog com barra de progresso mostrado na tela
//    ProgressDialog proDialog;
//
//    public GetMateriasServer(Context context) {
//        this.context = context;
//    }
//
//    // Mostra a barra de progresso na tela
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        // Showing progress loading dialog
//        proDialog = new ProgressDialog(context);
//        proDialog.setMessage("Sincronizando Matérias...");
//        proDialog.setCancelable(false);
//        proDialog.show();
//    }
//
//    // Pega o JSON do web service com a lista de materias
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
//        // Monta a lista de materias
//        listaMaterias = parseJsonMaterias(jsonStr);
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
//        MateriaBDManager sinc = new MateriaBDManager();
//        if (listaMaterias == null)
//            listaMaterias = new Vector<>();
//        sinc.atualizarMaterias(context, listaMaterias);
//
//    }
//
//    // Metodo responsavel por quebrar o JSON em Materias
//    private Vector<Materia> parseJsonMaterias(String json){
//        if (json != null)
//        {
//            try {
//
//                Vector<Materia> listaMaterias = new Vector<Materia>();
//
//                // Transforma a string JSON em objeto
//                JSONObject jsonObj = new JSONObject(json);
//
//                // Extrai o array results do objeto JSON
//                JSONArray materiasJson = jsonObj.getJSONArray("results");
//
//                // Percorrendo todas as Materias
//                for (int i = 0; i < materiasJson.length(); i++)
//                {
//                    // Extrai o i-esimo objeto
//                    JSONObject mJson = materiasJson.getJSONObject(i);
//
//                    // Extrai as informações do objeto
//                    int id = parseInt(mJson.getString("id"));
//                    String nome = mJson.getString("nome");
//
//                    // Adiciona a Materia obtida da lista de materias
//                    listaMaterias.add(new Materia(id, nome));
//                }
//                return listaMaterias;
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
//}
