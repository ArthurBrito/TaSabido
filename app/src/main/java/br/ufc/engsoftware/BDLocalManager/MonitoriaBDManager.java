package br.ufc.engsoftware.BDLocalManager;

import android.app.Activity;
import android.content.Context;
import java.util.Vector;
import br.ufc.engsoftware.models.Duvida;
import br.ufc.engsoftware.models.Monitoria;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Created by limaneto on 21/05/16.
 */
public class MonitoriaBDManager {
    private Realm realm;
    private RealmConfiguration realmConfig;
    private Context context;

    public MonitoriaBDManager(){

    }

    public MonitoriaBDManager(Context context){
        this.context = context;
    }

    public void activateRealm (Context context){
        // Create the Realm configuration
        this.context = context;
        realmConfig = new RealmConfiguration.Builder(context).deleteRealmIfMigrationNeeded().build();
        // Open the Realm for the UI thread.
        realm = Realm.getInstance(realmConfig);
    }

    public void atualizarMonitorias(Context context, final Vector<Monitoria> listaMonitoriasDoServidor){
        activateRealm(context);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realmm) {
                for (Monitoria monitoria : listaMonitoriasDoServidor) {
                    salvarMonitoria(monitoria);
                }

            }
        });
    }

    // Salva subtopico ao banco de dados local caso não exista
    public void salvarMonitoria(Monitoria monitoriaParam){
        activateRealm(context);
        if (!monitoriaJaSalva(monitoriaParam.getId_monitoria())) {
            Monitoria monitoria = realm.createObject(Monitoria.class);
            monitoria.setTitulo(monitoriaParam.getTitulo());
            monitoria.setData(monitoriaParam.getData());
            monitoria.setEndereco(monitoriaParam.getEndereco());
            monitoria.setDescricao(monitoriaParam.getDescricao());
            monitoria.setDia(monitoriaParam.getDia());
            monitoria.setHorario(monitoriaParam.getHorario());
            monitoria.setUsername(monitoriaParam.getUsername());
            monitoria.setId_usuario(monitoriaParam.getId_usuario());
            monitoria.setId_subtopico(monitoriaParam.getId_subtopico());
            monitoria.setId_materia(monitoriaParam.getId_materia());
            monitoria.setId_monitoria(monitoriaParam.getId_monitoria());
        }
    }

    /* METODOS PRA PEGAR DUVIDAS SALVAS NO BANDO DE DADOS DO APARELHO */
    public Vector<Monitoria> pegarMonitorias(Context context){
        activateRealm(context);
        RealmResults<Monitoria> resultInRealm = realm.where(Monitoria.class).findAll();
        return castRealmQuery(resultInRealm);
    }

    public Vector<Monitoria> pegarMonitoriasPorIdUsuario(Activity activity, int id_usuario){
        activateRealm(activity);
        final RealmResults<Monitoria> results = realm.where(Monitoria.class).equalTo("usuario", id_usuario).findAll();
        return castRealmQuery(results);
    }

    //POR ID_USUARIO && ID_SUBTOPICO
    public Vector<Monitoria> pegarMonitoriasPorIdUsuarioSubtopico(Activity activity, int id_usuario, int id_subtopico){
        activateRealm(activity);
        final RealmResults<Monitoria> results = realm.where(Monitoria.class).equalTo("usuario", id_usuario).equalTo("subtopico", id_subtopico).findAll();
        return castRealmQuery(results);
    }

    public Vector<Monitoria> pegarMonitoriasPorIdSubtopico(Activity activity, int id_subtopico){
        activateRealm(activity);
        final RealmResults<Monitoria> results = realm.where(Monitoria.class).equalTo("subtopico", id_subtopico).findAll();
        return castRealmQuery(results);
    }

    public Monitoria pegarMonitoriasPorIdMonitoria(Activity activity, int id_monitoria){
        activateRealm(activity);
        final RealmResults<Monitoria> results = realm.where(Monitoria.class).equalTo("id", id_monitoria).findAll();
        return results.first();
    }

    public void deleteTodasMonitorias(Activity activity){
        activateRealm(activity);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Monitoria.class);
            }
        });
    }

    public void deletarMonitoriaPorId(final int id_monitoria, Activity activity){
        activateRealm(activity);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Monitoria> result = realm.where(Monitoria.class).equalTo("id", id_monitoria).findAll();
                result.deleteFirstFromRealm();
            }
        });
    }

    // esse metodo verifica se a duvida ja existe no banco de dados
    public boolean monitoriaJaSalva(int id_monitoria){
        final RealmResults<Monitoria> results = realm.where(Monitoria.class).equalTo("id", id_monitoria).findAll();
        if (results.size() > 0){
            return true;
        }else{
            return false;
        }
    }

    public Vector<Monitoria> castRealmQuery(RealmResults<Monitoria> resultInRealm){
        Vector<Monitoria> monitoriasLista = new Vector<Monitoria>();
        for (Monitoria monitoria : resultInRealm) {
            monitoriasLista.add(monitoria);
        }
        return monitoriasLista;
    }
}