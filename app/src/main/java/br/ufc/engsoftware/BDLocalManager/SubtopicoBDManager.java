package br.ufc.engsoftware.BDLocalManager;

import android.app.Activity;
import java.util.ArrayList;
import java.util.Vector;

import br.ufc.engsoftware.models.Materia;
import br.ufc.engsoftware.models.Subtopico;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by limaneto on 21/05/16.
 */
public class SubtopicoBDManager {
    private Realm realm;
    private RealmConfiguration realmConfig;

    public void activateRealm (Activity activity){
        // Create the Realm configuration
        realmConfig = new RealmConfiguration.Builder(activity).deleteRealmIfMigrationNeeded().build();
        // Open the Realm for the UI thread.
        realm = Realm.getInstance(realmConfig);
    }

    public void atualizarSubtopicos(Activity activity, final Vector<Subtopico> listaSubtopicosDoServidor){
        activateRealm(activity);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realmm) {
                for (Subtopico subtopico : listaSubtopicosDoServidor) {
                        salvarSubtopico(subtopico);
                }
            }
        });
    }

    // Salva subtopico ao banco de dados local caso não exista
    public void salvarSubtopico(Subtopico subtopicoParam){
        if (!subtopicoJaSalvo(subtopicoParam.getId_subtopico())) {
            Subtopico subtopico = realm.createObject(Subtopico.class);
            subtopico.setId_subtopico(subtopicoParam.getId_subtopico());
            subtopico.setId_materia(subtopicoParam.getId_materia());
            subtopico.setNome_subtopico(subtopicoParam.getNome_subtopico());
        }
    }


    /* METODOS PRA PEGAR DUVIDAS SALVAS NO BANDO DE DADOS DO APARELHO */
    public Vector<Subtopico> pegarSubtopicos(Activity activity){
        activateRealm(activity);
        RealmResults<Subtopico> resultInRealm = realm.where(Subtopico.class).findAll();
        return castRealmQuery(resultInRealm);
    }

    public Vector<Subtopico> pegarSubtopicosPorIdMateria(Activity activity, int id_materia){
        activateRealm(activity);
        final RealmResults<Subtopico> results = realm.where(Subtopico.class).equalTo("id_materia", id_materia).findAll();
        return castRealmQuery(results);
    }

    public void deleteTodasSubtopicos(Activity activity){
        activateRealm(activity);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Subtopico.class);
            }
        });
    }

    // esse metodo verifica se a duvida ja existe no banco de dados
    public boolean subtopicoJaSalvo(int id_subtopico){
        final RealmResults<Subtopico> results = realm.where(Subtopico.class).equalTo("id_subtopico", id_subtopico).findAll();

        if (results.size() > 0){
            return true;
        }else{
            return false;
        }
    }

    public Vector<Subtopico> castRealmQuery(RealmResults<Subtopico> resultInRealm){
        Vector<Subtopico> subtopicosLista = new Vector<Subtopico>();
        for (Subtopico subtopico : resultInRealm) {
            subtopicosLista.add(subtopico);
        }
        return subtopicosLista;
    }
}
