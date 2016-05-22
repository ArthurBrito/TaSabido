package br.ufc.engsoftware.BDLocalManager;

import android.app.Activity;
import java.util.ArrayList;
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

    public Materia pegarMateriaPorIdSubtopico(int id_materia){
        Materia materia = realm.where(Materia.class).equalTo("id_materia", id_materia).findFirst();
        return materia;
    }

    public void activateRealm (Activity activity){
        // Create the Realm configuration
        realmConfig = new RealmConfiguration.Builder(activity).deleteRealmIfMigrationNeeded().build();
        // Open the Realm for the UI thread.
        realm = Realm.getInstance(realmConfig);
    }

    public void atualizarMaterias(Activity activity, final ArrayList<Materia> listaMateriasDoServidor){
        activateRealm(activity);

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
        if (!materiaJaSalva(materiaParam.getId())) {
            Materia materia = realm.createObject(Materia.class);
            materia.setId(materiaParam.getId());
            materia.setNome(materiaParam.getNome());
        }
    }


    /* METODOS PRA PEGAR DUVIDAS SALVAS NO BANDO DE DADOS DO APARELHO */
    public ArrayList<Materia> pegarMaterias(){
        RealmResults<Materia> resultInRealm = realm.where(Materia.class).findAll();
        return castRealmQuery(resultInRealm);
    }

    public void deleteTodasMaterias(){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Materia.class);
            }
        });
    }

    // esse metodo verifica se a duvida ja existe no banco de dados
    public boolean materiaJaSalva(int id_materia){
        final RealmResults<Materia> results = realm.where(Materia.class).equalTo("id", id_materia).findAll();
        if (results.size() > 0){
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<Materia> castRealmQuery(RealmResults<Materia> resultInRealm){
        ArrayList<Materia> materiasLista = new ArrayList<Materia>();
        for (Materia materia : resultInRealm) {
            materiasLista.add(materia);
        }
        return materiasLista;
    }
}
