package br.ufc.engsoftware.BDLocalManager;

import android.app.Activity;
import android.content.Context;
import java.util.Vector;
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
    private Context context;

    public DuvidaBDManager(){

    }

    public DuvidaBDManager(Context context){
        this.context = context;
    }

    public void activateRealm (Context context){
        // Create the Realm configuration
        this.context = context;
        realmConfig = new RealmConfiguration.Builder(context).deleteRealmIfMigrationNeeded().build();
        // Open the Realm for the UI thread.
        realm = Realm.getInstance(realmConfig);
    }

    public void atualizarDuvidas(Context context, final Vector<Duvida> listaDuvidasDoServidor){
        activateRealm(context);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realmm) {
                for (Duvida duvida : listaDuvidasDoServidor) {
                    salvarDuvida(duvida);
                }

            }
        });
    }

    // Salva subtopico ao banco de dados local caso não exista
    public void salvarDuvida(Duvida duvidaParam){
            activateRealm(context);
            Duvida duvida = realm.createObject(Duvida.class);
            duvida.setTitulo(duvidaParam.getTitulo());
            duvida.setDescricao(duvidaParam.getDescricao());
            duvida.setId_usuario(duvidaParam.getId_usuario());
            duvida.setUsername(duvidaParam.getUsername());
            duvida.setData_duvida(duvidaParam.getData_duvida());
            duvida.setId_subtopico(duvidaParam.getId_subtopico());
            duvida.setId_duvida(duvidaParam.getId_duvida());
    }

    public void updateDuvida(Duvida duvidaParam){
        activateRealm(context);

        if (duvidaJaSalva(duvidaParam.getId_duvida())) {

            Duvida duvida = realm.where(Duvida.class).equalTo("id", duvidaParam.getId_duvida()).findFirst();

            realm.beginTransaction();
            duvida.setTitulo(duvidaParam.getTitulo());
            duvida.setDescricao(duvidaParam.getDescricao());
            duvida.setId_usuario(duvidaParam.getId_usuario());
            duvida.setUsername(duvidaParam.getUsername());
            duvida.setData_duvida(duvidaParam.getData_duvida());
            duvida.setId_subtopico(duvidaParam.getId_subtopico());
            duvida.setId_duvida(duvidaParam.getId_duvida());
            realm.commitTransaction();
        }
    }


    /* METODOS PRA PEGAR DUVIDAS SALVAS NO BANDO DE DADOS DO APARELHO */
    public Vector<Duvida> pegarDuvidas(Context context){
        activateRealm(context);
        RealmResults<Duvida> resultInRealm = realm.where(Duvida.class).findAll();
        return castRealmQuery(resultInRealm);
    }

    //POR ID_DUVIDA
    public Duvida pegarDuvidasPorIdDuvida(Activity activity, int id_duvida){
        activateRealm(activity);
        final RealmResults<Duvida> results = realm.where(Duvida.class).equalTo("id", id_duvida).findAll();
        return results.first();
    }

    //POR ID_USUARIO
    public Vector<Duvida> pegarDuvidasPorIdUsuario(Activity activity, int id_usuario){
        activateRealm(activity);
        final RealmResults<Duvida> results = realm.where(Duvida.class).equalTo("usuario", id_usuario).findAll();
        return castRealmQuery(results);
    }

    //POR ID_USUARIO && ID_SUBTOPICO
    public Vector<Duvida> pegarDuvidasPorIdUsuarioSubtopico(Activity activity, int id_usuario, int id_subtopico){
        activateRealm(activity);
        final RealmResults<Duvida> results = realm.where(Duvida.class).equalTo("usuario", id_usuario).equalTo("subtopico", id_subtopico).findAll();
        return castRealmQuery(results);
    }

    //POR ID_SUBTOPICO
     public Vector<Duvida> pegarDuvidasPorIdSubtopico(Activity activity, int id_subtopico){
        activateRealm(activity);
        final RealmResults<Duvida> results = realm.where(Duvida.class).equalTo("subtopico", id_subtopico).findAll();
        return castRealmQuery(results);
    }

    public void deleteTodasDuvidas(Context context){
        activateRealm(context);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Duvida.class);
            }
        });
    }

    public void deleteDuvidaPorIdDuvida(Activity activity, final int id_duvida){
        activateRealm(activity);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                final RealmResults<Duvida> results = realm.where(Duvida.class).equalTo("id", id_duvida).findAll();
                results.deleteAllFromRealm();
            }
        });
    }

    // esse metodo verifica se a duvida ja existe no banco de dados
    public boolean duvidaJaSalva(int id_duvida){
        final RealmResults<Duvida> results = realm.where(Duvida.class).equalTo("id", id_duvida).findAll();
        if (results.size() > 0){
            return true;
        }else{
            return false;
        }
    }

    public Vector<Duvida> castRealmQuery(RealmResults<Duvida> resultInRealm){
        Vector<Duvida> duvidasLista = new Vector<Duvida>();
        for (Duvida duvida : resultInRealm) {
            String titulo = duvida.getTitulo();
            int id_duvida = duvida.getId_duvida();
            duvidasLista.add(duvida);
        }
        return duvidasLista;
    }
}