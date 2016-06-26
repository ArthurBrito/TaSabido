package br.ufc.engsoftware.tasabido;


import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import br.ufc.engsoftware.BDLocalManager.DuvidaBDManager;
import br.ufc.engsoftware.BDLocalManager.MonitoriaBDManager;
import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.fragments.DatePickerFragment;
import br.ufc.engsoftware.fragments.TimePickerFragment;
import br.ufc.engsoftware.models.Monitoria;
import br.ufc.engsoftware.views.DuvidaListView;
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
        montarListViewMonitorias();

    }

    private void montarListViewMonitorias(){
        //pega os subtopicos salvos no banco de dados local pela requisição com o servidor
        MonitoriaBDManager duvidaDB = new MonitoriaBDManager();
        gerenciadorMonitoriasLV = new MonitoriaListView(listViewMonitorias, this, duvidaDB.pegarMonitoriasPorIdSubtopico(this, id_subtopico));

    }

    private void montarListViewMonitoriasParticiparei(){
        //pega os subtopicos salvos no banco de dados local pela requisição com o servidor
        MonitoriaBDManager duvidaDB = new MonitoriaBDManager();
        Utils utils = new Utils(this);
        Set<String> array_ids = new HashSet<>();
        array_ids = utils.getMonitoriasConfirmadasFromSharedPreferences("monitorias", array_ids);
        Vector<Monitoria> vector_monitorias = new Vector<Monitoria>();

        for (String id_monitoria: array_ids) {
            Monitoria monitoria = duvidaDB.pegarMonitoriasPorIdMonitoria(this, Integer.parseInt(id_monitoria));
            vector_monitorias.add(monitoria);
        }

        gerenciadorMonitoriasLV = new MonitoriaListView(listViewMonitorias, this, vector_monitorias);

    }

    private void montarListViewMonitoriasCriei(){
        //pega os subtopicos salvos no banco de dados local pela requisição com o servidor
        MonitoriaBDManager monitoriaDB = new MonitoriaBDManager();
        Utils u = new Utils(this);
        int id_usuario = Integer.parseInt(u.getFromSharedPreferences("id_usuario", ""));
        gerenciadorMonitoriasLV = new MonitoriaListView(listViewMonitorias, this, monitoriaDB.pegarMonitoriasPorIdUsuario(this, id_usuario));
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

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
