package br.ufc.engsoftware.tasabido;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import br.ufc.engsoftware.Ormlite.Data;
import br.ufc.engsoftware.Ormlite.config.DatabaseManager;
import br.ufc.engsoftware.Presenters.DataPresenter;
import br.ufc.engsoftware.auxiliar.Utils;

/**
 * Created by limaneto on 24/07/16.
 */
public class MainActivity extends AppCompatActivity implements DataPresenter.DataCallbackListener {

    private DataPresenter dataPresenter;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataPresenter = new DataPresenter(this);
        utils =  new Utils(this);
        sincronizarInfo();
    }

    // Baixando dados do servidor e salvando no BD local
    public void sincronizarInfo(){
        utils.createProgressDialog("Sincronizando informações.");
        if(utils.checkConnection(this))
            dataPresenter.getServerData();

    }

    @Override
    public void finishedGettingServerData(Data cursos) {
        utils.dismissProgressDialog();
        salvarDadosDoServidor(cursos);
    }

    public void salvarDadosDoServidor(Data cursosParam){
        DatabaseManager db = new DatabaseManager(this);
        db.saveAllData(cursosParam);
        db.getAllCourses();
    }
}
