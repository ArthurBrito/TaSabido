package br.ufc.engsoftware.tasabido.ListActivitys;


import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import br.ufc.engsoftware.BDLocalManager.DuvidaBDManager;
import br.ufc.engsoftware.auxiliar.Utils;
import br.ufc.engsoftware.models.Duvida;
import br.ufc.engsoftware.retrofit.GetDuvidasServer;
import br.ufc.engsoftware.tasabido.CriarDuvidaActivity;
import br.ufc.engsoftware.tasabido.R;
import br.ufc.engsoftware.views.DuvidaListView;


public class ListaDuvidasActivity extends AppCompatActivity {

    // Informaçoes da duvida selecionada
    String nome_subtopico;
    int id_materia;
    int id_subtopico;
    Spinner spinner;
    SwipeRefreshLayout swipe;
    Vector<Duvida> duvidasAjudarei;
    Vector<Duvida> duvidasCriadas;
    Utils utils;

    // Referencia para o ListView da interface
    ListView listViewDuvidas;

    // Estado do ListView e suas informações
    DuvidaListView gerenciadorDuvidasLV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duvidas);

        // Pega a intent que chamou essa activity
        Intent intent = getIntent();
        nome_subtopico = intent.getStringExtra("NOME_SUBTOPICO");
        id_subtopico = intent.getIntExtra("ID_SUBTOPICO", 0);
        id_materia = intent.getIntExtra("ID_MATERIA", 0);
        utils = new Utils(this);

        // Seta as configurações da ActionBar
        ActionBar ab = getSupportActionBar();
        ab.setTitle(nome_subtopico);
        ab.setDisplayHomeAsUpEnabled(true);

//        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
//        swipe.setOnRefreshListener(this);

        spinner = (Spinner) findViewById(R.id.spinner_lista_duvidas);
        setSpinner();

        // Captura a referencia pro ListView a partir do id
        listViewDuvidas = (ListView) findViewById(R.id.listview_duvidas);
    }

    private void montarListViewDuvidas(){
        //pega os subtopicos salvos no banco de dados local pela requisição com o servidor
        DuvidaBDManager duvidaDB = new DuvidaBDManager();
        gerenciadorDuvidasLV = new DuvidaListView(listViewDuvidas, this, duvidaDB.pegarDuvidasPorIdSubtopico(this, id_subtopico));

    }

    private void montarListViewDuvidasAjudarei(){
        //pega os subtopicos salvos no banco de dados local pela requisição com o servidor
        DuvidaBDManager duvidaDB = new DuvidaBDManager();
        Utils utils = new Utils(this);
        Set<String> array_ids = new HashSet<>();
        array_ids = utils.getDuvidasConfirmadasFromSharedPreferences("duvidas", array_ids);
        duvidasAjudarei = new Vector<Duvida>();

        for (String id_duvida: array_ids) {
            Duvida duvida = duvidaDB.pegarDuvidasPorIdDuvida(this, Integer.parseInt(id_duvida));
            duvidasAjudarei.add(duvida);
        }
        gerenciadorDuvidasLV = new DuvidaListView(listViewDuvidas, this, duvidasAjudarei);
    }

    private void montarListViewDuvidasCriei(){
        //pega os subtopicos salvos no banco de dados local pela requisição com o servidor
        pegarDuvidasCriadas();
        gerenciadorDuvidasLV = new DuvidaListView(listViewDuvidas, this, duvidasCriadas);
    }

    private void pegarDuvidasCriadas() {
        DuvidaBDManager duvidaDB = new DuvidaBDManager();
        int id_usuario = Integer.parseInt(utils.getFromSharedPreferences("id_usuario", ""));
        duvidasCriadas = duvidaDB.pegarDuvidasPorIdUsuarioSubtopico(this, id_usuario, id_subtopico);
    }

    public void onClickChamarCriarDuvida(View view){

        if (!temSaldo()){
            Toast.makeText(this, "Você não tem moedas suficientes.", Toast.LENGTH_SHORT).show();
            return;
        }
            Intent intent = new Intent(this, CriarDuvidaActivity.class);
            intent.setAction("br.ufc.engsoftware.tasabido.CRIAR_DUVIDA");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("ID_SUBTOPICO", id_subtopico);
            intent.putExtra("ID_MATERIA", id_materia);
            startActivity(intent);

    }

    private boolean temSaldo() {
        int qt_moedas = utils.getIntFromSharedPreferences("moedas", 0);
        pegarDuvidasCriadas();

        if (duvidasCriadas != null){
            int qt_duvidas_criadas = duvidasCriadas.size();

            if (qt_moedas > qt_duvidas_criadas)
                return true;
            else
                return false;
        }
            return true;
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

    public void onClickChamarVerMonitorias(View view){
        Intent intent = new Intent(this, ListaMonitoriasActivity.class);
        intent.setAction("br.ufc.engsoftware.tasabido.CRIAR_DUVIDA");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("NOME_SUBTOPICO", nome_subtopico);
        intent.putExtra("ID_SUBTOPICO", id_subtopico);
        intent.putExtra("ID_MATERIA", id_materia);
        startActivity(intent);
    }

    public void setSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.options_search_duvidas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        montarListViewDuvidas();
                        break;
                    case 1:
                        montarListViewDuvidasCriei();
                        break;
                    case 2:
                        montarListViewDuvidasAjudarei();
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

//    @Override
//    public void onRefresh() {
//        //atualizar duvidas do servidor
////        Handler handler = new Handler();
//        GetDuvidasServer retrofitDuvidas = new GetDuvidasServer(this);
//        retrofitDuvidas.pegarDuvidasDoServidor();
//        montarListViewDuvidas();
//        swipe.setRefreshing(false);
//    }
}
