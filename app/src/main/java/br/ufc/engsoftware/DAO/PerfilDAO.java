package br.ufc.engsoftware.DAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;

import br.ufc.engsoftware.aux.GetServerDataAsync;
import br.ufc.engsoftware.aux.PostServerDataAsync;
import br.ufc.engsoftware.aux.Statics;
import br.ufc.engsoftware.models.Perfil;

public class PerfilDAO {
    private Vector<Perfil> perfis = new Vector<Perfil>();
    private ArrayList<Perfil> arraySupermarketList;

    public void add(Perfil perfil) {

        //transforma o objeto perfil em uma string pra ser mandada pras requisições
        String param = concatenateParam(perfil);

//        if (perfis.contains(perfil)) {
//            //throw perfil já existente
//        }else{
            new PostServerDataAsync(param).execute(Statics.CADASTRAR_USUARIO);
//        }
    }

    public void delete(Perfil perfil) {
        if(perfis.isEmpty()){
            // throw Tal perfil não existe
        }if(!perfis.contains(perfil)){
            // throw  tal perfil não existe
        } else {
            perfis.remove(perfil);
        }
    }

    public Vector<Perfil> update(Perfil perfil) {
        boolean itHas = false;
        for(Perfil it:perfis){
            if(it.equals(perfil)){
                itHas=true;
                it.setEmail(perfil.getEmail());
                it.setNome(perfil.getNome());
//                it.setMoedas(perfil.getMoedas());
            }
        }
        if (!itHas) {
            //throw perfil não existe
        }
        return perfis;

    }

    public Vector<Perfil> list() {
          new GetServerDataAsync(new GetServerDataAsync.AsyncResponse(){

            @Override
            public void processFinish(String output){
                //Here you will receive the result fired from async class
                //of onPostExecute(result) method.
                JSONObject obj;

                try {
                    obj = new JSONObject(output);
                    JSONArray arraySupermarketJson = obj.getJSONArray("results");

                    arraySupermarketList = new ArrayList<Perfil>();
                    Perfil supermarket;

                    for (int i = 0; i < arraySupermarketJson.length(); i++) {

                        JSONObject jo_inside = arraySupermarketJson.getJSONObject(i);

                        String nome_usuario = jo_inside.getString("nome_usuario");
                        String email = jo_inside.getString("email");


                        supermarket = new Perfil(nome_usuario, email);

                        arraySupermarketList.add(supermarket);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                supermarketAdapter = new SupermarketAdapter(getActivity(), arraySupermarketList);
//                supermarketAdapter.notifyDataSetChanged();
//                supermarkets.setAdapter(supermarketAdapter);
            }
        }).execute("http://avalan.herokuapp.com/tasabido/lista_usuarios/");

        return perfis;
    }

    public String concatenateParam(Perfil perfil){
        String param = "nome_usuario=";
        param += perfil.getNome();
        param += "&";
        param += "email=";
        param += perfil.getEmail();
        param += "&";
        param += "senha=";
        param += perfil.getSenha();

        return param;
    }
}
