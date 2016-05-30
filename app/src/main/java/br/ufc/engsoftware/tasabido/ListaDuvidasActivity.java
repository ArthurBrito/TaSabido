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
import br.ufc.engsoftware.fragments.DatePickerFragment;
import br.ufc.engsoftware.fragments.TimePickerFragment;
import br.ufc.engsoftware.views.DuvidaListView;


public class ListaDuvidasActivity extends AppCompatActivity {

    // Informaçoes da duvida selecionada
    String nome_subtopico;
    int id_materia;
    int id_subtopico;

    // Referencia para o ListView da interface
    ListView listviewDuvidas;

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

        // Seta as configurações da ActionBar
//        ActionBar ab = getSupportActionBar();
//        ab.setTitle(nome_subtopico);
//        ab.setDisplayHomeAsUpEnabled(true);

        // Captura a referencia pro ListView a partir do id
        listviewDuvidas = (ListView) findViewById(R.id.listview_duvidas);

        //Metodo responsável por montar o ListView das Dúvidas
        montarListViewDuvidas();

    }

    private void montarListViewDuvidas(){
        //pega os subtopicos salvos no banco de dados local pela requisição com o servidor
        DuvidaBDManager duvidaDB = new DuvidaBDManager();
        gerenciadorDuvidasLV = new DuvidaListView(listviewDuvidas, this, duvidaDB.pegarDuvidasPorIdSubtopico(this, id_subtopico));

    }

    public void onClickChamarCriarDuvida(View view){
        Intent intent = new Intent(this, CriarDuvidaActivity.class);
        intent.setAction("br.ufc.engsoftware.tasabido.CRIAR_DUVIDA");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("ID_SUBTOPICO", id_subtopico);
        intent.putExtra("ID_MATERIA", id_materia);
        startActivity(intent);
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
}
