package br.ufc.engsoftware.Ormlite.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

import br.ufc.engsoftware.Ormlite.Curso;
import br.ufc.engsoftware.Ormlite.Duvida;
import br.ufc.engsoftware.Ormlite.Materia;
import br.ufc.engsoftware.Ormlite.Monitoria;
import br.ufc.engsoftware.Ormlite.Subtopico;
import br.ufc.engsoftware.tasabido.R;

public class OpenDatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "tasabido";
    private static final int DATABASE_VERSION = 1;

    /**
     * The data access object used to interact with the Sqlite database to do C.R.U.D operations.
     */
    private Dao<Subtopico, Long> subtopicoDao;
    private Dao<Materia, Long> materiaDao;
    private Dao<Monitoria, Long> monitoriaDao;
    private Dao<Duvida, Long> duvidaDao;
    private Dao<Curso, Long> cursoDao;


    public OpenDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION,
                /**
                 * R.raw.ormlite_config is a reference to the ormlite_config.txt file in the
                 * /res/raw/ directory of this project
                 * */
                R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {

            /**
             * creates the Monitoria database table
             */
            TableUtils.createTable(connectionSource, Curso.class);
            TableUtils.createTable(connectionSource, Subtopico.class);
            TableUtils.createTable(connectionSource, Materia.class);
            TableUtils.createTable(connectionSource, Monitoria.class);
            TableUtils.createTable(connectionSource, Duvida.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            /**
             * Recreates the database when onUpgrade is called by the framework
             */
            TableUtils.dropTable(connectionSource, Curso.class, false);
            TableUtils.dropTable(connectionSource, Materia.class, false);
            TableUtils.dropTable(connectionSource, Subtopico.class, false);
            TableUtils.dropTable(connectionSource, Monitoria.class, false);
            TableUtils.dropTable(connectionSource, Duvida.class, false);
            onCreate(database, connectionSource);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns an instance of the data access object
     * @return
     * @throws SQLException
     */

    public Dao<Curso, Long> getCursoDao() throws SQLException {
        if(cursoDao == null) {
            cursoDao = getDao(Curso.class);
        }
        return cursoDao;
    }

    public Dao<Subtopico, Long> getSubtopicoDao() throws SQLException {
        if(subtopicoDao == null) {
            subtopicoDao = getDao(Subtopico.class);
        }
        return subtopicoDao;
    }

    public Dao<Materia, Long> getMateriaDao() throws SQLException {
        if(materiaDao == null) {
            materiaDao = getDao(Materia.class);
        }
        return materiaDao;
    }

    public Dao<Monitoria, Long> getMonitoriaDao() throws SQLException {
        if(monitoriaDao == null) {
            monitoriaDao = getDao(Monitoria.class);
        }
        return monitoriaDao;
    }

    public Dao<Duvida, Long> getDuvidaDao() throws SQLException {
        if(duvidaDao == null) {
            duvidaDao = getDao(Duvida.class);
        }
        return duvidaDao;
    }


    public void salvarCurso(Curso cursoParam){
        Dao<Curso, Long> cursoDao = null;
        try {
            cursoDao = getCursoDao();
            cursoDao.create(cursoParam);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void salvarMateria(Materia materiaParam){
        Dao<Materia, Long> materiaDao = null;
        try {
            materiaDao = getMateriaDao();
            materiaDao.create(materiaParam);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void salvarSubtopico(Subtopico subtopicoParam){
        Dao<Subtopico, Long> subtopicoDao = null;
        try {
            subtopicoDao = getSubtopicoDao();
            subtopicoDao.create(subtopicoParam);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void salvarMonitoria(Monitoria monitoriaParam){
        Dao<Monitoria, Long> monitoriaDao = null;
        try {
            monitoriaDao = getMonitoriaDao();
            monitoriaDao.create(monitoriaParam);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void salvarDuvida(Duvida duvidaParam){
        Dao<Duvida, Long> duvidaDao = null;
        try {
            duvidaDao = getDuvidaDao();
            duvidaDao.create(duvidaParam);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}