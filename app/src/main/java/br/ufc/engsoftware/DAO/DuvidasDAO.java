package br.ufc.engsoftware.DAO;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.ufc.engsoftware.auxiliar.GetServerDataAsync;
import br.ufc.engsoftware.auxiliar.PostServerDataAsync;
import br.ufc.engsoftware.BDLocalManager.DuvidaBDManager;
import br.ufc.engsoftware.auxiliar.Statics;
import br.ufc.engsoftware.models.Duvida;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by israelcvidal on 21/05/16.
 */
public class DuvidasDAO {
    JSONObject obj;
    ArrayList<Duvida> arrayDuvidaList;
    Duvida duvida;
    RealmConfiguration realmConfig;
    Realm realm;


    public void pegarDuvidasServidor(final Activity activity){
        arrayDuvidaList = new ArrayList<Duvida>();

        try {
            new GetServerDataAsync(new GetServerDataAsync.AsyncResponse(){

                @Override
                public void processFinish(String output){
                    try {
                        obj = new JSONObject(output);

                        JSONArray arraySupermarketJson = obj.getJSONArray("results");

                        for (int i = 0; i < arraySupermarketJson.length(); i++) {

                            JSONObject jo_inside = arraySupermarketJson.getJSONObject(i);

                            int id_duvida = jo_inside.getInt("id");
                            int id_usuario = jo_inside.getInt("usuario");
                            int id_materia = jo_inside.getInt("materia");
                            int id_subtopico = jo_inside.getInt("subtopico");
                            String titulo = jo_inside.getString("titulo");
                            String descricao = jo_inside.getString("descricao");

                            duvida = new Duvida(id_duvida, id_usuario, id_materia, id_subtopico, titulo, descricao);
                            arrayDuvidaList.add(duvida);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    DuvidaBDManager sinc = new DuvidaBDManager();
                    sinc.atualizarDuvidas(activity, arrayDuvidaList);
//                    duvidaAdapter = new DuvidaAdapter(getActivity(), arrayDuvidaList);
//                    duvidaAdapter.notifyDataSetChanged();
//                    supermarkets.setAdapter(duvidaAdapter);
                }
            }).execute(Statics.LISTAR_DUVIDAS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String add(Duvida duvida) {

        //transforma o objeto perfil em uma string pra ser mandada pras requisições
        String param = concatenateParam(duvida);
        String result;


        try {
            new PostServerDataAsync(param, new PostServerDataAsync.AsyncResponse(){
                public void processFinish(String output){

                }
            }).execute(Statics.CADASTRAR_DUVIDA);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String concatenateParam(Duvida duvida){
        String param = "titulo=";
        param += duvida.getTitulo();
        param += "&";
        param += "descricao=";
        param += duvida.getDescricao();
        param += "&";
        param += "usuario=";
        param += duvida.getId_usuario();
        param += "&";
        param += "materia=";
        param += duvida.getId_materia();
        param += "&";
        param += "subtopico=";
        param += duvida.getId_subtopico();

        return param;
    }
}

