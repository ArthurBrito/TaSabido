package br.ufc.engsoftware.tasabido;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Collection;

import br.ufc.engsoftware.Ormlite.Curso;
import br.ufc.engsoftware.Ormlite.Data;
import br.ufc.engsoftware.Ormlite.Duvida;
import br.ufc.engsoftware.Ormlite.Materia;
import br.ufc.engsoftware.Ormlite.Monitoria;
import br.ufc.engsoftware.Ormlite.Subtopico;
import br.ufc.engsoftware.Ormlite.config.OpenDatabaseHelper;
import br.ufc.engsoftware.Presenters.DataPresenter;
import br.ufc.engsoftware.auxiliar.Utils;

/**
 * Created by limaneto on 24/07/16.
 */
public class MainActivity extends AppCompatActivity implements DataPresenter.DataCallbackListener {

    private DataPresenter dataPresenter;
    Utils utils;
    OpenDatabaseHelper openDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataPresenter = new DataPresenter(this);
        utils =  new Utils(this);
        openDatabaseHelper = OpenHelperManager.getHelper(getApplicationContext(),
                OpenDatabaseHelper.class);
        sincronizarInfo();
    }

    // Baixando dados do servidor e salvando no BD local
    public void sincronizarInfo(){
        utils.createProgressDialog("Sincronizando informações.");
        if(utils.checkConnection(this)){

            dataPresenter.getData();
        }
    }

    @Override
    public void dadosPreparados(Data cursos) {
        utils.dismissProgressDialog();
        salvarDadosDoServidor(cursos);
    }

    public void salvarDadosDoServidor(Data cursosParam){
        Collection<Curso> cursos = cursosParam.results;

        for (Curso curso: cursos) {
            Collection<Materia> materias = curso.getMaterias();
            openDatabaseHelper.salvarCurso(curso);

            for (Materia materia: materias) {
                Collection<Subtopico> subtopicos = materia.getSubtopicos();
                openDatabaseHelper.salvarMateria(materia);

                for (Subtopico subtopico: subtopicos) {
                    Collection<Duvida> duvidas = subtopico.getDuvidas();
                    openDatabaseHelper.salvarSubtopico(subtopico);

                    for (Duvida duvida: duvidas) {
                        openDatabaseHelper.salvarDuvida(duvida);
                    }

                    Collection<Monitoria> monitorias = subtopico.getMonitorias();

                    for (Monitoria monitoria: monitorias) {
                        openDatabaseHelper.salvarMonitoria(monitoria);
                    }
                }
            }
        }
    }
}
