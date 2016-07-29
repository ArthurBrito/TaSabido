package br.ufc.engsoftware.Ormlite.config;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import br.ufc.engsoftware.Ormlite.Curso;
import br.ufc.engsoftware.Ormlite.Data;
import br.ufc.engsoftware.Ormlite.Duvida;
import br.ufc.engsoftware.Ormlite.Materia;
import br.ufc.engsoftware.Ormlite.Monitoria;
import br.ufc.engsoftware.Ormlite.Subtopico;

/**
 * Created by limaneto on 25/07/16.
 */
public class DatabaseManager {

    private Context context;
    OpenDatabaseHelper openDatabaseHelper;

    public DatabaseManager(){

    }

    public DatabaseManager(Context context){
        this.context = context;
        openDatabaseHelper = OpenHelperManager.getHelper(context, OpenDatabaseHelper.class);
//        openDatabaseHelper = new OpenDatabaseHelper(context);
        openDatabaseHelper.getWritableDatabase();
    }


    /* SAVE */
    public void saveAllData(Data cursosList){
        Collection<Curso> cursos = cursosList.results;

        for (Curso curso: cursos) {
            Collection<Materia> materias = curso.getMaterias();
            salvarCurso(curso);

            for (Materia materia: materias) {
                Collection<Subtopico> subtopicos = materia.getSubtopicos();
                salvarMateria(materia);

                for (Subtopico subtopico: subtopicos) {
                    Collection<Duvida> duvidas = subtopico.getDuvidas();
                    salvarSubtopico(subtopico);

                    for (Duvida duvida: duvidas) {
                        salvarDuvida(duvida);
                    }

                    Collection<Monitoria> monitorias = subtopico.getMonitorias();

                    for (Monitoria monitoria: monitorias) {
                        salvarMonitoria(monitoria);
                    }
                }
            }
        }
    }

    public void salvarCurso(Curso cursoParam){
        Dao<Curso, Long> cursoDao = null;
        try {
            cursoDao = openDatabaseHelper.getCursoDao();
            cursoDao.create(cursoParam);
            List<Curso> c = openDatabaseHelper.getCursoDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void salvarMateria(Materia materiaParam){
        Dao<Materia, Long> materiaDao = null;
        try {
            materiaDao = openDatabaseHelper.getMateriaDao();
            materiaDao.create(materiaParam);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void salvarSubtopico(Subtopico subtopicoParam){
        Dao<Subtopico, Long> subtopicoDao = null;
        try {
            subtopicoDao = openDatabaseHelper.getSubtopicoDao();
            subtopicoDao.create(subtopicoParam);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void salvarMonitoria(Monitoria monitoriaParam){
        Dao<Monitoria, Long> monitoriaDao = null;
        try {
            monitoriaDao = openDatabaseHelper.getMonitoriaDao();
            monitoriaDao.create(monitoriaParam);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void salvarDuvida(Duvida duvidaParam){
        Dao<Duvida, Long> duvidaDao = null;
        try {
            duvidaDao = openDatabaseHelper.getDuvidaDao();
            duvidaDao.create(duvidaParam);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* END SAVE */

    /* READ */

    public void getAllCourses(){
        try {
            List<Curso> curso = openDatabaseHelper.getCursoDao().queryForAll();
            String a ="a";
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
