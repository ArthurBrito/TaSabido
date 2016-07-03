package br.ufc.engsoftware.tasabido;


import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import br.ufc.engsoftware.Ormlite.IdSubtopico;
import br.ufc.engsoftware.Ormlite.Monitoria;
import br.ufc.engsoftware.auxiliar.MonitoriaOpenDatabaseHelper;
import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.views.MonitoriaListView;


public class ListaMonitoriasActivity extends AppCompatActivity {

    // Informaçoes da duvida selecionada
    String titulo, nome_subtopico;
    int id_subtopico, id_materia;
    Spinner spinner;

    // Referencia para o ListView da interface
    ListView listViewMonitorias;

    // Estado do ListView e suas informações
    MonitoriaListView gerenciadorMonitoriasLV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_monitorias);

        // Pega a intent que chamou essa activity
        Intent intent = getIntent();

        nome_subtopico = intent.getStringExtra("NOME_SUBTOPICO");
        titulo = intent.getStringExtra("TITULO");
        id_subtopico = intent.getIntExtra("ID_SUBTOPICO", 0);
        id_materia = intent.getIntExtra("ID_MATERIA", 0);

        // Seta as configurações da ActionBar
        ActionBar ab = getSupportActionBar();
        String titleOfActionBar = "Monitorias em - ";
        titleOfActionBar += nome_subtopico;
        ab.setTitle(titleOfActionBar);
        ab.setDisplayHomeAsUpEnabled(true);

        spinner = (Spinner) findViewById(R.id.spinner_lista_subtopicos);
        setSpinner();


        // Captura a referencia pro ListView a partir do id
        listViewMonitorias = (ListView) findViewById(R.id.listview_monitorias);

        //Metodo responsável por montar o ListView das Dúvidas
        try {
            montarListViewMonitorias();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void montarListViewMonitorias() throws SQLException {


        Vector<Monitoria> monitorias = new Vector<>();
        List<Monitoria> monitoriasFromOrm = new ArrayList<>();

        monitoriasFromOrm = getDao().queryForAll();

        for (Monitoria m: monitoriasFromOrm) {
            if (m.getId_subtopicos() == id_subtopico){
                monitorias.add(m);
            }
        }
        gerenciadorMonitoriasLV = new MonitoriaListView(listViewMonitorias, this, monitorias);
    }

    private void montarListViewMonitoriasParticiparei() throws SQLException {

        Utils utils = new Utils(this);
        Set<String> array_ids = new HashSet<>();
        array_ids = utils.getMonitoriasConfirmadasFromSharedPreferences("monitorias", array_ids);
        Vector<Monitoria> vector_monitorias = new Vector<Monitoria>();

        for (String id_monitoria: array_ids) {

            Monitoria monitoria = (Monitoria) getDao().queryForId(id_monitoria);
            vector_monitorias.add(monitoria);
        }

        gerenciadorMonitoriasLV = new MonitoriaListView(listViewMonitorias, this, vector_monitorias);

    }

    private void montarListViewMonitoriasCriei() throws SQLException {
        Utils u = new Utils(this);
        int id_usuario = Integer.parseInt(u.getFromSharedPreferences("id_usuario", ""));


        Vector<Monitoria> monitorias = new Vector<>();
        List<Monitoria> monitoriasFromOrm = new ArrayList<>();

        monitoriasFromOrm = getDao().queryForAll();


        for (Monitoria m: monitoriasFromOrm) {
            if (m.getId_subtopicos() == id_subtopico && m.getId_usuario() == id_usuario){
                monitorias.add(m);
            }
        }
        gerenciadorMonitoriasLV = new MonitoriaListView(listViewMonitorias, this, monitorias);
    }

    // Seta ação do botão de voltar na ActionBar
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.options_search_monitorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    switch (position){
                        case 0:
                            montarListViewMonitorias();
                            break;
                        case 1:
                            montarListViewMonitoriasCriei();
                            break;
                        case 2:
                            montarListViewMonitoriasParticiparei();
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public Dao getDao(){
        MonitoriaOpenDatabaseHelper monitoriaOpenDatabaseHelper = OpenHelperManager.getHelper(this,
                MonitoriaOpenDatabaseHelper.class);

        Dao<Monitoria, Long> monitoriaDao = null;
        try {
             monitoriaDao = monitoriaOpenDatabaseHelper.getDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return monitoriaDao;
    }
}
