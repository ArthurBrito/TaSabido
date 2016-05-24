package br.ufc.engsoftware.BDLocalManager;

import android.app.Activity;

import java.util.ArrayList;

import br.ufc.engsoftware.models.Duvida;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by limaneto on 21/05/16.
 */
public class DuvidaBDManager {
    private Realm realm;
    private RealmConfiguration realmConfig;

    public void activateRealm (Activity activity){
        // Create the Realm configuration
        realmConfig = new RealmConfiguration.Builder(activity).deleteRealmIfMigrationNeeded().build();
        // Open the Realm for the UI thread.
        realm = Realm.getInstance(realmConfig);
    }

    public void atualizarDuvidas(Activity activity, final ArrayList<Duvida> listaDuvidasDoServidor){
        activateRealm(activity);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Duvida duvida: listaDuvidasDoServidor) {
                    salvarDuvida(duvida);
                }
            }
        });
    }

    // Adicionar duvida ao banco de dados local caso não exista
    public void salvarDuvida(Duvida duvidaParam){
        if (!duvidaJaSalva(duvidaParam.getId_duvida())) {
            Duvida duvida = realm.createObject(Duvida.class);
            duvida.setId_duvida(duvidaParam.getId_duvida());
            duvida.setId_usuario(duvidaParam.getId_usuario());
            duvida.setId_materia(duvidaParam.getId_materia());
            duvida.setId_subtopico(duvidaParam.getId_subtopico());
            duvida.setTitulo(duvidaParam.getTitulo());
            duvida.setDescricao(duvidaParam.getDescricao());
        }
    }


    /* METODOS PRA PEGAR DUVIDAS SALVAS NO BANDO DE DADOS DO APARELHO */
    public ArrayList<Duvida> pegarDuvidasPorIdSubtopico(Activity activity, int id_subtopico){
        activateRealm(activity);
        RealmResults<Duvida> resultInRealm = realm.where(Duvida.class).equalTo("id_subtopico", id_subtopico).findAll();
        return castRealmQuery(resultInRealm);
    }

    public ArrayList<Duvida> pegarDuvidasUsuario(Activity activity, int id_usuario){
        activateRealm(activity);
        RealmResults<Duvida> resultInRealm = realm.where(Duvida.class).equalTo("id_usuario", id_usuario).findAll();
        return castRealmQuery(resultInRealm);
    }

    public void deleteTodasDuvidas(Activity activity){
        activateRealm(activity);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Duvida.class);
            }
        });
    }

    // esse metodo verifica se a duvida ja existe no banco de dados
    public boolean duvidaJaSalva(int id_duvidaParam){

        final RealmResults<Duvida> results = realm.where(Duvida.class).equalTo("id_duvida", id_duvidaParam).findAll();

        if (results.size() > 0){
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<Duvida> castRealmQuery(RealmResults<Duvida> resultInRealm){
        ArrayList<Duvida> duvidasLista = new ArrayList<Duvida>();
        for (Duvida duvida : resultInRealm) {
            duvidasLista.add(duvida);
        }
        return duvidasLista;
    }
}
