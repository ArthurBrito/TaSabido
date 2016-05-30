package br.ufc.engsoftware.tasabido;


import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import br.ufc.engsoftware.BDLocalManager.DuvidaBDManager;
import br.ufc.engsoftware.BDLocalManager.MonitoriaBDManager;
import br.ufc.engsoftware.fragments.DatePickerFragment;
import br.ufc.engsoftware.fragments.TimePickerFragment;
import br.ufc.engsoftware.views.DuvidaListView;
import br.ufc.engsoftware.views.MonitoriaListView;


public class ListaMonitoriasActivity extends AppCompatActivity {

    // Informaçoes da duvida selecionada
    String titulo, nome_subtopico;
    int id_subtopico;

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

        // Seta as configurações da ActionBar
//        ActionBar ab = getSupportActionBar();
//        ab.setTitle(nome_subtopico);
//        ab.setDisplayHomeAsUpEnabled(true);

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

}
