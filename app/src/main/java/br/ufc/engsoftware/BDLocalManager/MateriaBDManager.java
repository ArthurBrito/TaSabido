package br.ufc.engsoftware.BDLocalManager;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.Vector;

import br.ufc.engsoftware.models.Duvida;
import br.ufc.engsoftware.models.Materia;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by limaneto on 21/05/16.
 */
public class MateriaBDManager {
    private Realm realm;
    private RealmConfiguration realmConfig;

    public void activateRealm (Context context){
        // Create the Realm configuration
        realmConfig = new RealmConfiguration.Builder(context).deleteRealmIfMigrationNeeded().build();
        // Open the Realm for the UI thread.
        realm = Realm.getInstance(realmConfig);
    }

    public void atualizarMaterias(Context context, final Vector<Materia> listaMateriasDoServidor){
        activateRealm(context);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Materia materia : listaMateriasDoServidor) {
                    salvarDuvida(materia);
                }
            }
        });
    }

    public void salvarDuvida(Materia materiaParam){
        // Adicionar duvida ao banco de dados local
        if (!materiaJaSalva(materiaParam.getId_materia())) {
            Materia materia = realm.createObject(Materia.class);
            materia.setId_materia(materiaParam.getId_materia());
            materia.setNome(materiaParam.getNome());
        }
    }


    /* METODOS PRA PEGAR DUVIDAS SALVAS NO BANDO DE DADOS DO APARELHO */
    public Vector<Materia> pegarMaterias(Context context){
        activateRealm(context);
        RealmResults<Materia> resultInRealm = realm.where(Materia.class).findAll();
        return castRealmQuery(resultInRealm);
    }

    public Materia pegarMateriaPorIdSubtopico(Activity activity, int id_materia){
        activateRealm(activity);
        Materia materia = realm.where(Materia.class).equalTo("id_materia", id_materia).findFirst();
        return materia;
    }

    public void deleteTodasMaterias(Activity activity){
        activateRealm(activity);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Materia.class);
            }
        });
    }

    // esse metodo verifica se a duvida ja existe no banco de dados
    public boolean materiaJaSalva(int id_materia){
        final RealmResults<Materia> results = realm.where(Materia.class).equalTo("id_materia", id_materia).findAll();
        if (results.size() > 0){
            return true;
        }else{
            return false;
        }
    }

    public Vector<Materia> castRealmQuery(RealmResults<Materia> resultInRealm){
        Vector<Materia> materiasLista = new Vector<Materia>();
        for (Materia materia : resultInRealm) {
            materiasLista.add(materia);
        }
        return materiasLista;
    }
}
